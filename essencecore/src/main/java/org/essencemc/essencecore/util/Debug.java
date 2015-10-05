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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.essencemc.essencecore.EssenceCore;

import javax.swing.text.html.parser.Entity;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Debug {

    /**
     * @see #log(String, Object)
     */
    public static void log(Object obj) {
        log("", obj);
    }

    /**
     * Log the debug message to console.
     * @param prefix A prefix added to the debug message. (Can also be used to color it like '&amp;1')
     * @param obj The object to debug. (This can also be just a string.)
     */
    public static  void log(String prefix, Object obj) {
        EssenceCore.inst().log(getDebugMsg(prefix, obj));
    }



    /**
     * @see #bc(String, Object)
     */
    public static void bc(Object obj) {
        bc("", obj);
    }

    /**
     * Broadcast the debug message to the entire server.
     * @param prefix A prefix added to the debug message. (Can also be used to color it like '&amp;1')
     * @param obj The object to debug. (This can also be just a string.)
     */
    public static void bc(String prefix, Object obj) {
        EssenceCore.inst().getServer().broadcastMessage(getDebugMsg(prefix, obj));
    }



    /**
     * @see #send(Player, String, Object)
     */
    public static void send(Player player, Object obj) {
        send(player, "", obj);
    }

    /**
     * Send the debug message to the specified player.
     * @param prefix A prefix added to the debug message. (Can also be used to color it like '&amp;1')
     * @param obj The object to debug. (This can also be just a string.)
     */
    public static void send(Player player, String prefix, Object obj) {
        player.sendMessage(getDebugMsg(prefix, obj));
    }



    /**
     * @see #bcOps(String, Object)
     */
    public static void bcOps(Object obj) {
        bcOps("", obj);
    }

    /**
     * Send the debug message to all ops and player with 'cw.debug' permission.
     * @param prefix A prefix added to the debug message. (Can also be used to color it like '&amp;1')
     * @param obj The object to debug. (This can also be just a string.)
     */
    public static void bcOps(String prefix, Object obj) {
        for (Player player : EssenceCore.inst().getServer().getOnlinePlayers()) {
            if (player.isOp() || player.hasPermission("cw.debug")) {
                player.sendMessage(getDebugMsg(prefix, obj));
            }
        }
    }




    private static String getDebugMsg(String prefix, Object obj) {
        String objString = "";
        if (obj == null) {
            return Util.color("&8DEBUG&7: &4null");

        }
        objString = obj.toString();

        if (obj instanceof Vector) {
            objString = "Vector<X:" + ((Vector)obj).getX() + ", Y:" + ((Vector)obj).getY() + ", Z:" + ((Vector)obj).getZ() + ">";
        }

        if (obj instanceof Location) {
            objString = "Location<World:" + ((Location)obj).getWorld().getName() + ", X:" + ((Location)obj).getX() + ", Y:" + ((Location)obj).getY() + ", Z:" + ((Location)obj).getZ()
                    + " Yaw:" + ((Location)obj).getYaw() + ", Pitch:" + ((Location)obj).getPitch() + ">";
        }

        if (obj instanceof Player) {
            objString = "Player<Name:" + ((Player)obj).getName() + ", UUID:" + ((Player)obj).getUniqueId() + ">";
        }

        if (obj instanceof Block) {
            objString = "Location<Mat:" + ((Block)obj).getType() + ", X:" + ((Block)obj).getX() + ", Y:" + ((Block)obj).getY() + ", Z:" + ((Block)obj).getZ() + ">";
        }

        if (obj instanceof ItemStack) {
            objString = "ItemStack<Mat:" + ((ItemStack)obj).getType() + ", Amt:" + ((ItemStack)obj).getAmount() + ", Dur:" + ((ItemStack)obj).getDurability()
                    + ", Meta:" + ((ItemStack)obj).hasItemMeta() + ", Name:" + (((ItemStack)obj).hasItemMeta() ? ((ItemStack)obj).getItemMeta().getDisplayName() : "no-meta") + ">";
        }

        if (obj instanceof Entity) {
            objString = "Entity<Type:" + ((Entity)obj).getType() + ", Name:" + ((Entity)obj).getName() + ">";
        }

        if (obj instanceof Map) {
            objString = "Map<";
            for (Object key : ((Map)obj).keySet()) {
                objString += key.toString() + ":" + ((Map)obj).get(key) + ", ";
            }
            objString += ">";
        }

        if (obj instanceof Collection) {
            objString = "Collection<";
            for (Object key : ((Collection)obj)) {
                objString += key.toString() + ", ";
            }
            objString += ">";
        }

        if (obj instanceof Set) {
            objString = "Set<";
            for (Object key : ((Set)obj)) {
                objString += key.toString() + ", ";
            }
            objString += ">";
        }

        return Util.color("&8DEBUG&7: &f" + prefix + objString);
    }

}
