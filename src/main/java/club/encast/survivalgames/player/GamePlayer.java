package club.encast.survivalgames.player;

import club.encast.gameengine.GScoreboard;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.SurvivalGames;
import club.encast.survivalgames.state.StateEnd;
import club.encast.survivalgames.state.StateLobby;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class GamePlayer extends BukkitRunnable {

    private UUID uuid;
    private String playerName;
    private int kills;
    private int bowShots;
    private int bowHits;
    private GScoreboard scoreboard;
    private boolean endUpdate;

    public GamePlayer(Player p) {
        this.uuid = p.getUniqueId();
        this.playerName = p.getName();
        this.kills = 0;
        this.bowShots = 0;
        this.bowHits = 0;

        this.scoreboard = new GScoreboard(Game.getInstance(), "Survival Games", true);
        this.scoreboard.addLine(ChatColor.RED.toString());
        this.scoreboard.addLine("Current State");
        this.scoreboard.addLine("State : Time");
        this.scoreboard.addLine(ChatColor.GREEN.toString());
        this.scoreboard.addLine("Players Left: N/A");
        this.scoreboard.addLine("Watching: N/A");
        this.scoreboard.addLine("Kills: N/A");
        this.scoreboard.addLine(ChatColor.BLUE.toString());
        this.scoreboard.addLine("Map: §a" + Game.getInstance().getMapType().getMap().getMapName());
        this.scoreboard.addLine(ChatColor.YELLOW.toString());
        this.scoreboard.addLine("Mod Modes: N/A");
        this.scoreboard.addLine(ChatColor.GOLD.toString());
        this.scoreboard.addLine("§eSG Beta Test");

        this.scoreboard.setScoreboard(p);

        this.endUpdate = false;

        this.runTaskTimer(SurvivalGames.getInstance(), 0, 10);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getBowShots() {
        return bowShots;
    }

    public void setBowShots(int bowShots) {
        this.bowShots = bowShots;
    }

    public int getBowHits() {
        return bowHits;
    }

    public void setBowHits(int bowHits) {
        this.bowHits = bowHits;
    }

    public GScoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(GScoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public boolean isEndUpdate() {
        return endUpdate;
    }

    public void endUpdate() {
        this.endUpdate = true;
    }

    @Override
    public void run() {
        if(endUpdate) {
            this.cancel();
            return;
        }
        // Scoreboard state update
        Game game = Game.getInstance();
        if(game.getTimer().getCurrentState() instanceof StateLobby) {
            StateLobby lobby = (StateLobby) game.getTimer().getCurrentState();
            if(lobby.getTimeUntilStart() == lobby.getMaxStartingTime()) {
                getScoreboard().setLine(3, "§aWaiting...");
            } else {
                getScoreboard().setLine(3, "Starting in §a" + lobby.getTimeUntilStart() + "s");
            }
        } else if(game.getTimer().getCurrentState() instanceof StateEnd) {
            getScoreboard().setLine(3, "§aFinished!");
        } else {
            long time = Game.getInstance().getTimer().getRemainingTime();
            getScoreboard().setLine(3,Game.getInstance().getTimer().getCurrentState().getPublicName()
                    + ": §b" + ((time / 60) > 9 ? time / 60 : "0" +  (time / 60)) // Minutes
                    + ":" + ((time % 60) > 9 ? time % 60 : "0" + (time % 60))); // Seconds
        }

        // Scoreboard players alive/kills update
        getScoreboard().setLine(5, "Players Alive: §a" + game.getGamePlayers().size());
        getScoreboard().setLine(6, "Watching: §a" + game.getSpectators().size());
        getScoreboard().setLine(7, "Kills: §a" + kills);

        // Modified Modes update
        getScoreboard().setLine(11, "Mod Modes: §b" + game.getModifiedModes().size() + " Active");

    }
}
