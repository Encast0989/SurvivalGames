package club.encast.survivalgames;

import club.encast.survivalgames.map.MapType;
import club.encast.survivalgames.util.MapWorkInfo;
import com.google.common.base.Joiner;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

public class MapManager {

    private List<String> worldNames = new ArrayList<>();
    private String currentWorldName;
    private File mapsFolder;
    private String root;

    public MapManager() {
        File dataFolder = SurvivalGames.getInstance().getDataFolder();
        this.mapsFolder = new File(dataFolder.getAbsolutePath() + "/maps");

        String[] split = Arrays.copyOf(dataFolder.getAbsolutePath().split("/"), dataFolder.getAbsolutePath().split("/").length - 2);
        this.root = Joiner.on("/").join(split);
        if(!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        if(!mapsFolder.exists()) {
            mapsFolder.mkdir();
        }
    }

    public List<String> getWorldNames() {
        return worldNames;
    }

    public String getCurrentWorldName() {
        return currentWorldName;
    }

    public MapWorkInfo copyMap(MapType type) {
        File mapLocation = new File(mapsFolder.getAbsolutePath() + "/" + type.getFileName());
        String newWorldName = generateNewWorldName();
        File world = new File(root + "/" + newWorldName);
        if(mapLocation.exists()) {
            try {
                try {
                    FileUtils.copyDirectory(mapLocation, world);
                    WorldCreator creator = new WorldCreator(newWorldName)
                            .generatorSettings("3;minecraft:air;127")
                            .generateStructures(false)
                            .type(WorldType.FLAT);
                    World bukkitWorld = Bukkit.getServer().createWorld(creator);
                    bukkitWorld.setTime(0);
                    bukkitWorld.setDifficulty(Difficulty.NORMAL);
                    bukkitWorld.setGameRuleValue("doMobSpawning", "false");
                    bukkitWorld.setGameRuleValue("doMobLoot", "false");
                    bukkitWorld.setGameRuleValue("doDaylightCycle", "false");
                    bukkitWorld.setGameRuleValue("doFireTick", "false");
                    bukkitWorld.setSpawnLocation((int) type.getMap().getLobbyLocation().getX(),
                            (int) type.getMap().getLobbyLocation().getY(),
                            (int) type.getMap().getLobbyLocation().getZ());
                    bukkitWorld.setSpawnFlags(false, false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MapWorkInfo info = new MapWorkInfo(true, currentWorldName, newWorldName);
                currentWorldName = newWorldName;
                worldNames.add(newWorldName);
                return info;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Map file " + type.getFileName() + " does not exist!");
        }
        return null;
    }

    public boolean deleteMap(String fileName) {
        File file = Bukkit.getServer().getWorld(fileName).getWorldFolder();
        Bukkit.getServer().unloadWorld(fileName, true);
        if(file.exists()) {
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    FileUtils.deleteDirectory(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return true;
        }
        return false;
    }

    public void purgeAllSGMaps() {
        if(!Game.getInstance().getTimer().isRunning()) {
            File root = new File(this.root);
            if(root.isDirectory()) {
                if(root.listFiles() != null) {
                    for(File file : root.listFiles()) {
                        if(worldNames.contains(file.getName())) {
                            if(file.isDirectory()) {
                                deleteMap(file.getName());
                            }
                        }
                    }
                }
            }
        }
    }


    private String generateNewWorldName() {
        String worldName;
        do {
            worldName = "sgmap" + (worldNames.size() + 1);
        } while(worldNames.contains(worldName));
        return worldName;
    }
}
