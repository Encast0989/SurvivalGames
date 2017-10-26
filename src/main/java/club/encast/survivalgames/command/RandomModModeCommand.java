package club.encast.survivalgames.command;

import club.encast.gameengine.GameState;
import club.encast.gameengine.command.GCommand;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.mode.ModifiedMode;
import club.encast.survivalgames.mode.ModifiedModeType;
import club.encast.survivalgames.state.SGGameState;
import org.bukkit.command.CommandSender;

import java.util.Random;

public class RandomModModeCommand extends GCommand {

    public RandomModModeCommand() {
        super("randommodmode");

        addAlias("randommodemodes");
        addPermission("sg.command.randommodemod");
        setDescription("Apply random Modified Modes to the current game.");
        setUsage("/randommodemode");
        setConsoleAllowed(false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Game game = Game.getInstance();
        GameState state = game.getTimer().getCurrentState();
        if(state.getInternalName().equals(SGGameState.LOBBY.getInternalName())
                || state.getInternalName().equals(SGGameState.STARTING.getInternalName())) {
            Random r = new Random();
            int modes = r.nextInt(ModifiedModeType.values().length) + 1;
            int addedModes = 0;
            for(int i = 0; i < modes; i++) {
                ModifiedMode mode = game.addModifiedMode(ModifiedModeType.values()[modes - 1]);
                if(mode != null) {
                    sender.sendMessage("§eAdded Modified Mode: " + mode.getName());
                    addedModes++;
                }
            }
            sender.sendMessage("Added " + addedModes + " " + (addedModes == 1 ? "mode" : "modes") + " to the game.");
        } else {
            sender.sendMessage("§cYou can only randomize Modified Modes before the game starts!");
        }
    }
}
