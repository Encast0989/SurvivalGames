package club.encast.survivalgames.command;

import club.encast.gameengine.command.GCommand;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.util.SGUtil;
import org.bukkit.command.CommandSender;

public class GameStatsCommand extends GCommand {

    public GameStatsCommand() {
        super("gamestats");

        addPermission("sg.command.gamestats");
        setDescription("View SG statistics for this session.");
        setUsage("/gamestats");
        setConsoleAllowed(false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(SGUtil.centerMessage("§a§lGame Stats §c(Session)"));
        sender.sendMessage(SGUtil.centerMessage("§aTotal games this session: §6" + Game.getInstance().getMapManager().getWorldNames().size()));
        sender.sendMessage(SGUtil.centerMessage("§aTotal players this session: §6N/A"));
        sender.sendMessage(SGUtil.centerMessage("§a§lGame Stats §c(Current Game)"));
        sender.sendMessage(SGUtil.centerMessage("§aChests opened this game: §6" + Game.getInstance().getChests().size()));
        sender.sendMessage(SGUtil.centerMessage("§aNumber of chest items in play: " + Game.getInstance().getRandomItems()));
    }
}
