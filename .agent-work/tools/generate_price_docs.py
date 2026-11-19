#!/usr/bin/env python3
"""Generate docs/PRICE.md and item icon assets from a Minecraft client jar.

Usage:
    python3 .agent-work/tools/generate_price_docs.py \
        --jar .agent-work/tmp/mc/client.jar \
        --version 26.3 \
        --prices src/main/resources/prices.tsv \
        --docs docs

The client jar is never written into the repository. Only the resolved 16x16
item icons needed by PRICE.md are copied into docs/assets/icons/.
"""
from __future__ import annotations

import argparse
import json
import os
import re
import shutil
import struct
import sys
import zipfile
import zlib
from collections import Counter
from pathlib import Path

CATEGORY_TITLES = {
    "armor": "Armor",
    "auction": "Auction Only",
    "blocks": "Blocks",
    "creative": "Creative Only",
    "end": "The End",
    "food": "Food & Farming",
    "misc": "Miscellaneous",
    "nether": "The Nether",
    "ores": "Ores & Metals",
    "rare": "Rare & Unique",
    "redstone": "Redstone",
    "tools": "Tools & Weapons",
}
CATEGORY_ORDER = [
    "blocks", "ores", "nether", "end", "food", "redstone", "tools",
    "armor", "misc", "rare", "auction", "creative",
]
# Preferred texture keys, most specific / most useful first.
KEY_PRIORITY = [
    "layer0", "layer1", "layer2", "layer3", "texture", "all", "side", "front",
    "top", "bottom", "end", "cross", "ore", "stem", "base", "plant", "rail",
    "wool", "sand", "inside", "content", "source", "still",
]


# --------------------------------------------------------------------------- PNG
# Tiny dependency-free PNG reader/writer used only for the handful of items whose
# icon is rendered from an entity texture (banners, heads, statues).

