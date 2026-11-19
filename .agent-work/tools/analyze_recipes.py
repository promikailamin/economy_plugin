#!/usr/bin/env python3
import json, glob, os, re, sys
from collections import defaultdict

BASE = os.path.join(os.path.dirname(__file__), '..', 'tmp', 'mcmeta')
DATA = os.path.join(BASE, 'data', 'minecraft')
PRICES = sys.argv[1] if len(sys.argv) > 1 else os.path.join(os.path.dirname(__file__), '..', '..', 'src', 'main', 'resources', 'prices.tsv')

def norm(x):
    if not isinstance(x, str): return None
    return x.removeprefix('minecraft:')

prices = {}
with open(PRICES, encoding='utf-8') as f:
    for line in f:
        if not line.strip() or line.startswith('#'): continue
        p = line.rstrip('\n').split('\t')
        if len(p) < 7: continue
        id = norm(p[0].strip())
        try:
            buy = int(p[2]); sell = int(p[3])
        except ValueError:
            continue
        obtainable = p[4].strip().lower() == 'yes'
        buyable = p[5].strip().lower() == 'yes'
        cat = p[6].strip().lower()
        if not obtainable or cat == 'creative': continue
        if id == 'dragon_egg':
            buy = sell = 0; buyable = False
        if buy <= 0: buyable = False
        prices[id] = dict(buy=buy, sell=sell, buyable=buyable, line=line, parts=p)

# Load item tags
tags = {}
for f in glob.glob(os.path.join(DATA, 'tags', 'item', '**', '*.json'), recursive=True):
    rel = os.path.relpath(f, os.path.join(DATA, 'tags', 'item'))[:-5].replace(os.sep, '/')
    try:
        d = json.load(open(f))
    except Exception:
        continue
    tags[rel] = d.get('values', [])

def resolve_tag(name, seen=None):
    name = norm(name)
    if seen is None: seen = set()
    if name in seen: return []
    seen.add(name)
    out = []
    for v in tags.get(name, []):
        if isinstance(v, str):
            if v.startswith('#'):
                out += resolve_tag(v[1:], seen)
            else:
                out.append(norm(v))
        elif isinstance(v, dict):
            e = norm(v.get('id'))
            if e:
                if v.get('required', True) is False:
                    continue
                out.append(e)
    return list(dict.fromkeys(out))

def ing_ids(ing):
    if isinstance(ing, str):
        if ing.startswith('#'):
            return resolve_tag(ing[1:])
        return [norm(ing)]
    if isinstance(ing, list):
        out = []
        for x in ing:
            out += ing_ids(x)
        return list(dict.fromkeys(out))
    if isinstance(ing, dict):
        if 'item' in ing: return ing_ids(ing['item'])
        if 'tag' in ing: return ing_ids('#' + norm(ing['tag']))
        if 'id' in ing: return ing_ids(ing['id'])
    return []

def min_buy(ids):
    vals = [prices[i]['buy'] for i in ids if i in prices and prices[i]['buyable'] and prices[i]['buy'] > 0]
    return min(vals) if vals else None

def all_buyable_known(ids):
    # There must be at least one buyable option, and every non-buyable alternative can be ignored
    # (player chooses the cheapest purchasable one).
    return any(i in prices and prices[i]['buyable'] and prices[i]['buy'] > 0 for i in ids)

recipes = []
for f in glob.glob(os.path.join(DATA, 'recipe', '*.json')):
    rid = os.path.basename(f)[:-5]
    try:
        d = json.load(open(f))
    except Exception:
        continue
    t = norm(d.get('type')) or ''
    if t.startswith('crafting_special'): continue
    outputs = []
    ings = []
    def parse_result(res):
        if isinstance(res, str):
            return [(norm(res), 1)]
        if isinstance(res, dict):
            if 'id' in res:
                return [(norm(res['id']), res.get('count', 1))]
            return [(norm(k), v.get('count', 1)) for k, v in res.items()]
        return []
    if t in ('crafting_shaped', 'crafting_shapeless'):
        outputs = parse_result(d.get('result', {}))
        if t == 'crafting_shapeless':
            ings = d.get('ingredients', [])
        else:
            key = d.get('key', {})
            for row in d.get('pattern', []):
                for ch in row:
                    if ch != ' ' and ch in key:
                        ings.append(key[ch])
    elif t == 'stonecutting':
        outputs = parse_result(d.get('result'))
        ings = [d.get('ingredient')]
    elif t in ('smelting', 'blasting', 'smoking', 'campfire_cooking'):
        outputs = parse_result(d.get('result'))
        ings = [d.get('ingredient')]
    elif t == 'smithing_transform':
        outputs = parse_result(d.get('result', {}))
        ings = [d.get('template'), d.get('base'), d.get('addition')]
    elif t == 'crafting_transmute':
        outputs = parse_result(d.get('result'))
        ings = [d.get('input'), d.get('material')]
    else:
        continue
    if not outputs or any(o[0] is None for o in outputs): continue
    recipes.append(dict(id=rid, type=t, outputs=outputs, ings=[i for i in ings if i is not None]))

viol = []
unknown = 0
for r in recipes:
    input_ids = [ing_ids(i) for i in r['ings']]
    if not input_ids: continue
    if not all(all_buyable_known(ids) for ids in input_ids):
        unknown += 1
        continue
    cost = 0
    ok = True
    for ids in input_ids:
        c = min_buy(ids)
        if c is None:
            ok = False; break
        cost += c
    if not ok: continue
    for oid, oc in r['outputs']:
        p = prices.get(oid)
        if not p or p['sell'] <= 0: continue
        revenue = p['sell'] * oc
        if revenue > cost:
            viol.append((revenue - cost, r['id'], r['type'], oid, oc, p['buy'], p['sell'], cost, r['ings']))

viol.sort(reverse=True)
print(f'prices={len(prices)} recipes={len(recipes)} violations={len(viol)} skipped_unknown_inputs={unknown}')
for v in viol:
    profit, rid, typ, oid, oc, buy, sell, cost, ings = v
    print(f'{profit:>8} {rid:<55} {typ:<20} out={oid} x{oc} B={buy} S={sell} cost={cost} ings={ings}')
