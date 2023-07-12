package fr.nono74210.plugindetection;

import fr.nono74210.plugindetection.commands.CommandReload;
import fr.nono74210.plugindetection.commands.DetectionTab;
import fr.nono74210.plugindetection.datas.PlayerCounter;
import fr.nono74210.plugindetection.listeners.DestroyBlockListener;
import fr.nono74210.plugindetection.timedtypes.TimedHashSet;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class PluginDetection extends JavaPlugin implements Listener {
    private static PluginDetection instance;
    private TimedHashSet<PlayerCounter> playerCounterList;
    private TimedHashSet<UUID> playerMessages;

    public static ConsoleCommandSender log;

    @Override
    public void onEnable() {
        instance = this;
        log = Bukkit.getConsoleSender();

        playerCounterList = new TimedHashSet<>();
        playerMessages = new TimedHashSet<>();

        log.sendMessage(MessageUtils.colorize("&bPlugin&3Detection &eenabling..."));
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new DestroyBlockListener(), this);

        getCommand("detection").setExecutor(new CommandReload());
        getCommand("detection").setTabCompleter(new DetectionTab());

        log.sendMessage(MessageUtils.colorize("&bPlugin&3Detection &aenabled!"));
    }

    @Override
    public void onDisable() {
        log.sendMessage(MessageUtils.colorize("&bPlugin&3Detection &cdisabled!"));
    }

    public static PluginDetection getInstance() { return instance; }

    public TimedHashSet<PlayerCounter> getPlayerCounter() {
        return playerCounterList;
    }

    public TimedHashSet<UUID> getPlayerMessages() {
        return playerMessages;
    }
}
