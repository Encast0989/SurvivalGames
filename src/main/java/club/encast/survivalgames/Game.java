package club.encast.survivalgames;

import club.encast.gameengine.GameEngine;
import club.encast.survivalgames.event.SGEvent;
import club.encast.survivalgames.map.MapType;
import club.encast.survivalgames.mode.ModifiedMode;
import club.encast.survivalgames.mode.ModifiedModeType;
import club.encast.survivalgames.player.GamePlayer;
import club.encast.survivalgames.state.*;
import club.encast.survivalgames.util.MapWorkInfo;
import club.encast.survivalgames.util.SGUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.Executors;

public class Game extends GameEngine {

    private static Game instance;
    private MapType mapType;
    private List<Location> spawnLocations = new ArrayList<>();
    private List<ItemStack> randomItems = ChestItem.getItems();
    private List<Chest> chests = new ArrayList<>();
    private GameSettings gameSettings = new GameSettings();
    private MapManager mapManager = new MapManager();
    private List<ModifiedMode> modifiedModes = new ArrayList<>();
    private SGEvent currentEvent = null;

    private Map<UUID, GamePlayer> sgPlayers = new HashMap<>();
    private UUID winner;

    public Game() {
        super(SurvivalGames.getInstance(), "sg", "§6SG");
        instance = this;

        currentEvent = null;

        setMinPlayers(2);
        setMaxPlayers(24);

        getGameStates().add(new StateLobby());
        getGameStates().add(new StateStarting());
        getGameStates().add(new StateGame());
        getGameStates().add(new StateDeathmatch());
        getGameStates().add(new StateEnd());

        getTimer().setAlwaysTickState(new GameTime());

        newRandomMap();
    }

    public static Game getInstance() {
        return instance;
    }

    public MapType getMapType() {
        return mapType;
    }

    public void setMapType(MapType mapType, boolean teleportPlayers) {
        MapWorkInfo info = getMapManager().copyMap(mapType);
        this.mapType = mapType;
        if(info != null) {
            if(info.isCreated()) {
                loadLocations();
                if(teleportPlayers) {
                    Bukkit.getServer().getOnlinePlayers().forEach(o -> joinGame(o));
                }
                // We want to delete the previous map name, not so sure if it's good idea as it spams
                // the "could not delete session.lock" errors.
                // UPDATE: All maps will be deleted when the server is shutting down (or reloading).
                // UPDATE 2: Seems like it's a better idea to delete the previous map right away.
                if(info.getPreviousMapName() != null) getMapManager().deleteMap(info.getPreviousMapName());
            }
        }
    }

