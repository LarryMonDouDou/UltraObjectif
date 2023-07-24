package eq.larry.dev;

import eq.larry.dev.Listerners.ObjectifListener;
import eq.larry.dev.commands.uob;
import eq.larry.dev.config.Configurator;
import eq.larry.dev.config.Objectif;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class UltraObjectif extends JavaPlugin {
    Objectif currentObjectif = null;

    Configurator configurator;

    static UltraObjectif instance;

    public Objectif getCurrentObjectif() {
        return this.currentObjectif;
    }

    public static UltraObjectif getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        this.configurator = new Configurator();
        this.configurator.loadRewards();
        this.configurator.loadObjectifs();
        getCommand("objectif").setExecutor(new uob());
        getServer().getPluginManager().registerEvents((Listener)new ObjectifListener(this), (Plugin)this);
    }

    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (getCurrentObjectif() == null)
                continue;
            BossBar bossBar = (getCurrentObjectif()).bossBar;
            if (bossBar != null)
                bossBar.removePlayer(player);
        }
    }

    public void setCurrentObjectif(Objectif objectif) {
        if (objectif == null) {
            this.configurator.yamlUtils.getConfig().set("currentObjectif", null);
        } else {
            this.configurator.yamlUtils.getConfig().set("currentObjectif", objectif.getName());
        }
        this.configurator.yamlUtils.save();
        this.currentObjectif = objectif;
    }

    public void registerCommand(String commandName, Command commandClass) {
        try {
            CommandMap commandMap = Bukkit.getServer().getCommandMap();
            commandMap.register(commandName, commandClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

