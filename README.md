# Economy (Paper 1.21.x)

Server-side economy plugin. No client mod needed.

## Features
- Balances saved per-UUID in `plugins/Economy/balances.yml` (autosave + on shutdown)
- `/shop` GUI (categories), `/buy` catalog + search, quantity picker
- Sell chest (`/sell`): drop items in, close, get paid; unsellable items are returned
- `/sell hand | all | <item> [amount|all]`
- Auction house (`/ah`, `/ah sell <price>`, `/ah collect`) with expiry and sale tax
- Smart AI market: synthetic listings with realistic random tools, armour, enchantments,
  damage, stacks and shulker-box supply crates; per-category stock, restock timers,
  demand and a small neural network that learns from sales and reprices listings
- `/ah stock` market overview with live stock/demand/price information
- Limited special relics in the AI market: 3x3/4x4/5x5 area miners (pickaxe and
  shovel), vein miner, tree miner, level 10-15 enchanted books and gear, and eternal
  armour with one random slot-bound good or bad potion effect
- Mystery shop (`/mystery`, `/ah mystery`): crate tiers with stock/restock, a neural
  luck model, dry-streak compensation and weighted loot that can be better or worse
- Sell Axe: blocks broken with it are sold automatically (`/sellaxe`)
- Balance sidebar (`/ecosb`, remembered per player), `/baltop`
- `/pay`, `/fine` (can cause debt), `/eco give|take|set|reset|reload`
- Amounts accept `1500`, `1,500`, `2.5k`, `1m`, `0.5b`

## Files created in plugins/Economy/
`config.yml`, `prices.tsv` (editable, `/eco reload`), `balances.yml`, `auctions.yml`,
`ai-market.yml` (AI brain + stock state), `mystery.yml` (crate stock + stats)

## AI market and mystery shop
Configure them under `auction.ai` and `mystery` in `config.yml`. The AI market keeps a
target number of realistic listings live and restocks categories on a randomized timer.
The mystery shop uses weighted payout bands; its neural model gradually adjusts luck for
long dry streaks, while the configured payout factors keep long-term returns below 100%.
Use `/eco reload` after changing market settings. `/ah stock` shows stock, demand, price
trend and restock state for every AI category.

## Build
`./gradlew build` -> `build/libs/Economy-1.0.0.jar` (Java 21; API jars are in `libs/`).

GitHub Actions builds on every push/PR with `.github/workflows/build.yml`. Pushes to `main`
and `v*` tags also create or update a GitHub release containing the plugin jar,
[`docs/PRICE.md`](docs/PRICE.md) and [`docs/GUI.md`](docs/GUI.md).

## Guides
- [`docs/PRICE.md`](docs/PRICE.md) lists every shop price with its real Minecraft item
  texture.
- [`docs/GUI.md`](docs/GUI.md) shows every in-game GUI rendered with the official
  Minecraft container textures, item textures and bitmap font.

Most prices are 100x their original values. Easily renewable resources
(cobblestone, sand, gravel, concrete, rails and carpets) keep their old prices, and
sell prices are capped where a cheap resource could otherwise be crafted into a
profitable item. Regenerate the price guide after changing `prices.tsv`:

```bash
.agent-work/tools/fetch_client_jar.sh 26.3
python3 .agent-work/tools/generate_price_docs.py \
  --jar .agent-work/tmp/mc/client.jar --version 26.3 \
  --prices src/main/resources/prices.tsv --docs docs

python3 .agent-work/tools/generate_gui_docs.py \
  --jar .agent-work/tmp/mc/client.jar --version 26.3 \
  --prices src/main/resources/prices.tsv --docs docs
```

## Special relics
The AI market keeps at most a couple of special relics listed, and the total number it
may ever mint is capped by `auction.ai.max-special-relics` (default 24). Relics are
priced from a much higher base value than normal gear and cannot be sold back to the
server shop, so they only change hands through the auction house.

Area miners break a plane around the mined block based on where the player is looking;
vein miners break connected ores (up to 64) and tree miners fell connected logs (up to
128). Sneak while breaking to disable the extra effect. Eternal armour never loses
durability and grants its stored effect while worn; each piece has exactly one effect
matched to its slot (speed on boots, resistance/regeneration on chestplates and
leggings, and so on). Some relics carry a bad effect instead.

## Permissions
`economy.bal`, `economy.bal.others`, `economy.pay`, `economy.shop`, `economy.scoreboard`,
`economy.auction`, `economy.mystery` (all default true); `economy.admin` (default op):
`/eco`, `/fine`, `/sellaxe`, admin `/pay` grants (including self-payments) that ignore
the payer's balance. Shop, auction and mystery purchases still use normal prices and
balance checks for operators.
