#!/usr/bin/env python3
"""Render the Economy plugin GUIs and write docs/GUI.md.

The backgrounds come from the real ``assets/minecraft/textures/gui`` files in a
Minecraft client jar, the item icons come from the real item textures, and the
text is drawn with the real ``ascii.png`` font.  No screenshots, resource packs
or third-party images are used.

Usage:
    .agent-work/tools/fetch_client_jar.sh 26.3
    python3 .agent-work/tools/generate_gui_docs.py \
        --jar .agent-work/tmp/mc/client.jar \
        --version 26.3 \
        --prices src/main/resources/prices.tsv \
        --docs docs
"""
from __future__ import annotations

import argparse
import re
import sys
import zipfile
from pathlib import Path

HERE = Path(__file__).resolve().parent
sys.path.insert(0, str(HERE))
import generate_price_docs as mcdoc  # noqa: E402

GENERIC_54 = "assets/minecraft/textures/gui/container/generic_54.png"
ASCII_FONT = "assets/minecraft/textures/font/ascii.png"

MC_COLORS = {
    "0": (0, 0, 0), "1": (0, 0, 170), "2": (0, 170, 0), "3": (0, 170, 170),
    "4": (170, 0, 0), "5": (170, 0, 170), "6": (255, 170, 0), "7": (170, 170, 170),
    "8": (85, 85, 85), "9": (85, 85, 255), "a": (85, 255, 85), "b": (85, 255, 255),
    "c": (255, 85, 85), "d": (255, 85, 255), "e": (255, 255, 85), "f": (255, 255, 255),
}
DEFAULT_COLOR = MC_COLORS["f"]


# --------------------------------------------------------------------- images

def new_image(width: int, height: int, color=(0, 0, 0, 0)):
    return bytearray(bytes(color) * (width * height))


def crop(width, height, pixels, x, y, crop_w, crop_h):
    out = bytearray(crop_w * crop_h * 4)
    for row in range(crop_h):
        src = ((y + row) * width + x) * 4
        out[row * crop_w * 4:(row + 1) * crop_w * 4] = pixels[src:src + crop_w * 4]
    return crop_w, crop_h, out


