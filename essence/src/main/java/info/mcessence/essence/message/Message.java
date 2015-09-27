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

package info.mcessence.essence.message;

public enum Message {
    //Main messages
    PREFIX(MsgCat.GENERAL, "&8[&4Essence&8] &6"),
    NO_PERM(MsgCat.GENERAL, "&cInsuficcient permissions! &8'&7{0}&8'"),
    DEAD_PLAYER(MsgCat.GENERAL, "&4{0} &cis dead."),

    //Argument parsing messages
    INVALID_OPTIONAL_ARGUMENT(MsgCat.VALIDATION, "&cThe argument &4{0} &cneeds to be a &4{1}&c! &7You specified &8'&c{2}&8'"),
    INVALID_PLAYER(MsgCat.VALIDATION, "&4{0} &cis not a valid player name or uuid."),
    INVALID_WORLD(MsgCat.VALIDATION, "&4{0} &cis not a valid world name, id or uuid."),
    NUMBER_TOO_LOW(MsgCat.VALIDATION, "&4{0} &cis too low! &7Can't be less than &c{1}&7."),
    NUMBER_TOO_HIGH(MsgCat.VALIDATION, "&4{0} &cis too high! &7Can't be more than &c{1}&7."),
    INVALID_LIST_ARGUMENT(MsgCat.VALIDATION, "&4{0} &cis not a valid value! &7Values: &8{1}"),
    INVALID_LOCATION(MsgCat.VALIDATION, "&4{0} &cis not a valid location!"),
    NOT_A_NUMBER(MsgCat.VALIDATION, "&4{0} &cis not a number! &7Any whole number which can be negative."),
    NOT_A_DECIMAL(MsgCat.VALIDATION, "&4{0} &cis not a number! &7Any decimal number which can be negative."),
    NOT_A_BOOLEAN(MsgCat.VALIDATION, "&4{0} &cis not a boolean! &7true or false"),
    NOT_A_VECTOR(MsgCat.VALIDATION, "&4{0} &cis not a vector! &7xxx;yy;zz values can be decimals."),
    NOT_A_MATERIAL(MsgCat.VALIDATION, "&4{0} &cis not a material! &7stone, wool:10 or 264"),
    CANT_USE_RELATIVE_COORDS(MsgCat.VALIDATION, "&cYou can't use relative coordinates in the command line!"),
    NO_STRING_MATCH(MsgCat.VALIDATION, "&4{0} &cdoesn't match with &4{1}&c."),
    DOESNT_START_WITH(MsgCat.VALIDATION, "&4{0} &cdoesn't start with &4{1}&c."),
    DOESNT_END_WITH(MsgCat.VALIDATION, "&4{0} &cdoesn't end with &4{1}&c."),
    TOO_FEW_CHARACTERS(MsgCat.VALIDATION, "&4{0} &cis too short! &7Need at least &8{1} &7characters."),
    TOO_MUCH_CHARACTERS(MsgCat.VALIDATION, "&4{1} &cis too long! &7Can't have more than &8{1} &7characters."),
    NO_ARG_VALUE_NAME(MsgCat.VALIDATION, "&cMissing value for argument &4{0}&c! &7Specify the value after the semicolon!"),
    NO_ARG_VALUE(MsgCat.VALIDATION, "&cMissing argument value after the semicolon!"),
    INVALID_ENITY_TYPE(MsgCat.VALIDATION, "&4{0} &cis not a valid entity type! &7&l/entities &cfor a list!"),

    //Item parser error messages
    PARSER_VALID(MsgCat.ITEM_PARSER, "&6This is a valid item."),
    PARSER_NO_ITEM_SPECIFIED(MsgCat.ITEM_PARSER, "&cNo item specified!"),
    PARSER_INVALID_ITEM(MsgCat.ITEM_PARSER, "&cThe item &4{0} &cis not a valid item!"),
    PARSER_INVALID_AMOUNT(MsgCat.ITEM_PARSER, "&c4{0} &cis not a valid item amount!"),
    NO_META_VALUE(MsgCat.ITEM_PARSER, "No value specified for &4{0}:&c!"),
    PARSER_INVALID_COLOR(MsgCat.ITEM_PARSER, "&4{0} &cis not a valid color!"),
    PARSER_INVALID_DYE_COLOR(MsgCat.ITEM_PARSER, "&4{0} &cis not a valid dye color!"),
    PARSER_INVALID_SHAPE(MsgCat.ITEM_PARSER, "&4{0} &cis not a valid firework shape!"),
    PARSER_MISSING_FIREWORK_SHAPE(MsgCat.ITEM_PARSER, "&cTo create a firework effect you need to specify the shape!"),
    PARSER_MISSING_FIREWORK_COLOR(MsgCat.ITEM_PARSER, "&cTo create a firework effect you need to set at least one color!"),
    PARSER_ENCHANT_VALUE(MsgCat.ITEM_PARSER, "&4{0} &cis not a valid enchantment level."),
    PARSER_POTION_VALUE(MsgCat.ITEM_PARSER, "&4{0} &cis not a valid potion effect value. It should be {duration}.{amplifier}"),

