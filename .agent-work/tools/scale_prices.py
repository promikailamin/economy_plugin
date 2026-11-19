#!/usr/bin/env python3
"""Scale shop prices while keeping renewable/duplicable staples cheap.

The issue asks for every price to be multiplied by 100 except the easily
renewable building materials (cobblestone, sand, gravel, concrete, rails and
carpets).  Simply multiplying the TSV is not safe: a cheap protected material
can be turned into an unprotected block and sold for 100x its old value.  The
second half of this script therefore applies the same rule as
``validate_prices.py``: the sell price of every unprotected item is capped at
the cheapest way to acquire it (shop buy or a vanilla crafting/smelting recipe).

Protected rows are written back byte-for-byte, so their prices really are
"the same as before".

This was a one-time migration for issue #3.  To reproduce it, run the script
against the pre-scaling snapshot shown below; running it twice on the current
``prices.tsv`` multiplies the prices again.

Usage:
    python3 .agent-work/tools/scale_prices.py \
        --prices .agent-work/outputs/prices_before_issue3.tsv \
        --output src/main/resources/prices.tsv \
        --recipes .agent-work/tmp/mcmeta/data/minecraft \
        --multiplier 100
"""
from __future__ import annotations

import argparse
import glob
import json
import math
import os
from pathlib import Path

INF = float("inf")

# Item id fragments whose prices must stay at their previous value.  These are
# the resources players can farm, duplicate or craft in bulk.  ``concrete`` is
# included together with ``concrete_powder`` because powder becomes concrete by
# touching water - a transformation that is not present in the recipe JSON, so
# the arbitrage check below cannot see it.
PROTECTED_PATTERNS = (
    "cobblestone",
    "sand",
    "gravel",
    "concrete",
    "rail",
    "carpet",
)


def norm(value):
    if not isinstance(value, str):
        return None
    return value.removeprefix("minecraft:")


def is_protected(item_id: str) -> bool:
    return any(pattern in item_id.lower() for pattern in PROTECTED_PATTERNS)


def load_prices(path: Path):
    """Return (raw lines, price rows keyed by item id)."""
    raw_lines = path.read_text(encoding="utf-8").splitlines(keepends=True)
    rows = {}
    for raw in raw_lines:
        line = raw.rstrip("\n")
        if not line.strip() or line.startswith("#"):
            continue
        parts = line.split("\t")
        if len(parts) < 7:
            continue
        try:
            buy, sell = int(parts[2]), int(parts[3])
        except ValueError:
            continue
        item_id = norm(parts[0].strip())
        if not item_id:
            continue
        obtainable = parts[4].strip().lower() == "yes"
        category = parts[6].strip().lower()
        rows[item_id] = {
            "id": item_id,
            "parts": parts,
            "buy": buy,
            "sell": sell,
            "obtainable": obtainable and category != "creative",
            "buyable": obtainable and parts[5].strip().lower() == "yes" and buy > 0,
        }
    return raw_lines, rows


# ------------------------------------------------------------------ recipe data

def load_tags(data_dir: Path):
    tags = {}
    pattern = str(data_dir / "tags" / "item" / "**" / "*.json")
    for filename in glob.glob(pattern, recursive=True):
        rel = os.path.relpath(filename, data_dir / "tags" / "item")[:-5].replace(os.sep, "/")
        try:
            tags[rel] = json.load(open(filename, encoding="utf-8")).get("values", [])
        except (OSError, json.JSONDecodeError):
            continue
    return tags


def resolve_tag(name, tags, seen=None):
    name = norm(name)
    if seen is None:
        seen = set()
    if name in seen:
        return []
    seen.add(name)
    out = []
    for value in tags.get(name, []):
        if isinstance(value, str):
            if value.startswith("#"):
                out += resolve_tag(value[1:], tags, seen)
            else:
                out.append(norm(value))
        elif isinstance(value, dict) and value.get("required", True) is not False:
            if value.get("id"):
                out.append(norm(value["id"]))
    return list(dict.fromkeys(out))


def ingredient_groups(ingredient, tags):
    if isinstance(ingredient, str):
        return [resolve_tag(ingredient[1:], tags)] if ingredient.startswith("#") else [[norm(ingredient)]]
    if isinstance(ingredient, list):
        out = []
        for value in ingredient:
            out += ingredient_groups(value, tags)
        return out
    if isinstance(ingredient, dict):
        for key in ("item", "tag", "id"):
            if key in ingredient:
                return ingredient_groups(("#" if key == "tag" else "") + norm(ingredient[key]), tags)
    return []


def parse_result(result):
    if isinstance(result, str):
        return [(norm(result), 1)]
    if isinstance(result, dict) and "id" in result:
        return [(norm(result["id"]), result.get("count", 1))]
    return []


