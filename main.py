import os
import shutil
from datetime import datetime
from azure.ai.inference import ChatCompletionsClient
from azure.ai.inference.models import UserMessage
from azure.core.credentials import AzureKeyCredential
from dotenv import load_dotenv

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
        model="deepseek/DeepSeek-R1-0528",
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
    # Insert your prompt here
    prompt = (
        'Make a simple python project that acts like a calculator. It should be able to do basic arithmetic operations like addition, subtraction, multiplication, and division. The project should include any necessary modules. The code should be well-structured and easy to understand. It should be runnable.'
        'Please output the entire project as a single Python dictionary.'
        'You must give your entire response as a Python dictionary.'
        'start your python dictionary with title "--rawcode"'
        'Each key should be a filename (including any folder path if necessary), and each value should be the complete contents of that file as a string.'
        'Do not include explanations, comments, or any extra text. Give only the Python dictionary.'
        'correct Example output:'
        '{'
        '"main.py": "print("Hello, world!")\n",'
        '"src/calculator.py": "def add(a, b):\n    return a + b\n",'
        '"requirements.txt": ""'
        '}'
        'incorrect example: We are creating a simple calculator project with the following structure: A main script: `calculator/main.py`, A module for calculator operations: `calculator/operations.py`, A requirements.txt file (though no external ---- '
        'dont include any explanations, comments, or any extra text—only the Python dictionary.'
        'If you add any extra explanation, you will be penalized.'
    )

    output_dir = "output"
    history_dir = "history"
    raw_output_filename = "llm_raw_output.txt"
    raw_output_path = os.path.join(output_dir, raw_output_filename)

    # Check if previous output exists and move to history with version if so
    if os.path.exists(raw_output_path):
        if not os.path.exists(history_dir):
            os.makedirs(history_dir)
        moved_path = move_and_version_file(raw_output_path, history_dir)
        print(f"Previous output moved to history: {moved_path}")

    # Ask the LLM to generate Python project files
    llm_output = call_github_llm(prompt)

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
        print("Error:", e)