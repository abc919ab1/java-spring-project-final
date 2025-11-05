#!/usr/bin/env python3
"""
merge_java.py  –  merge all *.java files found below this script's folder
into one text file with clear filename headers.
"""
import os
from pathlib import Path

ROOT      = Path(__file__).resolve().parent   # folder that holds this script
OUT_FILE  = ROOT / "all_java.txt"
JAVA_EXT  = "*.java"

def main():
    java_files = sorted(ROOT.rglob(JAVA_EXT))  # recursive glob
    if not java_files:
        print("No .java files found below", ROOT)
        return

    with OUT_FILE.open("w", encoding="utf-8") as out:
        for j in java_files:
            relative = j.relative_to(ROOT)     # nice path for the header
            out.write(f"\n===== {relative} =====\n")
            try:
                out.write(j.read_text(encoding="utf-8"))
            except Exception as e:
                out.write(f"\n<!—- ERROR reading {j}: {e} —->\n")
    print(f"Merged {len(java_files)} files into {OUT_FILE}")

if __name__ == "__main__":
    main()