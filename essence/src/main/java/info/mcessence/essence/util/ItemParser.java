/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://www.mc-essence.info>
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

package info.mcessence.essence.util;

import info.mcessence.essence.message.Message;
import info.mcessence.essence.aliases.ItemAlias;
import info.mcessence.essence.aliases.Items;
import info.mcessence.essence.entity.EItem;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;

import java.util.*;

/**
 * The ItemParser can be used to parse strings to EItems and the other way around.
 * To parse an item or string you just create an instance of this class and pass a string or EItem in.
 * Then you can get isValid() to check if the parsing was successful.
 * If not you can call getError() to get the error message which you should display to the user.
 * And then finally getItem() or getString() to get the value.
 */
public class ItemParser {

    private String string = null;
    private EItem item = null;

    //TODO: Change this to the new message system and insert the data in to the messages.
    private Message error = Message.PARSER_VALID;


    /**
     * Parses the given item string in to an EItem.
     * If ignoreErrors is set to true it will still set the errors but it will try to continue parsing the rest.
     * It would still fail in some cases for example if there is an invalid item specified.
     * @param string The item string. (For example: /i {string})
     * @param ignoreErrors If true it will continue parsing even when there is an error.
     */
    public ItemParser(String string, boolean ignoreErrors) {
        this.string = string;

        EItem item = new EItem(Material.AIR);

        String[] sections = string.split(" ");
        if (sections.length < 1) {
            error = Message.PARSER_NO_ITEM_SPECIFIED;
            return;
        }
        List<String> sectionList = new ArrayList<String>(Arrays.asList(sections));

        //item[:data]
        ItemAlias itemAlias = Items.getItem(sections[0]);
        if (itemAlias == null) {
            error = Message.PARSER_INVALID_ITEM; //TODO: Set item specified
            return;
        }
        item.setType(itemAlias.getType());
        item.setDurability(itemAlias.getData());
        sectionList.remove(0);

        //No meta can be applied to air.
        if (item.getType() == Material.AIR) {
            this.item = item;
            return;
        }

        //Amount
        if (sections.length > 1) {
            if (NumberUtil.getInt(sections[1]) == null) {
                error = Message.PARSER_INVALID_AMOUNT; //TODO: Set amount specified
                if (!ignoreErrors) {
                    return;
                }
            } else {
                item.setAmount(NumberUtil.getInt(sections[1]));
            }
            sectionList.remove(0);
        }

        //No meta specified
        if (sectionList.size() < 1) {
            this.item = item;
            return;
        }

        //Create a map with all meta keys/values.
        Map<String, String> metaMap = new HashMap<String, String>();
        for (String section : sectionList) {
            String[] split = section.split(":");
            if (split.length < 2) {
                error = Message.NO_META_VALUE; //TODO: Set meta key
                if (ignoreErrors) {
                    continue;
                } else {
                    return;
                }
            }
            metaMap.put(split[0].toLowerCase(), split[1].replaceAll("_", " "));
        }

        //Name
        if (metaMap.containsKey("name")) {
            item.setName(metaMap.get("name"));
            metaMap.remove("name");
        }

        //Lore
        if (metaMap.containsKey("lore")) {
            String[] lore =  metaMap.get("lore").split("\\|");
            item.setLore(lore);
            metaMap.remove("lore");
        }

        //Color
        if (metaMap.containsKey("leather")) {
            Color color = Util.getColor(metaMap.get("leather"));
            if (color == null) {
                error = Message.PARSER_INVALID_COLOR; //TODO: Set color
                if (!ignoreErrors) {
                    return;
                }
            } else {
                item.setColor(color);
            }
            metaMap.remove("leather");
        }

        //Books
        if (metaMap.containsKey("book")) {
            //TODO: Get book file and format it etc.
            metaMap.remove("book");
        }
        if (metaMap.containsKey("author")) {
            item.setAuthor(metaMap.get("author"));
            metaMap.remove("author");
        }
        if (metaMap.containsKey("title")) {
            item.setAuthor(metaMap.get("title"));
            metaMap.remove("title");
        }
        if (metaMap.containsKey("editable")) {
            item.setEditable(Util.getBoolean(metaMap.get("editable")));
            metaMap.remove("editable");
        }

        //Skulls
        if (metaMap.containsKey("player")) {
            item.setSkull(metaMap.get("player"));
            metaMap.remove("player");
        }
        if (metaMap.containsKey("texture")) {
            item.setTexture(metaMap.get("texture"));
            metaMap.remove("texture");
        }

        //Banners
        if (metaMap.containsKey("basecolor")) {
            DyeColor color = DyeColor.WHITE; //TODO: Get color from alias.
            if (color == null) {
                error = Message.PARSER_INVALID_DYE_COLOR; //TODO: Set color
                if (!ignoreErrors) {
                    return;
                }
            } else {
                item.setBaseColor(color);
            }
            metaMap.remove("basecolor");
        }

        //Firework
        FireworkEffect.Builder fireworkBuilder = FireworkEffect.builder();
        boolean hasFireworkMeta = false;
        boolean hasShape = false;
        boolean hasColor = false;
        if (metaMap.containsKey("power")) {
            if (NumberUtil.getInt(metaMap.get("power")) == null) {
                error = Message.NOT_A_NUMBER; //TODO: Set power specified
                if (!ignoreErrors) {
                    return;
                }
            } else {
                item.setPower(NumberUtil.getInt(metaMap.get("power")));
            }
            metaMap.remove("power");
        }
        if (metaMap.containsKey("shape")) {
            FireworkEffect.Type shape = FireworkEffect.Type.BALL; //TODO: Get shape from alias
            if (shape == null) {
                error = Message.PARSER_INVALID_SHAPE; //TODO: Set shape specified.
                if (!ignoreErrors) {
                    return;
                }
            } else {
                fireworkBuilder.with(shape);
            }
            metaMap.remove("shape");
            hasFireworkMeta = true;
            hasShape = true;
        }
        if (metaMap.containsKey("color")) {
            String[] colorSplit = metaMap.get("color").split(";");
            List<Color> colors = new ArrayList<Color>();
            for (String color : colorSplit) {
                Color clr = Util.getColor(color);
                if (clr == null) {
                    error = Message.PARSER_INVALID_COLOR; //TODO: Set the color specified.
                    if (!ignoreErrors) {
                        return;
                    }
                } else {
                    colors.add(clr);
                }
            }
            if (colors.size() > 0) {
                fireworkBuilder.withColor(colors);
            }
            metaMap.remove("color");
            hasFireworkMeta = true;
            hasColor = true;
        }
        if (metaMap.containsKey("fade")) {
            String[] colorSplit = metaMap.get("fade").split(";");
            List<Color> colors = new ArrayList<Color>();
            for (String color : colorSplit) {
                Color clr = Util.getColor(color);
                if (clr == null) {
                    error = Message.PARSER_INVALID_COLOR; //TODO: Set the color specified.
                    if (!ignoreErrors) {
                        return;
                    }
                } else {
                    colors.add(clr);
                }
            }
            if (colors.size() > 0) {
                fireworkBuilder.withFade(colors);
            }
            metaMap.remove("fade");
            hasFireworkMeta = true;
        }
        if (metaMap.containsKey("flicker")) {
            if (Util.getBoolean(metaMap.get("flicker"))) {
                fireworkBuilder.withFlicker();
            }
            metaMap.remove("twinkle");
            hasFireworkMeta = true;
        }
        if (metaMap.containsKey("trail")) {
            if (Util.getBoolean(metaMap.get("trail"))) {
                fireworkBuilder.withTrail();
            }
            metaMap.remove("trail");
            hasFireworkMeta = true;
        }
        try {
            item.addEffect(fireworkBuilder.build());
        } catch (Exception e) {
            if (hasFireworkMeta) {
                if (!hasShape) {
                    error = Message.PARSER_MISSING_FIREWORK_SHAPE;
                    if (!ignoreErrors) {
                        return;
                    }
                }
                if (!hasShape) {
                    error = Message.PARSER_MISSING_FIREWORK_COLOR;
                    if (!ignoreErrors) {
                        return;
                    }
                }
            }
        }

        //If there is any meta remaining do enchants, effects and banner patterns.
        if (metaMap.size() > 0) {
            //TODO: Parse enchantments from aliases

            //TODO: Parse potion effects from aliases

            //TODO: Parse banner patterns from aliases
        }

        //DONE PARSING!
        this.item = item;
    }

