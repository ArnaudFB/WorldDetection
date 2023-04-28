package fr.nono74210.plugindetection.listeners;

import fr.nono74210.plugindetection.PluginDetection;
import fr.nono74210.plugindetection.timedtypes.TimedHashSet;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.clip.placeholderapi.PlaceholderAPI;

public class DestroyBlockLister implements Listener {
    private PluginDetection pluginDetection;
    public void getPluginDetection(PluginDetection pluginDetection){

        this.pluginDetection = pluginDetection;

    }
    Integer blockLimit = pluginDetection.getConfig().getInt("BlockLimit");
    TimedHashSet timedHashSet = new TimedHashSet();
    long delayMillis = pluginDetection.getConfig().getInt("DelaySeconds.DelayResetCounter");
    @EventHandler
    public void PlayerDestroyBlock(BlockBreakEvent event){

        Player player = event.getPlayer();
        Material material = event.getBlock().getType();
        World world = player.getWorld();

        Integer blockCounter = (Integer)timedHashSet.getItemI();

        if(!player.hasPermission("detection.bypass") &&
            pluginDetection.getConfig().getList("WorldList").contains(world) &&
            pluginDetection.getConfig().getList("BlockList").contains(material)){

            if(timedHashSet.contains(player)){

                timedHashSet.add(player,blockCounter + 1 , delayMillis);

            } else {

                timedHashSet.add(player, 0, delayMillis);

            }

        }

    }

}


