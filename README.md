# Economy (Paper 1.21.x)

Server-side economy plugin. No client mod needed.

## Features
- Balances saved per-UUID in `plugins/Economy/balances.yml` (autosave + on shutdown)
- `/shop` GUI (categories), `/buy` catalog + search, quantity picker
- Sell chest (`/sell`): drop items in, close, get paid; unsellable items are returned
- `/sell hand | all | <item> [amount|all]`
- Auction house (`/ah`, `/ah sell <price>`, `/ah collect`) with expiry and sale tax
- Sell Axe: blocks broken with it are sold automatically (`/sellaxe`)
- Balance sidebar (`/ecosb`, remembered per player), `/baltop`
- `/pay`, `/fine` (can cause debt), `/eco give|take|set|reset|reload`
- Amounts accept `1500`, `1,500`, `2.5k`, `1m`, `0.5b`

## Files created in plugins/Economy/
`config.yml`, `prices.tsv` (editable, `/eco reload`), `balances.yml`, `auctions.yml`

## Build
`./gradlew build` -> `build/libs/Economy-1.0.0.jar` (Java 21; API jars are in `libs/`).

GitHub Actions builds on every push/PR with `.github/workflows/build.yml`. Pushes to `main`
and `v*` tags also create or update a GitHub release containing the plugin jar and
[`docs/PRICE.md`](docs/PRICE.md).

## Price guide
[`docs/PRICE.md`](docs/PRICE.md) lists every shop price with its real Minecraft item
texture. Regenerate it after changing `prices.tsv`:

```bash
.agent-work/tools/fetch_client_jar.sh 26.3
python3 .agent-work/tools/generate_price_docs.py \
  --jar .agent-work/tmp/mc/client.jar --version 26.3 \
  --prices src/main/resources/prices.tsv --docs docs
```

## Permissions
`economy.bal`, `economy.bal.others`, `economy.pay`, `economy.shop`, `economy.scoreboard`,
`economy.auction` (all default true); `economy.admin` (default op): `/eco`, `/fine`, `/sellaxe`.
