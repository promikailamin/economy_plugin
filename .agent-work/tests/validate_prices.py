#!/usr/bin/env python3
"""Validate that the shop cannot be arbitraged with vanilla crafting recipes.

A purchase -> transform -> sale loop is profitable when an output's sell value
exceeds the cheapest acquisition cost of the recipe inputs. Acquisition cost is
the minimum of the shop buy price and every recipe that produces the item.
"""
import glob, json, math, os, sys

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.join(HERE, '..', '..')
DATA = sys.argv[2] if len(sys.argv) > 2 else os.path.join(HERE, '..', 'tmp', 'mcmeta', 'data', 'minecraft')
PRICES = sys.argv[1] if len(sys.argv) > 1 else os.path.join(ROOT, 'src', 'main', 'resources', 'prices.tsv')
INF = float('inf')

def norm(x):
    return x.removeprefix('minecraft:') if isinstance(x, str) else None

prices = {}
with open(PRICES, encoding='utf-8') as f:
    for line in f:
        if not line.strip() or line.startswith('#'):
            continue
        p = line.rstrip('\n').split('\t')
        if len(p) < 7:
            continue
        try:
            buy, sell = int(p[2]), int(p[3])
        except ValueError:
            continue
        if p[4].strip().lower() != 'yes' or p[6].strip().lower() == 'creative':
            continue
        id = norm(p[0].strip())
        buyable = p[5].strip().lower() == 'yes' and buy > 0
        if id == 'dragon_egg':
            buy = sell = 0
            buyable = False
        prices[id] = dict(buy=buy, sell=sell, buyable=buyable)

tags = {}
for f in glob.glob(os.path.join(DATA, 'tags', 'item', '**', '*.json'), recursive=True):
    rel = os.path.relpath(f, os.path.join(DATA, 'tags', 'item'))[:-5].replace(os.sep, '/')
    tags[rel] = json.load(open(f)).get('values', [])

def resolve_tag(name, seen=None):
    name = norm(name)
    if seen is None:
        seen = set()
    if name in seen:
        return []
    seen.add(name)
    out = []
    for v in tags.get(name, []):
        if isinstance(v, str):
            out += resolve_tag(v[1:], seen) if v.startswith('#') else [norm(v)]
        elif isinstance(v, dict) and v.get('required', True) is not False:
            if v.get('id'):
                out.append(norm(v['id']))
    return list(dict.fromkeys(out))

def groups(ing):
    if isinstance(ing, str):
        return [resolve_tag(ing[1:])] if ing.startswith('#') else [[norm(ing)]]
    if isinstance(ing, list):
        out = []
        for x in ing:
            out += groups(x)
        return out
    if isinstance(ing, dict):
        for k in ('item', 'tag', 'id'):
            if k in ing:
                return groups(('#' if k == 'tag' else '') + norm(ing[k]))
    return []

def result(res):
    if isinstance(res, str):
        return [(norm(res), 1)]
    if isinstance(res, dict) and 'id' in res:
        return [(norm(res['id']), res.get('count', 1))]
    return []

recipes = []
for f in glob.glob(os.path.join(DATA, 'recipe', '*.json')):
    d = json.load(open(f))
    t = norm(d.get('type')) or ''
    if t.startswith('crafting_special'):
        continue
    out, gs = [], []
    if t in ('crafting_shaped', 'crafting_shapeless'):
        out = result(d.get('result', {}))
        if t == 'crafting_shapeless':
            raw = d.get('ingredients', [])
        else:
            key = d.get('key', {})
            raw = [key[ch] for row in d.get('pattern', []) for ch in row if ch != ' ' and ch in key]
        gs = [g for ing in raw for g in groups(ing)]
    elif t == 'stonecutting':
        out = result(d.get('result'))
        gs = [g for ing in [d.get('ingredient')] for g in groups(ing)]
    elif t in ('smelting', 'blasting', 'smoking', 'campfire_cooking'):
        out = result(d.get('result'))
        gs = [g for ing in [d.get('ingredient')] for g in groups(ing)]
    elif t == 'smithing_transform':
        out = result(d.get('result', {}))
        gs = [g for ing in [d.get('template'), d.get('base'), d.get('addition')] for g in groups(ing)]
    elif t == 'crafting_transmute':
        out = result(d.get('result'))
        gs = [g for ing in [d.get('input'), d.get('material')] for g in groups(ing)]
    else:
        continue
    out = [(o, n) for o, n in out if o in prices]
    if out and gs:
        recipes.append((os.path.basename(f), out, gs))

def costs():
    c = {i: (e['buy'] if e['buyable'] else INF) for i, e in prices.items()}
    changed = True
    while changed:
        changed = False
        for _, out, gs in recipes:
            total = 0
            for g in gs:
                vals = [c[i] for i in g if i in c]
                best = min(vals) if vals else INF
                if best == INF:
                    total = INF
                    break
                total += best
            if total == INF:
                continue
            for o, n in out:
                per = total / n
                if per < c[o] - 1e-9:
                    c[o] = per
                    changed = True
    return c

C = costs()
errors = []
for i, e in prices.items():
    if e['buyable'] and e['sell'] > e['buy']:
        errors.append(f'{i}: sell {e["sell"]} > buy {e["buy"]}')
    if e['sell'] > 0 and C[i] != INF and e['sell'] > C[i] + 1e-9:
        errors.append(f'{i}: sell {e["sell"]} > cheapest acquisition {C[i]:.3f}')

for name, out, gs in recipes:
    total = 0
    for g in gs:
        vals = [C[i] for i in g if i in C]
        best = min(vals) if vals else INF
        if best == INF:
            total = INF
            break
        total += best
    if total == INF:
        continue
    for o, n in out:
        revenue = prices[o]['sell'] * n
        if revenue > total + 1e-9:
            errors.append(f'{name}: {n}x {o} sells for {revenue}, inputs cost {total:.3f}')

if errors:
    print(f'FAIL: {len(errors)} price arbitrage problem(s)')
    for e in errors[:50]:
        print('  ' + e)
    sys.exit(1)
print(f'OK: {len(prices)} prices, {len(recipes)} recipes, no buy -> craft -> sell profit')
