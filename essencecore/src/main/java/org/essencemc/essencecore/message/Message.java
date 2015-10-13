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

package org.essencemc.essencecore.message;

import org.bukkit.entity.Player;
import org.essencemc.essencecore.EssenceCore;

public enum Message {
    //Main messages
    PREFIX(MsgCat.GENERAL, "&8[&4Essence&8]"),
    NO_PERM(MsgCat.GENERAL, "{p} &cInsufficient permissions! &8'&7{perm}&8'"),
    DEAD_PLAYER(MsgCat.GENERAL, "{p} &4{player} &cis dead."),
    MODULE_DISABLED(MsgCat.GENERAL, "{p} &cThe &4{module} &cis disabled."),

    //Argument parsing messages
    INVALID_ARGUMENT_TYPE(MsgCat.VALIDATION, "{p} &4{input} &cis not a valid argument type."),
    INVALID_PLAYER(MsgCat.VALIDATION, "{p} &4{input} &cis not a valid player name or UUID."),
    INVALID_WORLD(MsgCat.VALIDATION, "{p} &4{input} &cis not a valid world name, ID or UUID."),
    NUMBER_TOO_LOW(MsgCat.VALIDATION, "{p} &4{input} &cis too low! &7It can't be less than &c{min}&7."),
    NUMBER_TOO_HIGH(MsgCat.VALIDATION, "{p} &4{input} &cis too high! &7It can't be more than &c{max}&7."),
    INVALID_LIST_ARGUMENT(MsgCat.VALIDATION, "{p} &4{input} &cis not a valid value! &7Values: &b{values}&7."),
    INVALID_LOCATION(MsgCat.VALIDATION, "{p} &4{input} &cis not a valid location!"),
    NOT_A_NUMBER(MsgCat.VALIDATION, "{p} &4{input} &cis not a number! &7Any whole number which can be negative."),
    NOT_A_DECIMAL(MsgCat.VALIDATION, "{p} &4{input} &cis not a number! &7Any decimal number which can be negative."),
    NOT_A_BOOLEAN(MsgCat.VALIDATION, "{p} &4{input} &cis not a boolean! &7true or false"),
    NOT_A_VECTOR(MsgCat.VALIDATION, "{p} &4{input} &cis not a vector! &7xxx,yy,zz values can be decimals."),
    NOT_A_MATERIAL(MsgCat.VALIDATION, "{p} &4{input} &cis not a material! &7stone, wool:10 or 264."),
    CANT_USE_RELATIVE_COORDS(MsgCat.VALIDATION, "{p} &cYou can't use relative coordinates in the command line!"),
    NO_STRING_MATCH(MsgCat.VALIDATION, "{p} &4{input} &cdoesn't match with &4{match}&c."),
    TOO_FEW_CHARACTERS(MsgCat.VALIDATION, "{p} &4{input} &cis too short! &7Need at least &8{min} &7characters."),
    TOO_MUCH_CHARACTERS(MsgCat.VALIDATION, "{p} &4{input} &cis too long! &7It can't have more than &8{max} &7characters."),
    NO_ARG_VALUE_NAME(MsgCat.VALIDATION, "{p} &cMissing value for argument &4{arg}&c! &7Specify the value after the semicolon!"),
    NO_ARG_VALUE(MsgCat.VALIDATION, "{p} &cMissing argument value after the semicolon!"),
    INVALID_ENTITY(MsgCat.VALIDATION, "{p} &4{input} is not a valid entity!"),
    INVALID_ENITY_TYPE(MsgCat.VALIDATION, "{p} &4{input} &cis not a valid entity type! &7&l/entities &cfor a list!"),
    INVALID_ENTITY_TAG(MsgCat.VALIDATION, "{p} &4{input} &cis not a valid entity tag. &7&l/entities <entity> &cfor a list. "),
    INVALID_ENTITY_TAG_ENTITY(MsgCat.VALIDATION, "{p} &4{input} &ccan not be used for &4{entity}&c. &7&l/entities <entity> &cfor a list of tags you can use. "),
    MISSING_XYZ_LOCATION(MsgCat.VALIDATION, "{p} &4{input} &cis not a valid location! &7You have to set the x,y,z values."),
    MISSING_WORLD_LOCATION(MsgCat.VALIDATION, "{p} &4{input} &cis not a valid location! &7You have to specify a world or player after x,y,z:"),
    ENTITY_NO_LOCATION(MsgCat.VALIDATION, "{p} &cMissing location for entity."),
    INVALID_PLACEHOLDER_VALUE(MsgCat.VALIDATION, "&c&o&mundefined"),
    INVALID_DURATION(MsgCat.VALIDATION, "{p} &4{input} &cis not a valid duration! &7Error found at &c{section}&7."),
    INVALID_DURATION_NOT_ZERO(MsgCat.VALIDATION, "{p} &4{input} &cis not a valid duration! &7Value can't be zero."),

