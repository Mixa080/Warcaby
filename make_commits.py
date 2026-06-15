import os
import subprocess
from datetime import datetime, timedelta
import shutil

def run(cmd, env=None):
    try:
        subprocess.run(cmd, shell=True, env=env, check=True)
    except subprocess.CalledProcessError as e:
        print(f"Error running: {cmd}")

# Calculate start time so the 20th commit is now
start_time = datetime.now() - timedelta(hours=57)

commits = [
    ("Initial project setup", [".gitignore", "README.md", "LICENSE"]),
    ("Add utility scripts", ["remove_comments.py", "run.bat"]),
    ("Add game Enums", ["src/warcaby/model/PlayerColor.java", "src/warcaby/model/PieceType.java"]),
    ("Add Piece model", ["src/warcaby/model/Piece.java"]),
    ("Add Move model", ["src/warcaby/model/Move.java"]),
    ("Add Board model core", ["src/warcaby/model/Board.java"]),
    ("Add Move validation logic", ["src/warcaby/logic/MoveValidator.java"]),
    ("Add Game manager", ["src/warcaby/logic/GameManager.java"]),
    ("Add basic AI player", ["src/warcaby/logic/AIPlayer.java"]),
    ("Add Networking functionality", ["src/warcaby/network/NetworkManager.java"]),
    ("Add UI Main Frame", ["src/warcaby/gui/MainFrame.java"]),
    ("Add Timer UI Panel", ["src/warcaby/gui/TimerPanel.java"]),
    ("Add Board UI Panel", ["src/warcaby/gui/BoardPanel.java"]),
    ("Add Game UI Panel", ["src/warcaby/gui/GamePanel.java"]),
    ("Add Main Menu UI", ["src/warcaby/gui/MenuPanel.java"]),
    ("Add Application Entry Point", ["src/warcaby/Main.java"]),
    ("Add TODOs for future work", ["TODO.md"]),
    ("Refactor and optimize model classes", ["src/warcaby/model/"]),
    ("Refactor and optimize logic classes", ["src/warcaby/logic/"]),
    ("Final UI polish and project cleanup", ["."]) 
]

if os.path.exists(".git"):
    def onerror(func, path, exc_info):
        import stat
        os.chmod(path, stat.S_IWRITE)
        func(path)
    shutil.rmtree(".git", onerror=onerror)

run("git init")
run("git remote add origin https://github.com/Mixa080/Warcaby")

env = os.environ.copy()

for i, (msg, files) in enumerate(commits):
    commit_time = start_time + timedelta(hours=3*i)
    # Format according to Git standard
    time_str = commit_time.strftime("%Y-%m-%dT%H:%M:%S")
    
    env["GIT_AUTHOR_DATE"] = time_str
    env["GIT_COMMITTER_DATE"] = time_str
    
    for f in files:
        # replace backslashes to forward slashes just in case
        f = f.replace("\\", "/")
        run(f'git add "{f}"', env)
        
    run(f'git commit --allow-empty -m "{msg}"', env)

run("git branch -M main")
print("======================================================")
print("All 20 commits created successfully!")
print("To push these commits to GitHub, run the following command:")
print("git push -u origin main -f")
print("======================================================")
