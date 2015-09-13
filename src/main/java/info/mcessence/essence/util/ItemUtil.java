package info.mcessence.essence.util;

import info.mcessence.essence.Essence;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class ItemUtil {

    public static ItemStack air = new ItemStack(Material.AIR);

    /**
     * @see {@link #compareItems(org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack, boolean, boolean)}
     */
    public static boolean compareItems(ItemStack stack1, ItemStack stack2) {
        return compareItems(stack1, stack2, false, true);
    }

    /**
     * Compare 2 ItemStack's with each other.
     * If checkName is true the names of the items need to match for items to be equal.
     * if checkDurability is true the durability of the items need to match for items to be equal.
     */
    public static boolean compareItems(ItemStack stack1, ItemStack stack2, boolean checkName, boolean checkDurability) {
        if (stack1.getType() != stack2.getType()) {
            return false;
        }
        if (checkDurability && stack1.getDurability() != stack2.getDurability()) {
            return false;
        }
        if (checkName && stack2.hasItemMeta()) {
            if (!stack1.hasItemMeta() || !stack1.getItemMeta().getDisplayName().equalsIgnoreCase(stack2.getItemMeta().getDisplayName())) {
                return false;
            }
        }
        return true;
    }


    /**
     * Drops the given itemstack at the given location.
     * It will return the dropped Item.
     */
    public static Item dropItem(Location loc, ItemStack item) {
        return loc.getWorld().dropItem(loc, item);
    }

    /** @see {@link #dropItem(Location, ItemStack, Player)} */
    public static Item dropItemStack(Block block, ItemStack item, Player owner) {
        return dropItem(block.getLocation().add(0.5f, 0.5f, 0.5f), item, owner);
    }

    /**
     * Drops the given itemstack at the given location.
     * An owner will be set for the item so only the given player can pick the item up.
     * It will return the dropped Item.
     */
    public static Item dropItem(Location loc, ItemStack item, Player owner) {
        Item itemDrop = loc.getWorld().dropItem(loc, item);
        itemDrop.setMetadata("owner", new FixedMetadataValue(Essence.inst(), owner.getName()));
        itemDrop.setVelocity(itemDrop.getVelocity().multiply(0.2f));
        return itemDrop;
    }
}
