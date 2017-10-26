package club.encast.survivalgames.state;

import club.encast.gameengine.GameEngine;
import club.encast.gameengine.GameState;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.event.SGEvent;
import club.encast.survivalgames.mode.ModifiedMode;
import club.encast.survivalgames.util.SGUtil;

import java.util.concurrent.TimeUnit;

public class StateGame extends GameState {

    public StateGame() {
        super(SGGameState.GAME.getInternalName(), "Game", 10, TimeUnit.MINUTES);
    }

    @Override
    public void onStateBegin(GameEngine engine) {
        engine.broadcast("§aThe game has started!");
        Game game = (Game) engine;
        game.getGameSettings().setPvp(true);
        // Displays a message for when deathmatch will start. If the duration seconds is greater
        // than 60, then the duration will be converted into minutes. If not, then it'll be in seconds.
        // SGUtil::formatTime will format the time values.
        engine.broadcast("§aDeathmatch starts in " + (getDurationSeconds() >= 60 ?
                SGUtil.formatTime(TimeUnit.MINUTES.convert(getDurationSeconds(), TimeUnit.SECONDS), TimeUnit.MINUTES)
                : SGUtil.formatTime(getDurationSeconds(), TimeUnit.SECONDS)));

        // Activate all the modified modes for the game.
        if(game.getModifiedModes().size() >= 1) {
            engine.broadcast("§e§lModified Modes for this game:");
            for(ModifiedMode mode : game.getModifiedModes()) {
                mode.onActivate(game);
                engine.broadcast("  §c- " + mode.getName());
            }
        }

        // Activate the SGEvent for the game if there is any.
        if(game.getCurrentEvent() != null) {
            SGEvent event = game.getCurrentEvent();
            event.onActivate(game);
            game.broadcast("§e[EVENT] The §6" + event.getName() + " §eEvent is running this game!");
        }
    }

    @Override
    public void onStateTick(GameEngine engine) {
        // Announces warning times for deathmatch.
        long remainingTime = engine.getTimer().getRemainingTime();
        if(remainingTime == 60 || remainingTime == 30) {
            engine.broadcast("§cDeathmatch starts in " + SGUtil.formatTime(remainingTime, TimeUnit.SECONDS) + "!");
        } else if(remainingTime <= 5) {
            engine.broadcast("§cDeatmatch starts in " + SGUtil.formatTime(remainingTime, TimeUnit.SECONDS) + "!");
        }
    }

    @Override
    public void onStateEnd(GameEngine engine) {
        // Nothing
    }
}
