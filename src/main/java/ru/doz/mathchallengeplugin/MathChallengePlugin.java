package ru.doz.mathchallengeplugin;

import Commands.MathChallengeCommands;
import org.bukkit.plugin.java.JavaPlugin;
import Events.MathChallengeListener;
public final class MathChallengePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("mathchallenge").setExecutor(new MathChallengeCommands(this));
        getServer().getPluginManager().registerEvents(new MathChallengeListener(this), this);
    }

    @Override
    public void onDisable() {
    }
}
