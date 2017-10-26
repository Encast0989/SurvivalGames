package club.encast.survivalgames.listener;

import club.encast.survivalgames.Game;
import club.encast.survivalgames.player.GamePlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Game game = Game.getInstance();
        e.setDeathMessage("");
        if(game.getGamePlayers().contains(p.getUniqueId())) {
            game.removeGamePlayer(p.getUniqueId());
            game.setSpectator(p);
            p.setGameMode(GameMode.SPECTATOR);
            if(e.getEntity().getKiller() != null) {
                Player killer = e.getEntity().getKiller();
                game.broadcast("§b" + p.getName() + " §ewas killed by §b" + killer.getName() + "§e!");
                GamePlayer gp = Game.getInstance().getSgPlayers().get(killer.getUniqueId());
                if(gp != null) {
                    gp.setKills(gp.getKills() + 1);
                }
            } else {
                game.broadcast("§b" + p.getName() + " §edied!");
            }
            p.sendMessage("§7You are now a spectator!");
        }
    }
}
