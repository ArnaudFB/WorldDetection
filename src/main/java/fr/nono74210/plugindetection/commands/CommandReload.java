package fr.nono74210.plugindetection.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import fr.nono74210.plugindetection.PluginDetection;
import org.jetbrains.annotations.NotNull;

public class CommandReload implements CommandExecutor {

    private final PluginDetection plugin;

    public CommandReload() {
        plugin = PluginDetection.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!sender.hasPermission("detection.reload")) {
            return false;
        }

        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(plugin.getConfig().getString("Messages.BadArguments", "§9§l[§bPlugin§3Detection§9§l]§r §c→ /detection reload is the only available command"));
            return false;
        }

        plugin.reloadConfig();

        sender.sendMessage(plugin.getConfig().getString("Messages.ReloadComplete", "§9§l[§bPlugin§3Detection§9§l]§r §c→ reload completed"));
        return true;
    }

}
