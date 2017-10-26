package club.encast.survivalgames.mode;

import club.encast.survivalgames.Game;
import club.encast.survivalgames.SurvivalGames;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AntiKnockbackMode extends ModifiedMode implements Listener {

    public AntiKnockbackMode() {
        super("Anti Kb.");
    }

    @Override
    public void onActivate(Game game) {
        // Nothing
    }

    @Override
    public void onTick(Game game) {
        // Nothing
    }

    @EventHandler
    public void onPlayerKnockbackTake(EntityDamageByEntityEvent e) {
        if(Game.getInstance().hasModifiedMode(ModifiedModeType.ANTI_KNOCKBACK)) {
            if(e.getEntity() instanceof Player) {
                Player p = (Player) e.getEntity();
                p.setVelocity(new Vector());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.setVelocity(new Vector());
                    }
                }.runTaskLater(SurvivalGames.getInstance(), 1L);
            }
        }
    }
}
