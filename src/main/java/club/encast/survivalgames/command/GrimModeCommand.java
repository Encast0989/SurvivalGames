package club.encast.survivalgames.command;

import club.encast.gameengine.GameState;
import club.encast.gameengine.command.GCommand;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.event.HalloweenEvent;
import club.encast.survivalgames.state.SGGameState;
import org.bukkit.command.CommandSender;

import java.util.Calendar;

public class GrimModeCommand extends GCommand {

    public GrimModeCommand() {
        super("grimmode");

        addPermission("sg.command.grimmode");
        addAlias("grim");
        setDescription("Makes the game spooky, just in time for Halloween!");
        setUsage("/grimmode [-i]");
        setConsoleAllowed(false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Calendar c = Calendar.getInstance();
        boolean runGrimMode = false;
        if(c.get(Calendar.MONTH) == Calendar.OCTOBER) {
            if(c.get(Calendar.DAY_OF_MONTH) == 31) {
                sender.sendMessage("§8Setting game to Grim Mode! §6Happy Halloween!");
                runGrimMode = true;
                return;
            }
        }
        if(args.length >= 1 && args[0].equalsIgnoreCase("-i") && !runGrimMode) {
            runGrimMode = true;
        }
        if(runGrimMode) {
            Game game = Game.getInstance();
            GameState state = game.getTimer().getCurrentState();
            if(state.getInternalName().equals(SGGameState.LOBBY.getInternalName())
                    || state.getInternalName().equals(SGGameState.STARTING.getInternalName())) {
                if(game.getCurrentEvent() == null || !(game.getCurrentEvent() instanceof HalloweenEvent)) {
                    game.setCurrentEvent(new HalloweenEvent()); // Maybe make this an enumerator?
                    sender.sendMessage("§aHalloween Event has been set.");
                } else {
                    sender.sendMessage("§cThe current event is already set to Halloween!");
                }
            } else {
                sender.sendMessage("§cYou can't set an event after the game has started!");
            }
        } else {
            sender.sendMessage("§8This command can only be used on §6Halloween §c(October 31st)§8!");
            sender.sendMessage("§cYou can also optionally add the §b-i §cparameter to ignore the date.");
        }
    }
}