    //Item parser error messages
    PARSER_VALID(MsgCat.ITEM_PARSER, "{p} &6This is a valid item."),
    PARSER_NO_ITEM_SPECIFIED(MsgCat.ITEM_PARSER, "{p} &cNo item specified!"),
    UNKNOWN_ITEM_TAG(MsgCat.ITEM_PARSER, "{p} &cNo meta tag found for &4{input}&c."),
    INVALID_ITEM_TAG(MsgCat.ITEM_PARSER, "{p} &cThe meta tag &4{input} &ccan't be applied to this item."),
    PARSER_INVALID_ITEM(MsgCat.ITEM_PARSER, "{p} &cThe item &4{input} &cis not a valid item!"),
    PARSER_INVALID_AMOUNT(MsgCat.ITEM_PARSER, "{p} &c4{input} &cis not a valid amount!"),
    NO_META_VALUE(MsgCat.ITEM_PARSER, "{p} &cNo value specified for meta &4{meta}&c!"),
    PARSER_INVALID_COLOR(MsgCat.ITEM_PARSER, "{p} &4{input} &cis not a valid color!"),
    PARSER_INVALID_DYE_COLOR(MsgCat.ITEM_PARSER, "{p} &4{input} &cis not a valid dye color!"),
    PARSER_INVALID_SHAPE(MsgCat.ITEM_PARSER, "{p} &4{input} &cis not a valid firework shape!"),
    PARSER_MISSING_FIREWORK_SHAPE(MsgCat.ITEM_PARSER, "{p} &cTo create a firework effect, you need to specify the shape!"),
    PARSER_MISSING_FIREWORK_COLOR(MsgCat.ITEM_PARSER, "{p} &cTo create a firework effect, you need to set at least one color!"),
    PARSER_ENCHANT_VALUE(MsgCat.ITEM_PARSER, "{p} &4{input} &cis not a valid enchantment level."),
    PARSER_POTION_VALUE(MsgCat.ITEM_PARSER, "{p} &4{input} &cis not a valid potion effect value. It should be {duration}.{amplifier}&c."),

