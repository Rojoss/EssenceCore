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

import org.essencemc.essencecore.EssenceCore;

public enum Message {
    //Main messages
    PREFIX(MsgCat.GENERAL, "&8[&4Essence&8] &6"),
    NO_PERM(MsgCat.GENERAL, "&cInsufficient permissions! &8'&7{0}&8'"),
    DEAD_PLAYER(MsgCat.GENERAL, "&4{0} &cis dead."),

    //Argument parsing messages
    INVALID_OPTIONAL_ARGUMENT(MsgCat.VALIDATION, "&cThe argument &4{0} &cneeds to be a &4{1}&c! &7You specified &8'&c{2}&8'&c."),
    INVALID_PLAYER(MsgCat.VALIDATION, "&4{0} &cis not a valid player name or UUID."),
    INVALID_WORLD(MsgCat.VALIDATION, "&4{0} &cis not a valid world name, ID or UUID."),
    NUMBER_TOO_LOW(MsgCat.VALIDATION, "&4{0} &cis too low! &7It can't be less than &c{1}&7."),
    NUMBER_TOO_HIGH(MsgCat.VALIDATION, "&4{0} &cis too high! &7It can't be more than &c{1}&7."),
    INVALID_LIST_ARGUMENT(MsgCat.VALIDATION, "&4{0} &cis not a valid value! &7Values: &8{1}&7."),
    INVALID_LOCATION(MsgCat.VALIDATION, "&4{0} &cis not a valid location!"),
    NOT_A_NUMBER(MsgCat.VALIDATION, "&4{0} &cis not a number! &7Any whole number which can be negative."),
    NOT_A_DECIMAL(MsgCat.VALIDATION, "&4{0} &cis not a number! &7Any decimal number which can be negative."),
    NOT_A_BOOLEAN(MsgCat.VALIDATION, "&4{0} &cis not a boolean! &7true or false"),
    NOT_A_VECTOR(MsgCat.VALIDATION, "&4{0} &cis not a vector! &7xxx;yy;zz values can be decimals."),
    NOT_A_MATERIAL(MsgCat.VALIDATION, "&4{0} &cis not a material! &7stone, wool:10 or 264."),
    CANT_USE_RELATIVE_COORDS(MsgCat.VALIDATION, "&cYou can't use relative coordinates in the command line!"),
    NO_STRING_MATCH(MsgCat.VALIDATION, "&4{0} &cdoesn't match with &4{1}&c."),
    DOESNT_START_WITH(MsgCat.VALIDATION, "&4{0} &cdoesn't start with &4{1}&c."),
    DOESNT_END_WITH(MsgCat.VALIDATION, "&4{0} &cdoesn't end with &4{1}&c."),
    TOO_FEW_CHARACTERS(MsgCat.VALIDATION, "&4{0} &cis too short! &7Need at least &8{1} &7characters."),
    TOO_MUCH_CHARACTERS(MsgCat.VALIDATION, "&4{1} &cis too long! &7It can't have more than &8{1} &7characters."),
    NO_ARG_VALUE_NAME(MsgCat.VALIDATION, "&cMissing value for argument &4{0}&c! &7Specify the value after the semicolon!"),
    NO_ARG_VALUE(MsgCat.VALIDATION, "&cMissing argument value after the semicolon!"),
    INVALID_ENITY_TYPE(MsgCat.VALIDATION, "&4{0} &cis not a valid entity type! &7&l/entities &cfor a list!"),
    INVALID_ENTITY_TAG(MsgCat.VALIDATION, "&4{0} &cis not a valid entity tag. &7&l/entities <entity> &cfor a list. "),
    INVALID_ENTITY_TAG_ENTITY(MsgCat.VALIDATION, "&4{0} &ccan not be used for &4{1}&c. &7&l/entities <entity> &cfor a list of tags you can use. "),
    MISSING_XYZ_LOCATION(MsgCat.VALIDATION, "&4{0} &cis not a valid location! &7You have to set the x,y,z values."),
    MISSING_WORLD_LOCATION(MsgCat.VALIDATION, "&4{0} &cis not a valid location! &7You have to specify a world or player after x,y,z:"),
    ENTITY_NO_LOCATION(MsgCat.VALIDATION, "&cMissing location for entity."),
    INVALID_PLACEHOLDER_VALUE(MsgCat.VALIDATION, "&c&o&mundefined"),

