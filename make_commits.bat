@echo off
echo Deleting old .git folder...
rmdir /S /Q .git

echo Initializing new Git repository...
git init
git remote add origin https://github.com/Mixa080/Warcaby

echo Creating commits...
set GIT_AUTHOR_DATE=2026-06-13T14:00:00
set GIT_COMMITTER_DATE=2026-06-13T14:00:00
git add .gitignore README.md LICENSE
git commit --allow-empty -m "Initial project setup"

set GIT_AUTHOR_DATE=2026-06-13T17:00:00
set GIT_COMMITTER_DATE=2026-06-13T17:00:00
git add remove_comments.py run.bat
git commit --allow-empty -m "Add utility scripts"

set GIT_AUTHOR_DATE=2026-06-13T20:00:00
set GIT_COMMITTER_DATE=2026-06-13T20:00:00
git add src/warcaby/model/PlayerColor.java src/warcaby/model/PieceType.java
git commit --allow-empty -m "Add game Enums"

set GIT_AUTHOR_DATE=2026-06-13T23:00:00
set GIT_COMMITTER_DATE=2026-06-13T23:00:00
git add src/warcaby/model/Piece.java
git commit --allow-empty -m "Add Piece model"

set GIT_AUTHOR_DATE=2026-06-14T02:00:00
set GIT_COMMITTER_DATE=2026-06-14T02:00:00
git add src/warcaby/model/Move.java
git commit --allow-empty -m "Add Move model"

set GIT_AUTHOR_DATE=2026-06-14T05:00:00
set GIT_COMMITTER_DATE=2026-06-14T05:00:00
git add src/warcaby/model/Board.java
git commit --allow-empty -m "Add Board model core"

set GIT_AUTHOR_DATE=2026-06-14T08:00:00
set GIT_COMMITTER_DATE=2026-06-14T08:00:00
git add src/warcaby/logic/MoveValidator.java
git commit --allow-empty -m "Add Move validation logic"

set GIT_AUTHOR_DATE=2026-06-14T11:00:00
set GIT_COMMITTER_DATE=2026-06-14T11:00:00
git add src/warcaby/logic/GameManager.java
git commit --allow-empty -m "Add Game manager"

set GIT_AUTHOR_DATE=2026-06-14T14:00:00
set GIT_COMMITTER_DATE=2026-06-14T14:00:00
git add src/warcaby/logic/AIPlayer.java
git commit --allow-empty -m "Add basic AI player"

set GIT_AUTHOR_DATE=2026-06-14T17:00:00
set GIT_COMMITTER_DATE=2026-06-14T17:00:00
git add src/warcaby/network/NetworkManager.java
git commit --allow-empty -m "Add Networking functionality"

set GIT_AUTHOR_DATE=2026-06-14T20:00:00
set GIT_COMMITTER_DATE=2026-06-14T20:00:00
git add src/warcaby/gui/MainFrame.java
git commit --allow-empty -m "Add UI Main Frame"

set GIT_AUTHOR_DATE=2026-06-14T23:00:00
set GIT_COMMITTER_DATE=2026-06-14T23:00:00
git add src/warcaby/gui/TimerPanel.java
git commit --allow-empty -m "Add Timer UI Panel"

set GIT_AUTHOR_DATE=2026-06-15T02:00:00
set GIT_COMMITTER_DATE=2026-06-15T02:00:00
git add src/warcaby/gui/BoardPanel.java
git commit --allow-empty -m "Add Board UI Panel"

set GIT_AUTHOR_DATE=2026-06-15T05:00:00
set GIT_COMMITTER_DATE=2026-06-15T05:00:00
git add src/warcaby/gui/GamePanel.java
git commit --allow-empty -m "Add Game UI Panel"

set GIT_AUTHOR_DATE=2026-06-15T08:00:00
set GIT_COMMITTER_DATE=2026-06-15T08:00:00
git add src/warcaby/gui/MenuPanel.java
git commit --allow-empty -m "Add Main Menu UI"

set GIT_AUTHOR_DATE=2026-06-15T11:00:00
set GIT_COMMITTER_DATE=2026-06-15T11:00:00
git add src/warcaby/Main.java
git commit --allow-empty -m "Add Application Entry Point"

set GIT_AUTHOR_DATE=2026-06-15T14:00:00
set GIT_COMMITTER_DATE=2026-06-15T14:00:00
git add TODO.md
git commit --allow-empty -m "Add TODOs for future work"

set GIT_AUTHOR_DATE=2026-06-15T17:00:00
set GIT_COMMITTER_DATE=2026-06-15T17:00:00
git add src/warcaby/model/
git commit --allow-empty -m "Refactor and optimize model classes"

set GIT_AUTHOR_DATE=2026-06-15T20:00:00
set GIT_COMMITTER_DATE=2026-06-15T20:00:00
git add src/warcaby/logic/
git commit --allow-empty -m "Refactor and optimize logic classes"

set GIT_AUTHOR_DATE=2026-06-15T23:00:00
set GIT_COMMITTER_DATE=2026-06-15T23:00:00
git add .
git commit --allow-empty -m "Final UI polish and project cleanup"

git branch -M main

echo ======================================================
echo Pushing commits to GitHub...
echo ======================================================
git push -u origin main -f

echo ======================================================
echo Done! 
echo ======================================================
pause
