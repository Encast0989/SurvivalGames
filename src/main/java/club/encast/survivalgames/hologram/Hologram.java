package club.encast.survivalgames.hologram;

import com.google.common.base.Joiner;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Hologram {

    private List<HologramCategory> categories;
    private Location location;
    private boolean displayCategories;
    private int currentlyDisplayed;
    private ArmorStand categorySign;

    public Hologram(Location location, boolean displayCategories, String name) {
        this.categories = new ArrayList<>();
        this.location = location;
        this.displayCategories = displayCategories;

        addCategory(name);
        this.currentlyDisplayed = 0;
        this.categorySign = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
    }

    public List<HologramCategory> getCategories() {
        return categories;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isDisplayCategories() {
        return displayCategories;
    }

    public int getCurrentlyDisplayed() {
        return currentlyDisplayed;
    }

    public HologramCategory getCategory(String name) {
        for(HologramCategory category : categories) {
            if(category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }


    public HologramCategory addCategory(String name) {
        HologramCategory category = new HologramCategory(name, location);
        categories.add(category);
        return category;
    }

    public void displayNextCategory() {
        if((currentlyDisplayed + 1) < categories.size()) {
            currentlyDisplayed++;
            reloadAllCategories();
        }
    }

    public void reloadAllCategories() {
        categories.forEach(HologramCategory::hideHolograms);
        HologramCategory h = categories.get(currentlyDisplayed);
        if(h != null) h.showHolograms();
        if(categorySign != null) {
            if(displayCategories) {
                StringBuilder categoryDisplay = new StringBuilder("§b");
                categories.forEach(c -> categoryDisplay.append(c.getName()).append(" "));
                String[] arrayCategory = categoryDisplay.toString().split(" ");
                arrayCategory[currentlyDisplayed] = "§6§l" + arrayCategory[currentlyDisplayed];

                h.reloadHolograms(0.5);

                categorySign.remove();
                categorySign = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                categorySign.setVisible(false);
                categorySign.setBasePlate(false);
                categorySign.setCustomName(Joiner.on(" ").join(arrayCategory));
                categorySign.setCustomNameVisible(true);
            }
        }
    }
}
