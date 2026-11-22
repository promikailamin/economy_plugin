package minecraft.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class EconomyPlugin extends JavaPlugin {

    private static EconomyPlugin instance;
    private AuctionHouse auctions;
    private AuctionAi ai;
    private MysteryShop mystery;

    public static EconomyPlugin get() {
        return instance;
    }

    public AuctionHouse auctions() {
        return auctions;
    }

    public AuctionAi ai() {
        return ai;
    }

    public MysteryShop mystery() {
        return mystery;
    }

    public void reloadMarketConfig() {
        if (ai != null) ai.reload();
        if (mystery != null) mystery.reload();
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        EconomyData.init(this);
        auctions = new AuctionHouse(this);
        auctions.load();
        ai = new AuctionAi(this, auctions);
        ai.load();
        mystery = new MysteryShop(this);
        mystery.load();

        EconomyCommands handler = new EconomyCommands(this);
        for (String name : new String[]{"bal", "baltop", "pay", "fine", "eco", "shop", "buy", "sell", "ecosb", "ah", "mystery", "sellaxe"}) {
            PluginCommand cmd = getCommand(name);
            if (cmd == null) {
                getLogger().severe("Command '" + name + "' is missing from plugin.yml");
                continue;
            }
            cmd.setExecutor(handler);
            cmd.setTabCompleter(handler);
        }

        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new KeepInventoryListener(this), this);
        Bukkit.getPluginManager().registerEvents(new SellAxeListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpecialToolListener(), this);
        Bukkit.getPluginManager().registerEvents(new AnvilBypassListener(), this);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) SpecialItems.tickArmor(p);
        }, 20L, 20L);

        // Players already online (e.g. after /reload) need an account and their sidebar.
        for (Player p : Bukkit.getOnlinePlayers()) {
            EconomyData.ensureAccount(p);
            EconomyScoreboard.get().restore(p);
        }

        long saveTicks = Math.max(1, getConfig().getLong("autosave-minutes", 5)) * 60L * 20L;
        Bukkit.getScheduler().runTaskTimer(this, EconomyData::saveAsync, saveTicks, saveTicks);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            auctions.expireOld();
            if (ai != null) ai.tick();
            if (mystery != null) mystery.tick();
        }, 20L * 30, 20L * 30);

        getLogger().info("Economy enabled.");
    }

    @Override
    public void onDisable() {
        // Close open economy screens first so the sell chest pays out and returns items.
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getOpenInventory().getTopInventory().getHolder(false) instanceof EcoGui) {
                p.closeInventory();
            }
        }
        EconomyData.save();
        if (auctions != null) auctions.save();
        if (ai != null) ai.save();
        if (mystery != null) mystery.save();
        getLogger().info("Economy disabled.");
    }
}
