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


if __name__ == "__main__":

    output_dir = "output"
    history_dir = "history"
    raw_output_filename = "llm_raw_output.txt"
    raw_output_path = os.path.join(output_dir, raw_output_filename)

    # Load head_base.txt
    with open("head_base.txt", "r", encoding="utf-8") as f:
        head_base = f.read()
    # Load tail_base.txt
    with open("tail_base.txt", "r", encoding="utf-8") as f:
        tail_base = f.read()
    # Load specs.txt
    with open("specs.txt", "r", encoding="utf-8") as f:
        specs = f.read()
    # Load head_common.txt
    with open("head_common.txt", "r", encoding="utf-8") as f:
        head_common = f.read()
    # Load tail_common.txt
    with open("tail_common.txt", "r", encoding="utf-8") as f:
                tail_common = f.read()

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

        # Prepare prompt for LLM: send head_base and github metadata
        base_prompt = f"Prompt:\n{head_base}\n{specs}\n{tail_base}\n\nGitHub Metadata:\n{ui_json}"
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

        # Call LLM n times with head_common.txt, passing current_file each time
        if 'file_names' in locals() and isinstance(file_names, list):
            
            from preprocessing import write_code_to_file
            for i in range(num_of_files):
                count = 1
                current_file = file_names[i]
                # Prepare prompt for LLM for current file
                file_prompt = f"Prompt:\n{head_common}\n{specs}\n{tail_common}\n\nProject Structure:\n{file_names}\n\nFile for which you should generate code: {current_file}\n"
                llm_file_output = call_github_llm(file_prompt)
                
                # Expecting output: {current_file: code}
                try:
                    file_dict = eval(llm_file_output)
                    if isinstance(file_dict, dict) and current_file in file_dict:
                        parse_and_write_llm_output(file_dict, base_dir=output_dir)
                        print(f"{count} Code for {current_file} written to file.")
                        count = count + 1
                    else: 
                        print(f"LLM output for {current_file} is not valid. Output:")
                        print(llm_file_output)
                    
                except Exception as e:
                    print(f"Failed to parse LLM output for {current_file}. Output:")
                    print(llm_file_output)

        