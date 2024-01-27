package fr.nono74210.worlddetection.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import fr.nono74210.worlddetection.WorldDetection;
import org.jetbrains.annotations.NotNull;

public class CommandReload implements CommandExecutor {

    private final WorldDetection plugin;

    public CommandReload() {
        plugin = WorldDetection.getInstance();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!command.getName().equalsIgnoreCase("detection")) {
            return false;
        }
        if (!sender.hasPermission("detection.reload")) {
            return false;
        }
        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            String defaultBadArgs = "§9§l[§bWorld§3Detection§9§l]§r §c→ /detection reload is the only available command";
            sender.sendMessage(plugin.getConfig().getString("Messages.Bad-Arguments", defaultBadArgs));
            return false;
        } else {
            plugin.reloadConfig();
            String defaultReload = "§9§l[§bWorld§3Detection§9§l]§r §c→ reload completed";
            sender.sendMessage(plugin.getConfig().getString("Messages.Reload-Complete", defaultReload));
            return true;
        }

    }

}
