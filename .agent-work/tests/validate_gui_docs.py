#!/usr/bin/env python3
"""Validate docs/GUI.md and the rendered GUI assets.

Checks that every image referenced by the guide exists, is a valid PNG, that the
guide has no leftover Minecraft colour codes in prose, and that the eight main
screens are documented.
"""
import re
import struct
import sys
from pathlib import Path

HERE = Path(__file__).resolve().parent
DOCS = HERE.parent.parent / "docs"
GUIDE = DOCS / "GUI.md"

EXPECTED_SCREENS = {
    "shop-main", "shop-category", "buy-catalog", "buy-search",
    "buy-confirm", "sell-chest", "auction-browse", "auction-confirm",
}


def png_size(path: Path):
    data = path.read_bytes()
    if data[:8] != b"\x89PNG\r\n\x1a\n":
        raise ValueError("not a PNG")
    width, height = struct.unpack(">II", data[16:24])
    return width, height


def main() -> int:
    errors = []
    if not GUIDE.is_file():
        print("FAIL: docs/GUI.md is missing")
        return 1

    text = GUIDE.read_text(encoding="utf-8")
    if "\u00a7" in text:
        errors.append("docs/GUI.md still contains a Minecraft section-sign code")

    gui_images = sorted(set(re.findall(r'src="(assets/gui/[^"]+\.png)"', text)))
    icon_images = sorted(set(re.findall(r'src="(assets/icons/[^"]+\.png)"', text)))
    if len(gui_images) < len(EXPECTED_SCREENS):
        errors.append(f"only {len(gui_images)} GUI images are referenced")

    for relative in gui_images + icon_images:
        path = DOCS / relative
        if not path.is_file():
            errors.append(f"missing image: {relative}")
            continue
        try:
            width, height = png_size(path)
        except ValueError as exc:
            errors.append(f"{relative}: {exc}")
            continue
        if width <= 0 or height <= 0:
            errors.append(f"{relative}: empty image")
        if relative.startswith("assets/gui/"):
            expected = Path(relative).stem
            if expected in EXPECTED_SCREENS and width % 3:
                errors.append(f"{relative}: width {width} is not a 3x render")

    for screen in EXPECTED_SCREENS:
        if f"assets/gui/{screen}.png" not in text:
            errors.append(f"screen not documented: {screen}")

    if errors:
        print(f"FAIL: {len(errors)} GUI documentation problem(s)")
        for error in errors[:50]:
            print("  " + error)
        return 1
    print(f"OK: {len(gui_images)} GUI images and {len(icon_images)} icon references validated")
    return 0


if __name__ == "__main__":
    sys.exit(main())
