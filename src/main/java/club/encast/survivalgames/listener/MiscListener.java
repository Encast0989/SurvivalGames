package club.encast.survivalgames.listener;

import club.encast.survivalgames.Game;
import club.encast.survivalgames.util.SGUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class MiscListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockMelt(BlockFadeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(SGUtil.convertToBukkitLocation(Game.getInstance().getMapType().getMap().getLobbyLocation(), Game.getInstance()));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTntPlace(BlockPlaceEvent e) {
        if(e.getBlock().getType() == Material.TNT) {
            e.setCancelled(true);
            TNTPrimed tnt = (TNTPrimed) e.getBlock().getLocation().getWorld().spawnEntity(e.getBlockAgainst().getLocation(), EntityType.PRIMED_TNT);
            tnt.setFuseTicks(60);
        }
    }
}