    //Item parser error messages
    PARSER_VALID(MsgCat.ITEM_PARSER, "&6This is a valid item."),
    PARSER_NO_ITEM_SPECIFIED(MsgCat.ITEM_PARSER, "&cNo item specified!"),
    PARSER_INVALID_ITEM(MsgCat.ITEM_PARSER, "&cThe item &4{0} &cis not a valid item!"),
    PARSER_INVALID_AMOUNT(MsgCat.ITEM_PARSER, "&c4{0} &cis not a valid amount!"),
    NO_META_VALUE(MsgCat.ITEM_PARSER, "No value specified for &4{0}:&c!"),
    PARSER_INVALID_COLOR(MsgCat.ITEM_PARSER, "&4{0} &cis not a valid color!"),
    PARSER_INVALID_DYE_COLOR(MsgCat.ITEM_PARSER, "&4{0} &cis not a valid dye color!"),
    PARSER_INVALID_SHAPE(MsgCat.ITEM_PARSER, "&4{0} &cis not a valid firework shape!"),
    PARSER_MISSING_FIREWORK_SHAPE(MsgCat.ITEM_PARSER, "&cTo create a firework effect, you need to specify the shape!"),
    PARSER_MISSING_FIREWORK_COLOR(MsgCat.ITEM_PARSER, "&cTo create a firework effect, you need to set at least one color!"),
    PARSER_ENCHANT_VALUE(MsgCat.ITEM_PARSER, "&4{0} &cis not a valid enchantment level."),
    PARSER_POTION_VALUE(MsgCat.ITEM_PARSER, "&4{0} &cis not a valid potion effect value. It should be {duration}.{amplifier}&c."),

    //Main command messages
    CMD_PLAYER_ONLY(MsgCat.COMMAND_OTHER, "&cThis command can only be executed by players."),
    CMD_INVALID_USAGE(MsgCat.COMMAND_OTHER, "&cInvalid usage! &7Command syntax: &8{0}&7."),
    CMD_HELP_ESSENCE(MsgCat.COMMAND_OTHER, "&8===== &4&l{cmd} &8=====\n&7&o{desc}\n&6Usage&8: &7{usage}\n&6Base permission&8: &7{perm}\n&6Aliases&8: &7{aliases}\n" +
            "&6Modifiers&8: &7{modifiers}\n&6Optional args&8: &7{opt-args}\n&6Options&8: &7{options}"),
    CMD_HELP_SEPARATOR(MsgCat.COMMAND_OTHER, "&8, &7"),
    CMD_HELP_NONE(MsgCat.COMMAND_OTHER, "None"),
    CMD_HELP_MODIFIER(MsgCat.COMMAND_OTHER, "{{&7&o{1}}&7{0}}"),
    CMD_HELP_OPT_ARG(MsgCat.COMMAND_OTHER, "{{&7&o{1}}&7{0}}"),
    CMD_HELP_OPTION(MsgCat.COMMAND_OTHER, "{{&7&o{1}}&7{0}&8(&a{2}&8)}"),
    CMD_HELP_ARG(MsgCat.COMMAND_OTHER, "{{&7&o{1}}&7{0}}"),
    CMD_LINK_CONFLICT(MsgCat.COMMAND_OTHER, "&cYou can't use &4{0} &ctogether with &4{1}&c!"),
    CMD_LINK_LINK(MsgCat.COMMAND_OTHER, "&cTo use &4{0} &cyou have to specify &4{1}&c!"),
    //Command modifiers
    MOD_HELP(MsgCat.COMMAND_MODIFIERS, "Show detailed command information."),
    MOD_SILENT(MsgCat.COMMAND_MODIFIERS, "Don't send any messages."),