    //Main command messages
    CMD_PLAYER_ONLY(MsgCat.COMMAND_OTHER, "&cThis command can only be executed by players."),
    CMD_INVALID_USAGE(MsgCat.COMMAND_OTHER, "&cInvalid usage! &7Command syntax: &8{0}"),
    CMD_HELP_ESSENCE(MsgCat.COMMAND_OTHER, "&8===== &4&l{cmd} &8=====\n&7&o{desc}\n&6Usage&8: &7{usage}\n&6Base permission&8: &7{perm}\n&6Aliases&8: &7{aliases}\n" +
            "&6Modifiers&8: &7{modifiers}\n&6Optional args&8: &7{opt-args}\n&6Options&8: &7{options}"),
    CMD_HELP_SEPARATOR(MsgCat.COMMAND_OTHER, "&8, &7"),
    CMD_HELP_NONE(MsgCat.COMMAND_OTHER, "None"),
    CMD_HELP_MODIFIER(MsgCat.COMMAND_OTHER, "{{&7&o{1}}&7{0}}"),
    CMD_HELP_OPT_ARG(MsgCat.COMMAND_OTHER, "{{&7&o{1}}&7{0}}"),
    CMD_HELP_OPTION(MsgCat.COMMAND_OTHER, "{{&7&o{1}}&7{0}&8(&a{2}&8)}"),
    CMD_HELP_ARG(MsgCat.COMMAND_OTHER, "{{&7&o{1}}&7{0}}"),

    //Command messages
    CMD_ESSENCE_INFO(MsgCat.COMMAND, "&8===== &4&lEssence plugin &8=====\n&8&o{0}\n&6Version&8: &7{1}\n&6Website&8: &9{2}\n&6Authors&8: &7{3}"),
    CMD_ESSENCE_RELOAD(MsgCat.COMMAND, "Configs and commands reloaded."),
    CMD_HEAL_HEALED(MsgCat.COMMAND, "You have been healed!"),
    CMD_HEAL_OTHER(MsgCat.COMMAND, "You have healed &a{0}&6."),
    CMD_FEED_FEEDED(MsgCat.COMMAND, "You have been feeded!"),
    CMD_FEED_OTHER(MsgCat.COMMAND, "You have fed &a{0}&6."),
    CMD_GAMEMODE_CHANGED(MsgCat.COMMAND, "Gamemode changed to &a{0}&6."),
    CMD_GAMEMODE_OTHER(MsgCat.COMMAND, "You have changed &a{0}'s &6gamemode to &a{1}&6."),
    CMD_LIGHTNING(MsgCat.COMMAND, "Lightning has struck!"),
    CMD_WARP_SET(MsgCat.COMMAND, "Warp &a{0} &6set!"),
    CMD_WARP_DELETED(MsgCat.COMMAND, "Warp &a{0} &6deleted!"),
    CMD_WARP_DELETED_AlL(MsgCat.COMMAND, "All warps have been deleted!"),
    CMD_WARP_INVALID(MsgCat.COMMAND, "&cNo warp found with the name &4{0}&c!"),
    CMD_WARPS(MsgCat.COMMAND, "&6&lWarps&8&l: &7{0}"),
    CMD_WARPS_NONE(MsgCat.COMMAND, "No warps set yet!"),
    CMD_WARP_USE(MsgCat.COMMAND, "Warping to &a{0}&6..."),
    CMD_WARP_OTHER(MsgCat.COMMAND, "You have send &a{0} &6to the warp &a{1}&6."),
    CMD_TP(MsgCat.COMMAND, "&6Teleported to &a{0}&6."),
    CMD_TP_OTHER(MsgCat.COMMAND, "&6You have teleported &a{1} &6to &a{0}&6."),
    CMD_NICK_CHANGED(MsgCat.COMMAND, "&6Nickname changed to &r{0}"),
    CMD_NICK_OTHER(MsgCat.COMMAND, "&6You have changed &a{1}'s &6nickname to &r{0}&6."),
    CMD_REMOVEEFFECT(MsgCat.COMMAND, "&6Removed &a{0} &6potion effect."),
    CMD_REMOVEEFFECT_ALL(MsgCat.COMMAND, "&6All potion effects removed."),
    CMD_REMOVEEFFECT_OTHER(MsgCat.COMMAND, "&6Removed &a{0}&6's &a{1} &6potion effect."),
    CMD_REMOVEEFFECT_OTHER_ALL(MsgCat.COMMAND, "&6All of &a{0}'s potion effects have been removed."),
    CMD_BURN(MsgCat.COMMAND, "&6You will burn for &a{0} &6seconds."),
    CMD_BURN_OTHER(MsgCat.COMMAND, "&a{0} &6will burn for &a{1} &6seconds."),
    CMD_FLY(MsgCat.COMMAND, "&6Flight state: &a{0}"),
    CMD_FLY_OTHER(MsgCat.COMMAND, "&a{0}&6's flight state: &a{1}"),
    CMD_WALKSPEED(MsgCat.COMMAND, "&6Your walking speed is now &a{0}"),
    CMD_WALKSPEED_OTHER(MsgCat.COMMAND, "&a{0}&6's walking speed is now &a{1}"),
    CMD_FLYSPEED(MsgCat.COMMAND, "&6Your flying speed is now &a{0}"),
    CMD_FLYSPEED_OTHER(MsgCat.COMMAND, "&a{0}&6's flying speed is now &a{1}"),
    CMD_SUICIDE(MsgCat.COMMAND, "&a{0} &6has decided to take his own life."),
    CMD_KILL(MsgCat.COMMAND, "&6You killed &a{0}."),
    CMD_KILL_EXEMPT(MsgCat.COMMAND, "&cYou cannot kill &a{0}&c."),
    CMD_TREE(MsgCat.COMMAND, "&6A tree has been generated."),
    CMD_TREE_FAILURE(MsgCat.COMMAND, "&cA tree cannot be generated there."),
    CMD_INVSEE(MsgCat.COMMAND, "&6You're now viewing &a{0}&6's inventory."),
    CMD_INVSEE_EXEMPT(MsgCat.COMMAND, "&cYou cannot view &a{0}&c's inventory."),
    CMD_ENDERCHEST(MsgCat.COMMAND, "&6You are viewing your enderchest."),
    CMD_ENDERCHEST_OTHER(MsgCat.COMMAND, "&cYou cannot view &a{0}&c's enderchest."),
    CMD_TPHERE(MsgCat.COMMAND, "&6You have teleported &a{0} &6here."),
    CMD_SUDO(MsgCat.COMMAND, "&6You made &a{0} &6run &a{1}&c."),
    CMD_SUMMON(MsgCat.COMMAND, "&6Entitie(s) summoned!"),