    META_NAME(MsgCat.META, "&7The display name for the item.\n&7Use underscores for spaces."),
    META_LORE(MsgCat.META, "&7The lore for the item displayed when hovering over the item.\n&7Use underscores for spaces and | for new lines."),
    META_ENCHANT(MsgCat.META, "&7Any of the enchantments as key and level as value.\n&7For example: &8sharpness:1 unbreaking:5"),
    META_POTION(MsgCat.META, "&7Any of the potion effects as key and then duration.amplifier.\n&7The duration is in seconds and the amplifier is the strength.\n&7For example: &8regeneration:3.1 speed:5.3"),
    META_COLOR(MsgCat.META, "&7The color for leather armor.\n&7The value can be &cr&8,&ag&8,&9b &7or &8#&crr&agg&9bb&8(hex)&7."),
    META_BOOK(MsgCat.META, "&7Set the book contents based on a book text file."),
    META_AUTHOR(MsgCat.META, "&7Set the author of the book.\n&7Use underscores for spaces."),
    META_TITLE(MsgCat.META, "&7Set the title of the book.\n&7Use underscores for spaces."),
    META_EDITABLE(MsgCat.META, "&7Make the book editable or signed."),
    META_BASECOLOR(MsgCat.META, "&7Set the base color of the banner.\n&7The value must be one of the dye colors."),
    META_PATTERN(MsgCat.META, "&7Any of the patterns as key and the color as value.\n&7The color/value must be one of the dye colors."),
    META_PLAYER(MsgCat.META, "&7Set the skull owner for the skull.\n&7This will set the texture of the skull to the specified players skull."),
    META_TEXTURE(MsgCat.META, "&7Set the skull texture for the skull.\n&7Look on the wiki for more information as this is quite advanced!"),
    META_POWER(MsgCat.META, "&7Set the firework rocket launching power."),
    META_SHAPE(MsgCat.META, "&7Set the shape of the firework effect.\n&7The shape can be: &8creeper, ball, balllarge, burst or star"),
    META_TWINKLE(MsgCat.META, "&7Add a twinkle effect to the firework effect."),
    META_TRAIL(MsgCat.META, "&7Add a trail effect to the firework effect."),
    META_COLORS(MsgCat.META, "&7Set the colors for the firework effect.\n&7The colors can be &cr&8,&ag&8,&9b &7or &8#&crr&agg&9bb&8(hex)&7.\n&7Seperate each color with a &7&l: &7like: &8colors:0,0,0:#191919"),
    META_FADE(MsgCat.META, "&7Set the fade colors for the firework effect.\n&7The colors can be &cr&8,&ag&8,&9b &7or &8#&crr&agg&9bb&8(hex)&7.\n&7Seperate each color with a &7&l: &7like: &8colors:0,0,0:#191919"),
    META_GLOW(MsgCat.META, "&7Add a glowing enchant effect to the item."),
    META_HIDEMETA(MsgCat.META, "&7Hide all attributes and meta from the item hover info."),

    //Main command messages
    CMD_PLAYER_ONLY(MsgCat.COMMAND_OTHER, "{p} &cThis command can only be executed by players."),
    CMD_INVALID_USAGE(MsgCat.COMMAND_OTHER, "{p} &cInvalid usage! &7Command syntax: &8{usage}&7."),
    CMD_HELP_ESSENCE(MsgCat.COMMAND_OTHER, "&8===== &4&l{cmd} &8=====\n&7&o{desc}\n&6Usage&8: &7{usage}\n&6Base permission&8: &7{perm}\n&6Aliases&8: &7{aliases}\n" +
            "&6Modifiers&8: &7{modifiers}\n&6Optional args&8: &7{opt-args}\n&6Options&8: &7{options}"),
    CMD_HELP_SEPARATOR(MsgCat.COMMAND_OTHER, "&8, &7"),
    CMD_HELP_NONE(MsgCat.COMMAND_OTHER, "&7&o&mNone"),
    CMD_HELP_MODIFIER(MsgCat.COMMAND_OTHER, "{{&7&o{desc}}&7{modifier}}"),
    CMD_HELP_OPT_ARG(MsgCat.COMMAND_OTHER, "{{&7&o{desc}\n{usage}}&7{arg}&8(&a{default}&8)}"),
    CMD_HELP_OPTION(MsgCat.COMMAND_OTHER, "{{&7&o{desc}\n{usage}}&7{option}&8(&a{value}&8)}"),
    CMD_HELP_ARG(MsgCat.COMMAND_OTHER, "{{&7&o{desc}}&7{arg}}"),
    CMD_LINK_CONFLICT(MsgCat.COMMAND_OTHER, "{p} &cYou can't specify &4{arg1} &ctogether with &4{arg2}&c!"),
    CMD_LINK_LINK(MsgCat.COMMAND_OTHER, "{p} &cTo specify &4{arg1} &cyou have to specify &4{arg2}&c!"),

    //Command modifiers
    MOD_HELP(MsgCat.COMMAND_MODIFIERS, "Show detailed command information."),
    MOD_SILENT(MsgCat.COMMAND_MODIFIERS, "Don't send any messages."),