    /**
     * Parses the given ItemStack in to a string.
     * @param item The item instance to be parsed.
     */
    public ItemParser(ItemStack item) {
        this(new EItem(item));
    }

    /**
     * Parses the given EItem in to a string.
     * @param item The item instance to be parsed.
     */
    public ItemParser(EItem item) {
        this.item = item;

        //Don't do anything for air.
        if (item == null || item.getType() == Material.AIR) {
            this.string = "Air";
            return;
        }

        List<String> components = new ArrayList<String>();

        //Material[:data]
        ItemAlias itemAlias = Items.getItem(item.getType(), item.getDurability());
        if (itemAlias == null) {
            error = Message.PARSER_INVALID_ITEM;
            return;
        }
        String itemString = itemAlias.getName().replaceAll(" ", "");
        if (itemAlias.getData() != 0) {
            itemString += ":" + itemAlias.getData();
        }
        components.add(itemString);

        //Amount
        components.add(Integer.toString(item.getAmount()));

        //No meta
        if (!item.hasItemMeta()) {
            this.string = Util.implode(components, " ");
            return;
        }
        ItemMeta meta = item.getItemMeta();

        //Name
        if (meta.hasDisplayName()) {
            components.add("name:" + Util.removeColor(meta.getDisplayName()).replaceAll(" ", "_"));
        }

        //Lore
        if (meta.hasLore()) {
            String lore = Util.implode(meta.getLore(), "|");
            components.add("lore:" + Util.removeColor(lore).replaceAll(" ", "_"));
        }

        //Enchants
        if (meta.hasEnchants()) {
            for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                //TODO: Use default name from alias.
                components.add(entry.getKey().getName().toLowerCase().replaceAll("_", "") + ":" + entry.getValue());
            }
        }

