package com.clashwars.essence.util;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.potion.PotionEffectType;

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
            str = str.replaceAll("&" + c.getChar() + "|&" + Character.toUpperCase(c.getChar()), c.toString());
        }
        return str;
    }


    /**
     * Remove all color and put colors as the formatting codes like &1.
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

    /**
     * Get a Byte[] array with the texture skull url
     * The input string can be a textures.minecraft.net link, the code from the link only or a Base64 encoded string.
     * http://heads.freshcoal.com/maincollection.php
     */
    public static byte[] getSkullTexture(String input) {
        if (input.endsWith("=")) {
            //Encoded texture.
            //eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTU1ZDYxMWE4NzhlODIxMjMxNzQ5YjI5NjU3MDhjYWQ5NDI2NTA2NzJkYjA5ZTI2ODQ3YTg4ZTJmYWMyOTQ2In19fQ==
            return input.getBytes();
        } else {
            if (input.contains("/")) {
                //Whole texture url from textures.minecraft.net
                //http://textures.minecraft.net/texture/955d611a878e821231749b2965708cad942650672db09e26847a88e2fac2946
                String[] split = input.split("/");
                input = split[split.length-1];
            }
            //Texture code split from the url.
            //955d611a878e821231749b2965708cad942650672db09e26847a88e2fac2946
            input = "http://textures.minecraft.net/texture/" + input;
            return Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", input).getBytes());
        }
    }

    public static boolean getBoolean(String input) {
        if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("1") || input.equalsIgnoreCase("v") || input.equalsIgnoreCase("y")) {
            return true;
        }
        return false;
    }


    /**
     * Check whether a PotionEffectType is negative or positive.
     * @param effect The PotionEffectType to check.
     * @return Boolean
     */
    public static boolean isNegativePotionEffect(PotionEffectType effect) {
        return NEGATIVE_POTION_EFFECTS.contains(effect);
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
