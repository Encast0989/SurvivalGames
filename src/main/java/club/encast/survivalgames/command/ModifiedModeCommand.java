package club.encast.survivalgames.command;

import club.encast.gameengine.command.GCommand;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.mode.ModifiedMode;
import club.encast.survivalgames.mode.ModifiedModeType;
import com.google.common.base.Joiner;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModifiedModeCommand extends GCommand {

    public ModifiedModeCommand() {
        super("modifiedmode");

        addPermission("sg.command.modifiedmode");
        addAlias("mm");
        setDescription("Modify the current game with the available modes.");
        setUsage("/" + getName() + " <add/remove> <ModifiedModeType>");
        setConsoleAllowed(false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length >= 2) {
            ModifiedModeType modeType = null;
            for(ModifiedModeType values : ModifiedModeType.values()) {
                if(values.name().equalsIgnoreCase(args[1])) {
                    modeType = values;
                }
            }
            if(modeType != null) {
                if(args[0].equalsIgnoreCase("add")) {
                    ModifiedMode mode = Game.getInstance().addModifiedMode(modeType);
                    if(mode != null) {
                        Game.getInstance().broadcast("§e" + p.getName() + " added the §c" + mode.getName() + " §eModified Mode!");
                    } else {
                        p.sendMessage("§cThat mode is already active.");
                    }
                } else if(args[0].equalsIgnoreCase("remove")) {
                    boolean removed = Game.getInstance().removeModifiedMode(modeType);
                    if(removed) {
                        // Might have to change this to get the modified mode's name from Game::getModifiedMode(ModifiedModeType type)
                        Game.getInstance().broadcastGameEvent("§eModified Mode §c" + modeType.getModifiedMode().getName() + " §ehas been removed.");
                    } else {
                        p.sendMessage("§cThat mode isn't currently active.");
                    }
                } else {
                    p.sendMessage(getUsageMessage());
                }
            } else {
                p.sendMessage("§cThat mode doesn't exist! §7Options: " + Joiner.on(", ").join(ModifiedModeType.values()));
            }
        } else {
            p.sendMessage(getUsageMessage() + " §7Options: " + Joiner.on(", ").join(ModifiedModeType.values()));
        }
    }
}
