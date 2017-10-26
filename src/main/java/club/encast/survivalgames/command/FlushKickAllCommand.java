package club.encast.survivalgames.command;

import club.encast.gameengine.command.GCommand;
import org.bukkit.command.CommandSender;

public class FlushKickAllCommand extends GCommand {

    public FlushKickAllCommand() {
        super("flushkickall");

        addPermission("sg.command.flushkickall");
        setDescription("Flush the cache and kick all players.");
        setConsoleAllowed(false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("§cThis command is unavailable.");
    }
}
