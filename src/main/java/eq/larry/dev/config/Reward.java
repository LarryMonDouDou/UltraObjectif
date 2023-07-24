package eq.larry.dev.config;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reward {
    public void setCommand(String command) {
        this.command = command;
    }

    static final HashMap<String, Reward> rewards = new HashMap<>();

    public static HashMap<String, Reward> getRewards() {
        return rewards;
    }

    static final HashMap<UUID, Reward> listeDesFDPQuiSeDeco = new HashMap<>();

    private String command;

    private final String name;

    public static HashMap<UUID, Reward> getListeDesFDPQuiSeDeco() {
        return listeDesFDPQuiSeDeco;
    }

    public Reward(String name) {
        this.command = null;
        this.name = name;
        rewards.put(name, this);
    }

    public String getCommand() {
        return this.command;
    }

    public String getName() {
        return this.name;
    }

    public void execute(Player player) {
        if (this.command != null)
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), this.command.replaceAll("%player%", player.getName()));
    }
}
