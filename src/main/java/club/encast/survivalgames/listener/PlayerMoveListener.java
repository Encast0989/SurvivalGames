package club.encast.survivalgames.listener;

import club.encast.survivalgames.Game;
import club.encast.survivalgames.state.SGGameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if(Game.getInstance().getTimer().getCurrentState().getInternalName().equals(SGGameState.STARTING.getInternalName())) {
            if(e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ())
                e.getPlayer().teleport(e.getFrom());
        }
    }
}