    //Argument descriptions
    ARG_NO_DESC(MsgCat.COMMAND_ARG, "&cNo description available..."),
    ARG_PLAYER(MsgCat.COMMAND_ARG, "&7The name, UUID or nickname of an online player."),
    ARG_OFFLINE_PLAYER(MsgCat.COMMAND_ARG, "&7The name, UUID or nickname of a player."),
    ARG_BOOL(MsgCat.COMMAND_ARG, "&7Either &atrue &7or &cfalse. &8(&ay&8,&cn&8, &av&8,&cx&8, &ayes&8,&cno&8)"),
    ARG_DECIMAL(MsgCat.COMMAND_ARG, "&7Any number which can have decimals. &8Example: &76.9"),
    ARG_DECIMAL_MIN_MAX(MsgCat.COMMAND_ARG, "&7Any number between &a{min} &7and &a{max}\n&7which can have decimals. &8Example: &76.9&8."),
    ARG_DECIMAL_MIN(MsgCat.COMMAND_ARG, "&7Any number above &a{min} &7which can have decimals. &8Example: &76.9&8."),
    ARG_DECIMAL_MAX(MsgCat.COMMAND_ARG, "&7Any number below &a{max} &7which can have decimals. &8Example: &76.9&8."),
    ARG_INT(MsgCat.COMMAND_ARG, "&7Any whole number."),
    ARG_INT_MIN_MAX(MsgCat.COMMAND_ARG, "&7Any whole number between &a{min} &7and &a{max}&7."),
    ARG_INT_MIN(MsgCat.COMMAND_ARG, "&7Any whole number above &a{min}&7."),
    ARG_INT_MAX(MsgCat.COMMAND_ARG, "&7Any whole number below &a{max}&7."),
    ARG_LIST(MsgCat.COMMAND_ARG, "&7Any of these values&8: &b{values}&7."),
    ARG_LIST_VALUE(MsgCat.COMMAND_ARG, "&7{{&7{aliases}}&b{value}}"),
    ARG_LOCATION(MsgCat.COMMAND_ARG, "&7A location &8(&ax,y,z:world&7, &a@player&7, &a~x,~y,~z:@player&8)&7."),
    ARG_STRING(MsgCat.COMMAND_ARG, "&7Any string without spaces."),
    ARG_STRING_MATCH(MsgCat.COMMAND_ARG, "&7The value needs to match &a{match}&7."),
    ARG_STRING_MIN_MAX(MsgCat.COMMAND_ARG, "&7Any string with &a{min} &7to &a{max} &7characters."),
    ARG_STRING_MIN(MsgCat.COMMAND_ARG, "&7Any string with at least &a{min} &7characters."),
    ARG_STRING_MAX(MsgCat.COMMAND_ARG, "&7Any string with fewer than &a{max} &7characters."),
    ARG_WORLD(MsgCat.COMMAND_ARG, "&7A world name, UUID or ID."),
    ARG_VECTOR(MsgCat.COMMAND_ARG, "&7A vector with a x y z value. &8Example: &71;5.5;-1&8."),
    ARG_ITEM(MsgCat.COMMAND_ARG, "&7An item string like you would use in /item surrounded with quotes.\n&8Example: &7\"diamondsword 1 sharpness:1\"&8."),
    ARG_MATERIAL(MsgCat.COMMAND_ARG, "&7A material string with optional data &8Example: &7stone, wool:10 or 264&8."),
    ARG_ENTITY(MsgCat.COMMAND_ARG, "&7An entity UUID, ID or string used in summon."),
    ARG_DURATION(MsgCat.COMMAND_ARG, "&7A duration in milliseconds or a string.\n&8Example: &71d12h30m10s500ms, 10h5m or 5s."),
    ;

    private EMessage message;

    Message(MsgCat category, String defaultMsg) {
        message = new EMessage(category, this.toString(), defaultMsg, EssenceCore.inst().getMessages());
    }

    public EMessage emsg() {
        return message;
    }

    public EText msg() {
        return message.getText();
    }

    public static EMessage fromString(String name) {
        name = name.toLowerCase().replace("_", "");
        name = name.toLowerCase().replace("-", "");
        for (Message msg : values()) {
            if (msg.toString().toLowerCase().replace("_", "").equals(name)) {
                return msg.emsg();
            }
        }
        return null;
    }
}
