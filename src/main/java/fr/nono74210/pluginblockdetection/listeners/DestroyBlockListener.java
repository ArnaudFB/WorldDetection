package fr.nono74210.pluginblockdetection.listeners;

import fr.nono74210.pluginblockdetection.MessageUtils;
import fr.nono74210.pluginblockdetection.PluginBlockDetection;
import fr.nono74210.pluginblockdetection.datas.PlayerCounter;
import fr.nono74210.pluginblockdetection.timedtypes.TimedHashSet;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import com.onarandombox.MultiverseCore.MultiverseCore;

public class DestroyBlockListener implements Listener {
    private final PluginBlockDetection plugin;

    MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

    public DestroyBlockListener() {
        plugin = PluginBlockDetection.getInstance();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        var player = event.getPlayer();
        var material = event.getBlock().getType();
        var world = player.getWorld();

        var worldList = plugin.getConfig().getStringList("WorldList");
        var blockList = plugin.getConfig().getStringList("BlockList");

        if (player.hasPermission("detection.bypass")) {
            return;
        }

        if (!worldList.contains(world.getName()) || !blockList.contains(material.name())) {
            return;
        }

        var optPlayerUUID = plugin.getPlayerMessages().get().stream()
                .map(TimedHashSet.TimedItem::item)
                .filter(pM -> player.getUniqueId().equals(pM))
                .findFirst();

        var optPlayerCounter = plugin.getPlayerCounter().get().stream()
                .map(TimedHashSet.TimedItem::item)
                .map(PlayerCounter.class::cast)
                .filter(pC -> player.getUniqueId().equals(pC.uuid()))
                .filter(pC -> player.getWorld() == pC.world())
                .findFirst();

        if (optPlayerCounter.isEmpty()) {
            // Sinon ajout au timedHashSet
            plugin.getPlayerCounter().add(new PlayerCounter(player.getUniqueId(), world, 1),
                    plugin.getConfig().getLong("DelaySeconds.DelayResetCounter", 10L) * 1000L);

            if (plugin.getConfig().getBoolean("Debug", false) && player.hasPermission("detection.debug")) {
                player.sendMessage("Ajout de " + player.getName() + " dans la liste");
            }

            return;
        }

        // Si le joueur a déjà un compteur, incrémentation
        var playerCounter = optPlayerCounter.get();
        playerCounter.incrementCount();

        if (plugin.getConfig().getBoolean("Debug", false) && player.hasPermission("detection.debug")) {
            player.sendMessage("Incrément de " + playerCounter.counter() + " pour " + player.getName());
        }

        if ((playerCounter.counter() <= plugin.getConfig().getInt("BlockLimit")) || optPlayerUUID.isPresent()) {
            return;
        }

        // Si il est dans le monde concerné et son compteur dépasse la limite
        var alertTitle = plugin.getConfig().getString("Messages.Alert.Title", "&cYou mined too many blocks");
        var alertSubtitle = plugin.getConfig().getString("Messages.Alert.Subtitle", "&7&oDon't forget you are in the world &e&o%worldName%")
                .replaceAll("%worldName%", core.getMVWorldManager().getMVWorld(player.getWorld()).getAlias());

        plugin.getPlayerMessages().add(player.getUniqueId(), plugin.getConfig().getLong("DelaySeconds.DelayBetweenMessage", 10L) * 1000L);

        // Envoie du message d'alerte de la config
        player.sendTitle(MessageUtils.colorize(alertTitle), MessageUtils.colorize(alertSubtitle), 10, 70, 20);
    }
}