def png_read(data: bytes):
    if data[:8] != b"\x89PNG\r\n\x1a\n":
        raise ValueError("not a PNG")
    pos = 8
    width = height = bit_depth = color_type = interlace = None
    palette = b""
    alpha_table = b""
    idat = bytearray()
    while pos < len(data):
        length = struct.unpack(">I", data[pos:pos + 4])[0]
        kind = data[pos + 4:pos + 8]
        chunk = data[pos + 8:pos + 8 + length]
        pos += 12 + length
        if kind == b"IHDR":
            width, height, bit_depth, color_type, _, _, interlace = struct.unpack(">IIBBBBB", chunk)
        elif kind == b"PLTE":
            palette = chunk
        elif kind == b"tRNS":
            alpha_table = chunk
        elif kind == b"IDAT":
            idat += chunk
        elif kind == b"IEND":
            break
    if interlace:
        raise ValueError("interlaced PNGs are not supported")
    channels = {0: 1, 2: 3, 3: 1, 4: 2, 6: 4}[color_type]
    bits_per_pixel = channels * bit_depth
    bpp = max(1, (bits_per_pixel + 7) // 8)
    stride = (width * bits_per_pixel + 7) // 8
    raw = zlib.decompress(bytes(idat))
    rows = []
    previous = bytearray(stride)
    offset = 0
    for _ in range(height):
        filter_type = raw[offset]
        current = bytearray(raw[offset + 1:offset + 1 + stride])
        offset += 1 + stride
        for i in range(stride):
            left = current[i - bpp] if i >= bpp else 0
            up = previous[i]
            up_left = previous[i - bpp] if i >= bpp else 0
            if filter_type == 0:
                value = current[i]
            elif filter_type == 1:
                value = current[i] + left
            elif filter_type == 2:
                value = current[i] + up
            elif filter_type == 3:
                value = current[i] + ((left + up) // 2)
            elif filter_type == 4:
                p = left + up - up_left
                pa, pb, pc = abs(p - left), abs(p - up), abs(p - up_left)
                predictor = left if pa <= pb and pa <= pc else (up if pb <= pc else up_left)
                value = current[i] + predictor
            else:
                raise ValueError(f"unknown PNG filter {filter_type}")
            current[i] = value & 0xFF
        rows.append(current)
        previous = current

    def sample(row, index):
        if bit_depth == 8:
            return row[index]
        if bit_depth == 16:
            return row[index * 2]
        bit_offset = index * bit_depth
        byte = row[bit_offset // 8]
        shift = 8 - bit_depth - (bit_offset % 8)
        return (byte >> shift) & ((1 << bit_depth) - 1)

    def scale_sample(value):
        if bit_depth == 16:
            return value
        return value * 255 // ((1 << bit_depth) - 1)

    pixels = bytearray(width * height * 4)
    out = 0
    for row in rows:
        for x in range(width):
            if color_type == 0:
                gray = scale_sample(sample(row, x))
                r = g = b = gray
                a = 255
            elif color_type == 2:
                base = x * 3
                r = scale_sample(sample(row, base))
                g = scale_sample(sample(row, base + 1))
                b = scale_sample(sample(row, base + 2))
                a = 255
            elif color_type == 3:
                index = sample(row, x)
                r, g, b = palette[index * 3:index * 3 + 3]
                a = alpha_table[index] if index < len(alpha_table) else 255
            elif color_type == 4:
                base = x * 2
                r = g = b = scale_sample(sample(row, base))
                a = scale_sample(sample(row, base + 1))
            else:  # color_type == 6
                base = x * 4
                r = scale_sample(sample(row, base))
                g = scale_sample(sample(row, base + 1))
                b = scale_sample(sample(row, base + 2))
                a = scale_sample(sample(row, base + 3))
            pixels[out:out + 4] = bytes((r, g, b, a))
            out += 4
    return width, height, pixels


def png_write(path: Path, width: int, height: int, pixels: bytes):
    def chunk(kind: bytes, payload: bytes):
        return (struct.pack(">I", len(payload)) + kind + payload
                + struct.pack(">I", zlib.crc32(kind + payload) & 0xFFFFFFFF))

    raw = b"".join(b"\x00" + bytes(pixels[y * width * 4:(y + 1) * width * 4])
                   for y in range(height))
    data = (b"\x89PNG\r\n\x1a\n"
            + chunk(b"IHDR", struct.pack(">IIBBBBB", width, height, 8, 6, 0, 0, 0))
            + chunk(b"IDAT", zlib.compress(raw, 9))
            + chunk(b"IEND", b""))
    path.write_bytes(data)


def png_bbox(width, height, pixels):
    min_x, min_y, max_x, max_y = width, height, -1, -1
    for y in range(height):
        for x in range(width):
            if pixels[(y * width + x) * 4 + 3]:
                min_x, min_y = min(min_x, x), min(min_y, y)
                max_x, max_y = max(max_x, x), max(max_y, y)
    if max_x < 0:
        return 0, 0, width, height
    return min_x, min_y, max_x - min_x + 1, max_y - min_y + 1


def png_crop(width, height, pixels, x, y, crop_w, crop_h):
    out = bytearray(crop_w * crop_h * 4)
    for row in range(crop_h):
        src = ((y + row) * width + x) * 4
        out[row * crop_w * 4:(row + 1) * crop_w * 4] = pixels[src:src + crop_w * 4]
    return crop_w, crop_h, out


def png_resize(width, height, pixels, new_size: int):
    out = bytearray(new_size * new_size * 4)
    for y in range(new_size):
        sy = min(height - 1, y * height // new_size)
        for x in range(new_size):
            sx = min(width - 1, x * width // new_size)
            src = (sy * width + sx) * 4
            dst = (y * new_size + x) * 4
            out[dst:dst + 4] = pixels[src:src + 4]
    return new_size, new_size, out


def png_scale_to(width, height, pixels, new_w: int, new_h: int):
    out = bytearray(new_w * new_h * 4)
    for y in range(new_h):
        sy = min(height - 1, y * height // new_h)
        for x in range(new_w):
            sx = min(width - 1, x * width // new_w)
            src = (sy * width + sx) * 4
            dst = (y * new_w + x) * 4
            out[dst:dst + 4] = pixels[src:src + 4]
    return new_w, new_h, out


def png_blit(dst_w, dst_h, dst, src_w, src_h, src, x, y):
    for row in range(src_h):
        ty = y + row
        if ty < 0 or ty >= dst_h:
            continue
        for col in range(src_w):
            tx = x + col
            if tx < 0 or tx >= dst_w:
                continue
            si = (row * src_w + col) * 4
            if not src[si + 3]:
                continue
            di = (ty * dst_w + tx) * 4
            dst[di:di + 4] = src[si:si + 4]


def png_chest_icon(width, height, pixels):
    """Compose the chest's lid/front texture patches into a readable flat icon."""
    if width < 64 or height < 64:
        return png_resize(width, height, pixels, 16)
    lid = png_crop(width, height, pixels, 14, 14, 14, 5)
    body = png_crop(width, height, pixels, 14, 33, 14, 10)
    lid = png_scale_to(lid[0], lid[1], lid[2], 14, 4)
    body = png_scale_to(body[0], body[1], body[2], 14, 9)
    canvas = bytearray(16 * 16 * 4)
    for i in range(0, len(canvas), 4):
        canvas[i:i + 4] = bytes((43, 30, 15, 255))
    png_blit(16, 16, canvas, *lid, 1, 1)
    png_blit(16, 16, canvas, *body, 1, 6)
    for x in range(1, 15):
        idx = (5 * 16 + x) * 4
        canvas[idx:idx + 4] = bytes((29, 20, 10, 255))
    for y in range(5, 8):
        for x in (7, 8):
            idx = (y * 16 + x) * 4
            canvas[idx:idx + 4] = bytes((196, 160, 76, 255))
    return 16, 16, canvas


def png_shield_icon(width, height, pixels):
    """Turn a wood texture into a shield-shaped icon using a simple silhouette."""
    width, height, pixels = png_resize(width, height, pixels, 16)
    edges = {0: (2, 13), 1: (1, 14), 2: (1, 14), 3: (1, 14), 4: (1, 14),
             5: (1, 14), 6: (1, 14), 7: (1, 14), 8: (1, 14), 9: (1, 14),
             10: (1, 14), 11: (2, 13), 12: (3, 12), 13: (4, 11), 14: (6, 9),
             15: (7, 8)}
    out = bytearray(pixels)
    for y in range(16):
        lo, hi = edges[y]
        for x in range(16):
            idx = (y * 16 + x) * 4
            if x < lo or x > hi:
                out[idx + 3] = 0
            elif x in (lo, hi) or y == 0 or y == 15:
                out[idx] = out[idx] * 3 // 5
                out[idx + 1] = out[idx + 1] * 3 // 5
                out[idx + 2] = out[idx + 2] * 3 // 5
    return 16, 16, out


def png_tint_cloth(width, height, pixels, color: str):
    r, g, b = int(color[0:2], 16), int(color[2:4], 16), int(color[4:6], 16)
    out = bytearray(pixels)
    for i in range(0, len(out), 4):
        pr, pg, pb, pa = out[i:i + 4]
        if not pa:
            continue
        # The banner base mixes a neutral cloth with a coloured wooden pole.
        # Tint only the near-greyscale cloth pixels so the pole stays brown.
        if max(pr, pg, pb) - min(pr, pg, pb) <= 20:
            out[i] = pr * r // 255
            out[i + 1] = pg * g // 255
            out[i + 2] = pb * b // 255
    return width, height, out


def load_json(zf: zipfile.ZipFile, path: str):
    try:
        return json.loads(zf.read(path))
    except (KeyError, json.JSONDecodeError):
        return None


def merge_model(zf: zipfile.ZipFile, path: str, seen=None):
    """Return (textures, elements) after resolving parent models."""
    if seen is None:
        seen = set()
    if path in seen:
        return {}, []
    seen.add(path)
    data = load_json(zf, f"assets/minecraft/models/{path}.json")
    if not isinstance(data, dict):
        return {}, []
    textures, elements = {}, []
    parent = data.get("parent")
    if isinstance(parent, str) and not parent.startswith("builtin/"):
        parent = parent.removeprefix("minecraft:")
        textures, elements = merge_model(zf, parent, seen)
    for key, value in (data.get("textures") or {}).items():
        textures[key] = value
    if data.get("elements"):
        elements = data["elements"]
    return textures, elements


def texture_value(value):
    if isinstance(value, str):
        return value
    if isinstance(value, dict):
        for key in ("sprite", "texture", "all", "id"):
            if key in value:
                return texture_value(value[key])
    return None


def resolve_texture(textures: dict, key: str, seen=None):
    """Follow #references in a model texture map to an actual asset path."""
    if seen is None:
        seen = set()
    if key in seen:
        return None
    seen.add(key)
    value = texture_value(textures.get(key))
    if not value:
        return None
    value = value.removeprefix("minecraft:")
    if value.startswith("#"):
        return resolve_texture(textures, value[1:], seen)
    return value


def face_usage(elements):
    counts = Counter()
    for element in elements or []:
        for face in (element.get("faces") or {}).values():
            ref = face.get("texture") if isinstance(face, dict) else None
            if isinstance(ref, str) and ref.startswith("#"):
                counts[ref[1:]] += 1
    return counts


def choose_texture(textures: dict, elements) -> str | None:
    # Prefer the texture used by the most model faces (usually the visible side).
    usage = face_usage(elements)
    for key, _ in usage.most_common():
        tex = resolve_texture(textures, key)
        if tex:
            return tex
    # Flat generated item models use layer0; fall back to a stable priority list.
    for key in KEY_PRIORITY:
        tex = resolve_texture(textures, key)
        if tex:
            return tex
    for key in textures:
        if key == "particle":
            continue
        tex = resolve_texture(textures, key)
        if tex:
            return tex
    return None


BANNER_COLORS = {
    "white": "F9FFFE", "orange": "F9801D", "magenta": "C74EBD",
    "light_blue": "3AB3DA", "yellow": "FED83D", "lime": "80C71F",
    "pink": "F38BAA", "gray": "474F52", "light_gray": "9D9D97",
    "cyan": "169C9C", "purple": "8932B8", "blue": "3C44AA",
    "brown": "835432", "green": "5E7C16", "red": "B02E26",
    "black": "1D1D21",
}
HEAD_TEXTURES = {
    "creeper": "entity/creeper/creeper",
    "skeleton": "entity/skeleton/skeleton",
    "wither_skeleton": "entity/skeleton/wither_skeleton",
    "zombie": "entity/zombie/zombie",
    "piglin": "entity/piglin/piglin",
    "player": "entity/player/wide/steve",
}


def item_definition(zf: zipfile.ZipFile, item_id: str):
    """Return (model refs, direct texture assets, special metadata)."""
    data = load_json(zf, f"assets/minecraft/items/{item_id}.json")
    refs, assets, meta = [], [], {"kind": None, "color": None, "texture": None}

    def walk(node):
        if isinstance(node, dict):
            for key, value in node.items():
                if key in ("model", "base") and isinstance(value, str) and not value.startswith("#"):
                    ref = value.removeprefix("minecraft:")
                    if "/" in ref:
                        refs.append(ref)
                elif key == "texture" and isinstance(value, str) and "textures/" in value:
                    assets.append(value.removeprefix("minecraft:").split("textures/", 1)[1])
                elif key == "kind" and isinstance(value, str):
                    meta["kind"] = value
                elif key == "color" and isinstance(value, str):
                    meta["color"] = value
                elif key == "texture" and isinstance(value, str):
                    meta["texture"] = value.removeprefix("minecraft:")
                else:
                    walk(value)
        elif isinstance(node, list):
            for value in node:
                walk(value)

    walk(data)
    return list(dict.fromkeys(refs)), list(dict.fromkeys(assets)), meta


def try_model(zf: zipfile.ZipFile, model_path: str):
    textures, elements = merge_model(zf, model_path)
    if not textures:
        return None
    tex = choose_texture(textures, elements)
    if not tex:
        return None
    asset = f"assets/minecraft/textures/{tex}.png"
    try:
        zf.getinfo(asset)
        return asset
    except KeyError:
        return None


def icon_for(zf: zipfile.ZipFile, item_id: str):
    """Find the best icon asset for an item id: (asset, transform, source)."""
    refs, direct_assets, meta = item_definition(zf, item_id)
    for asset in direct_assets:
        full = f"assets/minecraft/textures/{asset}"
        try:
            zf.getinfo(full)
            return full, "raw", f"item-definition:{asset}"
        except KeyError:
            pass
    for model_path in dict.fromkeys(refs + [f"item/{item_id}", f"block/{item_id}"]):
        asset = try_model(zf, model_path)
        if asset:
            return asset, None, model_path
    # Dynamically rendered banners use a neutral cloth texture plus a dye colour.
    color = meta.get("color")
    if color in BANNER_COLORS:
        return ("assets/minecraft/textures/entity/banner/banner_base.png",
                ("banner", BANNER_COLORS[color]), "special:banner")
    # Skulls use the mob skin's front face; build a flat 16x16 icon from it.
    kind = meta.get("kind")
    if kind in HEAD_TEXTURES:
        return (f"assets/minecraft/textures/{HEAD_TEXTURES[kind]}.png",
                ("head", None), f"special:head/{kind}")
    if item_id == "player_head":
        return ("assets/minecraft/textures/entity/player/wide/steve.png",
                ("head", None), "special:head/player")
    if item_id == "dragon_head":
        return ("assets/minecraft/textures/entity/enderdragon/dragon.png",
                ("bbox", None), "special:head/dragon")
    # Chests and pots are rendered from entity textures rather than block models.
    chest_texture = meta.get("texture")
    if chest_texture in ("normal", "trapped", "ender", "christmas",
                         "copper", "copper_exposed", "copper_oxidized", "copper_weathered"):
        if chest_texture == "christmas":
            chest_texture = "normal" if item_id == "chest" else "trapped"
        return (f"assets/minecraft/textures/entity/chest/{chest_texture}.png",
                ("chest", None), "special:chest")
    if item_id == "decorated_pot":
        return ("assets/minecraft/textures/entity/decorated_pot/decorated_pot_base.png",
                ("bbox", None), "special:decorated_pot")
    if any(ref.startswith("item/shield") for ref in refs):
        return ("assets/minecraft/textures/block/dark_oak_planks.png",
                ("shield", None), "special:shield")
    # Direct texture fallbacks (covers models that only exist in item definitions).
    for directory in ("item", "block"):
        asset = f"assets/minecraft/textures/{directory}/{item_id}.png"
        try:
            zf.getinfo(asset)
            return asset, None, f"{directory}/{item_id}.png"
        except KeyError:
            pass
    return None, None, "missing"


def read_prices(path: Path):
    rows = []
    with path.open(encoding="utf-8") as handle:
        for line in handle:
            if not line.strip() or line.startswith("#"):
                continue
            parts = line.rstrip("\n").split("\t")
            if len(parts) < 7:
                continue
            try:
                buy, sell = int(parts[2].strip()), int(parts[3].strip())
            except ValueError:
                continue
            rows.append({
                "id": parts[0].strip().removeprefix("minecraft:"),
                "name": parts[1].strip(),
                "buy": buy,
                "sell": sell,
                "obtainable": parts[4].strip().lower() == "yes",
                "buyable": parts[5].strip().lower() == "yes" and buy > 0,
                "category": parts[6].strip().lower() or "misc",
                "note": parts[7].strip() if len(parts) > 7 else "",
            })
    return rows


def fmt(number: int) -> str:
    return f"{number:,}"


def escape(text: str) -> str:
    return text.replace("|", "\\|")


def prettify(category: str) -> str:
    return CATEGORY_TITLES.get(category, category.replace("_", " ").title())


def anchor(category: str) -> str:
    return re.sub(r"[^a-z0-9]+", "-", prettify(category).lower()).strip("-")


def flag(row):
    sell = row["sell"] > 0
    if row["buyable"] and sell:
        return "Buy / Sell"
    if row["buyable"]:
        return "Buy only"
    if sell:
        return "Sell only"
    return "Not traded"


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--jar", required=True, type=Path)
    parser.add_argument("--version", default="unknown")
    parser.add_argument("--prices", default="src/main/resources/prices.tsv", type=Path)
    parser.add_argument("--docs", default="docs", type=Path)
    args = parser.parse_args()

    rows = read_prices(args.prices)
    icons_dir = args.docs / "assets" / "icons"
    icons_dir.mkdir(parents=True, exist_ok=True)

    missing = []
    resolved = []
    with zipfile.ZipFile(args.jar) as zf:
        for row in rows:
            asset, transform, source = icon_for(zf, row["id"])
            if asset:
                icon_name = f"{row['id']}.png"
                icon_path = icons_dir / icon_name
                if transform is None:
                    icon_path.write_bytes(zf.read(asset))
                else:
                    width, height, pixels = png_read(zf.read(asset))
                    if transform[0] == "raw":
                        pass
                    elif transform[0] == "banner":
                        width, height, pixels = png_crop(width, height, pixels,
                                                         *png_bbox(width, height, pixels))
                        width, height, pixels = png_tint_cloth(width, height, pixels, transform[1])
                        width, height, pixels = png_resize(width, height, pixels, 16)
                    elif transform[0] == "head":
                        if width >= 16 and height >= 16:
                            width, height, pixels = png_crop(width, height, pixels, 8, 8, 8, 8)
                        width, height, pixels = png_resize(width, height, pixels, 16)
                    elif transform[0] == "bbox":
                        width, height, pixels = png_crop(width, height, pixels,
                                                         *png_bbox(width, height, pixels))
                        width, height, pixels = png_resize(width, height, pixels, 16)
                    elif transform[0] == "tint":
                        width, height, pixels = png_crop(width, height, pixels,
                                                         *png_bbox(width, height, pixels))
                        width, height, pixels = png_tint_cloth(width, height, pixels, transform[1])
                        width, height, pixels = png_resize(width, height, pixels, 16)
                    elif transform[0] == "chest":
                        width, height, pixels = png_chest_icon(width, height, pixels)
                    elif transform[0] == "shield":
                        width, height, pixels = png_shield_icon(width, height, pixels)
                    png_write(icon_path, width, height, pixels)
                row["icon"] = f"assets/icons/{icon_name}"
                resolved.append((row["id"], asset, source))
            else:
                missing.append(row["id"])
                row["icon"] = ""

    with (args.docs / "assets" / "icons" / "_manifest.tsv").open("w", encoding="utf-8") as out:
        out.write("item\ttexture\tmodel\n")
        for item_id, asset, source in resolved:
            out.write(f"{item_id}\t{asset}\t{source}\n")
        for item_id in missing:
            out.write(f"{item_id}\tMISSING\tmissing\n")

    grouped = {c: [] for c in CATEGORY_ORDER}
    for row in rows:
        grouped.setdefault(row["category"], []).append(row)
    for category, items in grouped.items():
        items.sort(key=lambda r: r["name"].lower())

    total = len(rows)
    buyable = sum(1 for r in rows if r["buyable"])
    sellable = sum(1 for r in rows if r["sell"] > 0)
    with_icons = total - len(missing)
    traded = [r for r in rows if r["obtainable"] and r["category"] != "creative"]
    cheapest = min((r for r in traded if r["sell"] > 0), key=lambda r: r["sell"], default=None)
    priciest = max((r for r in traded if r["sell"] > 0), key=lambda r: r["sell"], default=None)

    lines = []
    add = lines.append
    add("# Economy Shop Price Guide")
    add("")
    add(f"![Minecraft {escape(str(args.version))}](https://img.shields.io/badge/Minecraft-{escape(str(args.version))}-3C8527?style=for-the-badge)")
    add("![Items](https://img.shields.io/badge/items-"
        f"{total}-2D6A4F?style=for-the-badge)")
    add("![Icons](https://img.shields.io/badge/icons-"
        f"{with_icons}-1B4332?style=for-the-badge)")
    add("")
    add("> Complete shop price list for the **Economy** Paper plugin, with the actual "
        "Minecraft item textures extracted from the client jar.")
    add("")
    add("Every price is read directly from [`src/main/resources/prices.tsv`](../src/main/resources/prices.tsv). "
        "Amounts are in server currency and are **per single item**. The icon beside each entry is the "
        "real texture shipped in the Minecraft client, so the list is easy to scan in-game and on GitHub.")
    add("")
    add("---")
    add("")
    add("## At a Glance")
    add("")
    add("| Metric | Value |")
    add("|:--|--:|")
    add(f"| Items documented | **{fmt(total)}** |")
    add(f"| Buyable from `/shop` | **{fmt(buyable)}** |")
    add(f"| Sellable to the server | **{fmt(sellable)}** |")
    add(f"| Item icons extracted | **{fmt(with_icons)} / {fmt(total)}** |")
    add(f"| Categories | **{len([c for c in grouped if grouped[c]])}** |")
    add(f"| Minecraft assets version | **{escape(str(args.version))}** |")
    if cheapest:
        add(f"| Cheapest sellable item | **{escape(cheapest['name'])}** ({fmt(cheapest['sell'])}) |")
    if priciest:
        add(f"| Most valuable sellable item | **{escape(priciest['name'])}** ({fmt(priciest['sell'])}) |")
    add("")
    add("## How to Read the Tables")
    add("")
    add("| Column | Meaning |")
    add("|:--|:--|")
    add("| **Icon** | Texture extracted from `client.jar` (`assets/minecraft/textures/...`). |")
    add("| **Item** | Display name used by the shop. |")
    add("| **ID** | Minecraft item/material id, useful for `/buy <id>`, `/sell <id>` and `prices.tsv`. |")
    add("| **Buy** | Price the server charges per item. `-` means it cannot be bought. |")
    add("| **Sell** | Price the server pays per item. `-` means it cannot be sold. |")
    add("| **Trade** | Whether the item is buyable, sellable, both, or not traded. |")
    add("| **Notes** | Special handling such as auction-only or creative-only items. |")
    add("")
    add("### Trade Rules")
    add("")
    add("- The shop always pays the **sell** price and charges the **buy** price shown below.")
    add("- Creative-only items exist in the file for completeness but are ignored by the plugin economy.")
    add("- Auction-only and rare items cannot be bought from `/shop`; obtain them from the auction house or gameplay.")
    add("- `-` in the Buy column means the item is not purchasable; `-` in the Sell column means it is not sellable.")
    add("")
    add("## Contents")
    add("")
    for category in CATEGORY_ORDER + [c for c in grouped if c not in CATEGORY_ORDER]:
        items = grouped.get(category) or []
        if not items:
            continue
        add(f"- [{prettify(category)}](#{anchor(category)}) - {len(items)} items")
    add("")
    add("---")
    add("")
    for category in CATEGORY_ORDER + [c for c in grouped if c not in CATEGORY_ORDER]:
        items = grouped.get(category) or []
        if not items:
            continue
        add(f"## {prettify(category)}")
        add("")
        buy_count = sum(1 for r in items if r["buyable"])
        sell_count = sum(1 for r in items if r["sell"] > 0)
        add(f"_{len(items)} items, {buy_count} buyable, {sell_count} sellable._")
        add("")
        add("| Icon | Item | ID | Buy | Sell | Trade | Notes |")
        add("|:---:|:--|:--|--:|--:|:--:|:--|")
        for row in items:
            if row["icon"]:
                icon = (f'<img src="{row["icon"]}" width="32" height="32" '
                        f'alt="{escape(row["name"])}" title="{escape(row["name"])}">')
            else:
                icon = "n/a"
            buy = fmt(row["buy"]) if row["buyable"] else "-"
            sell = fmt(row["sell"]) if row["sell"] > 0 else "-"
            add(f"| {icon} | **{escape(row['name'])}** | `{escape(row['id'])}` | "
                f"{buy} | {sell} | {flag(row)} | {escape(row['note']) or '-'} |")
        add("")
    add("---")
    add("")
    add("## Regenerating This Document")
    add("")
    add("The icons and tables are generated from the actual Minecraft client jar. "
        "Nothing from the jar except the small 16x16 icons is committed.")
    add("")
    add("```bash")
    add("# Download the client jar for a specific version")
    add(".agent-work/tools/fetch_client_jar.sh 26.3")
    add("")
    add("# Rebuild the document and icons")
    add("python3 .agent-work/tools/generate_price_docs.py \\")
    add("  --jar .agent-work/tmp/mc/client.jar \\")
    add("  --version 26.3 \\")
    add("  --prices src/main/resources/prices.tsv \\")
    add("  --docs docs")
    add("```")
    add("")
    add("_Generated from `prices.tsv` and the Minecraft client assets. "
        "Do not edit the tables by hand; change the TSV or the generator instead._")
    add("")

    args.docs.mkdir(parents=True, exist_ok=True)
    (args.docs / "PRICE.md").write_text("\n".join(lines), encoding="utf-8")

    print(f"PRICE.md: {total} items, {with_icons} icons, {len(missing)} missing icons")
    if missing:
        print("Missing icon ids: " + ", ".join(missing[:50]))
        if len(missing) > 50:
            print(f"... and {len(missing) - 50} more (see docs/assets/icons/_manifest.tsv)")
    return 0


if __name__ == "__main__":
    sys.exit(main())
