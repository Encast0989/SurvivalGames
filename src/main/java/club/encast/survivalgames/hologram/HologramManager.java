package club.encast.survivalgames.hologram;

import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class HologramManager implements Listener {

    private List<Hologram> holograms;

    public HologramManager() {
        this.holograms = new ArrayList<>();
    }

    public List<Hologram> getHolograms() {
        return holograms;
    }

    public void addHologram(Hologram hologram) {
        holograms.add(hologram);
    }

    public void clearHolograms() {
        for(Hologram hologram : holograms) {
            for(HologramCategory category : hologram.getCategories()) {
                category.despawnAll();
            }
        }
        holograms.clear();
    }

    @EventHandler
    public void onHologramInteract(PlayerInteractAtEntityEvent e) {
        if(e.getRightClicked() instanceof ArmorStand) {
            for(Hologram hologram : holograms) {
                if(hologram.getCategories().size() > 1) {
                    HologramCategory displayed = hologram.getCategories().get(hologram.getCurrentlyDisplayed());
                    if(displayed != null) {
                        for(ArmorStand as : displayed.getHolograms()) {
                            if(as == e.getRightClicked()) {
                                hologram.displayNextCategory();
                            }
                        }
                    }
                }
            }
        }
    }
}
