package club.encast.survivalgames.hologram;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class HologramCategory {

    private List<ArmorStand> holograms;
    private String name;
    private Location location;

    public HologramCategory(String name, Location location) {
        this.holograms = new ArrayList<>();
        this.name = name;
        this.location = location;
    }

    public List<ArmorStand> getHolograms() {
        return holograms;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void addHologram(String text) {
        ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setBasePlate(false);
        as.setCustomName(text);
        as.setCustomNameVisible(true);
        holograms.add(as);
        reloadHolograms();
    }

    public void reloadHolograms(double... initialSpacing) {
        double currentSpacing = (initialSpacing != null ? initialSpacing[0] : 0);
        List<ArmorStand> newHologramList = new ArrayList<>();
        for(ArmorStand as : holograms) {
            if(as.isValid()) {
                as.remove();
            }
            ArmorStand newHologram = (ArmorStand) location.getWorld().spawnEntity(location.add(0, currentSpacing, 0), EntityType.ARMOR_STAND);
            newHologram.setVisible(false);
            newHologram.setBasePlate(false);
            newHologram.setCustomName(as.getCustomName());
            newHologram.setCustomNameVisible(true);
            newHologramList.add(newHologram);
            currentSpacing += 0.5;
        }
        holograms.clear();
        holograms = newHologramList;
    }

    public void hideHolograms() {
        for(ArmorStand as : holograms) {
            if(as.isValid()) as.remove();
        }
    }

    public void showHolograms() {
        reloadHolograms();
    }

    public void despawnAll() {
        for(ArmorStand as : holograms) {
            if(as.isValid()) {
                as.remove();
            }
        }
        holograms.clear();
    }
}
