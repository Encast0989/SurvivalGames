package club.encast.survivalgames.mode;

import club.encast.gameengine.util.ItemFactory;
import club.encast.survivalgames.Game;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public class KnockbackMode extends ModifiedMode {

    public KnockbackMode() {
        super("Knockback");
    }

    @Override
    public void onActivate(Game game) {
        for(UUID uuid : game.getGamePlayers()) {
            Player p = Bukkit.getServer().getPlayer(uuid);
            if(p != null) {
                ItemStack item = new ItemFactory(Material.STICK)
                        .setDisplayName("§aSOME STICK")
                        .setLore(Arrays.asList(" ", "§7I don't know what to put here help"))
                        .create();
                ItemMeta meta = item.getItemMeta();
                meta.addEnchant(Enchantment.KNOCKBACK, 10, true);
                item.setItemMeta(meta);
                p.getInventory().addItem(item);
            }
        }
    }

    @Override
    public void onTick(Game game) {

    }
}
