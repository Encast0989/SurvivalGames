package club.encast.survivalgames.listener;

import club.encast.survivalgames.ChestItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FoodConsumptionListener implements Listener {

    @EventHandler
    public void onFoodComsume(PlayerItemConsumeEvent e) {
        if(e.getItem().hasItemMeta() && e.getItem().getItemMeta().hasDisplayName()) {
            if(e.getItem().getItemMeta().getDisplayName().equals(ChestItem.APPLE_OF_COMMAND.getName())) {
                Player p = e.getPlayer();
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5, 0, true, false));
                p.sendMessage("§fThe " + ChestItem.APPLE_OF_COMMAND.getName() + " §fgave you regeneration for 5 seconds!");
            }
        }
    }
}
