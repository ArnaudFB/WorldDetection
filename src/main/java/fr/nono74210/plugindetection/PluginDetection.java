package fr.nono74210.plugindetection;

import fr.nono74210.plugindetection.listeners.DestroyBlockLister;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginDetection extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        System.out.println("PluginDetection se lance");
        getServer().getPluginManager().registerEvents(new DestroyBlockLister(), this);

    }

    @Override
    public void onDisable() {
        System.out.println("Plugin de detection s'arrête");
    }
}
