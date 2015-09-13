package com.clashwars.essence.util;

import com.clashwars.essence.aliases.ItemAlias;
import com.clashwars.essence.aliases.Items;
import com.clashwars.essence.entity.EItem;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemParser {

    //TODO: Create an ItemParseResult that can have errors and such.
    public static EItem fromString(String string) {
        EItem item = new EItem(Material.STONE);

        String[] sections = string.split(" ");
        List<String> sectionList = new ArrayList<String>(Arrays.asList(sections));

        //item:data
        if (sections.length > 0) {
            ItemAlias itemAlias = Items.getItem(sections[0]);
            if (itemAlias != null) {
                item.setType(itemAlias.getType());
                item.setDurability(itemAlias.getData());
                sectionList.remove(0);
            }
        } else {
            return item;
        }

        //Amount
        if (sections.length > 1) {
            item.setAmount(NumberUtil.getInt(sections[1]) != null ? NumberUtil.getInt(sections[1]) : 1);
            sectionList.remove(0);
        }

        FireworkEffect.Builder fireworkBuilder = FireworkEffect.builder();

        //Meta
        for (String section : sections) {
            String[] split = section.split(":");
            if (split.length < 2) {
                continue;
            }
            String key = split[0].toLowerCase();
            String value = split[1];

            //Name
            if (key.equals("name") || key.equals("displayname")) {
                item.setName(value.replaceAll("_", " "));

            //Lore
            } else if (key.equals("lore")) {
                String loreStr = value.replaceAll("_", " ");
                String[] lore =  loreStr.split("\\|");
                item.setLore(lore);

            //Color
            } else if (key.equals("color") || key.equals("armor") || key.equals("leather") || key.equals("leathercolor") || key.equals("armorcolor")) {
                item.setColor(value);

            //Books
            } else if (key.equals("book")) {
                //TODO: Get book file and format it.
            } else if (key.equals("author") || key.equals("authors")) {
                item.setAuthor(value.replaceAll("_", " "));
            } else if (key.equals("title")) {
                item.setTitle(value.replaceAll("_", " "));
            } else if (key.equals("editable")) {
                item.setEditable(Util.getBoolean(value));

            //Banners
            } else if (key.equals("basecolor") || key.equals("bannercolor")) {
                //TODO: Get DyeColor from alias.
                item.setBaseColor(DyeColor.WHITE);

            //Skulls
            } else if (key.equals("player") || key.equals("owner") || key.equals("skull")) {
                item.setSkull(value);
            } else if (key.equals("texture") || key.equals("skin")) {
                item.setTexture(value);

            //Firework
            } else if (key.equals("power")) {
                item.setPower(NumberUtil.getInt(value) == null ? 1 : NumberUtil.getInt(value));
            } else if (key.equals("shape") || key.equals("effect")) {
                //TODO: Get firework effect from alias.
                fireworkBuilder.with(FireworkEffect.Type.BALL);
            } else if (key.equals("twinkle") || key.equals("twinkles") || key.equals("sparks") || key.equals("flicker")) {
                if (Util.getBoolean(value)) {
                    fireworkBuilder.withFlicker();
                }
            } else if (key.equals("trail")) {
                if (Util.getBoolean(value)) {
                    fireworkBuilder.withTrail();
                }
            } else if (key.equals("color")) {
                String[] colorSplit = value.split(";");
                List<Color> colors = new ArrayList<Color>();
                for (String color : colorSplit) {
                    Color clr = Util.getColor(color);
                    if (clr != null) {
                        colors.add(clr);
                    }
                }
                if (colors.size() < 1) {
                    colors.add(Color.WHITE);
                }
                fireworkBuilder.withColor(colors);
            } else if (key.equals("color")) {
                String[] colorSplit = value.split(";");
                List<Color> colors = new ArrayList<Color>();
                for (String color : colorSplit) {
                    Color clr = Util.getColor(color);
                    if (clr != null) {
                        colors.add(clr);
                    }
                }
                if (colors.size() > 0) {
                    fireworkBuilder.withFade(colors);
                }
            }

            //TODO: Check for key match with enchant aliases

            //TODO: Check for key match with potion effect aliases

            //TODO: Check for key match with banner pattern aliases
        }
        try {
            item.addEffect(fireworkBuilder.build());
        } catch (Exception e) {
        }

        return item;
    }

    //TODO: Implement
    public static String toString(EItem item) {
        return "";
    }
}
