package org.essencemc.essencecore.placeholders;

import org.bukkit.command.CommandSender;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.arguments.*;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
    public static String parse(String string, CommandSender sender) {
        //If there are no placeholders do nothing.
        if (!string.contains("$")) {
            return string;
        }

        String[] words = string.split(" ");
        List<String> placeholders = new ArrayList<String>();

        //Combine placeholders with spaces and put them in placholders list.
        List<String> curPlaceholder = new ArrayList<String>();
        int parsingPlaceholders = 0;
        for (String word : words) {
            //Check if the word starts a new placeholder.
            if (word.contains("$")) {
                parsingPlaceholders++;

                if (parsingPlaceholders <= 0) {
                    //Not parsing placeholders yet.
                    //If the word doesn't have data add the placeholder, or if all data is in the same word add it too.
                    if (!word.contains("(") || word.contains("()")) {
                        placeholders.add(word);
                        parsingPlaceholders--;
                        continue;
                    }

                    //Start a new placeholder.
                    curPlaceholder.add(word);
                } else {
                    curPlaceholder.add(word);
                    //Check for completion.
                    parsingPlaceholders -= Util.countChars(word, ')');
                    if (parsingPlaceholders <= 0) {
                        parsingPlaceholders = 0;
                        placeholders.add(Util.implode(curPlaceholder, " "));
                        curPlaceholder.clear();
                    }
                }
                continue;
            }

            //If the word doesn't start a placeholder and we're not parsing any just continue...
            if (parsingPlaceholders <= 0) {
                continue;
            }

            //We're still parsing placeholders... (Check for completion)
            //Check for completion.
            curPlaceholder.add(word);
            parsingPlaceholders -= Util.countChars(word, ')');
            if (parsingPlaceholders <= 0) {
                parsingPlaceholders = 0;
                placeholders.add(Util.implode(curPlaceholder, " "));
                curPlaceholder.clear();
            }
        }

        //Go through all the placeholders and replace them recursively
        for (String placeholder : placeholders) {
            String placeholderClone = placeholder;
            String value = placeholder;

            //TODO: Get placeholder values recursively..

            string = string.replaceAll(Pattern.quote(placeholderClone), value);
        }

        return string;
    }

    private static String getPlaceholderValue(String placeholder, Map<String, Object> data) {
        String p = placeholder.toLowerCase();
        PlaceholderType type = PlaceholderType.fromString(p);
        if (type == null) {
            type = PlaceholderType.CUSTOM;
        }

        Argument[] args = new Argument[data.size()];
        int index = 0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            ArgumentType argType = ArgumentType.fromObject(entry.getValue());
            if (argType == ArgumentType.NULL || argType == ArgumentType.UNKNOWN) {
                argType = ArgumentType.fromValue(entry.getKey());
            }
            Argument arg = argType.getNewArg();;
            arg.setDefault(entry.getValue());
            arg.parse(entry.getKey());
            args[index] = arg;
            index++;
        }

        PlaceholderRequestEvent event = new PlaceholderRequestEvent(type, placeholder, args);
        EssenceCore.inst().getServer().getPluginManager().callEvent(event);

        if (event.getValue() == null || event.getValue().isEmpty()) {
            return Util.color(Message.INVALID_PLACEHOLDER_VALUE.msg().getMsg(false));
        }

        return event.getValue();
    }
}
