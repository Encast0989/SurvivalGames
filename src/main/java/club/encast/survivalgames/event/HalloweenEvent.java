package club.encast.survivalgames.event;

import club.encast.gameengine.util.ItemFactory;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.SurvivalGames;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class HalloweenEvent extends SGEvent {

    //TODO: Cancel players being able to click on the pumpkins.
    private Random r = new Random();
    private boolean pumpkingActivated = false; // Boolean for if player helmets have been replaced by pumpkins.

    public HalloweenEvent() {
        super("Halloween Event", "Spooky for the spookiest time of the year. SPOOKY SCARY SKELETONS!");
    }

    @Override
    public void onActivate(Game game) {
        int stormTime = 20 * 60 * 10;
        World world = Bukkit.getServer().getWorld(game.getMapManager().getCurrentWorldName());
        world.setTime(18000);
        world.setDifficulty(Difficulty.HARD);
        world.setGameRuleValue("doMobSpawning", "true");
        world.setStorm(true);
        world.setWeatherDuration(stormTime); // Multiplying ticks by 60 (for 1 minute) then by 10 (for 10 minutes).
        world.setThundering(true);
        world.setThunderDuration(stormTime);
    }

    @Override
    public void onTick(Game game) {
        if(r.nextInt(40) == 31) {
            if(!pumpkingActivated) {
                for(UUID uuid : game.getGamePlayers()) {
                    Player p = Bukkit.getServer().getPlayer(uuid);
                    if(p != null) {
                        ItemStack currentHelmet = p.getInventory().getHelmet();
                        p.getInventory().setHelmet(new ItemFactory(Material.PUMPKIN)
                                .setDisplayName("§6Spooky Pumpkin")
                                .setLore(Arrays.asList(" ", "§7SPOOKY"))
                                .create());
                        p.playSound(p.getLocation(), Sound.GHAST_SCREAM, 5, 1);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5, 1, true, false));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3, 1, true, false));
                        p.sendMessage("§8BE SPOOKED!");
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                p.getInventory().setHelmet(currentHelmet);
                            }
                        }.runTaskLater(SurvivalGames.getInstance(), 50);
                    }
                }
                pumpkingActivated = true;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        pumpkingActivated = false;
                    }
                }.runTaskLater(SurvivalGames.getInstance(), 50);
            }
        }
    }
}
