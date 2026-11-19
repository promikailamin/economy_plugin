package minecraft.economy;

import io.papermc.paper.scoreboard.numbers.NumberFormat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server-side sidebar showing the player's balance (no client mod needed).
 * Each player gets a private scoreboard; {@code /ecosb} toggles it and the choice is saved.
 */
public final class EconomyScoreboard {

    private static final EconomyScoreboard INSTANCE = new EconomyScoreboard();
    private static final String BALANCE_ENTRY = ChatColor.RESET.toString();

    public static EconomyScoreboard get() {
        return INSTANCE;
    }

    private final Map<UUID, Scoreboard> boards = new ConcurrentHashMap<>();

    private EconomyScoreboard() {
    }

    public boolean isVisible(Player player) {
        return boards.containsKey(player.getUniqueId());
    }

    public void toggle(Player player) {
        if (isVisible(player)) {
            hide(player);
            EconomyData.setScoreboardEnabled(player.getUniqueId(), false);
            player.sendMessage(ChatColor.GRAY + "Economy scoreboard " + ChatColor.RED + "disabled" + ChatColor.GRAY + ".");
        } else {
            show(player);
            EconomyData.setScoreboardEnabled(player.getUniqueId(), true);
            player.sendMessage(ChatColor.GRAY + "Economy scoreboard " + ChatColor.GREEN + "enabled" + ChatColor.GRAY + ".");
        }
    }

    @SuppressWarnings("deprecation")
    public void show(Player player) {
        if (Bukkit.getScoreboardManager() == null) return;
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("economy", "dummy", ChatColor.GOLD + "" + ChatColor.BOLD + "Economy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.numberFormat(NumberFormat.blank()); // hide the red score numbers

        obj.getScore(" ").setScore(3);
        obj.getScore(ChatColor.GRAY + "Balance").setScore(2);
        obj.getScore(BALANCE_ENTRY).setScore(1);
        Team team = board.registerNewTeam("bal");
        team.addEntry(BALANCE_ENTRY);

        boards.put(player.getUniqueId(), board);
        player.setScoreboard(board);
        update(player);
    }

    public void hide(Player player) {
        boards.remove(player.getUniqueId());
        if (Bukkit.getScoreboardManager() != null) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }

    /** Called on quit: forget the board but keep the saved preference. */
    public void forget(Player player) {
        boards.remove(player.getUniqueId());
    }

    /** Called on join: restores the sidebar if the player had it enabled. */
    public void restore(Player player) {
        if (EconomyData.scoreboardEnabled(player.getUniqueId())) show(player);
    }

    public void update(UUID id) {
        Player p = Bukkit.getPlayer(id);
        if (p != null) update(p);
    }

    @SuppressWarnings("deprecation")
    public void update(Player player) {
        Scoreboard board = boards.get(player.getUniqueId());
        if (board == null) return;
        Team team = board.getTeam("bal");
        if (team == null) return;
        long balance = EconomyData.getBalance(player);
        ChatColor color = balance < 0 ? ChatColor.RED : ChatColor.GOLD;
        team.setPrefix(color + EconomyData.formatMoney(balance));
    }

    public void updateAll() {
        for (Player p : Bukkit.getOnlinePlayers()) update(p);
    }
}
