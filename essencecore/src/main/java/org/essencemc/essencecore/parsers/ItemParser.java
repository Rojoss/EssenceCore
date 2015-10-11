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

package org.essencemc.essencecore.parsers;

import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.aliases.AliasType;
import org.essencemc.essencecore.aliases.Aliases;
import org.essencemc.essencecore.aliases.ItemAlias;
import org.essencemc.essencecore.aliases.Items;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.entity.EItem;
import org.essencemc.essencecore.entity.ItemTag;
import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.message.Param;
import org.essencemc.essencecore.util.NumberUtil;
import org.essencemc.essencecore.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private EText error = null;


    /**
     * Parses the given item string in to an EItem.
     * If ignoreErrors is set to true it will still set the errors but it will try to continue parsing the rest.
     * It would still fail in some cases for example if there is an invalid item specified.
     * @param string The item string. (For example: /i {string})
     * @param ignoreErrors If true it will continue parsing even when there is an error.
     */
    public ItemParser(String string, int defaultAmount, boolean ignoreErrors) {
        this.string = string;

        String[] words = string.split(" ");
        if (string == null || string.trim().length() < 1) {
            error = Message.PARSER_NO_ITEM_SPECIFIED.msg();
            return;
        }

        EItem item = new EItem(Material.AIR);
        List<String> sections = Util.splitQuotedString(string.trim());

        //Item
        ItemAlias itemAlias = Items.getItem(sections.get(0));
        if (itemAlias == null) {
            error = Message.PARSER_INVALID_ITEM.msg().params(Param.P("input", sections.get(0)));
            return;
        }
        item.setType(itemAlias.getType());
        item.setDurability(itemAlias.getData());
        item.setAmount(defaultAmount);

        //If it's air or if there is no meta specified we're done parsing...
        if (item.getType() == Material.AIR || sections.size() < 2) {
            this.item = item;
            return;
        }
        ItemMeta defaultMeta = Bukkit.getServer().getItemFactory().getItemMeta(item.getType());

        //Set amount and create map with key values for meta.
        Map<ItemTag, String> metaMap = new HashMap<ItemTag, String>();
        for (int i = 1; i < sections.size(); i++) {
            String section = sections.get(i);
            //Amount
            if (NumberUtil.getInt(section) != null) {
                item.setAmount(NumberUtil.getInt(section));
                continue;
            }

            String[] split = section.split(":", 2);
            //Only accept valid tags.
            ItemTag tag = ItemTag.fromString(split[0]);
            if (tag == null) {
                error = Message.UNKNOWN_ITEM_TAG.msg().params(Param.P("input", split[0]));
                if (ignoreErrors) {
                    continue;
                } else {
                    return;
                }
            }
            //Make sure the tag can be applied to the item we're creating.
            if (!ItemTag.getTagList(defaultMeta).contains(tag)) {
                error = Message.INVALID_ITEM_TAG.msg().params(Param.P("input", split[0]));
                if (ignoreErrors) {
                    continue;
                } else {
                    return;
                }
            }
            //Make sure there is a value.
            if (split.length < 2 || split[1].trim().isEmpty()) {
                error = Message.NO_META_VALUE.msg().params(Param.P("meta", split[0]));
                if (ignoreErrors) {
                    continue;
                } else {
                    return;
                }
            }
            metaMap.put(tag, split[1].trim());
        }


        //Parse all meta and apply it to the item.
        FireworkEffect.Type firework_effect = null;
        boolean firework_twinkle = false;
        boolean firework_trail = false;
        List<Color> firework_colors = new ArrayList<Color>();
        List<Color> firework_fadecolors = new ArrayList<Color>();
        for (Map.Entry<ItemTag, String> meta : metaMap.entrySet()) {
            ItemTag tag = meta.getKey();
            String val = meta.getValue();

            Argument arg = tag.getArg();
            if (!arg.parse(meta.getValue())) {
                error = arg.getError();
                return;
            }

            //Parse and apply custom meta.
            if (tag.getName().equals("color")) {
                Color color = Util.getColor(val);
                if (color == null) {
                    error = Message.PARSER_INVALID_COLOR.msg().params(Param.P("input", val));
                    if (!ignoreErrors) {
                        return;
                    }
                } else {
                    item.setColor(color);
                }
            } else if (tag.getName().equals("book")) {
                //TODO: Get book content and set it.
            } else if (tag.getName().equals("basecolor")) {
                item.setBaseColor(Aliases.getDyeColor(val));
            } else if (tag.getName().equals("shape")) {
                firework_effect = Aliases.getFireworkEffect(val);
            } else if (tag.getName().equals("twinkle")) {
                firework_twinkle = (Boolean)arg.getValue();
            } else if (tag.getName().equals("trail")) {
                firework_trail = (Boolean)arg.getValue();
            } else if (tag.getName().equals("colors") || tag.getName().equals("fade")) {
                String[] split = val.split(":");
                for (String clr : split) {
                    Color color = Util.getColor(clr.trim());
                    if (color == null) {
                        error = Message.PARSER_INVALID_COLOR.msg().params(Param.P("input", clr));
                    } else {
                        if (tag.getName().equals("fade")) {
                            firework_fadecolors.add(color);
                        } else {
                            firework_colors.add(color);
                        }
                    }
                }
            } else if (tag.getMethod().equals("ENCHANT")) {
                Enchantment enchant = Aliases.getEnchantment(tag.getName());
                item.addEnchant(enchant, (Integer)arg.getValue());
            } else if (tag.getName().equals("POTION")) {
                String[] split = val.split("\\.");
                if (split.length < 2) {
                    error = Message.PARSER_POTION_VALUE.msg().params(Param.P("input", val));
                } else {
                    Integer duration = NumberUtil.getInt(split[0]);
                    Integer amplifier = NumberUtil.getInt(split[1]);
                    if (duration == null || amplifier == null) {
                        error = Message.PARSER_POTION_VALUE.msg().params(Param.P("input", val));
                    } else {
                        PotionEffectType potion = Aliases.getPotionEffect(tag.getName());
                        item.addEffect(new PotionEffect(potion, duration, amplifier), true);
                    }
                }
            } else if (tag.getMethod().equals("PATTERN")) {
                PatternType pattern = Aliases.getBannerPattern(tag.getName());
                item.addPattern(pattern, Aliases.getDyeColor(val));
            } else {
                //Apply meta.
                try {
                    Method method = item.getClass().getMethod(tag.getMethod(), arg.getRawClass());
                    method.invoke(item, arg.getValue());
                } catch (NoSuchMethodException e) {
                    EssenceCore.inst().logError("No item method for " + tag.getMethod() + " for the tag " + tag.getName() + "!");
                } catch (IllegalAccessException e) {
                    EssenceCore.inst().logError("The item method " + tag.getMethod() + " can't be accessed!");
                } catch (InvocationTargetException e) {
                    EssenceCore.inst().logError("Failed to invoke the method " + tag.getMethod() + " for the tag " + tag.getName() + "!");
                    EssenceCore.inst().logError(e.getTargetException().getMessage());
                } catch (IllegalArgumentException e) {
                    EssenceCore.inst().logError("Invalid method body for the method " + tag.getMethod() + " for the tag " + tag.getName() + "!");
                    EssenceCore.inst().logError("Input: " + arg.getValue().getClass() + " : " + arg.getValue().toString());
                    try {
                        Method method = item.getClass().getMethod(tag.getMethod(), arg.getValue().getClass());
                        List<String> params = new ArrayList<String>();
                        for (Class c : method.getParameterTypes()) {
                            params.add(c.getName());
                        }
                        EssenceCore.inst().logError("Expected: " + Util.implode(params, ", "));
                    } catch (NoSuchMethodException e1) {}
                }
            }

            if (!ignoreErrors && error != null) {
                return;
            }
        }

        //Set item when ignoring errors in case firework building fails.
        if (ignoreErrors) {
            this.item = item;
        }

        //Create firework effect if applied.
        if (firework_effect != null || firework_trail || firework_twinkle || !firework_colors.isEmpty() || !firework_fadecolors.isEmpty()) {
            if (firework_effect == null) {
                error = Message.PARSER_MISSING_FIREWORK_SHAPE.msg();
                return;
            }
            if (firework_colors.isEmpty()) {
                error = Message.PARSER_MISSING_FIREWORK_SHAPE.msg();
                return;
            }
            item.addEffect(firework_effect, firework_colors, firework_fadecolors, firework_twinkle, firework_trail);
        }

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
            error = Message.PARSER_INVALID_ITEM.msg();
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
                components.add(Aliases.getName(AliasType.ENCHANTMENT, entry.getKey().getName()).replaceAll(" ", "") + ":" + entry.getValue());
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
                    if (EssenceCore.inst().getSkull() != null) {
                        components.add("texture:" + EssenceCore.inst().getSkull().getSkullUrl(skullMeta));
                    }
                } else {
                    components.add("player:" + skullMeta.getOwner());
                }
            }
        }

        //Banners
        if (meta instanceof BannerMeta) {
            BannerMeta bannerMeta = (BannerMeta)meta;

            components.add("basecolor:" + Aliases.getName(AliasType.DYE_COLOR, bannerMeta.getBaseColor().toString()).replaceAll(" ", ""));
            if (bannerMeta.getPatterns() != null && bannerMeta.getPatterns().size() > 0) {
                for (Pattern pattern : bannerMeta.getPatterns()) {
                    components.add(Aliases.getName(AliasType.BANNER_PATTERNS, pattern.getPattern().toString()).replaceAll(" ", "")
                            + ":" + Aliases.getName(AliasType.DYE_COLOR, pattern.getColor().toString()).replaceAll(" ", ""));
                }
            }
        }

        //Firework
        if (meta instanceof FireworkMeta) {
            FireworkMeta fireworkMeta = (FireworkMeta)meta;
            components.add("power:" + fireworkMeta.getPower());

            if (fireworkMeta.hasEffects()) {
                for (FireworkEffect effect : fireworkMeta.getEffects()) {
                    components.add("shape:" + Aliases.getName(AliasType.FIREWORK_EFFECT, effect.getType().toString()).replaceAll(" ", ""));

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
                    components.add(Aliases.getName(AliasType.POTION_EFFECT, effect.getType().getName()).replaceAll(" ", "") + ":" + effect.getDuration() + "." + effect.getAmplifier());
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
        return item != null && string != null && error == null;
    }

    /**
     * If the validation was unsuccessful this will return the error text.
     * @return the text which contains the error. If it was successfull the text will be null.
     */
    public EText getError() {
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
