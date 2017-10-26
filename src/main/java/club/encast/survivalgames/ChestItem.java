package club.encast.survivalgames;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public enum ChestItem {

    WOOD_AXE("Wood Axe", new ItemStack(Material.WOOD_AXE), 20),
    WOOD_SWORD("Wood Sword", new ItemStack(Material.WOOD_SWORD), 10),
    STONE_AXE("Stone Axe", new ItemStack(Material.STONE_AXE), 10),
    STONE_SWORD("Stone Sword", new ItemStack(Material.STONE_SWORD), 5),
    APPLE("Apple", new ItemStack(Material.APPLE, 5), 10, false),
    APPLE_OF_COMMAND("§dApple of Command", new ItemStack(Material.APPLE, 2), 10, false),
    STEAK("Steak", new ItemStack(Material.COOKED_BEEF), 3, false),
    BOW("Bow", new ItemStack(Material.BOW), 5),
    ARROWS("Arrow", new ItemStack(Material.ARROW, 16), 10, false),
    LEATHER_HELMET("Leather Helmet", new ItemStack(Material.LEATHER_HELMET), 15),
    LEATHER_CHESTPLATE("Leather Chestplate", new ItemStack(Material.LEATHER_CHESTPLATE), 15),
    LEATHER_LEGGINGS("Leather Leggings", new ItemStack(Material.LEATHER_LEGGINGS), 15),
    LEATHER_BOOTS("Leather Boots", new ItemStack(Material.LEATHER_BOOTS), 15),
    CHAIN_HELMET("Chain Helmet", new ItemStack(Material.CHAINMAIL_HELMET), 15),
    CHAIN_CHESTPLATE("Chain Chestplate", new ItemStack(Material.CHAINMAIL_CHESTPLATE), 15),
    CHAIN_LEGGINGS("Chain Leggings", new ItemStack(Material.CHAINMAIL_LEGGINGS), 15),
    CHAIN_BOOTS("Chain Boots", new ItemStack(Material.CHAINMAIL_BOOTS), 5),
    IRON_HELMET("Iron Helmet", new ItemStack(Material.IRON_HELMET), 5),
    IRON_CHESTPLATE("Iron Chestplate", new ItemStack(Material.IRON_CHESTPLATE), 5),
    IRON_BOOTS("Iron Boots", new ItemStack(Material.IRON_BOOTS), 5),
    FISHING_ROD("Fishing Rod", new ItemStack(Material.FISHING_ROD), 15);

    private String name;
    private ItemStack item;
    private double percentChance;
    private boolean renamable;
    private static String[] prefixes = new String[] {"Fun", "Fast", "Blackbeard's", "Encast's", "Cute", "Raw", "Broken",
            "Terrifying", "Dogegogo's", "TERRIFYINGLY STICKY", "Magically Magical", "Blue"};

    ChestItem(String name, ItemStack item, double percentChance) {
        this.name = name;
        this.item = item;
        this.percentChance = percentChance;
        this.renamable = true;
    }

    ChestItem(String name, ItemStack item, double percentChance, boolean renamable) {
        this.name = name;
        this.item = item;
        this.percentChance = percentChance;
        this.renamable = renamable;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return item;
    }

    public double getPercentChance() {
        return percentChance;
    }

    public boolean isRenamable() {
        return renamable;
    }

    public static ChestItem getByItemStack(ItemStack item) {
        if(item != null) {
            for(ChestItem ci : values()) {
                if(ci.getItem().getType() == item.getType() && ci.getItem().getAmount() == item.getAmount()) {
                    return ci;
                }
            }
        }
        return null;
    }

    public static List<ItemStack> getItems() {
        List<ItemStack> items = new ArrayList<>();
        for(ChestItem item : values()) {
            items.add(item.getItem());
        }
        return items;
    }

    public static String[] getPrefixes() {
        return prefixes;
    }
}
