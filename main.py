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

        # Load only_prompt.txt
        with open("only_prompt.txt", "r", encoding="utf-8") as f:
            only_prompt = f.read()

        # Prepare prompt for LLM: only_prompt + github metadata
        prompt = f"{only_prompt}\nINPUT: {ui_json}\nOUTPUT:"
        llm_output = call_github_llm(prompt)

        # Save the raw LLM output to a file in the output folder
        if not os.path.exists(output_dir):
            os.makedirs(output_dir)
        with open(raw_output_path, "w", encoding="utf-8") as raw_file:
            raw_file.write(llm_output)
        # Also store the full LLM output in output/output.txt
        with open(os.path.join(output_dir, "output.txt"), "w", encoding="utf-8") as out_file:
            out_file.write(llm_output)

        # Parse and write files using preprocessing.py
        from preprocessing import parse_and_write_llm_output
        try:
            parse_and_write_llm_output(llm_output, base_dir=output_dir)
            print("Project files generated and saved in 'output' folder.")
        except Exception as e:
            print("Failed to parse and write LLM output. Output:")
            print(llm_output)

        