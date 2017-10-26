package club.encast.survivalgames.command;

import club.encast.gameengine.command.GCommand;
import club.encast.survivalgames.Game;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ViewPlayersCommand extends GCommand {

    public ViewPlayersCommand() {
        super("viewplayers");

        addPermission("sg.command.viewplayers");
        setDescription("View all players in the game.");
        setUsage("/viewplayers");
        setConsoleAllowed(false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        // Optimize this in the future, this is a pretty heavy task, or let's hope no one
        // spams this command.
        StringBuilder gamePlayers = new StringBuilder("§cGame Players: §b");
        StringBuilder spectators = new StringBuilder("§cSpectators: §b");

        // Game players
        int count = 0;
        for(UUID uuid : Game.getInstance().getGamePlayers()) {
            Player p = Bukkit.getServer().getPlayer(uuid);
            if(p != null) {
                gamePlayers.append("§b").append(p.getName());
            }
            if(count < Game.getInstance().getGamePlayers().size() - 1) {
                gamePlayers.append(", ");
            }
            count++;
        }
        // Spectators
        count = 0; // Setting count to 0 so that it can be used for the spectators.
        for(UUID uuid : Game.getInstance().getSpectators()) {
            Player p = Bukkit.getServer().getPlayer(uuid);
            if(p != null) {
                spectators.append("§b").append(p.getName());
            }
            if(count < Game.getInstance().getSpectators().size() - 1) {
                spectators.append(", ");
            }
            count++;
        }
        sender.sendMessage(gamePlayers.toString());
        sender.sendMessage(spectators.toString());
    }
}
