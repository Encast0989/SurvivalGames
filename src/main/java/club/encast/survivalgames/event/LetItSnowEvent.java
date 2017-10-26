package club.encast.survivalgames.event;

import club.encast.survivalgames.Game;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.Random;

public class LetItSnowEvent extends SGEvent implements Listener {

    private Random r = new Random();

    public LetItSnowEvent() {
        super("Let It Snow", "Let it snow everywhere!");
    }

    @Override
    public void onActivate(Game game) {
        World world = Bukkit.getServer().getWorld(game.getMapManager().getCurrentWorldName());
        for(Chunk chunk : world.getLoadedChunks()) {
            int cx = chunk.getX() << 4;
            int cz = chunk.getZ() << 4;
            for(int x = cx; x < cx + 16; x++) {
                for(int z = 0; z < cz + 16; z++) {
                    world.setBiome(x, z, Biome.COLD_TAIGA);
                }
            }
        }
        world.setStorm(true);
        world.setWeatherDuration((20 * 60) * 10); // Storm lasts for 10 minutes.
    }

    @Override
    public void onTick(Game game) {
        // Optimize in the future.

        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            World world = p.getWorld();
            // Replace with particles.
            world.spawnEntity(p.getLocation().add(0, 4, 0), EntityType.SNOWBALL);
            world.spawnEntity(p.getLocation().add(1, 4, 0), EntityType.SNOWBALL);
            world.spawnEntity(p.getLocation().add(-1, 4, 0), EntityType.SNOWBALL);
            world.spawnEntity(p.getLocation().add(0, 4, 1), EntityType.SNOWBALL);
            world.spawnEntity(p.getLocation().add(0, 4, -1), EntityType.SNOWBALL);
            world.spawnEntity(p.getLocation().add(1, 4, -1), EntityType.SNOWBALL);
            world.spawnEntity(p.getLocation().add(-1, 4, 1), EntityType.SNOWBALL);
        }
    }

    @EventHandler
    public void onProjectLand(ProjectileHitEvent e) {
        //TODO: Fix this code.
        if(e.getEntity() instanceof Snowball) {
            if((r.nextInt(10) + 1) % 2 == 0) {
                World world = e.getEntity().getWorld();
                BlockState state = world.getBlockAt(e.getEntity().getLocation()).getState();
                state.setType(Material.SNOW_BLOCK);
                state.update();
            }
        }
    }
}
