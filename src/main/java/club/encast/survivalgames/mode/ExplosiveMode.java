package club.encast.survivalgames.mode;

import club.encast.gameengine.util.ItemFactory;
import club.encast.survivalgames.Game;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public class ExplosiveMode extends ModifiedMode {

    public ExplosiveMode() {
        super("Explosive");
    }

    @Override
    public void onActivate(Game game) {
        for(UUID uuid : game.getGamePlayers()) {
            Player p = Bukkit.getServer().getPlayer(uuid);
            if(p != null) {
                p.getInventory().addItem(new ItemFactory(Material.TNT)
                        .setDisplayName("§cTNT")
                        .setLore(Arrays.asList(" ", "§7Some say TNT is overpowered."))
                        .create());
            }
        }
    }

    @Override
    public void onTick(Game game) {
        // Nothing
    }
}
