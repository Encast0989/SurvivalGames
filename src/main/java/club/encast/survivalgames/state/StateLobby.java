package club.encast.survivalgames.state;

import club.encast.gameengine.GameEngine;
import club.encast.gameengine.GameState;
import club.encast.survivalgames.util.SGUtil;

import java.util.concurrent.TimeUnit;

public class StateLobby extends GameState {

    private final int MAX_STARTING_TIME = 10;
    private int time = MAX_STARTING_TIME;

    public StateLobby() {
        super(SGGameState.LOBBY.getInternalName(), "Lobby", 0, TimeUnit.SECONDS);
        setInfinite(true);
    }

    @Override
    public void onStateBegin(GameEngine engine) {
        // Nothing
    }

    @Override
    public void onStateTick(GameEngine engine) {
        // Announces the time until start depending on if the min player requirement is met.
        // The countdown will be cancelled if a the minimum requirement isn't met and the countdown
        // has started.
        if(engine.getGamePlayers().size() >= engine.getMinPlayers()) {
            if(time == MAX_STARTING_TIME) {
                engine.broadcast("§6The game will be started in " + SGUtil.formatTime(time, TimeUnit.SECONDS) + "!");
            } else if(time == 0) {
                time = MAX_STARTING_TIME;
                engine.getTimer().nextState();
                return;
            }
            time--;
        } else {
            if(time != MAX_STARTING_TIME) {
                time = MAX_STARTING_TIME;
                engine.broadcast("§cNot enough players, countdown cancelled.");
            }
        }
    }

    @Override
    public void onStateEnd(GameEngine engine) {
        // Nothing
    }

    public int getTimeUntilStart() {
        return time;
    }

    public int getMaxStartingTime() {
        return MAX_STARTING_TIME;
    }
}
