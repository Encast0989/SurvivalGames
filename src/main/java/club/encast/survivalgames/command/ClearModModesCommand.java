package club.encast.survivalgames.command;

import club.encast.gameengine.command.GCommand;
import club.encast.survivalgames.Game;
import org.bukkit.command.CommandSender;

public class ClearModModesCommand extends GCommand {

    public ClearModModesCommand() {
        super("clearmodmodes");

        addAlias("cmm");
        addPermission("sg.command.clearmodmodes");
        setDescription("Clear all the Modified Modes for the current game.");
        setUsage("/clearmodmodes");
        setConsoleAllowed(false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Game.getInstance().getModifiedModes().clear();
        sender.sendMessage("§aCleared all the Modified Modes for this game.");
    }
}
