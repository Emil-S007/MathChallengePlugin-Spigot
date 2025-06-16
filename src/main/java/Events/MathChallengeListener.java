package Events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import ru.doz.mathchallengeplugin.MathChallengePlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MathChallengeListener implements Listener {
    private final MathChallengePlugin plugin;
    private static final Map<UUID, Integer> currentChalanges = new HashMap<>();
    private final Random random = new Random();

    public MathChallengeListener(MathChallengePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        String Message = event.getMessage();

        if (currentChalanges.containsKey(playerId))
        {
            int correctAnswer = currentChalanges.get(playerId);
            try {
                int playerAnswer = Integer.parseInt(Message);
                if (playerAnswer == correctAnswer)
                {
                    player.sendMessage(ChatColor.GREEN + "Правильно! Вы получили приз!");
                    ItemStack item = new ItemStack(RandomItem(), random.nextInt(16,  32));
                    player.getInventory().addItem(item);
                    player.playSound(player.getLocation(), "entity.player.levelup", 1.0f, 1.0f);
                }else {
                    player.sendMessage(ChatColor.RED + "Неправильно. Попробуйте снова!");
                }
            } catch (NumberFormatException  ex) {
                player.sendMessage(ChatColor.RED + "Пожалуйста, введите число!");
            }
            currentChalanges.remove(playerId);
        }
    }

    private Material RandomItem() {
        Material[] items = { Material.WHEAT, Material.DIAMOND, Material.IRON_INGOT, Material.GOLD_INGOT, Material.FEATHER, Material.GUNPOWDER, Material.STRING, Material.ARROW, Material.FLINT, Material.GOLDEN_APPLE, Material.NETHER_BRICK, Material.FIREWORK_ROCKET };
        return items[random.nextInt(items.length)];
    }

    public static void setCurrentChalanges(Player player, int answer) {
        currentChalanges.put(player.getUniqueId(), answer);
    }
}
