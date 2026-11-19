package marketharness;

import minecraft.economy.AiItemFactory;
import minecraft.economy.AuctionAi;
import minecraft.economy.EconomyData;
import minecraft.economy.EconomyPlugin;
import minecraft.economy.MysteryShop;
import minecraft.economy.SpecialItems;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

public final class MarketHarness extends JavaPlugin {

    private static final List<String> ARCHETYPES = List.of(
            "junk", "supplies", "tools", "armor", "enchanted_tools",
            "enchanted_armor", "weapons", "crates", "books", "treasures", "special"
    );

    @Override
    public void onEnable() {
        StringBuilder report = new StringBuilder();
        int failures = 0;
        Random rng = new Random(20261119);

        report.append("ai market scale checks\n");
        AuctionAi ai = EconomyPlugin.get().ai();
        if (ai == null) {
            failures++;
            report.append("AI market missing\n");
        } else {
            report.append("target=").append(ai.targetListings())
                    .append(" active=").append(ai.activeListings()).append('\n');
            if (ai.targetListings() < 1000) failures++;
            if (ai.activeListings() < Math.min(ai.targetListings(), 1000)) failures++;
        }

        report.append("\nhead trade checks\n");
        for (String id : List.of("player_head", "creeper_head", "piglin_head",
                "skeleton_skull", "wither_skeleton_skull", "zombie_head")) {
            EconomyData.ShopEntry entry = EconomyData.get(id);
            boolean ok = entry != null && !entry.buyable && entry.sellable();
            if (!ok) failures++;
            report.append(id).append(" buyable=").append(entry != null && entry.buyable)
                    .append(" sellable=").append(entry != null && entry.sellable())
                    .append('\n');
        }
        report.append('\n');
        report.append("generation checks\n");
        for (AiItemFactory.Quality quality : AiItemFactory.Quality.values()) {
            for (String archetype : ARCHETYPES) {
                int min = Integer.MAX_VALUE;
                int max = 0;
                long total = 0;
                for (int i = 0; i < 40; i++) {
                    ItemStack item = AiItemFactory.generate(rng, archetype, quality);
                    if (item == null || item.getType().isAir()) {
                        failures++;
                        continue;
                    }
                    long value = AiItemFactory.value(item);
                    if (value <= 0) failures++;
                    min = Math.min(min, item.getAmount());
                    max = Math.max(max, item.getAmount());
                    total += value;
                }
                report.append(archetype).append(' ').append(quality)
                        .append(" avgValue=").append(total / 40)
                        .append(" amount=").append(min).append('-').append(max).append('\n');
            }
        }

        report.append("\nkeep inventory helmet checks\n");
        for (int i = 0; i < 50; i++) {
            ItemStack helmet = SpecialItems.keepInventoryHelmet(rng);
            if (!SpecialItems.isKeepInventoryHelmet(helmet)) failures++;
            if (SpecialItems.value(helmet) < 500_000_000L) failures++;
            if (SpecialItems.serial(helmet) <= 0) failures++;
        }
        report.append("generated 50 helmets, failures so far=").append(failures).append('\n');

        report.append("\ncrate payout checks\n");
        MysteryShop shop = EconomyPlugin.get().mystery();
        for (MysteryShop.Tier tier : MysteryShop.Tier.values()) {
            long cost = shop.cost(tier);
            long totalValue = 0;
            int rolls = 0;
            int jackpots = 0;
            long min = Long.MAX_VALUE;
            long max = 0;
            for (int i = 0; i < 4000; i++) {
                double roll = rng.nextDouble();
                double multiplier;
                if (roll < 0.60) multiplier = 0.10 + rng.nextDouble() * 0.40;
                else if (roll < 0.82) multiplier = 0.50 + rng.nextDouble() * 0.45;
                else if (roll < 0.95) multiplier = 0.95 + rng.nextDouble() * 0.75;
                else if (roll < 0.99) multiplier = 1.70 + rng.nextDouble() * 1.30;
                else multiplier = 3.00 + rng.nextDouble() * 4.00;
                boolean jackpot = rng.nextDouble() < tier.jackpotChance() || multiplier >= 4.0;
                if (jackpot) multiplier = Math.max(multiplier, 4.2);
                AiItemFactory.Quality quality = jackpot ? AiItemFactory.Quality.byRank(tier.quality().rank() + 1) : tier.quality();
                List<ItemStack> items = AiItemFactory.lootBundle(
                        rng, quality, Math.round(cost * multiplier * tier.payoutFactor()), jackpot);
                if (items.isEmpty()) {
                    failures++;
                    continue;
                }
                long value = 0;
                for (ItemStack item : items) {
                    long itemValue = AiItemFactory.value(item);
                    if (itemValue <= 0) failures++;
                    value += itemValue;
                }
                totalValue += value;
                rolls++;
                if (jackpot) jackpots++;
                min = Math.min(min, value);
                max = Math.max(max, value);
            }
            long average = totalValue / Math.max(1, rolls);
            report.append(tier.id())
                    .append(" cost=").append(cost)
                    .append(" rolls=").append(rolls)
                    .append(" jackpots=").append(jackpots)
                    .append(" avg=").append(average)
                    .append(" return=").append(Math.round(average * 1000.0 / cost) / 10.0).append('%')
                    .append(" min=").append(min)
                    .append(" max=").append(max)
                    .append('\n');
        }
        report.append("\nfailures=").append(failures).append('\n');

        try {
            File file = new File(getDataFolder(), "report.txt");
            Files.createDirectories(file.getParentFile().toPath());
            Files.writeString(file.toPath(), report.toString(), StandardCharsets.UTF_8);
            getLogger().info("MarketHarness report written, failures=" + failures);
        } catch (Exception ex) {
            getLogger().severe("MarketHarness write failed: " + ex);
        }
        Bukkit.getScheduler().runTaskLater(this, Bukkit::shutdown, 20L * 3);
    }
}
