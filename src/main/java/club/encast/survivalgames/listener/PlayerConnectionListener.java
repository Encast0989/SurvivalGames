package club.encast.survivalgames.listener;

import club.encast.survivalgames.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage("");
        Game.getInstance().joinGame(p);
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage("");
        Game.getInstance().leaveGame(p);
    }
}
