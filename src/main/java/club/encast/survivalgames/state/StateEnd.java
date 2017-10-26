package club.encast.survivalgames.state;

import club.encast.gameengine.GameEngine;
import club.encast.gameengine.GameState;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.player.GamePlayer;
import club.encast.survivalgames.util.SGUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class StateEnd extends GameState {

    public StateEnd() {
        super(SGGameState.END.getInternalName(), "End", 10, TimeUnit.SECONDS);
    }

    @Override
    public void onStateBegin(GameEngine engine) {
        engine.broadcast("§c§lTHE GAME HAS ENDED!");
        ((Game) engine).getGameSettings().setPvp(false);
    }

    @Override
    public void onStateTick(GameEngine engine) {
        //TODO: Clean up code.
        Game game = (Game) engine;
        if(engine.getTimer().getRemainingTime() == 7) {
            engine.broadcast(SGUtil.centerMessage("§e" + StringUtils.repeat("-", 53)));
            if(game.getWinner() != null && Bukkit.getServer().getPlayer(game.getWinner()) != null) {
                Player p = Bukkit.getServer().getPlayer(game.getWinner());
                engine.broadcast(SGUtil.centerMessage("§a§lThe winner is " + p.getName()));
            } else {
                engine.broadcast(SGUtil.centerMessage("§c§lThe game ended with a draw!"));
            }

            engine.broadcast(SGUtil.centerMessage("§e" + StringUtils.repeat("-", 53)));

            // Announces the top 3 players with the most kills in the game.
            Map<UUID, GamePlayer> topKills = game.getTopKills();
            engine.broadcast(SGUtil.centerMessage("§e" + StringUtils.repeat("-", 53)));
            engine.broadcast(SGUtil.centerMessage("§aTop 3 Killers"));
            int currentIndex = 0;
            String[] romanNumerals = {"I", "II", "III"};
            for(Map.Entry<UUID, GamePlayer> entries : topKills.entrySet()) {
                if(currentIndex < topKills.size()) {
                    engine.broadcast(SGUtil.centerMessage("§7[" + romanNumerals[currentIndex] + "]: §b" + entries.getValue().getPlayerName() + " §e- §c" + entries.getValue().getKills()));
                } else {
                    engine.broadcast(SGUtil.centerMessage("§7[" + romanNumerals[currentIndex] + "]: None"));
                }
                currentIndex++;
                if(currentIndex == 3) return;
            }
            engine.broadcast(SGUtil.centerMessage("§e" + StringUtils.repeat("-", 53)));
        }
    }

    @Override
    public void onStateEnd(GameEngine engine) {
        // Everything in the game will be reset to the original state to get ready for the next game.
        ((Game) engine).flushRestart(null);
    }
}