        //Leather color
        if (meta instanceof LeatherArmorMeta) {
            Color color = ((LeatherArmorMeta)meta).getColor();
            if (color != null) {
                components.add("leather:" + String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
            }
        }

        //Book
        if (meta instanceof BookMeta) {
            BookMeta bookMeta = (BookMeta)meta;

            if (bookMeta.hasPages()) {
                //TODO: Get the book template from EItem (probably have to store it in EItem when setting book)
                components.add("book:Unknown");
            }
            if (bookMeta.hasAuthor()) {
                components.add("author:" + Util.removeColor(bookMeta.getAuthor()).replaceAll(" ", "_"));
            }
            if (bookMeta.hasTitle()) {
                components.add("title:" + Util.removeColor(bookMeta.getTitle()).replaceAll(" ", "_"));
            }
        }

        //Skulls
        if (meta instanceof SkullMeta) {
            SkullMeta skullMeta = (SkullMeta)meta;

            if (skullMeta.hasOwner()) {
                if (skullMeta.getOwner() == null) {
                    //TODO: Get skull texture
                } else {
                    components.add("player:" + skullMeta.getOwner());
                }
            }
        }

        //Banners
        if (meta instanceof BannerMeta) {
            BannerMeta bannerMeta = (BannerMeta)meta;

            //TODO: Use default DyeColor name from alias.
            components.add("basecolor:" + bannerMeta.getBaseColor().toString().toLowerCase().replaceAll("_", ""));
            if (bannerMeta.getPatterns() != null && bannerMeta.getPatterns().size() > 0) {
                for (Pattern pattern : bannerMeta.getPatterns()) {
                    //TODO: Use default Pattern and DyeColor name from alias.
                    components.add(pattern.getPattern().toString().toLowerCase().replace("_", "") + ":" + pattern.getColor().toString().toLowerCase().replaceAll("_", ""));
                }
            }
        }

        //Firework
        if (meta instanceof FireworkMeta) {
            FireworkMeta fireworkMeta = (FireworkMeta)meta;
            components.add("power:" + fireworkMeta.getPower());

            if (fireworkMeta.hasEffects()) {
                for (FireworkEffect effect : fireworkMeta.getEffects()) {
                    //TODO: Use default Shape/Effect name from alias.
                    components.add("shape:" + effect.getType().toString().replaceAll("_", ""));

                    components.add("flicker:" + effect.hasFlicker());
                    components.add("trail:" + effect.hasTrail());

                    List<String> colors = new ArrayList<String>();
                    if (effect.getColors() != null && effect.getColors().size() > 0) {
                        for (Color color : effect.getColors()) {
                            colors.add(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                        }
                        components.add("color:" + Util.implode(colors, ";"));
                    }

                    if (effect.getFadeColors() != null && effect.getFadeColors().size() > 0) {
                        colors.clear();
                        for (Color color : effect.getFadeColors()) {
                            colors.add(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                        }
                        components.add("fade:" + Util.implode(colors, ";"));
                    }
                }
            }
        }

        //Potion effects
        if (meta instanceof PotionMeta) {
            PotionMeta potionMeta = (PotionMeta)meta;
            if (potionMeta.hasCustomEffects()) {
                for (PotionEffect effect : potionMeta.getCustomEffects()) {
                    //TODO: Use default Effect name from alias.
                    components.add(effect.getType().getName().toLowerCase().replaceAll("_", "") + ":" + effect.getDuration() + "." + effect.getAmplifier());
                }
            }
        }

        //DONE PARSING!
        this.string = Util.implode(components, " ");
    }

    /**
     * Checks if the parsing was successful or not.
     * Call getError() if it wasn't successful and display it to the user.
     * @return if it parsed successful.
     */
    public boolean isValid() {
        return item != null && string != null && error == Message.PARSER_VALID;
    }

    /**
     * If the validation was unsuccessful this will return the error message.
     * @return the Message which contains the error. If it was successfull the message will be PARSER_VALID
     */
    public Message getError() {
        return error;
    }

    /**
     * Get the parsed string value.
     * @return string which can be used in configurations and commands.
     */
    public String getString() {
        return string;
    }

    /**
     * Get the parsed EItem value.
     * Note: The item can be air so for spawning items and such make sure to check if it's not air.
     * @return EItem instance with all meta parsed.
     */
    public EItem getItem() {
        return item;
    }
}
