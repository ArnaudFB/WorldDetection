package fr.nono74210.pluginblockdetection;

import fr.nono74210.pluginblockdetection.commands.CommandReload;
import fr.nono74210.pluginblockdetection.commands.DetectionTab;
import fr.nono74210.pluginblockdetection.datas.PlayerCounter;
import fr.nono74210.pluginblockdetection.listeners.DestroyBlockListener;
import fr.nono74210.pluginblockdetection.timedtypes.TimedHashSet;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class PluginBlockDetection extends JavaPlugin implements Listener {
    private static PluginBlockDetection instance;
    private TimedHashSet<PlayerCounter> playerCounterList;
    private TimedHashSet<UUID> playerMessages;

    public static ConsoleCommandSender log;

    @Override
    public void onEnable() {
        instance = this;
        log = Bukkit.getConsoleSender();

        playerCounterList = new TimedHashSet<>();
        playerMessages = new TimedHashSet<>();

        log.sendMessage(MessageUtils.colorize("&bPlugin&3Block&bDetection &eenabling..."));
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new DestroyBlockListener(), this);

        getCommand("detection").setExecutor(new CommandReload());
        getCommand("detection").setTabCompleter(new DetectionTab());

        log.sendMessage(MessageUtils.colorize("&bPlugin&3Block&bDetection &aenabled!"));
    }

    @Override
    public void onDisable() {
        log.sendMessage(MessageUtils.colorize("&bPlugin&3Block&bDetection &cdisabled!"));
    }

    public static PluginBlockDetection getInstance() { return instance; }

    public TimedHashSet<PlayerCounter> getPlayerCounter() {
        return playerCounterList;
    }

    public TimedHashSet<UUID> getPlayerMessages() {
        return playerMessages;
    }
}