    //Command modifiers
    MOD_HELP(MsgCat.COMMAND_MODIFIERS, "Show detailed command information"),
    MOD_SILENT(MsgCat.COMMAND_MODIFIERS, "Don't send any messages"),
    MOD_HEAL_ONLY(MsgCat.COMMAND_MODIFIERS, "Only modify the health limited by max health"),
    MOD_HEAL_MAX_ONLY(MsgCat.COMMAND_MODIFIERS, "Only modify the max health"),
    MOD_DELWARP_ALL(MsgCat.COMMAND_MODIFIERS, "Delete all warps"),
    MOD_NICK_REMOVE(MsgCat.COMMAND_MODIFIERS, "Remove your nickname."),
    MOD_REMOVEEFFECT_NEGATIVE(MsgCat.COMMAND_MODIFIERS, "Will ignore all positive potion effects and only remove the negative ones"),
    MOD_REMOVEEFFECT_POSITIVE(MsgCat.COMMAND_MODIFIERS, "Will ignore all negative potion effects and only remove the positive ones"),
    MOD_BURN_INCREMENT(MsgCat.COMMAND_MODIFIERS, "Increment the duration if the player is already burning"),
    MOD_GOD_RESET(MsgCat.COMMAND_MODIFIERS, "Reset any remaining effects like fire ticks, negative potion effects and so on"),
    MOD_RIDE_ENTITY(MsgCat.COMMAND_MODIFIERS, "Ride on top of the summoned entity."),

