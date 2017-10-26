package club.encast.survivalgames.listener;

import club.encast.gameengine.GameState;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.state.SGGameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.Random;

public class FoodLevelListener implements Listener {

    private Random r = new Random();

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        Game game = Game.getInstance();
        GameState state = game.getTimer().getCurrentState();

        if(e.getFoodLevel() < p.getFoodLevel()) {
            if(state.getInternalName().equals(SGGameState.GAME.getInternalName())
                    || state.getInternalName().equals(SGGameState.DEATHMATCH.getInternalName())) {
                if((r.nextInt(20) + 1) % 2 != 0) {
                    e.setCancelled(true); // Odd numbers will make the player lose hunger.
                }
            } else {
                if(e.getFoodLevel() != 20) {
                    e.setFoodLevel(20); // To fully replenish the player's food level.
                }
                e.setCancelled(true);
            }
        }
    }
}
