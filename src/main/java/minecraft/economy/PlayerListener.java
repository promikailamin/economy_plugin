package minecraft.economy;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        EconomyData.ensureAccount(p); // also records the current name for offline lookups
        EconomyScoreboard.get().restore(p);

        int waiting = EconomyPlugin.get().auctions().claimCount(p.getUniqueId());
        if (waiting > 0) {
            p.sendMessage(ChatColor.YELLOW + "You have " + waiting + " auction item" + (waiting == 1 ? "" : "s")
                    + " waiting. Use /ah collect.");
        }
        long bal = EconomyData.getBalance(p);
        if (bal < 0) {
            p.sendMessage(ChatColor.RED + "You are in debt: " + EconomyData.formatMoney(bal)
                    + ". Money you earn pays it off first.");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        EconomyScoreboard.get().forget(p);
        GuiHistoryManager.get().clear(p);
    }
}
