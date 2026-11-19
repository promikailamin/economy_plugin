# Economy Shop Price Guide

![Minecraft 26.3](https://img.shields.io/badge/Minecraft-26.3-3C8527?style=for-the-badge)
![Items](https://img.shields.io/badge/items-1657-2D6A4F?style=for-the-badge)
![Icons](https://img.shields.io/badge/icons-1657-1B4332?style=for-the-badge)

> Complete shop price list for the **Economy** Paper plugin, with the actual Minecraft item textures extracted from the client jar.

Every price is read directly from [`src/main/resources/prices.tsv`](../src/main/resources/prices.tsv). Amounts are in server currency and are **per single item**. The icon beside each entry is the real texture shipped in the Minecraft client, so the list is easy to scan in-game and on GitHub.

---

## At a Glance

| Metric | Value |
|:--|--:|
| Items documented | **1,657** |
| Buyable from `/shop` | **1,545** |
| Sellable to the server | **1,656** |
| Item icons extracted | **1,657 / 1,657** |
| Categories | **12** |
| Minecraft assets version | **26.3** |
| Cheapest sellable item | **Glass Pane** (3) |
| Most valuable sellable item | **Elytra** (2,500,000) |

## How to Read the Tables

| Column | Meaning |
|:--|:--|
| **Icon** | Texture extracted from `client.jar` (`assets/minecraft/textures/...`). |
| **Item** | Display name used by the shop. |
| **ID** | Minecraft item/material id, useful for `/buy <id>`, `/sell <id>` and `prices.tsv`. |
| **Buy** | Price the server charges per item. `-` means it cannot be bought. |
| **Sell** | Price the server pays per item. `-` means it cannot be sold. |
| **Trade** | Whether the item is buyable, sellable, both, or not traded. |
| **Notes** | Special handling such as auction-only or creative-only items. |

### Trade Rules

- The shop always pays the **sell** price and charges the **buy** price shown below.
- Creative-only items exist in the file for completeness but are ignored by the plugin economy.
- Auction-only and rare items cannot be bought from `/shop`; obtain them from the auction house or gameplay.
- `-` in the Buy column means the item is not purchasable; `-` in the Sell column means it is not sellable.

## Contents

- [Blocks](#blocks) - 970 items
- [Ores & Metals](#ores-metals) - 44 items
- [The Nether](#the-nether) - 45 items
- [The End](#the-end) - 35 items
- [Food & Farming](#food-farming) - 51 items
- [Redstone](#redstone) - 49 items
- [Tools & Weapons](#tools-weapons) - 42 items
- [Armor](#armor) - 29 items
- [Miscellaneous](#miscellaneous) - 280 items
- [Rare & Unique](#rare-unique) - 3 items
- [Auction Only](#auction-only) - 1 items
- [Creative Only](#creative-only) - 108 items

---

## Blocks

_970 items, 970 buyable, 970 sellable._

| Icon | Item | ID | Buy | Sell | Trade | Notes |
|:---:|:--|:--|--:|--:|:--:|:--|
| <img src="assets/icons/acacia_door.png" width="32" height="32" alt="Acacia Door" title="Acacia Door"> | **Acacia Door** | `acacia_door` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/acacia_fence.png" width="32" height="32" alt="Acacia Fence" title="Acacia Fence"> | **Acacia Fence** | `acacia_fence` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/acacia_fence_gate.png" width="32" height="32" alt="Acacia Fence Gate" title="Acacia Fence Gate"> | **Acacia Fence Gate** | `acacia_fence_gate` | 1,200 | 800 | Buy / Sell | - |
| <img src="assets/icons/acacia_hanging_sign.png" width="32" height="32" alt="Acacia Hanging Sign" title="Acacia Hanging Sign"> | **Acacia Hanging Sign** | `acacia_hanging_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/acacia_leaves.png" width="32" height="32" alt="Acacia Leaves" title="Acacia Leaves"> | **Acacia Leaves** | `acacia_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/acacia_log.png" width="32" height="32" alt="Acacia Log" title="Acacia Log"> | **Acacia Log** | `acacia_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/acacia_planks.png" width="32" height="32" alt="Acacia Planks" title="Acacia Planks"> | **Acacia Planks** | `acacia_planks` | 800 | 200 | Buy / Sell | - |
| <img src="assets/icons/acacia_sapling.png" width="32" height="32" alt="Acacia Sapling" title="Acacia Sapling"> | **Acacia Sapling** | `acacia_sapling` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/acacia_shelf.png" width="32" height="32" alt="Acacia Shelf" title="Acacia Shelf"> | **Acacia Shelf** | `acacia_shelf` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/acacia_sign.png" width="32" height="32" alt="Acacia Sign" title="Acacia Sign"> | **Acacia Sign** | `acacia_sign` | 2,000 | 400 | Buy / Sell | - |
| <img src="assets/icons/acacia_slab.png" width="32" height="32" alt="Acacia Slab" title="Acacia Slab"> | **Acacia Slab** | `acacia_slab` | 1,200 | 100 | Buy / Sell | - |
| <img src="assets/icons/acacia_stairs.png" width="32" height="32" alt="Acacia Stairs" title="Acacia Stairs"> | **Acacia Stairs** | `acacia_stairs` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/acacia_trapdoor.png" width="32" height="32" alt="Acacia Trapdoor" title="Acacia Trapdoor"> | **Acacia Trapdoor** | `acacia_trapdoor` | 2,400 | 600 | Buy / Sell | - |
| <img src="assets/icons/acacia_wood.png" width="32" height="32" alt="Acacia Wood" title="Acacia Wood"> | **Acacia Wood** | `acacia_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/allium.png" width="32" height="32" alt="Allium" title="Allium"> | **Allium** | `allium` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/amethyst_cluster.png" width="32" height="32" alt="Amethyst Cluster" title="Amethyst Cluster"> | **Amethyst Cluster** | `amethyst_cluster` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/andesite.png" width="32" height="32" alt="Andesite" title="Andesite"> | **Andesite** | `andesite` | 1,000 | 505 | Buy / Sell | - |
| <img src="assets/icons/andesite_slab.png" width="32" height="32" alt="Andesite Slab" title="Andesite Slab"> | **Andesite Slab** | `andesite_slab` | 1,200 | 252 | Buy / Sell | - |
| <img src="assets/icons/andesite_stairs.png" width="32" height="32" alt="Andesite Stairs" title="Andesite Stairs"> | **Andesite Stairs** | `andesite_stairs` | 1,200 | 505 | Buy / Sell | - |
| <img src="assets/icons/andesite_wall.png" width="32" height="32" alt="Andesite Wall" title="Andesite Wall"> | **Andesite Wall** | `andesite_wall` | 1,200 | 505 | Buy / Sell | - |
| <img src="assets/icons/anvil.png" width="32" height="32" alt="Anvil" title="Anvil"> | **Anvil** | `anvil` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/azalea.png" width="32" height="32" alt="Azalea" title="Azalea"> | **Azalea** | `azalea` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/azalea_leaves.png" width="32" height="32" alt="Azalea Leaves" title="Azalea Leaves"> | **Azalea Leaves** | `azalea_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/azure_bluet.png" width="32" height="32" alt="Azure Bluet" title="Azure Bluet"> | **Azure Bluet** | `azure_bluet` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/bamboo.png" width="32" height="32" alt="Bamboo" title="Bamboo"> | **Bamboo** | `bamboo` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/bamboo_door.png" width="32" height="32" alt="Bamboo Door" title="Bamboo Door"> | **Bamboo Door** | `bamboo_door` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/bamboo_fence.png" width="32" height="32" alt="Bamboo Fence" title="Bamboo Fence"> | **Bamboo Fence** | `bamboo_fence` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/bamboo_fence_gate.png" width="32" height="32" alt="Bamboo Fence Gate" title="Bamboo Fence Gate"> | **Bamboo Fence Gate** | `bamboo_fence_gate` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/bamboo_hanging_sign.png" width="32" height="32" alt="Bamboo Hanging Sign" title="Bamboo Hanging Sign"> | **Bamboo Hanging Sign** | `bamboo_hanging_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/bamboo_mosaic.png" width="32" height="32" alt="Bamboo Mosaic" title="Bamboo Mosaic"> | **Bamboo Mosaic** | `bamboo_mosaic` | 2,400 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/bamboo_mosaic_slab.png" width="32" height="32" alt="Bamboo Mosaic Slab" title="Bamboo Mosaic Slab"> | **Bamboo Mosaic Slab** | `bamboo_mosaic_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/bamboo_mosaic_stairs.png" width="32" height="32" alt="Bamboo Mosaic Stairs" title="Bamboo Mosaic Stairs"> | **Bamboo Mosaic Stairs** | `bamboo_mosaic_stairs` | 3,600 | 1,500 | Buy / Sell | - |
| <img src="assets/icons/bamboo_planks.png" width="32" height="32" alt="Bamboo Planks" title="Bamboo Planks"> | **Bamboo Planks** | `bamboo_planks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/bamboo_shelf.png" width="32" height="32" alt="Bamboo Shelf" title="Bamboo Shelf"> | **Bamboo Shelf** | `bamboo_shelf` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/bamboo_sign.png" width="32" height="32" alt="Bamboo Sign" title="Bamboo Sign"> | **Bamboo Sign** | `bamboo_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/bamboo_slab.png" width="32" height="32" alt="Bamboo Slab" title="Bamboo Slab"> | **Bamboo Slab** | `bamboo_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/bamboo_stairs.png" width="32" height="32" alt="Bamboo Stairs" title="Bamboo Stairs"> | **Bamboo Stairs** | `bamboo_stairs` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/bamboo_trapdoor.png" width="32" height="32" alt="Bamboo Trapdoor" title="Bamboo Trapdoor"> | **Bamboo Trapdoor** | `bamboo_trapdoor` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/barrel.png" width="32" height="32" alt="Barrel" title="Barrel"> | **Barrel** | `barrel` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/basalt.png" width="32" height="32" alt="Basalt" title="Basalt"> | **Basalt** | `basalt` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/beacon.png" width="32" height="32" alt="Beacon" title="Beacon"> | **Beacon** | `beacon` | 500,000 | 400,000 | Buy / Sell | - |
| <img src="assets/icons/bee_nest.png" width="32" height="32" alt="Bee Nest" title="Bee Nest"> | **Bee Nest** | `bee_nest` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/beehive.png" width="32" height="32" alt="Beehive" title="Beehive"> | **Beehive** | `beehive` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/bell.png" width="32" height="32" alt="Bell" title="Bell"> | **Bell** | `bell` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/big_dripleaf.png" width="32" height="32" alt="Big Dripleaf" title="Big Dripleaf"> | **Big Dripleaf** | `big_dripleaf` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/birch_door.png" width="32" height="32" alt="Birch Door" title="Birch Door"> | **Birch Door** | `birch_door` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/birch_fence.png" width="32" height="32" alt="Birch Fence" title="Birch Fence"> | **Birch Fence** | `birch_fence` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/birch_fence_gate.png" width="32" height="32" alt="Birch Fence Gate" title="Birch Fence Gate"> | **Birch Fence Gate** | `birch_fence_gate` | 1,200 | 800 | Buy / Sell | - |
| <img src="assets/icons/birch_hanging_sign.png" width="32" height="32" alt="Birch Hanging Sign" title="Birch Hanging Sign"> | **Birch Hanging Sign** | `birch_hanging_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/birch_leaves.png" width="32" height="32" alt="Birch Leaves" title="Birch Leaves"> | **Birch Leaves** | `birch_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/birch_log.png" width="32" height="32" alt="Birch Log" title="Birch Log"> | **Birch Log** | `birch_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/birch_planks.png" width="32" height="32" alt="Birch Planks" title="Birch Planks"> | **Birch Planks** | `birch_planks` | 800 | 200 | Buy / Sell | - |
| <img src="assets/icons/birch_sapling.png" width="32" height="32" alt="Birch Sapling" title="Birch Sapling"> | **Birch Sapling** | `birch_sapling` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/birch_shelf.png" width="32" height="32" alt="Birch Shelf" title="Birch Shelf"> | **Birch Shelf** | `birch_shelf` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/birch_sign.png" width="32" height="32" alt="Birch Sign" title="Birch Sign"> | **Birch Sign** | `birch_sign` | 2,000 | 400 | Buy / Sell | - |
| <img src="assets/icons/birch_slab.png" width="32" height="32" alt="Birch Slab" title="Birch Slab"> | **Birch Slab** | `birch_slab` | 1,200 | 100 | Buy / Sell | - |
| <img src="assets/icons/birch_stairs.png" width="32" height="32" alt="Birch Stairs" title="Birch Stairs"> | **Birch Stairs** | `birch_stairs` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/birch_trapdoor.png" width="32" height="32" alt="Birch Trapdoor" title="Birch Trapdoor"> | **Birch Trapdoor** | `birch_trapdoor` | 2,400 | 600 | Buy / Sell | - |
| <img src="assets/icons/birch_wood.png" width="32" height="32" alt="Birch Wood" title="Birch Wood"> | **Birch Wood** | `birch_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/black_banner.png" width="32" height="32" alt="Black Banner" title="Black Banner"> | **Black Banner** | `black_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/black_bed.png" width="32" height="32" alt="Black Bed" title="Black Bed"> | **Black Bed** | `black_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/black_candle.png" width="32" height="32" alt="Black Candle" title="Black Candle"> | **Black Candle** | `black_candle` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/black_carpet.png" width="32" height="32" alt="Black Carpet" title="Black Carpet"> | **Black Carpet** | `black_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/black_concrete.png" width="32" height="32" alt="Black Concrete" title="Black Concrete"> | **Black Concrete** | `black_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/black_concrete_powder.png" width="32" height="32" alt="Black Concrete Powder" title="Black Concrete Powder"> | **Black Concrete Powder** | `black_concrete_powder` | 15 | 11 | Buy / Sell | - |
| <img src="assets/icons/black_concrete_slab.png" width="32" height="32" alt="Black Concrete Slab" title="Black Concrete Slab"> | **Black Concrete Slab** | `black_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/black_concrete_stairs.png" width="32" height="32" alt="Black Concrete Stairs" title="Black Concrete Stairs"> | **Black Concrete Stairs** | `black_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/black_glazed_terracotta.png" width="32" height="32" alt="Black Glazed Terracotta" title="Black Glazed Terracotta"> | **Black Glazed Terracotta** | `black_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/black_stained_glass.png" width="32" height="32" alt="Black Stained Glass" title="Black Stained Glass"> | **Black Stained Glass** | `black_stained_glass` | 1,000 | 135 | Buy / Sell | - |
| <img src="assets/icons/black_stained_glass_pane.png" width="32" height="32" alt="Black Stained Glass Pane" title="Black Stained Glass Pane"> | **Black Stained Glass Pane** | `black_stained_glass_pane` | 1,000 | 50 | Buy / Sell | - |
| <img src="assets/icons/black_terracotta.png" width="32" height="32" alt="Black Terracotta" title="Black Terracotta"> | **Black Terracotta** | `black_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/black_wool.png" width="32" height="32" alt="Black Wool" title="Black Wool"> | **Black Wool** | `black_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/black_wool_slab.png" width="32" height="32" alt="Black Wool Slab" title="Black Wool Slab"> | **Black Wool Slab** | `black_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/black_wool_stairs.png" width="32" height="32" alt="Black Wool Stairs" title="Black Wool Stairs"> | **Black Wool Stairs** | `black_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/blackstone.png" width="32" height="32" alt="Blackstone" title="Blackstone"> | **Blackstone** | `blackstone` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/blackstone_slab.png" width="32" height="32" alt="Blackstone Slab" title="Blackstone Slab"> | **Blackstone Slab** | `blackstone_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/blackstone_stairs.png" width="32" height="32" alt="Blackstone Stairs" title="Blackstone Stairs"> | **Blackstone Stairs** | `blackstone_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/blackstone_wall.png" width="32" height="32" alt="Blackstone Wall" title="Blackstone Wall"> | **Blackstone Wall** | `blackstone_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/blast_furnace.png" width="32" height="32" alt="Blast Furnace" title="Blast Furnace"> | **Blast Furnace** | `blast_furnace` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/bamboo_block.png" width="32" height="32" alt="Block of Bamboo" title="Block of Bamboo"> | **Block of Bamboo** | `bamboo_block` | 9,000 | 7,200 | Buy / Sell | - |
| <img src="assets/icons/resin_block.png" width="32" height="32" alt="Block of Resin" title="Block of Resin"> | **Block of Resin** | `resin_block` | 10,800 | 8,600 | Buy / Sell | - |
| <img src="assets/icons/stripped_bamboo_block.png" width="32" height="32" alt="Block of Stripped Bamboo" title="Block of Stripped Bamboo"> | **Block of Stripped Bamboo** | `stripped_bamboo_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/blue_banner.png" width="32" height="32" alt="Blue Banner" title="Blue Banner"> | **Blue Banner** | `blue_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/blue_bed.png" width="32" height="32" alt="Blue Bed" title="Blue Bed"> | **Blue Bed** | `blue_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/blue_candle.png" width="32" height="32" alt="Blue Candle" title="Blue Candle"> | **Blue Candle** | `blue_candle` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/blue_carpet.png" width="32" height="32" alt="Blue Carpet" title="Blue Carpet"> | **Blue Carpet** | `blue_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/blue_concrete.png" width="32" height="32" alt="Blue Concrete" title="Blue Concrete"> | **Blue Concrete** | `blue_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/blue_concrete_powder.png" width="32" height="32" alt="Blue Concrete Powder" title="Blue Concrete Powder"> | **Blue Concrete Powder** | `blue_concrete_powder` | 15 | 11 | Buy / Sell | - |
| <img src="assets/icons/blue_concrete_slab.png" width="32" height="32" alt="Blue Concrete Slab" title="Blue Concrete Slab"> | **Blue Concrete Slab** | `blue_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/blue_concrete_stairs.png" width="32" height="32" alt="Blue Concrete Stairs" title="Blue Concrete Stairs"> | **Blue Concrete Stairs** | `blue_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/blue_glazed_terracotta.png" width="32" height="32" alt="Blue Glazed Terracotta" title="Blue Glazed Terracotta"> | **Blue Glazed Terracotta** | `blue_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/blue_ice.png" width="32" height="32" alt="Blue Ice" title="Blue Ice"> | **Blue Ice** | `blue_ice` | 81,000 | 64,800 | Buy / Sell | - |
| <img src="assets/icons/blue_orchid.png" width="32" height="32" alt="Blue Orchid" title="Blue Orchid"> | **Blue Orchid** | `blue_orchid` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/blue_stained_glass.png" width="32" height="32" alt="Blue Stained Glass" title="Blue Stained Glass"> | **Blue Stained Glass** | `blue_stained_glass` | 1,000 | 135 | Buy / Sell | - |
| <img src="assets/icons/blue_stained_glass_pane.png" width="32" height="32" alt="Blue Stained Glass Pane" title="Blue Stained Glass Pane"> | **Blue Stained Glass Pane** | `blue_stained_glass_pane` | 1,000 | 50 | Buy / Sell | - |
| <img src="assets/icons/blue_terracotta.png" width="32" height="32" alt="Blue Terracotta" title="Blue Terracotta"> | **Blue Terracotta** | `blue_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/blue_wool.png" width="32" height="32" alt="Blue Wool" title="Blue Wool"> | **Blue Wool** | `blue_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/blue_wool_slab.png" width="32" height="32" alt="Blue Wool Slab" title="Blue Wool Slab"> | **Blue Wool Slab** | `blue_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/blue_wool_stairs.png" width="32" height="32" alt="Blue Wool Stairs" title="Blue Wool Stairs"> | **Blue Wool Stairs** | `blue_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/bone_block.png" width="32" height="32" alt="Bone Block" title="Bone Block"> | **Bone Block** | `bone_block` | 22,500 | 7,500 | Buy / Sell | - |
| <img src="assets/icons/bookshelf.png" width="32" height="32" alt="Bookshelf" title="Bookshelf"> | **Bookshelf** | `bookshelf` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/brain_coral.png" width="32" height="32" alt="Brain Coral" title="Brain Coral"> | **Brain Coral** | `brain_coral` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/brain_coral_block.png" width="32" height="32" alt="Brain Coral Block" title="Brain Coral Block"> | **Brain Coral Block** | `brain_coral_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/brain_coral_fan.png" width="32" height="32" alt="Brain Coral Fan" title="Brain Coral Fan"> | **Brain Coral Fan** | `brain_coral_fan` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/brewing_stand.png" width="32" height="32" alt="Brewing Stand" title="Brewing Stand"> | **Brewing Stand** | `brewing_stand` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/brick_slab.png" width="32" height="32" alt="Brick Slab" title="Brick Slab"> | **Brick Slab** | `brick_slab` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/brick_stairs.png" width="32" height="32" alt="Brick Stairs" title="Brick Stairs"> | **Brick Stairs** | `brick_stairs` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/brick_wall.png" width="32" height="32" alt="Brick Wall" title="Brick Wall"> | **Brick Wall** | `brick_wall` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/bricks.png" width="32" height="32" alt="Bricks" title="Bricks"> | **Bricks** | `bricks` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/brown_banner.png" width="32" height="32" alt="Brown Banner" title="Brown Banner"> | **Brown Banner** | `brown_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/brown_bed.png" width="32" height="32" alt="Brown Bed" title="Brown Bed"> | **Brown Bed** | `brown_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/brown_candle.png" width="32" height="32" alt="Brown Candle" title="Brown Candle"> | **Brown Candle** | `brown_candle` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/brown_carpet.png" width="32" height="32" alt="Brown Carpet" title="Brown Carpet"> | **Brown Carpet** | `brown_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/brown_concrete.png" width="32" height="32" alt="Brown Concrete" title="Brown Concrete"> | **Brown Concrete** | `brown_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/brown_concrete_powder.png" width="32" height="32" alt="Brown Concrete Powder" title="Brown Concrete Powder"> | **Brown Concrete Powder** | `brown_concrete_powder` | 15 | 11 | Buy / Sell | - |
| <img src="assets/icons/brown_concrete_slab.png" width="32" height="32" alt="Brown Concrete Slab" title="Brown Concrete Slab"> | **Brown Concrete Slab** | `brown_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/brown_concrete_stairs.png" width="32" height="32" alt="Brown Concrete Stairs" title="Brown Concrete Stairs"> | **Brown Concrete Stairs** | `brown_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/brown_glazed_terracotta.png" width="32" height="32" alt="Brown Glazed Terracotta" title="Brown Glazed Terracotta"> | **Brown Glazed Terracotta** | `brown_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/brown_mushroom.png" width="32" height="32" alt="Brown Mushroom" title="Brown Mushroom"> | **Brown Mushroom** | `brown_mushroom` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/brown_mushroom_block.png" width="32" height="32" alt="Brown Mushroom Block" title="Brown Mushroom Block"> | **Brown Mushroom Block** | `brown_mushroom_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/brown_stained_glass.png" width="32" height="32" alt="Brown Stained Glass" title="Brown Stained Glass"> | **Brown Stained Glass** | `brown_stained_glass` | 1,000 | 135 | Buy / Sell | - |
| <img src="assets/icons/brown_stained_glass_pane.png" width="32" height="32" alt="Brown Stained Glass Pane" title="Brown Stained Glass Pane"> | **Brown Stained Glass Pane** | `brown_stained_glass_pane` | 1,000 | 50 | Buy / Sell | - |
| <img src="assets/icons/brown_terracotta.png" width="32" height="32" alt="Brown Terracotta" title="Brown Terracotta"> | **Brown Terracotta** | `brown_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/brown_wool.png" width="32" height="32" alt="Brown Wool" title="Brown Wool"> | **Brown Wool** | `brown_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/brown_wool_slab.png" width="32" height="32" alt="Brown Wool Slab" title="Brown Wool Slab"> | **Brown Wool Slab** | `brown_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/brown_wool_stairs.png" width="32" height="32" alt="Brown Wool Stairs" title="Brown Wool Stairs"> | **Brown Wool Stairs** | `brown_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/bubble_coral.png" width="32" height="32" alt="Bubble Coral" title="Bubble Coral"> | **Bubble Coral** | `bubble_coral` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/bubble_coral_block.png" width="32" height="32" alt="Bubble Coral Block" title="Bubble Coral Block"> | **Bubble Coral Block** | `bubble_coral_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/bubble_coral_fan.png" width="32" height="32" alt="Bubble Coral Fan" title="Bubble Coral Fan"> | **Bubble Coral Fan** | `bubble_coral_fan` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/bush.png" width="32" height="32" alt="Bush" title="Bush"> | **Bush** | `bush` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cactus.png" width="32" height="32" alt="Cactus" title="Cactus"> | **Cactus** | `cactus` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cactus_flower.png" width="32" height="32" alt="Cactus Flower" title="Cactus Flower"> | **Cactus Flower** | `cactus_flower` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/calcite.png" width="32" height="32" alt="Calcite" title="Calcite"> | **Calcite** | `calcite` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/calibrated_sculk_sensor.png" width="32" height="32" alt="Calibrated Sculk Sensor" title="Calibrated Sculk Sensor"> | **Calibrated Sculk Sensor** | `calibrated_sculk_sensor` | 20,000 | 10,000 | Buy / Sell | - |
| <img src="assets/icons/campfire.png" width="32" height="32" alt="Campfire" title="Campfire"> | **Campfire** | `campfire` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/candle.png" width="32" height="32" alt="Candle" title="Candle"> | **Candle** | `candle` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cartography_table.png" width="32" height="32" alt="Cartography Table" title="Cartography Table"> | **Cartography Table** | `cartography_table` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/carved_pumpkin.png" width="32" height="32" alt="Carved Pumpkin" title="Carved Pumpkin"> | **Carved Pumpkin** | `carved_pumpkin` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cauldron.png" width="32" height="32" alt="Cauldron" title="Cauldron"> | **Cauldron** | `cauldron` | 28,000 | 22,400 | Buy / Sell | - |
| <img src="assets/icons/cherry_door.png" width="32" height="32" alt="Cherry Door" title="Cherry Door"> | **Cherry Door** | `cherry_door` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/cherry_fence.png" width="32" height="32" alt="Cherry Fence" title="Cherry Fence"> | **Cherry Fence** | `cherry_fence` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/cherry_fence_gate.png" width="32" height="32" alt="Cherry Fence Gate" title="Cherry Fence Gate"> | **Cherry Fence Gate** | `cherry_fence_gate` | 1,200 | 800 | Buy / Sell | - |
| <img src="assets/icons/cherry_hanging_sign.png" width="32" height="32" alt="Cherry Hanging Sign" title="Cherry Hanging Sign"> | **Cherry Hanging Sign** | `cherry_hanging_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/cherry_leaves.png" width="32" height="32" alt="Cherry Leaves" title="Cherry Leaves"> | **Cherry Leaves** | `cherry_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/cherry_log.png" width="32" height="32" alt="Cherry Log" title="Cherry Log"> | **Cherry Log** | `cherry_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/cherry_planks.png" width="32" height="32" alt="Cherry Planks" title="Cherry Planks"> | **Cherry Planks** | `cherry_planks` | 800 | 200 | Buy / Sell | - |
| <img src="assets/icons/cherry_sapling.png" width="32" height="32" alt="Cherry Sapling" title="Cherry Sapling"> | **Cherry Sapling** | `cherry_sapling` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/cherry_shelf.png" width="32" height="32" alt="Cherry Shelf" title="Cherry Shelf"> | **Cherry Shelf** | `cherry_shelf` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/cherry_sign.png" width="32" height="32" alt="Cherry Sign" title="Cherry Sign"> | **Cherry Sign** | `cherry_sign` | 2,000 | 400 | Buy / Sell | - |
| <img src="assets/icons/cherry_slab.png" width="32" height="32" alt="Cherry Slab" title="Cherry Slab"> | **Cherry Slab** | `cherry_slab` | 1,200 | 100 | Buy / Sell | - |
| <img src="assets/icons/cherry_stairs.png" width="32" height="32" alt="Cherry Stairs" title="Cherry Stairs"> | **Cherry Stairs** | `cherry_stairs` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/cherry_trapdoor.png" width="32" height="32" alt="Cherry Trapdoor" title="Cherry Trapdoor"> | **Cherry Trapdoor** | `cherry_trapdoor` | 2,400 | 600 | Buy / Sell | - |
| <img src="assets/icons/cherry_wood.png" width="32" height="32" alt="Cherry Wood" title="Cherry Wood"> | **Cherry Wood** | `cherry_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/chest.png" width="32" height="32" alt="Chest" title="Chest"> | **Chest** | `chest` | 6,400 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/chipped_anvil.png" width="32" height="32" alt="Chipped Anvil" title="Chipped Anvil"> | **Chipped Anvil** | `chipped_anvil` | 30,000 | 24,000 | Buy / Sell | - |
| <img src="assets/icons/chiseled_bookshelf.png" width="32" height="32" alt="Chiseled Bookshelf" title="Chiseled Bookshelf"> | **Chiseled Bookshelf** | `chiseled_bookshelf` | 10,000 | 1,500 | Buy / Sell | - |
| <img src="assets/icons/chiseled_cinnabar.png" width="32" height="32" alt="Chiseled Cinnabar" title="Chiseled Cinnabar"> | **Chiseled Cinnabar** | `chiseled_cinnabar` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/chiseled_copper.png" width="32" height="32" alt="Chiseled Copper" title="Chiseled Copper"> | **Chiseled Copper** | `chiseled_copper` | 5,000 | 3,300 | Buy / Sell | - |
| <img src="assets/icons/chiseled_deepslate.png" width="32" height="32" alt="Chiseled Deepslate" title="Chiseled Deepslate"> | **Chiseled Deepslate** | `chiseled_deepslate` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/chiseled_polished_blackstone.png" width="32" height="32" alt="Chiseled Polished Blackstone" title="Chiseled Polished Blackstone"> | **Chiseled Polished Blackstone** | `chiseled_polished_blackstone` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/chiseled_red_sandstone.png" width="32" height="32" alt="Chiseled Red Sandstone" title="Chiseled Red Sandstone"> | **Chiseled Red Sandstone** | `chiseled_red_sandstone` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/chiseled_resin_bricks.png" width="32" height="32" alt="Chiseled Resin Bricks" title="Chiseled Resin Bricks"> | **Chiseled Resin Bricks** | `chiseled_resin_bricks` | 4,800 | 3,800 | Buy / Sell | - |
| <img src="assets/icons/chiseled_sandstone.png" width="32" height="32" alt="Chiseled Sandstone" title="Chiseled Sandstone"> | **Chiseled Sandstone** | `chiseled_sandstone` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/chiseled_stone_bricks.png" width="32" height="32" alt="Chiseled Stone Bricks" title="Chiseled Stone Bricks"> | **Chiseled Stone Bricks** | `chiseled_stone_bricks` | 1,000 | 10 | Buy / Sell | - |
| <img src="assets/icons/chiseled_sulfur.png" width="32" height="32" alt="Chiseled Sulfur" title="Chiseled Sulfur"> | **Chiseled Sulfur** | `chiseled_sulfur` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/chiseled_tuff.png" width="32" height="32" alt="Chiseled Tuff" title="Chiseled Tuff"> | **Chiseled Tuff** | `chiseled_tuff` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/chiseled_tuff_bricks.png" width="32" height="32" alt="Chiseled Tuff Bricks" title="Chiseled Tuff Bricks"> | **Chiseled Tuff Bricks** | `chiseled_tuff_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cinnabar.png" width="32" height="32" alt="Cinnabar" title="Cinnabar"> | **Cinnabar** | `cinnabar` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cinnabar_brick_slab.png" width="32" height="32" alt="Cinnabar Brick Slab" title="Cinnabar Brick Slab"> | **Cinnabar Brick Slab** | `cinnabar_brick_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/cinnabar_brick_stairs.png" width="32" height="32" alt="Cinnabar Brick Stairs" title="Cinnabar Brick Stairs"> | **Cinnabar Brick Stairs** | `cinnabar_brick_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/cinnabar_brick_wall.png" width="32" height="32" alt="Cinnabar Brick Wall" title="Cinnabar Brick Wall"> | **Cinnabar Brick Wall** | `cinnabar_brick_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/cinnabar_bricks.png" width="32" height="32" alt="Cinnabar Bricks" title="Cinnabar Bricks"> | **Cinnabar Bricks** | `cinnabar_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cinnabar_slab.png" width="32" height="32" alt="Cinnabar Slab" title="Cinnabar Slab"> | **Cinnabar Slab** | `cinnabar_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/cinnabar_stairs.png" width="32" height="32" alt="Cinnabar Stairs" title="Cinnabar Stairs"> | **Cinnabar Stairs** | `cinnabar_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/cinnabar_wall.png" width="32" height="32" alt="Cinnabar Wall" title="Cinnabar Wall"> | **Cinnabar Wall** | `cinnabar_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/clay.png" width="32" height="32" alt="Clay" title="Clay"> | **Clay** | `clay` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/closed_eyeblossom.png" width="32" height="32" alt="Closed Eyeblossom" title="Closed Eyeblossom"> | **Closed Eyeblossom** | `closed_eyeblossom` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/coarse_dirt.png" width="32" height="32" alt="Coarse Dirt" title="Coarse Dirt"> | **Coarse Dirt** | `coarse_dirt` | 1,000 | 505 | Buy / Sell | - |
| <img src="assets/icons/cobbled_deepslate.png" width="32" height="32" alt="Cobbled Deepslate" title="Cobbled Deepslate"> | **Cobbled Deepslate** | `cobbled_deepslate` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cobbled_deepslate_slab.png" width="32" height="32" alt="Cobbled Deepslate Slab" title="Cobbled Deepslate Slab"> | **Cobbled Deepslate Slab** | `cobbled_deepslate_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/cobbled_deepslate_stairs.png" width="32" height="32" alt="Cobbled Deepslate Stairs" title="Cobbled Deepslate Stairs"> | **Cobbled Deepslate Stairs** | `cobbled_deepslate_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/cobbled_deepslate_wall.png" width="32" height="32" alt="Cobbled Deepslate Wall" title="Cobbled Deepslate Wall"> | **Cobbled Deepslate Wall** | `cobbled_deepslate_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/cobblestone.png" width="32" height="32" alt="Cobblestone" title="Cobblestone"> | **Cobblestone** | `cobblestone` | 10 | 8 | Buy / Sell | - |
| <img src="assets/icons/cobblestone_slab.png" width="32" height="32" alt="Cobblestone Slab" title="Cobblestone Slab"> | **Cobblestone Slab** | `cobblestone_slab` | 12 | 5 | Buy / Sell | - |
| <img src="assets/icons/cobblestone_stairs.png" width="32" height="32" alt="Cobblestone Stairs" title="Cobblestone Stairs"> | **Cobblestone Stairs** | `cobblestone_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/cobblestone_wall.png" width="32" height="32" alt="Cobblestone Wall" title="Cobblestone Wall"> | **Cobblestone Wall** | `cobblestone_wall` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/cobweb.png" width="32" height="32" alt="Cobweb" title="Cobweb"> | **Cobweb** | `cobweb` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cocoa_beans.png" width="32" height="32" alt="Cocoa Beans" title="Cocoa Beans"> | **Cocoa Beans** | `cocoa_beans` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/composter.png" width="32" height="32" alt="Composter" title="Composter"> | **Composter** | `composter` | 8,400 | 700 | Buy / Sell | - |
| <img src="assets/icons/conduit.png" width="32" height="32" alt="Conduit" title="Conduit"> | **Conduit** | `conduit` | 300,000 | 240,000 | Buy / Sell | - |
| <img src="assets/icons/copper_bars.png" width="32" height="32" alt="Copper Bars" title="Copper Bars"> | **Copper Bars** | `copper_bars` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/copper_bulb.png" width="32" height="32" alt="Copper Bulb" title="Copper Bulb"> | **Copper Bulb** | `copper_bulb` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/copper_chain.png" width="32" height="32" alt="Copper Chain" title="Copper Chain"> | **Copper Chain** | `copper_chain` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/copper_chest.png" width="32" height="32" alt="Copper Chest" title="Copper Chest"> | **Copper Chest** | `copper_chest` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/copper_door.png" width="32" height="32" alt="Copper Door" title="Copper Door"> | **Copper Door** | `copper_door` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/copper_golem_statue.png" width="32" height="32" alt="Copper Golem Statue" title="Copper Golem Statue"> | **Copper Golem Statue** | `copper_golem_statue` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/copper_grate.png" width="32" height="32" alt="Copper Grate" title="Copper Grate"> | **Copper Grate** | `copper_grate` | 3,400 | 2,700 | Buy / Sell | - |
| <img src="assets/icons/copper_lantern.png" width="32" height="32" alt="Copper Lantern" title="Copper Lantern"> | **Copper Lantern** | `copper_lantern` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/copper_torch.png" width="32" height="32" alt="Copper Torch" title="Copper Torch"> | **Copper Torch** | `copper_torch` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/copper_trapdoor.png" width="32" height="32" alt="Copper Trapdoor" title="Copper Trapdoor"> | **Copper Trapdoor** | `copper_trapdoor` | 4,500 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/cornflower.png" width="32" height="32" alt="Cornflower" title="Cornflower"> | **Cornflower** | `cornflower` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cracked_deepslate_bricks.png" width="32" height="32" alt="Cracked Deepslate Bricks" title="Cracked Deepslate Bricks"> | **Cracked Deepslate Bricks** | `cracked_deepslate_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cracked_deepslate_tiles.png" width="32" height="32" alt="Cracked Deepslate Tiles" title="Cracked Deepslate Tiles"> | **Cracked Deepslate Tiles** | `cracked_deepslate_tiles` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cracked_polished_blackstone_bricks.png" width="32" height="32" alt="Cracked Polished Blackstone Bricks" title="Cracked Polished Blackstone Bricks"> | **Cracked Polished Blackstone Bricks** | `cracked_polished_blackstone_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cracked_stone_bricks.png" width="32" height="32" alt="Cracked Stone Bricks" title="Cracked Stone Bricks"> | **Cracked Stone Bricks** | `cracked_stone_bricks` | 1,000 | 10 | Buy / Sell | - |
| <img src="assets/icons/crafter.png" width="32" height="32" alt="Crafter" title="Crafter"> | **Crafter** | `crafter` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/crafting_table.png" width="32" height="32" alt="Crafting Table" title="Crafting Table"> | **Crafting Table** | `crafting_table` | 3,200 | 800 | Buy / Sell | - |
| <img src="assets/icons/creaking_heart.png" width="32" height="32" alt="Creaking Heart" title="Creaking Heart"> | **Creaking Heart** | `creaking_heart` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/creeper_head.png" width="32" height="32" alt="Creeper Head" title="Creeper Head"> | **Creeper Head** | `creeper_head` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/crimson_door.png" width="32" height="32" alt="Crimson Door" title="Crimson Door"> | **Crimson Door** | `crimson_door` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/crimson_fence.png" width="32" height="32" alt="Crimson Fence" title="Crimson Fence"> | **Crimson Fence** | `crimson_fence` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/crimson_fence_gate.png" width="32" height="32" alt="Crimson Fence Gate" title="Crimson Fence Gate"> | **Crimson Fence Gate** | `crimson_fence_gate` | 1,200 | 800 | Buy / Sell | - |
| <img src="assets/icons/crimson_fungus.png" width="32" height="32" alt="Crimson Fungus" title="Crimson Fungus"> | **Crimson Fungus** | `crimson_fungus` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/crimson_hanging_sign.png" width="32" height="32" alt="Crimson Hanging Sign" title="Crimson Hanging Sign"> | **Crimson Hanging Sign** | `crimson_hanging_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/crimson_hyphae.png" width="32" height="32" alt="Crimson Hyphae" title="Crimson Hyphae"> | **Crimson Hyphae** | `crimson_hyphae` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/crimson_nylium.png" width="32" height="32" alt="Crimson Nylium" title="Crimson Nylium"> | **Crimson Nylium** | `crimson_nylium` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/crimson_planks.png" width="32" height="32" alt="Crimson Planks" title="Crimson Planks"> | **Crimson Planks** | `crimson_planks` | 800 | 200 | Buy / Sell | - |
| <img src="assets/icons/crimson_roots.png" width="32" height="32" alt="Crimson Roots" title="Crimson Roots"> | **Crimson Roots** | `crimson_roots` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/crimson_shelf.png" width="32" height="32" alt="Crimson Shelf" title="Crimson Shelf"> | **Crimson Shelf** | `crimson_shelf` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/crimson_sign.png" width="32" height="32" alt="Crimson Sign" title="Crimson Sign"> | **Crimson Sign** | `crimson_sign` | 2,000 | 400 | Buy / Sell | - |
| <img src="assets/icons/crimson_slab.png" width="32" height="32" alt="Crimson Slab" title="Crimson Slab"> | **Crimson Slab** | `crimson_slab` | 1,200 | 100 | Buy / Sell | - |
| <img src="assets/icons/crimson_stairs.png" width="32" height="32" alt="Crimson Stairs" title="Crimson Stairs"> | **Crimson Stairs** | `crimson_stairs` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/crimson_stem.png" width="32" height="32" alt="Crimson Stem" title="Crimson Stem"> | **Crimson Stem** | `crimson_stem` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/crimson_trapdoor.png" width="32" height="32" alt="Crimson Trapdoor" title="Crimson Trapdoor"> | **Crimson Trapdoor** | `crimson_trapdoor` | 2,400 | 600 | Buy / Sell | - |
| <img src="assets/icons/crying_obsidian.png" width="32" height="32" alt="Crying Obsidian" title="Crying Obsidian"> | **Crying Obsidian** | `crying_obsidian` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cut_copper.png" width="32" height="32" alt="Cut Copper" title="Cut Copper"> | **Cut Copper** | `cut_copper` | 5,000 | 3,300 | Buy / Sell | - |
| <img src="assets/icons/cut_copper_slab.png" width="32" height="32" alt="Cut Copper Slab" title="Cut Copper Slab"> | **Cut Copper Slab** | `cut_copper_slab` | 1,700 | 1,300 | Buy / Sell | - |
| <img src="assets/icons/cut_copper_stairs.png" width="32" height="32" alt="Cut Copper Stairs" title="Cut Copper Stairs"> | **Cut Copper Stairs** | `cut_copper_stairs` | 3,400 | 2,700 | Buy / Sell | - |
| <img src="assets/icons/cut_red_sandstone.png" width="32" height="32" alt="Cut Red Sandstone" title="Cut Red Sandstone"> | **Cut Red Sandstone** | `cut_red_sandstone` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/cut_red_sandstone_slab.png" width="32" height="32" alt="Cut Red Sandstone Slab" title="Cut Red Sandstone Slab"> | **Cut Red Sandstone Slab** | `cut_red_sandstone_slab` | 20 | 16 | Buy / Sell | - |
| <img src="assets/icons/cut_sandstone.png" width="32" height="32" alt="Cut Sandstone" title="Cut Sandstone"> | **Cut Sandstone** | `cut_sandstone` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/cut_sandstone_slab.png" width="32" height="32" alt="Cut Sandstone Slab" title="Cut Sandstone Slab"> | **Cut Sandstone Slab** | `cut_sandstone_slab` | 20 | 16 | Buy / Sell | - |
| <img src="assets/icons/cyan_banner.png" width="32" height="32" alt="Cyan Banner" title="Cyan Banner"> | **Cyan Banner** | `cyan_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/cyan_bed.png" width="32" height="32" alt="Cyan Bed" title="Cyan Bed"> | **Cyan Bed** | `cyan_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/cyan_candle.png" width="32" height="32" alt="Cyan Candle" title="Cyan Candle"> | **Cyan Candle** | `cyan_candle` | 2,000 | 1,500 | Buy / Sell | - |
| <img src="assets/icons/cyan_carpet.png" width="32" height="32" alt="Cyan Carpet" title="Cyan Carpet"> | **Cyan Carpet** | `cyan_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/cyan_concrete.png" width="32" height="32" alt="Cyan Concrete" title="Cyan Concrete"> | **Cyan Concrete** | `cyan_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/cyan_concrete_powder.png" width="32" height="32" alt="Cyan Concrete Powder" title="Cyan Concrete Powder"> | **Cyan Concrete Powder** | `cyan_concrete_powder` | 15 | 10 | Buy / Sell | - |
| <img src="assets/icons/cyan_concrete_slab.png" width="32" height="32" alt="Cyan Concrete Slab" title="Cyan Concrete Slab"> | **Cyan Concrete Slab** | `cyan_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/cyan_concrete_stairs.png" width="32" height="32" alt="Cyan Concrete Stairs" title="Cyan Concrete Stairs"> | **Cyan Concrete Stairs** | `cyan_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/cyan_glazed_terracotta.png" width="32" height="32" alt="Cyan Glazed Terracotta" title="Cyan Glazed Terracotta"> | **Cyan Glazed Terracotta** | `cyan_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/cyan_stained_glass.png" width="32" height="32" alt="Cyan Stained Glass" title="Cyan Stained Glass"> | **Cyan Stained Glass** | `cyan_stained_glass` | 1,000 | 72 | Buy / Sell | - |
| <img src="assets/icons/cyan_stained_glass_pane.png" width="32" height="32" alt="Cyan Stained Glass Pane" title="Cyan Stained Glass Pane"> | **Cyan Stained Glass Pane** | `cyan_stained_glass_pane` | 1,000 | 27 | Buy / Sell | - |
| <img src="assets/icons/cyan_terracotta.png" width="32" height="32" alt="Cyan Terracotta" title="Cyan Terracotta"> | **Cyan Terracotta** | `cyan_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/cyan_wool.png" width="32" height="32" alt="Cyan Wool" title="Cyan Wool"> | **Cyan Wool** | `cyan_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cyan_wool_slab.png" width="32" height="32" alt="Cyan Wool Slab" title="Cyan Wool Slab"> | **Cyan Wool Slab** | `cyan_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/cyan_wool_stairs.png" width="32" height="32" alt="Cyan Wool Stairs" title="Cyan Wool Stairs"> | **Cyan Wool Stairs** | `cyan_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/damaged_anvil.png" width="32" height="32" alt="Damaged Anvil" title="Damaged Anvil"> | **Damaged Anvil** | `damaged_anvil` | 30,000 | 24,000 | Buy / Sell | - |
| <img src="assets/icons/dandelion.png" width="32" height="32" alt="Dandelion" title="Dandelion"> | **Dandelion** | `dandelion` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_door.png" width="32" height="32" alt="Dark Oak Door" title="Dark Oak Door"> | **Dark Oak Door** | `dark_oak_door` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_fence.png" width="32" height="32" alt="Dark Oak Fence" title="Dark Oak Fence"> | **Dark Oak Fence** | `dark_oak_fence` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_fence_gate.png" width="32" height="32" alt="Dark Oak Fence Gate" title="Dark Oak Fence Gate"> | **Dark Oak Fence Gate** | `dark_oak_fence_gate` | 1,200 | 800 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_hanging_sign.png" width="32" height="32" alt="Dark Oak Hanging Sign" title="Dark Oak Hanging Sign"> | **Dark Oak Hanging Sign** | `dark_oak_hanging_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_leaves.png" width="32" height="32" alt="Dark Oak Leaves" title="Dark Oak Leaves"> | **Dark Oak Leaves** | `dark_oak_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_log.png" width="32" height="32" alt="Dark Oak Log" title="Dark Oak Log"> | **Dark Oak Log** | `dark_oak_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_planks.png" width="32" height="32" alt="Dark Oak Planks" title="Dark Oak Planks"> | **Dark Oak Planks** | `dark_oak_planks` | 800 | 200 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_sapling.png" width="32" height="32" alt="Dark Oak Sapling" title="Dark Oak Sapling"> | **Dark Oak Sapling** | `dark_oak_sapling` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_shelf.png" width="32" height="32" alt="Dark Oak Shelf" title="Dark Oak Shelf"> | **Dark Oak Shelf** | `dark_oak_shelf` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_sign.png" width="32" height="32" alt="Dark Oak Sign" title="Dark Oak Sign"> | **Dark Oak Sign** | `dark_oak_sign` | 2,000 | 400 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_slab.png" width="32" height="32" alt="Dark Oak Slab" title="Dark Oak Slab"> | **Dark Oak Slab** | `dark_oak_slab` | 1,200 | 100 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_stairs.png" width="32" height="32" alt="Dark Oak Stairs" title="Dark Oak Stairs"> | **Dark Oak Stairs** | `dark_oak_stairs` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_trapdoor.png" width="32" height="32" alt="Dark Oak Trapdoor" title="Dark Oak Trapdoor"> | **Dark Oak Trapdoor** | `dark_oak_trapdoor` | 2,400 | 600 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_wood.png" width="32" height="32" alt="Dark Oak Wood" title="Dark Oak Wood"> | **Dark Oak Wood** | `dark_oak_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/dark_prismarine.png" width="32" height="32" alt="Dark Prismarine" title="Dark Prismarine"> | **Dark Prismarine** | `dark_prismarine` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/dark_prismarine_slab.png" width="32" height="32" alt="Dark Prismarine Slab" title="Dark Prismarine Slab"> | **Dark Prismarine Slab** | `dark_prismarine_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/dark_prismarine_stairs.png" width="32" height="32" alt="Dark Prismarine Stairs" title="Dark Prismarine Stairs"> | **Dark Prismarine Stairs** | `dark_prismarine_stairs` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/daylight_detector.png" width="32" height="32" alt="Daylight Detector" title="Daylight Detector"> | **Daylight Detector** | `daylight_detector` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/dead_brain_coral.png" width="32" height="32" alt="Dead Brain Coral" title="Dead Brain Coral"> | **Dead Brain Coral** | `dead_brain_coral` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/dead_brain_coral_block.png" width="32" height="32" alt="Dead Brain Coral Block" title="Dead Brain Coral Block"> | **Dead Brain Coral Block** | `dead_brain_coral_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/dead_brain_coral_fan.png" width="32" height="32" alt="Dead Brain Coral Fan" title="Dead Brain Coral Fan"> | **Dead Brain Coral Fan** | `dead_brain_coral_fan` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/dead_bubble_coral.png" width="32" height="32" alt="Dead Bubble Coral" title="Dead Bubble Coral"> | **Dead Bubble Coral** | `dead_bubble_coral` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/dead_bubble_coral_block.png" width="32" height="32" alt="Dead Bubble Coral Block" title="Dead Bubble Coral Block"> | **Dead Bubble Coral Block** | `dead_bubble_coral_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/dead_bubble_coral_fan.png" width="32" height="32" alt="Dead Bubble Coral Fan" title="Dead Bubble Coral Fan"> | **Dead Bubble Coral Fan** | `dead_bubble_coral_fan` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/dead_bush.png" width="32" height="32" alt="Dead Bush" title="Dead Bush"> | **Dead Bush** | `dead_bush` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/dead_fire_coral.png" width="32" height="32" alt="Dead Fire Coral" title="Dead Fire Coral"> | **Dead Fire Coral** | `dead_fire_coral` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/dead_fire_coral_block.png" width="32" height="32" alt="Dead Fire Coral Block" title="Dead Fire Coral Block"> | **Dead Fire Coral Block** | `dead_fire_coral_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/dead_fire_coral_fan.png" width="32" height="32" alt="Dead Fire Coral Fan" title="Dead Fire Coral Fan"> | **Dead Fire Coral Fan** | `dead_fire_coral_fan` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/dead_horn_coral.png" width="32" height="32" alt="Dead Horn Coral" title="Dead Horn Coral"> | **Dead Horn Coral** | `dead_horn_coral` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/dead_horn_coral_block.png" width="32" height="32" alt="Dead Horn Coral Block" title="Dead Horn Coral Block"> | **Dead Horn Coral Block** | `dead_horn_coral_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/dead_horn_coral_fan.png" width="32" height="32" alt="Dead Horn Coral Fan" title="Dead Horn Coral Fan"> | **Dead Horn Coral Fan** | `dead_horn_coral_fan` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/dead_tube_coral.png" width="32" height="32" alt="Dead Tube Coral" title="Dead Tube Coral"> | **Dead Tube Coral** | `dead_tube_coral` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/dead_tube_coral_block.png" width="32" height="32" alt="Dead Tube Coral Block" title="Dead Tube Coral Block"> | **Dead Tube Coral Block** | `dead_tube_coral_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/dead_tube_coral_fan.png" width="32" height="32" alt="Dead Tube Coral Fan" title="Dead Tube Coral Fan"> | **Dead Tube Coral Fan** | `dead_tube_coral_fan` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/decorated_pot.png" width="32" height="32" alt="Decorated Pot" title="Decorated Pot"> | **Decorated Pot** | `decorated_pot` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/deepslate.png" width="32" height="32" alt="Deepslate" title="Deepslate"> | **Deepslate** | `deepslate` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/deepslate_brick_slab.png" width="32" height="32" alt="Deepslate Brick Slab" title="Deepslate Brick Slab"> | **Deepslate Brick Slab** | `deepslate_brick_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/deepslate_brick_stairs.png" width="32" height="32" alt="Deepslate Brick Stairs" title="Deepslate Brick Stairs"> | **Deepslate Brick Stairs** | `deepslate_brick_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/deepslate_brick_wall.png" width="32" height="32" alt="Deepslate Brick Wall" title="Deepslate Brick Wall"> | **Deepslate Brick Wall** | `deepslate_brick_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/deepslate_bricks.png" width="32" height="32" alt="Deepslate Bricks" title="Deepslate Bricks"> | **Deepslate Bricks** | `deepslate_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/deepslate_tile_slab.png" width="32" height="32" alt="Deepslate Tile Slab" title="Deepslate Tile Slab"> | **Deepslate Tile Slab** | `deepslate_tile_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/deepslate_tile_stairs.png" width="32" height="32" alt="Deepslate Tile Stairs" title="Deepslate Tile Stairs"> | **Deepslate Tile Stairs** | `deepslate_tile_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/deepslate_tile_wall.png" width="32" height="32" alt="Deepslate Tile Wall" title="Deepslate Tile Wall"> | **Deepslate Tile Wall** | `deepslate_tile_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/deepslate_tiles.png" width="32" height="32" alt="Deepslate Tiles" title="Deepslate Tiles"> | **Deepslate Tiles** | `deepslate_tiles` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/diorite.png" width="32" height="32" alt="Diorite" title="Diorite"> | **Diorite** | `diorite` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/diorite_slab.png" width="32" height="32" alt="Diorite Slab" title="Diorite Slab"> | **Diorite Slab** | `diorite_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/diorite_stairs.png" width="32" height="32" alt="Diorite Stairs" title="Diorite Stairs"> | **Diorite Stairs** | `diorite_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/diorite_wall.png" width="32" height="32" alt="Diorite Wall" title="Diorite Wall"> | **Diorite Wall** | `diorite_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/dirt.png" width="32" height="32" alt="Dirt" title="Dirt"> | **Dirt** | `dirt` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/dirt_path.png" width="32" height="32" alt="Dirt Path" title="Dirt Path"> | **Dirt Path** | `dirt_path` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/dripstone_block.png" width="32" height="32" alt="Dripstone Block" title="Dripstone Block"> | **Dripstone Block** | `dripstone_block` | 4,000 | 3,200 | Buy / Sell | - |
| <img src="assets/icons/enchanting_table.png" width="32" height="32" alt="Enchanting Table" title="Enchanting Table"> | **Enchanting Table** | `enchanting_table` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/ender_chest.png" width="32" height="32" alt="Ender Chest" title="Ender Chest"> | **Ender Chest** | `ender_chest` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/exposed_chiseled_copper.png" width="32" height="32" alt="Exposed Chiseled Copper" title="Exposed Chiseled Copper"> | **Exposed Chiseled Copper** | `exposed_chiseled_copper` | 5,000 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/exposed_copper.png" width="32" height="32" alt="Exposed Copper" title="Exposed Copper"> | **Exposed Copper** | `exposed_copper` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/exposed_copper_bars.png" width="32" height="32" alt="Exposed Copper Bars" title="Exposed Copper Bars"> | **Exposed Copper Bars** | `exposed_copper_bars` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/exposed_copper_bulb.png" width="32" height="32" alt="Exposed Copper Bulb" title="Exposed Copper Bulb"> | **Exposed Copper Bulb** | `exposed_copper_bulb` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/exposed_copper_chain.png" width="32" height="32" alt="Exposed Copper Chain" title="Exposed Copper Chain"> | **Exposed Copper Chain** | `exposed_copper_chain` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/exposed_copper_chest.png" width="32" height="32" alt="Exposed Copper Chest" title="Exposed Copper Chest"> | **Exposed Copper Chest** | `exposed_copper_chest` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/exposed_copper_door.png" width="32" height="32" alt="Exposed Copper Door" title="Exposed Copper Door"> | **Exposed Copper Door** | `exposed_copper_door` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/exposed_copper_golem_statue.png" width="32" height="32" alt="Exposed Copper Golem Statue" title="Exposed Copper Golem Statue"> | **Exposed Copper Golem Statue** | `exposed_copper_golem_statue` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/exposed_copper_grate.png" width="32" height="32" alt="Exposed Copper Grate" title="Exposed Copper Grate"> | **Exposed Copper Grate** | `exposed_copper_grate` | 1,300 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/exposed_copper_lantern.png" width="32" height="32" alt="Exposed Copper Lantern" title="Exposed Copper Lantern"> | **Exposed Copper Lantern** | `exposed_copper_lantern` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/exposed_copper_trapdoor.png" width="32" height="32" alt="Exposed Copper Trapdoor" title="Exposed Copper Trapdoor"> | **Exposed Copper Trapdoor** | `exposed_copper_trapdoor` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/exposed_cut_copper.png" width="32" height="32" alt="Exposed Cut Copper" title="Exposed Cut Copper"> | **Exposed Cut Copper** | `exposed_cut_copper` | 5,000 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/exposed_cut_copper_slab.png" width="32" height="32" alt="Exposed Cut Copper Slab" title="Exposed Cut Copper Slab"> | **Exposed Cut Copper Slab** | `exposed_cut_copper_slab` | 1,200 | 600 | Buy / Sell | - |
| <img src="assets/icons/exposed_cut_copper_stairs.png" width="32" height="32" alt="Exposed Cut Copper Stairs" title="Exposed Cut Copper Stairs"> | **Exposed Cut Copper Stairs** | `exposed_cut_copper_stairs` | 1,300 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/exposed_lightning_rod.png" width="32" height="32" alt="Exposed Lightning Rod" title="Exposed Lightning Rod"> | **Exposed Lightning Rod** | `exposed_lightning_rod` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/farmland.png" width="32" height="32" alt="Farmland" title="Farmland"> | **Farmland** | `farmland` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/fern.png" width="32" height="32" alt="Fern" title="Fern"> | **Fern** | `fern` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/fire_coral.png" width="32" height="32" alt="Fire Coral" title="Fire Coral"> | **Fire Coral** | `fire_coral` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/fire_coral_block.png" width="32" height="32" alt="Fire Coral Block" title="Fire Coral Block"> | **Fire Coral Block** | `fire_coral_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/fire_coral_fan.png" width="32" height="32" alt="Fire Coral Fan" title="Fire Coral Fan"> | **Fire Coral Fan** | `fire_coral_fan` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/firefly_bush.png" width="32" height="32" alt="Firefly Bush" title="Firefly Bush"> | **Firefly Bush** | `firefly_bush` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/fletching_table.png" width="32" height="32" alt="Fletching Table" title="Fletching Table"> | **Fletching Table** | `fletching_table` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/flower_pot.png" width="32" height="32" alt="Flower Pot" title="Flower Pot"> | **Flower Pot** | `flower_pot` | 7,500 | 6,000 | Buy / Sell | - |
| <img src="assets/icons/flowering_azalea.png" width="32" height="32" alt="Flowering Azalea" title="Flowering Azalea"> | **Flowering Azalea** | `flowering_azalea` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/flowering_azalea_leaves.png" width="32" height="32" alt="Flowering Azalea Leaves" title="Flowering Azalea Leaves"> | **Flowering Azalea Leaves** | `flowering_azalea_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/frogspawn.png" width="32" height="32" alt="Frogspawn" title="Frogspawn"> | **Frogspawn** | `frogspawn` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/furnace.png" width="32" height="32" alt="Furnace" title="Furnace"> | **Furnace** | `furnace` | 8,000 | 80 | Buy / Sell | - |
| <img src="assets/icons/gilded_blackstone.png" width="32" height="32" alt="Gilded Blackstone" title="Gilded Blackstone"> | **Gilded Blackstone** | `gilded_blackstone` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/glass.png" width="32" height="32" alt="Glass" title="Glass"> | **Glass** | `glass` | 1,000 | 10 | Buy / Sell | - |
| <img src="assets/icons/glass_pane.png" width="32" height="32" alt="Glass Pane" title="Glass Pane"> | **Glass Pane** | `glass_pane` | 1,000 | 3 | Buy / Sell | - |
| <img src="assets/icons/glow_lichen.png" width="32" height="32" alt="Glow Lichen" title="Glow Lichen"> | **Glow Lichen** | `glow_lichen` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/golden_dandelion.png" width="32" height="32" alt="Golden Dandelion" title="Golden Dandelion"> | **Golden Dandelion** | `golden_dandelion` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/granite.png" width="32" height="32" alt="Granite" title="Granite"> | **Granite** | `granite` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/granite_slab.png" width="32" height="32" alt="Granite Slab" title="Granite Slab"> | **Granite Slab** | `granite_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/granite_stairs.png" width="32" height="32" alt="Granite Stairs" title="Granite Stairs"> | **Granite Stairs** | `granite_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/granite_wall.png" width="32" height="32" alt="Granite Wall" title="Granite Wall"> | **Granite Wall** | `granite_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/grass_block.png" width="32" height="32" alt="Grass Block" title="Grass Block"> | **Grass Block** | `grass_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/gravel.png" width="32" height="32" alt="Gravel" title="Gravel"> | **Gravel** | `gravel` | 10 | 8 | Buy / Sell | - |
| <img src="assets/icons/gray_banner.png" width="32" height="32" alt="Gray Banner" title="Gray Banner"> | **Gray Banner** | `gray_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/gray_bed.png" width="32" height="32" alt="Gray Bed" title="Gray Bed"> | **Gray Bed** | `gray_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/gray_candle.png" width="32" height="32" alt="Gray Candle" title="Gray Candle"> | **Gray Candle** | `gray_candle` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/gray_carpet.png" width="32" height="32" alt="Gray Carpet" title="Gray Carpet"> | **Gray Carpet** | `gray_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/gray_concrete.png" width="32" height="32" alt="Gray Concrete" title="Gray Concrete"> | **Gray Concrete** | `gray_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/gray_concrete_powder.png" width="32" height="32" alt="Gray Concrete Powder" title="Gray Concrete Powder"> | **Gray Concrete Powder** | `gray_concrete_powder` | 15 | 11 | Buy / Sell | - |
| <img src="assets/icons/gray_concrete_slab.png" width="32" height="32" alt="Gray Concrete Slab" title="Gray Concrete Slab"> | **Gray Concrete Slab** | `gray_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/gray_concrete_stairs.png" width="32" height="32" alt="Gray Concrete Stairs" title="Gray Concrete Stairs"> | **Gray Concrete Stairs** | `gray_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/gray_glazed_terracotta.png" width="32" height="32" alt="Gray Glazed Terracotta" title="Gray Glazed Terracotta"> | **Gray Glazed Terracotta** | `gray_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/gray_stained_glass.png" width="32" height="32" alt="Gray Stained Glass" title="Gray Stained Glass"> | **Gray Stained Glass** | `gray_stained_glass` | 1,000 | 124 | Buy / Sell | - |
| <img src="assets/icons/gray_stained_glass_pane.png" width="32" height="32" alt="Gray Stained Glass Pane" title="Gray Stained Glass Pane"> | **Gray Stained Glass Pane** | `gray_stained_glass_pane` | 1,000 | 46 | Buy / Sell | - |
| <img src="assets/icons/gray_terracotta.png" width="32" height="32" alt="Gray Terracotta" title="Gray Terracotta"> | **Gray Terracotta** | `gray_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/gray_wool.png" width="32" height="32" alt="Gray Wool" title="Gray Wool"> | **Gray Wool** | `gray_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/gray_wool_slab.png" width="32" height="32" alt="Gray Wool Slab" title="Gray Wool Slab"> | **Gray Wool Slab** | `gray_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/gray_wool_stairs.png" width="32" height="32" alt="Gray Wool Stairs" title="Gray Wool Stairs"> | **Gray Wool Stairs** | `gray_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/green_banner.png" width="32" height="32" alt="Green Banner" title="Green Banner"> | **Green Banner** | `green_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/green_bed.png" width="32" height="32" alt="Green Bed" title="Green Bed"> | **Green Bed** | `green_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/green_candle.png" width="32" height="32" alt="Green Candle" title="Green Candle"> | **Green Candle** | `green_candle` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/green_carpet.png" width="32" height="32" alt="Green Carpet" title="Green Carpet"> | **Green Carpet** | `green_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/green_concrete.png" width="32" height="32" alt="Green Concrete" title="Green Concrete"> | **Green Concrete** | `green_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/green_concrete_powder.png" width="32" height="32" alt="Green Concrete Powder" title="Green Concrete Powder"> | **Green Concrete Powder** | `green_concrete_powder` | 15 | 11 | Buy / Sell | - |
| <img src="assets/icons/green_concrete_slab.png" width="32" height="32" alt="Green Concrete Slab" title="Green Concrete Slab"> | **Green Concrete Slab** | `green_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/green_concrete_stairs.png" width="32" height="32" alt="Green Concrete Stairs" title="Green Concrete Stairs"> | **Green Concrete Stairs** | `green_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/green_glazed_terracotta.png" width="32" height="32" alt="Green Glazed Terracotta" title="Green Glazed Terracotta"> | **Green Glazed Terracotta** | `green_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/green_stained_glass.png" width="32" height="32" alt="Green Stained Glass" title="Green Stained Glass"> | **Green Stained Glass** | `green_stained_glass` | 1,000 | 135 | Buy / Sell | - |
| <img src="assets/icons/green_stained_glass_pane.png" width="32" height="32" alt="Green Stained Glass Pane" title="Green Stained Glass Pane"> | **Green Stained Glass Pane** | `green_stained_glass_pane` | 1,000 | 50 | Buy / Sell | - |
| <img src="assets/icons/green_terracotta.png" width="32" height="32" alt="Green Terracotta" title="Green Terracotta"> | **Green Terracotta** | `green_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/green_wool.png" width="32" height="32" alt="Green Wool" title="Green Wool"> | **Green Wool** | `green_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/green_wool_slab.png" width="32" height="32" alt="Green Wool Slab" title="Green Wool Slab"> | **Green Wool Slab** | `green_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/green_wool_stairs.png" width="32" height="32" alt="Green Wool Stairs" title="Green Wool Stairs"> | **Green Wool Stairs** | `green_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/grindstone.png" width="32" height="32" alt="Grindstone" title="Grindstone"> | **Grindstone** | `grindstone` | 1,000 | 605 | Buy / Sell | - |
| <img src="assets/icons/hanging_roots.png" width="32" height="32" alt="Hanging Roots" title="Hanging Roots"> | **Hanging Roots** | `hanging_roots` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/hay_block.png" width="32" height="32" alt="Hay Bale" title="Hay Bale"> | **Hay Bale** | `hay_block` | 22,500 | 18,000 | Buy / Sell | - |
| <img src="assets/icons/heavy_core.png" width="32" height="32" alt="Heavy Core" title="Heavy Core"> | **Heavy Core** | `heavy_core` | 500,000 | 400,000 | Buy / Sell | - |
| <img src="assets/icons/honey_block.png" width="32" height="32" alt="Honey Block" title="Honey Block"> | **Honey Block** | `honey_block` | 3,200 | 2,500 | Buy / Sell | - |
| <img src="assets/icons/honeycomb_block.png" width="32" height="32" alt="Honeycomb Block" title="Honeycomb Block"> | **Honeycomb Block** | `honeycomb_block` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/horn_coral.png" width="32" height="32" alt="Horn Coral" title="Horn Coral"> | **Horn Coral** | `horn_coral` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/horn_coral_block.png" width="32" height="32" alt="Horn Coral Block" title="Horn Coral Block"> | **Horn Coral Block** | `horn_coral_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/horn_coral_fan.png" width="32" height="32" alt="Horn Coral Fan" title="Horn Coral Fan"> | **Horn Coral Fan** | `horn_coral_fan` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/ice.png" width="32" height="32" alt="Ice" title="Ice"> | **Ice** | `ice` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/infested_chiseled_stone_bricks.png" width="32" height="32" alt="Infested Chiseled Stone Bricks" title="Infested Chiseled Stone Bricks"> | **Infested Chiseled Stone Bricks** | `infested_chiseled_stone_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/infested_cobblestone.png" width="32" height="32" alt="Infested Cobblestone" title="Infested Cobblestone"> | **Infested Cobblestone** | `infested_cobblestone` | 10 | 8 | Buy / Sell | - |
| <img src="assets/icons/infested_cracked_stone_bricks.png" width="32" height="32" alt="Infested Cracked Stone Bricks" title="Infested Cracked Stone Bricks"> | **Infested Cracked Stone Bricks** | `infested_cracked_stone_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/infested_deepslate.png" width="32" height="32" alt="Infested Deepslate" title="Infested Deepslate"> | **Infested Deepslate** | `infested_deepslate` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/infested_mossy_stone_bricks.png" width="32" height="32" alt="Infested Mossy Stone Bricks" title="Infested Mossy Stone Bricks"> | **Infested Mossy Stone Bricks** | `infested_mossy_stone_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/infested_stone.png" width="32" height="32" alt="Infested Stone" title="Infested Stone"> | **Infested Stone** | `infested_stone` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/infested_stone_bricks.png" width="32" height="32" alt="Infested Stone Bricks" title="Infested Stone Bricks"> | **Infested Stone Bricks** | `infested_stone_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/iron_bars.png" width="32" height="32" alt="Iron Bars" title="Iron Bars"> | **Iron Bars** | `iron_bars` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/iron_chain.png" width="32" height="32" alt="Iron Chain" title="Iron Chain"> | **Iron Chain** | `iron_chain` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/iron_door.png" width="32" height="32" alt="Iron Door" title="Iron Door"> | **Iron Door** | `iron_door` | 8,000 | 6,400 | Buy / Sell | - |
| <img src="assets/icons/iron_trapdoor.png" width="32" height="32" alt="Iron Trapdoor" title="Iron Trapdoor"> | **Iron Trapdoor** | `iron_trapdoor` | 16,000 | 12,800 | Buy / Sell | - |
| <img src="assets/icons/jack_o_lantern.png" width="32" height="32" alt="Jack o'Lantern" title="Jack o'Lantern"> | **Jack o'Lantern** | `jack_o_lantern` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/jukebox.png" width="32" height="32" alt="Jukebox" title="Jukebox"> | **Jukebox** | `jukebox` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/jungle_door.png" width="32" height="32" alt="Jungle Door" title="Jungle Door"> | **Jungle Door** | `jungle_door` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/jungle_fence.png" width="32" height="32" alt="Jungle Fence" title="Jungle Fence"> | **Jungle Fence** | `jungle_fence` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/jungle_fence_gate.png" width="32" height="32" alt="Jungle Fence Gate" title="Jungle Fence Gate"> | **Jungle Fence Gate** | `jungle_fence_gate` | 1,200 | 800 | Buy / Sell | - |
| <img src="assets/icons/jungle_hanging_sign.png" width="32" height="32" alt="Jungle Hanging Sign" title="Jungle Hanging Sign"> | **Jungle Hanging Sign** | `jungle_hanging_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/jungle_leaves.png" width="32" height="32" alt="Jungle Leaves" title="Jungle Leaves"> | **Jungle Leaves** | `jungle_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/jungle_log.png" width="32" height="32" alt="Jungle Log" title="Jungle Log"> | **Jungle Log** | `jungle_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/jungle_planks.png" width="32" height="32" alt="Jungle Planks" title="Jungle Planks"> | **Jungle Planks** | `jungle_planks` | 800 | 200 | Buy / Sell | - |
| <img src="assets/icons/jungle_sapling.png" width="32" height="32" alt="Jungle Sapling" title="Jungle Sapling"> | **Jungle Sapling** | `jungle_sapling` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/jungle_shelf.png" width="32" height="32" alt="Jungle Shelf" title="Jungle Shelf"> | **Jungle Shelf** | `jungle_shelf` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/jungle_sign.png" width="32" height="32" alt="Jungle Sign" title="Jungle Sign"> | **Jungle Sign** | `jungle_sign` | 2,000 | 400 | Buy / Sell | - |
| <img src="assets/icons/jungle_slab.png" width="32" height="32" alt="Jungle Slab" title="Jungle Slab"> | **Jungle Slab** | `jungle_slab` | 1,200 | 100 | Buy / Sell | - |
| <img src="assets/icons/jungle_stairs.png" width="32" height="32" alt="Jungle Stairs" title="Jungle Stairs"> | **Jungle Stairs** | `jungle_stairs` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/jungle_trapdoor.png" width="32" height="32" alt="Jungle Trapdoor" title="Jungle Trapdoor"> | **Jungle Trapdoor** | `jungle_trapdoor` | 2,400 | 600 | Buy / Sell | - |
| <img src="assets/icons/jungle_wood.png" width="32" height="32" alt="Jungle Wood" title="Jungle Wood"> | **Jungle Wood** | `jungle_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/ladder.png" width="32" height="32" alt="Ladder" title="Ladder"> | **Ladder** | `ladder` | 5,900 | 200 | Buy / Sell | - |
| <img src="assets/icons/lantern.png" width="32" height="32" alt="Lantern" title="Lantern"> | **Lantern** | `lantern` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/large_amethyst_bud.png" width="32" height="32" alt="Large Amethyst Bud" title="Large Amethyst Bud"> | **Large Amethyst Bud** | `large_amethyst_bud` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/large_fern.png" width="32" height="32" alt="Large Fern" title="Large Fern"> | **Large Fern** | `large_fern` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/leaf_litter.png" width="32" height="32" alt="Leaf Litter" title="Leaf Litter"> | **Leaf Litter** | `leaf_litter` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/lectern.png" width="32" height="32" alt="Lectern" title="Lectern"> | **Lectern** | `lectern` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/light_blue_banner.png" width="32" height="32" alt="Light Blue Banner" title="Light Blue Banner"> | **Light Blue Banner** | `light_blue_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/light_blue_bed.png" width="32" height="32" alt="Light Blue Bed" title="Light Blue Bed"> | **Light Blue Bed** | `light_blue_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/light_blue_candle.png" width="32" height="32" alt="Light Blue Candle" title="Light Blue Candle"> | **Light Blue Candle** | `light_blue_candle` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/light_blue_carpet.png" width="32" height="32" alt="Light Blue Carpet" title="Light Blue Carpet"> | **Light Blue Carpet** | `light_blue_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/light_blue_concrete.png" width="32" height="32" alt="Light Blue Concrete" title="Light Blue Concrete"> | **Light Blue Concrete** | `light_blue_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/light_blue_concrete_powder.png" width="32" height="32" alt="Light Blue Concrete Powder" title="Light Blue Concrete Powder"> | **Light Blue Concrete Powder** | `light_blue_concrete_powder` | 15 | 11 | Buy / Sell | - |
| <img src="assets/icons/light_blue_concrete_slab.png" width="32" height="32" alt="Light Blue Concrete Slab" title="Light Blue Concrete Slab"> | **Light Blue Concrete Slab** | `light_blue_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/light_blue_concrete_stairs.png" width="32" height="32" alt="Light Blue Concrete Stairs" title="Light Blue Concrete Stairs"> | **Light Blue Concrete Stairs** | `light_blue_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/light_blue_glazed_terracotta.png" width="32" height="32" alt="Light Blue Glazed Terracotta" title="Light Blue Glazed Terracotta"> | **Light Blue Glazed Terracotta** | `light_blue_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/light_blue_stained_glass.png" width="32" height="32" alt="Light Blue Stained Glass" title="Light Blue Stained Glass"> | **Light Blue Stained Glass** | `light_blue_stained_glass` | 1,000 | 124 | Buy / Sell | - |
| <img src="assets/icons/light_blue_stained_glass_pane.png" width="32" height="32" alt="Light Blue Stained Glass Pane" title="Light Blue Stained Glass Pane"> | **Light Blue Stained Glass Pane** | `light_blue_stained_glass_pane` | 1,000 | 46 | Buy / Sell | - |
| <img src="assets/icons/light_blue_terracotta.png" width="32" height="32" alt="Light Blue Terracotta" title="Light Blue Terracotta"> | **Light Blue Terracotta** | `light_blue_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/light_blue_wool.png" width="32" height="32" alt="Light Blue Wool" title="Light Blue Wool"> | **Light Blue Wool** | `light_blue_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/light_blue_wool_slab.png" width="32" height="32" alt="Light Blue Wool Slab" title="Light Blue Wool Slab"> | **Light Blue Wool Slab** | `light_blue_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/light_blue_wool_stairs.png" width="32" height="32" alt="Light Blue Wool Stairs" title="Light Blue Wool Stairs"> | **Light Blue Wool Stairs** | `light_blue_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/light_gray_banner.png" width="32" height="32" alt="Light Gray Banner" title="Light Gray Banner"> | **Light Gray Banner** | `light_gray_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/light_gray_bed.png" width="32" height="32" alt="Light Gray Bed" title="Light Gray Bed"> | **Light Gray Bed** | `light_gray_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/light_gray_candle.png" width="32" height="32" alt="Light Gray Candle" title="Light Gray Candle"> | **Light Gray Candle** | `light_gray_candle` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/light_gray_carpet.png" width="32" height="32" alt="Light Gray Carpet" title="Light Gray Carpet"> | **Light Gray Carpet** | `light_gray_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/light_gray_concrete.png" width="32" height="32" alt="Light Gray Concrete" title="Light Gray Concrete"> | **Light Gray Concrete** | `light_gray_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/light_gray_concrete_powder.png" width="32" height="32" alt="Light Gray Concrete Powder" title="Light Gray Concrete Powder"> | **Light Gray Concrete Powder** | `light_gray_concrete_powder` | 15 | 11 | Buy / Sell | - |
| <img src="assets/icons/light_gray_concrete_slab.png" width="32" height="32" alt="Light Gray Concrete Slab" title="Light Gray Concrete Slab"> | **Light Gray Concrete Slab** | `light_gray_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/light_gray_concrete_stairs.png" width="32" height="32" alt="Light Gray Concrete Stairs" title="Light Gray Concrete Stairs"> | **Light Gray Concrete Stairs** | `light_gray_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/light_gray_glazed_terracotta.png" width="32" height="32" alt="Light Gray Glazed Terracotta" title="Light Gray Glazed Terracotta"> | **Light Gray Glazed Terracotta** | `light_gray_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/light_gray_stained_glass.png" width="32" height="32" alt="Light Gray Stained Glass" title="Light Gray Stained Glass"> | **Light Gray Stained Glass** | `light_gray_stained_glass` | 1,000 | 119 | Buy / Sell | - |
| <img src="assets/icons/light_gray_stained_glass_pane.png" width="32" height="32" alt="Light Gray Stained Glass Pane" title="Light Gray Stained Glass Pane"> | **Light Gray Stained Glass Pane** | `light_gray_stained_glass_pane` | 1,000 | 44 | Buy / Sell | - |
| <img src="assets/icons/light_gray_terracotta.png" width="32" height="32" alt="Light Gray Terracotta" title="Light Gray Terracotta"> | **Light Gray Terracotta** | `light_gray_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/light_gray_wool.png" width="32" height="32" alt="Light Gray Wool" title="Light Gray Wool"> | **Light Gray Wool** | `light_gray_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/light_gray_wool_slab.png" width="32" height="32" alt="Light Gray Wool Slab" title="Light Gray Wool Slab"> | **Light Gray Wool Slab** | `light_gray_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/light_gray_wool_stairs.png" width="32" height="32" alt="Light Gray Wool Stairs" title="Light Gray Wool Stairs"> | **Light Gray Wool Stairs** | `light_gray_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/lightning_rod.png" width="32" height="32" alt="Lightning Rod" title="Lightning Rod"> | **Lightning Rod** | `lightning_rod` | 4,500 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/lilac.png" width="32" height="32" alt="Lilac" title="Lilac"> | **Lilac** | `lilac` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/lily_of_the_valley.png" width="32" height="32" alt="Lily of the Valley" title="Lily of the Valley"> | **Lily of the Valley** | `lily_of_the_valley` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/lily_pad.png" width="32" height="32" alt="Lily Pad" title="Lily Pad"> | **Lily Pad** | `lily_pad` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/lime_banner.png" width="32" height="32" alt="Lime Banner" title="Lime Banner"> | **Lime Banner** | `lime_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/lime_bed.png" width="32" height="32" alt="Lime Bed" title="Lime Bed"> | **Lime Bed** | `lime_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/lime_candle.png" width="32" height="32" alt="Lime Candle" title="Lime Candle"> | **Lime Candle** | `lime_candle` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/lime_carpet.png" width="32" height="32" alt="Lime Carpet" title="Lime Carpet"> | **Lime Carpet** | `lime_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/lime_concrete.png" width="32" height="32" alt="Lime Concrete" title="Lime Concrete"> | **Lime Concrete** | `lime_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/lime_concrete_powder.png" width="32" height="32" alt="Lime Concrete Powder" title="Lime Concrete Powder"> | **Lime Concrete Powder** | `lime_concrete_powder` | 15 | 11 | Buy / Sell | - |
| <img src="assets/icons/lime_concrete_slab.png" width="32" height="32" alt="Lime Concrete Slab" title="Lime Concrete Slab"> | **Lime Concrete Slab** | `lime_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/lime_concrete_stairs.png" width="32" height="32" alt="Lime Concrete Stairs" title="Lime Concrete Stairs"> | **Lime Concrete Stairs** | `lime_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/lime_glazed_terracotta.png" width="32" height="32" alt="Lime Glazed Terracotta" title="Lime Glazed Terracotta"> | **Lime Glazed Terracotta** | `lime_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/lime_stained_glass.png" width="32" height="32" alt="Lime Stained Glass" title="Lime Stained Glass"> | **Lime Stained Glass** | `lime_stained_glass` | 1,000 | 124 | Buy / Sell | - |
| <img src="assets/icons/lime_stained_glass_pane.png" width="32" height="32" alt="Lime Stained Glass Pane" title="Lime Stained Glass Pane"> | **Lime Stained Glass Pane** | `lime_stained_glass_pane` | 1,000 | 46 | Buy / Sell | - |
| <img src="assets/icons/lime_terracotta.png" width="32" height="32" alt="Lime Terracotta" title="Lime Terracotta"> | **Lime Terracotta** | `lime_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/lime_wool.png" width="32" height="32" alt="Lime Wool" title="Lime Wool"> | **Lime Wool** | `lime_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/lime_wool_slab.png" width="32" height="32" alt="Lime Wool Slab" title="Lime Wool Slab"> | **Lime Wool Slab** | `lime_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/lime_wool_stairs.png" width="32" height="32" alt="Lime Wool Stairs" title="Lime Wool Stairs"> | **Lime Wool Stairs** | `lime_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/lodestone.png" width="32" height="32" alt="Lodestone" title="Lodestone"> | **Lodestone** | `lodestone` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/loom.png" width="32" height="32" alt="Loom" title="Loom"> | **Loom** | `loom` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/magenta_banner.png" width="32" height="32" alt="Magenta Banner" title="Magenta Banner"> | **Magenta Banner** | `magenta_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/magenta_bed.png" width="32" height="32" alt="Magenta Bed" title="Magenta Bed"> | **Magenta Bed** | `magenta_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/magenta_candle.png" width="32" height="32" alt="Magenta Candle" title="Magenta Candle"> | **Magenta Candle** | `magenta_candle` | 2,000 | 1,500 | Buy / Sell | - |
| <img src="assets/icons/magenta_carpet.png" width="32" height="32" alt="Magenta Carpet" title="Magenta Carpet"> | **Magenta Carpet** | `magenta_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/magenta_concrete.png" width="32" height="32" alt="Magenta Concrete" title="Magenta Concrete"> | **Magenta Concrete** | `magenta_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/magenta_concrete_powder.png" width="32" height="32" alt="Magenta Concrete Powder" title="Magenta Concrete Powder"> | **Magenta Concrete Powder** | `magenta_concrete_powder` | 15 | 10 | Buy / Sell | - |
| <img src="assets/icons/magenta_concrete_slab.png" width="32" height="32" alt="Magenta Concrete Slab" title="Magenta Concrete Slab"> | **Magenta Concrete Slab** | `magenta_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/magenta_concrete_stairs.png" width="32" height="32" alt="Magenta Concrete Stairs" title="Magenta Concrete Stairs"> | **Magenta Concrete Stairs** | `magenta_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/magenta_glazed_terracotta.png" width="32" height="32" alt="Magenta Glazed Terracotta" title="Magenta Glazed Terracotta"> | **Magenta Glazed Terracotta** | `magenta_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/magenta_stained_glass.png" width="32" height="32" alt="Magenta Stained Glass" title="Magenta Stained Glass"> | **Magenta Stained Glass** | `magenta_stained_glass` | 1,000 | 72 | Buy / Sell | - |
| <img src="assets/icons/magenta_stained_glass_pane.png" width="32" height="32" alt="Magenta Stained Glass Pane" title="Magenta Stained Glass Pane"> | **Magenta Stained Glass Pane** | `magenta_stained_glass_pane` | 1,000 | 27 | Buy / Sell | - |
| <img src="assets/icons/magenta_terracotta.png" width="32" height="32" alt="Magenta Terracotta" title="Magenta Terracotta"> | **Magenta Terracotta** | `magenta_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/magenta_wool.png" width="32" height="32" alt="Magenta Wool" title="Magenta Wool"> | **Magenta Wool** | `magenta_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/magenta_wool_slab.png" width="32" height="32" alt="Magenta Wool Slab" title="Magenta Wool Slab"> | **Magenta Wool Slab** | `magenta_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/magenta_wool_stairs.png" width="32" height="32" alt="Magenta Wool Stairs" title="Magenta Wool Stairs"> | **Magenta Wool Stairs** | `magenta_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/mangrove_door.png" width="32" height="32" alt="Mangrove Door" title="Mangrove Door"> | **Mangrove Door** | `mangrove_door` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/mangrove_fence.png" width="32" height="32" alt="Mangrove Fence" title="Mangrove Fence"> | **Mangrove Fence** | `mangrove_fence` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/mangrove_fence_gate.png" width="32" height="32" alt="Mangrove Fence Gate" title="Mangrove Fence Gate"> | **Mangrove Fence Gate** | `mangrove_fence_gate` | 1,200 | 800 | Buy / Sell | - |
| <img src="assets/icons/mangrove_hanging_sign.png" width="32" height="32" alt="Mangrove Hanging Sign" title="Mangrove Hanging Sign"> | **Mangrove Hanging Sign** | `mangrove_hanging_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/mangrove_leaves.png" width="32" height="32" alt="Mangrove Leaves" title="Mangrove Leaves"> | **Mangrove Leaves** | `mangrove_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/mangrove_log.png" width="32" height="32" alt="Mangrove Log" title="Mangrove Log"> | **Mangrove Log** | `mangrove_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/mangrove_planks.png" width="32" height="32" alt="Mangrove Planks" title="Mangrove Planks"> | **Mangrove Planks** | `mangrove_planks` | 800 | 200 | Buy / Sell | - |
| <img src="assets/icons/mangrove_propagule.png" width="32" height="32" alt="Mangrove Propagule" title="Mangrove Propagule"> | **Mangrove Propagule** | `mangrove_propagule` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/mangrove_roots.png" width="32" height="32" alt="Mangrove Roots" title="Mangrove Roots"> | **Mangrove Roots** | `mangrove_roots` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/mangrove_shelf.png" width="32" height="32" alt="Mangrove Shelf" title="Mangrove Shelf"> | **Mangrove Shelf** | `mangrove_shelf` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/mangrove_sign.png" width="32" height="32" alt="Mangrove Sign" title="Mangrove Sign"> | **Mangrove Sign** | `mangrove_sign` | 2,000 | 400 | Buy / Sell | - |
| <img src="assets/icons/mangrove_slab.png" width="32" height="32" alt="Mangrove Slab" title="Mangrove Slab"> | **Mangrove Slab** | `mangrove_slab` | 1,200 | 100 | Buy / Sell | - |
| <img src="assets/icons/mangrove_stairs.png" width="32" height="32" alt="Mangrove Stairs" title="Mangrove Stairs"> | **Mangrove Stairs** | `mangrove_stairs` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/mangrove_trapdoor.png" width="32" height="32" alt="Mangrove Trapdoor" title="Mangrove Trapdoor"> | **Mangrove Trapdoor** | `mangrove_trapdoor` | 2,400 | 600 | Buy / Sell | - |
| <img src="assets/icons/mangrove_wood.png" width="32" height="32" alt="Mangrove Wood" title="Mangrove Wood"> | **Mangrove Wood** | `mangrove_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/medium_amethyst_bud.png" width="32" height="32" alt="Medium Amethyst Bud" title="Medium Amethyst Bud"> | **Medium Amethyst Bud** | `medium_amethyst_bud` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/melon.png" width="32" height="32" alt="Melon" title="Melon"> | **Melon** | `melon` | 7,200 | 5,700 | Buy / Sell | - |
| <img src="assets/icons/melon_seeds.png" width="32" height="32" alt="Melon Seeds" title="Melon Seeds"> | **Melon Seeds** | `melon_seeds` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/spawner.png" width="32" height="32" alt="Monster Spawner" title="Monster Spawner"> | **Monster Spawner** | `spawner` | 500,000 | 400,000 | Buy / Sell | - |
| <img src="assets/icons/moss_block.png" width="32" height="32" alt="Moss Block" title="Moss Block"> | **Moss Block** | `moss_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/moss_carpet.png" width="32" height="32" alt="Moss Carpet" title="Moss Carpet"> | **Moss Carpet** | `moss_carpet` | 14 | 11 | Buy / Sell | - |
| <img src="assets/icons/mossy_cobblestone.png" width="32" height="32" alt="Mossy Cobblestone" title="Mossy Cobblestone"> | **Mossy Cobblestone** | `mossy_cobblestone` | 10 | 8 | Buy / Sell | - |
| <img src="assets/icons/mossy_cobblestone_slab.png" width="32" height="32" alt="Mossy Cobblestone Slab" title="Mossy Cobblestone Slab"> | **Mossy Cobblestone Slab** | `mossy_cobblestone_slab` | 12 | 5 | Buy / Sell | - |
| <img src="assets/icons/mossy_cobblestone_stairs.png" width="32" height="32" alt="Mossy Cobblestone Stairs" title="Mossy Cobblestone Stairs"> | **Mossy Cobblestone Stairs** | `mossy_cobblestone_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/mossy_cobblestone_wall.png" width="32" height="32" alt="Mossy Cobblestone Wall" title="Mossy Cobblestone Wall"> | **Mossy Cobblestone Wall** | `mossy_cobblestone_wall` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/mossy_stone_brick_slab.png" width="32" height="32" alt="Mossy Stone Brick Slab" title="Mossy Stone Brick Slab"> | **Mossy Stone Brick Slab** | `mossy_stone_brick_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/mossy_stone_brick_stairs.png" width="32" height="32" alt="Mossy Stone Brick Stairs" title="Mossy Stone Brick Stairs"> | **Mossy Stone Brick Stairs** | `mossy_stone_brick_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/mossy_stone_brick_wall.png" width="32" height="32" alt="Mossy Stone Brick Wall" title="Mossy Stone Brick Wall"> | **Mossy Stone Brick Wall** | `mossy_stone_brick_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/mossy_stone_bricks.png" width="32" height="32" alt="Mossy Stone Bricks" title="Mossy Stone Bricks"> | **Mossy Stone Bricks** | `mossy_stone_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/mud.png" width="32" height="32" alt="Mud" title="Mud"> | **Mud** | `mud` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/mud_brick_slab.png" width="32" height="32" alt="Mud Brick Slab" title="Mud Brick Slab"> | **Mud Brick Slab** | `mud_brick_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/mud_brick_stairs.png" width="32" height="32" alt="Mud Brick Stairs" title="Mud Brick Stairs"> | **Mud Brick Stairs** | `mud_brick_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/mud_brick_wall.png" width="32" height="32" alt="Mud Brick Wall" title="Mud Brick Wall"> | **Mud Brick Wall** | `mud_brick_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/mud_bricks.png" width="32" height="32" alt="Mud Bricks" title="Mud Bricks"> | **Mud Bricks** | `mud_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/muddy_mangrove_roots.png" width="32" height="32" alt="Muddy Mangrove Roots" title="Muddy Mangrove Roots"> | **Muddy Mangrove Roots** | `muddy_mangrove_roots` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/mushroom_stem.png" width="32" height="32" alt="Mushroom Stem" title="Mushroom Stem"> | **Mushroom Stem** | `mushroom_stem` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/mycelium.png" width="32" height="32" alt="Mycelium" title="Mycelium"> | **Mycelium** | `mycelium` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/note_block.png" width="32" height="32" alt="Note Block" title="Note Block"> | **Note Block** | `note_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/oak_door.png" width="32" height="32" alt="Oak Door" title="Oak Door"> | **Oak Door** | `oak_door` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/oak_fence.png" width="32" height="32" alt="Oak Fence" title="Oak Fence"> | **Oak Fence** | `oak_fence` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/oak_fence_gate.png" width="32" height="32" alt="Oak Fence Gate" title="Oak Fence Gate"> | **Oak Fence Gate** | `oak_fence_gate` | 1,200 | 800 | Buy / Sell | - |
| <img src="assets/icons/oak_hanging_sign.png" width="32" height="32" alt="Oak Hanging Sign" title="Oak Hanging Sign"> | **Oak Hanging Sign** | `oak_hanging_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/oak_leaves.png" width="32" height="32" alt="Oak Leaves" title="Oak Leaves"> | **Oak Leaves** | `oak_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/oak_log.png" width="32" height="32" alt="Oak Log" title="Oak Log"> | **Oak Log** | `oak_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/oak_planks.png" width="32" height="32" alt="Oak Planks" title="Oak Planks"> | **Oak Planks** | `oak_planks` | 800 | 200 | Buy / Sell | - |
| <img src="assets/icons/oak_sapling.png" width="32" height="32" alt="Oak Sapling" title="Oak Sapling"> | **Oak Sapling** | `oak_sapling` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/oak_shelf.png" width="32" height="32" alt="Oak Shelf" title="Oak Shelf"> | **Oak Shelf** | `oak_shelf` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/oak_sign.png" width="32" height="32" alt="Oak Sign" title="Oak Sign"> | **Oak Sign** | `oak_sign` | 2,000 | 400 | Buy / Sell | - |
| <img src="assets/icons/oak_slab.png" width="32" height="32" alt="Oak Slab" title="Oak Slab"> | **Oak Slab** | `oak_slab` | 1,200 | 100 | Buy / Sell | - |
| <img src="assets/icons/oak_stairs.png" width="32" height="32" alt="Oak Stairs" title="Oak Stairs"> | **Oak Stairs** | `oak_stairs` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/oak_trapdoor.png" width="32" height="32" alt="Oak Trapdoor" title="Oak Trapdoor"> | **Oak Trapdoor** | `oak_trapdoor` | 2,400 | 600 | Buy / Sell | - |
| <img src="assets/icons/oak_wood.png" width="32" height="32" alt="Oak Wood" title="Oak Wood"> | **Oak Wood** | `oak_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/obsidian.png" width="32" height="32" alt="Obsidian" title="Obsidian"> | **Obsidian** | `obsidian` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/ochre_froglight.png" width="32" height="32" alt="Ochre Froglight" title="Ochre Froglight"> | **Ochre Froglight** | `ochre_froglight` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/open_eyeblossom.png" width="32" height="32" alt="Open Eyeblossom" title="Open Eyeblossom"> | **Open Eyeblossom** | `open_eyeblossom` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/orange_banner.png" width="32" height="32" alt="Orange Banner" title="Orange Banner"> | **Orange Banner** | `orange_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/orange_bed.png" width="32" height="32" alt="Orange Bed" title="Orange Bed"> | **Orange Bed** | `orange_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/orange_candle.png" width="32" height="32" alt="Orange Candle" title="Orange Candle"> | **Orange Candle** | `orange_candle` | 2,000 | 1,500 | Buy / Sell | - |
| <img src="assets/icons/orange_carpet.png" width="32" height="32" alt="Orange Carpet" title="Orange Carpet"> | **Orange Carpet** | `orange_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/orange_concrete.png" width="32" height="32" alt="Orange Concrete" title="Orange Concrete"> | **Orange Concrete** | `orange_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/orange_concrete_powder.png" width="32" height="32" alt="Orange Concrete Powder" title="Orange Concrete Powder"> | **Orange Concrete Powder** | `orange_concrete_powder` | 15 | 10 | Buy / Sell | - |
| <img src="assets/icons/orange_concrete_slab.png" width="32" height="32" alt="Orange Concrete Slab" title="Orange Concrete Slab"> | **Orange Concrete Slab** | `orange_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/orange_concrete_stairs.png" width="32" height="32" alt="Orange Concrete Stairs" title="Orange Concrete Stairs"> | **Orange Concrete Stairs** | `orange_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/orange_glazed_terracotta.png" width="32" height="32" alt="Orange Glazed Terracotta" title="Orange Glazed Terracotta"> | **Orange Glazed Terracotta** | `orange_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/orange_poplar_leaves.png" width="32" height="32" alt="Orange Poplar Leaves" title="Orange Poplar Leaves"> | **Orange Poplar Leaves** | `orange_poplar_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/orange_stained_glass.png" width="32" height="32" alt="Orange Stained Glass" title="Orange Stained Glass"> | **Orange Stained Glass** | `orange_stained_glass` | 1,000 | 72 | Buy / Sell | - |
| <img src="assets/icons/orange_stained_glass_pane.png" width="32" height="32" alt="Orange Stained Glass Pane" title="Orange Stained Glass Pane"> | **Orange Stained Glass Pane** | `orange_stained_glass_pane` | 1,000 | 27 | Buy / Sell | - |
| <img src="assets/icons/orange_terracotta.png" width="32" height="32" alt="Orange Terracotta" title="Orange Terracotta"> | **Orange Terracotta** | `orange_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/orange_tulip.png" width="32" height="32" alt="Orange Tulip" title="Orange Tulip"> | **Orange Tulip** | `orange_tulip` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/orange_wool.png" width="32" height="32" alt="Orange Wool" title="Orange Wool"> | **Orange Wool** | `orange_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/orange_wool_slab.png" width="32" height="32" alt="Orange Wool Slab" title="Orange Wool Slab"> | **Orange Wool Slab** | `orange_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/orange_wool_stairs.png" width="32" height="32" alt="Orange Wool Stairs" title="Orange Wool Stairs"> | **Orange Wool Stairs** | `orange_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/oxeye_daisy.png" width="32" height="32" alt="Oxeye Daisy" title="Oxeye Daisy"> | **Oxeye Daisy** | `oxeye_daisy` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/oxidized_chiseled_copper.png" width="32" height="32" alt="Oxidized Chiseled Copper" title="Oxidized Chiseled Copper"> | **Oxidized Chiseled Copper** | `oxidized_chiseled_copper` | 5,000 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/oxidized_copper.png" width="32" height="32" alt="Oxidized Copper" title="Oxidized Copper"> | **Oxidized Copper** | `oxidized_copper` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/oxidized_copper_bars.png" width="32" height="32" alt="Oxidized Copper Bars" title="Oxidized Copper Bars"> | **Oxidized Copper Bars** | `oxidized_copper_bars` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/oxidized_copper_bulb.png" width="32" height="32" alt="Oxidized Copper Bulb" title="Oxidized Copper Bulb"> | **Oxidized Copper Bulb** | `oxidized_copper_bulb` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/oxidized_copper_chain.png" width="32" height="32" alt="Oxidized Copper Chain" title="Oxidized Copper Chain"> | **Oxidized Copper Chain** | `oxidized_copper_chain` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/oxidized_copper_chest.png" width="32" height="32" alt="Oxidized Copper Chest" title="Oxidized Copper Chest"> | **Oxidized Copper Chest** | `oxidized_copper_chest` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/oxidized_copper_door.png" width="32" height="32" alt="Oxidized Copper Door" title="Oxidized Copper Door"> | **Oxidized Copper Door** | `oxidized_copper_door` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/oxidized_copper_golem_statue.png" width="32" height="32" alt="Oxidized Copper Golem Statue" title="Oxidized Copper Golem Statue"> | **Oxidized Copper Golem Statue** | `oxidized_copper_golem_statue` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/oxidized_copper_grate.png" width="32" height="32" alt="Oxidized Copper Grate" title="Oxidized Copper Grate"> | **Oxidized Copper Grate** | `oxidized_copper_grate` | 1,300 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/oxidized_copper_lantern.png" width="32" height="32" alt="Oxidized Copper Lantern" title="Oxidized Copper Lantern"> | **Oxidized Copper Lantern** | `oxidized_copper_lantern` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/oxidized_copper_trapdoor.png" width="32" height="32" alt="Oxidized Copper Trapdoor" title="Oxidized Copper Trapdoor"> | **Oxidized Copper Trapdoor** | `oxidized_copper_trapdoor` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/oxidized_cut_copper.png" width="32" height="32" alt="Oxidized Cut Copper" title="Oxidized Cut Copper"> | **Oxidized Cut Copper** | `oxidized_cut_copper` | 5,000 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/oxidized_cut_copper_slab.png" width="32" height="32" alt="Oxidized Cut Copper Slab" title="Oxidized Cut Copper Slab"> | **Oxidized Cut Copper Slab** | `oxidized_cut_copper_slab` | 1,200 | 600 | Buy / Sell | - |
| <img src="assets/icons/oxidized_cut_copper_stairs.png" width="32" height="32" alt="Oxidized Cut Copper Stairs" title="Oxidized Cut Copper Stairs"> | **Oxidized Cut Copper Stairs** | `oxidized_cut_copper_stairs` | 1,300 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/oxidized_lightning_rod.png" width="32" height="32" alt="Oxidized Lightning Rod" title="Oxidized Lightning Rod"> | **Oxidized Lightning Rod** | `oxidized_lightning_rod` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/packed_ice.png" width="32" height="32" alt="Packed Ice" title="Packed Ice"> | **Packed Ice** | `packed_ice` | 9,000 | 7,200 | Buy / Sell | - |
| <img src="assets/icons/packed_mud.png" width="32" height="32" alt="Packed Mud" title="Packed Mud"> | **Packed Mud** | `packed_mud` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/pale_hanging_moss.png" width="32" height="32" alt="Pale Hanging Moss" title="Pale Hanging Moss"> | **Pale Hanging Moss** | `pale_hanging_moss` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/pale_moss_block.png" width="32" height="32" alt="Pale Moss Block" title="Pale Moss Block"> | **Pale Moss Block** | `pale_moss_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/pale_moss_carpet.png" width="32" height="32" alt="Pale Moss Carpet" title="Pale Moss Carpet"> | **Pale Moss Carpet** | `pale_moss_carpet` | 14 | 11 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_door.png" width="32" height="32" alt="Pale Oak Door" title="Pale Oak Door"> | **Pale Oak Door** | `pale_oak_door` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_fence.png" width="32" height="32" alt="Pale Oak Fence" title="Pale Oak Fence"> | **Pale Oak Fence** | `pale_oak_fence` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_fence_gate.png" width="32" height="32" alt="Pale Oak Fence Gate" title="Pale Oak Fence Gate"> | **Pale Oak Fence Gate** | `pale_oak_fence_gate` | 1,200 | 800 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_hanging_sign.png" width="32" height="32" alt="Pale Oak Hanging Sign" title="Pale Oak Hanging Sign"> | **Pale Oak Hanging Sign** | `pale_oak_hanging_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_leaves.png" width="32" height="32" alt="Pale Oak Leaves" title="Pale Oak Leaves"> | **Pale Oak Leaves** | `pale_oak_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_log.png" width="32" height="32" alt="Pale Oak Log" title="Pale Oak Log"> | **Pale Oak Log** | `pale_oak_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_planks.png" width="32" height="32" alt="Pale Oak Planks" title="Pale Oak Planks"> | **Pale Oak Planks** | `pale_oak_planks` | 800 | 200 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_sapling.png" width="32" height="32" alt="Pale Oak Sapling" title="Pale Oak Sapling"> | **Pale Oak Sapling** | `pale_oak_sapling` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_shelf.png" width="32" height="32" alt="Pale Oak Shelf" title="Pale Oak Shelf"> | **Pale Oak Shelf** | `pale_oak_shelf` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_sign.png" width="32" height="32" alt="Pale Oak Sign" title="Pale Oak Sign"> | **Pale Oak Sign** | `pale_oak_sign` | 2,000 | 400 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_slab.png" width="32" height="32" alt="Pale Oak Slab" title="Pale Oak Slab"> | **Pale Oak Slab** | `pale_oak_slab` | 1,200 | 100 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_stairs.png" width="32" height="32" alt="Pale Oak Stairs" title="Pale Oak Stairs"> | **Pale Oak Stairs** | `pale_oak_stairs` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_trapdoor.png" width="32" height="32" alt="Pale Oak Trapdoor" title="Pale Oak Trapdoor"> | **Pale Oak Trapdoor** | `pale_oak_trapdoor` | 2,400 | 600 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_wood.png" width="32" height="32" alt="Pale Oak Wood" title="Pale Oak Wood"> | **Pale Oak Wood** | `pale_oak_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/pearlescent_froglight.png" width="32" height="32" alt="Pearlescent Froglight" title="Pearlescent Froglight"> | **Pearlescent Froglight** | `pearlescent_froglight` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/peony.png" width="32" height="32" alt="Peony" title="Peony"> | **Peony** | `peony` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/piglin_head.png" width="32" height="32" alt="Piglin Head" title="Piglin Head"> | **Piglin Head** | `piglin_head` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/pink_banner.png" width="32" height="32" alt="Pink Banner" title="Pink Banner"> | **Pink Banner** | `pink_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/pink_bed.png" width="32" height="32" alt="Pink Bed" title="Pink Bed"> | **Pink Bed** | `pink_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/pink_candle.png" width="32" height="32" alt="Pink Candle" title="Pink Candle"> | **Pink Candle** | `pink_candle` | 2,000 | 1,500 | Buy / Sell | - |
| <img src="assets/icons/pink_carpet.png" width="32" height="32" alt="Pink Carpet" title="Pink Carpet"> | **Pink Carpet** | `pink_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/pink_concrete.png" width="32" height="32" alt="Pink Concrete" title="Pink Concrete"> | **Pink Concrete** | `pink_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/pink_concrete_powder.png" width="32" height="32" alt="Pink Concrete Powder" title="Pink Concrete Powder"> | **Pink Concrete Powder** | `pink_concrete_powder` | 15 | 10 | Buy / Sell | - |
| <img src="assets/icons/pink_concrete_slab.png" width="32" height="32" alt="Pink Concrete Slab" title="Pink Concrete Slab"> | **Pink Concrete Slab** | `pink_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/pink_concrete_stairs.png" width="32" height="32" alt="Pink Concrete Stairs" title="Pink Concrete Stairs"> | **Pink Concrete Stairs** | `pink_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/pink_glazed_terracotta.png" width="32" height="32" alt="Pink Glazed Terracotta" title="Pink Glazed Terracotta"> | **Pink Glazed Terracotta** | `pink_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/pink_petals.png" width="32" height="32" alt="Pink Petals" title="Pink Petals"> | **Pink Petals** | `pink_petals` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/pink_stained_glass.png" width="32" height="32" alt="Pink Stained Glass" title="Pink Stained Glass"> | **Pink Stained Glass** | `pink_stained_glass` | 1,000 | 72 | Buy / Sell | - |
| <img src="assets/icons/pink_stained_glass_pane.png" width="32" height="32" alt="Pink Stained Glass Pane" title="Pink Stained Glass Pane"> | **Pink Stained Glass Pane** | `pink_stained_glass_pane` | 1,000 | 27 | Buy / Sell | - |
| <img src="assets/icons/pink_terracotta.png" width="32" height="32" alt="Pink Terracotta" title="Pink Terracotta"> | **Pink Terracotta** | `pink_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/pink_tulip.png" width="32" height="32" alt="Pink Tulip" title="Pink Tulip"> | **Pink Tulip** | `pink_tulip` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/pink_wool.png" width="32" height="32" alt="Pink Wool" title="Pink Wool"> | **Pink Wool** | `pink_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/pink_wool_slab.png" width="32" height="32" alt="Pink Wool Slab" title="Pink Wool Slab"> | **Pink Wool Slab** | `pink_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/pink_wool_stairs.png" width="32" height="32" alt="Pink Wool Stairs" title="Pink Wool Stairs"> | **Pink Wool Stairs** | `pink_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/pitcher_plant.png" width="32" height="32" alt="Pitcher Plant" title="Pitcher Plant"> | **Pitcher Plant** | `pitcher_plant` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/pitcher_pod.png" width="32" height="32" alt="Pitcher Pod" title="Pitcher Pod"> | **Pitcher Pod** | `pitcher_pod` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/player_head.png" width="32" height="32" alt="Player Head" title="Player Head"> | **Player Head** | `player_head` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/podzol.png" width="32" height="32" alt="Podzol" title="Podzol"> | **Podzol** | `podzol` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/pointed_dripstone.png" width="32" height="32" alt="Pointed Dripstone" title="Pointed Dripstone"> | **Pointed Dripstone** | `pointed_dripstone` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/polished_andesite.png" width="32" height="32" alt="Polished Andesite" title="Polished Andesite"> | **Polished Andesite** | `polished_andesite` | 1,000 | 505 | Buy / Sell | - |
| <img src="assets/icons/polished_andesite_slab.png" width="32" height="32" alt="Polished Andesite Slab" title="Polished Andesite Slab"> | **Polished Andesite Slab** | `polished_andesite_slab` | 1,200 | 252 | Buy / Sell | - |
| <img src="assets/icons/polished_andesite_stairs.png" width="32" height="32" alt="Polished Andesite Stairs" title="Polished Andesite Stairs"> | **Polished Andesite Stairs** | `polished_andesite_stairs` | 1,200 | 505 | Buy / Sell | - |
| <img src="assets/icons/polished_basalt.png" width="32" height="32" alt="Polished Basalt" title="Polished Basalt"> | **Polished Basalt** | `polished_basalt` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/polished_blackstone.png" width="32" height="32" alt="Polished Blackstone" title="Polished Blackstone"> | **Polished Blackstone** | `polished_blackstone` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/polished_blackstone_brick_slab.png" width="32" height="32" alt="Polished Blackstone Brick Slab" title="Polished Blackstone Brick Slab"> | **Polished Blackstone Brick Slab** | `polished_blackstone_brick_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/polished_blackstone_brick_stairs.png" width="32" height="32" alt="Polished Blackstone Brick Stairs" title="Polished Blackstone Brick Stairs"> | **Polished Blackstone Brick Stairs** | `polished_blackstone_brick_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_blackstone_brick_wall.png" width="32" height="32" alt="Polished Blackstone Brick Wall" title="Polished Blackstone Brick Wall"> | **Polished Blackstone Brick Wall** | `polished_blackstone_brick_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_blackstone_bricks.png" width="32" height="32" alt="Polished Blackstone Bricks" title="Polished Blackstone Bricks"> | **Polished Blackstone Bricks** | `polished_blackstone_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/polished_blackstone_slab.png" width="32" height="32" alt="Polished Blackstone Slab" title="Polished Blackstone Slab"> | **Polished Blackstone Slab** | `polished_blackstone_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/polished_blackstone_stairs.png" width="32" height="32" alt="Polished Blackstone Stairs" title="Polished Blackstone Stairs"> | **Polished Blackstone Stairs** | `polished_blackstone_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_blackstone_wall.png" width="32" height="32" alt="Polished Blackstone Wall" title="Polished Blackstone Wall"> | **Polished Blackstone Wall** | `polished_blackstone_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_cinnabar.png" width="32" height="32" alt="Polished Cinnabar" title="Polished Cinnabar"> | **Polished Cinnabar** | `polished_cinnabar` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/polished_cinnabar_slab.png" width="32" height="32" alt="Polished Cinnabar Slab" title="Polished Cinnabar Slab"> | **Polished Cinnabar Slab** | `polished_cinnabar_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_cinnabar_stairs.png" width="32" height="32" alt="Polished Cinnabar Stairs" title="Polished Cinnabar Stairs"> | **Polished Cinnabar Stairs** | `polished_cinnabar_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_cinnabar_wall.png" width="32" height="32" alt="Polished Cinnabar Wall" title="Polished Cinnabar Wall"> | **Polished Cinnabar Wall** | `polished_cinnabar_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_deepslate.png" width="32" height="32" alt="Polished Deepslate" title="Polished Deepslate"> | **Polished Deepslate** | `polished_deepslate` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/polished_deepslate_slab.png" width="32" height="32" alt="Polished Deepslate Slab" title="Polished Deepslate Slab"> | **Polished Deepslate Slab** | `polished_deepslate_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/polished_deepslate_stairs.png" width="32" height="32" alt="Polished Deepslate Stairs" title="Polished Deepslate Stairs"> | **Polished Deepslate Stairs** | `polished_deepslate_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_deepslate_wall.png" width="32" height="32" alt="Polished Deepslate Wall" title="Polished Deepslate Wall"> | **Polished Deepslate Wall** | `polished_deepslate_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_diorite.png" width="32" height="32" alt="Polished Diorite" title="Polished Diorite"> | **Polished Diorite** | `polished_diorite` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/polished_diorite_slab.png" width="32" height="32" alt="Polished Diorite Slab" title="Polished Diorite Slab"> | **Polished Diorite Slab** | `polished_diorite_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/polished_diorite_stairs.png" width="32" height="32" alt="Polished Diorite Stairs" title="Polished Diorite Stairs"> | **Polished Diorite Stairs** | `polished_diorite_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_granite.png" width="32" height="32" alt="Polished Granite" title="Polished Granite"> | **Polished Granite** | `polished_granite` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/polished_granite_slab.png" width="32" height="32" alt="Polished Granite Slab" title="Polished Granite Slab"> | **Polished Granite Slab** | `polished_granite_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/polished_granite_stairs.png" width="32" height="32" alt="Polished Granite Stairs" title="Polished Granite Stairs"> | **Polished Granite Stairs** | `polished_granite_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_sulfur.png" width="32" height="32" alt="Polished Sulfur" title="Polished Sulfur"> | **Polished Sulfur** | `polished_sulfur` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/polished_sulfur_slab.png" width="32" height="32" alt="Polished Sulfur Slab" title="Polished Sulfur Slab"> | **Polished Sulfur Slab** | `polished_sulfur_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_sulfur_stairs.png" width="32" height="32" alt="Polished Sulfur Stairs" title="Polished Sulfur Stairs"> | **Polished Sulfur Stairs** | `polished_sulfur_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_sulfur_wall.png" width="32" height="32" alt="Polished Sulfur Wall" title="Polished Sulfur Wall"> | **Polished Sulfur Wall** | `polished_sulfur_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_tuff.png" width="32" height="32" alt="Polished Tuff" title="Polished Tuff"> | **Polished Tuff** | `polished_tuff` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/polished_tuff_slab.png" width="32" height="32" alt="Polished Tuff Slab" title="Polished Tuff Slab"> | **Polished Tuff Slab** | `polished_tuff_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/polished_tuff_stairs.png" width="32" height="32" alt="Polished Tuff Stairs" title="Polished Tuff Stairs"> | **Polished Tuff Stairs** | `polished_tuff_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_tuff_wall.png" width="32" height="32" alt="Polished Tuff Wall" title="Polished Tuff Wall"> | **Polished Tuff Wall** | `polished_tuff_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/poplar_door.png" width="32" height="32" alt="Poplar Door" title="Poplar Door"> | **Poplar Door** | `poplar_door` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/poplar_fence.png" width="32" height="32" alt="Poplar Fence" title="Poplar Fence"> | **Poplar Fence** | `poplar_fence` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/poplar_fence_gate.png" width="32" height="32" alt="Poplar Fence Gate" title="Poplar Fence Gate"> | **Poplar Fence Gate** | `poplar_fence_gate` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/poplar_hanging_sign.png" width="32" height="32" alt="Poplar Hanging Sign" title="Poplar Hanging Sign"> | **Poplar Hanging Sign** | `poplar_hanging_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/poplar_log.png" width="32" height="32" alt="Poplar Log" title="Poplar Log"> | **Poplar Log** | `poplar_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/poplar_planks.png" width="32" height="32" alt="Poplar Planks" title="Poplar Planks"> | **Poplar Planks** | `poplar_planks` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/poplar_sapling.png" width="32" height="32" alt="Poplar Sapling" title="Poplar Sapling"> | **Poplar Sapling** | `poplar_sapling` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/poplar_shelf.png" width="32" height="32" alt="Poplar Shelf" title="Poplar Shelf"> | **Poplar Shelf** | `poplar_shelf` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/poplar_sign.png" width="32" height="32" alt="Poplar Sign" title="Poplar Sign"> | **Poplar Sign** | `poplar_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/poplar_slab.png" width="32" height="32" alt="Poplar Slab" title="Poplar Slab"> | **Poplar Slab** | `poplar_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/poplar_stairs.png" width="32" height="32" alt="Poplar Stairs" title="Poplar Stairs"> | **Poplar Stairs** | `poplar_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/poplar_trapdoor.png" width="32" height="32" alt="Poplar Trapdoor" title="Poplar Trapdoor"> | **Poplar Trapdoor** | `poplar_trapdoor` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/poplar_wood.png" width="32" height="32" alt="Poplar Wood" title="Poplar Wood"> | **Poplar Wood** | `poplar_wood` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/poppy.png" width="32" height="32" alt="Poppy" title="Poppy"> | **Poppy** | `poppy` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/potent_sulfur.png" width="32" height="32" alt="Potent Sulfur" title="Potent Sulfur"> | **Potent Sulfur** | `potent_sulfur` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/powder_snow_bucket.png" width="32" height="32" alt="Powder Snow Bucket" title="Powder Snow Bucket"> | **Powder Snow Bucket** | `powder_snow_bucket` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/prismarine.png" width="32" height="32" alt="Prismarine" title="Prismarine"> | **Prismarine** | `prismarine` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/prismarine_brick_slab.png" width="32" height="32" alt="Prismarine Brick Slab" title="Prismarine Brick Slab"> | **Prismarine Brick Slab** | `prismarine_brick_slab` | 11,300 | 9,000 | Buy / Sell | - |
| <img src="assets/icons/prismarine_brick_stairs.png" width="32" height="32" alt="Prismarine Brick Stairs" title="Prismarine Brick Stairs"> | **Prismarine Brick Stairs** | `prismarine_brick_stairs` | 22,500 | 18,000 | Buy / Sell | - |
| <img src="assets/icons/prismarine_bricks.png" width="32" height="32" alt="Prismarine Bricks" title="Prismarine Bricks"> | **Prismarine Bricks** | `prismarine_bricks` | 22,500 | 18,000 | Buy / Sell | - |
| <img src="assets/icons/prismarine_slab.png" width="32" height="32" alt="Prismarine Slab" title="Prismarine Slab"> | **Prismarine Slab** | `prismarine_slab` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/prismarine_stairs.png" width="32" height="32" alt="Prismarine Stairs" title="Prismarine Stairs"> | **Prismarine Stairs** | `prismarine_stairs` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/prismarine_wall.png" width="32" height="32" alt="Prismarine Wall" title="Prismarine Wall"> | **Prismarine Wall** | `prismarine_wall` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/pumpkin.png" width="32" height="32" alt="Pumpkin" title="Pumpkin"> | **Pumpkin** | `pumpkin` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/pumpkin_seeds.png" width="32" height="32" alt="Pumpkin Seeds" title="Pumpkin Seeds"> | **Pumpkin Seeds** | `pumpkin_seeds` | 500 | 200 | Buy / Sell | - |
| <img src="assets/icons/purple_banner.png" width="32" height="32" alt="Purple Banner" title="Purple Banner"> | **Purple Banner** | `purple_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/purple_bed.png" width="32" height="32" alt="Purple Bed" title="Purple Bed"> | **Purple Bed** | `purple_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/purple_candle.png" width="32" height="32" alt="Purple Candle" title="Purple Candle"> | **Purple Candle** | `purple_candle` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/purple_carpet.png" width="32" height="32" alt="Purple Carpet" title="Purple Carpet"> | **Purple Carpet** | `purple_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/purple_concrete.png" width="32" height="32" alt="Purple Concrete" title="Purple Concrete"> | **Purple Concrete** | `purple_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/purple_concrete_powder.png" width="32" height="32" alt="Purple Concrete Powder" title="Purple Concrete Powder"> | **Purple Concrete Powder** | `purple_concrete_powder` | 15 | 10 | Buy / Sell | - |
| <img src="assets/icons/purple_concrete_slab.png" width="32" height="32" alt="Purple Concrete Slab" title="Purple Concrete Slab"> | **Purple Concrete Slab** | `purple_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/purple_concrete_stairs.png" width="32" height="32" alt="Purple Concrete Stairs" title="Purple Concrete Stairs"> | **Purple Concrete Stairs** | `purple_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/purple_glazed_terracotta.png" width="32" height="32" alt="Purple Glazed Terracotta" title="Purple Glazed Terracotta"> | **Purple Glazed Terracotta** | `purple_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/purple_stained_glass.png" width="32" height="32" alt="Purple Stained Glass" title="Purple Stained Glass"> | **Purple Stained Glass** | `purple_stained_glass` | 1,000 | 103 | Buy / Sell | - |
| <img src="assets/icons/purple_stained_glass_pane.png" width="32" height="32" alt="Purple Stained Glass Pane" title="Purple Stained Glass Pane"> | **Purple Stained Glass Pane** | `purple_stained_glass_pane` | 1,000 | 38 | Buy / Sell | - |
| <img src="assets/icons/purple_terracotta.png" width="32" height="32" alt="Purple Terracotta" title="Purple Terracotta"> | **Purple Terracotta** | `purple_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/purple_wool.png" width="32" height="32" alt="Purple Wool" title="Purple Wool"> | **Purple Wool** | `purple_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/purple_wool_slab.png" width="32" height="32" alt="Purple Wool Slab" title="Purple Wool Slab"> | **Purple Wool Slab** | `purple_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/purple_wool_stairs.png" width="32" height="32" alt="Purple Wool Stairs" title="Purple Wool Stairs"> | **Purple Wool Stairs** | `purple_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/red_banner.png" width="32" height="32" alt="Red Banner" title="Red Banner"> | **Red Banner** | `red_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/red_bed.png" width="32" height="32" alt="Red Bed" title="Red Bed"> | **Red Bed** | `red_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/red_candle.png" width="32" height="32" alt="Red Candle" title="Red Candle"> | **Red Candle** | `red_candle` | 2,000 | 1,500 | Buy / Sell | - |
| <img src="assets/icons/red_carpet.png" width="32" height="32" alt="Red Carpet" title="Red Carpet"> | **Red Carpet** | `red_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/red_concrete.png" width="32" height="32" alt="Red Concrete" title="Red Concrete"> | **Red Concrete** | `red_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/red_concrete_powder.png" width="32" height="32" alt="Red Concrete Powder" title="Red Concrete Powder"> | **Red Concrete Powder** | `red_concrete_powder` | 15 | 10 | Buy / Sell | - |
| <img src="assets/icons/red_concrete_slab.png" width="32" height="32" alt="Red Concrete Slab" title="Red Concrete Slab"> | **Red Concrete Slab** | `red_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/red_concrete_stairs.png" width="32" height="32" alt="Red Concrete Stairs" title="Red Concrete Stairs"> | **Red Concrete Stairs** | `red_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/red_glazed_terracotta.png" width="32" height="32" alt="Red Glazed Terracotta" title="Red Glazed Terracotta"> | **Red Glazed Terracotta** | `red_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/red_mushroom.png" width="32" height="32" alt="Red Mushroom" title="Red Mushroom"> | **Red Mushroom** | `red_mushroom` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/red_mushroom_block.png" width="32" height="32" alt="Red Mushroom Block" title="Red Mushroom Block"> | **Red Mushroom Block** | `red_mushroom_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/red_poplar_leaves.png" width="32" height="32" alt="Red Poplar Leaves" title="Red Poplar Leaves"> | **Red Poplar Leaves** | `red_poplar_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/red_sand.png" width="32" height="32" alt="Red Sand" title="Red Sand"> | **Red Sand** | `red_sand` | 10 | 8 | Buy / Sell | - |
| <img src="assets/icons/red_sandstone.png" width="32" height="32" alt="Red Sandstone" title="Red Sandstone"> | **Red Sandstone** | `red_sandstone` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/red_sandstone_slab.png" width="32" height="32" alt="Red Sandstone Slab" title="Red Sandstone Slab"> | **Red Sandstone Slab** | `red_sandstone_slab` | 20 | 16 | Buy / Sell | - |
| <img src="assets/icons/red_sandstone_stairs.png" width="32" height="32" alt="Red Sandstone Stairs" title="Red Sandstone Stairs"> | **Red Sandstone Stairs** | `red_sandstone_stairs` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/red_sandstone_wall.png" width="32" height="32" alt="Red Sandstone Wall" title="Red Sandstone Wall"> | **Red Sandstone Wall** | `red_sandstone_wall` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/red_shrub.png" width="32" height="32" alt="Red Shrub" title="Red Shrub"> | **Red Shrub** | `red_shrub` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/red_stained_glass.png" width="32" height="32" alt="Red Stained Glass" title="Red Stained Glass"> | **Red Stained Glass** | `red_stained_glass` | 1,000 | 72 | Buy / Sell | - |
| <img src="assets/icons/red_stained_glass_pane.png" width="32" height="32" alt="Red Stained Glass Pane" title="Red Stained Glass Pane"> | **Red Stained Glass Pane** | `red_stained_glass_pane` | 1,000 | 27 | Buy / Sell | - |
| <img src="assets/icons/red_terracotta.png" width="32" height="32" alt="Red Terracotta" title="Red Terracotta"> | **Red Terracotta** | `red_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/red_tulip.png" width="32" height="32" alt="Red Tulip" title="Red Tulip"> | **Red Tulip** | `red_tulip` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/red_wool.png" width="32" height="32" alt="Red Wool" title="Red Wool"> | **Red Wool** | `red_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/red_wool_slab.png" width="32" height="32" alt="Red Wool Slab" title="Red Wool Slab"> | **Red Wool Slab** | `red_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/red_wool_stairs.png" width="32" height="32" alt="Red Wool Stairs" title="Red Wool Stairs"> | **Red Wool Stairs** | `red_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/resin_brick_slab.png" width="32" height="32" alt="Resin Brick Slab" title="Resin Brick Slab"> | **Resin Brick Slab** | `resin_brick_slab` | 2,400 | 1,900 | Buy / Sell | - |
| <img src="assets/icons/resin_brick_stairs.png" width="32" height="32" alt="Resin Brick Stairs" title="Resin Brick Stairs"> | **Resin Brick Stairs** | `resin_brick_stairs` | 4,800 | 3,800 | Buy / Sell | - |
| <img src="assets/icons/resin_brick_wall.png" width="32" height="32" alt="Resin Brick Wall" title="Resin Brick Wall"> | **Resin Brick Wall** | `resin_brick_wall` | 4,800 | 3,800 | Buy / Sell | - |
| <img src="assets/icons/resin_bricks.png" width="32" height="32" alt="Resin Bricks" title="Resin Bricks"> | **Resin Bricks** | `resin_bricks` | 4,800 | 3,800 | Buy / Sell | - |
| <img src="assets/icons/resin_clump.png" width="32" height="32" alt="Resin Clump" title="Resin Clump"> | **Resin Clump** | `resin_clump` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/respawn_anchor.png" width="32" height="32" alt="Respawn Anchor" title="Respawn Anchor"> | **Respawn Anchor** | `respawn_anchor` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/rooted_dirt.png" width="32" height="32" alt="Rooted Dirt" title="Rooted Dirt"> | **Rooted Dirt** | `rooted_dirt` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/rose_bush.png" width="32" height="32" alt="Rose Bush" title="Rose Bush"> | **Rose Bush** | `rose_bush` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/sand.png" width="32" height="32" alt="Sand" title="Sand"> | **Sand** | `sand` | 10 | 8 | Buy / Sell | - |
| <img src="assets/icons/sandstone.png" width="32" height="32" alt="Sandstone" title="Sandstone"> | **Sandstone** | `sandstone` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/sandstone_slab.png" width="32" height="32" alt="Sandstone Slab" title="Sandstone Slab"> | **Sandstone Slab** | `sandstone_slab` | 20 | 16 | Buy / Sell | - |
| <img src="assets/icons/sandstone_stairs.png" width="32" height="32" alt="Sandstone Stairs" title="Sandstone Stairs"> | **Sandstone Stairs** | `sandstone_stairs` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/sandstone_wall.png" width="32" height="32" alt="Sandstone Wall" title="Sandstone Wall"> | **Sandstone Wall** | `sandstone_wall` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/scaffolding.png" width="32" height="32" alt="Scaffolding" title="Scaffolding"> | **Scaffolding** | `scaffolding` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/sculk.png" width="32" height="32" alt="Sculk" title="Sculk"> | **Sculk** | `sculk` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/sculk_catalyst.png" width="32" height="32" alt="Sculk Catalyst" title="Sculk Catalyst"> | **Sculk Catalyst** | `sculk_catalyst` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/sculk_sensor.png" width="32" height="32" alt="Sculk Sensor" title="Sculk Sensor"> | **Sculk Sensor** | `sculk_sensor` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/sculk_shrieker.png" width="32" height="32" alt="Sculk Shrieker" title="Sculk Shrieker"> | **Sculk Shrieker** | `sculk_shrieker` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/sculk_vein.png" width="32" height="32" alt="Sculk Vein" title="Sculk Vein"> | **Sculk Vein** | `sculk_vein` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/sea_lantern.png" width="32" height="32" alt="Sea Lantern" title="Sea Lantern"> | **Sea Lantern** | `sea_lantern` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/sea_pickle.png" width="32" height="32" alt="Sea Pickle" title="Sea Pickle"> | **Sea Pickle** | `sea_pickle` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/seagrass.png" width="32" height="32" alt="Seagrass" title="Seagrass"> | **Seagrass** | `seagrass` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/shelf_mushroom.png" width="32" height="32" alt="Shelf Mushroom" title="Shelf Mushroom"> | **Shelf Mushroom** | `shelf_mushroom` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/short_dry_grass.png" width="32" height="32" alt="Short Dry Grass" title="Short Dry Grass"> | **Short Dry Grass** | `short_dry_grass` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/short_grass.png" width="32" height="32" alt="Short Grass" title="Short Grass"> | **Short Grass** | `short_grass` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/shroomlight.png" width="32" height="32" alt="Shroomlight" title="Shroomlight"> | **Shroomlight** | `shroomlight` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/skeleton_skull.png" width="32" height="32" alt="Skeleton Skull" title="Skeleton Skull"> | **Skeleton Skull** | `skeleton_skull` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/slime_block.png" width="32" height="32" alt="Slime Block" title="Slime Block"> | **Slime Block** | `slime_block` | 22,500 | 18,000 | Buy / Sell | - |
| <img src="assets/icons/small_amethyst_bud.png" width="32" height="32" alt="Small Amethyst Bud" title="Small Amethyst Bud"> | **Small Amethyst Bud** | `small_amethyst_bud` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/small_dripleaf.png" width="32" height="32" alt="Small Dripleaf" title="Small Dripleaf"> | **Small Dripleaf** | `small_dripleaf` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/smithing_table.png" width="32" height="32" alt="Smithing Table" title="Smithing Table"> | **Smithing Table** | `smithing_table` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/smoker.png" width="32" height="32" alt="Smoker" title="Smoker"> | **Smoker** | `smoker` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/smooth_basalt.png" width="32" height="32" alt="Smooth Basalt" title="Smooth Basalt"> | **Smooth Basalt** | `smooth_basalt` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/smooth_red_sandstone.png" width="32" height="32" alt="Smooth Red Sandstone" title="Smooth Red Sandstone"> | **Smooth Red Sandstone** | `smooth_red_sandstone` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/smooth_red_sandstone_slab.png" width="32" height="32" alt="Smooth Red Sandstone Slab" title="Smooth Red Sandstone Slab"> | **Smooth Red Sandstone Slab** | `smooth_red_sandstone_slab` | 20 | 16 | Buy / Sell | - |
| <img src="assets/icons/smooth_red_sandstone_stairs.png" width="32" height="32" alt="Smooth Red Sandstone Stairs" title="Smooth Red Sandstone Stairs"> | **Smooth Red Sandstone Stairs** | `smooth_red_sandstone_stairs` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/smooth_sandstone.png" width="32" height="32" alt="Smooth Sandstone" title="Smooth Sandstone"> | **Smooth Sandstone** | `smooth_sandstone` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/smooth_sandstone_slab.png" width="32" height="32" alt="Smooth Sandstone Slab" title="Smooth Sandstone Slab"> | **Smooth Sandstone Slab** | `smooth_sandstone_slab` | 20 | 16 | Buy / Sell | - |
| <img src="assets/icons/smooth_sandstone_stairs.png" width="32" height="32" alt="Smooth Sandstone Stairs" title="Smooth Sandstone Stairs"> | **Smooth Sandstone Stairs** | `smooth_sandstone_stairs` | 40 | 32 | Buy / Sell | - |
| <img src="assets/icons/smooth_stone.png" width="32" height="32" alt="Smooth Stone" title="Smooth Stone"> | **Smooth Stone** | `smooth_stone` | 1,000 | 10 | Buy / Sell | - |
| <img src="assets/icons/smooth_stone_slab.png" width="32" height="32" alt="Smooth Stone Slab" title="Smooth Stone Slab"> | **Smooth Stone Slab** | `smooth_stone_slab` | 1,200 | 5 | Buy / Sell | - |
| <img src="assets/icons/sniffer_egg.png" width="32" height="32" alt="Sniffer Egg" title="Sniffer Egg"> | **Sniffer Egg** | `sniffer_egg` | 500,000 | 400,000 | Buy / Sell | - |
| <img src="assets/icons/snow.png" width="32" height="32" alt="Snow" title="Snow"> | **Snow** | `snow` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/snow_block.png" width="32" height="32" alt="Snow Block" title="Snow Block"> | **Snow Block** | `snow_block` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/sponge.png" width="32" height="32" alt="Sponge" title="Sponge"> | **Sponge** | `sponge` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/spore_blossom.png" width="32" height="32" alt="Spore Blossom" title="Spore Blossom"> | **Spore Blossom** | `spore_blossom` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/spruce_door.png" width="32" height="32" alt="Spruce Door" title="Spruce Door"> | **Spruce Door** | `spruce_door` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/spruce_fence.png" width="32" height="32" alt="Spruce Fence" title="Spruce Fence"> | **Spruce Fence** | `spruce_fence` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/spruce_fence_gate.png" width="32" height="32" alt="Spruce Fence Gate" title="Spruce Fence Gate"> | **Spruce Fence Gate** | `spruce_fence_gate` | 1,200 | 800 | Buy / Sell | - |
| <img src="assets/icons/spruce_hanging_sign.png" width="32" height="32" alt="Spruce Hanging Sign" title="Spruce Hanging Sign"> | **Spruce Hanging Sign** | `spruce_hanging_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/spruce_leaves.png" width="32" height="32" alt="Spruce Leaves" title="Spruce Leaves"> | **Spruce Leaves** | `spruce_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/spruce_log.png" width="32" height="32" alt="Spruce Log" title="Spruce Log"> | **Spruce Log** | `spruce_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/spruce_planks.png" width="32" height="32" alt="Spruce Planks" title="Spruce Planks"> | **Spruce Planks** | `spruce_planks` | 800 | 200 | Buy / Sell | - |
| <img src="assets/icons/spruce_sapling.png" width="32" height="32" alt="Spruce Sapling" title="Spruce Sapling"> | **Spruce Sapling** | `spruce_sapling` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/spruce_shelf.png" width="32" height="32" alt="Spruce Shelf" title="Spruce Shelf"> | **Spruce Shelf** | `spruce_shelf` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/spruce_sign.png" width="32" height="32" alt="Spruce Sign" title="Spruce Sign"> | **Spruce Sign** | `spruce_sign` | 2,000 | 400 | Buy / Sell | - |
| <img src="assets/icons/spruce_slab.png" width="32" height="32" alt="Spruce Slab" title="Spruce Slab"> | **Spruce Slab** | `spruce_slab` | 1,200 | 100 | Buy / Sell | - |
| <img src="assets/icons/spruce_stairs.png" width="32" height="32" alt="Spruce Stairs" title="Spruce Stairs"> | **Spruce Stairs** | `spruce_stairs` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/spruce_trapdoor.png" width="32" height="32" alt="Spruce Trapdoor" title="Spruce Trapdoor"> | **Spruce Trapdoor** | `spruce_trapdoor` | 2,400 | 600 | Buy / Sell | - |
| <img src="assets/icons/spruce_wood.png" width="32" height="32" alt="Spruce Wood" title="Spruce Wood"> | **Spruce Wood** | `spruce_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/stone.png" width="32" height="32" alt="Stone" title="Stone"> | **Stone** | `stone` | 1,000 | 10 | Buy / Sell | - |
| <img src="assets/icons/stone_brick_slab.png" width="32" height="32" alt="Stone Brick Slab" title="Stone Brick Slab"> | **Stone Brick Slab** | `stone_brick_slab` | 1,200 | 5 | Buy / Sell | - |
| <img src="assets/icons/stone_brick_stairs.png" width="32" height="32" alt="Stone Brick Stairs" title="Stone Brick Stairs"> | **Stone Brick Stairs** | `stone_brick_stairs` | 1,200 | 10 | Buy / Sell | - |
| <img src="assets/icons/stone_brick_wall.png" width="32" height="32" alt="Stone Brick Wall" title="Stone Brick Wall"> | **Stone Brick Wall** | `stone_brick_wall` | 1,200 | 10 | Buy / Sell | - |
| <img src="assets/icons/stone_bricks.png" width="32" height="32" alt="Stone Bricks" title="Stone Bricks"> | **Stone Bricks** | `stone_bricks` | 1,000 | 10 | Buy / Sell | - |
| <img src="assets/icons/stone_slab.png" width="32" height="32" alt="Stone Slab" title="Stone Slab"> | **Stone Slab** | `stone_slab` | 1,200 | 5 | Buy / Sell | - |
| <img src="assets/icons/stone_stairs.png" width="32" height="32" alt="Stone Stairs" title="Stone Stairs"> | **Stone Stairs** | `stone_stairs` | 1,200 | 10 | Buy / Sell | - |
| <img src="assets/icons/stonecutter.png" width="32" height="32" alt="Stonecutter" title="Stonecutter"> | **Stonecutter** | `stonecutter` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/straw_bed.png" width="32" height="32" alt="Straw Bed" title="Straw Bed"> | **Straw Bed** | `straw_bed` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/string.png" width="32" height="32" alt="String" title="String"> | **String** | `string` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/stripped_acacia_log.png" width="32" height="32" alt="Stripped Acacia Log" title="Stripped Acacia Log"> | **Stripped Acacia Log** | `stripped_acacia_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/stripped_acacia_wood.png" width="32" height="32" alt="Stripped Acacia Wood" title="Stripped Acacia Wood"> | **Stripped Acacia Wood** | `stripped_acacia_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/stripped_birch_log.png" width="32" height="32" alt="Stripped Birch Log" title="Stripped Birch Log"> | **Stripped Birch Log** | `stripped_birch_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/stripped_birch_wood.png" width="32" height="32" alt="Stripped Birch Wood" title="Stripped Birch Wood"> | **Stripped Birch Wood** | `stripped_birch_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/stripped_cherry_log.png" width="32" height="32" alt="Stripped Cherry Log" title="Stripped Cherry Log"> | **Stripped Cherry Log** | `stripped_cherry_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/stripped_cherry_wood.png" width="32" height="32" alt="Stripped Cherry Wood" title="Stripped Cherry Wood"> | **Stripped Cherry Wood** | `stripped_cherry_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/stripped_crimson_hyphae.png" width="32" height="32" alt="Stripped Crimson Hyphae" title="Stripped Crimson Hyphae"> | **Stripped Crimson Hyphae** | `stripped_crimson_hyphae` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/stripped_crimson_stem.png" width="32" height="32" alt="Stripped Crimson Stem" title="Stripped Crimson Stem"> | **Stripped Crimson Stem** | `stripped_crimson_stem` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/stripped_dark_oak_log.png" width="32" height="32" alt="Stripped Dark Oak Log" title="Stripped Dark Oak Log"> | **Stripped Dark Oak Log** | `stripped_dark_oak_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/stripped_dark_oak_wood.png" width="32" height="32" alt="Stripped Dark Oak Wood" title="Stripped Dark Oak Wood"> | **Stripped Dark Oak Wood** | `stripped_dark_oak_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/stripped_jungle_log.png" width="32" height="32" alt="Stripped Jungle Log" title="Stripped Jungle Log"> | **Stripped Jungle Log** | `stripped_jungle_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/stripped_jungle_wood.png" width="32" height="32" alt="Stripped Jungle Wood" title="Stripped Jungle Wood"> | **Stripped Jungle Wood** | `stripped_jungle_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/stripped_mangrove_log.png" width="32" height="32" alt="Stripped Mangrove Log" title="Stripped Mangrove Log"> | **Stripped Mangrove Log** | `stripped_mangrove_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/stripped_mangrove_wood.png" width="32" height="32" alt="Stripped Mangrove Wood" title="Stripped Mangrove Wood"> | **Stripped Mangrove Wood** | `stripped_mangrove_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/stripped_oak_log.png" width="32" height="32" alt="Stripped Oak Log" title="Stripped Oak Log"> | **Stripped Oak Log** | `stripped_oak_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/stripped_oak_wood.png" width="32" height="32" alt="Stripped Oak Wood" title="Stripped Oak Wood"> | **Stripped Oak Wood** | `stripped_oak_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/stripped_pale_oak_log.png" width="32" height="32" alt="Stripped Pale Oak Log" title="Stripped Pale Oak Log"> | **Stripped Pale Oak Log** | `stripped_pale_oak_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/stripped_pale_oak_wood.png" width="32" height="32" alt="Stripped Pale Oak Wood" title="Stripped Pale Oak Wood"> | **Stripped Pale Oak Wood** | `stripped_pale_oak_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/stripped_poplar_log.png" width="32" height="32" alt="Stripped Poplar Log" title="Stripped Poplar Log"> | **Stripped Poplar Log** | `stripped_poplar_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/stripped_poplar_wood.png" width="32" height="32" alt="Stripped Poplar Wood" title="Stripped Poplar Wood"> | **Stripped Poplar Wood** | `stripped_poplar_wood` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/stripped_spruce_log.png" width="32" height="32" alt="Stripped Spruce Log" title="Stripped Spruce Log"> | **Stripped Spruce Log** | `stripped_spruce_log` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/stripped_spruce_wood.png" width="32" height="32" alt="Stripped Spruce Wood" title="Stripped Spruce Wood"> | **Stripped Spruce Wood** | `stripped_spruce_wood` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/stripped_warped_hyphae.png" width="32" height="32" alt="Stripped Warped Hyphae" title="Stripped Warped Hyphae"> | **Stripped Warped Hyphae** | `stripped_warped_hyphae` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/stripped_warped_stem.png" width="32" height="32" alt="Stripped Warped Stem" title="Stripped Warped Stem"> | **Stripped Warped Stem** | `stripped_warped_stem` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/sugar_cane.png" width="32" height="32" alt="Sugar Cane" title="Sugar Cane"> | **Sugar Cane** | `sugar_cane` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/sulfur.png" width="32" height="32" alt="Sulfur" title="Sulfur"> | **Sulfur** | `sulfur` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/sulfur_brick_slab.png" width="32" height="32" alt="Sulfur Brick Slab" title="Sulfur Brick Slab"> | **Sulfur Brick Slab** | `sulfur_brick_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/sulfur_brick_stairs.png" width="32" height="32" alt="Sulfur Brick Stairs" title="Sulfur Brick Stairs"> | **Sulfur Brick Stairs** | `sulfur_brick_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/sulfur_brick_wall.png" width="32" height="32" alt="Sulfur Brick Wall" title="Sulfur Brick Wall"> | **Sulfur Brick Wall** | `sulfur_brick_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/sulfur_bricks.png" width="32" height="32" alt="Sulfur Bricks" title="Sulfur Bricks"> | **Sulfur Bricks** | `sulfur_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/sulfur_slab.png" width="32" height="32" alt="Sulfur Slab" title="Sulfur Slab"> | **Sulfur Slab** | `sulfur_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/sulfur_spike.png" width="32" height="32" alt="Sulfur Spike" title="Sulfur Spike"> | **Sulfur Spike** | `sulfur_spike` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/sulfur_stairs.png" width="32" height="32" alt="Sulfur Stairs" title="Sulfur Stairs"> | **Sulfur Stairs** | `sulfur_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/sulfur_wall.png" width="32" height="32" alt="Sulfur Wall" title="Sulfur Wall"> | **Sulfur Wall** | `sulfur_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/sunflower.png" width="32" height="32" alt="Sunflower" title="Sunflower"> | **Sunflower** | `sunflower` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/suspicious_gravel.png" width="32" height="32" alt="Suspicious Gravel" title="Suspicious Gravel"> | **Suspicious Gravel** | `suspicious_gravel` | 50 | 40 | Buy / Sell | - |
| <img src="assets/icons/suspicious_sand.png" width="32" height="32" alt="Suspicious Sand" title="Suspicious Sand"> | **Suspicious Sand** | `suspicious_sand` | 50 | 40 | Buy / Sell | - |
| <img src="assets/icons/tall_dry_grass.png" width="32" height="32" alt="Tall Dry Grass" title="Tall Dry Grass"> | **Tall Dry Grass** | `tall_dry_grass` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/tall_grass.png" width="32" height="32" alt="Tall Grass" title="Tall Grass"> | **Tall Grass** | `tall_grass` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/target.png" width="32" height="32" alt="Target" title="Target"> | **Target** | `target` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/terracotta.png" width="32" height="32" alt="Terracotta" title="Terracotta"> | **Terracotta** | `terracotta` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/tinted_glass.png" width="32" height="32" alt="Tinted Glass" title="Tinted Glass"> | **Tinted Glass** | `tinted_glass` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/tnt.png" width="32" height="32" alt="TNT" title="TNT"> | **TNT** | `tnt` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/torch.png" width="32" height="32" alt="Torch" title="Torch"> | **Torch** | `torch` | 1,000 | 400 | Buy / Sell | - |
| <img src="assets/icons/torchflower.png" width="32" height="32" alt="Torchflower" title="Torchflower"> | **Torchflower** | `torchflower` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/torchflower_seeds.png" width="32" height="32" alt="Torchflower Seeds" title="Torchflower Seeds"> | **Torchflower Seeds** | `torchflower_seeds` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/trapped_chest.png" width="32" height="32" alt="Trapped Chest" title="Trapped Chest"> | **Trapped Chest** | `trapped_chest` | 5,000 | 2,600 | Buy / Sell | - |
| <img src="assets/icons/tripwire_hook.png" width="32" height="32" alt="Tripwire Hook" title="Tripwire Hook"> | **Tripwire Hook** | `tripwire_hook` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/tube_coral.png" width="32" height="32" alt="Tube Coral" title="Tube Coral"> | **Tube Coral** | `tube_coral` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/tube_coral_block.png" width="32" height="32" alt="Tube Coral Block" title="Tube Coral Block"> | **Tube Coral Block** | `tube_coral_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/tube_coral_fan.png" width="32" height="32" alt="Tube Coral Fan" title="Tube Coral Fan"> | **Tube Coral Fan** | `tube_coral_fan` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/tuff.png" width="32" height="32" alt="Tuff" title="Tuff"> | **Tuff** | `tuff` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/tuff_brick_slab.png" width="32" height="32" alt="Tuff Brick Slab" title="Tuff Brick Slab"> | **Tuff Brick Slab** | `tuff_brick_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/tuff_brick_stairs.png" width="32" height="32" alt="Tuff Brick Stairs" title="Tuff Brick Stairs"> | **Tuff Brick Stairs** | `tuff_brick_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/tuff_brick_wall.png" width="32" height="32" alt="Tuff Brick Wall" title="Tuff Brick Wall"> | **Tuff Brick Wall** | `tuff_brick_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/tuff_bricks.png" width="32" height="32" alt="Tuff Bricks" title="Tuff Bricks"> | **Tuff Bricks** | `tuff_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/tuff_slab.png" width="32" height="32" alt="Tuff Slab" title="Tuff Slab"> | **Tuff Slab** | `tuff_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/tuff_stairs.png" width="32" height="32" alt="Tuff Stairs" title="Tuff Stairs"> | **Tuff Stairs** | `tuff_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/tuff_wall.png" width="32" height="32" alt="Tuff Wall" title="Tuff Wall"> | **Tuff Wall** | `tuff_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/turtle_egg.png" width="32" height="32" alt="Turtle Egg" title="Turtle Egg"> | **Turtle Egg** | `turtle_egg` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/twisting_vines.png" width="32" height="32" alt="Twisting Vines" title="Twisting Vines"> | **Twisting Vines** | `twisting_vines` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/verdant_froglight.png" width="32" height="32" alt="Verdant Froglight" title="Verdant Froglight"> | **Verdant Froglight** | `verdant_froglight` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/vine.png" width="32" height="32" alt="Vines" title="Vines"> | **Vines** | `vine` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/warped_door.png" width="32" height="32" alt="Warped Door" title="Warped Door"> | **Warped Door** | `warped_door` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/warped_fence.png" width="32" height="32" alt="Warped Fence" title="Warped Fence"> | **Warped Fence** | `warped_fence` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/warped_fence_gate.png" width="32" height="32" alt="Warped Fence Gate" title="Warped Fence Gate"> | **Warped Fence Gate** | `warped_fence_gate` | 1,200 | 800 | Buy / Sell | - |
| <img src="assets/icons/warped_fungus.png" width="32" height="32" alt="Warped Fungus" title="Warped Fungus"> | **Warped Fungus** | `warped_fungus` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/warped_hanging_sign.png" width="32" height="32" alt="Warped Hanging Sign" title="Warped Hanging Sign"> | **Warped Hanging Sign** | `warped_hanging_sign` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/warped_hyphae.png" width="32" height="32" alt="Warped Hyphae" title="Warped Hyphae"> | **Warped Hyphae** | `warped_hyphae` | 1,100 | 800 | Buy / Sell | - |
| <img src="assets/icons/warped_nylium.png" width="32" height="32" alt="Warped Nylium" title="Warped Nylium"> | **Warped Nylium** | `warped_nylium` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/warped_planks.png" width="32" height="32" alt="Warped Planks" title="Warped Planks"> | **Warped Planks** | `warped_planks` | 800 | 200 | Buy / Sell | - |
| <img src="assets/icons/warped_roots.png" width="32" height="32" alt="Warped Roots" title="Warped Roots"> | **Warped Roots** | `warped_roots` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/warped_shelf.png" width="32" height="32" alt="Warped Shelf" title="Warped Shelf"> | **Warped Shelf** | `warped_shelf` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/warped_sign.png" width="32" height="32" alt="Warped Sign" title="Warped Sign"> | **Warped Sign** | `warped_sign` | 2,000 | 400 | Buy / Sell | - |
| <img src="assets/icons/warped_slab.png" width="32" height="32" alt="Warped Slab" title="Warped Slab"> | **Warped Slab** | `warped_slab` | 1,200 | 100 | Buy / Sell | - |
| <img src="assets/icons/warped_stairs.png" width="32" height="32" alt="Warped Stairs" title="Warped Stairs"> | **Warped Stairs** | `warped_stairs` | 1,200 | 300 | Buy / Sell | - |
| <img src="assets/icons/warped_stem.png" width="32" height="32" alt="Warped Stem" title="Warped Stem"> | **Warped Stem** | `warped_stem` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/warped_trapdoor.png" width="32" height="32" alt="Warped Trapdoor" title="Warped Trapdoor"> | **Warped Trapdoor** | `warped_trapdoor` | 2,400 | 600 | Buy / Sell | - |
| <img src="assets/icons/warped_wart_block.png" width="32" height="32" alt="Warped Wart Block" title="Warped Wart Block"> | **Warped Wart Block** | `warped_wart_block` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/waxed_chiseled_copper.png" width="32" height="32" alt="Waxed Chiseled Copper" title="Waxed Chiseled Copper"> | **Waxed Chiseled Copper** | `waxed_chiseled_copper` | 5,000 | 3,300 | Buy / Sell | - |
| <img src="assets/icons/waxed_copper_bars.png" width="32" height="32" alt="Waxed Copper Bars" title="Waxed Copper Bars"> | **Waxed Copper Bars** | `waxed_copper_bars` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_copper_bulb.png" width="32" height="32" alt="Waxed Copper Bulb" title="Waxed Copper Bulb"> | **Waxed Copper Bulb** | `waxed_copper_bulb` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_copper_chain.png" width="32" height="32" alt="Waxed Copper Chain" title="Waxed Copper Chain"> | **Waxed Copper Chain** | `waxed_copper_chain` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_copper_chest.png" width="32" height="32" alt="Waxed Copper Chest" title="Waxed Copper Chest"> | **Waxed Copper Chest** | `waxed_copper_chest` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_copper_door.png" width="32" height="32" alt="Waxed Copper Door" title="Waxed Copper Door"> | **Waxed Copper Door** | `waxed_copper_door` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/waxed_copper_golem_statue.png" width="32" height="32" alt="Waxed Copper Golem Statue" title="Waxed Copper Golem Statue"> | **Waxed Copper Golem Statue** | `waxed_copper_golem_statue` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_copper_grate.png" width="32" height="32" alt="Waxed Copper Grate" title="Waxed Copper Grate"> | **Waxed Copper Grate** | `waxed_copper_grate` | 3,400 | 2,700 | Buy / Sell | - |
| <img src="assets/icons/waxed_copper_lantern.png" width="32" height="32" alt="Waxed Copper Lantern" title="Waxed Copper Lantern"> | **Waxed Copper Lantern** | `waxed_copper_lantern` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/waxed_copper_trapdoor.png" width="32" height="32" alt="Waxed Copper Trapdoor" title="Waxed Copper Trapdoor"> | **Waxed Copper Trapdoor** | `waxed_copper_trapdoor` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/waxed_cut_copper.png" width="32" height="32" alt="Waxed Cut Copper" title="Waxed Cut Copper"> | **Waxed Cut Copper** | `waxed_cut_copper` | 5,000 | 3,300 | Buy / Sell | - |
| <img src="assets/icons/waxed_cut_copper_slab.png" width="32" height="32" alt="Waxed Cut Copper Slab" title="Waxed Cut Copper Slab"> | **Waxed Cut Copper Slab** | `waxed_cut_copper_slab` | 1,700 | 1,300 | Buy / Sell | - |
| <img src="assets/icons/waxed_cut_copper_stairs.png" width="32" height="32" alt="Waxed Cut Copper Stairs" title="Waxed Cut Copper Stairs"> | **Waxed Cut Copper Stairs** | `waxed_cut_copper_stairs` | 3,400 | 2,700 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_chiseled_copper.png" width="32" height="32" alt="Waxed Exposed Chiseled Copper" title="Waxed Exposed Chiseled Copper"> | **Waxed Exposed Chiseled Copper** | `waxed_exposed_chiseled_copper` | 5,000 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_copper.png" width="32" height="32" alt="Waxed Exposed Copper" title="Waxed Exposed Copper"> | **Waxed Exposed Copper** | `waxed_exposed_copper` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_copper_bars.png" width="32" height="32" alt="Waxed Exposed Copper Bars" title="Waxed Exposed Copper Bars"> | **Waxed Exposed Copper Bars** | `waxed_exposed_copper_bars` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_copper_bulb.png" width="32" height="32" alt="Waxed Exposed Copper Bulb" title="Waxed Exposed Copper Bulb"> | **Waxed Exposed Copper Bulb** | `waxed_exposed_copper_bulb` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_copper_chain.png" width="32" height="32" alt="Waxed Exposed Copper Chain" title="Waxed Exposed Copper Chain"> | **Waxed Exposed Copper Chain** | `waxed_exposed_copper_chain` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_copper_chest.png" width="32" height="32" alt="Waxed Exposed Copper Chest" title="Waxed Exposed Copper Chest"> | **Waxed Exposed Copper Chest** | `waxed_exposed_copper_chest` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_copper_door.png" width="32" height="32" alt="Waxed Exposed Copper Door" title="Waxed Exposed Copper Door"> | **Waxed Exposed Copper Door** | `waxed_exposed_copper_door` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_copper_golem_statue.png" width="32" height="32" alt="Waxed Exposed Copper Golem Statue" title="Waxed Exposed Copper Golem Statue"> | **Waxed Exposed Copper Golem Statue** | `waxed_exposed_copper_golem_statue` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_copper_grate.png" width="32" height="32" alt="Waxed Exposed Copper Grate" title="Waxed Exposed Copper Grate"> | **Waxed Exposed Copper Grate** | `waxed_exposed_copper_grate` | 1,300 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_copper_lantern.png" width="32" height="32" alt="Waxed Exposed Copper Lantern" title="Waxed Exposed Copper Lantern"> | **Waxed Exposed Copper Lantern** | `waxed_exposed_copper_lantern` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_copper_trapdoor.png" width="32" height="32" alt="Waxed Exposed Copper Trapdoor" title="Waxed Exposed Copper Trapdoor"> | **Waxed Exposed Copper Trapdoor** | `waxed_exposed_copper_trapdoor` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_cut_copper.png" width="32" height="32" alt="Waxed Exposed Cut Copper" title="Waxed Exposed Cut Copper"> | **Waxed Exposed Cut Copper** | `waxed_exposed_cut_copper` | 5,000 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_cut_copper_slab.png" width="32" height="32" alt="Waxed Exposed Cut Copper Slab" title="Waxed Exposed Cut Copper Slab"> | **Waxed Exposed Cut Copper Slab** | `waxed_exposed_cut_copper_slab` | 1,200 | 600 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_cut_copper_stairs.png" width="32" height="32" alt="Waxed Exposed Cut Copper Stairs" title="Waxed Exposed Cut Copper Stairs"> | **Waxed Exposed Cut Copper Stairs** | `waxed_exposed_cut_copper_stairs` | 1,300 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_exposed_lightning_rod.png" width="32" height="32" alt="Waxed Exposed Lightning Rod" title="Waxed Exposed Lightning Rod"> | **Waxed Exposed Lightning Rod** | `waxed_exposed_lightning_rod` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_lightning_rod.png" width="32" height="32" alt="Waxed Lightning Rod" title="Waxed Lightning Rod"> | **Waxed Lightning Rod** | `waxed_lightning_rod` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_chiseled_copper.png" width="32" height="32" alt="Waxed Oxidized Chiseled Copper" title="Waxed Oxidized Chiseled Copper"> | **Waxed Oxidized Chiseled Copper** | `waxed_oxidized_chiseled_copper` | 5,000 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_copper.png" width="32" height="32" alt="Waxed Oxidized Copper" title="Waxed Oxidized Copper"> | **Waxed Oxidized Copper** | `waxed_oxidized_copper` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_copper_bars.png" width="32" height="32" alt="Waxed Oxidized Copper Bars" title="Waxed Oxidized Copper Bars"> | **Waxed Oxidized Copper Bars** | `waxed_oxidized_copper_bars` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_copper_bulb.png" width="32" height="32" alt="Waxed Oxidized Copper Bulb" title="Waxed Oxidized Copper Bulb"> | **Waxed Oxidized Copper Bulb** | `waxed_oxidized_copper_bulb` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_copper_chain.png" width="32" height="32" alt="Waxed Oxidized Copper Chain" title="Waxed Oxidized Copper Chain"> | **Waxed Oxidized Copper Chain** | `waxed_oxidized_copper_chain` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_copper_chest.png" width="32" height="32" alt="Waxed Oxidized Copper Chest" title="Waxed Oxidized Copper Chest"> | **Waxed Oxidized Copper Chest** | `waxed_oxidized_copper_chest` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_copper_door.png" width="32" height="32" alt="Waxed Oxidized Copper Door" title="Waxed Oxidized Copper Door"> | **Waxed Oxidized Copper Door** | `waxed_oxidized_copper_door` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_copper_golem_statue.png" width="32" height="32" alt="Waxed Oxidized Copper Golem Statue" title="Waxed Oxidized Copper Golem Statue"> | **Waxed Oxidized Copper Golem Statue** | `waxed_oxidized_copper_golem_statue` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_copper_grate.png" width="32" height="32" alt="Waxed Oxidized Copper Grate" title="Waxed Oxidized Copper Grate"> | **Waxed Oxidized Copper Grate** | `waxed_oxidized_copper_grate` | 1,300 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_copper_lantern.png" width="32" height="32" alt="Waxed Oxidized Copper Lantern" title="Waxed Oxidized Copper Lantern"> | **Waxed Oxidized Copper Lantern** | `waxed_oxidized_copper_lantern` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_copper_trapdoor.png" width="32" height="32" alt="Waxed Oxidized Copper Trapdoor" title="Waxed Oxidized Copper Trapdoor"> | **Waxed Oxidized Copper Trapdoor** | `waxed_oxidized_copper_trapdoor` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_cut_copper.png" width="32" height="32" alt="Waxed Oxidized Cut Copper" title="Waxed Oxidized Cut Copper"> | **Waxed Oxidized Cut Copper** | `waxed_oxidized_cut_copper` | 5,000 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_cut_copper_slab.png" width="32" height="32" alt="Waxed Oxidized Cut Copper Slab" title="Waxed Oxidized Cut Copper Slab"> | **Waxed Oxidized Cut Copper Slab** | `waxed_oxidized_cut_copper_slab` | 1,200 | 600 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_cut_copper_stairs.png" width="32" height="32" alt="Waxed Oxidized Cut Copper Stairs" title="Waxed Oxidized Cut Copper Stairs"> | **Waxed Oxidized Cut Copper Stairs** | `waxed_oxidized_cut_copper_stairs` | 1,300 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_oxidized_lightning_rod.png" width="32" height="32" alt="Waxed Oxidized Lightning Rod" title="Waxed Oxidized Lightning Rod"> | **Waxed Oxidized Lightning Rod** | `waxed_oxidized_lightning_rod` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_chiseled_copper.png" width="32" height="32" alt="Waxed Weathered Chiseled Copper" title="Waxed Weathered Chiseled Copper"> | **Waxed Weathered Chiseled Copper** | `waxed_weathered_chiseled_copper` | 5,000 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_copper.png" width="32" height="32" alt="Waxed Weathered Copper" title="Waxed Weathered Copper"> | **Waxed Weathered Copper** | `waxed_weathered_copper` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_copper_bars.png" width="32" height="32" alt="Waxed Weathered Copper Bars" title="Waxed Weathered Copper Bars"> | **Waxed Weathered Copper Bars** | `waxed_weathered_copper_bars` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_copper_bulb.png" width="32" height="32" alt="Waxed Weathered Copper Bulb" title="Waxed Weathered Copper Bulb"> | **Waxed Weathered Copper Bulb** | `waxed_weathered_copper_bulb` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_copper_chain.png" width="32" height="32" alt="Waxed Weathered Copper Chain" title="Waxed Weathered Copper Chain"> | **Waxed Weathered Copper Chain** | `waxed_weathered_copper_chain` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_copper_chest.png" width="32" height="32" alt="Waxed Weathered Copper Chest" title="Waxed Weathered Copper Chest"> | **Waxed Weathered Copper Chest** | `waxed_weathered_copper_chest` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_copper_door.png" width="32" height="32" alt="Waxed Weathered Copper Door" title="Waxed Weathered Copper Door"> | **Waxed Weathered Copper Door** | `waxed_weathered_copper_door` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_copper_golem_statue.png" width="32" height="32" alt="Waxed Weathered Copper Golem Statue" title="Waxed Weathered Copper Golem Statue"> | **Waxed Weathered Copper Golem Statue** | `waxed_weathered_copper_golem_statue` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_copper_grate.png" width="32" height="32" alt="Waxed Weathered Copper Grate" title="Waxed Weathered Copper Grate"> | **Waxed Weathered Copper Grate** | `waxed_weathered_copper_grate` | 1,300 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_copper_lantern.png" width="32" height="32" alt="Waxed Weathered Copper Lantern" title="Waxed Weathered Copper Lantern"> | **Waxed Weathered Copper Lantern** | `waxed_weathered_copper_lantern` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_copper_trapdoor.png" width="32" height="32" alt="Waxed Weathered Copper Trapdoor" title="Waxed Weathered Copper Trapdoor"> | **Waxed Weathered Copper Trapdoor** | `waxed_weathered_copper_trapdoor` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_cut_copper.png" width="32" height="32" alt="Waxed Weathered Cut Copper" title="Waxed Weathered Cut Copper"> | **Waxed Weathered Cut Copper** | `waxed_weathered_cut_copper` | 5,000 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_cut_copper_slab.png" width="32" height="32" alt="Waxed Weathered Cut Copper Slab" title="Waxed Weathered Cut Copper Slab"> | **Waxed Weathered Cut Copper Slab** | `waxed_weathered_cut_copper_slab` | 1,200 | 600 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_cut_copper_stairs.png" width="32" height="32" alt="Waxed Weathered Cut Copper Stairs" title="Waxed Weathered Cut Copper Stairs"> | **Waxed Weathered Cut Copper Stairs** | `waxed_weathered_cut_copper_stairs` | 1,300 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/waxed_weathered_lightning_rod.png" width="32" height="32" alt="Waxed Weathered Lightning Rod" title="Waxed Weathered Lightning Rod"> | **Waxed Weathered Lightning Rod** | `waxed_weathered_lightning_rod` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/weathered_chiseled_copper.png" width="32" height="32" alt="Weathered Chiseled Copper" title="Weathered Chiseled Copper"> | **Weathered Chiseled Copper** | `weathered_chiseled_copper` | 5,000 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/weathered_copper.png" width="32" height="32" alt="Weathered Copper" title="Weathered Copper"> | **Weathered Copper** | `weathered_copper` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/weathered_copper_bars.png" width="32" height="32" alt="Weathered Copper Bars" title="Weathered Copper Bars"> | **Weathered Copper Bars** | `weathered_copper_bars` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/weathered_copper_bulb.png" width="32" height="32" alt="Weathered Copper Bulb" title="Weathered Copper Bulb"> | **Weathered Copper Bulb** | `weathered_copper_bulb` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/weathered_copper_chain.png" width="32" height="32" alt="Weathered Copper Chain" title="Weathered Copper Chain"> | **Weathered Copper Chain** | `weathered_copper_chain` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/weathered_copper_chest.png" width="32" height="32" alt="Weathered Copper Chest" title="Weathered Copper Chest"> | **Weathered Copper Chest** | `weathered_copper_chest` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/weathered_copper_door.png" width="32" height="32" alt="Weathered Copper Door" title="Weathered Copper Door"> | **Weathered Copper Door** | `weathered_copper_door` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/weathered_copper_golem_statue.png" width="32" height="32" alt="Weathered Copper Golem Statue" title="Weathered Copper Golem Statue"> | **Weathered Copper Golem Statue** | `weathered_copper_golem_statue` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/weathered_copper_grate.png" width="32" height="32" alt="Weathered Copper Grate" title="Weathered Copper Grate"> | **Weathered Copper Grate** | `weathered_copper_grate` | 1,300 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/weathered_copper_lantern.png" width="32" height="32" alt="Weathered Copper Lantern" title="Weathered Copper Lantern"> | **Weathered Copper Lantern** | `weathered_copper_lantern` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/weathered_copper_trapdoor.png" width="32" height="32" alt="Weathered Copper Trapdoor" title="Weathered Copper Trapdoor"> | **Weathered Copper Trapdoor** | `weathered_copper_trapdoor` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/weathered_cut_copper.png" width="32" height="32" alt="Weathered Cut Copper" title="Weathered Cut Copper"> | **Weathered Cut Copper** | `weathered_cut_copper` | 5,000 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/weathered_cut_copper_slab.png" width="32" height="32" alt="Weathered Cut Copper Slab" title="Weathered Cut Copper Slab"> | **Weathered Cut Copper Slab** | `weathered_cut_copper_slab` | 1,200 | 600 | Buy / Sell | - |
| <img src="assets/icons/weathered_cut_copper_stairs.png" width="32" height="32" alt="Weathered Cut Copper Stairs" title="Weathered Cut Copper Stairs"> | **Weathered Cut Copper Stairs** | `weathered_cut_copper_stairs` | 1,300 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/weathered_lightning_rod.png" width="32" height="32" alt="Weathered Lightning Rod" title="Weathered Lightning Rod"> | **Weathered Lightning Rod** | `weathered_lightning_rod` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/weeping_vines.png" width="32" height="32" alt="Weeping Vines" title="Weeping Vines"> | **Weeping Vines** | `weeping_vines` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/wet_sponge.png" width="32" height="32" alt="Wet Sponge" title="Wet Sponge"> | **Wet Sponge** | `wet_sponge` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/wheat_seeds.png" width="32" height="32" alt="Wheat Seeds" title="Wheat Seeds"> | **Wheat Seeds** | `wheat_seeds` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/white_banner.png" width="32" height="32" alt="White Banner" title="White Banner"> | **White Banner** | `white_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/white_bed.png" width="32" height="32" alt="White Bed" title="White Bed"> | **White Bed** | `white_bed` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/white_candle.png" width="32" height="32" alt="White Candle" title="White Candle"> | **White Candle** | `white_candle` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/white_carpet.png" width="32" height="32" alt="White Carpet" title="White Carpet"> | **White Carpet** | `white_carpet` | 27 | 21 | Buy / Sell | - |
| <img src="assets/icons/white_concrete.png" width="32" height="32" alt="White Concrete" title="White Concrete"> | **White Concrete** | `white_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/white_concrete_powder.png" width="32" height="32" alt="White Concrete Powder" title="White Concrete Powder"> | **White Concrete Powder** | `white_concrete_powder` | 15 | 11 | Buy / Sell | - |
| <img src="assets/icons/white_concrete_slab.png" width="32" height="32" alt="White Concrete Slab" title="White Concrete Slab"> | **White Concrete Slab** | `white_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/white_concrete_stairs.png" width="32" height="32" alt="White Concrete Stairs" title="White Concrete Stairs"> | **White Concrete Stairs** | `white_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/white_glazed_terracotta.png" width="32" height="32" alt="White Glazed Terracotta" title="White Glazed Terracotta"> | **White Glazed Terracotta** | `white_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/white_stained_glass.png" width="32" height="32" alt="White Stained Glass" title="White Stained Glass"> | **White Stained Glass** | `white_stained_glass` | 1,000 | 114 | Buy / Sell | - |
| <img src="assets/icons/white_stained_glass_pane.png" width="32" height="32" alt="White Stained Glass Pane" title="White Stained Glass Pane"> | **White Stained Glass Pane** | `white_stained_glass_pane` | 1,000 | 42 | Buy / Sell | - |
| <img src="assets/icons/white_terracotta.png" width="32" height="32" alt="White Terracotta" title="White Terracotta"> | **White Terracotta** | `white_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/white_tulip.png" width="32" height="32" alt="White Tulip" title="White Tulip"> | **White Tulip** | `white_tulip` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/white_wool.png" width="32" height="32" alt="White Wool" title="White Wool"> | **White Wool** | `white_wool` | 4,000 | 3,200 | Buy / Sell | - |
| <img src="assets/icons/white_wool_slab.png" width="32" height="32" alt="White Wool Slab" title="White Wool Slab"> | **White Wool Slab** | `white_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/white_wool_stairs.png" width="32" height="32" alt="White Wool Stairs" title="White Wool Stairs"> | **White Wool Stairs** | `white_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/wildflowers.png" width="32" height="32" alt="Wildflowers" title="Wildflowers"> | **Wildflowers** | `wildflowers` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/yellow_banner.png" width="32" height="32" alt="Yellow Banner" title="Yellow Banner"> | **Yellow Banner** | `yellow_banner` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/yellow_bed.png" width="32" height="32" alt="Yellow Bed" title="Yellow Bed"> | **Yellow Bed** | `yellow_bed` | 5,000 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/yellow_candle.png" width="32" height="32" alt="Yellow Candle" title="Yellow Candle"> | **Yellow Candle** | `yellow_candle` | 2,000 | 1,500 | Buy / Sell | - |
| <img src="assets/icons/yellow_carpet.png" width="32" height="32" alt="Yellow Carpet" title="Yellow Carpet"> | **Yellow Carpet** | `yellow_carpet` | 7 | 5 | Buy / Sell | - |
| <img src="assets/icons/yellow_concrete.png" width="32" height="32" alt="Yellow Concrete" title="Yellow Concrete"> | **Yellow Concrete** | `yellow_concrete` | 15 | 12 | Buy / Sell | - |
| <img src="assets/icons/yellow_concrete_powder.png" width="32" height="32" alt="Yellow Concrete Powder" title="Yellow Concrete Powder"> | **Yellow Concrete Powder** | `yellow_concrete_powder` | 15 | 10 | Buy / Sell | - |
| <img src="assets/icons/yellow_concrete_slab.png" width="32" height="32" alt="Yellow Concrete Slab" title="Yellow Concrete Slab"> | **Yellow Concrete Slab** | `yellow_concrete_slab` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/yellow_concrete_stairs.png" width="32" height="32" alt="Yellow Concrete Stairs" title="Yellow Concrete Stairs"> | **Yellow Concrete Stairs** | `yellow_concrete_stairs` | 12 | 9 | Buy / Sell | - |
| <img src="assets/icons/yellow_glazed_terracotta.png" width="32" height="32" alt="Yellow Glazed Terracotta" title="Yellow Glazed Terracotta"> | **Yellow Glazed Terracotta** | `yellow_glazed_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/yellow_poplar_leaves.png" width="32" height="32" alt="Yellow Poplar Leaves" title="Yellow Poplar Leaves"> | **Yellow Poplar Leaves** | `yellow_poplar_leaves` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/yellow_stained_glass.png" width="32" height="32" alt="Yellow Stained Glass" title="Yellow Stained Glass"> | **Yellow Stained Glass** | `yellow_stained_glass` | 1,000 | 72 | Buy / Sell | - |
| <img src="assets/icons/yellow_stained_glass_pane.png" width="32" height="32" alt="Yellow Stained Glass Pane" title="Yellow Stained Glass Pane"> | **Yellow Stained Glass Pane** | `yellow_stained_glass_pane` | 1,000 | 27 | Buy / Sell | - |
| <img src="assets/icons/yellow_terracotta.png" width="32" height="32" alt="Yellow Terracotta" title="Yellow Terracotta"> | **Yellow Terracotta** | `yellow_terracotta` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/yellow_wool.png" width="32" height="32" alt="Yellow Wool" title="Yellow Wool"> | **Yellow Wool** | `yellow_wool` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/yellow_wool_slab.png" width="32" height="32" alt="Yellow Wool Slab" title="Yellow Wool Slab"> | **Yellow Wool Slab** | `yellow_wool_slab` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/yellow_wool_stairs.png" width="32" height="32" alt="Yellow Wool Stairs" title="Yellow Wool Stairs"> | **Yellow Wool Stairs** | `yellow_wool_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/zombie_head.png" width="32" height="32" alt="Zombie Head" title="Zombie Head"> | **Zombie Head** | `zombie_head` | 100,000 | 80,000 | Buy / Sell | - |

## Ores & Metals

_44 items, 44 buyable, 44 sellable._

| Icon | Item | ID | Buy | Sell | Trade | Notes |
|:---:|:--|:--|--:|--:|:--:|:--|
| <img src="assets/icons/amethyst_block.png" width="32" height="32" alt="Block of Amethyst" title="Block of Amethyst"> | **Block of Amethyst** | `amethyst_block` | 12,000 | 9,600 | Buy / Sell | - |
| <img src="assets/icons/coal_block.png" width="32" height="32" alt="Block of Coal" title="Block of Coal"> | **Block of Coal** | `coal_block` | 9,000 | 7,200 | Buy / Sell | - |
| <img src="assets/icons/copper_block.png" width="32" height="32" alt="Block of Copper" title="Block of Copper"> | **Block of Copper** | `copper_block` | 13,500 | 10,800 | Buy / Sell | - |
| <img src="assets/icons/diamond_block.png" width="32" height="32" alt="Block of Diamond" title="Block of Diamond"> | **Block of Diamond** | `diamond_block` | 270,000 | 216,000 | Buy / Sell | - |
| <img src="assets/icons/emerald_block.png" width="32" height="32" alt="Block of Emerald" title="Block of Emerald"> | **Block of Emerald** | `emerald_block` | 225,000 | 180,000 | Buy / Sell | - |
| <img src="assets/icons/gold_block.png" width="32" height="32" alt="Block of Gold" title="Block of Gold"> | **Block of Gold** | `gold_block` | 72,000 | 57,600 | Buy / Sell | - |
| <img src="assets/icons/iron_block.png" width="32" height="32" alt="Block of Iron" title="Block of Iron"> | **Block of Iron** | `iron_block` | 36,000 | 28,800 | Buy / Sell | - |
| <img src="assets/icons/lapis_block.png" width="32" height="32" alt="Block of Lapis Lazuli" title="Block of Lapis Lazuli"> | **Block of Lapis Lazuli** | `lapis_block` | 18,000 | 14,400 | Buy / Sell | - |
| <img src="assets/icons/netherite_block.png" width="32" height="32" alt="Block of Netherite" title="Block of Netherite"> | **Block of Netherite** | `netherite_block` | 1,080,000 | 864,000 | Buy / Sell | - |
| <img src="assets/icons/quartz_block.png" width="32" height="32" alt="Block of Quartz" title="Block of Quartz"> | **Block of Quartz** | `quartz_block` | 12,000 | 9,600 | Buy / Sell | - |
| <img src="assets/icons/raw_copper_block.png" width="32" height="32" alt="Block of Raw Copper" title="Block of Raw Copper"> | **Block of Raw Copper** | `raw_copper_block` | 45,000 | 36,000 | Buy / Sell | - |
| <img src="assets/icons/raw_gold_block.png" width="32" height="32" alt="Block of Raw Gold" title="Block of Raw Gold"> | **Block of Raw Gold** | `raw_gold_block` | 81,000 | 64,800 | Buy / Sell | - |
| <img src="assets/icons/raw_iron_block.png" width="32" height="32" alt="Block of Raw Iron" title="Block of Raw Iron"> | **Block of Raw Iron** | `raw_iron_block` | 40,500 | 32,400 | Buy / Sell | - |
| <img src="assets/icons/redstone_block.png" width="32" height="32" alt="Block of Redstone" title="Block of Redstone"> | **Block of Redstone** | `redstone_block` | 10,800 | 8,600 | Buy / Sell | - |
| <img src="assets/icons/chiseled_quartz_block.png" width="32" height="32" alt="Chiseled Quartz Block" title="Chiseled Quartz Block"> | **Chiseled Quartz Block** | `chiseled_quartz_block` | 12,000 | 9,600 | Buy / Sell | - |
| <img src="assets/icons/coal_ore.png" width="32" height="32" alt="Coal Ore" title="Coal Ore"> | **Coal Ore** | `coal_ore` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/copper_ingot.png" width="32" height="32" alt="Copper Ingot" title="Copper Ingot"> | **Copper Ingot** | `copper_ingot` | 1,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/copper_nugget.png" width="32" height="32" alt="Copper Nugget" title="Copper Nugget"> | **Copper Nugget** | `copper_nugget` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/copper_ore.png" width="32" height="32" alt="Copper Ore" title="Copper Ore"> | **Copper Ore** | `copper_ore` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/deepslate_coal_ore.png" width="32" height="32" alt="Deepslate Coal Ore" title="Deepslate Coal Ore"> | **Deepslate Coal Ore** | `deepslate_coal_ore` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/deepslate_copper_ore.png" width="32" height="32" alt="Deepslate Copper Ore" title="Deepslate Copper Ore"> | **Deepslate Copper Ore** | `deepslate_copper_ore` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/deepslate_diamond_ore.png" width="32" height="32" alt="Deepslate Diamond Ore" title="Deepslate Diamond Ore"> | **Deepslate Diamond Ore** | `deepslate_diamond_ore` | 40,000 | 32,000 | Buy / Sell | - |
| <img src="assets/icons/deepslate_emerald_ore.png" width="32" height="32" alt="Deepslate Emerald Ore" title="Deepslate Emerald Ore"> | **Deepslate Emerald Ore** | `deepslate_emerald_ore` | 30,000 | 24,000 | Buy / Sell | - |
| <img src="assets/icons/deepslate_gold_ore.png" width="32" height="32" alt="Deepslate Gold Ore" title="Deepslate Gold Ore"> | **Deepslate Gold Ore** | `deepslate_gold_ore` | 15,000 | 12,000 | Buy / Sell | - |
| <img src="assets/icons/deepslate_iron_ore.png" width="32" height="32" alt="Deepslate Iron Ore" title="Deepslate Iron Ore"> | **Deepslate Iron Ore** | `deepslate_iron_ore` | 8,000 | 6,400 | Buy / Sell | - |
| <img src="assets/icons/deepslate_lapis_ore.png" width="32" height="32" alt="Deepslate Lapis Lazuli Ore" title="Deepslate Lapis Lazuli Ore"> | **Deepslate Lapis Lazuli Ore** | `deepslate_lapis_ore` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/deepslate_redstone_ore.png" width="32" height="32" alt="Deepslate Redstone Ore" title="Deepslate Redstone Ore"> | **Deepslate Redstone Ore** | `deepslate_redstone_ore` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/diamond_ore.png" width="32" height="32" alt="Diamond Ore" title="Diamond Ore"> | **Diamond Ore** | `diamond_ore` | 40,000 | 32,000 | Buy / Sell | - |
| <img src="assets/icons/emerald_ore.png" width="32" height="32" alt="Emerald Ore" title="Emerald Ore"> | **Emerald Ore** | `emerald_ore` | 30,000 | 24,000 | Buy / Sell | - |
| <img src="assets/icons/gold_ingot.png" width="32" height="32" alt="Gold Ingot" title="Gold Ingot"> | **Gold Ingot** | `gold_ingot` | 8,000 | 6,400 | Buy / Sell | - |
| <img src="assets/icons/gold_nugget.png" width="32" height="32" alt="Gold Nugget" title="Gold Nugget"> | **Gold Nugget** | `gold_nugget` | 900 | 700 | Buy / Sell | - |
| <img src="assets/icons/gold_ore.png" width="32" height="32" alt="Gold Ore" title="Gold Ore"> | **Gold Ore** | `gold_ore` | 15,000 | 12,000 | Buy / Sell | - |
| <img src="assets/icons/iron_ingot.png" width="32" height="32" alt="Iron Ingot" title="Iron Ingot"> | **Iron Ingot** | `iron_ingot` | 4,000 | 3,200 | Buy / Sell | - |
| <img src="assets/icons/iron_nugget.png" width="32" height="32" alt="Iron Nugget" title="Iron Nugget"> | **Iron Nugget** | `iron_nugget` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/iron_ore.png" width="32" height="32" alt="Iron Ore" title="Iron Ore"> | **Iron Ore** | `iron_ore` | 8,000 | 6,400 | Buy / Sell | - |
| <img src="assets/icons/lapis_ore.png" width="32" height="32" alt="Lapis Lazuli Ore" title="Lapis Lazuli Ore"> | **Lapis Lazuli Ore** | `lapis_ore` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/nether_gold_ore.png" width="32" height="32" alt="Nether Gold Ore" title="Nether Gold Ore"> | **Nether Gold Ore** | `nether_gold_ore` | 15,000 | 12,000 | Buy / Sell | - |
| <img src="assets/icons/nether_quartz_ore.png" width="32" height="32" alt="Nether Quartz Ore" title="Nether Quartz Ore"> | **Nether Quartz Ore** | `nether_quartz_ore` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/netherite_ingot.png" width="32" height="32" alt="Netherite Ingot" title="Netherite Ingot"> | **Netherite Ingot** | `netherite_ingot` | 120,000 | 96,000 | Buy / Sell | - |
| <img src="assets/icons/raw_copper.png" width="32" height="32" alt="Raw Copper" title="Raw Copper"> | **Raw Copper** | `raw_copper` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/raw_gold.png" width="32" height="32" alt="Raw Gold" title="Raw Gold"> | **Raw Gold** | `raw_gold` | 9,000 | 7,200 | Buy / Sell | - |
| <img src="assets/icons/raw_iron.png" width="32" height="32" alt="Raw Iron" title="Raw Iron"> | **Raw Iron** | `raw_iron` | 4,500 | 3,600 | Buy / Sell | - |
| <img src="assets/icons/redstone_ore.png" width="32" height="32" alt="Redstone Ore" title="Redstone Ore"> | **Redstone Ore** | `redstone_ore` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/waxed_copper_block.png" width="32" height="32" alt="Waxed Block of Copper" title="Waxed Block of Copper"> | **Waxed Block of Copper** | `waxed_copper_block` | 13,500 | 10,800 | Buy / Sell | - |

## The Nether

_45 items, 45 buyable, 45 sellable._

| Icon | Item | ID | Buy | Sell | Trade | Notes |
|:---:|:--|:--|--:|--:|:--:|:--|
| <img src="assets/icons/ancient_debris.png" width="32" height="32" alt="Ancient Debris" title="Ancient Debris"> | **Ancient Debris** | `ancient_debris` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/blaze_powder.png" width="32" height="32" alt="Blaze Powder" title="Blaze Powder"> | **Blaze Powder** | `blaze_powder` | 2,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/blaze_rod.png" width="32" height="32" alt="Blaze Rod" title="Blaze Rod"> | **Blaze Rod** | `blaze_rod` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/chiseled_nether_bricks.png" width="32" height="32" alt="Chiseled Nether Bricks" title="Chiseled Nether Bricks"> | **Chiseled Nether Bricks** | `chiseled_nether_bricks` | 10,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/cracked_nether_bricks.png" width="32" height="32" alt="Cracked Nether Bricks" title="Cracked Nether Bricks"> | **Cracked Nether Bricks** | `cracked_nether_bricks` | 10,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/dried_ghast.png" width="32" height="32" alt="Dried Ghast" title="Dried Ghast"> | **Dried Ghast** | `dried_ghast` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/ghast_tear.png" width="32" height="32" alt="Ghast Tear" title="Ghast Tear"> | **Ghast Tear** | `ghast_tear` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/glowstone.png" width="32" height="32" alt="Glowstone" title="Glowstone"> | **Glowstone** | `glowstone` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/glowstone_dust.png" width="32" height="32" alt="Glowstone Dust" title="Glowstone Dust"> | **Glowstone Dust** | `glowstone_dust` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/magma_block.png" width="32" height="32" alt="Magma Block" title="Magma Block"> | **Magma Block** | `magma_block` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/magma_cream.png" width="32" height="32" alt="Magma Cream" title="Magma Cream"> | **Magma Cream** | `magma_cream` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/nether_brick.png" width="32" height="32" alt="Nether Brick" title="Nether Brick"> | **Nether Brick** | `nether_brick` | 2,500 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/nether_brick_fence.png" width="32" height="32" alt="Nether Brick Fence" title="Nether Brick Fence"> | **Nether Brick Fence** | `nether_brick_fence` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/nether_brick_slab.png" width="32" height="32" alt="Nether Brick Slab" title="Nether Brick Slab"> | **Nether Brick Slab** | `nether_brick_slab` | 5,000 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/nether_brick_stairs.png" width="32" height="32" alt="Nether Brick Stairs" title="Nether Brick Stairs"> | **Nether Brick Stairs** | `nether_brick_stairs` | 10,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/nether_brick_wall.png" width="32" height="32" alt="Nether Brick Wall" title="Nether Brick Wall"> | **Nether Brick Wall** | `nether_brick_wall` | 10,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/nether_bricks.png" width="32" height="32" alt="Nether Bricks" title="Nether Bricks"> | **Nether Bricks** | `nether_bricks` | 10,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/quartz.png" width="32" height="32" alt="Nether Quartz" title="Nether Quartz"> | **Nether Quartz** | `quartz` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/nether_sprouts.png" width="32" height="32" alt="Nether Sprouts" title="Nether Sprouts"> | **Nether Sprouts** | `nether_sprouts` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/nether_star.png" width="32" height="32" alt="Nether Star" title="Nether Star"> | **Nether Star** | `nether_star` | 1,000,000 | 800,000 | Buy / Sell | - |
| <img src="assets/icons/nether_wart.png" width="32" height="32" alt="Nether Wart" title="Nether Wart"> | **Nether Wart** | `nether_wart` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/nether_wart_block.png" width="32" height="32" alt="Nether Wart Block" title="Nether Wart Block"> | **Nether Wart Block** | `nether_wart_block` | 9,000 | 7,200 | Buy / Sell | - |
| <img src="assets/icons/netherite_horse_armor.png" width="32" height="32" alt="Netherite Horse Armor" title="Netherite Horse Armor"> | **Netherite Horse Armor** | `netherite_horse_armor` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/netherite_nautilus_armor.png" width="32" height="32" alt="Netherite Nautilus Armor" title="Netherite Nautilus Armor"> | **Netherite Nautilus Armor** | `netherite_nautilus_armor` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/netherite_scrap.png" width="32" height="32" alt="Netherite Scrap" title="Netherite Scrap"> | **Netherite Scrap** | `netherite_scrap` | 60,000 | 48,000 | Buy / Sell | - |
| <img src="assets/icons/netherrack.png" width="32" height="32" alt="Netherrack" title="Netherrack"> | **Netherrack** | `netherrack` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/quartz_bricks.png" width="32" height="32" alt="Quartz Bricks" title="Quartz Bricks"> | **Quartz Bricks** | `quartz_bricks` | 12,000 | 9,600 | Buy / Sell | - |
| <img src="assets/icons/quartz_pillar.png" width="32" height="32" alt="Quartz Pillar" title="Quartz Pillar"> | **Quartz Pillar** | `quartz_pillar` | 12,000 | 9,600 | Buy / Sell | - |
| <img src="assets/icons/quartz_slab.png" width="32" height="32" alt="Quartz Slab" title="Quartz Slab"> | **Quartz Slab** | `quartz_slab` | 6,000 | 4,800 | Buy / Sell | - |
| <img src="assets/icons/quartz_stairs.png" width="32" height="32" alt="Quartz Stairs" title="Quartz Stairs"> | **Quartz Stairs** | `quartz_stairs` | 12,000 | 9,600 | Buy / Sell | - |
| <img src="assets/icons/red_nether_brick_slab.png" width="32" height="32" alt="Red Nether Brick Slab" title="Red Nether Brick Slab"> | **Red Nether Brick Slab** | `red_nether_brick_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/red_nether_brick_stairs.png" width="32" height="32" alt="Red Nether Brick Stairs" title="Red Nether Brick Stairs"> | **Red Nether Brick Stairs** | `red_nether_brick_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/red_nether_brick_wall.png" width="32" height="32" alt="Red Nether Brick Wall" title="Red Nether Brick Wall"> | **Red Nether Brick Wall** | `red_nether_brick_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/red_nether_bricks.png" width="32" height="32" alt="Red Nether Bricks" title="Red Nether Bricks"> | **Red Nether Bricks** | `red_nether_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/netherite_upgrade_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `netherite_upgrade_smithing_template` | 300,000 | 211,000 | Buy / Sell | - |
| <img src="assets/icons/smooth_quartz.png" width="32" height="32" alt="Smooth Quartz Block" title="Smooth Quartz Block"> | **Smooth Quartz Block** | `smooth_quartz` | 12,000 | 9,600 | Buy / Sell | - |
| <img src="assets/icons/smooth_quartz_slab.png" width="32" height="32" alt="Smooth Quartz Slab" title="Smooth Quartz Slab"> | **Smooth Quartz Slab** | `smooth_quartz_slab` | 6,000 | 4,800 | Buy / Sell | - |
| <img src="assets/icons/smooth_quartz_stairs.png" width="32" height="32" alt="Smooth Quartz Stairs" title="Smooth Quartz Stairs"> | **Smooth Quartz Stairs** | `smooth_quartz_stairs` | 12,000 | 9,600 | Buy / Sell | - |
| <img src="assets/icons/soul_campfire.png" width="32" height="32" alt="Soul Campfire" title="Soul Campfire"> | **Soul Campfire** | `soul_campfire` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/soul_lantern.png" width="32" height="32" alt="Soul Lantern" title="Soul Lantern"> | **Soul Lantern** | `soul_lantern` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/soul_sand.png" width="32" height="32" alt="Soul Sand" title="Soul Sand"> | **Soul Sand** | `soul_sand` | 10 | 8 | Buy / Sell | - |
| <img src="assets/icons/soul_soil.png" width="32" height="32" alt="Soul Soil" title="Soul Soil"> | **Soul Soil** | `soul_soil` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/soul_torch.png" width="32" height="32" alt="Soul Torch" title="Soul Torch"> | **Soul Torch** | `soul_torch` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/wither_rose.png" width="32" height="32" alt="Wither Rose" title="Wither Rose"> | **Wither Rose** | `wither_rose` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/wither_skeleton_skull.png" width="32" height="32" alt="Wither Skeleton Skull" title="Wither Skeleton Skull"> | **Wither Skeleton Skull** | `wither_skeleton_skull` | 100,000 | 80,000 | Buy / Sell | - |

## The End

_35 items, 35 buyable, 35 sellable._

| Icon | Item | ID | Buy | Sell | Trade | Notes |
|:---:|:--|:--|--:|--:|:--:|:--|
| <img src="assets/icons/black_shulker_box.png" width="32" height="32" alt="Black Shulker Box" title="Black Shulker Box"> | **Black Shulker Box** | `black_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/blue_shulker_box.png" width="32" height="32" alt="Blue Shulker Box" title="Blue Shulker Box"> | **Blue Shulker Box** | `blue_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/brown_shulker_box.png" width="32" height="32" alt="Brown Shulker Box" title="Brown Shulker Box"> | **Brown Shulker Box** | `brown_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/chorus_flower.png" width="32" height="32" alt="Chorus Flower" title="Chorus Flower"> | **Chorus Flower** | `chorus_flower` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/chorus_fruit.png" width="32" height="32" alt="Chorus Fruit" title="Chorus Fruit"> | **Chorus Fruit** | `chorus_fruit` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/chorus_plant.png" width="32" height="32" alt="Chorus Plant" title="Chorus Plant"> | **Chorus Plant** | `chorus_plant` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/cyan_shulker_box.png" width="32" height="32" alt="Cyan Shulker Box" title="Cyan Shulker Box"> | **Cyan Shulker Box** | `cyan_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/dragon_breath.png" width="32" height="32" alt="Dragon's Breath" title="Dragon's Breath"> | **Dragon's Breath** | `dragon_breath` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/end_crystal.png" width="32" height="32" alt="End Crystal" title="End Crystal"> | **End Crystal** | `end_crystal` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/end_rod.png" width="32" height="32" alt="End Rod" title="End Rod"> | **End Rod** | `end_rod` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/end_stone.png" width="32" height="32" alt="End Stone" title="End Stone"> | **End Stone** | `end_stone` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/end_stone_brick_slab.png" width="32" height="32" alt="End Stone Brick Slab" title="End Stone Brick Slab"> | **End Stone Brick Slab** | `end_stone_brick_slab` | 1,200 | 500 | Buy / Sell | - |
| <img src="assets/icons/end_stone_brick_stairs.png" width="32" height="32" alt="End Stone Brick Stairs" title="End Stone Brick Stairs"> | **End Stone Brick Stairs** | `end_stone_brick_stairs` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/end_stone_brick_wall.png" width="32" height="32" alt="End Stone Brick Wall" title="End Stone Brick Wall"> | **End Stone Brick Wall** | `end_stone_brick_wall` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/end_stone_bricks.png" width="32" height="32" alt="End Stone Bricks" title="End Stone Bricks"> | **End Stone Bricks** | `end_stone_bricks` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/friend_pottery_sherd.png" width="32" height="32" alt="Friend Pottery Sherd" title="Friend Pottery Sherd"> | **Friend Pottery Sherd** | `friend_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/gray_shulker_box.png" width="32" height="32" alt="Gray Shulker Box" title="Gray Shulker Box"> | **Gray Shulker Box** | `gray_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/green_shulker_box.png" width="32" height="32" alt="Green Shulker Box" title="Green Shulker Box"> | **Green Shulker Box** | `green_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/light_blue_shulker_box.png" width="32" height="32" alt="Light Blue Shulker Box" title="Light Blue Shulker Box"> | **Light Blue Shulker Box** | `light_blue_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/light_gray_shulker_box.png" width="32" height="32" alt="Light Gray Shulker Box" title="Light Gray Shulker Box"> | **Light Gray Shulker Box** | `light_gray_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/lime_shulker_box.png" width="32" height="32" alt="Lime Shulker Box" title="Lime Shulker Box"> | **Lime Shulker Box** | `lime_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/magenta_shulker_box.png" width="32" height="32" alt="Magenta Shulker Box" title="Magenta Shulker Box"> | **Magenta Shulker Box** | `magenta_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/orange_shulker_box.png" width="32" height="32" alt="Orange Shulker Box" title="Orange Shulker Box"> | **Orange Shulker Box** | `orange_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/pink_shulker_box.png" width="32" height="32" alt="Pink Shulker Box" title="Pink Shulker Box"> | **Pink Shulker Box** | `pink_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/popped_chorus_fruit.png" width="32" height="32" alt="Popped Chorus Fruit" title="Popped Chorus Fruit"> | **Popped Chorus Fruit** | `popped_chorus_fruit` | 2,500 | 800 | Buy / Sell | - |
| <img src="assets/icons/purple_shulker_box.png" width="32" height="32" alt="Purple Shulker Box" title="Purple Shulker Box"> | **Purple Shulker Box** | `purple_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/purpur_block.png" width="32" height="32" alt="Purpur Block" title="Purpur Block"> | **Purpur Block** | `purpur_block` | 2,500 | 800 | Buy / Sell | - |
| <img src="assets/icons/purpur_pillar.png" width="32" height="32" alt="Purpur Pillar" title="Purpur Pillar"> | **Purpur Pillar** | `purpur_pillar` | 2,500 | 800 | Buy / Sell | - |
| <img src="assets/icons/purpur_slab.png" width="32" height="32" alt="Purpur Slab" title="Purpur Slab"> | **Purpur Slab** | `purpur_slab` | 1,300 | 400 | Buy / Sell | - |
| <img src="assets/icons/purpur_stairs.png" width="32" height="32" alt="Purpur Stairs" title="Purpur Stairs"> | **Purpur Stairs** | `purpur_stairs` | 2,500 | 800 | Buy / Sell | - |
| <img src="assets/icons/red_shulker_box.png" width="32" height="32" alt="Red Shulker Box" title="Red Shulker Box"> | **Red Shulker Box** | `red_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/shulker_box.png" width="32" height="32" alt="Shulker Box" title="Shulker Box"> | **Shulker Box** | `shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/shulker_shell.png" width="32" height="32" alt="Shulker Shell" title="Shulker Shell"> | **Shulker Shell** | `shulker_shell` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/white_shulker_box.png" width="32" height="32" alt="White Shulker Box" title="White Shulker Box"> | **White Shulker Box** | `white_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/yellow_shulker_box.png" width="32" height="32" alt="Yellow Shulker Box" title="Yellow Shulker Box"> | **Yellow Shulker Box** | `yellow_shulker_box` | 100,000 | 80,000 | Buy / Sell | - |

## Food & Farming

_51 items, 51 buyable, 51 sellable._

| Icon | Item | ID | Buy | Sell | Trade | Notes |
|:---:|:--|:--|--:|--:|:--:|:--|
| <img src="assets/icons/apple.png" width="32" height="32" alt="Apple" title="Apple"> | **Apple** | `apple` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/baked_potato.png" width="32" height="32" alt="Baked Potato" title="Baked Potato"> | **Baked Potato** | `baked_potato` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/beetroot.png" width="32" height="32" alt="Beetroot" title="Beetroot"> | **Beetroot** | `beetroot` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/beetroot_seeds.png" width="32" height="32" alt="Beetroot Seeds" title="Beetroot Seeds"> | **Beetroot Seeds** | `beetroot_seeds` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/beetroot_soup.png" width="32" height="32" alt="Beetroot Soup" title="Beetroot Soup"> | **Beetroot Soup** | `beetroot_soup` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/bread.png" width="32" height="32" alt="Bread" title="Bread"> | **Bread** | `bread` | 7,500 | 6,000 | Buy / Sell | - |
| <img src="assets/icons/cod_bucket.png" width="32" height="32" alt="Bucket of Cod" title="Bucket of Cod"> | **Bucket of Cod** | `cod_bucket` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/pufferfish_bucket.png" width="32" height="32" alt="Bucket of Pufferfish" title="Bucket of Pufferfish"> | **Bucket of Pufferfish** | `pufferfish_bucket` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/salmon_bucket.png" width="32" height="32" alt="Bucket of Salmon" title="Bucket of Salmon"> | **Bucket of Salmon** | `salmon_bucket` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/tropical_fish_bucket.png" width="32" height="32" alt="Bucket of Tropical Fish" title="Bucket of Tropical Fish"> | **Bucket of Tropical Fish** | `tropical_fish_bucket` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/cake.png" width="32" height="32" alt="Cake" title="Cake"> | **Cake** | `cake` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/carrot.png" width="32" height="32" alt="Carrot" title="Carrot"> | **Carrot** | `carrot` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/carrot_on_a_stick.png" width="32" height="32" alt="Carrot on a Stick" title="Carrot on a Stick"> | **Carrot on a Stick** | `carrot_on_a_stick` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/cooked_chicken.png" width="32" height="32" alt="Cooked Chicken" title="Cooked Chicken"> | **Cooked Chicken** | `cooked_chicken` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/cooked_cod.png" width="32" height="32" alt="Cooked Cod" title="Cooked Cod"> | **Cooked Cod** | `cooked_cod` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/cooked_mutton.png" width="32" height="32" alt="Cooked Mutton" title="Cooked Mutton"> | **Cooked Mutton** | `cooked_mutton` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/cooked_porkchop.png" width="32" height="32" alt="Cooked Porkchop" title="Cooked Porkchop"> | **Cooked Porkchop** | `cooked_porkchop` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/cooked_rabbit.png" width="32" height="32" alt="Cooked Rabbit" title="Cooked Rabbit"> | **Cooked Rabbit** | `cooked_rabbit` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/cooked_salmon.png" width="32" height="32" alt="Cooked Salmon" title="Cooked Salmon"> | **Cooked Salmon** | `cooked_salmon` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/cookie.png" width="32" height="32" alt="Cookie" title="Cookie"> | **Cookie** | `cookie` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/dried_kelp.png" width="32" height="32" alt="Dried Kelp" title="Dried Kelp"> | **Dried Kelp** | `dried_kelp` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/dried_kelp_block.png" width="32" height="32" alt="Dried Kelp Block" title="Dried Kelp Block"> | **Dried Kelp Block** | `dried_kelp_block` | 7,200 | 5,700 | Buy / Sell | - |
| <img src="assets/icons/enchanted_golden_apple.png" width="32" height="32" alt="Enchanted Golden Apple" title="Enchanted Golden Apple"> | **Enchanted Golden Apple** | `enchanted_golden_apple` | 200,000 | 160,000 | Buy / Sell | - |
| <img src="assets/icons/fishing_rod.png" width="32" height="32" alt="Fishing Rod" title="Fishing Rod"> | **Fishing Rod** | `fishing_rod` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/glistering_melon_slice.png" width="32" height="32" alt="Glistering Melon Slice" title="Glistering Melon Slice"> | **Glistering Melon Slice** | `glistering_melon_slice` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/glow_berries.png" width="32" height="32" alt="Glow Berries" title="Glow Berries"> | **Glow Berries** | `glow_berries` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/golden_apple.png" width="32" height="32" alt="Golden Apple" title="Golden Apple"> | **Golden Apple** | `golden_apple` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/golden_carrot.png" width="32" height="32" alt="Golden Carrot" title="Golden Carrot"> | **Golden Carrot** | `golden_carrot` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/honey_bottle.png" width="32" height="32" alt="Honey Bottle" title="Honey Bottle"> | **Honey Bottle** | `honey_bottle` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/kelp.png" width="32" height="32" alt="Kelp" title="Kelp"> | **Kelp** | `kelp` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/melon_slice.png" width="32" height="32" alt="Melon Slice" title="Melon Slice"> | **Melon Slice** | `melon_slice` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/milk_bucket.png" width="32" height="32" alt="Milk Bucket" title="Milk Bucket"> | **Milk Bucket** | `milk_bucket` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/mushroom_stew.png" width="32" height="32" alt="Mushroom Stew" title="Mushroom Stew"> | **Mushroom Stew** | `mushroom_stew` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/music_disc_lava_chicken.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_lava_chicken` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/poisonous_potato.png" width="32" height="32" alt="Poisonous Potato" title="Poisonous Potato"> | **Poisonous Potato** | `poisonous_potato` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/potato.png" width="32" height="32" alt="Potato" title="Potato"> | **Potato** | `potato` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/pufferfish.png" width="32" height="32" alt="Pufferfish" title="Pufferfish"> | **Pufferfish** | `pufferfish` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/rabbit_hide.png" width="32" height="32" alt="Rabbit Hide" title="Rabbit Hide"> | **Rabbit Hide** | `rabbit_hide` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/rabbit_stew.png" width="32" height="32" alt="Rabbit Stew" title="Rabbit Stew"> | **Rabbit Stew** | `rabbit_stew` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/rabbit_foot.png" width="32" height="32" alt="Rabbit's Foot" title="Rabbit's Foot"> | **Rabbit's Foot** | `rabbit_foot` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/beef.png" width="32" height="32" alt="Raw Beef" title="Raw Beef"> | **Raw Beef** | `beef` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/chicken.png" width="32" height="32" alt="Raw Chicken" title="Raw Chicken"> | **Raw Chicken** | `chicken` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/cod.png" width="32" height="32" alt="Raw Cod" title="Raw Cod"> | **Raw Cod** | `cod` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/mutton.png" width="32" height="32" alt="Raw Mutton" title="Raw Mutton"> | **Raw Mutton** | `mutton` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/porkchop.png" width="32" height="32" alt="Raw Porkchop" title="Raw Porkchop"> | **Raw Porkchop** | `porkchop` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/rabbit.png" width="32" height="32" alt="Raw Rabbit" title="Raw Rabbit"> | **Raw Rabbit** | `rabbit` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/salmon.png" width="32" height="32" alt="Raw Salmon" title="Raw Salmon"> | **Raw Salmon** | `salmon` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/cooked_beef.png" width="32" height="32" alt="Steak" title="Steak"> | **Steak** | `cooked_beef` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/suspicious_stew.png" width="32" height="32" alt="Suspicious Stew" title="Suspicious Stew"> | **Suspicious Stew** | `suspicious_stew` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/sweet_berries.png" width="32" height="32" alt="Sweet Berries" title="Sweet Berries"> | **Sweet Berries** | `sweet_berries` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/tropical_fish.png" width="32" height="32" alt="Tropical Fish" title="Tropical Fish"> | **Tropical Fish** | `tropical_fish` | 800 | 600 | Buy / Sell | - |

## Redstone

_49 items, 49 buyable, 49 sellable._

| Icon | Item | ID | Buy | Sell | Trade | Notes |
|:---:|:--|:--|--:|--:|:--:|:--|
| <img src="assets/icons/acacia_button.png" width="32" height="32" alt="Acacia Button" title="Acacia Button"> | **Acacia Button** | `acacia_button` | 1,200 | 200 | Buy / Sell | - |
| <img src="assets/icons/acacia_pressure_plate.png" width="32" height="32" alt="Acacia Pressure Plate" title="Acacia Pressure Plate"> | **Acacia Pressure Plate** | `acacia_pressure_plate` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/activator_rail.png" width="32" height="32" alt="Activator Rail" title="Activator Rail"> | **Activator Rail** | `activator_rail` | 8 | 6 | Buy / Sell | - |
| <img src="assets/icons/bamboo_button.png" width="32" height="32" alt="Bamboo Button" title="Bamboo Button"> | **Bamboo Button** | `bamboo_button` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/bamboo_pressure_plate.png" width="32" height="32" alt="Bamboo Pressure Plate" title="Bamboo Pressure Plate"> | **Bamboo Pressure Plate** | `bamboo_pressure_plate` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/birch_button.png" width="32" height="32" alt="Birch Button" title="Birch Button"> | **Birch Button** | `birch_button` | 1,200 | 200 | Buy / Sell | - |
| <img src="assets/icons/birch_pressure_plate.png" width="32" height="32" alt="Birch Pressure Plate" title="Birch Pressure Plate"> | **Birch Pressure Plate** | `birch_pressure_plate` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/cherry_button.png" width="32" height="32" alt="Cherry Button" title="Cherry Button"> | **Cherry Button** | `cherry_button` | 1,200 | 200 | Buy / Sell | - |
| <img src="assets/icons/cherry_pressure_plate.png" width="32" height="32" alt="Cherry Pressure Plate" title="Cherry Pressure Plate"> | **Cherry Pressure Plate** | `cherry_pressure_plate` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/crimson_button.png" width="32" height="32" alt="Crimson Button" title="Crimson Button"> | **Crimson Button** | `crimson_button` | 1,200 | 200 | Buy / Sell | - |
| <img src="assets/icons/crimson_pressure_plate.png" width="32" height="32" alt="Crimson Pressure Plate" title="Crimson Pressure Plate"> | **Crimson Pressure Plate** | `crimson_pressure_plate` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_button.png" width="32" height="32" alt="Dark Oak Button" title="Dark Oak Button"> | **Dark Oak Button** | `dark_oak_button` | 1,200 | 200 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_pressure_plate.png" width="32" height="32" alt="Dark Oak Pressure Plate" title="Dark Oak Pressure Plate"> | **Dark Oak Pressure Plate** | `dark_oak_pressure_plate` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/detector_rail.png" width="32" height="32" alt="Detector Rail" title="Detector Rail"> | **Detector Rail** | `detector_rail` | 8 | 6 | Buy / Sell | - |
| <img src="assets/icons/dispenser.png" width="32" height="32" alt="Dispenser" title="Dispenser"> | **Dispenser** | `dispenser` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/dropper.png" width="32" height="32" alt="Dropper" title="Dropper"> | **Dropper** | `dropper` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/heavy_weighted_pressure_plate.png" width="32" height="32" alt="Heavy Weighted Pressure Plate" title="Heavy Weighted Pressure Plate"> | **Heavy Weighted Pressure Plate** | `heavy_weighted_pressure_plate` | 8,000 | 6,400 | Buy / Sell | - |
| <img src="assets/icons/hopper.png" width="32" height="32" alt="Hopper" title="Hopper"> | **Hopper** | `hopper` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/jungle_button.png" width="32" height="32" alt="Jungle Button" title="Jungle Button"> | **Jungle Button** | `jungle_button` | 1,200 | 200 | Buy / Sell | - |
| <img src="assets/icons/jungle_pressure_plate.png" width="32" height="32" alt="Jungle Pressure Plate" title="Jungle Pressure Plate"> | **Jungle Pressure Plate** | `jungle_pressure_plate` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/lever.png" width="32" height="32" alt="Lever" title="Lever"> | **Lever** | `lever` | 1,000 | 110 | Buy / Sell | - |
| <img src="assets/icons/light_weighted_pressure_plate.png" width="32" height="32" alt="Light Weighted Pressure Plate" title="Light Weighted Pressure Plate"> | **Light Weighted Pressure Plate** | `light_weighted_pressure_plate` | 16,000 | 12,800 | Buy / Sell | - |
| <img src="assets/icons/mangrove_button.png" width="32" height="32" alt="Mangrove Button" title="Mangrove Button"> | **Mangrove Button** | `mangrove_button` | 1,200 | 200 | Buy / Sell | - |
| <img src="assets/icons/mangrove_pressure_plate.png" width="32" height="32" alt="Mangrove Pressure Plate" title="Mangrove Pressure Plate"> | **Mangrove Pressure Plate** | `mangrove_pressure_plate` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/hopper_minecart.png" width="32" height="32" alt="Minecart with Hopper" title="Minecart with Hopper"> | **Minecart with Hopper** | `hopper_minecart` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/oak_button.png" width="32" height="32" alt="Oak Button" title="Oak Button"> | **Oak Button** | `oak_button` | 1,200 | 200 | Buy / Sell | - |
| <img src="assets/icons/oak_pressure_plate.png" width="32" height="32" alt="Oak Pressure Plate" title="Oak Pressure Plate"> | **Oak Pressure Plate** | `oak_pressure_plate` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/observer.png" width="32" height="32" alt="Observer" title="Observer"> | **Observer** | `observer` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_button.png" width="32" height="32" alt="Pale Oak Button" title="Pale Oak Button"> | **Pale Oak Button** | `pale_oak_button` | 1,200 | 200 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_pressure_plate.png" width="32" height="32" alt="Pale Oak Pressure Plate" title="Pale Oak Pressure Plate"> | **Pale Oak Pressure Plate** | `pale_oak_pressure_plate` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/piston.png" width="32" height="32" alt="Piston" title="Piston"> | **Piston** | `piston` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/polished_blackstone_button.png" width="32" height="32" alt="Polished Blackstone Button" title="Polished Blackstone Button"> | **Polished Blackstone Button** | `polished_blackstone_button` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/polished_blackstone_pressure_plate.png" width="32" height="32" alt="Polished Blackstone Pressure Plate" title="Polished Blackstone Pressure Plate"> | **Polished Blackstone Pressure Plate** | `polished_blackstone_pressure_plate` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/poplar_button.png" width="32" height="32" alt="Poplar Button" title="Poplar Button"> | **Poplar Button** | `poplar_button` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/poplar_pressure_plate.png" width="32" height="32" alt="Poplar Pressure Plate" title="Poplar Pressure Plate"> | **Poplar Pressure Plate** | `poplar_pressure_plate` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/powered_rail.png" width="32" height="32" alt="Powered Rail" title="Powered Rail"> | **Powered Rail** | `powered_rail` | 8 | 6 | Buy / Sell | - |
| <img src="assets/icons/rail.png" width="32" height="32" alt="Rail" title="Rail"> | **Rail** | `rail` | 5 | 4 | Buy / Sell | - |
| <img src="assets/icons/comparator.png" width="32" height="32" alt="Redstone Comparator" title="Redstone Comparator"> | **Redstone Comparator** | `comparator` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/redstone.png" width="32" height="32" alt="Redstone Dust" title="Redstone Dust"> | **Redstone Dust** | `redstone` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/redstone_lamp.png" width="32" height="32" alt="Redstone Lamp" title="Redstone Lamp"> | **Redstone Lamp** | `redstone_lamp` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/repeater.png" width="32" height="32" alt="Redstone Repeater" title="Redstone Repeater"> | **Redstone Repeater** | `repeater` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/redstone_torch.png" width="32" height="32" alt="Redstone Torch" title="Redstone Torch"> | **Redstone Torch** | `redstone_torch` | 500 | 400 | Buy / Sell | - |
| <img src="assets/icons/spruce_button.png" width="32" height="32" alt="Spruce Button" title="Spruce Button"> | **Spruce Button** | `spruce_button` | 1,200 | 200 | Buy / Sell | - |
| <img src="assets/icons/spruce_pressure_plate.png" width="32" height="32" alt="Spruce Pressure Plate" title="Spruce Pressure Plate"> | **Spruce Pressure Plate** | `spruce_pressure_plate` | 1,600 | 400 | Buy / Sell | - |
| <img src="assets/icons/sticky_piston.png" width="32" height="32" alt="Sticky Piston" title="Sticky Piston"> | **Sticky Piston** | `sticky_piston` | 10,000 | 3,500 | Buy / Sell | - |
| <img src="assets/icons/stone_button.png" width="32" height="32" alt="Stone Button" title="Stone Button"> | **Stone Button** | `stone_button` | 1,200 | 10 | Buy / Sell | - |
| <img src="assets/icons/stone_pressure_plate.png" width="32" height="32" alt="Stone Pressure Plate" title="Stone Pressure Plate"> | **Stone Pressure Plate** | `stone_pressure_plate` | 2,000 | 20 | Buy / Sell | - |
| <img src="assets/icons/warped_button.png" width="32" height="32" alt="Warped Button" title="Warped Button"> | **Warped Button** | `warped_button` | 1,200 | 200 | Buy / Sell | - |
| <img src="assets/icons/warped_pressure_plate.png" width="32" height="32" alt="Warped Pressure Plate" title="Warped Pressure Plate"> | **Warped Pressure Plate** | `warped_pressure_plate` | 1,600 | 400 | Buy / Sell | - |

## Tools & Weapons

_42 items, 42 buyable, 42 sellable._

| Icon | Item | ID | Buy | Sell | Trade | Notes |
|:---:|:--|:--|--:|--:|:--:|:--|
| <img src="assets/icons/copper_axe.png" width="32" height="32" alt="Copper Axe" title="Copper Axe"> | **Copper Axe** | `copper_axe` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/copper_hoe.png" width="32" height="32" alt="Copper Hoe" title="Copper Hoe"> | **Copper Hoe** | `copper_hoe` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/copper_pickaxe.png" width="32" height="32" alt="Copper Pickaxe" title="Copper Pickaxe"> | **Copper Pickaxe** | `copper_pickaxe` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/copper_shovel.png" width="32" height="32" alt="Copper Shovel" title="Copper Shovel"> | **Copper Shovel** | `copper_shovel` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/copper_spear.png" width="32" height="32" alt="Copper Spear" title="Copper Spear"> | **Copper Spear** | `copper_spear` | 15,000 | 12,000 | Buy / Sell | - |
| <img src="assets/icons/copper_sword.png" width="32" height="32" alt="Copper Sword" title="Copper Sword"> | **Copper Sword** | `copper_sword` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/diamond_axe.png" width="32" height="32" alt="Diamond Axe" title="Diamond Axe"> | **Diamond Axe** | `diamond_axe` | 40,000 | 32,000 | Buy / Sell | - |
| <img src="assets/icons/diamond_hoe.png" width="32" height="32" alt="Diamond Hoe" title="Diamond Hoe"> | **Diamond Hoe** | `diamond_hoe` | 40,000 | 32,000 | Buy / Sell | - |
| <img src="assets/icons/diamond_pickaxe.png" width="32" height="32" alt="Diamond Pickaxe" title="Diamond Pickaxe"> | **Diamond Pickaxe** | `diamond_pickaxe` | 40,000 | 32,000 | Buy / Sell | - |
| <img src="assets/icons/diamond_shovel.png" width="32" height="32" alt="Diamond Shovel" title="Diamond Shovel"> | **Diamond Shovel** | `diamond_shovel` | 40,000 | 30,200 | Buy / Sell | - |
| <img src="assets/icons/diamond_spear.png" width="32" height="32" alt="Diamond Spear" title="Diamond Spear"> | **Diamond Spear** | `diamond_spear` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/diamond_sword.png" width="32" height="32" alt="Diamond Sword" title="Diamond Sword"> | **Diamond Sword** | `diamond_sword` | 40,000 | 32,000 | Buy / Sell | - |
| <img src="assets/icons/golden_axe.png" width="32" height="32" alt="Golden Axe" title="Golden Axe"> | **Golden Axe** | `golden_axe` | 15,000 | 12,000 | Buy / Sell | - |
| <img src="assets/icons/golden_hoe.png" width="32" height="32" alt="Golden Hoe" title="Golden Hoe"> | **Golden Hoe** | `golden_hoe` | 15,000 | 12,000 | Buy / Sell | - |
| <img src="assets/icons/golden_pickaxe.png" width="32" height="32" alt="Golden Pickaxe" title="Golden Pickaxe"> | **Golden Pickaxe** | `golden_pickaxe` | 15,000 | 12,000 | Buy / Sell | - |
| <img src="assets/icons/golden_shovel.png" width="32" height="32" alt="Golden Shovel" title="Golden Shovel"> | **Golden Shovel** | `golden_shovel` | 15,000 | 8,200 | Buy / Sell | - |
| <img src="assets/icons/golden_spear.png" width="32" height="32" alt="Golden Spear" title="Golden Spear"> | **Golden Spear** | `golden_spear` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/golden_sword.png" width="32" height="32" alt="Golden Sword" title="Golden Sword"> | **Golden Sword** | `golden_sword` | 15,000 | 12,000 | Buy / Sell | - |
| <img src="assets/icons/iron_axe.png" width="32" height="32" alt="Iron Axe" title="Iron Axe"> | **Iron Axe** | `iron_axe` | 8,000 | 6,400 | Buy / Sell | - |
| <img src="assets/icons/iron_hoe.png" width="32" height="32" alt="Iron Hoe" title="Iron Hoe"> | **Iron Hoe** | `iron_hoe` | 8,000 | 6,400 | Buy / Sell | - |
| <img src="assets/icons/iron_pickaxe.png" width="32" height="32" alt="Iron Pickaxe" title="Iron Pickaxe"> | **Iron Pickaxe** | `iron_pickaxe` | 8,000 | 6,400 | Buy / Sell | - |
| <img src="assets/icons/iron_shovel.png" width="32" height="32" alt="Iron Shovel" title="Iron Shovel"> | **Iron Shovel** | `iron_shovel` | 8,000 | 4,200 | Buy / Sell | - |
| <img src="assets/icons/iron_spear.png" width="32" height="32" alt="Iron Spear" title="Iron Spear"> | **Iron Spear** | `iron_spear` | 30,000 | 24,000 | Buy / Sell | - |
| <img src="assets/icons/iron_sword.png" width="32" height="32" alt="Iron Sword" title="Iron Sword"> | **Iron Sword** | `iron_sword` | 8,000 | 6,400 | Buy / Sell | - |
| <img src="assets/icons/netherite_axe.png" width="32" height="32" alt="Netherite Axe" title="Netherite Axe"> | **Netherite Axe** | `netherite_axe` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/netherite_hoe.png" width="32" height="32" alt="Netherite Hoe" title="Netherite Hoe"> | **Netherite Hoe** | `netherite_hoe` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/netherite_pickaxe.png" width="32" height="32" alt="Netherite Pickaxe" title="Netherite Pickaxe"> | **Netherite Pickaxe** | `netherite_pickaxe` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/netherite_shovel.png" width="32" height="32" alt="Netherite Shovel" title="Netherite Shovel"> | **Netherite Shovel** | `netherite_shovel` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/netherite_spear.png" width="32" height="32" alt="Netherite Spear" title="Netherite Spear"> | **Netherite Spear** | `netherite_spear` | 400,000 | 320,000 | Buy / Sell | - |
| <img src="assets/icons/netherite_sword.png" width="32" height="32" alt="Netherite Sword" title="Netherite Sword"> | **Netherite Sword** | `netherite_sword` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/stone_axe.png" width="32" height="32" alt="Stone Axe" title="Stone Axe"> | **Stone Axe** | `stone_axe` | 6,000 | 230 | Buy / Sell | - |
| <img src="assets/icons/stone_hoe.png" width="32" height="32" alt="Stone Hoe" title="Stone Hoe"> | **Stone Hoe** | `stone_hoe` | 6,000 | 220 | Buy / Sell | - |
| <img src="assets/icons/stone_pickaxe.png" width="32" height="32" alt="Stone Pickaxe" title="Stone Pickaxe"> | **Stone Pickaxe** | `stone_pickaxe` | 6,000 | 230 | Buy / Sell | - |
| <img src="assets/icons/stone_shovel.png" width="32" height="32" alt="Stone Shovel" title="Stone Shovel"> | **Stone Shovel** | `stone_shovel` | 6,000 | 210 | Buy / Sell | - |
| <img src="assets/icons/stone_spear.png" width="32" height="32" alt="Stone Spear" title="Stone Spear"> | **Stone Spear** | `stone_spear` | 6,000 | 4,800 | Buy / Sell | - |
| <img src="assets/icons/stone_sword.png" width="32" height="32" alt="Stone Sword" title="Stone Sword"> | **Stone Sword** | `stone_sword` | 6,000 | 120 | Buy / Sell | - |
| <img src="assets/icons/wooden_axe.png" width="32" height="32" alt="Wooden Axe" title="Wooden Axe"> | **Wooden Axe** | `wooden_axe` | 2,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/wooden_hoe.png" width="32" height="32" alt="Wooden Hoe" title="Wooden Hoe"> | **Wooden Hoe** | `wooden_hoe` | 2,000 | 600 | Buy / Sell | - |
| <img src="assets/icons/wooden_pickaxe.png" width="32" height="32" alt="Wooden Pickaxe" title="Wooden Pickaxe"> | **Wooden Pickaxe** | `wooden_pickaxe` | 2,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/wooden_shovel.png" width="32" height="32" alt="Wooden Shovel" title="Wooden Shovel"> | **Wooden Shovel** | `wooden_shovel` | 2,000 | 400 | Buy / Sell | - |
| <img src="assets/icons/wooden_spear.png" width="32" height="32" alt="Wooden Spear" title="Wooden Spear"> | **Wooden Spear** | `wooden_spear` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/wooden_sword.png" width="32" height="32" alt="Wooden Sword" title="Wooden Sword"> | **Wooden Sword** | `wooden_sword` | 2,000 | 500 | Buy / Sell | - |

## Armor

_29 items, 29 buyable, 29 sellable._

| Icon | Item | ID | Buy | Sell | Trade | Notes |
|:---:|:--|:--|--:|--:|:--:|:--|
| <img src="assets/icons/chainmail_boots.png" width="32" height="32" alt="Chainmail Boots" title="Chainmail Boots"> | **Chainmail Boots** | `chainmail_boots` | 25,000 | 20,000 | Buy / Sell | - |
| <img src="assets/icons/chainmail_chestplate.png" width="32" height="32" alt="Chainmail Chestplate" title="Chainmail Chestplate"> | **Chainmail Chestplate** | `chainmail_chestplate` | 25,000 | 20,000 | Buy / Sell | - |
| <img src="assets/icons/chainmail_helmet.png" width="32" height="32" alt="Chainmail Helmet" title="Chainmail Helmet"> | **Chainmail Helmet** | `chainmail_helmet` | 25,000 | 20,000 | Buy / Sell | - |
| <img src="assets/icons/chainmail_leggings.png" width="32" height="32" alt="Chainmail Leggings" title="Chainmail Leggings"> | **Chainmail Leggings** | `chainmail_leggings` | 25,000 | 20,000 | Buy / Sell | - |
| <img src="assets/icons/copper_boots.png" width="32" height="32" alt="Copper Boots" title="Copper Boots"> | **Copper Boots** | `copper_boots` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/copper_chestplate.png" width="32" height="32" alt="Copper Chestplate" title="Copper Chestplate"> | **Copper Chestplate** | `copper_chestplate` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/copper_helmet.png" width="32" height="32" alt="Copper Helmet" title="Copper Helmet"> | **Copper Helmet** | `copper_helmet` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/copper_leggings.png" width="32" height="32" alt="Copper Leggings" title="Copper Leggings"> | **Copper Leggings** | `copper_leggings` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/diamond_boots.png" width="32" height="32" alt="Diamond Boots" title="Diamond Boots"> | **Diamond Boots** | `diamond_boots` | 120,000 | 96,000 | Buy / Sell | - |
| <img src="assets/icons/diamond_chestplate.png" width="32" height="32" alt="Diamond Chestplate" title="Diamond Chestplate"> | **Diamond Chestplate** | `diamond_chestplate` | 240,000 | 192,000 | Buy / Sell | - |
| <img src="assets/icons/diamond_helmet.png" width="32" height="32" alt="Diamond Helmet" title="Diamond Helmet"> | **Diamond Helmet** | `diamond_helmet` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/diamond_leggings.png" width="32" height="32" alt="Diamond Leggings" title="Diamond Leggings"> | **Diamond Leggings** | `diamond_leggings` | 210,000 | 168,000 | Buy / Sell | - |
| <img src="assets/icons/golden_boots.png" width="32" height="32" alt="Golden Boots" title="Golden Boots"> | **Golden Boots** | `golden_boots` | 32,000 | 25,600 | Buy / Sell | - |
| <img src="assets/icons/golden_chestplate.png" width="32" height="32" alt="Golden Chestplate" title="Golden Chestplate"> | **Golden Chestplate** | `golden_chestplate` | 64,000 | 51,200 | Buy / Sell | - |
| <img src="assets/icons/golden_helmet.png" width="32" height="32" alt="Golden Helmet" title="Golden Helmet"> | **Golden Helmet** | `golden_helmet` | 40,000 | 32,000 | Buy / Sell | - |
| <img src="assets/icons/golden_leggings.png" width="32" height="32" alt="Golden Leggings" title="Golden Leggings"> | **Golden Leggings** | `golden_leggings` | 56,000 | 44,800 | Buy / Sell | - |
| <img src="assets/icons/iron_boots.png" width="32" height="32" alt="Iron Boots" title="Iron Boots"> | **Iron Boots** | `iron_boots` | 16,000 | 12,800 | Buy / Sell | - |
| <img src="assets/icons/iron_chestplate.png" width="32" height="32" alt="Iron Chestplate" title="Iron Chestplate"> | **Iron Chestplate** | `iron_chestplate` | 32,000 | 25,600 | Buy / Sell | - |
| <img src="assets/icons/iron_helmet.png" width="32" height="32" alt="Iron Helmet" title="Iron Helmet"> | **Iron Helmet** | `iron_helmet` | 20,000 | 16,000 | Buy / Sell | - |
| <img src="assets/icons/iron_leggings.png" width="32" height="32" alt="Iron Leggings" title="Iron Leggings"> | **Iron Leggings** | `iron_leggings` | 28,000 | 22,400 | Buy / Sell | - |
| <img src="assets/icons/leather_boots.png" width="32" height="32" alt="Leather Boots" title="Leather Boots"> | **Leather Boots** | `leather_boots` | 40,000 | 32,000 | Buy / Sell | - |
| <img src="assets/icons/leather_helmet.png" width="32" height="32" alt="Leather Cap" title="Leather Cap"> | **Leather Cap** | `leather_helmet` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/leather_leggings.png" width="32" height="32" alt="Leather Pants" title="Leather Pants"> | **Leather Pants** | `leather_leggings` | 70,000 | 56,000 | Buy / Sell | - |
| <img src="assets/icons/leather_chestplate.png" width="32" height="32" alt="Leather Tunic" title="Leather Tunic"> | **Leather Tunic** | `leather_chestplate` | 80,000 | 64,000 | Buy / Sell | - |
| <img src="assets/icons/netherite_boots.png" width="32" height="32" alt="Netherite Boots" title="Netherite Boots"> | **Netherite Boots** | `netherite_boots` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/netherite_chestplate.png" width="32" height="32" alt="Netherite Chestplate" title="Netherite Chestplate"> | **Netherite Chestplate** | `netherite_chestplate` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/netherite_helmet.png" width="32" height="32" alt="Netherite Helmet" title="Netherite Helmet"> | **Netherite Helmet** | `netherite_helmet` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/netherite_leggings.png" width="32" height="32" alt="Netherite Leggings" title="Netherite Leggings"> | **Netherite Leggings** | `netherite_leggings` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/turtle_helmet.png" width="32" height="32" alt="Turtle Shell" title="Turtle Shell"> | **Turtle Shell** | `turtle_helmet` | 50,000 | 12,500 | Buy / Sell | - |

## Miscellaneous

_280 items, 280 buyable, 280 sellable._

| Icon | Item | ID | Buy | Sell | Trade | Notes |
|:---:|:--|:--|--:|--:|:--:|:--|
| <img src="assets/icons/abandoned_camp_map.png" width="32" height="32" alt="Abandoned Camp Map" title="Abandoned Camp Map"> | **Abandoned Camp Map** | `abandoned_camp_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/acacia_boat.png" width="32" height="32" alt="Acacia Boat" title="Acacia Boat"> | **Acacia Boat** | `acacia_boat` | 4,000 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/acacia_chest_boat.png" width="32" height="32" alt="Acacia Boat with Chest" title="Acacia Boat with Chest"> | **Acacia Boat with Chest** | `acacia_chest_boat` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/amethyst_shard.png" width="32" height="32" alt="Amethyst Shard" title="Amethyst Shard"> | **Amethyst Shard** | `amethyst_shard` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/angler_pottery_sherd.png" width="32" height="32" alt="Angler Pottery Sherd" title="Angler Pottery Sherd"> | **Angler Pottery Sherd** | `angler_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/archer_pottery_sherd.png" width="32" height="32" alt="Archer Pottery Sherd" title="Archer Pottery Sherd"> | **Archer Pottery Sherd** | `archer_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/armadillo_scute.png" width="32" height="32" alt="Armadillo Scute" title="Armadillo Scute"> | **Armadillo Scute** | `armadillo_scute` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/armor_stand.png" width="32" height="32" alt="Armor Stand" title="Armor Stand"> | **Armor Stand** | `armor_stand` | 2,500 | 605 | Buy / Sell | - |
| <img src="assets/icons/arms_up_pottery_sherd.png" width="32" height="32" alt="Arms Up Pottery Sherd" title="Arms Up Pottery Sherd"> | **Arms Up Pottery Sherd** | `arms_up_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/arrow.png" width="32" height="32" alt="Arrow" title="Arrow"> | **Arrow** | `arrow` | 2,500 | 1,200 | Buy / Sell | - |
| <img src="assets/icons/bamboo_raft.png" width="32" height="32" alt="Bamboo Raft" title="Bamboo Raft"> | **Bamboo Raft** | `bamboo_raft` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/bamboo_chest_raft.png" width="32" height="32" alt="Bamboo Raft with Chest" title="Bamboo Raft with Chest"> | **Bamboo Raft with Chest** | `bamboo_chest_raft` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/creeper_banner_pattern.png" width="32" height="32" alt="Banner Pattern" title="Banner Pattern"> | **Banner Pattern** | `creeper_banner_pattern` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/flow_banner_pattern.png" width="32" height="32" alt="Banner Pattern" title="Banner Pattern"> | **Banner Pattern** | `flow_banner_pattern` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/flower_banner_pattern.png" width="32" height="32" alt="Banner Pattern" title="Banner Pattern"> | **Banner Pattern** | `flower_banner_pattern` | 100,000 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/globe_banner_pattern.png" width="32" height="32" alt="Banner Pattern" title="Banner Pattern"> | **Banner Pattern** | `globe_banner_pattern` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/guster_banner_pattern.png" width="32" height="32" alt="Banner Pattern" title="Banner Pattern"> | **Banner Pattern** | `guster_banner_pattern` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/mojang_banner_pattern.png" width="32" height="32" alt="Banner Pattern" title="Banner Pattern"> | **Banner Pattern** | `mojang_banner_pattern` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/piglin_banner_pattern.png" width="32" height="32" alt="Banner Pattern" title="Banner Pattern"> | **Banner Pattern** | `piglin_banner_pattern` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/skull_banner_pattern.png" width="32" height="32" alt="Banner Pattern" title="Banner Pattern"> | **Banner Pattern** | `skull_banner_pattern` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/birch_boat.png" width="32" height="32" alt="Birch Boat" title="Birch Boat"> | **Birch Boat** | `birch_boat` | 4,000 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/birch_chest_boat.png" width="32" height="32" alt="Birch Boat with Chest" title="Birch Boat with Chest"> | **Birch Boat with Chest** | `birch_chest_boat` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/black_bundle.png" width="32" height="32" alt="Black Bundle" title="Black Bundle"> | **Black Bundle** | `black_bundle` | 5,000 | 3,500 | Buy / Sell | - |
| <img src="assets/icons/black_cushion.png" width="32" height="32" alt="Black Cushion" title="Black Cushion"> | **Black Cushion** | `black_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/black_dye.png" width="32" height="32" alt="Black Dye" title="Black Dye"> | **Black Dye** | `black_dye` | 2,500 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/black_harness.png" width="32" height="32" alt="Black Harness" title="Black Harness"> | **Black Harness** | `black_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/blade_pottery_sherd.png" width="32" height="32" alt="Blade Pottery Sherd" title="Blade Pottery Sherd"> | **Blade Pottery Sherd** | `blade_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/blue_bundle.png" width="32" height="32" alt="Blue Bundle" title="Blue Bundle"> | **Blue Bundle** | `blue_bundle` | 5,000 | 3,500 | Buy / Sell | - |
| <img src="assets/icons/blue_cushion.png" width="32" height="32" alt="Blue Cushion" title="Blue Cushion"> | **Blue Cushion** | `blue_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/blue_dye.png" width="32" height="32" alt="Blue Dye" title="Blue Dye"> | **Blue Dye** | `blue_dye` | 2,500 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/blue_egg.png" width="32" height="32" alt="Blue Egg" title="Blue Egg"> | **Blue Egg** | `blue_egg` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/blue_harness.png" width="32" height="32" alt="Blue Harness" title="Blue Harness"> | **Blue Harness** | `blue_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/bone.png" width="32" height="32" alt="Bone" title="Bone"> | **Bone** | `bone` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/bone_meal.png" width="32" height="32" alt="Bone Meal" title="Bone Meal"> | **Bone Meal** | `bone_meal` | 2,500 | 800 | Buy / Sell | - |
| <img src="assets/icons/book.png" width="32" height="32" alt="Book" title="Book"> | **Book** | `book` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/writable_book.png" width="32" height="32" alt="Book and Quill" title="Book and Quill"> | **Book and Quill** | `writable_book` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/bordure_indented_banner_pattern.png" width="32" height="32" alt="Bordure Indented Banner Pattern" title="Bordure Indented Banner Pattern"> | **Bordure Indented Banner Pattern** | `bordure_indented_banner_pattern` | 100,000 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/experience_bottle.png" width="32" height="32" alt="Bottle o' Enchanting" title="Bottle o' Enchanting"> | **Bottle o' Enchanting** | `experience_bottle` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/bow.png" width="32" height="32" alt="Bow" title="Bow"> | **Bow** | `bow` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/bowl.png" width="32" height="32" alt="Bowl" title="Bowl"> | **Bowl** | `bowl` | 2,500 | 100 | Buy / Sell | - |
| <img src="assets/icons/breeze_rod.png" width="32" height="32" alt="Breeze Rod" title="Breeze Rod"> | **Breeze Rod** | `breeze_rod` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/brewer_pottery_sherd.png" width="32" height="32" alt="Brewer Pottery Sherd" title="Brewer Pottery Sherd"> | **Brewer Pottery Sherd** | `brewer_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/brick.png" width="32" height="32" alt="Brick" title="Brick"> | **Brick** | `brick` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/brown_bundle.png" width="32" height="32" alt="Brown Bundle" title="Brown Bundle"> | **Brown Bundle** | `brown_bundle` | 5,000 | 3,500 | Buy / Sell | - |
| <img src="assets/icons/brown_cushion.png" width="32" height="32" alt="Brown Cushion" title="Brown Cushion"> | **Brown Cushion** | `brown_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/brown_dye.png" width="32" height="32" alt="Brown Dye" title="Brown Dye"> | **Brown Dye** | `brown_dye` | 2,500 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/brown_egg.png" width="32" height="32" alt="Brown Egg" title="Brown Egg"> | **Brown Egg** | `brown_egg` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/brown_harness.png" width="32" height="32" alt="Brown Harness" title="Brown Harness"> | **Brown Harness** | `brown_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/brush.png" width="32" height="32" alt="Brush" title="Brush"> | **Brush** | `brush` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/bucket.png" width="32" height="32" alt="Bucket" title="Bucket"> | **Bucket** | `bucket` | 12,000 | 9,600 | Buy / Sell | - |
| <img src="assets/icons/axolotl_bucket.png" width="32" height="32" alt="Bucket of Axolotl" title="Bucket of Axolotl"> | **Bucket of Axolotl** | `axolotl_bucket` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/sulfur_cube_bucket.png" width="32" height="32" alt="Bucket of Sulfur Cube" title="Bucket of Sulfur Cube"> | **Bucket of Sulfur Cube** | `sulfur_cube_bucket` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/tadpole_bucket.png" width="32" height="32" alt="Bucket of Tadpole" title="Bucket of Tadpole"> | **Bucket of Tadpole** | `tadpole_bucket` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/bundle.png" width="32" height="32" alt="Bundle" title="Bundle"> | **Bundle** | `bundle` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/buried_ancient_city_map.png" width="32" height="32" alt="Buried Ancient City Map" title="Buried Ancient City Map"> | **Buried Ancient City Map** | `buried_ancient_city_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/buried_mineshaft_map.png" width="32" height="32" alt="Buried Mineshaft Map" title="Buried Mineshaft Map"> | **Buried Mineshaft Map** | `buried_mineshaft_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/buried_treasure_map.png" width="32" height="32" alt="Buried Treasure Map" title="Buried Treasure Map"> | **Buried Treasure Map** | `buried_treasure_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/buried_trial_chambers_map.png" width="32" height="32" alt="Buried Trial Chambers Map" title="Buried Trial Chambers Map"> | **Buried Trial Chambers Map** | `buried_trial_chambers_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/burn_pottery_sherd.png" width="32" height="32" alt="Burn Pottery Sherd" title="Burn Pottery Sherd"> | **Burn Pottery Sherd** | `burn_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/charcoal.png" width="32" height="32" alt="Charcoal" title="Charcoal"> | **Charcoal** | `charcoal` | 2,500 | 800 | Buy / Sell | - |
| <img src="assets/icons/cherry_boat.png" width="32" height="32" alt="Cherry Boat" title="Cherry Boat"> | **Cherry Boat** | `cherry_boat` | 4,000 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/cherry_chest_boat.png" width="32" height="32" alt="Cherry Boat with Chest" title="Cherry Boat with Chest"> | **Cherry Boat with Chest** | `cherry_chest_boat` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/clay_ball.png" width="32" height="32" alt="Clay Ball" title="Clay Ball"> | **Clay Ball** | `clay_ball` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/clock.png" width="32" height="32" alt="Clock" title="Clock"> | **Clock** | `clock` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/coal.png" width="32" height="32" alt="Coal" title="Coal"> | **Coal** | `coal` | 1,000 | 800 | Buy / Sell | - |
| <img src="assets/icons/compass.png" width="32" height="32" alt="Compass" title="Compass"> | **Compass** | `compass` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/copper_horse_armor.png" width="32" height="32" alt="Copper Horse Armor" title="Copper Horse Armor"> | **Copper Horse Armor** | `copper_horse_armor` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/copper_nautilus_armor.png" width="32" height="32" alt="Copper Nautilus Armor" title="Copper Nautilus Armor"> | **Copper Nautilus Armor** | `copper_nautilus_armor` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/crossbow.png" width="32" height="32" alt="Crossbow" title="Crossbow"> | **Crossbow** | `crossbow` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/cyan_bundle.png" width="32" height="32" alt="Cyan Bundle" title="Cyan Bundle"> | **Cyan Bundle** | `cyan_bundle` | 5,000 | 3,000 | Buy / Sell | - |
| <img src="assets/icons/cyan_cushion.png" width="32" height="32" alt="Cyan Cushion" title="Cyan Cushion"> | **Cyan Cushion** | `cyan_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/cyan_dye.png" width="32" height="32" alt="Cyan Dye" title="Cyan Dye"> | **Cyan Dye** | `cyan_dye` | 2,500 | 500 | Buy / Sell | - |
| <img src="assets/icons/cyan_harness.png" width="32" height="32" alt="Cyan Harness" title="Cyan Harness"> | **Cyan Harness** | `cyan_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/danger_pottery_sherd.png" width="32" height="32" alt="Danger Pottery Sherd" title="Danger Pottery Sherd"> | **Danger Pottery Sherd** | `danger_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_boat.png" width="32" height="32" alt="Dark Oak Boat" title="Dark Oak Boat"> | **Dark Oak Boat** | `dark_oak_boat` | 4,000 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/dark_oak_chest_boat.png" width="32" height="32" alt="Dark Oak Boat with Chest" title="Dark Oak Boat with Chest"> | **Dark Oak Boat with Chest** | `dark_oak_chest_boat` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/desert_pyramid_map.png" width="32" height="32" alt="Desert Pyramid Map" title="Desert Pyramid Map"> | **Desert Pyramid Map** | `desert_pyramid_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/desert_village_map.png" width="32" height="32" alt="Desert Village Map" title="Desert Village Map"> | **Desert Village Map** | `desert_village_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/diamond.png" width="32" height="32" alt="Diamond" title="Diamond"> | **Diamond** | `diamond` | 30,000 | 24,000 | Buy / Sell | - |
| <img src="assets/icons/diamond_horse_armor.png" width="32" height="32" alt="Diamond Horse Armor" title="Diamond Horse Armor"> | **Diamond Horse Armor** | `diamond_horse_armor` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/diamond_nautilus_armor.png" width="32" height="32" alt="Diamond Nautilus Armor" title="Diamond Nautilus Armor"> | **Diamond Nautilus Armor** | `diamond_nautilus_armor` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/disc_fragment_5.png" width="32" height="32" alt="Disc Fragment" title="Disc Fragment"> | **Disc Fragment** | `disc_fragment_5` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/echo_shard.png" width="32" height="32" alt="Echo Shard" title="Echo Shard"> | **Echo Shard** | `echo_shard` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/egg.png" width="32" height="32" alt="Egg" title="Egg"> | **Egg** | `egg` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/emerald.png" width="32" height="32" alt="Emerald" title="Emerald"> | **Emerald** | `emerald` | 25,000 | 20,000 | Buy / Sell | - |
| <img src="assets/icons/map.png" width="32" height="32" alt="Empty Map" title="Empty Map"> | **Empty Map** | `map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/ender_pearl.png" width="32" height="32" alt="Ender Pearl" title="Ender Pearl"> | **Ender Pearl** | `ender_pearl` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/explorer_pottery_sherd.png" width="32" height="32" alt="Explorer Pottery Sherd" title="Explorer Pottery Sherd"> | **Explorer Pottery Sherd** | `explorer_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/ender_eye.png" width="32" height="32" alt="Eye of Ender" title="Eye of Ender"> | **Eye of Ender** | `ender_eye` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/feather.png" width="32" height="32" alt="Feather" title="Feather"> | **Feather** | `feather` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/fermented_spider_eye.png" width="32" height="32" alt="Fermented Spider Eye" title="Fermented Spider Eye"> | **Fermented Spider Eye** | `fermented_spider_eye` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/field_masoned_banner_pattern.png" width="32" height="32" alt="Field Masoned Banner Pattern" title="Field Masoned Banner Pattern"> | **Field Masoned Banner Pattern** | `field_masoned_banner_pattern` | 100,000 | 11,000 | Buy / Sell | - |
| <img src="assets/icons/fire_charge.png" width="32" height="32" alt="Fire Charge" title="Fire Charge"> | **Fire Charge** | `fire_charge` | 2,500 | 1,800 | Buy / Sell | - |
| <img src="assets/icons/firework_rocket.png" width="32" height="32" alt="Firework Rocket" title="Firework Rocket"> | **Firework Rocket** | `firework_rocket` | 2,500 | 1,100 | Buy / Sell | - |
| <img src="assets/icons/firework_star.png" width="32" height="32" alt="Firework Star" title="Firework Star"> | **Firework Star** | `firework_star` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/flint.png" width="32" height="32" alt="Flint" title="Flint"> | **Flint** | `flint` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/flint_and_steel.png" width="32" height="32" alt="Flint and Steel" title="Flint and Steel"> | **Flint and Steel** | `flint_and_steel` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/flow_pottery_sherd.png" width="32" height="32" alt="Flow Pottery Sherd" title="Flow Pottery Sherd"> | **Flow Pottery Sherd** | `flow_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/glass_bottle.png" width="32" height="32" alt="Glass Bottle" title="Glass Bottle"> | **Glass Bottle** | `glass_bottle` | 2,500 | 10 | Buy / Sell | - |
| <img src="assets/icons/glow_ink_sac.png" width="32" height="32" alt="Glow Ink Sac" title="Glow Ink Sac"> | **Glow Ink Sac** | `glow_ink_sac` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/glow_item_frame.png" width="32" height="32" alt="Glow Item Frame" title="Glow Item Frame"> | **Glow Item Frame** | `glow_item_frame` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/goat_horn.png" width="32" height="32" alt="Goat Horn" title="Goat Horn"> | **Goat Horn** | `goat_horn` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/golden_horse_armor.png" width="32" height="32" alt="Golden Horse Armor" title="Golden Horse Armor"> | **Golden Horse Armor** | `golden_horse_armor` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/golden_nautilus_armor.png" width="32" height="32" alt="Golden Nautilus Armor" title="Golden Nautilus Armor"> | **Golden Nautilus Armor** | `golden_nautilus_armor` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/gray_bundle.png" width="32" height="32" alt="Gray Bundle" title="Gray Bundle"> | **Gray Bundle** | `gray_bundle` | 5,000 | 3,400 | Buy / Sell | - |
| <img src="assets/icons/gray_cushion.png" width="32" height="32" alt="Gray Cushion" title="Gray Cushion"> | **Gray Cushion** | `gray_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/gray_dye.png" width="32" height="32" alt="Gray Dye" title="Gray Dye"> | **Gray Dye** | `gray_dye` | 2,500 | 900 | Buy / Sell | - |
| <img src="assets/icons/gray_harness.png" width="32" height="32" alt="Gray Harness" title="Gray Harness"> | **Gray Harness** | `gray_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/green_bundle.png" width="32" height="32" alt="Green Bundle" title="Green Bundle"> | **Green Bundle** | `green_bundle` | 5,000 | 3,500 | Buy / Sell | - |
| <img src="assets/icons/green_cushion.png" width="32" height="32" alt="Green Cushion" title="Green Cushion"> | **Green Cushion** | `green_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/green_dye.png" width="32" height="32" alt="Green Dye" title="Green Dye"> | **Green Dye** | `green_dye` | 2,500 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/green_harness.png" width="32" height="32" alt="Green Harness" title="Green Harness"> | **Green Harness** | `green_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/gunpowder.png" width="32" height="32" alt="Gunpowder" title="Gunpowder"> | **Gunpowder** | `gunpowder` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/guster_pottery_sherd.png" width="32" height="32" alt="Guster Pottery Sherd" title="Guster Pottery Sherd"> | **Guster Pottery Sherd** | `guster_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/heart_of_the_sea.png" width="32" height="32" alt="Heart of the Sea" title="Heart of the Sea"> | **Heart of the Sea** | `heart_of_the_sea` | 200,000 | 160,000 | Buy / Sell | - |
| <img src="assets/icons/heart_pottery_sherd.png" width="32" height="32" alt="Heart Pottery Sherd" title="Heart Pottery Sherd"> | **Heart Pottery Sherd** | `heart_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/heartbreak_pottery_sherd.png" width="32" height="32" alt="Heartbreak Pottery Sherd" title="Heartbreak Pottery Sherd"> | **Heartbreak Pottery Sherd** | `heartbreak_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/honeycomb.png" width="32" height="32" alt="Honeycomb" title="Honeycomb"> | **Honeycomb** | `honeycomb` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/howl_pottery_sherd.png" width="32" height="32" alt="Howl Pottery Sherd" title="Howl Pottery Sherd"> | **Howl Pottery Sherd** | `howl_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/ink_sac.png" width="32" height="32" alt="Ink Sac" title="Ink Sac"> | **Ink Sac** | `ink_sac` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/iron_horse_armor.png" width="32" height="32" alt="Iron Horse Armor" title="Iron Horse Armor"> | **Iron Horse Armor** | `iron_horse_armor` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/iron_nautilus_armor.png" width="32" height="32" alt="Iron Nautilus Armor" title="Iron Nautilus Armor"> | **Iron Nautilus Armor** | `iron_nautilus_armor` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/item_frame.png" width="32" height="32" alt="Item Frame" title="Item Frame"> | **Item Frame** | `item_frame` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/jungle_boat.png" width="32" height="32" alt="Jungle Boat" title="Jungle Boat"> | **Jungle Boat** | `jungle_boat` | 4,000 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/jungle_chest_boat.png" width="32" height="32" alt="Jungle Boat with Chest" title="Jungle Boat with Chest"> | **Jungle Boat with Chest** | `jungle_chest_boat` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/jungle_pyramid_map.png" width="32" height="32" alt="Jungle Pyramid Map" title="Jungle Pyramid Map"> | **Jungle Pyramid Map** | `jungle_pyramid_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/lapis_lazuli.png" width="32" height="32" alt="Lapis Lazuli" title="Lapis Lazuli"> | **Lapis Lazuli** | `lapis_lazuli` | 2,000 | 1,600 | Buy / Sell | - |
| <img src="assets/icons/lava_bucket.png" width="32" height="32" alt="Lava Bucket" title="Lava Bucket"> | **Lava Bucket** | `lava_bucket` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/lead.png" width="32" height="32" alt="Lead" title="Lead"> | **Lead** | `lead` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/leather.png" width="32" height="32" alt="Leather" title="Leather"> | **Leather** | `leather` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/leather_horse_armor.png" width="32" height="32" alt="Leather Horse Armor" title="Leather Horse Armor"> | **Leather Horse Armor** | `leather_horse_armor` | 70,000 | 56,000 | Buy / Sell | - |
| <img src="assets/icons/light_blue_bundle.png" width="32" height="32" alt="Light Blue Bundle" title="Light Blue Bundle"> | **Light Blue Bundle** | `light_blue_bundle` | 5,000 | 3,400 | Buy / Sell | - |
| <img src="assets/icons/light_blue_cushion.png" width="32" height="32" alt="Light Blue Cushion" title="Light Blue Cushion"> | **Light Blue Cushion** | `light_blue_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/light_blue_dye.png" width="32" height="32" alt="Light Blue Dye" title="Light Blue Dye"> | **Light Blue Dye** | `light_blue_dye` | 2,500 | 900 | Buy / Sell | - |
| <img src="assets/icons/light_blue_harness.png" width="32" height="32" alt="Light Blue Harness" title="Light Blue Harness"> | **Light Blue Harness** | `light_blue_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/light_gray_bundle.png" width="32" height="32" alt="Light Gray Bundle" title="Light Gray Bundle"> | **Light Gray Bundle** | `light_gray_bundle` | 5,000 | 3,300 | Buy / Sell | - |
| <img src="assets/icons/light_gray_cushion.png" width="32" height="32" alt="Light Gray Cushion" title="Light Gray Cushion"> | **Light Gray Cushion** | `light_gray_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/light_gray_dye.png" width="32" height="32" alt="Light Gray Dye" title="Light Gray Dye"> | **Light Gray Dye** | `light_gray_dye` | 2,500 | 800 | Buy / Sell | - |
| <img src="assets/icons/light_gray_harness.png" width="32" height="32" alt="Light Gray Harness" title="Light Gray Harness"> | **Light Gray Harness** | `light_gray_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/lime_bundle.png" width="32" height="32" alt="Lime Bundle" title="Lime Bundle"> | **Lime Bundle** | `lime_bundle` | 5,000 | 3,400 | Buy / Sell | - |
| <img src="assets/icons/lime_cushion.png" width="32" height="32" alt="Lime Cushion" title="Lime Cushion"> | **Lime Cushion** | `lime_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/lime_dye.png" width="32" height="32" alt="Lime Dye" title="Lime Dye"> | **Lime Dye** | `lime_dye` | 2,500 | 900 | Buy / Sell | - |
| <img src="assets/icons/lime_harness.png" width="32" height="32" alt="Lime Harness" title="Lime Harness"> | **Lime Harness** | `lime_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/lingering_potion.png" width="32" height="32" alt="Lingering Potion" title="Lingering Potion"> | **Lingering Potion** | `lingering_potion` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/mace.png" width="32" height="32" alt="Mace" title="Mace"> | **Mace** | `mace` | 500,000 | 400,000 | Buy / Sell | - |
| <img src="assets/icons/magenta_bundle.png" width="32" height="32" alt="Magenta Bundle" title="Magenta Bundle"> | **Magenta Bundle** | `magenta_bundle` | 5,000 | 3,000 | Buy / Sell | - |
| <img src="assets/icons/magenta_cushion.png" width="32" height="32" alt="Magenta Cushion" title="Magenta Cushion"> | **Magenta Cushion** | `magenta_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/magenta_dye.png" width="32" height="32" alt="Magenta Dye" title="Magenta Dye"> | **Magenta Dye** | `magenta_dye` | 2,500 | 500 | Buy / Sell | - |
| <img src="assets/icons/magenta_harness.png" width="32" height="32" alt="Magenta Harness" title="Magenta Harness"> | **Magenta Harness** | `magenta_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/mangrove_boat.png" width="32" height="32" alt="Mangrove Boat" title="Mangrove Boat"> | **Mangrove Boat** | `mangrove_boat` | 4,000 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/mangrove_chest_boat.png" width="32" height="32" alt="Mangrove Boat with Chest" title="Mangrove Boat with Chest"> | **Mangrove Boat with Chest** | `mangrove_chest_boat` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/filled_map.png" width="32" height="32" alt="Map" title="Map"> | **Map** | `filled_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/minecart.png" width="32" height="32" alt="Minecart" title="Minecart"> | **Minecart** | `minecart` | 20,000 | 16,000 | Buy / Sell | - |
| <img src="assets/icons/chest_minecart.png" width="32" height="32" alt="Minecart with Chest" title="Minecart with Chest"> | **Minecart with Chest** | `chest_minecart` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/furnace_minecart.png" width="32" height="32" alt="Minecart with Furnace" title="Minecart with Furnace"> | **Minecart with Furnace** | `furnace_minecart` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/tnt_minecart.png" width="32" height="32" alt="Minecart with TNT" title="Minecart with TNT"> | **Minecart with TNT** | `tnt_minecart` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/miner_pottery_sherd.png" width="32" height="32" alt="Miner Pottery Sherd" title="Miner Pottery Sherd"> | **Miner Pottery Sherd** | `miner_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/mourner_pottery_sherd.png" width="32" height="32" alt="Mourner Pottery Sherd" title="Mourner Pottery Sherd"> | **Mourner Pottery Sherd** | `mourner_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_11.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_11` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_13.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_13` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_5.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_5` | 900,000 | 720,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_blocks.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_blocks` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_bounce.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_bounce` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_cat.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_cat` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_chirp.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_chirp` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_creator.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_creator` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_creator_music_box.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_creator_music_box` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_far.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_far` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_mall.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_mall` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_mellohi.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_mellohi` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_otherside.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_otherside` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_pigstep.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_pigstep` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_precipice.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_precipice` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_relic.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_relic` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_stal.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_stal` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_strad.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_strad` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_tears.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_tears` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_wait.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_wait` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/music_disc_ward.png" width="32" height="32" alt="Music Disc" title="Music Disc"> | **Music Disc** | `music_disc_ward` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/name_tag.png" width="32" height="32" alt="Name Tag" title="Name Tag"> | **Name Tag** | `name_tag` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/nautilus_shell.png" width="32" height="32" alt="Nautilus Shell" title="Nautilus Shell"> | **Nautilus Shell** | `nautilus_shell` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/oak_boat.png" width="32" height="32" alt="Oak Boat" title="Oak Boat"> | **Oak Boat** | `oak_boat` | 4,000 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/oak_chest_boat.png" width="32" height="32" alt="Oak Boat with Chest" title="Oak Boat with Chest"> | **Oak Boat with Chest** | `oak_chest_boat` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/ocean_monument_map.png" width="32" height="32" alt="Ocean Monument Map" title="Ocean Monument Map"> | **Ocean Monument Map** | `ocean_monument_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/ominous_bottle.png" width="32" height="32" alt="Ominous Bottle" title="Ominous Bottle"> | **Ominous Bottle** | `ominous_bottle` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/ominous_trial_key.png" width="32" height="32" alt="Ominous Trial Key" title="Ominous Trial Key"> | **Ominous Trial Key** | `ominous_trial_key` | 150,000 | 120,000 | Buy / Sell | - |
| <img src="assets/icons/orange_bundle.png" width="32" height="32" alt="Orange Bundle" title="Orange Bundle"> | **Orange Bundle** | `orange_bundle` | 5,000 | 3,000 | Buy / Sell | - |
| <img src="assets/icons/orange_cushion.png" width="32" height="32" alt="Orange Cushion" title="Orange Cushion"> | **Orange Cushion** | `orange_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/orange_dye.png" width="32" height="32" alt="Orange Dye" title="Orange Dye"> | **Orange Dye** | `orange_dye` | 2,500 | 500 | Buy / Sell | - |
| <img src="assets/icons/orange_harness.png" width="32" height="32" alt="Orange Harness" title="Orange Harness"> | **Orange Harness** | `orange_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/painting.png" width="32" height="32" alt="Painting" title="Painting"> | **Painting** | `painting` | 2,500 | 1,800 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_boat.png" width="32" height="32" alt="Pale Oak Boat" title="Pale Oak Boat"> | **Pale Oak Boat** | `pale_oak_boat` | 4,000 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/pale_oak_chest_boat.png" width="32" height="32" alt="Pale Oak Boat with Chest" title="Pale Oak Boat with Chest"> | **Pale Oak Boat with Chest** | `pale_oak_chest_boat` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/paper.png" width="32" height="32" alt="Paper" title="Paper"> | **Paper** | `paper` | 2,500 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/phantom_membrane.png" width="32" height="32" alt="Phantom Membrane" title="Phantom Membrane"> | **Phantom Membrane** | `phantom_membrane` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/pink_bundle.png" width="32" height="32" alt="Pink Bundle" title="Pink Bundle"> | **Pink Bundle** | `pink_bundle` | 5,000 | 3,000 | Buy / Sell | - |
| <img src="assets/icons/pink_cushion.png" width="32" height="32" alt="Pink Cushion" title="Pink Cushion"> | **Pink Cushion** | `pink_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/pink_dye.png" width="32" height="32" alt="Pink Dye" title="Pink Dye"> | **Pink Dye** | `pink_dye` | 2,500 | 500 | Buy / Sell | - |
| <img src="assets/icons/pink_harness.png" width="32" height="32" alt="Pink Harness" title="Pink Harness"> | **Pink Harness** | `pink_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/plains_village_map.png" width="32" height="32" alt="Plains Village Map" title="Plains Village Map"> | **Plains Village Map** | `plains_village_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/plenty_pottery_sherd.png" width="32" height="32" alt="Plenty Pottery Sherd" title="Plenty Pottery Sherd"> | **Plenty Pottery Sherd** | `plenty_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/poplar_boat.png" width="32" height="32" alt="Poplar Boat" title="Poplar Boat"> | **Poplar Boat** | `poplar_boat` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/poplar_chest_boat.png" width="32" height="32" alt="Poplar Boat with Chest" title="Poplar Boat with Chest"> | **Poplar Boat with Chest** | `poplar_chest_boat` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/potion.png" width="32" height="32" alt="Potion" title="Potion"> | **Potion** | `potion` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/prismarine_crystals.png" width="32" height="32" alt="Prismarine Crystals" title="Prismarine Crystals"> | **Prismarine Crystals** | `prismarine_crystals` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/prismarine_shard.png" width="32" height="32" alt="Prismarine Shard" title="Prismarine Shard"> | **Prismarine Shard** | `prismarine_shard` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/prize_pottery_sherd.png" width="32" height="32" alt="Prize Pottery Sherd" title="Prize Pottery Sherd"> | **Prize Pottery Sherd** | `prize_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/pumpkin_pie.png" width="32" height="32" alt="Pumpkin Pie" title="Pumpkin Pie"> | **Pumpkin Pie** | `pumpkin_pie` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/purple_bundle.png" width="32" height="32" alt="Purple Bundle" title="Purple Bundle"> | **Purple Bundle** | `purple_bundle` | 5,000 | 3,200 | Buy / Sell | - |
| <img src="assets/icons/purple_cushion.png" width="32" height="32" alt="Purple Cushion" title="Purple Cushion"> | **Purple Cushion** | `purple_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/purple_dye.png" width="32" height="32" alt="Purple Dye" title="Purple Dye"> | **Purple Dye** | `purple_dye` | 2,500 | 700 | Buy / Sell | - |
| <img src="assets/icons/purple_harness.png" width="32" height="32" alt="Purple Harness" title="Purple Harness"> | **Purple Harness** | `purple_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/recovery_compass.png" width="32" height="32" alt="Recovery Compass" title="Recovery Compass"> | **Recovery Compass** | `recovery_compass` | 100,000 | 80,000 | Buy / Sell | - |
| <img src="assets/icons/red_bundle.png" width="32" height="32" alt="Red Bundle" title="Red Bundle"> | **Red Bundle** | `red_bundle` | 5,000 | 3,000 | Buy / Sell | - |
| <img src="assets/icons/red_cushion.png" width="32" height="32" alt="Red Cushion" title="Red Cushion"> | **Red Cushion** | `red_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/red_dye.png" width="32" height="32" alt="Red Dye" title="Red Dye"> | **Red Dye** | `red_dye` | 2,500 | 500 | Buy / Sell | - |
| <img src="assets/icons/red_harness.png" width="32" height="32" alt="Red Harness" title="Red Harness"> | **Red Harness** | `red_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/resin_brick.png" width="32" height="32" alt="Resin Brick" title="Resin Brick"> | **Resin Brick** | `resin_brick` | 1,200 | 900 | Buy / Sell | - |
| <img src="assets/icons/rotten_flesh.png" width="32" height="32" alt="Rotten Flesh" title="Rotten Flesh"> | **Rotten Flesh** | `rotten_flesh` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/saddle.png" width="32" height="32" alt="Saddle" title="Saddle"> | **Saddle** | `saddle` | 15,000 | 12,000 | Buy / Sell | - |
| <img src="assets/icons/savanna_village_map.png" width="32" height="32" alt="Savanna Village Map" title="Savanna Village Map"> | **Savanna Village Map** | `savanna_village_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/scrape_pottery_sherd.png" width="32" height="32" alt="Scrape Pottery Sherd" title="Scrape Pottery Sherd"> | **Scrape Pottery Sherd** | `scrape_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/sheaf_pottery_sherd.png" width="32" height="32" alt="Sheaf Pottery Sherd" title="Sheaf Pottery Sherd"> | **Sheaf Pottery Sherd** | `sheaf_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/shears.png" width="32" height="32" alt="Shears" title="Shears"> | **Shears** | `shears` | 8,000 | 6,400 | Buy / Sell | - |
| <img src="assets/icons/shelter_pottery_sherd.png" width="32" height="32" alt="Shelter Pottery Sherd" title="Shelter Pottery Sherd"> | **Shelter Pottery Sherd** | `shelter_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/shield.png" width="32" height="32" alt="Shield" title="Shield"> | **Shield** | `shield` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/skull_pottery_sherd.png" width="32" height="32" alt="Skull Pottery Sherd" title="Skull Pottery Sherd"> | **Skull Pottery Sherd** | `skull_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/slime_ball.png" width="32" height="32" alt="Slimeball" title="Slimeball"> | **Slimeball** | `slime_ball` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/bolt_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `bolt_armor_trim_smithing_template` | 300,000 | 237,000 | Buy / Sell | - |
| <img src="assets/icons/coast_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `coast_armor_trim_smithing_template` | 300,000 | 210,010 | Buy / Sell | - |
| <img src="assets/icons/dune_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `dune_armor_trim_smithing_template` | 300,000 | 210,040 | Buy / Sell | - |
| <img src="assets/icons/eye_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `eye_armor_trim_smithing_template` | 300,000 | 211,000 | Buy / Sell | - |
| <img src="assets/icons/flow_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `flow_armor_trim_smithing_template` | 300,000 | 240,000 | Buy / Sell | - |
| <img src="assets/icons/host_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `host_armor_trim_smithing_template` | 300,000 | 220,000 | Buy / Sell | - |
| <img src="assets/icons/raiser_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `raiser_armor_trim_smithing_template` | 300,000 | 220,000 | Buy / Sell | - |
| <img src="assets/icons/rib_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `rib_armor_trim_smithing_template` | 300,000 | 211,000 | Buy / Sell | - |
| <img src="assets/icons/sentry_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `sentry_armor_trim_smithing_template` | 300,000 | 210,010 | Buy / Sell | - |
| <img src="assets/icons/shaper_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `shaper_armor_trim_smithing_template` | 300,000 | 220,000 | Buy / Sell | - |
| <img src="assets/icons/silence_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `silence_armor_trim_smithing_template` | 300,000 | 211,000 | Buy / Sell | - |
| <img src="assets/icons/snout_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `snout_armor_trim_smithing_template` | 300,000 | 211,000 | Buy / Sell | - |
| <img src="assets/icons/spire_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `spire_armor_trim_smithing_template` | 300,000 | 210,800 | Buy / Sell | - |
| <img src="assets/icons/tide_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `tide_armor_trim_smithing_template` | 300,000 | 220,000 | Buy / Sell | - |
| <img src="assets/icons/vex_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `vex_armor_trim_smithing_template` | 300,000 | 210,010 | Buy / Sell | - |
| <img src="assets/icons/ward_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `ward_armor_trim_smithing_template` | 300,000 | 211,000 | Buy / Sell | - |
| <img src="assets/icons/wayfinder_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `wayfinder_armor_trim_smithing_template` | 300,000 | 220,000 | Buy / Sell | - |
| <img src="assets/icons/wild_armor_trim_smithing_template.png" width="32" height="32" alt="Smithing Template" title="Smithing Template"> | **Smithing Template** | `wild_armor_trim_smithing_template` | 300,000 | 210,010 | Buy / Sell | - |
| <img src="assets/icons/snort_pottery_sherd.png" width="32" height="32" alt="Snort Pottery Sherd" title="Snort Pottery Sherd"> | **Snort Pottery Sherd** | `snort_pottery_sherd` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/snowball.png" width="32" height="32" alt="Snowball" title="Snowball"> | **Snowball** | `snowball` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/snowy_village_map.png" width="32" height="32" alt="Snowy Village Map" title="Snowy Village Map"> | **Snowy Village Map** | `snowy_village_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/spectral_arrow.png" width="32" height="32" alt="Spectral Arrow" title="Spectral Arrow"> | **Spectral Arrow** | `spectral_arrow` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/spider_eye.png" width="32" height="32" alt="Spider Eye" title="Spider Eye"> | **Spider Eye** | `spider_eye` | 800 | 600 | Buy / Sell | - |
| <img src="assets/icons/splash_potion.png" width="32" height="32" alt="Splash Potion" title="Splash Potion"> | **Splash Potion** | `splash_potion` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/spruce_boat.png" width="32" height="32" alt="Spruce Boat" title="Spruce Boat"> | **Spruce Boat** | `spruce_boat` | 4,000 | 1,000 | Buy / Sell | - |
| <img src="assets/icons/spruce_chest_boat.png" width="32" height="32" alt="Spruce Boat with Chest" title="Spruce Boat with Chest"> | **Spruce Boat with Chest** | `spruce_chest_boat` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/spyglass.png" width="32" height="32" alt="Spyglass" title="Spyglass"> | **Spyglass** | `spyglass` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/stick.png" width="32" height="32" alt="Stick" title="Stick"> | **Stick** | `stick` | 2,500 | 100 | Buy / Sell | - |
| <img src="assets/icons/sugar.png" width="32" height="32" alt="Sugar" title="Sugar"> | **Sugar** | `sugar` | 2,500 | 200 | Buy / Sell | - |
| <img src="assets/icons/swamp_hut_map.png" width="32" height="32" alt="Swamp Hut Map" title="Swamp Hut Map"> | **Swamp Hut Map** | `swamp_hut_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/taiga_village_map.png" width="32" height="32" alt="Taiga Village Map" title="Taiga Village Map"> | **Taiga Village Map** | `taiga_village_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/tipped_arrow.png" width="32" height="32" alt="Tipped Arrow" title="Tipped Arrow"> | **Tipped Arrow** | `tipped_arrow` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/totem_of_undying.png" width="32" height="32" alt="Totem of Undying" title="Totem of Undying"> | **Totem of Undying** | `totem_of_undying` | 500,000 | 400,000 | Buy / Sell | - |
| <img src="assets/icons/trial_key.png" width="32" height="32" alt="Trial Key" title="Trial Key"> | **Trial Key** | `trial_key` | 50,000 | 40,000 | Buy / Sell | - |
| <img src="assets/icons/trident.png" width="32" height="32" alt="Trident" title="Trident"> | **Trident** | `trident` | 300,000 | 240,000 | Buy / Sell | - |
| <img src="assets/icons/turtle_scute.png" width="32" height="32" alt="Turtle Scute" title="Turtle Scute"> | **Turtle Scute** | `turtle_scute` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/warm_ocean_ruins_map.png" width="32" height="32" alt="Warm Ocean Ruins Map" title="Warm Ocean Ruins Map"> | **Warm Ocean Ruins Map** | `warm_ocean_ruins_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/warped_fungus_on_a_stick.png" width="32" height="32" alt="Warped Fungus on a Stick" title="Warped Fungus on a Stick"> | **Warped Fungus on a Stick** | `warped_fungus_on_a_stick` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/water_bucket.png" width="32" height="32" alt="Water Bucket" title="Water Bucket"> | **Water Bucket** | `water_bucket` | 5,000 | 4,000 | Buy / Sell | - |
| <img src="assets/icons/wheat.png" width="32" height="32" alt="Wheat" title="Wheat"> | **Wheat** | `wheat` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/white_bundle.png" width="32" height="32" alt="White Bundle" title="White Bundle"> | **White Bundle** | `white_bundle` | 5,000 | 3,300 | Buy / Sell | - |
| <img src="assets/icons/white_cushion.png" width="32" height="32" alt="White Cushion" title="White Cushion"> | **White Cushion** | `white_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/white_dye.png" width="32" height="32" alt="White Dye" title="White Dye"> | **White Dye** | `white_dye` | 2,500 | 800 | Buy / Sell | - |
| <img src="assets/icons/white_harness.png" width="32" height="32" alt="White Harness" title="White Harness"> | **White Harness** | `white_harness` | 10,000 | 8,000 | Buy / Sell | - |
| <img src="assets/icons/wind_charge.png" width="32" height="32" alt="Wind Charge" title="Wind Charge"> | **Wind Charge** | `wind_charge` | 12,500 | 10,000 | Buy / Sell | - |
| <img src="assets/icons/wolf_armor.png" width="32" height="32" alt="Wolf Armor" title="Wolf Armor"> | **Wolf Armor** | `wolf_armor` | 15,000 | 12,000 | Buy / Sell | - |
| <img src="assets/icons/woodland_mansion_map.png" width="32" height="32" alt="Woodland Mansion Map" title="Woodland Mansion Map"> | **Woodland Mansion Map** | `woodland_mansion_map` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/written_book.png" width="32" height="32" alt="Written Book" title="Written Book"> | **Written Book** | `written_book` | 2,500 | 2,000 | Buy / Sell | - |
| <img src="assets/icons/yellow_bundle.png" width="32" height="32" alt="Yellow Bundle" title="Yellow Bundle"> | **Yellow Bundle** | `yellow_bundle` | 5,000 | 3,000 | Buy / Sell | - |
| <img src="assets/icons/yellow_cushion.png" width="32" height="32" alt="Yellow Cushion" title="Yellow Cushion"> | **Yellow Cushion** | `yellow_cushion` | 3,000 | 2,400 | Buy / Sell | - |
| <img src="assets/icons/yellow_dye.png" width="32" height="32" alt="Yellow Dye" title="Yellow Dye"> | **Yellow Dye** | `yellow_dye` | 2,500 | 500 | Buy / Sell | - |
| <img src="assets/icons/yellow_harness.png" width="32" height="32" alt="Yellow Harness" title="Yellow Harness"> | **Yellow Harness** | `yellow_harness` | 10,000 | 8,000 | Buy / Sell | - |

## Rare & Unique

_3 items, 0 buyable, 2 sellable._

| Icon | Item | ID | Buy | Sell | Trade | Notes |
|:---:|:--|:--|--:|--:|:--:|:--|
| <img src="assets/icons/dragon_egg.png" width="32" height="32" alt="Dragon Egg" title="Dragon Egg"> | **Dragon Egg** | `dragon_egg` | - | - | Not traded | unique / auction only |
| <img src="assets/icons/dragon_head.png" width="32" height="32" alt="Dragon Head" title="Dragon Head"> | **Dragon Head** | `dragon_head` | - | 1,500,000 | Sell only | unique / auction only |
| <img src="assets/icons/elytra.png" width="32" height="32" alt="Elytra" title="Elytra"> | **Elytra** | `elytra` | - | 2,500,000 | Sell only | unique / auction only |

## Auction Only

_1 items, 0 buyable, 1 sellable._

| Icon | Item | ID | Buy | Sell | Trade | Notes |
|:---:|:--|:--|--:|--:|:--:|:--|
| <img src="assets/icons/enchanted_book.png" width="32" height="32" alt="Enchanted Book" title="Enchanted Book"> | **Enchanted Book** | `enchanted_book` | - | 50,000 | Sell only | auction only |

## Creative Only

_108 items, 0 buyable, 108 sellable._

| Icon | Item | ID | Buy | Sell | Trade | Notes |
|:---:|:--|:--|--:|--:|:--:|:--|
| <img src="assets/icons/allay_spawn_egg.png" width="32" height="32" alt="Allay Spawn Egg" title="Allay Spawn Egg"> | **Allay Spawn Egg** | `allay_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/armadillo_spawn_egg.png" width="32" height="32" alt="Armadillo Spawn Egg" title="Armadillo Spawn Egg"> | **Armadillo Spawn Egg** | `armadillo_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/axolotl_spawn_egg.png" width="32" height="32" alt="Axolotl Spawn Egg" title="Axolotl Spawn Egg"> | **Axolotl Spawn Egg** | `axolotl_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/barrier.png" width="32" height="32" alt="Barrier" title="Barrier"> | **Barrier** | `barrier` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/bat_spawn_egg.png" width="32" height="32" alt="Bat Spawn Egg" title="Bat Spawn Egg"> | **Bat Spawn Egg** | `bat_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/bedrock.png" width="32" height="32" alt="Bedrock" title="Bedrock"> | **Bedrock** | `bedrock` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/bee_spawn_egg.png" width="32" height="32" alt="Bee Spawn Egg" title="Bee Spawn Egg"> | **Bee Spawn Egg** | `bee_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/blaze_spawn_egg.png" width="32" height="32" alt="Blaze Spawn Egg" title="Blaze Spawn Egg"> | **Blaze Spawn Egg** | `blaze_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/bogged_spawn_egg.png" width="32" height="32" alt="Bogged Spawn Egg" title="Bogged Spawn Egg"> | **Bogged Spawn Egg** | `bogged_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/breeze_spawn_egg.png" width="32" height="32" alt="Breeze Spawn Egg" title="Breeze Spawn Egg"> | **Breeze Spawn Egg** | `breeze_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/budding_amethyst.png" width="32" height="32" alt="Budding Amethyst" title="Budding Amethyst"> | **Budding Amethyst** | `budding_amethyst` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/camel_husk_spawn_egg.png" width="32" height="32" alt="Camel Husk Spawn Egg" title="Camel Husk Spawn Egg"> | **Camel Husk Spawn Egg** | `camel_husk_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/camel_spawn_egg.png" width="32" height="32" alt="Camel Spawn Egg" title="Camel Spawn Egg"> | **Camel Spawn Egg** | `camel_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/cat_spawn_egg.png" width="32" height="32" alt="Cat Spawn Egg" title="Cat Spawn Egg"> | **Cat Spawn Egg** | `cat_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/cave_spider_spawn_egg.png" width="32" height="32" alt="Cave Spider Spawn Egg" title="Cave Spider Spawn Egg"> | **Cave Spider Spawn Egg** | `cave_spider_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/chain_command_block.png" width="32" height="32" alt="Chain Command Block" title="Chain Command Block"> | **Chain Command Block** | `chain_command_block` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/chicken_spawn_egg.png" width="32" height="32" alt="Chicken Spawn Egg" title="Chicken Spawn Egg"> | **Chicken Spawn Egg** | `chicken_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/cod_spawn_egg.png" width="32" height="32" alt="Cod Spawn Egg" title="Cod Spawn Egg"> | **Cod Spawn Egg** | `cod_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/command_block.png" width="32" height="32" alt="Command Block" title="Command Block"> | **Command Block** | `command_block` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/copper_golem_spawn_egg.png" width="32" height="32" alt="Copper Golem Spawn Egg" title="Copper Golem Spawn Egg"> | **Copper Golem Spawn Egg** | `copper_golem_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/cow_spawn_egg.png" width="32" height="32" alt="Cow Spawn Egg" title="Cow Spawn Egg"> | **Cow Spawn Egg** | `cow_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/creaking_spawn_egg.png" width="32" height="32" alt="Creaking Spawn Egg" title="Creaking Spawn Egg"> | **Creaking Spawn Egg** | `creaking_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/creeper_spawn_egg.png" width="32" height="32" alt="Creeper Spawn Egg" title="Creeper Spawn Egg"> | **Creeper Spawn Egg** | `creeper_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/debug_stick.png" width="32" height="32" alt="Debug Stick" title="Debug Stick"> | **Debug Stick** | `debug_stick` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/dolphin_spawn_egg.png" width="32" height="32" alt="Dolphin Spawn Egg" title="Dolphin Spawn Egg"> | **Dolphin Spawn Egg** | `dolphin_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/donkey_spawn_egg.png" width="32" height="32" alt="Donkey Spawn Egg" title="Donkey Spawn Egg"> | **Donkey Spawn Egg** | `donkey_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/drowned_spawn_egg.png" width="32" height="32" alt="Drowned Spawn Egg" title="Drowned Spawn Egg"> | **Drowned Spawn Egg** | `drowned_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/elder_guardian_spawn_egg.png" width="32" height="32" alt="Elder Guardian Spawn Egg" title="Elder Guardian Spawn Egg"> | **Elder Guardian Spawn Egg** | `elder_guardian_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/end_portal_frame.png" width="32" height="32" alt="End Portal Frame" title="End Portal Frame"> | **End Portal Frame** | `end_portal_frame` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/ender_dragon_spawn_egg.png" width="32" height="32" alt="Ender Dragon Spawn Egg" title="Ender Dragon Spawn Egg"> | **Ender Dragon Spawn Egg** | `ender_dragon_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/enderman_spawn_egg.png" width="32" height="32" alt="Enderman Spawn Egg" title="Enderman Spawn Egg"> | **Enderman Spawn Egg** | `enderman_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/endermite_spawn_egg.png" width="32" height="32" alt="Endermite Spawn Egg" title="Endermite Spawn Egg"> | **Endermite Spawn Egg** | `endermite_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/evoker_spawn_egg.png" width="32" height="32" alt="Evoker Spawn Egg" title="Evoker Spawn Egg"> | **Evoker Spawn Egg** | `evoker_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/fox_spawn_egg.png" width="32" height="32" alt="Fox Spawn Egg" title="Fox Spawn Egg"> | **Fox Spawn Egg** | `fox_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/frog_spawn_egg.png" width="32" height="32" alt="Frog Spawn Egg" title="Frog Spawn Egg"> | **Frog Spawn Egg** | `frog_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/ghast_spawn_egg.png" width="32" height="32" alt="Ghast Spawn Egg" title="Ghast Spawn Egg"> | **Ghast Spawn Egg** | `ghast_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/glow_squid_spawn_egg.png" width="32" height="32" alt="Glow Squid Spawn Egg" title="Glow Squid Spawn Egg"> | **Glow Squid Spawn Egg** | `glow_squid_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/goat_spawn_egg.png" width="32" height="32" alt="Goat Spawn Egg" title="Goat Spawn Egg"> | **Goat Spawn Egg** | `goat_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/guardian_spawn_egg.png" width="32" height="32" alt="Guardian Spawn Egg" title="Guardian Spawn Egg"> | **Guardian Spawn Egg** | `guardian_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/happy_ghast_spawn_egg.png" width="32" height="32" alt="Happy Ghast Spawn Egg" title="Happy Ghast Spawn Egg"> | **Happy Ghast Spawn Egg** | `happy_ghast_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/hoglin_spawn_egg.png" width="32" height="32" alt="Hoglin Spawn Egg" title="Hoglin Spawn Egg"> | **Hoglin Spawn Egg** | `hoglin_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/horse_spawn_egg.png" width="32" height="32" alt="Horse Spawn Egg" title="Horse Spawn Egg"> | **Horse Spawn Egg** | `horse_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/husk_spawn_egg.png" width="32" height="32" alt="Husk Spawn Egg" title="Husk Spawn Egg"> | **Husk Spawn Egg** | `husk_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/iron_golem_spawn_egg.png" width="32" height="32" alt="Iron Golem Spawn Egg" title="Iron Golem Spawn Egg"> | **Iron Golem Spawn Egg** | `iron_golem_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/jigsaw.png" width="32" height="32" alt="Jigsaw Block" title="Jigsaw Block"> | **Jigsaw Block** | `jigsaw` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/knowledge_book.png" width="32" height="32" alt="Knowledge Book" title="Knowledge Book"> | **Knowledge Book** | `knowledge_book` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/light.png" width="32" height="32" alt="Light" title="Light"> | **Light** | `light` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/llama_spawn_egg.png" width="32" height="32" alt="Llama Spawn Egg" title="Llama Spawn Egg"> | **Llama Spawn Egg** | `llama_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/magma_cube_spawn_egg.png" width="32" height="32" alt="Magma Cube Spawn Egg" title="Magma Cube Spawn Egg"> | **Magma Cube Spawn Egg** | `magma_cube_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/command_block_minecart.png" width="32" height="32" alt="Minecart with Command Block" title="Minecart with Command Block"> | **Minecart with Command Block** | `command_block_minecart` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/mooshroom_spawn_egg.png" width="32" height="32" alt="Mooshroom Spawn Egg" title="Mooshroom Spawn Egg"> | **Mooshroom Spawn Egg** | `mooshroom_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/mule_spawn_egg.png" width="32" height="32" alt="Mule Spawn Egg" title="Mule Spawn Egg"> | **Mule Spawn Egg** | `mule_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/nautilus_spawn_egg.png" width="32" height="32" alt="Nautilus Spawn Egg" title="Nautilus Spawn Egg"> | **Nautilus Spawn Egg** | `nautilus_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/ocelot_spawn_egg.png" width="32" height="32" alt="Ocelot Spawn Egg" title="Ocelot Spawn Egg"> | **Ocelot Spawn Egg** | `ocelot_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/panda_spawn_egg.png" width="32" height="32" alt="Panda Spawn Egg" title="Panda Spawn Egg"> | **Panda Spawn Egg** | `panda_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/parched_spawn_egg.png" width="32" height="32" alt="Parched Spawn Egg" title="Parched Spawn Egg"> | **Parched Spawn Egg** | `parched_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/parrot_spawn_egg.png" width="32" height="32" alt="Parrot Spawn Egg" title="Parrot Spawn Egg"> | **Parrot Spawn Egg** | `parrot_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/petrified_oak_slab.png" width="32" height="32" alt="Petrified Oak Slab" title="Petrified Oak Slab"> | **Petrified Oak Slab** | `petrified_oak_slab` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/phantom_spawn_egg.png" width="32" height="32" alt="Phantom Spawn Egg" title="Phantom Spawn Egg"> | **Phantom Spawn Egg** | `phantom_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/pig_spawn_egg.png" width="32" height="32" alt="Pig Spawn Egg" title="Pig Spawn Egg"> | **Pig Spawn Egg** | `pig_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/piglin_brute_spawn_egg.png" width="32" height="32" alt="Piglin Brute Spawn Egg" title="Piglin Brute Spawn Egg"> | **Piglin Brute Spawn Egg** | `piglin_brute_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/piglin_spawn_egg.png" width="32" height="32" alt="Piglin Spawn Egg" title="Piglin Spawn Egg"> | **Piglin Spawn Egg** | `piglin_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/pillager_spawn_egg.png" width="32" height="32" alt="Pillager Spawn Egg" title="Pillager Spawn Egg"> | **Pillager Spawn Egg** | `pillager_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/polar_bear_spawn_egg.png" width="32" height="32" alt="Polar Bear Spawn Egg" title="Polar Bear Spawn Egg"> | **Polar Bear Spawn Egg** | `polar_bear_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/pufferfish_spawn_egg.png" width="32" height="32" alt="Pufferfish Spawn Egg" title="Pufferfish Spawn Egg"> | **Pufferfish Spawn Egg** | `pufferfish_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/rabbit_spawn_egg.png" width="32" height="32" alt="Rabbit Spawn Egg" title="Rabbit Spawn Egg"> | **Rabbit Spawn Egg** | `rabbit_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/ravager_spawn_egg.png" width="32" height="32" alt="Ravager Spawn Egg" title="Ravager Spawn Egg"> | **Ravager Spawn Egg** | `ravager_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/reinforced_deepslate.png" width="32" height="32" alt="Reinforced Deepslate" title="Reinforced Deepslate"> | **Reinforced Deepslate** | `reinforced_deepslate` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/repeating_command_block.png" width="32" height="32" alt="Repeating Command Block" title="Repeating Command Block"> | **Repeating Command Block** | `repeating_command_block` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/salmon_spawn_egg.png" width="32" height="32" alt="Salmon Spawn Egg" title="Salmon Spawn Egg"> | **Salmon Spawn Egg** | `salmon_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/sheep_spawn_egg.png" width="32" height="32" alt="Sheep Spawn Egg" title="Sheep Spawn Egg"> | **Sheep Spawn Egg** | `sheep_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/shulker_spawn_egg.png" width="32" height="32" alt="Shulker Spawn Egg" title="Shulker Spawn Egg"> | **Shulker Spawn Egg** | `shulker_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/silverfish_spawn_egg.png" width="32" height="32" alt="Silverfish Spawn Egg" title="Silverfish Spawn Egg"> | **Silverfish Spawn Egg** | `silverfish_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/skeleton_horse_spawn_egg.png" width="32" height="32" alt="Skeleton Horse Spawn Egg" title="Skeleton Horse Spawn Egg"> | **Skeleton Horse Spawn Egg** | `skeleton_horse_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/skeleton_spawn_egg.png" width="32" height="32" alt="Skeleton Spawn Egg" title="Skeleton Spawn Egg"> | **Skeleton Spawn Egg** | `skeleton_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/slime_spawn_egg.png" width="32" height="32" alt="Slime Spawn Egg" title="Slime Spawn Egg"> | **Slime Spawn Egg** | `slime_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/sniffer_spawn_egg.png" width="32" height="32" alt="Sniffer Spawn Egg" title="Sniffer Spawn Egg"> | **Sniffer Spawn Egg** | `sniffer_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/snow_golem_spawn_egg.png" width="32" height="32" alt="Snow Golem Spawn Egg" title="Snow Golem Spawn Egg"> | **Snow Golem Spawn Egg** | `snow_golem_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/spider_spawn_egg.png" width="32" height="32" alt="Spider Spawn Egg" title="Spider Spawn Egg"> | **Spider Spawn Egg** | `spider_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/squid_spawn_egg.png" width="32" height="32" alt="Squid Spawn Egg" title="Squid Spawn Egg"> | **Squid Spawn Egg** | `squid_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/stray_spawn_egg.png" width="32" height="32" alt="Stray Spawn Egg" title="Stray Spawn Egg"> | **Stray Spawn Egg** | `stray_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/strider_spawn_egg.png" width="32" height="32" alt="Strider Spawn Egg" title="Strider Spawn Egg"> | **Strider Spawn Egg** | `strider_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/structure_block.png" width="32" height="32" alt="Structure Block" title="Structure Block"> | **Structure Block** | `structure_block` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/structure_void.png" width="32" height="32" alt="Structure Void" title="Structure Void"> | **Structure Void** | `structure_void` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/sulfur_cube_spawn_egg.png" width="32" height="32" alt="Sulfur Cube Spawn Egg" title="Sulfur Cube Spawn Egg"> | **Sulfur Cube Spawn Egg** | `sulfur_cube_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/tadpole_spawn_egg.png" width="32" height="32" alt="Tadpole Spawn Egg" title="Tadpole Spawn Egg"> | **Tadpole Spawn Egg** | `tadpole_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/test_block.png" width="32" height="32" alt="Test Block" title="Test Block"> | **Test Block** | `test_block` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/test_instance_block.png" width="32" height="32" alt="Test Instance Block" title="Test Instance Block"> | **Test Instance Block** | `test_instance_block` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/trader_llama_spawn_egg.png" width="32" height="32" alt="Trader Llama Spawn Egg" title="Trader Llama Spawn Egg"> | **Trader Llama Spawn Egg** | `trader_llama_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/trial_spawner.png" width="32" height="32" alt="Trial Spawner" title="Trial Spawner"> | **Trial Spawner** | `trial_spawner` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/tropical_fish_spawn_egg.png" width="32" height="32" alt="Tropical Fish Spawn Egg" title="Tropical Fish Spawn Egg"> | **Tropical Fish Spawn Egg** | `tropical_fish_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/turtle_spawn_egg.png" width="32" height="32" alt="Turtle Spawn Egg" title="Turtle Spawn Egg"> | **Turtle Spawn Egg** | `turtle_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/vault.png" width="32" height="32" alt="Vault" title="Vault"> | **Vault** | `vault` | - | 500,000 | Sell only | creative only |
| <img src="assets/icons/vex_spawn_egg.png" width="32" height="32" alt="Vex Spawn Egg" title="Vex Spawn Egg"> | **Vex Spawn Egg** | `vex_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/villager_spawn_egg.png" width="32" height="32" alt="Villager Spawn Egg" title="Villager Spawn Egg"> | **Villager Spawn Egg** | `villager_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/vindicator_spawn_egg.png" width="32" height="32" alt="Vindicator Spawn Egg" title="Vindicator Spawn Egg"> | **Vindicator Spawn Egg** | `vindicator_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/wandering_trader_spawn_egg.png" width="32" height="32" alt="Wandering Trader Spawn Egg" title="Wandering Trader Spawn Egg"> | **Wandering Trader Spawn Egg** | `wandering_trader_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/warden_spawn_egg.png" width="32" height="32" alt="Warden Spawn Egg" title="Warden Spawn Egg"> | **Warden Spawn Egg** | `warden_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/witch_spawn_egg.png" width="32" height="32" alt="Witch Spawn Egg" title="Witch Spawn Egg"> | **Witch Spawn Egg** | `witch_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/wither_skeleton_spawn_egg.png" width="32" height="32" alt="Wither Skeleton Spawn Egg" title="Wither Skeleton Spawn Egg"> | **Wither Skeleton Spawn Egg** | `wither_skeleton_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/wither_spawn_egg.png" width="32" height="32" alt="Wither Spawn Egg" title="Wither Spawn Egg"> | **Wither Spawn Egg** | `wither_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/wolf_spawn_egg.png" width="32" height="32" alt="Wolf Spawn Egg" title="Wolf Spawn Egg"> | **Wolf Spawn Egg** | `wolf_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/zoglin_spawn_egg.png" width="32" height="32" alt="Zoglin Spawn Egg" title="Zoglin Spawn Egg"> | **Zoglin Spawn Egg** | `zoglin_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/zombie_horse_spawn_egg.png" width="32" height="32" alt="Zombie Horse Spawn Egg" title="Zombie Horse Spawn Egg"> | **Zombie Horse Spawn Egg** | `zombie_horse_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/zombie_nautilus_spawn_egg.png" width="32" height="32" alt="Zombie Nautilus Spawn Egg" title="Zombie Nautilus Spawn Egg"> | **Zombie Nautilus Spawn Egg** | `zombie_nautilus_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/zombie_spawn_egg.png" width="32" height="32" alt="Zombie Spawn Egg" title="Zombie Spawn Egg"> | **Zombie Spawn Egg** | `zombie_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/zombie_villager_spawn_egg.png" width="32" height="32" alt="Zombie Villager Spawn Egg" title="Zombie Villager Spawn Egg"> | **Zombie Villager Spawn Egg** | `zombie_villager_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |
| <img src="assets/icons/zombified_piglin_spawn_egg.png" width="32" height="32" alt="Zombified Piglin Spawn Egg" title="Zombified Piglin Spawn Egg"> | **Zombified Piglin Spawn Egg** | `zombified_piglin_spawn_egg` | - | 100,000 | Sell only | spawn egg (creative only) |

---

## Regenerating This Document

The icons and tables are generated from the actual Minecraft client jar. Nothing from the jar except the small 16x16 icons is committed.

```bash
# Download the client jar for a specific version
.agent-work/tools/fetch_client_jar.sh 26.3

# Rebuild the document and icons
python3 .agent-work/tools/generate_price_docs.py \
  --jar .agent-work/tmp/mc/client.jar \
  --version 26.3 \
  --prices src/main/resources/prices.tsv \
  --docs docs
```

_Generated from `prices.tsv` and the Minecraft client assets. Do not edit the tables by hand; change the TSV or the generator instead._
