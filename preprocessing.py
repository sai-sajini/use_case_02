import subprocess
import shutil
from bs4 import BeautifulSoup
def parse_and_write_llm_output(llm_output_dict, base_dir="output"):
    """
    Given a dictionary {filename: code}, write each code to the corresponding file.
    """
    if isinstance(llm_output_dict, dict):
        for fname, code in llm_output_dict.items():
            file_path = os.path.join(base_dir, fname)
            os.makedirs(os.path.dirname(file_path), exist_ok=True)
            with open(file_path, "w", encoding="utf-8") as f:
                f.write(code)
def create_empty_files(file_names, base_dir="output"):
    """
    Creates empty files for each file name in the list, including directories.
    """
    for fname in file_names:
        file_path = os.path.join(base_dir, fname)
        os.makedirs(os.path.dirname(file_path), exist_ok=True)
        with open(file_path, "w", encoding="utf-8") as f:
            pass

def write_code_to_file(file_name, code, base_dir="output"):
    """
    Writes code to the specified file (overwrites if exists).
    """
    file_path = os.path.join(base_dir, file_name)
    os.makedirs(os.path.dirname(file_path), exist_ok=True)
    with open(file_path, "w", encoding="utf-8") as f:
        f.write(code)
import os
import re

def clone_repo(repo_url, clone_dir):
    # Clear the directory if it exists and is not empty
    if os.path.exists(clone_dir):
        for filename in os.listdir(clone_dir):
            file_path = os.path.join(clone_dir, filename)
            if os.path.isfile(file_path) or os.path.islink(file_path):
                os.unlink(file_path)
            elif os.path.isdir(file_path):
                import shutil
                shutil.rmtree(file_path)
    subprocess.run(["git", "clone", repo_url, clone_dir], check=True)


def get_ui_files(root_dir):
    ui_extensions = {".html", ".jsp", ".js", ".jsx", ".vue", ".ts", ".css"}
    ui_files = []
    for dirpath, _, filenames in os.walk(root_dir):
        for fname in filenames:
            ext = os.path.splitext(fname)[1].lower()
            if ext in ui_extensions:
                ui_files.append(os.path.join(dirpath, fname))
    return ui_files


def extract_elements_from_html(html_content):
    soup = BeautifulSoup(html_content, "html.parser")
    elements = []
    # Input fields
    for inp in soup.find_all(["input", "textarea"]):
        locator = {k: v for k, v in inp.attrs.items() if k in ["id", "name", "class", "aria-label"]}
        elements.append({"tag": inp.name, "locator": locator, "action": "enter"})
    # Buttons
    for btn in soup.find_all(["button"]):
        locator = {k: v for k, v in btn.attrs.items() if k in ["id", "name", "class", "aria-label"]}
        elements.append({"tag": btn.name, "locator": locator, "action": "click"})
    # Links
    for link in soup.find_all("a"):
        locator = {k: v for k, v in link.attrs.items() if k in ["id", "name", "class", "aria-label"]}
        locator["text"] = link.get_text(strip=True)
        elements.append({"tag": "a", "locator": locator, "action": "click"})
    # Dropdowns/select
    for sel in soup.find_all("select"):
        locator = {k: v for k, v in sel.attrs.items() if k in ["id", "name", "class", "aria-label"]}
        elements.append({"tag": "select", "locator": locator, "action": "select"})
    # Checkboxes/radio
    for chk in soup.find_all("input", {"type": ["checkbox", "radio"]}):
        locator = {k: v for k, v in chk.attrs.items() if k in ["id", "name", "class", "aria-label"]}
        elements.append({"tag": chk.name, "locator": locator, "action": "select"})
    # Labels/messages
    for lbl in soup.find_all(["label", "span", "div"]):
        locator = {k: v for k, v in lbl.attrs.items() if k in ["id", "name", "class", "aria-label"]}
        locator["text"] = lbl.get_text(strip=True)
        elements.append({"tag": lbl.name, "locator": locator, "action": "validate"})
    return elements


def extract_elements_from_js(js_content):
    # Simple regex-based extraction for demo purposes
    elements = []
    # Buttons
    for m in re.finditer(r'document\.getElementById\(["\'](.*?)["\']\)', js_content):
        elements.append({"tag": "button", "locator": {"id": m.group(1)}, "action": "click"})
    return elements


def parse_ui_files(ui_files):
    page_elements = {}
    for fpath in ui_files:
        ext = os.path.splitext(fpath)[1].lower()
        with open(fpath, "r", encoding="utf-8", errors="ignore") as f:
            content = f.read()
        if ext in [".html", ".jsp"]:
            elements = extract_elements_from_html(content)
        elif ext in [".js", ".jsx", ".ts", ".vue"]:
            elements = extract_elements_from_js(content)
        else:
            elements = []
        page_name = os.path.relpath(fpath, start=os.path.dirname(ui_files[0]))
        page_elements[page_name] = {"elements": elements}
    return page_elements
