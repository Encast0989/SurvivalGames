package club.encast.survivalgames.command;

import club.encast.gameengine.command.GCommand;
import club.encast.gameengine.util.ItemFactory;
import club.encast.survivalgames.Game;
import club.encast.survivalgames.util.PartialLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Random;

public class SpawnLootChestCommand extends GCommand {

    public SpawnLootChestCommand() {
        super("spawnlootchest");

        addPermission("sg.command.spawnlootchest");
        setDescription("Spawn a loot chest at mid.");
        setUsage("/spawnlootchest");
        setConsoleAllowed(false);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        PartialLocation midLoc = Game.getInstance().getMapType().getMap().getMiddleLocation();
        Location spawnChest = new Location(Bukkit.getServer().getWorld(
                Game.getInstance().getMapManager().getCurrentWorldName()),
                midLoc.getX(),
                midLoc.getY(),
                midLoc.getZ())
                .add(0.5, 3, 0.5);

        BlockState chestState = spawnChest.getBlock().getState();
        if(spawnChest instanceof Chest && Game.getInstance().getChests().contains((Chest) chestState)) return;

        chestState.setType(Material.CHEST);
        chestState.update(true);
        Chest chest = (Chest) chestState;
        Material[] possibleSpecialItems = {Material.DIAMOND_SWORD,
                Material.DIAMOND_HELMET,
                Material.DIAMOND_CHESTPLATE,
                Material.DIAMOND_LEGGINGS,
                Material.DIAMOND_LEGGINGS};
        Random r = new Random();
        Game.getInstance().randomizeChestLoot(chest);
        chest.getInventory().addItem(new ItemFactory(Material.DIAMOND)
                .setAmount(2)
                .setDisplayName("§6Encast's Diamond")
                .setLore(Arrays.asList("§7Go make something special with this...", "§7or, you can make a hoe :("))
                .create());
        chest.getInventory().setItem(13, new ItemStack(possibleSpecialItems[r.nextInt(possibleSpecialItems.length)]));
        chest.update();

        BlockState aboveChestState = spawnChest.clone().add(0.5, 1, 0.5).getBlock().getState();
        aboveChestState.setType(Material.AIR);
        aboveChestState.update(true);

        Game.getInstance().broadcast("§6§lA loot chest has spawned at "
                + spawnChest.getX() + ", " + spawnChest.getY() + ", " + spawnChest.getZ());
    }
}
