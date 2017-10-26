package club.encast.survivalgames.command;

import club.encast.gameengine.command.GCommand;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.map.MapType;
import com.google.common.base.Joiner;
import org.bukkit.command.CommandSender;

public class NewMapCommand extends GCommand {

    public NewMapCommand() {
        super("newmap");

        addPermission("sg.command.newmap");
        setDescription("Restart the game with the specified map.");
        setConsoleAllowed(false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length >= 1) {
            try {
                MapType map = MapType.valueOf(args[0].toUpperCase());
                sender.sendMessage("§aRestarting game with map " + map.getMap().getMapName() + "!");
                Game.getInstance().flushRestart(map);
            } catch (Exception e) {
                sender.sendMessage("§cThat map doesn't exist!");
            }
        } else {
            sender.sendMessage(getUsageMessage() + " §7Options: " + Joiner.on(", ").join(MapType.values()));
        }
    }
}
