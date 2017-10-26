package club.encast.survivalgames.listener;

import club.encast.gameengine.GameState;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.state.SGGameState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChestListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getClickedBlock().getState() instanceof Chest) {
                GameState state = Game.getInstance().getTimer().getCurrentState();
                if(state.getInternalName().equals(SGGameState.GAME.getInternalName())
                        || state.getInternalName().equals(SGGameState.DEATHMATCH.getInternalName())) {
                    Chest chest = (Chest) e.getClickedBlock().getState();
                    if(!Game.getInstance().getChests().contains(chest)) {
                        Game.getInstance().randomizeChestLoot(chest);
                    }
                } else {
                    e.setCancelled(true);
                }
            }
        }
    }
}
