#!/usr/bin/env python3
"""Normalize prices.tsv against vanilla 1.21.4 recipe data.

The rule is simple: an item's sell price may never exceed the cheapest way a
player can obtain it. Cheapest acquisition is either buying it from the shop or
crafting/smelting/stonecutting it from other priced items. Buy prices are lifted
when a shop item could be crafted for more than the shop sells it for; this
keeps block/unblock recipes (9 diamonds <-> diamond block, etc.) coherent instead
of collapsing the value of the base item.
"""
import json, glob, math, os, sys
from collections import defaultdict

HERE = os.path.dirname(os.path.abspath(__file__))
BASE = os.path.join(HERE, '..', 'tmp', 'mcmeta')
DATA = os.path.join(BASE, 'data', 'minecraft')
SRC = sys.argv[1] if len(sys.argv) > 1 else os.path.join(HERE, '..', '..', 'src', 'main', 'resources', 'prices.tsv')
OUT = sys.argv[2] if len(sys.argv) > 2 else os.path.join(HERE, '..', 'outputs', 'prices_fixed.tsv')
INF = float('inf')

def norm(x):
    if not isinstance(x, str): return None
    return x.removeprefix('minecraft:')

# ---------------------------------------------------------------- load prices
rows = []
by_id = {}
with open(SRC, encoding='utf-8') as f:
    for raw in f:
        line = raw.rstrip('\n')
        if not line.strip() or line.startswith('#'):
            rows.append(None); continue
        p = line.split('\t')
        if len(p) < 7:
            rows.append(None); continue
        id = norm(p[0].strip())
        try:
            buy = int(p[2]); sell = int(p[3])
        except ValueError:
            rows.append(None); continue
        obtainable = p[4].strip().lower() == 'yes'
        buyable = p[5].strip().lower() == 'yes'
        category = p[6].strip().lower()
        if not obtainable or category == 'creative':
            rows.append(None); continue
        if id == 'dragon_egg':
            buy = sell = 0; buyable = False
        if buy <= 0: buyable = False
        e = dict(id=id, parts=p, buy=buy, sell=sell, buyable=buyable, old_buy=buy, old_sell=sell)
        rows.append(e)
        by_id[id] = e

# ---------------------------------------------------------------- load tags
tags = {}
for f in glob.glob(os.path.join(DATA, 'tags', 'item', '**', '*.json'), recursive=True):
    rel = os.path.relpath(f, os.path.join(DATA, 'tags', 'item'))[:-5].replace(os.sep, '/')
    try: d = json.load(open(f))
    except Exception: continue
    tags[rel] = d.get('values', [])

def resolve_tag(name, seen=None):
    name = norm(name)
    if seen is None: seen = set()
    if name in seen: return []
    seen.add(name)
    out = []
    for v in tags.get(name, []):
        if isinstance(v, str):
            if v.startswith('#'): out += resolve_tag(v[1:], seen)
            else: out.append(norm(v))
        elif isinstance(v, dict):
            if v.get('required', True) is False: continue
            e = norm(v.get('id'))
            if e: out.append(e)
    return list(dict.fromkeys(out))

def ing_groups(ing):
    if isinstance(ing, str):
        if ing.startswith('#'):
            return [resolve_tag(ing[1:])]
        return [[norm(ing)]]
    if isinstance(ing, list):
        out = []
        for x in ing: out += ing_groups(x)
        return out
    if isinstance(ing, dict):
        if 'item' in ing: return ing_groups(ing['item'])
        if 'tag' in ing: return ing_groups('#' + norm(ing['tag']))
        if 'id' in ing: return ing_groups(ing['id'])
    return []

def parse_result(res):
    if isinstance(res, str): return [(norm(res), 1)]
    if isinstance(res, dict):
        if 'id' in res: return [(norm(res['id']), res.get('count', 1))]
        return [(norm(k), v.get('count', 1)) for k, v in res.items()]
    return []

# ---------------------------------------------------------------- load recipes
recipes = []
for f in glob.glob(os.path.join(DATA, 'recipe', '*.json')):
    try: d = json.load(open(f))
    except Exception: continue
    t = norm(d.get('type')) or ''
    if t.startswith('crafting_special'): continue
    outputs = []; groups = []
    if t in ('crafting_shaped', 'crafting_shapeless'):
        outputs = parse_result(d.get('result', {}))
        if t == 'crafting_shapeless':
            raw_ings = d.get('ingredients', [])
        else:
            key = d.get('key', {})
            raw_ings = []
            for row in d.get('pattern', []):
                for ch in row:
                    if ch != ' ' and ch in key: raw_ings.append(key[ch])
        groups = [g for ing in raw_ings for g in ing_groups(ing)]
    elif t == 'stonecutting':
        outputs = parse_result(d.get('result'))
        groups = [g for ing in [d.get('ingredient')] for g in ing_groups(ing)]
    elif t in ('smelting', 'blasting', 'smoking', 'campfire_cooking'):
        outputs = parse_result(d.get('result'))
        groups = [g for ing in [d.get('ingredient')] for g in ing_groups(ing)]
    elif t == 'smithing_transform':
        outputs = parse_result(d.get('result', {}))
        groups = [g for ing in [d.get('template'), d.get('base'), d.get('addition')] for g in ing_groups(ing)]
    elif t == 'crafting_transmute':
        outputs = parse_result(d.get('result'))
        groups = [g for ing in [d.get('input'), d.get('material')] for g in ing_groups(ing)]
    else:
        continue
    outputs = [(o, c) for o, c in outputs if o in by_id]
    if not outputs or not groups: continue
    recipes.append(dict(id=os.path.basename(f)[:-5], outputs=outputs, groups=groups))