def blit(dst, dst_w, dst_h, src, src_w, src_h, x, y):
    for row in range(src_h):
        ty = y + row
        if ty < 0 or ty >= dst_h:
            continue
        for col in range(src_w):
            tx = x + col
            if tx < 0 or tx >= dst_w:
                continue
            si = (row * src_w + col) * 4
            sa = src[si + 3]
            if not sa:
                continue
            di = (ty * dst_w + tx) * 4
            if sa == 255:
                dst[di:di + 4] = src[si:si + 4]
                continue
            da = dst[di + 3]
            out_a = sa + da * (255 - sa) // 255
            if out_a:
                for channel in range(3):
                    dst[di + channel] = (src[si + channel] * sa
                                         + dst[di + channel] * da * (255 - sa) // 255) // out_a
            dst[di + 3] = out_a


def scale_nearest(width, height, pixels, factor: int):
    out = bytearray(width * factor * height * factor * 4)
    for y in range(height * factor):
        sy = y // factor
        for x in range(width * factor):
            sx = x // factor
            src = (sy * width + sx) * 4
            dst = (y * width * factor + x) * 4
            out[dst:dst + 4] = pixels[src:src + 4]
    return width * factor, height * factor, out


# ---------------------------------------------------------------------- font

class MinecraftFont:
    def __init__(self, jar: zipfile.ZipFile):
        self.width, self.height, self.pixels = mcdoc.png_read(jar.read(ASCII_FONT))
        self.glyphs = {}
        for code in range(32, 127):
            self.glyphs[chr(code)] = self._read_glyph(code)
        self.glyphs["?"] = self._read_glyph(ord("?"))

    def _read_glyph(self, code: int):
        col = (code % 16) * 8
        row = (code // 16) * 8
        alpha = [[self.pixels[((row + y) * self.width + col + x) * 4 + 3]
                  for x in range(8)] for y in range(8)]
        used = [x for x in range(8) if any(alpha[y][x] for y in range(8))]
        advance = (max(used) + 2) if used else 4
        return alpha, advance

    @staticmethod
    def parse(text: str):
        runs = []
        color = DEFAULT_COLOR
        bold = False
        i = 0
        while i < len(text):
            ch = text[i]
            if ch == "\u00a7" and i + 1 < len(text):
                code = text[i + 1].lower()
                if code in MC_COLORS:
                    color = MC_COLORS[code]
                    bold = False
                elif code == "l":
                    bold = True
                elif code == "r":
                    color = DEFAULT_COLOR
                    bold = False
                i += 2
                continue
            runs.append((ch, color, bold))
            i += 1
        return runs

    def width_of(self, text: str) -> int:
        total = 0
        for ch, _, bold in self.parse(text):
            _, advance = self.glyphs.get(ch, self.glyphs["?"])
            total += advance + (1 if bold else 0)
        return total

    def _draw_glyph(self, target, target_w, target_h, ch, x, y, color, bold=False):
        alpha, _ = self.glyphs.get(ch, self.glyphs["?"])
        for row in range(8):
            for col in range(8):
                a = alpha[row][col]
                if not a:
                    continue
                tx, ty = x + col, y + row
                if 0 <= tx < target_w and 0 <= ty < target_h:
                    di = (ty * target_w + tx) * 4
                    if a == 255:
                        target[di:di + 4] = bytes((*color, 255))
                    else:
                        da = target[di + 3]
                        out_a = a + da * (255 - a) // 255
                        if out_a:
                            for channel in range(3):
                                target[di + channel] = (
                                    color[channel] * a
                                    + target[di + channel] * da * (255 - a) // 255
                                ) // out_a
                            target[di + 3] = out_a
                if bold:
                    tx += 1
                    if 0 <= tx < target_w and 0 <= ty < target_h:
                        di = (ty * target_w + tx) * 4
                        target[di:di + 4] = bytes((*color, 255))

    def draw(self, target, target_w, target_h, text, x, y):
        # Minecraft draws every glyph's shadow first, offset one pixel down/right.
        cursor = x
        for ch, color, bold in self.parse(text):
            shadow = tuple(channel // 4 for channel in color)
            self._draw_glyph(target, target_w, target_h, ch, cursor + 1, y + 1, shadow, bold)
            cursor += self.glyphs.get(ch, self.glyphs["?"])[1] + (1 if bold else 0)
        cursor = x
        for ch, color, bold in self.parse(text):
            self._draw_glyph(target, target_w, target_h, ch, cursor, y, color, bold)
            cursor += self.glyphs.get(ch, self.glyphs["?"])[1] + (1 if bold else 0)

    def draw_right(self, target, target_w, target_h, text, right_x, y):
        self.draw(target, target_w, target_h, text, right_x - self.width_of(text), y)

    def draw_centered(self, target, target_w, target_h, text, y):
        self.draw(target, target_w, target_h, text, max(0, (target_w - self.width_of(text)) // 2), y)


# --------------------------------------------------------------------- icons

class Icons:
    def __init__(self, jar: zipfile.ZipFile, docs: Path):
        self.jar = jar
        self.docs = docs
        self.cache = {}

    def get(self, item_id: str):
        item_id = item_id.removeprefix("minecraft:").lower()
        if item_id in self.cache:
            return self.cache[item_id]
        cache_file = self.docs / "assets" / "icons" / f"{item_id}.png"
        if cache_file.is_file():
            rendered = mcdoc.png_read(cache_file.read_bytes())
        else:
            rendered = self._extract(item_id)
        self.cache[item_id] = rendered
        return rendered

    def _extract(self, item_id: str):
        asset, transform, _ = mcdoc.icon_for(self.jar, item_id)
        if not asset:
            return new_image(16, 16)
        width, height, pixels = mcdoc.png_read(self.jar.read(asset))
        if transform is None:
            return width, height, pixels
        kind = transform[0]
        if kind == "banner":
            width, height, pixels = mcdoc.png_crop(width, height, pixels,
                                                   *mcdoc.png_bbox(width, height, pixels))
            width, height, pixels = mcdoc.png_tint_cloth(width, height, pixels, transform[1])
            width, height, pixels = mcdoc.png_resize(width, height, pixels, 16)
        elif kind == "head":
            if width >= 16 and height >= 16:
                width, height, pixels = mcdoc.png_crop(width, height, pixels, 8, 8, 8, 8)
            width, height, pixels = mcdoc.png_resize(width, height, pixels, 16)
        elif kind == "bbox":
            width, height, pixels = mcdoc.png_crop(width, height, pixels,
                                                   *mcdoc.png_bbox(width, height, pixels))
            width, height, pixels = mcdoc.png_resize(width, height, pixels, 16)
        elif kind == "tint":
            width, height, pixels = mcdoc.png_crop(width, height, pixels,
                                                   *mcdoc.png_bbox(width, height, pixels))
            width, height, pixels = mcdoc.png_tint_cloth(width, height, pixels, transform[1])
            width, height, pixels = mcdoc.png_resize(width, height, pixels, 16)
        elif kind == "chest":
            width, height, pixels = mcdoc.png_chest_icon(width, height, pixels)
        elif kind == "shield":
            width, height, pixels = mcdoc.png_shield_icon(width, height, pixels)
        return width, height, pixels


# ------------------------------------------------------------------ money/text

def format_money(amount: int) -> str:
    suffixes = ("K", "M", "B", "T", "Q")
    if amount < 0:
        return "-" + format_money(-amount)
    if amount < 1000:
        return "$" + str(amount)
    value = float(amount)
    index = -1
    while value >= 1000 and index < len(suffixes) - 1:
        value /= 1000.0
        index += 1
    text = f"{value:.1f}"
    if text == "1000.0" and index < len(suffixes) - 1:
        text = "1"
        index += 1
    elif text.endswith(".0"):
        text = text[:-2]
    return "$" + text + suffixes[index]


def exact_money(amount: int) -> str:
    return ("-$" if amount < 0 else "$") + f"{abs(amount):,}"


def strip_codes(text: str) -> str:
    return re.sub(r"\u00a7.", "", text)


def escape_md(text: str) -> str:
    return strip_codes(text).replace("|", "\\|")


def lore_html(lines):
    return "<br>".join(escape_md(line) for line in lines)


# ------------------------------------------------------------------- helpers

def shop_entry_map(rows):
    return {row["id"]: row for row in rows}


def shop_item(entry, with_actions=True):
    lore = ["\u00a77Category: \u00a7e" + entry["category"]]
    if entry["buyable"]:
        lore.append("\u00a7aBuy price: \u00a76" + format_money(entry["buy"]))
    else:
        lore.append("\u00a7cNot sold by the shop")
    if entry["sell"] > 0:
        lore.append("\u00a7eSell price: \u00a76" + format_money(entry["sell"]))
    if entry["note"]:
        lore.append("\u00a78" + entry["note"])
    if with_actions and entry["buyable"]:
        lore += ["\u00a78-------------------------",
                 "\u00a7a\u25b6 Left-Click: \u00a7fBuy 1x",
                 "\u00a7d\u25b6 Right-Click: \u00a7fSelect quantity"]
    return {
        "id": entry["id"],
        "name": "\u00a7f" + entry["name"],
        "lore": lore,
        "amount": 1,
        "bulk": True,
    }


def balance_head(name, balance):
    if balance < 0:
        return {
            "id": "player_head",
            "name": "\u00a7c\u00a7l" + name + " \u00a77| \u00a7cIn Debt",
            "lore": ["\u00a7cBalance: \u00a74" + format_money(balance),
                     "\u00a77-------------------------",
                     "\u00a77Status: \u00a7cDebt outstanding",
                     "\u00a77Any money you earn pays it off first."],
        }
    return {
        "id": "player_head",
        "name": "\u00a76\u00a7l" + name + " \u00a77| \u00a7eProfile",
        "lore": ["\u00a77Balance: \u00a76" + format_money(balance),
                 "\u00a78(" + exact_money(balance) + ")",
                 "\u00a77-------------------------",
                 "\u00a7aAccount active"],
    }


def control(material, name, *lore):
    return {"id": material, "name": name, "lore": list(lore)}


def container_background(jar, rows):
    width, height, pixels = mcdoc.png_read(jar.read(GENERIC_54))
    if rows >= 6:
        return crop(width, height, pixels, 0, 0, 176, 222)
    top = 17 + rows * 18
    bottom_y = 126
    bottom_h = 222 - bottom_y
    _, _, top_pixels = crop(width, height, pixels, 0, 0, 176, top)
    _, _, bottom_pixels = crop(width, height, pixels, 0, bottom_y, 176, bottom_h)
    out = new_image(176, top + bottom_h)
    blit(out, 176, top + bottom_h, top_pixels, 176, top, 0, 0)
    blit(out, 176, top + bottom_h, bottom_pixels, 176, bottom_h, 0, top)
    return 176, top + bottom_h, out


def draw_item(pixels, width, height, icons, item, x, y, font):
    icon_w, icon_h, icon = icons.get(item["id"])
    blit(pixels, width, height, icon, icon_w, icon_h, x, y)
    amount = int(item.get("amount", 1) or 1)
    if amount > 1:
        font.draw_right(pixels, width, height, str(amount), x + 17, y + 9)
    if item.get("glint"):
        for row in range(min(16, height - y)):
            for col in range(min(16, width - x)):
                if icon[(row * icon_w + col) * 4 + 3]:
                    di = ((y + row) * width + x + col) * 4
                    pixels[di] = min(255, pixels[di] + 40)
                    pixels[di + 2] = min(255, pixels[di + 2] + 70)


def render_gui(jar, icons, font, spec, scale=3):
    rows = spec["rows"]
    bg_w, bg_h, background = container_background(jar, rows)
    title = spec["title"]
    title_w = font.width_of(title)
    width = max(bg_w, title_w + 8, 176)
    title_area = 10
    height = title_area + bg_h
    pixels = new_image(width, height)
    gui_x = (width - bg_w) // 2
    blit(pixels, width, height, background, bg_w, bg_h, gui_x, title_area)
    font.draw_centered(pixels, width, height, title, 1)

    occupied = {item["slot"] for item in spec["items"]}
    for start, end, material in spec.get("fill", []):
        for slot in range(start, end):
            if slot in occupied:
                continue
            x = gui_x + 8 + (slot % 9) * 18
            y = title_area + 18 + (slot // 9) * 18
            icon_w, icon_h, icon = icons.get(material)
            blit(pixels, width, height, icon, icon_w, icon_h, x, y)

    for item in spec["items"]:
        slot = item["slot"]
        x = gui_x + 8 + (slot % 9) * 18
        y = title_area + 18 + (slot // 9) * 18
        draw_item(pixels, width, height, icons, item, x, y, font)

    return scale_nearest(width, height, pixels, scale)


# --------------------------------------------------------------------- screens

def build_specs(rows):
    entries = shop_entry_map(rows)
    usable = [r for r in rows if r["obtainable"] and r["category"] != "creative"]
    buyable = [r for r in rows if r["buyable"]]
    balance = 1_000_000
    head = balance_head("Steve", balance)

    def by_name(items):
        return sorted(items, key=lambda r: r["name"])

    # ---------------------------------------------------------- main shop menu
    categories = [
        ("blocks", "bricks"), ("tools", "iron_pickaxe"), ("armor", "iron_chestplate"),
        ("food", "golden_apple"), ("ores", "diamond_ore"), ("redstone", "redstone"),
        ("nether", "nether_brick"), ("end", "ender_eye"), ("misc", "compass"),
        ("rare", "dragon_egg"),
    ]
    category_slots = [0, 1, 2, 3, 5, 6, 7, 8, 9, 10]
    main_items = []
    for (category, icon), slot in zip(categories, category_slots):
        main_items.append({
            "slot": slot, "id": icon,
            "name": "\u00a7e" + category.capitalize(),
            "lore": ["\u00a77Browse \u00a7e" + category + " \u00a77items",
                     "\u00a7aClick to browse"],
        })
    main_items += [
        {"slot": 4, "id": "compass", "name": "\u00a76All Items",
         "lore": ["\u00a77Browse all shop items", "\u00a7aClick to browse"]},
        {"slot": 45, "id": "ender_eye", "name": "\u00a7b\u00a7lAuction House",
         "lore": ["\u00a77Buy and sell items with other players", "\u00a7aClick to open"]},
        {"slot": 46, "id": "gold_ingot", "name": "\u00a7a\u00a7lBuy Catalog",
         "lore": ["\u00a77Browse the catalog by item family", "\u00a7aClick to open"]},
        {"slot": 47, "id": "hopper", "name": "\u00a7e\u00a7lSell Chest",
         "lore": ["\u00a77Drop items in, close the chest", "\u00a77and they are sold!",
                  "\u00a7aClick to open"]},
        dict(head, slot=49),
        {"slot": 53, "id": "barrier", "name": "\u00a7cClose", "lore": ["\u00a77Close this menu"]},
    ]
    main = {
        "file": "shop-main", "title": "\u00a76Economy Shop", "rows": 6,
        "command": "/shop", "items": main_items, "fill": [(0, 54, "gray_stained_glass_pane")],
        "summary": "The root menu. Category buttons are arranged around the compass shortcut "
                   "to **All Items**, with utility screens along the bottom row.",
    }

    # ------------------------------------------------------- category browser
    blocks = by_name([r for r in usable if r["category"] == "blocks"])
    max_pages = max(1, (len(blocks) + 44) // 45)
    sample_blocks = blocks[:45]
    category_items = [
        dict(shop_item(entry, True), slot=i) for i, entry in enumerate(sample_blocks)
    ]
    category_items += [
        {"slot": 45, "id": "arrow", "name": "\u00a7c\u00ab Back",
         "lore": ["\u00a77Return to previous screen"]},
        {"slot": 47, "id": "gold_ingot", "name": "\u00a7a\u00a7lBuy Catalog",
         "lore": ["\u00a77Catalog grouped by item family"]},
        dict(head, slot=49),
        {"slot": 50, "id": "hopper", "name": "\u00a7e\u00a7lSell Chest",
         "lore": ["\u00a77Drop items in, close to sell"]},
        {"slot": 52, "id": "lime_stained_glass_pane", "name": "\u00a7aNext Page \u00bb",
         "lore": ["\u00a77Page 2"]},
        {"slot": 53, "id": "barrier", "name": "\u00a7cClose", "lore": ["\u00a77Close this menu"]},
    ]
    category = {
        "file": "shop-category", "title": f"\u00a76Shop: \u00a7eBlocks \u00a78(1/{max_pages})",
        "rows": 6, "command": "/shop", "items": category_items,
        "fill": [(45, 54, "gray_stained_glass_pane")],
        "summary": f"Category pages show 45 shop entries. This sample is the first page of "
                   f"**Blocks** ({len(blocks)} items, {max_pages} pages).",
    }

    # ------------------------------------------------------------ buy catalog
    tab_data = [
        ("all", "compass", "All Items", True),
        ("wood", "oak_log", "Wood & Logs", False),
        ("stone", "deepslate", "Stone & Rocks", False),
        ("ores", "diamond_ore", "Ores & Metals", False),
        ("tools", "netherite_sword", "Tools & Weapons", False),
        ("armor", "netherite_chestplate", "Armor Gear", False),
        ("food", "golden_carrot", "Food & Crops", False),
        ("redstone", "repeater", "Redstone", False),
        ("misc", "beacon", "Misc Supplies", False),
    ]
    catalog_entries = by_name(buyable)
    catalog_pages = max(1, (len(catalog_entries) + 35) // 36)
    catalog_items = []
    for i, (tab, icon, name, active) in enumerate(tab_data):
        catalog_items.append({
            "slot": i, "id": icon,
            "name": ("\u00a7a\u00a7l\u25b6 " if active else "\u00a7e") + name,
            "lore": (["\u00a7aCurrently viewing this category"] if active
                     else ["\u00a77Click to browse " + name]),
        })
    for i, entry in enumerate(catalog_entries[:36]):
        catalog_items.append(dict(shop_item(entry, True), slot=9 + i))
    catalog_items += [
        {"slot": 45, "id": "arrow", "name": "\u00a76\u00ab Back",
         "lore": ["\u00a77Return to the previous screen"]},
        {"slot": 47, "id": "hopper", "name": "\u00a7e\u00a7lSell Chest",
         "lore": ["\u00a77Drop items in, close to sell"]},
        dict(head, slot=49),
        {"slot": 50, "id": "lime_stained_glass_pane", "name": "\u00a7aPage 2 \u00bb",
         "lore": ["\u00a77Next page"]},
        {"slot": 51, "id": "ender_eye", "name": "\u00a7b\u00a7lAuction House",
         "lore": ["\u00a77Buy from other players"]},
        {"slot": 53, "id": "barrier", "name": "\u00a7c\u00a7lClose",
         "lore": ["\u00a77Exit the catalog"]},
    ]
    catalog = {
        "file": "buy-catalog", "title": f"\u00a76Buy Catalog \u00a78| \u00a7eAll Items \u00a78(1/{catalog_pages})",
        "rows": 6, "command": "/buy", "items": catalog_items,
        "fill": [(9, 45, "black_stained_glass_pane"), (45, 54, "gray_stained_glass_pane")],
        "summary": f"The tabbed catalog. Tabs occupy the top row and 36 buyable entries fill "
                   f"slots 9-44 ({len(buyable)} buyable items, {catalog_pages} pages).",
    }

    # ------------------------------------------------------------- buy search
    matches = by_name([e for e in buyable if "diamond" in e["id"] or "diamond" in e["name"].lower()])
    search_items = [dict(shop_item(entry, True), slot=i) for i, entry in enumerate(matches[:45])]
    search_items += [
        {"slot": 45, "id": "arrow", "name": "\u00a7c\u00ab Back",
         "lore": ["\u00a77Return to previous screen"]},
        dict(head, slot=49),
        {"slot": 53, "id": "barrier", "name": "\u00a7cClose", "lore": ["\u00a77Close this menu"]},
    ]
    search = {
        "file": "buy-search", "title": f"\u00a76Search: \u00a7eDiamond \u00a78({len(matches)})",
        "rows": 6, "command": "/buy search diamond", "items": search_items,
        "fill": [(0, 45, "gray_stained_glass_pane"), (45, 54, "gray_stained_glass_pane")],
        "summary": f"Search results for **diamond**. Every match is buyable and uses the same "
                   f"left-click / right-click actions as the catalog ({len(matches)} matches).",
    }

    # ------------------------------------------------------- buy confirmation
    sword = entries["diamond_sword"]
    amount = 4
    cost = sword["buy"] * amount
    confirm_items = [
        {"slot": 2, "id": "player_head", "name": head["name"], "lore": head["lore"]},
        {"slot": 9, "id": "red_stained_glass_pane", "name": "\u00a7c-64",
         "lore": ["\u00a77Reduce amount by 64"]},
        {"slot": 10, "id": "red_stained_glass_pane", "name": "\u00a7c-8",
         "lore": ["\u00a77Reduce amount by 8"]},
        {"slot": 11, "id": "red_stained_glass_pane", "name": "\u00a7c-1",
         "lore": ["\u00a77Reduce amount by 1"]},
        {"slot": 13, "id": "diamond_sword",
         "name": "\u00a7fDiamond Sword \u00a77x" + str(amount),
         "lore": ["\u00a77Total cost: \u00a76" + format_money(cost),
                  "\u00a77Price each: \u00a76" + format_money(sword["buy"])],
         "amount": amount},
        {"slot": 15, "id": "lime_stained_glass_pane", "name": "\u00a7a+1",
         "lore": ["\u00a77Increase amount by 1"]},
        {"slot": 16, "id": "lime_stained_glass_pane", "name": "\u00a7a+8",
         "lore": ["\u00a77Increase amount by 8"]},
        {"slot": 17, "id": "lime_stained_glass_pane", "name": "\u00a7a+64",
         "lore": ["\u00a77Increase amount by 64"]},
        {"slot": 22, "id": "emerald_block",
         "name": "\u00a7a\u2714 Buy " + str(amount) + "x for " + format_money(cost),
         "lore": ["\u00a77Click to confirm purchase"]},
        {"slot": 24, "id": "redstone_block", "name": "\u00a7cCancel",
         "lore": ["\u00a77Go back"]},
    ]
    confirm = {
        "file": "buy-confirm", "title": "\u00a76Buy: Diamond Sword", "rows": 3,
        "command": "Right-click a shop entry", "items": confirm_items,
        "fill": [(0, 27, "gray_stained_glass_pane")],
        "summary": "The quantity picker and purchase confirmation. The example is buying "
                   "4 Diamond Swords.",
    }

    # -------------------------------------------------------------- sell chest
    sample_drops = [
        ("cobblestone", 64, 0), ("sand", 64, 1), ("diamond", 12, 2), ("oak_log", 32, 3),
    ]
    sell_items = []
    sold = 0
    earned = 0
    for item_id, count, slot in sample_drops:
        entry = entries[item_id]
        sell_items.append(dict(shop_item(entry, False), slot=slot, amount=count))
        sold += count
        earned += entry["sell"] * count
    sell_items += [
        {"slot": 45, "id": "arrow", "name": "\u00a7c\u00ab Back",
         "lore": ["\u00a77Return to previous screen", "\u00a78Items placed are sold on close"]},
        {"slot": 48, "id": "gold_nugget", "name": "\u00a7eSale preview",
         "lore": ["\u00a77Sellable items: \u00a7f" + str(sold),
                  "\u00a77You will receive: \u00a76" + format_money(earned),
                  "\u00a78Close the chest to sell"]},
        dict(head, slot=49),
        {"slot": 53, "id": "barrier", "name": "\u00a7cClose & Sell",
         "lore": ["\u00a77Closing sells everything placed"]},
    ]
    sell = {
        "file": "sell-chest", "title": "\u00a7eSell Chest \u00a78(drop items, then close)",
        "rows": 6, "command": "/sell", "items": sell_items,
        "fill": [(45, 54, "gray_stained_glass_pane")],
        "summary": "The sell chest. Slots 0-44 are a normal drop area; the preview updates as "
                   "items are placed. Closing the inventory sells everything and returns items "
                   "the shop does not buy.",
    }

    # ------------------------------------------------------------ auction house
    listings = [
        ("diamond_pickaxe", 45000, "Alex", "47h 12m", 0),
        ("golden_apple", 12000, "Steve", "31h 5m", 1),
        ("elytra", 250000, "Nova", "18h 44m", 2),
        ("netherite_ingot", 60000, "Kai", "9h 3m", 3),
        ("shulker_shell", 18000, "Mika", "2h 39m", 4),
        ("enchanted_book", 32000, "Rune", "46h 2m", 5),
        ("diamond_block", 240000, "Ari", "22h 18m", 6),
    ]
    auction_items = []
    for item_id, price, seller, expires, slot in listings:
        entry = entries[item_id]
        auction_items.append({
            "slot": slot, "id": item_id, "name": "\u00a7f" + entry["name"],
            "lore": ["\u00a78-------------------------",
                     "\u00a77Price: \u00a76" + format_money(price),
                     "\u00a77Seller: \u00a7e" + seller,
                     "\u00a77Expires in: \u00a7f" + expires,
                     "\u00a7a\u25b6 Click to buy"],
            "bulk": True,
        })
    auction_items += [
        {"slot": 45, "id": "arrow", "name": "\u00a7c\u00ab Back",
         "lore": ["\u00a77Return to previous screen"]},
        {"slot": 47, "id": "chest", "name": "\u00a76Collect items",
         "lore": ["\u00a77Waiting for you: \u00a7f2",
                  "\u00a77(expired or cancelled listings)",
                  "\u00a7a\u25b6 Click to collect"]},
        dict(head, slot=49),
        {"slot": 51, "id": "comparator", "name": "\u00a7b\u00a7lFilter: \u00a7fSorted",
         "lore": ["\u00a77Same item types are grouped together",
                  "\u00a78Modes: Sorted, Low to High, High to Low",
                  "\u00a7a\u25b6 Click to cycle filter mode",
                  "\u00a77Your choice is saved for this account."]},
        {"slot": 53, "id": "barrier", "name": "\u00a7cClose",
         "lore": ["\u00a77Close this menu"]},
    ]
    auction = {
        "file": "auction-browse", "title": "\u00a7bAuction House \u00a78(1/1)", "rows": 6,
        "command": "/ah", "items": auction_items,
        "fill": [(45, 54, "gray_stained_glass_pane")],
        "summary": "The auction browser. Listings fill slots 0-44; the control row shows "
                   "collection, the saved filter mode and navigation. The sample shows seven listings.",
    }

    # -------------------------------------------------------- auction confirm
    listing_item = entries["diamond_pickaxe"]
    auction_confirm_items = [
        {"slot": 11, "id": "emerald_block",
         "name": "\u00a7a\u2714 Buy for " + format_money(45000),
         "lore": ["\u00a77Your balance: \u00a76" + format_money(balance)]},
        {"slot": 13, "id": "diamond_pickaxe", "name": "\u00a7fDiamond Pickaxe",
         "lore": ["\u00a78-------------------------",
                  "\u00a77Price: \u00a76" + format_money(45000),
                  "\u00a77Seller: \u00a7eAlex",
                  "\u00a77Expires in: \u00a7f47h 12m",
                  "\u00a7a\u25b6 Click to buy"]},
        {"slot": 15, "id": "redstone_block", "name": "\u00a7cCancel",
         "lore": ["\u00a77Go back"]},
    ]
    auction_confirm = {
        "file": "auction-confirm", "title": "\u00a7bConfirm purchase", "rows": 3,
        "command": "Click a listing in /ah", "items": auction_confirm_items,
        "fill": [(0, 27, "gray_stained_glass_pane")],
        "summary": "The auction purchase confirmation for a 45,000 coin Diamond Pickaxe "
                   "listed by **Alex**.",
    }

    return [main, category, catalog, search, confirm, sell, auction, auction_confirm]


# ------------------------------------------------------------------ markdown

def item_table(spec, max_bulk=6):
    items = []
    bulk_seen = 0
    for item in sorted(spec["items"], key=lambda i: i["slot"]):
        if item.get("pane"):
            continue
        if item.get("bulk"):
            bulk_seen += 1
            if bulk_seen > max_bulk:
                continue
        items.append(item)
    lines = ["| Icon | Slot | Item / control | Lore / action |",
             "|:---:|:---:|:--|:--|"]
    for item in items:
        icon = f'<img src="assets/icons/{item["id"]}.png" width="24" height="24" alt="">'
        lore = lore_html(item.get("lore", [])) or "-"
        lines.append(f'| {icon} | `{item["slot"]}` | **{escape_md(item["name"])}** | {lore} |')
    if bulk_seen > max_bulk:
        lines.append(f"|  | `...` | **{bulk_seen - max_bulk} more dynamic entries** | "
                     f"Rendered in the same slots in-game. |")
    return "\n".join(lines)


def build_markdown(specs, version):
    lines = []
    add = lines.append
    add("# Economy GUI Guide")
    add("")
    add(f"![Minecraft {version}](https://img.shields.io/badge/Minecraft-{version}-3C8527?style=for-the-badge)")
    add("![GUI](https://img.shields.io/badge/GUI-{}-2D6A4F?style=for-the-badge)".format(len(specs)))
    add("")
    add("> Every screen in the **Economy** Paper plugin, rendered with the actual Minecraft "
        "container textures, item textures and bitmap font extracted from the client jar.")
    add("")
    add("The previews below are not hand-drawn mockups. They are generated by "
        "[`generate_gui_docs.py`](../.agent-work/tools/generate_gui_docs.py), which reads:")
    add("")
    add("- `assets/minecraft/textures/gui/container/generic_54.png` for the container background")
    add("- `assets/minecraft/textures/item/*.png` and `block/*.png` for every item icon")
    add("- `assets/minecraft/textures/font/ascii.png` for the title text")
    add("")
    add("The item names, lore, slot numbers and navigation controls match the Java sources in "
        "[`src/main/java/minecraft/economy`](../src/main/java/minecraft/economy). Slots are "
        "inventory slots (0 is the top-left slot).")
    add("")
    add("## Screen Flow")
    add("")
    add("```text")
    add("/shop -> Economy Shop")
    add("  +-- category button or compass -> Category browser -> Buy confirmation")
    add("  +-- Buy Catalog -> Buy confirmation")
    add("  +-- Sell Chest -> close to sell")
    add("  +-- Auction House -> listing -> Confirm purchase")
    add("/buy [search] -> Buy Catalog / Search results -> Buy confirmation")
    add("/sell -> Sell Chest")
    add("/ah -> Auction House -> Confirm purchase")
    add("```")
    add("")
    add("---")
    add("")

    for index, spec in enumerate(specs, 1):
        clean_title = strip_codes(spec["title"])
        add(f"## {index}. {clean_title}")
        add("")
        add(f"**Opened by:** `{spec['command']}`  ")
        add(f"**Inventory size:** {spec['rows']} rows ({spec['rows'] * 9} slots)")
        add("")
        add(f'<img src="assets/gui/{spec["file"]}.png" alt="{clean_title} GUI">')
        add("")
        add(spec["summary"])
        add("")
        add(item_table(spec))
        add("")
        add("---")
        add("")

    add("## Regenerating These Images")
    add("")
    add("```bash")
    add("# 1. Download the client jar (never committed)")
    add(".agent-work/tools/fetch_client_jar.sh 26.3")
    add("")
    add("# 2. Refresh item icons and docs/PRICE.md")
    add("python3 .agent-work/tools/generate_price_docs.py \\")
    add("  --jar .agent-work/tmp/mc/client.jar \\")
    add("  --version 26.3 \\")
    add("  --prices src/main/resources/prices.tsv \\")
    add("  --docs docs")
    add("")
    add("# 3. Render the GUIs and build docs/GUI.md")
    add("python3 .agent-work/tools/generate_gui_docs.py \\")
    add("  --jar .agent-work/tmp/mc/client.jar \\")
    add("  --version 26.3 \\")
    add("  --prices src/main/resources/prices.tsv \\")
    add("  --docs docs")
    add("```")
    add("")
    add("_Generated from the plugin sources and the Minecraft client assets. "
        "Do not edit the screenshots by hand; change the Java GUI code or the generator instead._")
    add("")
    return "\n".join(lines)


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--jar", required=True, type=Path)
    parser.add_argument("--version", default="unknown")
    parser.add_argument("--prices", default="src/main/resources/prices.tsv", type=Path)
    parser.add_argument("--docs", default="docs", type=Path)
    parser.add_argument("--scale", default=3, type=int)
    args = parser.parse_args()

    rows = mcdoc.read_prices(args.prices)
    specs = build_specs(rows)
    out_dir = args.docs / "assets" / "gui"
    out_dir.mkdir(parents=True, exist_ok=True)

    with zipfile.ZipFile(args.jar) as jar:
        font = MinecraftFont(jar)
        icons = Icons(jar, args.docs)
        for spec in specs:
            width, height, pixels = render_gui(jar, icons, font, spec, args.scale)
            mcdoc.png_write(out_dir / f"{spec['file']}.png", width, height, pixels)
            print(f"{spec['file']}: {width}x{height}")

    (args.docs / "GUI.md").write_text(build_markdown(specs, args.version), encoding="utf-8")
    print(f"GUI.md: {len(specs)} screens")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
