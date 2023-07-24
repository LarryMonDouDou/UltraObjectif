package eq.larry.dev.commands;

import eq.larry.dev.config.Objectif;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class uob implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("start name");
            sender.sendMessage("stop");
            sender.sendMessage("reload");
            return true;
        }
        if (args[0].equalsIgnoreCase("start")) {
            if (args.length == 1) {
                sender.sendMessage("start name");
                return true;
            }
            if (!Objectif.getObjectifs().containsKey(args[1])) {
                sender.sendMessage("not found");
                return true;
            }
            Objectif objectif = (Objectif)Objectif.getObjectifs().get(args[1]);
            objectif.current = 0;
            objectif.genTitle();
            objectif.launch();
        }
        if (args[0].equalsIgnoreCase("stop")) {
            Objectif objectif = eq.larry.dev.UltraObjectif.getInstance().getCurrentObjectif();
            if (objectif == null) {
                sender.sendMessage("objectif is running");
                return true;
            }
            objectif.stop();
        }
        return false;
    }

}