    //Argument descriptions
    ARG_NO_DESC(MsgCat.COMMAND_ARG, "&cNo description available..."),
    ARG_PLAYER(MsgCat.COMMAND_ARG, "&7The name, UUID or nickname of an online player."),
    ARG_OFFLINE_PLAYER(MsgCat.COMMAND_ARG, "&7The name, UUID or nickname of a player."),
    ARG_BOOL(MsgCat.COMMAND_ARG, "&7Either &atrue &7or &cfalse. &8(&ay&8,&cn&8, &av&8,&cx&8, &ayes&8,&cno&8)"),
    ARG_DECIMAL(MsgCat.COMMAND_ARG, "&7Any number which can have decimals. &8Example: &76.9"),
    ARG_DECIMAL_MIN_MAX(MsgCat.COMMAND_ARG, "&7Any number between &a{0} &7and &a{1} &7which can have decimals. &8Example: &76.9&8."),
    ARG_DECIMAL_MIN(MsgCat.COMMAND_ARG, "&7Any number above &a{0} &7which can have decimals. &8Example: &76.9&8."),
    ARG_DECIMAL_MAX(MsgCat.COMMAND_ARG, "&7Any number below &a{0} &7which can have decimals. &8Example: &76.9&8."),
    ARG_INT(MsgCat.COMMAND_ARG, "&7Any whole number."),
    ARG_INT_MIN_MAX(MsgCat.COMMAND_ARG, "&7Any whole number between &a{0} &7and &a{1}&7."),
    ARG_INT_MIN(MsgCat.COMMAND_ARG, "&7Any whole number above &a{0}&7."),
    ARG_INT_MAX(MsgCat.COMMAND_ARG, "&7Any whole number below &a{0}&7."),
    ARG_LIST(MsgCat.COMMAND_ARG, "&7Any of these values&8: &a{0}&7."),
    ARG_LOCATION(MsgCat.COMMAND_ARG, "&7A location &8(&ax,y,z:world&7, &a@player&7, &a~x,~y,~z:@player&8)&7."),
    ARG_STRING(MsgCat.COMMAND_ARG, "&7Any string without spaces."),
    ARG_STRING_MATCH(MsgCat.COMMAND_ARG, "&7The value needs to match &a{0}&7."),
    ARG_STRING_START(MsgCat.COMMAND_ARG, "&7The value needs to start with &a{0}&7."),
    ARG_STRING_END(MsgCat.COMMAND_ARG, "&7The value needs to end with &a{0}&7."),
    ARG_STRING_START_END(MsgCat.COMMAND_ARG, "&7The value needs to start with &a{0} &7and end with &a{0}&7."),
    ARG_STRING_MIN_MAX(MsgCat.COMMAND_ARG, "&7Any string with &a{0} &7to &a{1} &7characters."),
    ARG_STRING_MIN(MsgCat.COMMAND_ARG, "&7Any string with at least &a{0} &7characters."),
    ARG_STRING_MAX(MsgCat.COMMAND_ARG, "&7Any string with fewer than &a{0} &7characters."),
    ARG_WORLD(MsgCat.COMMAND_ARG, "&7A world name, UUID or ID."),
    ARG_VECTOR(MsgCat.COMMAND_ARG, "&7A vector with a x y z value. &8Example: &71;5.5;-1&8."),
    ARG_ITEM(MsgCat.COMMAND_ARG, "&7An item string like you would use in /item surrounded with quotes. &8Example: &7\"diamondsword 1 sharpness:1\"&8."),
    ARG_MATERIAL(MsgCat.COMMAND_ARG, "&7A material string with optional data &8Example: &7stone, wool:10 or 264&8."),
    ;

    private EMessage message;

    Message(MsgCat category, String defaultMsg) {
        message = new EMessage(category, this.toString(), defaultMsg, EssenceCore.inst().getMessages());
    }

    public EMessage msg() {
        return message;
    }

    public static EMessage fromString(String name) {
        name = name.toLowerCase().replace("_", "");
        name = name.toLowerCase().replace("-", "");
        for (Message msg : values()) {
            if (msg.toString().toLowerCase().replace("_", "").equals(name)) {
                return msg.msg();
            }
        }
        return null;
    }
}
