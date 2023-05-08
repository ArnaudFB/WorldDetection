package fr.nono74210.plugindetection.listeners;

import fr.nono74210.plugindetection.PluginDetection;
import fr.nono74210.plugindetection.datas.PlayerCounter;
import fr.nono74210.plugindetection.timedtypes.TimedHashSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import com.onarandombox.MultiverseCore.MultiverseCore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DestroyBlockListener implements Listener {
    private final PluginDetection plugin;

    MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

    public DestroyBlockListener() {
        plugin = PluginDetection.getInstance();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Material material = event.getBlock().getType();
        World world = player.getWorld();
        List<String> worldList = plugin.getConfig().getStringList("WorldList");
        List<String> blockList = plugin.getConfig().getStringList("BlockList");

        if (player.hasPermission("detection.bypass")) {
            return;
        }

        if (!worldList.contains(world.getName()) || !blockList.contains(material.name())) {
            return;
        }

        Optional<UUID> optPlayerUUID = plugin.getPlayerMessages().get().stream()
                .map(TimedHashSet.TimedItem::item)
                .filter(pM -> player.getUniqueId().equals(pM))
                .findFirst();

        Optional<PlayerCounter> optPlayerCounter = plugin.getPlayerCounter().get().stream()
                .map(TimedHashSet.TimedItem::item)
                .map(PlayerCounter.class::cast)
                .filter(pC -> player.getUniqueId().equals(pC.uuid()))
                .filter(pC -> player.getWorld().equals(pC.world()))
                .findFirst();

        if (optPlayerCounter.isEmpty()) {
            // Sinon ajout au timedHashSet
            plugin.getPlayerCounter().add(new PlayerCounter(player.getUniqueId(), world, 1),
                    plugin.getConfig().getLong("DelaySeconds.DelayResetCounter", 10L)*1000L);

            if (plugin.getConfig().getBoolean("debug", false) && player.hasPermission("detection.debug")) {
                player.sendMessage("Ajout de " + player.getName() + " dans la liste");
            }

            return;
        }

        // Si le joueur a déjà un compteur, incrémentation
        PlayerCounter playerCounter = optPlayerCounter.get();
        playerCounter.incrementCount();

        if (plugin.getConfig().getBoolean("debug", false) && player.hasPermission("detection.debug")) {
            player.sendMessage("Incrément de " + playerCounter.counter() + " pour " + player.getName());
        }

        if ((playerCounter.counter() <= plugin.getConfig().getInt("BlockLimit")) || optPlayerUUID.isPresent()) {
            return;
        } // Si il est dans le monde concerné et son compteur dépasse la limite
            String alias = core.getMVWorldManager().getMVWorld(player.getWorld()).getAlias();
            String defaultTitle = "You have destroyed too many blocks !";
            String defaultSubtitle = "Keep in mind, you are in the World : ";
            String alertTitle = plugin.getConfig().getString("Messages.Alert.Title", defaultTitle);
            String alertSubtitle = plugin.getConfig().getString("Messages.Alert.Subtitle", defaultSubtitle) + alias;
            plugin.getPlayerMessages().add(player.getUniqueId(), plugin.getConfig().getLong("DelaySeconds.DelayBetweenMessage", 10L)*1000L);
            // Envoie du message d'alerte de la config
            player.sendTitle(ChatColor.DARK_RED + alertTitle, ChatColor.RED + alertSubtitle, 10, 70, 20);
        }
    }
