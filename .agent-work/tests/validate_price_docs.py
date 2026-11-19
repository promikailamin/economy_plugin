#!/usr/bin/env python3
"""Validate docs/PRICE.md against prices.tsv and the extracted icon files."""
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
PRICES = ROOT / "src/main/resources/prices.tsv"
DOC = ROOT / "docs/PRICE.md"
ICONS = ROOT / "docs/assets/icons"

rows = []
for line in PRICES.read_text(encoding="utf-8").splitlines():
    if not line.strip() or line.startswith("#"):
        continue
    parts = line.split("\t")
    if len(parts) >= 7:
        rows.append(parts[0].strip().removeprefix("minecraft:"))

doc = DOC.read_text(encoding="utf-8")
errors = []

for item_id in rows:
    icon = ICONS / f"{item_id}.png"
    if not icon.exists():
        errors.append(f"missing icon: {item_id}")
    elif icon.read_bytes()[:8] != b"\x89PNG\r\n\x1a\n":
        errors.append(f"invalid PNG: {item_id}")
    if f"`{item_id}`" not in doc:
        errors.append(f"missing table row: {item_id}")

if f"Items documented | **{len(rows):,}**" not in doc:
    errors.append(f"documented count does not match {len(rows)}")

if errors:
    print(f"FAIL: {len(errors)} problem(s)")
    print("\n".join(errors[:30]))
    sys.exit(1)

print(f"OK: {len(rows)} prices, {len(rows)} icons, PRICE.md is complete")
