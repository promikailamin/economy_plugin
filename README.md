# Economy (Paper 1.21.x)

Server-side economy plugin. No client mod needed.

## Features
- Balances saved per-UUID in `plugins/Economy/balances.yml` (autosave + on shutdown)
- `/shop` GUI (categories), `/buy` catalog + search, quantity picker
- Player heads and mob heads can be sold to the server but are **not buyable** from the shop
- Sell chest (`/sell`): drop items in, close, get paid; unsellable items are returned
- `/sell hand | all | <item> [amount|all]`
- Auction house (`/ah`, `/ah sell <price>`, `/ah collect`) with expiry and sale tax
- Auction browser filter (`Sorted`, `Low to High`, `High to Low`), saved per player
- Smart AI market: up to 1000 synthetic listings by default with realistic random tools,
  armour, enchantments, damage, stacks and shulker-box supply crates; per-category stock,
  restock timers, demand and a small neural network that learns from sales and reprices listings
- AI buyers that occasionally purchase reasonably priced listings created by players,
  paying the seller (minus tax) and reporting the sale in chat
- `/ah stock` market overview with live stock/demand/price information
- Limited special relics in the AI market: 3x3/4x4/5x5 area miners (pickaxe and
  shovel), vein miner, tree miner, level 10-15 enchanted books and gear, effect
  swords that inflict their bound non-instant potion effect on hit, eternal armour
  with one random non-instant potion effect (level 1-4), and at most one Keep
  Inventory Helmet
- Anvil bypass: level 10-15 books and any enchantment level can be applied, equal
  levels combine one level higher (Protection 4 + 4 = 5), and conflicting
  enchantments such as Infinity + Mending or Sharpness + Smite can coexist
- Mystery shop (`/mystery`, `/ah mystery`): crate tiers with stock/restock, a neural
  luck model, dry-streak compensation and weighted loot that can be better or worse
- Sell Axe: breaking a chest with it keeps the chest in place and sells its contents
  (double chests included); other blocks break normally (`/sellaxe`)
- Balance sidebar (`/ecosb`, remembered per player), `/baltop`
- `/pay`, `/fine` (can cause debt), `/eco give|take|set|reset|reload`
- Amounts accept `1500`, `1,500`, `2.5k`, `1m`, `0.5b`

## Files created in plugins/Economy/
`config.yml`, `prices.tsv` (editable, `/eco reload`), `balances.yml`, `auctions.yml`,
`ai-market.yml` (AI brain, stock and buyer stats), `mystery.yml` (crate stock + stats)

## AI market and mystery shop
Configure them under `auction.ai` and `mystery` in `config.yml`. The AI market keeps a
target number of realistic listings live (1000 by default) and restocks categories on a
randomized timer. Per-category stock capacity scales with the target, so the market stays
supplied even with 1000+ listings.
The mystery shop uses weighted payout bands; its neural model gradually adjusts luck for
long dry streaks, while the configured payout factors keep long-term returns below 100%.
Use `/eco reload` after changing market settings. `/ah stock` shows stock, demand, price
trend and restock state for every AI category.

AI listings expire after a random 1-5 hours (`auction.ai.duration-hours-min` and
`duration-hours-max`). The browser groups matching item types in `Sorted` mode, and the
chosen filter is stored per UUID in `balances.yml`. On startup the saved market is
normalised: synthetic listings are trimmed back to the configured target and duplicate
Keep Inventory Helmet listings are removed (player items go to `/ah collect`).

AI buyers run on the same 30-second market tick. They only consider player-created
listings, skip items below `min-value` or above `max-price`, and ignore listings priced
more than `max-value-ratio` times their appraised value. The chance per listing can be
tuned with `auction.ai.buyers.chance-per-listing-per-tick`; sales pay the seller the
normal proceeds after auction tax. Buyer statistics are stored in `ai-market.yml`.

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
The AI market maintains configured active-count ranges for each relic family under
`auction.ai.special-counts`: effect armour (10-30), effect swords (10-30), high-enchant
armour (10-30), super tools (10-30), high-enchant books (20-50), and the Keep Inventory
Helmet (0-1). The total active special-relic count is capped by
`auction.ai.max-special-relics` (default 200). Relics are priced from a much higher base
value than normal gear and cannot be sold back to the server shop, so they only change
hands through the auction house.

Area miners break a 3x3x3, 4x4x4 or 5x5x5 cube centred on the block the player mined;
vein miners break connected ores (up to 64) and tree miners fell connected logs (up to
128). Sneak while breaking to disable the extra effect. Effect swords inflict their
bound potion effect on the entity they hit. Eternal armour never loses durability and
grants its stored effect while worn; each piece has exactly one effect, which can be any
non-instant potion effect at level 1-4 (good or bad). High-enchant books no longer roll
single-level enchantments such as Mending, Infinity or Silk Touch, and high-enchant gear
keeps those enchantments at level 1. Existing listings with impossible single-level
enchantment levels are removed and returned to their owner's collection box.

## Anvil bypass
The anvil ignores vanilla level, conflict and repair-cost limits. A level 10-15
enchanted book can be applied to any item. Combining two
equal enchantment levels raises the result by one level even past the vanilla maximum
(Protection 4 + Protection 4 = Protection 5), while different levels keep the higher
level. Conflicting enchantments can be combined freely, so a bow can have Infinity and
Mending, and a sword can have Sharpness and Smite at the same time. Taking the
result does not charge XP.

The Keep Inventory Helmet is much rarer than other relics (`auction.ai.keep-inventory-helmet-chance`,
2% by default) and costs hundreds of millions. Only one can be listed by any seller at a
time. While worn it keeps the player's entire inventory on death, but experience is still
lost normally. Each respawn costs 25% of the helmet's maximum durability; it shatters
when that would leave less than 25% durability remaining.

## Permissions
`economy.bal`, `economy.bal.others`, `economy.pay`, `economy.shop`, `economy.scoreboard`,
`economy.auction`, `economy.mystery` (all default true); `economy.admin` (default op):
`/eco`, `/fine`, `/sellaxe`, admin `/pay` grants (including self-payments) that ignore
the payer's balance. Shop, auction and mystery purchases still use normal prices and
balance checks for operators.
