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

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.essencemc.essencecore.arguments.*;
import org.essencemc.essencecore.entity.EEntity;
import org.essencemc.essencecore.entity.EItem;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Util {

    private static final List<PotionEffectType> NEGATIVE_POTION_EFFECTS = Arrays.asList(PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.HARM, PotionEffectType.HUNGER, PotionEffectType.POISON, PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING, PotionEffectType.WEAKNESS, PotionEffectType.WITHER);

    /**
     * Integrate ChatColor in a string based on color codes.
     * @param str The string to apply color to.
     * @return formatted string
     */
    public static String color(String str) {
        for (ChatColor c : ChatColor.values()) {
            str = str.replaceAll("&" + c.getChar() + "|&" + Character.toUpperCase(c.getChar()), "§" + c.getChar());
        }
        return str;
    }


    /**
     * Remove all color and put colors as the formatting codes like &amp;1.
     * @param str The string to remove color from.
     * @return formatted string
     */
    public static String removeColor(String str) {
        for (ChatColor c : ChatColor.values()) {
            str = str.replace(c.toString(), "&" + c.getChar());
        }
        return str;
    }


    /**
     * Get a Color from a string.
     * The string can be either rrr,ggg,bbb or #hexhex or without the hashtag.
     * It can also be a name of a color preset.
     */
    public static Color getColor(String string) {
        if (string.isEmpty()) {
            return Color.WHITE;
        }
        if (string.contains("#")) {
            string = string.replace("#", "");
        }

        if (string.split(",").length > 2) {
            return getColorFromRGB(string);
        } else if (string.matches("[0-9A-Fa-f]+")) {
            return getColorFromHex(string);
        } else {
            //TODO: Return color from preset.
            return null;
        }
    }

    public static Color getColorFromHex(String string) {
        int c = 0;
        if (string.contains("#")) {
            string = string.replace("#", "");
        }
        if (string.matches("[0-9A-Fa-f]+")) {
            return Color.fromRGB(Integer.parseInt(string, 16));
        }
        return null;
    }

    public static Color getColorFromRGB(String string) {
        String[] split = string.split(",");
        if (split.length < 3) {
            return null;
        }
        Integer red = NumberUtil.getInt(split[0]);
        Integer green = NumberUtil.getInt(split[1]);
        Integer blue = NumberUtil.getInt(split[2]);
        if (red == null || green == null || blue == null) {
            return null;
        }
        return Color.fromRGB(Math.min(Math.max(red, 0), 255), Math.min(Math.max(green, 0), 255), Math.min(Math.max(blue, 0), 255));
    }

    public static boolean getBoolean(String input) {
        if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("1") || input.equalsIgnoreCase("v") || input.equalsIgnoreCase("y")) {
            return true;
        }
        return false;
    }

    public static Rotation getRotation(int degrees) {
        if (degrees >= 337.5 && degrees < 22.5) {
            return Rotation.NONE;
        } else if (degrees >= 22.5 && degrees < 67.5) {
            return Rotation.CLOCKWISE_45;
        } else if (degrees >= 67.5 && degrees < 112.5) {
            return Rotation.CLOCKWISE;
        } else if (degrees >= 112.5 && degrees < 157.5) {
            return Rotation.CLOCKWISE_135;
        } else if (degrees >= 157.5 && degrees < 202.5) {
            return Rotation.FLIPPED;
        } else if (degrees >= 202.5 && degrees < 247.5) {
            return Rotation.FLIPPED_45;
        } else if (degrees >= 247.5 && degrees < 292.5) {
            return Rotation.COUNTER_CLOCKWISE;
        } else if (degrees >= 292.5 && degrees < 337.5) {
            return Rotation.COUNTER_CLOCKWISE_45;
        }
        return Rotation.NONE;
    }


    public static Timestamp getTimeStamp() {
        java.util.Date now = new java.util.Date();
        return new java.sql.Timestamp(now.getTime());
    }


    public static String objectToString(Object obj) {
        if (obj == null) {
            return "&4&o&mnull";
        }
        if (obj instanceof Boolean) {
            return BoolArg.Parse((Boolean)obj);
        } else if (obj instanceof  Integer) {
            return IntArg.Parse((Integer)obj);
        //} else if (obj instanceof Long) {
            //return LongArg.Parse((Long)obj);
        } else if (obj instanceof Float) {
            return FloatArg.Parse((Float)obj);
        } else if (obj instanceof Double) {
            return DoubleArg.Parse((Double)obj);
        } else if (obj instanceof Location) {
            return LocationArg.Parse((Location)obj);
        } else if (obj instanceof Vector) {
            return VectorArg.Parse((Vector)obj);
        } else if (obj instanceof World) {
            return WorldArg.Parse((World)obj);
        } else if (obj instanceof Player) {
            return PlayerArg.Parse((Player) obj);
        //} else if (obj instanceof EEntity) {
            //return EntityArg.Parse((EEntity)obj);
        //} else if (obj instanceof Entity) {
            //return EntityArg.Parse(new EEntity((Entity)obj));
        } else if (obj instanceof EItem) {
            return ItemArg.Parse((EItem)obj);
        } else if (obj instanceof ItemStack) {
            return ItemArg.Parse(new EItem((ItemStack)obj));
        //} else if (obj instanceof Inventory) {
            //return InvArg.Parse((Inventory)obj);
        } else if (obj instanceof MaterialData) {
            return MaterialArg.Parse((MaterialData) obj);
        } else if (obj instanceof String) {
            return (String)obj;
        } else {
            return obj.toString();
        }
    }


    /**
     * Check whether a PotionEffectType is negative or positive.
     * @param effect The PotionEffectType to check.
     * @return Boolean
     */
    public static boolean isNegativePotionEffect(PotionEffectType effect) {
        return NEGATIVE_POTION_EFFECTS.contains(effect);
    }

    public static int countChars(String string, char ch) {
        int count = 0;
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }

    public static String trim(String string, String trim) {
        return Util.trimLast(Util.trimFirst(string, trim), trim);
    }

    public static String trimFirst(String string, String trim) {
        while (string.startsWith(trim)) {
            string = string.substring(trim.length());
        }
        return string;
    }

    public static String trimLast(String string, String trim) {
        while (string.endsWith(trim)) {
            string = string.substring(0, string.length() - 1 - trim.length());
        }
        return string;
    }

    public static String implode(Object[] arr, String glue, String lastGlue, int start, int end) {
        String ret = "";

        if (arr == null || arr.length <= 0)
            return ret;

        for (int i = start; i <= end && i < arr.length; i++) {
            if (i >= end-1 || i >= arr.length-2) {
                ret += arr[i].toString() + lastGlue;
            } else {
                ret += arr[i].toString() + glue;
            }
        }

        return ret.substring(0, ret.length() - lastGlue.length());
    }

    public static String implode(Object[] arr, String glue, int start) {
        return implode(arr, glue, glue, start, arr.length - 1);
    }

    public static String implode(Object[] arr, String glue, String lastGlue) {
        return implode(arr, glue, lastGlue, 0, arr.length - 1);
    }

    public static String implode(Object[] arr, String glue) {
        return implode(arr, glue, 0);
    }

    public static String implode(Collection<?> args, String glue) {
        if (args.isEmpty())
            return "";
        return implode(args.toArray(new Object[args.size()]), glue);
    }

    public static String implode(Collection<?> args, String glue, String lastGlue) {
        if (args.isEmpty())
            return "";
        return implode(args.toArray(new Object[args.size()]), glue, lastGlue);
    }

}
