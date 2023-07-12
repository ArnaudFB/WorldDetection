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
            sender.sendMessage(plugin.getConfig().getString("Messages.BadArguments", "&bPlugin&3Detection &8→ &cThere's an error in the command."));
            return false;
        }

        plugin.reloadConfig();

        sender.sendMessage(plugin.getConfig().getString("Messages.ReloadComplete", "&bPlugin&3Detection &8→ &aPlugin reloaded without error."));
        return true;
    }

}
