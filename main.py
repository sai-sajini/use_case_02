import os
import tempfile
import json
import shutil
from preprocessing import clone_repo, get_ui_files, parse_ui_files, create_empty_files, write_code_to_file, parse_and_write_llm_output
from azure.ai.inference import ChatCompletionsClient
from azure.ai.inference.models import UserMessage
from azure.core.credentials import AzureKeyCredential
from dotenv import load_dotenv
from datetime import datetime


# Load API key from .env file
load_dotenv()  # This loads variables from .env
GITHUB_TOKEN = os.environ.get("GITHUB_TOKEN", "")
print("GITHUB_TOKEN loaded:", repr(GITHUB_TOKEN))

# Load prompt from prompt_base.txt
with open("prompt_base.txt", "r", encoding="utf-8") as f:
    prompt = f.read()

client = ChatCompletionsClient(
    endpoint="https://models.github.ai/inference",
    credential=AzureKeyCredential(GITHUB_TOKEN),
)

def call_github_llm(prompt, max_tokens=2048):
    response = client.complete(
        messages=[UserMessage(prompt)],
        model="openai/gpt-4.1",
        max_tokens=max_tokens,
    )
    return response.choices[0].message.content

def save_project_files(files_content, output_dir="output"):
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)
    for filename, content in files_content.items():
        file_path = os.path.join(output_dir, filename)
        os.makedirs(os.path.dirname(file_path), exist_ok=True)
        with open(file_path, "w", encoding="utf-8") as f:
            f.write(content)

def move_and_version_file(src_path, dest_dir):
    """
    Moves src_path to dest_dir with a dynamic version (timestamp) appended.
    Returns the new path.
    """
    base = os.path.basename(src_path)                # e.g., 'llm_raw_output.txt'
    name, ext = os.path.splitext(base)               # e.g., ('llm_raw_output', '.txt')
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    new_name = f"{name}_v{timestamp}{ext}"           # e.g., 'llm_raw_output_v20240822_030700.txt'
    dest_path = os.path.join(dest_dir, new_name)
    shutil.move(src_path, dest_path)
    return dest_path

if __name__ == "__main__":

    output_dir = "output"
    history_dir = "history"
    raw_output_filename = "llm_raw_output.txt"
    raw_output_path = os.path.join(output_dir, raw_output_filename)

    # Get git repo link from user
    repo_link = input("Enter the git repo link: ").strip()

    # Clone repo to temp dir
    with tempfile.TemporaryDirectory() as clone_dir:
        print(f"Cloning repo to {clone_dir}...")
        clone_repo(repo_link, clone_dir)
        print("Repo cloned.")

        # Filter UI files
        ui_files = get_ui_files(clone_dir)
        print(f"Found {len(ui_files)} UI files.")

        # Parse UI files for interactive elements
        ui_metadata = parse_ui_files(ui_files)
        ui_json = json.dumps(ui_metadata, indent=2)

        # Load prompt_base.txt
        with open("prompt_base.txt", "r", encoding="utf-8") as f:
            prompt_base = f.read()

        # Prepare prompt for LLM: send prompt_base and github metadata
        base_prompt = f"Prompt:\n{prompt_base}\n\nGitHub Metadata:\n{ui_json}"
        llm_file_names_output = call_github_llm(base_prompt)

        # Parse output as list of file names
        try:
            file_names = eval(llm_file_names_output)
            if isinstance(file_names, list):
                num_of_files = len(file_names)
                print(f"File names from LLM: {file_names}")
                print(f"Number of files: {num_of_files}")
                # Create empty files using preprocessing.py
                from preprocessing import create_empty_files
                create_empty_files(file_names, base_dir=output_dir)
            else:
                print("LLM output is not a list of file names. Output:")
                print(llm_file_names_output)
        except Exception as e:
            print("Failed to parse LLM output as list. Output:")
            print(llm_file_names_output)

        # Call LLM n times with prompt_common.txt, passing current_file each time
        if 'file_names' in locals() and isinstance(file_names, list):
            with open("prompt_common.txt", "r", encoding="utf-8") as f:
                prompt_common = f.read()
            from preprocessing import write_code_to_file
            for i in range(num_of_files):
                current_file = file_names[i]
                # Prepare prompt for LLM for current file
                file_prompt = f"Prompt:\n{prompt_common}\n\nProject Structure:\n{file_names}\n\nFile for which you should generate code: {current_file}\n"
                llm_file_output = call_github_llm(file_prompt)
                # Expecting output: {current_file: code}
                try:
                    file_dict = eval(llm_file_output)
                    if isinstance(file_dict, dict) and current_file in file_dict:
                        parse_and_write_llm_output(file_dict, base_dir=output_dir)
                        print(f"Code for {current_file} written to file.")
                    else:
                        print(f"LLM output for {current_file} is not valid. Output:")
                        print(llm_file_output)
                except Exception as e:
                    print(f"Failed to parse LLM output for {current_file}. Output:")
                    print(llm_file_output)

        # Combine prompt, repo link, and UI metadata
        full_prompt = f"Prompt:\n{prompt}\n\nUI Metadata:\n{ui_json}"

        # Check if previous output exists and move to history with version if so
        if os.path.exists(raw_output_path):
            if not os.path.exists(history_dir):
                os.makedirs(history_dir)
            moved_path = move_and_version_file(raw_output_path, history_dir)
            print(f"Previous output moved to history: {moved_path}")

        # Ask the LLM to generate Python project files
        llm_output = call_github_llm(full_prompt)

        # Save the raw LLM output to a file in the output folder
        if not os.path.exists(output_dir):
            os.makedirs(output_dir)
        with open(raw_output_path, "w", encoding="utf-8") as raw_file:
            raw_file.write(llm_output)

        # Expecting LLM output to be a dict of {filename: filecontent}
        try:
            files_content = eval(llm_output)
            if isinstance(files_content, dict):
                save_project_files(files_content)
                print("Project files generated and saved in 'output' folder.")
            else:
                print("LLM output is not a dict of files. Output:")
                print(llm_output)
        except Exception as e:
            print("Failed to parse LLM output as dict. Output:")
            print(llm_output)
