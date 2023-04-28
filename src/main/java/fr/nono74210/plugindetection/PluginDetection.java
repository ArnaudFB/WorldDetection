package fr.nono74210.plugindetection;

import fr.nono74210.plugindetection.listeners.DestroyBlockLister;
import fr.nono74210.plugindetection.timedtypes.TimedHashSet;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class PluginDetection extends JavaPlugin implements Listener {

    private TimedHashSet<Player, Integer, Integer> timedHashSet = new TimedHashSet<>();

    @Override
    public void onEnable() {

        if(Bukkit.getPluginManager().getPlugin("PlaceHolderAPI") != null){
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            getLogger().warning("PlaceHolderAPI n'as pas pu être trouvé. \n" +
                    "PlaceHolderAPI est requis pour lancer le PluginDetection");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        saveDefaultConfig();
        System.out.println("PluginDetection se lance");
        getServer().getPluginManager().registerEvents(new DestroyBlockLister(), this);

        new BukkitRunnable(){

            @Override
            public void run(){

                for(Player player : timedHashSet){

                    World world = player.getWorld();

                    if(timedHashSet.getItemI() > getConfig().getInt("BlockLimit") &&
                    getConfig().getList("WorldList").contains(world)){
                        String alertTitle =  getConfig().getString("messages.alert.title");
                        String alertSubtitle = PlaceholderAPI.setPlaceholders(player,
                                                                getConfig().getString("messages.alert.subtitle"));
                        player.sendTitle(ChatColor.DARK_RED + alertTitle, ChatColor.RED + alertSubtitle);
                    }
                }
            }
        }.runTaskTimer(this, 0L, getConfig().getLong("DelaySeconds.DelayBetweenMessage")*1000L);

    }

    @Override
    public void onDisable() {
        System.out.println("Plugin de detection s'arrête");
    }

    public Player getTimedHashSetPlayer(){
        return timedHashSet.iterator().next();
    }

    public Integer getTimedHashSetCounter(){
        return timedHashSet.getItemI();
    }

}
