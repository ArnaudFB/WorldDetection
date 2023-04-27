package fr.nono74210.plugindetection.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class DestroyBlockLister implements Listener {

    @EventHandler
    public void PlayerDestroyBlock(BlockBreakEvent event){

        Player player = event.getPlayer();
        Material material = event.getBlock().getType();

        if(!player.hasPermission("detection.bypass")){

        }

    }

}
