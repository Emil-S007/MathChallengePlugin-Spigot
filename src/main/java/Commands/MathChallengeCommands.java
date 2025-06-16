package Commands;

import Math.MathChallenge;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.doz.mathchallengeplugin.MathChallengePlugin;

import java.util.HashMap;
import java.util.Map;

public class MathChallengeCommands implements CommandExecutor {
    private final MathChallengePlugin plugin;
    private final MathChallenge mathChallenge;
    private final long cooldown = 120000;
    private final Map<String, Long> cooldowns = new HashMap<>();

    public MathChallengeCommands(MathChallengePlugin plugin) {
        this.plugin = plugin;
        this.mathChallenge = new MathChallenge(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            String playerId = player.getUniqueId().toString();
            long currentTime = System.currentTimeMillis();

            if (cooldowns.containsKey(playerId)) {
                long lastUsed = cooldowns.get(playerId);
                if (currentTime - lastUsed < cooldown) {
                    long remainingTime = (cooldown - (currentTime - lastUsed)) / 1000;
                    player.sendMessage(ChatColor.RED + "Пожалуйста, подождите " + remainingTime + " секунд перед повторным использованием команды.");
                    return true;
                }
            }

            mathChallenge.startChallenge(player);
            cooldowns.put(playerId, currentTime);
            return true;
        } else {
            commandSender.sendMessage(ChatColor.RED + "Эта команда может быть использована только игроками.");
            return false;
        }
    }
}
