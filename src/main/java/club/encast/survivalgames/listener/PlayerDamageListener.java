package club.encast.survivalgames.listener;

import club.encast.survivalgames.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player) {
            if(!Game.getInstance().getGameSettings().isPvp()) {
                e.setCancelled(true);
            }
        }
    }
}