def load_recipes(data_dir: Path, tags, priced_ids):
    recipes = []
    for filename in glob.glob(str(data_dir / "recipe" / "*.json")):
        try:
            data = json.load(open(filename, encoding="utf-8"))
        except (OSError, json.JSONDecodeError):
            continue
        recipe_type = norm(data.get("type")) or ""
        if recipe_type.startswith("crafting_special"):
            continue
        outputs, groups = [], []
        if recipe_type in ("crafting_shaped", "crafting_shapeless"):
            outputs = parse_result(data.get("result", {}))
            if recipe_type == "crafting_shapeless":
                raw = data.get("ingredients", [])
            else:
                key = data.get("key", {})
                raw = [key[ch] for row in data.get("pattern", [])
                       for ch in row if ch != " " and ch in key]
            groups = [group for ingredient in raw for group in ingredient_groups(ingredient, tags)]
        elif recipe_type == "stonecutting":
            outputs = parse_result(data.get("result"))
            groups = [group for ingredient in [data.get("ingredient")]
                      for group in ingredient_groups(ingredient, tags)]
        elif recipe_type in ("smelting", "blasting", "smoking", "campfire_cooking"):
            outputs = parse_result(data.get("result"))
            groups = [group for ingredient in [data.get("ingredient")]
                      for group in ingredient_groups(ingredient, tags)]
        elif recipe_type == "smithing_transform":
            outputs = parse_result(data.get("result", {}))
            groups = [group for ingredient in (data.get("template"), data.get("base"), data.get("addition"))
                      for group in ingredient_groups(ingredient, tags)]
        elif recipe_type == "crafting_transmute":
            outputs = parse_result(data.get("result"))
            groups = [group for ingredient in (data.get("input"), data.get("material"))
                      for group in ingredient_groups(ingredient, tags)]
        else:
            continue
        outputs = [(item, count) for item, count in outputs if item in priced_ids]
        if outputs and groups:
            recipes.append((os.path.basename(filename), outputs, groups))
    return recipes


def cheapest_acquisition(rows, recipes):
    cost = {item_id: (row["buy"] if row["buyable"] else INF) for item_id, row in rows.items()}
    changed = True
    while changed:
        changed = False
        for _, outputs, groups in recipes:
            total = 0.0
            for group in groups:
                values = [cost[item] for item in group if item in cost]
                best = min(values) if values else INF
                if best == INF:
                    total = INF
                    break
                total += best
            if total == INF:
                continue
            for item, count in outputs:
                per = total / count
                if per < cost[item] - 1e-9:
                    cost[item] = per
                    changed = True
    return cost


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--prices", default="src/main/resources/prices.tsv", type=Path)
    parser.add_argument("--recipes", default=".agent-work/tmp/mcmeta/data/minecraft", type=Path)
    parser.add_argument("--multiplier", default=100, type=int)
    parser.add_argument("--output", default=None, type=Path,
                        help="write here instead of replacing --prices")
    args = parser.parse_args()

    raw_lines, rows = load_prices(args.prices)
    tags = load_tags(args.recipes)
    recipes = load_recipes(args.recipes, tags, set(rows))

    protected = {item_id for item_id in rows if is_protected(item_id)}
    scaled = {item_id: row for item_id, row in rows.items() if item_id not in protected}

    # Raw values after the x100 pass, before the anti-arbitrage sell cap.
    for row in scaled.values():
        row["scaled_buy"] = row["buy"] * args.multiplier
        row["scaled_sell"] = row["sell"] * args.multiplier

    # Acquisition costs must use the *new* buy prices.  Protected rows keep
    # their old prices; every other row uses the scaled buy price.
    effective_rows = {}
    for item_id, row in rows.items():
        row_buy = row["scaled_buy"] if item_id in scaled else row["buy"]
        effective_rows[item_id] = {
            "buy": row_buy,
            "buyable": row["buyable"] and row_buy > 0,
        }
    cost = cheapest_acquisition(effective_rows, recipes)

    # Cap sell prices for unprotected rows.  Buying prices are deliberately not
    # reduced: the issue asked for the x100 increase, and the cap only removes a
    # buy -> craft -> sell profit loop.
    reduced = []
    for item_id, row in scaled.items():
        new_sell = row["scaled_sell"]
        if row["obtainable"] and cost[item_id] != INF:
            new_sell = min(new_sell, int(math.floor(cost[item_id] + 1e-9)))
        if row["buyable"]:
            new_sell = min(new_sell, row["scaled_buy"])
        new_sell = max(0, new_sell)
        row["new_buy"] = row["scaled_buy"]
        row["new_sell"] = new_sell
        if (new_sell, row["scaled_buy"]) != (row["scaled_sell"], row["scaled_buy"]):
            reduced.append(row)

    output = args.output or args.prices
    out_lines = []
    for raw in raw_lines:
        line = raw.rstrip("\n")
        if not line.strip() or line.startswith("#"):
            out_lines.append(raw)
            continue
        parts = line.split("\t")
        item_id = norm(parts[0].strip()) if parts else None
        row = rows.get(item_id)
        if row is None or len(parts) < 7:
            out_lines.append(raw)
            continue
        if item_id in protected:
            out_lines.append(raw)
            continue
        parts[2] = str(row["new_buy"])
        parts[3] = str(row["new_sell"])
        out_lines.append("\t".join(parts) + ("\n" if raw.endswith("\n") else ""))
    output.write_text("".join(out_lines), encoding="utf-8")

    protected_buy = sum(rows[i]["buy"] for i in protected)
    protected_sell = sum(rows[i]["sell"] for i in protected)
    print(f"rows={len(rows)} protected={len(protected)} scaled={len(scaled)} "
          f"multiplier={args.multiplier} sell_capped={len(reduced)}")
    print(f"protected buy total={protected_buy:,} sell total={protected_sell:,}")
    print("\nLargest sell caps:")
    for row in sorted(reduced, key=lambda r: r["scaled_sell"] - r["new_sell"], reverse=True)[:30]:
        print(f"  {row['id']:<48} buy {row['scaled_buy']:>10,}  "
              f"sell {row['scaled_sell']:>10,} -> {row['new_sell']:<10,}")
    print(f"\nWrote {output}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
