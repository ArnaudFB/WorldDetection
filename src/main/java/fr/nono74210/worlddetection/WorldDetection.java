package fr.nono74210.worlddetection;

import fr.nono74210.worlddetection.commands.CommandReload;
import fr.nono74210.worlddetection.commands.DetectionTab;
import fr.nono74210.worlddetection.datas.PlayerCounter;
import fr.nono74210.worlddetection.listeners.DestroyBlockListener;
import fr.nono74210.worlddetection.timedtypes.TimedHashSet;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;

public final class WorldDetection extends JavaPlugin implements Listener {
    private static WorldDetection instance;
    private TimedHashSet<PlayerCounter> playerCounterList;
    private TimedHashSet<UUID> playerMessages;
    public static WorldDetection getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;

        playerCounterList = new TimedHashSet<>();
        playerMessages = new TimedHashSet<>();

        saveDefaultConfig();
        Bukkit.getLogger();
        getServer().getPluginManager().registerEvents(new DestroyBlockListener(), this);
        Objects.requireNonNull(this.getCommand("detection")).setExecutor(new CommandReload());
        Objects.requireNonNull(this.getCommand("detection")).setTabCompleter(new DetectionTab());
    }
    @Override
    public void onDisable() {
        Bukkit.getLogger();
    }

    public TimedHashSet<PlayerCounter> getPlayerCounter() {
        return playerCounterList;
    }

    public TimedHashSet<UUID> getPlayerMessages() {
        return playerMessages;
    }
}
