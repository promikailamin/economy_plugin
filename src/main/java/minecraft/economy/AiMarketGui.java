package minecraft.economy;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class AiMarketGui {

    private AiMarketGui() {
    }

    public static void open(Player player) {
        new View(player).open();
    }

    private static AuctionAi ai() {
        return EconomyPlugin.get().ai();
    }

    private static final class View extends EcoGui {
        private final List<AuctionAi.Status> statuses;

        View(Player player) {
            super(player, 6, "§bSmart Market §8| §7AI stock engine");
            AuctionAi engine = ai();
            this.statuses = engine == null ? List.of() : engine.statuses();

            if (engine != null) {
                inv.setItem(4, item(Material.BEACON, "§b§lNeural Market Engine",
                        "§7Synthetic listings: §f" + engine.activeListings() + "§8/§f" + engine.targetListings(),
                        "§7Market heat: §e" + percent(engine.heat()),
                        "§7Sales learned: §f" + engine.totalSold() + " §8(" + engine.trainedSamples() + " samples)",
                        engine.buyersEnabled()
                                ? "§7AI buyers: §a" + engine.totalBoughtByAi() + " bought §8("
                                        + EconomyData.formatMoney(engine.totalSpentByAi()) + "§8)"
                                : "§7AI buyers: §cdisabled",
                        engine.specialRelicsEnabled()
                                ? "§7Special relics: §d" + engine.activeSpecialCount() + "§8/§d" + engine.maxSpecialRelics()
                                : "§7Special relics: §cdisabled",
                        "§7The engine restocks, reprices and buys",
                        "§7reasonably priced player listings.",
                        "§8Stock can run out and come back later."));
            }

            for (int i = 0; i < statuses.size(); i++) {
                AuctionAi.Status status = statuses.get(i);
                inv.setItem(9 + i, statusItem(status));
            }

            inv.setItem(BACK, item(Material.ARROW, "§c« Back", "§7Return to previous screen"));
            inv.setItem(48, item(Material.ENDER_EYE, "§b§lAuction House", "§7Browse all live listings"));
            inv.setItem(HEAD, balanceHead(player));
            inv.setItem(50, item(Material.ENDER_CHEST, "§d§lMystery Shop",
                    "§7Spend money on a random crate", "§7Worse or best - the engine decides"));
            inv.setItem(51, item(Material.COMPARATOR, "§eRefresh", "§7Recalculate the market view"));
            inv.setItem(CLOSE, item(Material.BARRIER, "§cClose", "§7Close this menu"));
            fillEmpty(inv, 0, 45, Material.BLACK_STAINED_GLASS_PANE);
            fillEmpty(inv, 45, 54, Material.GRAY_STAINED_GLASS_PANE);
        }

        @Override
        protected void handle(InventoryClickEvent e, int slot) {
            GuiHistoryManager.GuiState me = new GuiHistoryManager.GuiState("ai_market");
            if (slot >= 9 && slot < 9 + statuses.size()) {
                navigate(me, () -> AuctionGui.open(player, 1));
                return;
            }
            switch (slot) {
                case BACK -> back();
                case 48 -> navigate(me, () -> AuctionGui.open(player, 1));
                case 50 -> navigate(me, () -> MysteryGui.open(player));
                case 51 -> AiMarketGui.open(player);
                case CLOSE -> close();
                default -> {
                }
            }
        }

        private static ItemStack statusItem(AuctionAi.Status status) {
            boolean out = status.stock() <= 0;
            List<String> lore = new ArrayList<>();
            lore.add("§7Stock: " + (out ? "§cOUT OF STOCK" : "§a" + status.stock() + "§8/§7" + status.capacity()));
            lore.add("§7Active listings: §f" + status.active());
            lore.add("§7Demand: §e" + bar(status.demand()) + " §8" + percent(status.demand()));
            lore.add("§7Price index: " + priceTrend(status.priceIndex()));
            lore.add("§7Lifetime sold: §f" + status.sold() + " §8(§7" + status.listed() + " listed§8)");
            if (out) {
                long ms = status.restockAt() - System.currentTimeMillis();
                long minutes = Math.max(1, (ms + 59_999) / 60_000);
                lore.add("§cRestocking in about §f" + minutes + "m");
            } else {
                lore.add("§aAvailable on the auction house now");
            }
            return item(status.icon(), "§e" + status.label(), lore);
        }

        private static String bar(double value) {
            int filled = Math.max(0, Math.min(10, (int) Math.round(value * 10)));
            return "§a" + "|".repeat(filled) + "§8" + "|".repeat(10 - filled);
        }

        private static String priceTrend(double index) {
            if (index > 1.08) return "§c▲ high §8(" + percent(index) + ")";
            if (index < 0.94) return "§a▼ low §8(" + percent(index) + ")";
            return "§e■ stable §8(" + percent(index) + ")";
        }

        private static String percent(double value) {
            return Math.round(value * 100) + "%";
        }
    }
}
