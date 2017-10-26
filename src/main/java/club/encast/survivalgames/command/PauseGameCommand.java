package club.encast.survivalgames.command;

import club.encast.gameengine.command.GCommand;
import club.encast.survivalgames.Game;
import org.bukkit.command.CommandSender;

public class PauseGameCommand extends GCommand {

    public PauseGameCommand() {
        super("pausegame");

        addAlias("pause");
        addPermission("sg.command.pausegame");
        setDescription("Pause the current game.");

        setConsoleAllowed(false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Game game = Game.getInstance();
        game.getTimer().setRunning(!game.getTimer().isRunning());
        if(!game.getTimer().isRunning()) {
            game.broadcastGameEvent("The game has been paused.");
        } else {
            game.broadcastGameEvent("The game has resumed.");
        }
    }
}
