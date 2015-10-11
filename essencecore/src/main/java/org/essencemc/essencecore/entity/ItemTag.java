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

package org.essencemc.essencecore.entity;

import org.bukkit.inventory.meta.*;
import org.essencemc.essencecore.aliases.Alias;
import org.essencemc.essencecore.aliases.AliasType;
import org.essencemc.essencecore.aliases.Aliases;
import org.essencemc.essencecore.arguments.*;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ItemTag {

    private static Map<String, ItemTag> tags = new HashMap<String, ItemTag>();

    public static void register() {
        //All items
        new ItemTag("NAME", new StringArg("name"), "name", Message.META_NAME.msg(), "setName", ItemMeta.class);
        new ItemTag("LORE", new StringArg("lore"), "lore", Message.META_LORE.msg(), "setLore", ItemMeta.class);

        //Leather
        new ItemTag("COLOR", new StringArg("color"), "color", Message.META_COLOR.msg(), "setColor", LeatherArmorMeta.class);

        //Books
        new ItemTag("BOOK", new StringArg("book"), "book.book", Message.META_BOOK.msg(), "CUSTOM", BookMeta.class);
        new ItemTag("AUTHOR", new StringArg("author"), "book.author", Message.META_AUTHOR.msg(), "setAuthor", BookMeta.class);
        new ItemTag("TITLE", new StringArg("title"), "book.title", Message.META_TITLE.msg(), "setTitle", BookMeta.class);
        new ItemTag("EDITABLE", new BoolArg("editable"), "book.editable", Message.META_EDITABLE.msg(), "setEditable", BookMeta.class);

        //Banners
        new ItemTag("BASECOLOR", new MappedListArg("basecolor", Aliases.getAliasesMap(AliasType.DYE_COLOR)), "banner.basecolor", Message.META_BASECOLOR.msg(), "CUSTOM", BannerMeta.class);

        //Skulls
        new ItemTag("PLAYER", new StringArg("player"), "skull.player", Message.META_PLAYER.msg(), "setSkull", SkullMeta.class);
        new ItemTag("TEXTURE", new StringArg("texture"), "skull.texture", Message.META_TEXTURE.msg(), "setTexture", SkullMeta.class);

        //Firework
        new ItemTag("POWER", new IntArg("power", 0, 10), "firework.power", Message.META_POWER.msg(), "setPower", FireworkMeta.class);
        new ItemTag("SHAPE", new MappedListArg("shape", Aliases.getAliasesMap(AliasType.FIREWORK_EFFECT)), "firework.shape", Message.META_SHAPE.msg(), "CUSTOM", FireworkMeta.class);
        new ItemTag("TWINKLE", new BoolArg("twinkle"), "firework.twinkle", Message.META_TWINKLE.msg(), "CUSTOM", FireworkMeta.class);
        new ItemTag("TRAIL", new BoolArg("trail"), "firework.trail", Message.META_TRAIL.msg(), "CUSTOM", FireworkMeta.class);
        new ItemTag("COLORS", new StringArg("colors"), "firework.colors", Message.META_COLORS.msg(), "CUSTOM", FireworkMeta.class);
        new ItemTag("FADE", new StringArg("fade"), "firework.fade", Message.META_FADE.msg(), "CUSTOM", FireworkMeta.class);

        //Misc
        new ItemTag("GLOW", new BoolArg("glow"), "glow", Message.META_GLOW.msg(), "makeGlowing", ItemMeta.class);
        new ItemTag("HIDEMETA", new BoolArg("hidemeta"), "hidemeta", Message.META_HIDEMETA.msg(), "addAllFlags", ItemMeta.class);


        //TODO: Maybe don't add the aliases here as it will flood the help messages. Alternatively maybe add a visibility flag.
        //Enchants
        for (Alias enchant : Aliases.getAliases(AliasType.ENCHANTMENT)) {
            String key = enchant.getName().toLowerCase().replace(" ", "");
            for (String alias : enchant.getAliases()) {
                new ItemTag(alias, new IntArg(alias), "enchant." + key, Message.META_ENCHANT.msg(), "ENCHANT", ItemMeta.class);
            }
        }

        //Potions
        for (Alias potion : Aliases.getAliases(AliasType.POTION_EFFECT)) {
            String key = potion.getName().toLowerCase().replace(" ", "");
            for (String alias : potion.getAliases()) {
                new ItemTag(alias, new StringArg(alias), "potion." + key, Message.META_POTION.msg(), "POTION", PotionMeta.class);
            }
        }

        //Banner patterns
        Map<String, List<String>> dyeColors = Aliases.getAliasesMap(AliasType.DYE_COLOR);
        for (Alias pattern : Aliases.getAliases(AliasType.BANNER_PATTERNS)) {
            String key = pattern.getName().toLowerCase().replace(" ", "");
            for (String alias : pattern.getAliases()) {
                new ItemTag(alias, new MappedListArg(alias, dyeColors), "pattern." + key, Message.META_PATTERN.msg(), "PATTERN", BannerMeta.class);
            }
        }
    }

    private String name;
    private Argument argument;
    private String permission;
    private EText description;
    private String method;
    private Class<? extends ItemMeta>[] classes;

    public ItemTag(String name, Argument argument, String permission, EText description, String method, Class<? extends ItemMeta>... classes) {
        name = name.toLowerCase().replace("_", "").replace(" ", "");
        if (tags.containsKey(name)) {
            return;
        }
        this.name = name;
        this.argument = argument;
        this.permission = "essence.meta." + permission;
        this.description = description;
        this.method = method;
        this.classes = classes;
        tags.put(name, this);
    }

    public Class<? extends ItemMeta>[] getClasses() {
        return classes;
    }

    public String getName() {
        return name;
    }

    public Argument getArg() {
        return argument;
    }

    public String getPermission() {
        return permission;
    }

    public EText getDescription() {
        return description;
    }

    public String getMethod() {
        return method;
    }

    public static ItemTag fromString(String name) {
        name = name.toLowerCase().replace("_","").replace(" ","");
        if (tags.containsKey(name)) {
            return tags.get(name);
        }
        return null;
    }

    public static List<ItemTag> getTagList(ItemMeta meta) {
        List<ItemTag> tags = new ArrayList<ItemTag>();
        for (ItemTag tag : ItemTag.tags.values()) {
            for (Class<? extends ItemMeta> clazz : tag.getClasses()) {
                if (clazz.isAssignableFrom(meta.getClass())) {
                    tags.add(tag);
                }
            }
        }
        return tags;
    }

    public static List<ItemTag> getTagList() {
        return new ArrayList<>(tags.values());
    }

    public static Map<String, ItemTag> getTags() {
        return tags;
    }

}
