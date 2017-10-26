package club.encast.survivalgames;

import club.encast.gameengine.command.CommandHandler;
import club.encast.survivalgames.command.*;
import club.encast.survivalgames.event.LetItSnowEvent;
import club.encast.survivalgames.listener.*;
import club.encast.survivalgames.mode.AntiKnockbackMode;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class SurvivalGames extends JavaPlugin {

    private static SurvivalGames instance;
    private Game game;
    private CommandHandler commandHandler;

    @Override
    public void onEnable() {
        instance = this;
        game = new Game();
        commandHandler = new CommandHandler();

        Bukkit.getServer().getOnlinePlayers().forEach(o -> game.joinGame(o));

        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        game.getGameStates().clear();
        game.getGamePlayers().clear();
        game.getSpectators().clear();
        game.getSpawnLocations().clear();
        game.getSgPlayers().clear();
        game.getMapManager().purgeAllSGMaps();
    }

    public static SurvivalGames getInstance() {
        return instance;
    }

    public Game getGame() {
        return game;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new ChatListener(), this);
        pm.registerEvents(new ChestListener(), this);
        pm.registerEvents(new FoodConsumptionListener(), this);
        pm.registerEvents(new FoodLevelListener(), this);
        pm.registerEvents(new MiscListener(), this);
        pm.registerEvents(new PlayerConnectionListener(), this);
        pm.registerEvents(new PlayerDamageListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new AntiKnockbackMode(), this);
        pm.registerEvents(new LetItSnowEvent(), this);
    }

    private void registerCommands() {
        final String PREFIX = "sg";
        // Have to delay this so that the GameEngine plugin loads up.
        //TODO: Find a better way to do this.
        new BukkitRunnable() {
            @Override
            public void run() {
                commandHandler.registerCommand(PREFIX, new ClearModModesCommand());
                commandHandler.registerCommand(PREFIX, new FlushKickAllCommand());
                commandHandler.registerCommand(PREFIX, new GameStatsCommand());
                commandHandler.registerCommand(PREFIX, new GrimModeCommand());
                commandHandler.registerCommand(PREFIX, new ModifiedModeCommand());
                commandHandler.registerCommand(PREFIX, new NewMapCommand());
                commandHandler.registerCommand(PREFIX, new PauseGameCommand());
                commandHandler.registerCommand(PREFIX, new RandomModModeCommand());
                commandHandler.registerCommand(PREFIX, new SGCommandsCommand());
                commandHandler.registerCommand(PREFIX, new ViewPlayersCommand());
                commandHandler.registerCommand(PREFIX, new SpawnLootChestCommand());
            }
        }.runTaskLater(this, 40);
    }
}
