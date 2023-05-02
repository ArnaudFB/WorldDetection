package fr.nono74210.plugindetection;

import fr.nono74210.plugindetection.commands.CommandReload;
import fr.nono74210.plugindetection.datas.PlayerCounter;
import fr.nono74210.plugindetection.listeners.DestroyBlockListener;
import fr.nono74210.plugindetection.timedtypes.TimedHashSet;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public final class PluginDetection extends JavaPlugin implements Listener {
    private static PluginDetection instance;
    private TimedHashSet<PlayerCounter> playerCounterList;
    private TimedHashSet<UUID> playerMessages;
    public static PluginDetection getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;

        playerCounterList = new TimedHashSet<>();
        playerMessages = new TimedHashSet<>();

        saveDefaultConfig();
        System.out.println("PluginDetection se lance");
        getServer().getPluginManager().registerEvents(new DestroyBlockListener(), this);
        getCommand("detection").setExecutor(new CommandReload());
    }
    @Override
    public void onDisable() {
        System.out.println("Plugin de detection s'arrête");
    }

    public TimedHashSet<PlayerCounter> getPlayerCounter() {
        return playerCounterList;
    }

    public TimedHashSet<UUID> getPlayerMessages() {
        return playerMessages;
    }
}
