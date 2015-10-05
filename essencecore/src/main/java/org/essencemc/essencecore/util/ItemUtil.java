/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://essencemc.org>
 * Copyright (c) 2015 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.essencemc.essencecore.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.essencemc.essencecore.EssenceCore;

public class ItemUtil {

    public static ItemStack air = new ItemStack(Material.AIR);

    /**
     * @see #compareItems(org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack, boolean, boolean)
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

    /** @see #dropItem(Location, ItemStack, Player) */
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
        itemDrop.setMetadata("owner", new FixedMetadataValue(EssenceCore.inst(), owner.getName()));
        itemDrop.setVelocity(itemDrop.getVelocity().multiply(0.2f));
        return itemDrop;
    }
}
