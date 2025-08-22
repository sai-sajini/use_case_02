from dotenv import load_dotenv
import os

load_dotenv()
print("GITHUB_TOKEN loaded:", repr(os.environ.get("GITHUB_TOKEN")))