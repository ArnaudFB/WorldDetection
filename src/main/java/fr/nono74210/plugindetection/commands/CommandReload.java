package fr.nono74210.plugindetection.commands;

import fr.nono74210.plugindetection.MessageUtils;
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
            sender.sendMessage(MessageUtils.colorize(plugin.getConfig().getString("Messages.BadArguments", "&bPlugin&3Detection &8→ &cThere's an error in the command.")));
            return false;
        }

        if (plugin.getConfig().getBoolean("ClearPlayersTimesOnReload", false)) {
            plugin.getPlayerCounter().clear();
            plugin.getPlayerMessages().clear();
        }

        plugin.reloadConfig();

        sender.sendMessage(MessageUtils.colorize(plugin.getConfig().getString("Messages.ReloadComplete", "&bPlugin&3Detection &8→ &aPlugin reloaded without error.")));
        return true;
    }

}
