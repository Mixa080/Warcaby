import os
import re

def remove_comments_from_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    new_lines = []
    for line in lines:
        if re.match(r'^\s*//', line):
            continue
        line = re.sub(r'\s*//.*', '', line)
        new_lines.append(line)
        
    # Remove trailing newlines cleanly if it was just trailing empty lines
    content = "".join(new_lines)
    # Ensure it ends with a single newline if not empty
    if content and not content.endswith('\n'):
        content += '\n'
        
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

src_dir = 'src'
for root, dirs, files in os.walk(src_dir):
    for file in files:
        if file.endswith('.java'):
            filepath = os.path.join(root, file)
            remove_comments_from_file(filepath)
            print(f'Processed {filepath}')
