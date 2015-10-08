package org.essencemc.essencecore.placeholders;

import org.bukkit.entity.Player;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.arguments.*;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.message.Message;

import java.util.ArrayList;
import java.util.List;

public class Placeholder {

    /**
     * Parse all placeholders in the given text.
     * Nested placeholders are fully supported as well.
     * It will create a Placeholder instance for each placeholder parsed.
     * This placeholder will trigger a PlaceholderRequestEvent so plugins can modify/set the value.
     * The final string will have all placeholders replaced.
     * @param string The text with placeholders that needs to be parsed.
     * @return The string with all placeholders replaced.
     */
    public static String parse(String string, Player player) {
        return parsePayload(string, player, true);
    }

    private static String parsePayload(String string, Player player, boolean finalize) {
        //If there are no placeholders do nothing.
        if (!string.contains("$")) {
            return string;
        }

        String result = "";

        // The actual parsing:
        String[] words = parseSplitNotInBrackets(string, " ");
        for (String word : words) {
            word = word.trim(); // Remove any weird whitespace
            if (word.startsWith("$") && !word.startsWith("$$")) { // Account for escaping
                // Placeholder

                List<String> data = new ArrayList<String>();
                int leftBracketIndex = word.length();

                if (word.contains("(") && word.contains(")")) {
                    leftBracketIndex = word.indexOf('(');
                    int rightBracketIndex = word.lastIndexOf(')');

                    String[] arguments = parseSplitNotDouble(word.substring(leftBracketIndex + 1, rightBracketIndex).trim(), ',');

                    for (String argument : arguments) {
                        data.add(parsePayload(argument, player, false).replace(",", ",,").replace("$", "$$"));
                    }
                }

                String placeholder = word.substring(1, leftBracketIndex).trim();
                result += getPlaceholderValue(player, placeholder, data).replace(",,", ",").replace("$$", "$") + " ";
            } else {
                // Literal
                result += word.replace(",", ",,").replace("$", "$$") + " ";
            }
        }

        return result.trim().replace(",,", ",").replace("$$", "$"); // Remove the last space
    }

    /**
     * Parses a split of the specified string at the given delimiters, excluding round brackets.
     *
     * @param string    the string to split
     * @param delimiter the delimiter strings
     * @return the split string array
     */
    public static String[] parseSplitNotInBrackets(String string, String... delimiter) {
        List<String> list = new ArrayList<String>();

        int roundBrackets = 0;
        String builder = "";

        for (int count = 0; count < string.length(); count++) {
            char c = string.charAt(count);

            if (c == '(') {
                roundBrackets++;
            } else if (c == ')') {
                roundBrackets--;
            }

            builder += c;
            if (roundBrackets == 0) {
                for (String split : delimiter) {
                    if (builder.endsWith(split)) {
                        list.add(builder.substring(0, builder.length() - split.length()));
                        builder = "";
                        break;
                    }
                }
            }
        }

        if (!builder.isEmpty()) {
            list.add(builder);
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Parses a split of a string with the given char, but not doubles of it
     * @param string the string
     * @param delimiter the delimiter character
     * @return the split string array
     */
    public static String[] parseSplitNotDouble(String string, char delimiter) {
        List<String> list = new ArrayList<String>();
        String builder = "";

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == delimiter) {
                if (string.length() > (i + 1) && string.charAt(i + 1) == c) {
                    builder += c; // Add both in
                    i++;
                } else {
                    list.add(builder);
                    builder = "";
                    continue;
                }
            }

            builder += c;
        }

        if (!builder.isEmpty()) {
            list.add(builder);
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Get the placeholder value by dispatching an event.
     * @param player The player which will be used as default source if there is no source specified.
     * @param placeholder The full placeholder string as {type}{name}
     * @param data List with all argument strings. (data)
     * @return Placeholder value or undefined.
     */
    private static String getPlaceholderValue(Player player, String placeholder, List<String> data) {
        Object source = null;
        String p = placeholder.toLowerCase();

        //Get the placeholder type.
        PlaceholderType type = PlaceholderType.fromString(p);
        if (type == null) {
            type = PlaceholderType.CUSTOM;
        }
        p = PlaceholderType.trimType(type, p);

        //Parse all arguments.
        List<Object> dataValues = new ArrayList<Object>();
        int index = 0;
        for (String argument : data) {
            argument = argument.replace(",,", ",").replace("$$", "$");
            ArgumentType argType = ArgumentType.fromValue(argument);
            Argument arg = argType.getNewArg();
            arg.parse(argument);

            //If the first argument is the argument type for the placeholder then use the argument as source if not add it as argument.
            if (index == 0 && arg.getValue().getClass().equals(type.getClazz())) {
                source = arg.getValue();
            } else {
                dataValues.add(arg);
            }
            index++;
        }
        //Get default source if there is no source specified.
        if (source == null) {
            if (player != null) {
                //If there is a player specified get the source from the player.
                if (type == PlaceholderType.PLAYER || type == PlaceholderType.ENTITY) {
                    source = player;
                } else if (type == PlaceholderType.LOCATION) {
                    source = player.getLocation();
                } else if (type == PlaceholderType.WORLD) {
                    source = player.getWorld();
                } else if (type == PlaceholderType.VECTOR) {
                    source = player.getLocation().toVector();
                } else if (type == PlaceholderType.ITEM) {
                    source = player.getInventory().getItemInHand();
                } else if (type == PlaceholderType.INVENTORY) {
                    source = player.getInventory();
                }
            }
        }

        //If there is no source specified and the type is a raw type or there is no player the placeholder is invalid.
        //TODO: Should probably have error codes for setting the value. Like Undefined-1 would be no raw source value specified etc.
        if (source == null) {
            return Message.INVALID_PLACEHOLDER_VALUE.msg(true, true, player).getText();
        }

        //Dispatch the custom event with the placeholder, source and arguments.
        PlaceholderRequestEvent event = new PlaceholderRequestEvent(type, p, source, dataValues);
        EssenceCore.inst().getServer().getPluginManager().callEvent(event);

        //If there is no value set for the placeholder it's invalid.
        if (event.getValue() == null || event.getValue().isEmpty()) {
            return Message.INVALID_PLACEHOLDER_VALUE.msg(true, true, player).getText();
        }

        return event.getValue();
    }
}