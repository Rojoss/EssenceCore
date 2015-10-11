package org.essencemc.essencecore.parsers;

import org.essencemc.essencecore.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>This can be used to parse a string in to a JSON formatted string.
 * These strings can be used in like tellraw commands but also inside a lot EssenceCore methods and configurations.</p>
 *
 * <ul>
 * <li>The following formats will be parsed in to JSON:
 * <li>[[url]text] - Will open the given url.
 * <li>{{hover}text} - Will display hover when hovering over text.
 * <li><<cmd>text> - Will run /cmd when clicking on text.
 * <li><<<cmd>>text> - Will suggest /cmd when clicking on text.
 * </ul>
 *
 * <p>Inheritance is supported for hover messages.
 * This means the message can be something like this:</p>
 * <p>[[http://essencemc.org]{{Click to go to the website!}website}]</p>
 * <p><<heal>{{Click to heal yourself!}heal}]</p>
 */
public class TextParser {

    private final String string;
    private final String json;
    private String error = "";

    public TextParser(String string) {
        this.string = string;
        String json = "";

        // We're just gonna integrate the JSON parser into the string parser/splitter
        String builder = "";
        int square = 0, curly = 0, angle = 0;

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            builder += c;

            switch (c) {
                case '[':
                    if (++square == 2 && curly == 0 && angle == 0 && builder.endsWith("[[")) {
                        json = formatJSONWithArray(json, builder.substring(0, builder.length() - 2));
                        builder = "[[";
                    }
                    break;
                case '{':
                    if (++curly == 2 && square == 0 && angle == 0 && builder.endsWith("{{")) {
                        json = formatJSONWithArray(json, builder.substring(0, builder.length() - 2));
                        builder = "{{";
                    }
                    break;
                case '<':
                    if (++angle == 2 && square == 0 && curly == 0 && builder.endsWith("<<")) {
                        json = formatJSONWithArray(json, builder.substring(0, builder.length() - 2));
                        builder = "<<";
                    }
                    break;
                case ']':
                    if (--square == 0 && curly == 0 && angle == 0 && builder.startsWith("[[")) {
                        json = formatJSONWithArray(json, builder);
                        builder = "";
                    }
                    break;
                case '}':
                    if (--curly == 0 && square == 0 && angle == 0 && builder.startsWith("{{")) {
                        json = formatJSONWithArray(json, builder);
                        builder = "";
                    }
                    break;
                case '>':
                    if (--angle == 0 && square == 0 && curly == 0 && builder.startsWith("<<")) {
                        json = formatJSONWithArray(json, builder);
                        builder = "";
                    }
                    break;
            }
        }

        if (!builder.isEmpty()) {
            json = formatJSONWithArray(json, builder);
        }

        if (json.startsWith("[")) {
            json += "]";
        }

        this.json = json;
    }

    private String formatJSON(String text) {
        text = text.replace("\\n", "\n").replace("&", "§");

        //Get JSON for custom formats.
        if (text.startsWith("{{")) {
            return formatHover(text);
        } else if (text.startsWith("[[")) {
            return formatClick(text, "]", 2, "open_url");
        } else if (text.startsWith("<<<")) {
            return formatClick(text, ">>", 3, "suggest_command");
        } else if (text.startsWith("<<")) {
            return formatClick(text, ">", 2, "run_command");
        }

        //Default format for regular text.
        return "{\"text\":\"" + text + "\"}";
    }

    private String formatJSONWithArray(String json, String text) {
        // Don't format empty texts
        if (text.isEmpty()) {
            return "";
        }

        boolean empty = json.isEmpty();

        if (!empty) {
            if (!json.startsWith("[")) {
                json = "[" + json + ", " + formatJSON(text);
            } else {
                json += ", " + formatJSON(text);
            }
        } else {
            json = formatJSON(text);
        }

        return json;
    }

    private String formatHover(String text) {
        text = text.substring(2, text.length() - 1);
        String[] split = Util.bisect(text, "}");
        return "{\"text\": \"" + split[1] + "\",\"hoverEvent\": {\"action\": \"show_text\",\"value\": {\"text\": \"\",\"extra\": [{\"text\": \"" + split[0] + "\"}]}}}";
    }

    private String[] formatHoverInherit(String text) {
        text = text.substring(2, text.length() - 1);
        String[] split = Util.bisect(text, "}");
        split[0] = "\"hoverEvent\": {\"action\": \"show_text\",\"value\": {\"text\": \"\",\"extra\": [{\"text\": \"" + split[0] + "\"}]}}";
        return split;
    }

    private String formatClick(String text, String splitChar, int charCount, String action) {
        text = text.substring(charCount, text.length() - 1);
        String[] split = Util.bisect(text, splitChar);

        String[] hover = null;
        if (split[1].contains("{{")) {
            hover = formatHoverInherit(split[1]);
            if (hover.length > 1) {
                split[1] = hover[1];
            }
        }

        String json = "\"text\": \"" + split[1] + "\",\"clickEvent\": {\"action\": \"" + action + "\",\"value\": \"" + split[0] + "\"}";
        if (hover != null) {
            json += "," + hover[0];
        }
        return "{" + json + "}";
    }

    /**
     * Get the input string value.
     * @return string which can be used in configurations and messages etc.
     */
    public String getString() {
        return string;
    }

    /**
     * Get the parsed JSON string.
     * @return String with JSON format which can be used in tellraw etc.
     */
    public String getJSON() {
        return json;
    }
}