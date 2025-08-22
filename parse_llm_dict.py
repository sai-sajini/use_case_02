import os
import ast

def extract_python_dict(llm_output: str):
    """
    Extracts the first Python dictionary found in the string.
    Uses ast.literal_eval for safety.
    """
    # Find the first open brace
    start = llm_output.find('{')
    if start == -1:
        raise ValueError("No dictionary found in output (no '{' character).")
    
    # Try to find the matching closing brace
    # Approach: Count braces to find the matching one
    count = 0
    for i, char in enumerate(llm_output[start:], start):
        if char == '{':
            count += 1
        elif char == '}':
            count -= 1
            if count == 0:
                # Found the closing brace
                dict_str = llm_output[start:i+1]
                break
    else:
        raise ValueError("Could not find matching closing brace for dict.")
    
    # Evaluate the dictionary safely
    try:
        files_dict = ast.literal_eval(dict_str)
    except Exception as e:
        raise ValueError(f"Failed to parse dictionary: {e}")
    if not isinstance(files_dict, dict):
        raise ValueError("Extracted object is not a dictionary.")
    return files_dict

def save_files(files_dict, output_dir="output"):
    for filename, content in files_dict.items():
        # Create any necessary directories for nested files
        file_path = os.path.join(output_dir, filename)
        os.makedirs(os.path.dirname(file_path), exist_ok=True)
        with open(file_path, "w", encoding="utf-8") as f:
            f.write(content)

if __name__ == "__main__":
    # Commented code for pasting output directly:
    # llm_output = '''PASTE THE RAW LLM OUTPUT HERE AS A STRING'''

    # Instead, read the output from the file created by main.py (llm_raw_output.txt)
    raw_output_path = os.path.join("output", "llm_raw_output.txt")
    with open(raw_output_path, "r", encoding="utf-8") as file:
        llm_output = file.read()

    files_dict = extract_python_dict(llm_output)
    save_files(files_dict)
    print("Files saved to output/")