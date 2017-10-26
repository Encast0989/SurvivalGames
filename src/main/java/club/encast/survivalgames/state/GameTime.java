package club.encast.survivalgames.state;

import club.encast.gameengine.GameEngine;
import club.encast.gameengine.GameState;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.mode.ModifiedMode;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class GameTime extends GameState {

    private boolean dmEnabled = false;
    private final int dmStartTime = 30;

    public GameTime() {
        super("game-time", "Game Time", 0, TimeUnit.SECONDS);
        setInfinite(true);
    }

    @Override
    public void onStateBegin(GameEngine engine) {
        // Nothing
    }

    @Override
    public void onStateTick(GameEngine engine) {
        // We can safely cast this to Game because we know that the GameEngine class will only be
        // an instance of the Game class.
        Game game = (Game) engine;
        GameState state = Game.getInstance().getTimer().getCurrentState();
        // Checking if the player count is at 3 or less (but greater than 1) to start deathmatch
        if(dmEnabled) {
            if(state.getInternalName().equals(SGGameState.GAME.getInternalName())) {
                if(engine.getGamePlayers().size() <= 3 && engine.getGamePlayers().size() > 1) {
                    if(engine.getTimer().getRemainingTime() > dmStartTime) {
                        engine.getTimer().setCurrentTime(dmStartTime);
                        engine.broadcast("§cDeathmatch starts in " + dmStartTime + " seconds!");
                    }
                }
            }
        }

        // The following will be run if the game is running.
        if(state.getInternalName().equals(SGGameState.GAME.getInternalName())
                || state.getInternalName().equals(SGGameState.DEATHMATCH.getInternalName())) {
            // Set the winner if the player size is 1.
            if(engine.getGamePlayers().size() == 1) {
                game.setWinner(engine.getGamePlayers().toArray(new UUID[] {})[0]);
                engine.getTimer().setCurrentStateIndex(SGGameState.END.getIndex());
            }
            // Modified mode ticking
            if(game.getModifiedModes().size() >= 1) {
                for(ModifiedMode mode : game.getModifiedModes()) {
                    mode.onTick(game);
                }
            }
            // SG Event ticking
            if(game.getCurrentEvent() != null) {
                game.getCurrentEvent().onTick(game);
            }
        }
    }

    @Override
    public void onStateEnd(GameEngine engine) {
        // Nothing
    }
}
