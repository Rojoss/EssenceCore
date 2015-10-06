package org.essencemc.essencecore.placeholders;

import org.bukkit.command.CommandSender;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.arguments.*;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.util.Util;

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
    public static String parse(String string, CommandSender source) {
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

                int leftBracketIndex = word.indexOf('(');
                int rightBracketIndex = word.contains(")") ? word.lastIndexOf(')') : word.length();

                String placeholder = word.substring(1, (leftBracketIndex == -1 ? word.length() : leftBracketIndex)).trim();

                List<String> data = new ArrayList<String>();
                String[] arguments = parseSplitNotDouble(word.substring((leftBracketIndex == -1 ? 0 : leftBracketIndex) + 1, rightBracketIndex).trim(), ',');

                for (String argument : arguments) {
                    data.add(parse(argument, source));
                }

                result += getPlaceholderValue(source, placeholder, data) + " ";
            } else {
                // Literal
                result += word + " ";
            }
        }

        return result.trim(); // Remove the last space
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
                if (string.length() > (count + 1) && string.charAt(count + 1) == '(') { // Account for escaping
                    count++;
                } else {
                    roundBrackets++;
                }
            } else if (c == ')') {
                if (string.length() > (count + 1) && string.charAt(count + 1) == ')') { // Account for escaping
                    count++;
                } else {
                    roundBrackets--;
                }
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

    private static String getPlaceholderValue(CommandSender source, String placeholder, List<String> data) {
        String p = placeholder.toLowerCase();
        PlaceholderType type = PlaceholderType.fromString(p);
        if (type == null) {
            type = PlaceholderType.CUSTOM;
        }

        Argument[] args = new Argument[data.size()];
        int index = 0;
        for (String argument : data) {
            ArgumentType argType = ArgumentType.fromValue(argument);
            Argument arg = argType.getNewArg();

            arg.parse(argument);
            args[index++] = arg;
        }

        PlaceholderRequestEvent event = new PlaceholderRequestEvent(source, type, placeholder, args);
        EssenceCore.inst().getServer().getPluginManager().callEvent(event);

        if (event.getValue() == null || event.getValue().isEmpty()) {
            return Util.color(Message.INVALID_PLACEHOLDER_VALUE.msg().getMsg(false));
        }

        return event.getValue();
    }
}