    //Command options/optional arguments
    OPT_HEAL_FEED(MsgCat.COMMAND_OPTIONS, "Restore hunger?"),
    OPT_HEAL_CLEAR_EFFECTS(MsgCat.COMMAND_OPTIONS, "Remove all active potion effects?"),
    OPT_HEAL_EXTINGUISH(MsgCat.COMMAND_OPTIONS, "Remove remaining fire ticks?"),
    OPT_FEED_SATURATION(MsgCat.COMMAND_OPTIONS, "The amount of saturation given."),
    OPT_FEED_EXHAUSTION(MsgCat.COMMAND_OPTIONS, "Reset exhaustion?"),
    OPT_WARP_PERM_BASED(MsgCat.COMMAND_OPTIONS, "Should warps be permissions based? Like essence.warp.spawn to use /warp spawn"),
    OPT_NICK_PREFIX(MsgCat.COMMAND_OPTIONS, "Prefix added in front of all nicknames."),
    OPT_NICK_MIN_CHARS(MsgCat.COMMAND_OPTIONS, "Minimum amount of characters required. (exclusive prefix)"),
    OPT_NICK_MAX_CHARS(MsgCat.COMMAND_OPTIONS, "Maximum amount of characters allowed. (exclusive prefix)"),
    OPT_BURN_TICKS(MsgCat.COMMAND_OPTIONS, "Change the time from seconds to ticks for more precision. (20 ticks per second)"),
    OPT_ALLOW_FLY(MsgCat.COMMAND_OPTIONS, "If true it will allow the player to keep toggling flying by double tapping space. If false the player can't start flying when double tapping space"),
    OPT_NO_HUNGER_LOSS(MsgCat.COMMAND_OPTIONS, "If enabled you wont lose hunger while in god mode"),
    OPT_NO_DAMAGE(MsgCat.COMMAND_OPTIONS, "If enabled you wont be able to damage other entities wile in god mode"),
    //OPT_FLYING(MsgCat.COMMAND_OPTIONS, "If true it will set the player to currently flying. If false it will stop flight"),

    //Argument descriptions
    ARG_NO_DESC(MsgCat.COMMAND_ARG, "&cNo description available..."),
    ARG_PLAYER(MsgCat.COMMAND_ARG, "&7The name, uuid or nickname of an online player."),
    ARG_OFFLINE_PLAYER(MsgCat.COMMAND_ARG, "&7The name, uuid or nickname of a player."),
    ARG_BOOL(MsgCat.COMMAND_ARG, "&7Either &atrue &7or &cfalse. &8(&ay&8,&cn&8, &av&8,&cx&8, &ayes&8,&cno&8)"),
    ARG_DECIMAL(MsgCat.COMMAND_ARG, "&77Any number which can have decimals. &8Example: &76.9"),
    ARG_DECIMAL_MIN_MAX(MsgCat.COMMAND_ARG, "&77Any number between &a{0} &7and &a{1} &7which can have decimals. &8Example: &76.9"),
    ARG_DECIMAL_MIN(MsgCat.COMMAND_ARG, "&77Any number above &a{0} &7which can have decimals. &8Example: &76.9"),
    ARG_DECIMAL_MAX(MsgCat.COMMAND_ARG, "&77Any number below &a{0} &7which can have decimals. &8Example: &76.9"),
    ARG_INT(MsgCat.COMMAND_ARG, "&7Any whole number."),
    ARG_INT_MIN_MAX(MsgCat.COMMAND_ARG, "&7Any whole number between &a{0} &7and &a{1}&7."),
    ARG_INT_MIN(MsgCat.COMMAND_ARG, "&7Any whole number above &a{0}&7."),
    ARG_INT_MAX(MsgCat.COMMAND_ARG, "&7Any whole number below &a{0}&7."),
    ARG_LIST(MsgCat.COMMAND_ARG, "&7Any of these values&8: &a{0}"),
    ARG_LOCATION(MsgCat.COMMAND_ARG, "&7A location &8(&ax,y,z:world&7, &a@player&7, &a~x,~y,~z:@player&8)"),
    ARG_STRING(MsgCat.COMMAND_ARG, "&7Any string without spaces."),
    ARG_STRING_MATCH(MsgCat.COMMAND_ARG, "&7The value needs to match &a{0}"),
    ARG_STRING_START(MsgCat.COMMAND_ARG, "&7The value needs to start with &a{0}"),
    ARG_STRING_END(MsgCat.COMMAND_ARG, "&7The value needs to end with &a{0}"),
    ARG_STRING_START_END(MsgCat.COMMAND_ARG, "&7The value needs to start with &a{0} &7and end with &a{0}"),
    ARG_STRING_MIN_MAX(MsgCat.COMMAND_ARG, "&7Any string with &a{0} &7to &a{1} &7characters."),
    ARG_STRING_MIN(MsgCat.COMMAND_ARG, "&7Any string with at least &a{0} &7characters."),
    ARG_STRING_MAX(MsgCat.COMMAND_ARG, "&7Any string with less than &a{0} &7characters."),
    ARG_WORLD(MsgCat.COMMAND_ARG, "&7A world name, uuid or id."),
    ARG_VECTOR(MsgCat.COMMAND_ARG, "&7A vector with a x y z value. &8Example: &71;5.5;-1"),
    ARG_ITEM(MsgCat.COMMAND_ARG, "&7An item string like you would use in /item surounded with quotes. &8Example: &7\"diamondsword 1 sharpness:1\""),
    ARG_MATERIAL(MsgCat.COMMAND_ARG, "&7A material string with optional data &8Example: &7stone, wool:10 or 264"),
    ;

    private EMessage message;

    Message(MsgCat category, String defaultMsg) {
        message = new EMessage(category, this.toString(), defaultMsg);
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
