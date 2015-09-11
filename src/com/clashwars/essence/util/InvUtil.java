package com.clashwars.essence.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Horse;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class InvUtil {

    /** @see {@link #addItems(Inventory, ItemStack, int, boolean)} */
    public static HashMap<Integer, ItemStack> addItems(Inventory inventory, ItemStack item) {
        return addItems(inventory, item, -1, true);
    }

    /** @see {@link #addItems(Inventory, ItemStack, int, boolean)} */
    public static HashMap<Integer, ItemStack> addItems(Inventory inventory, ItemStack item, int slot) {
        return addItems(inventory, item, slot, true);
    }

    /** @see {@link #addItems(Inventory, ItemStack, int, boolean)} */
    public static HashMap<Integer, ItemStack> addItems(Inventory inventory, ItemStack item, boolean dropifFull) {
        return addItems(inventory, item, -1, dropifFull);
    }

    /**
     * Adds the itemstack to the given inventory.
     * If a slot is specified it will first try to place it in the given slot.
     * If that slot is full or if there is no slot specified it will try to add the items to the inventory normally.
     * If the items don't fit in it will drop them on the ground if the boolean dropIfFull is set to true.
     * It will return a map with excess items if items didn't fit in. (This includes items that have been dropped)
     */
    public static HashMap<Integer, ItemStack> addItems(Inventory inventory, ItemStack item, int slot, boolean dropIfFull) {
        if (slot >= 0) {
            if (inventory.getItem(slot) == null || inventory.getItem(slot).getType() == Material.AIR) {
                inventory.setItem(slot, item);
            }
            return new HashMap<Integer, ItemStack>();
        }

        HashMap<Integer, ItemStack> excess = inventory.addItem(item);
        if (!dropIfFull) {
            return excess;
        }

        Location location = getLocation(inventory);
        for (ItemStack itemStack : excess.values()) {
            location.getWorld().dropItem(location, itemStack);
        }
        return excess;
    }


    /** Clear a player's inventory completely. Including armor slots if armorSlots is set to true. */
    public static void clearPlayerInv(Player player, boolean armorSlots) {
        player.getInventory().clear();
        if (armorSlots) {
            player.getInventory().setHelmet(ItemUtil.air);
            player.getInventory().setChestplate(ItemUtil.air);
            player.getInventory().setLeggings(ItemUtil.air);
            player.getInventory().setBoots(ItemUtil.air);
        }
    }


    /** Gets the location of the inventory if the inventory holder has a location. */
    public static Location getLocation(Inventory inventory) {
        InventoryHolder holder = inventory.getHolder();
        Location location = null;
        if (holder instanceof Chest) {
            location = ((Chest)holder).getLocation();
        } else if (holder instanceof DoubleChest) {
            location = ((DoubleChest)holder).getLocation();
        } else if (holder instanceof Beacon) {
            location = ((Beacon)holder).getLocation();
        } else if (holder instanceof Beacon) {
            location = ((Beacon)holder).getLocation();
        } else if (holder instanceof BrewingStand) {
            location = ((BrewingStand)holder).getLocation();
        } else if (holder instanceof Dispenser) {
            location = ((Dispenser)holder).getLocation();
        } else if (holder instanceof Dropper) {
            location = ((Dropper)holder).getLocation();
        } else if (holder instanceof Hopper) {
            location = ((Hopper)holder).getLocation();
        } else if (holder instanceof Furnace) {
            location = ((Furnace)holder).getLocation();
        } else if (holder instanceof HopperMinecart) {
            location = ((HopperMinecart)holder).getLocation();
        } else if (holder instanceof HopperMinecart) {
            location = ((HopperMinecart)holder).getLocation();
        } else if (holder instanceof StorageMinecart) {
            location = ((StorageMinecart)holder).getLocation();
        } else if (holder instanceof Horse) {
            location = ((Horse)holder).getLocation();
        } else if (holder instanceof HumanEntity) {
            location = ((HumanEntity)holder).getLocation();
        } else if (holder instanceof Player) {
            location = ((Player)holder).getLocation();
        }
        return location;
    }


}
