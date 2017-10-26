package club.encast.survivalgames.command;

import club.encast.gameengine.command.GCommand;
import club.encast.survivalgames.SurvivalGames;
import org.bukkit.command.CommandSender;

public class SGCommandsCommand extends GCommand {

    public SGCommandsCommand() {
        super("sgcommands");

        addPermission("sg.command.sgcommands");
        setDescription("View all SG commands.");
        setUsage("/sgcommands");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        for(GCommand command : SurvivalGames.getInstance().getCommandHandler().getCommands()) {
            sender.sendMessage("§e" + command.getName() + " - " + command.getUsage() + " - " + command.getDescription());
        }
    }
}