    public void newRandomMap() {
        Random r = new Random();
        setMapType(MapType.values()[r.nextInt(MapType.values().length)], true);
    }

    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }

    public List<ItemStack> getRandomItems() {
        return randomItems;
    }

    public List<Chest> getChests() {
        return chests;
    }

    public void clearChests() {
        chests.clear();
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public Map<UUID, GamePlayer> getSgPlayers() {
        return sgPlayers;
    }

    public UUID getWinner() {
        return winner;
    }

    public void setWinner(UUID winner) {
        this.winner = winner;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public List<ModifiedMode> getModifiedModes() {
        return modifiedModes;
    }

    public boolean hasModifiedMode(ModifiedModeType modeType) {
        for(ModifiedMode m : getModifiedModes()) {
            if(m.getName().equals(modeType.getModifiedMode().getName())) {
                return true;
            }
        }
        return false;
    }

    public ModifiedMode addModifiedMode(ModifiedModeType modeType) {
        if(!hasModifiedMode(modeType)) {
            ModifiedMode mode = modeType.getModifiedMode();
            modifiedModes.add(mode);
            return mode;
        }
        return null;
    }

    public boolean removeModifiedMode(ModifiedModeType modeType) {
        for(ModifiedMode mode : getModifiedModes()) {
            if(mode.getName().equals(modeType.getModifiedMode().getName())) {
                getModifiedModes().remove(mode);
                return true;
            }
        }
        return false;
    }

    public SGEvent getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(SGEvent currentEvent) {
        this.currentEvent = currentEvent;
    }

    public void joinGame(Player p) {
        if(getTimer().getCurrentStateIndex() == 0) {
            setGamePlayer(p.getUniqueId());
            getSgPlayers().put(p.getUniqueId(), new GamePlayer(p));
            broadcast("§e" + p.getName() + " joined (§b" + getGamePlayers().size() + "§e/§b" + getMaxPlayers() + "§e)");
            p.setGameMode(GameMode.SURVIVAL);
        } else {
            setSpectator(p);
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage("§7You are now a spectator.");
        }
        p.teleport(SGUtil.convertToBukkitLocation(getMapType().getMap().getLobbyLocation(), this));
        p.getInventory().setArmorContents(new ItemStack[] {
                new ItemStack(Material.AIR),
                new ItemStack(Material.AIR),
                new ItemStack(Material.AIR),
                new ItemStack(Material.AIR)
        });
        p.getInventory().clear();
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(20);
    }

    public void leaveGame(Player p) {
        getGamePlayers().remove(p.getUniqueId());
        getSpectators().remove(p.getUniqueId());
        getSgPlayers().remove(p.getUniqueId());
        broadcast("§6" + p.getName() + " has left.");
    }

    public List<Player> getSortedGamePlayers() {
        List<Player> players = new ArrayList<>();
        for(UUID uuid : getGamePlayers()) {
            Player p = Bukkit.getServer().getPlayer(uuid);
            if(p != null) {
                players.add(p);
            }
        }
        return players;
    }

    public void randomizeChestLoot(Chest chest) {
        Executors.newSingleThreadExecutor().submit(() -> {
            Random r = new Random();
            List<ItemStack> items = new ArrayList<>();
            int maxItems = 2 + r.nextInt(4);
            chests.add(chest);
            chest.getInventory().clear();
            for(int i = 0; i < maxItems; i++) {
                ItemStack item = randomItems.get(r.nextInt(randomItems.size()));
                ItemMeta meta = item.getItemMeta();
                ChestItem ci = ChestItem.getByItemStack(item);
                if(ci != null) {
                    if (ci.isRenamable()) {
                        meta.setDisplayName("§f" + ChestItem.getPrefixes()[r.nextInt(ChestItem.getPrefixes().length)] + " " + ci.getName());
                    } else {
                        meta.setDisplayName("§f" + ci.getName());
                    }
                } else {
                    meta.setDisplayName("Unknown");
                }
                item.setItemMeta(meta);
                items.add(item);
            }
            for(ItemStack item : items) {
                int slot = r.nextInt(chest.getInventory().getSize());
                //TODO: Factor in item percent when randomizing.
                chest.getInventory().setItem(slot, item);
            }
        });
        chest.update(true);
    }

    public void loadLocations() {
        spawnLocations.clear();
        World world = Bukkit.getServer().getWorld(getMapManager().getCurrentWorldName());
        Location midLocation = SGUtil.convertToBukkitLocation(mapType.getMap().getMiddleLocation(), this);
        Chunk midChunk = world.getChunkAt(midLocation);
        // This will load a 3x3 chunk area around the middle location so that spawn points can be loaded.
        world.loadChunk(midChunk.getX(), midChunk.getZ(), true);
        world.loadChunk(midChunk.getX() + 1, midChunk.getZ(), true);
        world.loadChunk(midChunk.getX(), midChunk.getZ() + 1, true);
        world.loadChunk(midChunk.getX() - 1, midChunk.getZ(), true);
        world.loadChunk(midChunk.getX(), midChunk.getZ() - 1, true);
        world.loadChunk(midChunk.getX() + 1, midChunk.getZ() - 1, true);
        world.loadChunk(midChunk.getX() - 1, midChunk.getZ() + 1, true);
        world.loadChunk(midChunk.getX() + 1, midChunk.getZ() + 1, true);
        world.loadChunk(midChunk.getX() - 1, midChunk.getZ() - 1, true);

        for (Chunk chunk : world.getLoadedChunks()) {
            // Converts the x and y of the chunk into block x and y
            int chunkX = chunk.getX() << 4;
            int chunkZ = chunk.getZ() << 4;
            // Getting all the blocks in the chunk
            for (int x = chunkX; x < chunkX + 16; x++) {
                for (int z = chunkZ; z < chunkZ + 16; z++) {
                    for (int y = 0; y < 256; y++) {
                        Block block = world.getBlockAt(x, y, z);
                        if (block.getState() instanceof Sign) {
                            Sign sign = (Sign) block.getState();
                            if (sign.getLine(0).equalsIgnoreCase("spawn")) {
                                spawnLocations.add(sign.getLocation().add(0.5, 0, 0.5));
                                sign.setType(Material.AIR);
                                sign.update(true);
                            }
                        }
                    }
                }
            }
        }
    }

    public Map<UUID, GamePlayer> getTopKills() {
        List<Map.Entry<UUID, GamePlayer>> list = new LinkedList<>(getSgPlayers().entrySet());
        list.sort(new Comparator<Map.Entry<UUID, GamePlayer>>() {
            @Override
            public int compare(Map.Entry<UUID, GamePlayer> o1, Map.Entry<UUID, GamePlayer> o2) {
                if(o1.getValue().getKills() > o2.getValue().getKills()) {
                    return -1; // This is done to reverse the order of the list (optimization purposes).
                }
                return 1;
            }
        });
        Map<UUID, GamePlayer> topKills = new LinkedHashMap<>();
        for(Map.Entry<UUID, GamePlayer> entry : list) {
            topKills.put(entry.getKey(), entry.getValue());
        }
        return topKills;
    }

    public void flushRestart(MapType mapType) {
        broadcast("§c[GAME] Generating new map, expect lag for ~10 seconds. " +
                "All players will be teleported once the map has generated.");
        getTimer().setRunning(false);
        getGamePlayers().clear();
        getSpectators().clear();
        getGameStates().remove(0);
        getGameStates().add(0, new StateLobby());
        getTimer().resetStates();

        getSgPlayers().forEach((k, v) -> v.endUpdate());
        getSgPlayers().clear();
        clearChests();
        getModifiedModes().clear();
        getGameSettings().setPvp(false);
        setWinner(null);
        if(mapType != null) {
            setMapType(mapType, true);
        } else {
            newRandomMap();
        }
        getTimer().setRunning(true);
    }

    public void broadcastGameEvent(String message) {
        broadcast("§c[GAME] " + ChatColor.stripColor(message));
    }
}
