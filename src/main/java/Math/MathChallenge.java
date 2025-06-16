package Math;

import Events.MathChallengeListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.doz.mathchallengeplugin.MathChallengePlugin;

import java.util.Random;

public class MathChallenge {
    private final MathChallengePlugin plugin;
    private final Random random = new Random();

    public MathChallenge(MathChallengePlugin plugin) {
        this.plugin = plugin;
    }

    public void startChallenge(Player player) {
        int num1 = random.nextInt(10000);
        int num2 = random.nextInt(10000);
        String operator = getRandomOperator();
        String question = num1 + " " + operator + " " + num2;
        player.sendMessage(ChatColor.GREEN + "Вопрос: " + question);

        int correctAnwer = calculateAnswer(num1, num2, operator);
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            player.sendMessage(ChatColor.YELLOW + "Введите ваш ответ:");
            MathChallengeListener.setCurrentChalanges(player, correctAnwer);
        }, 150L);
    }

    private String getRandomOperator() {
        String[] operators = { "+", "-" };
        return operators[random.nextInt(operators.length)];
    }

    private int calculateAnswer(int num1, int num2, String operator) {
        switch (operator) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            default:
                throw new IllegalArgumentException("Неизвестный оператор" + operator);
        }
    }
}
