package club.encast.survivalgames.state;

import club.encast.gameengine.GameEngine;
import club.encast.gameengine.GameState;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.util.SGUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class StateStarting extends GameState {

    public StateStarting() {
        super(SGGameState.STARTING.getInternalName(), "Starting", 10, TimeUnit.SECONDS);
    }

    @Override
    public void onStateBegin(GameEngine engine) {
        // Iterating through the possible spawn locations and teleporting the player there.
//        engine.broadcast("§c[GAME] Please wait while we teleport all players.");
//        Game.getInstance().loadLocations();
        List<Location> spawns = Game.getInstance().getSpawnLocations();
        int currentSpawnIndex = 0;
        for(UUID uuid : engine.getGamePlayers()) {
            Player p = Bukkit.getServer().getPlayer(uuid);
            if(p != null) {
                if(currentSpawnIndex < spawns.size()) {
                    p.teleport(spawns.get(currentSpawnIndex));
                    if((currentSpawnIndex + 1) < spawns.size()) {
                        currentSpawnIndex++; // Set the current index to 0 because there aren't enough spawns for each player to have one.
                    } else {
                        currentSpawnIndex = 0;
                    }
                }
            }
        }
        engine.broadcast("§aThe game will start in " + SGUtil.formatTime(engine.getTimer().getRemainingTime(), TimeUnit.SECONDS) + "!");
        ((Game) engine).getGameSettings().setPvp(false);
    }

    @Override
    public void onStateTick(GameEngine engine) {
        if(engine.getTimer().getRemainingTime() <= 5) {
            engine.broadcast("§aThe game will start in " + SGUtil.formatTime(engine.getTimer().getRemainingTime(), TimeUnit.SECONDS) + "!");
            ((Game) engine).getSortedGamePlayers().forEach(p
                    -> p.playSound(p.getLocation(), Sound.NOTE_STICKS, 5, 1));
        }
        if(engine.getTimer().getRemainingTime() == 1) {
            ((Game) engine).getGameSettings().setPvp(true);
        }
    }

    @Override
    public void onStateEnd(GameEngine engine) {
        // This has to be done because sometimes, some players can't see other players after teleporting.
        for(Player o : Bukkit.getServer().getOnlinePlayers()) {
            for(Player o2 : Bukkit.getServer().getOnlinePlayers()) {
                if(o != o2) o.showPlayer(o2);
            }
        }
    }
}
