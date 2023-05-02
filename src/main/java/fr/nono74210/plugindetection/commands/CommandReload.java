package fr.nono74210.plugindetection.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import fr.nono74210.plugindetection.PluginDetection;
import org.bukkit.configuration.file.YamlConfiguration;

public class CommandReload implements CommandExecutor {

    private final PluginDetection plugin;

    public CommandReload() {
        plugin = PluginDetection.getInstance();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!command.getName().equalsIgnoreCase("detection")) {
            return false;
        }
        if (!sender.hasPermission("detection.reload")) {
            return false;
        }
        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(plugin.getConfig().getString("Messages.Bad-Arguments"));
            return false;
        } else {
            plugin.reloadConfig();
            sender.sendMessage(plugin.getConfig().getString("Messages.Reload-Complete"));
            return true;
        }

    }

}