def group_cost(group, C):
    vals = [C[i] for i in group if i in C]
    return min(vals) if vals else INF

def compute_costs(B, buyable):
    C = {i: (B[i] if buyable.get(i) else INF) for i in by_id}
    changed = True
    while changed:
        changed = False
        for r in recipes:
            cost = 0
            for g in r['groups']:
                c = group_cost(g, C)
                if c == INF:
                    cost = INF; break
                cost += c
            if cost == INF: continue
            for o, n in r['outputs']:
                per = cost / n
                if per < C[o] - 1e-9:
                    C[o] = per
                    changed = True
    return C

B = {i: e['buy'] for i, e in by_id.items()}
buyable = {i: e['buyable'] for i, e in by_id.items()}

# Step 1: keep one-ingredient recipes coherent (9 diamonds <-> diamond block,
# logs -> planks, wool -> carpet, ...). Without this, a cheap storage block
# would make its base material look cheap and wreck that material's sell price.
# Only the cheapest structural recipe is used so an unusual expensive recipe
# cannot inflate an item.
structural = []
for r in recipes:
    if not r['groups']:
        continue
    key = frozenset(r['groups'][0])
    if key and all(frozenset(g) == key for g in r['groups']):
        structural.append(r)

for _ in range(100):
    cheapest = {}
    for r in structural:
        vals = [B[i] for i in r['groups'][0] if i in B and B[i] > 0]
        if not vals:
            continue
        per = len(r['groups']) * min(vals) / r['outputs'][0][1]
        for o, n in r['outputs']:
            p = len(r['groups']) * min(vals) / n
            cheapest[o] = min(cheapest.get(o, INF), p)
    changed = False
    for o, per in cheapest.items():
        if B.get(o, 0) <= 0:
            continue
        need = max(0, math.ceil(per - 1e-9))
        if need > B[o]:
            B[o] = need
            changed = True
    if not changed:
        break
else:
    raise SystemExit('structural price normalization did not converge')

# Step 2: compute the real cheapest acquisition cost for every item using all
# vanilla recipes (buying counts as one way to acquire an item).
def compute_costs(B, buyable):
    C = {i: (B[i] if buyable.get(i) and B[i] > 0 else INF) for i in by_id}
    changed = True
    while changed:
        changed = False
        for r in recipes:
            cost = 0
            for g in r['groups']:
                vals = [C[i] for i in g if i in C]
                c = min(vals) if vals else INF
                if c == INF:
                    cost = INF
                    break
                cost += c
            if cost == INF:
                continue
            for o, n in r['outputs']:
                per = cost / n
                if per < C[o] - 1e-9:
                    C[o] = per
                    changed = True
    return C

C = compute_costs(B, buyable)

# Step 3: never pay more for an item than it costs to acquire it.
changed_entries = []
for i, e in by_id.items():
    old_b, old_s = e['buy'], e['sell']
    b = B[i]
    s = old_s
    if b != old_b:
        s = max(s, int(math.floor(b * 0.8)))
    if C[i] != INF:
        s = min(s, int(math.floor(C[i] + 1e-9)))
    if b > 0:
        s = min(s, b)
    s = max(0, s)
    e['new_buy'], e['new_sell'] = b, s
    if b != old_b or s != old_s:
        changed_entries.append(e)

# ---------------------------------------------------------------- write output
os.makedirs(os.path.dirname(OUT), exist_ok=True)
with open(OUT, 'w', encoding='utf-8', newline='\n') as f:
    for raw in open(SRC, encoding='utf-8').read().splitlines(keepends=True):
        line = raw.rstrip('\n')
        if not line.strip() or line.startswith('#'):
            f.write(raw); continue
        p = line.split('\t')
        e = by_id.get(norm(p[0].strip()))
        if not e or len(p) < 7:
            f.write(raw); continue
        p[2] = str(e['new_buy'])
        p[3] = str(e['new_sell'])
        f.write('\t'.join(p) + ('\n' if raw.endswith('\n') else ''))

raised = [e for e in changed_entries if e['new_buy'] != e['old_buy']]
reduced = [e for e in changed_entries if e['new_sell'] < e['old_sell']]
print(f'entries={len(by_id)} recipes={len(recipes)} changed={len(changed_entries)} buy_raised={len(raised)} sell_reduced={len(reduced)}')
print('\nTop buy increases:')
for e in sorted(raised, key=lambda x: x['new_buy'] - x['old_buy'], reverse=True)[:40]:
    print(f"  {e['id']:<45} {e['old_buy']:>10} -> {e['new_buy']:<10} sell {e['old_sell']} -> {e['new_sell']}")
print('\nTop sell reductions:')
for e in sorted(reduced, key=lambda x: x['new_sell'] - x['old_sell'])[:60]:
    print(f"  {e['id']:<45} buy {e['old_buy']} -> {e['new_buy']:<8} sell {e['old_sell']:>8} -> {e['new_sell']}")
