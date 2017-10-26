package club.encast.survivalgames.state;

import club.encast.gameengine.GameEngine;
import club.encast.gameengine.GameState;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.util.SGUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class StateDeathmatch extends GameState {

    private final int cooldown = 15;

    public StateDeathmatch() {
        super(SGGameState.DEATHMATCH.getInternalName(), "Deathmatch", (60 * 5) + 15, TimeUnit.SECONDS);
    }

    @Override
    public void onStateBegin(GameEngine engine) {
        engine.broadcast("§aYou have been teleported to the deathmatch arena. The game will start in " + cooldown + " seconds!");
        Game game = (Game) engine;
        game.getGameSettings().setPvp(false);
        for(UUID uuid : engine.getGamePlayers()) {
            Player p = Bukkit.getServer().getPlayer(uuid);
            if(p != null) {
                p.teleport(SGUtil.convertToBukkitLocation(game.getMapType().getMap().getDeathmatchLocation(), Game.getInstance()));
            }
        }
    }

    @Override
    public void onStateTick(GameEngine engine) {
        // Code to announce messages if deathmatch is about to start.
        if(engine.getTimer().getCurrentTime() >= (cooldown - 5) && engine.getTimer().getCurrentTime() <= cooldown) {
            engine.broadcast("§cYou can PvP in " + (cooldown - engine.getTimer().getCurrentTime() + 1) + "s!");
        } else if(engine.getTimer().getCurrentTime() == (cooldown + 1)) {
            engine.broadcast("§c§lDEATHMATCH HAS STARTED! GOOD LUCK!");
            ((Game) engine).getGameSettings().setPvp(true);
        }
        // This block of code checks if the timer is 60 or 30 seconds to display the appropriate warning message.
        if(engine.getTimer().getRemainingTime() == 60) {
            engine.broadcast("§cDeathmatch ends in 1 minute.");
        } else if(engine.getTimer().getRemainingTime() == 30) {
            engine.broadcast("§cDeathmatch ends in 30 seconds.");
        }
    }

    @Override
    public void onStateEnd(GameEngine engine) {
        // Nothing
    }
}
