package org.essencemc.essencecore.parsers;

import org.essencemc.essencecore.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * This can be used to parse a string in to a JSON formatted string.
 * These strings can be used in like tellraw commands but also inside a lot EssenceCore methods and configurations.
 *
 * The following formats will be parsed in to JSON.
 * [[url]text] - Will open the given url
 * {{hover}text} - Will display hover when hovering over text.
 * <<cmd>text> - Will run /cmd when clicking on text.
 * <<<cmd>>text> - Will suggest /cmd when clicking on text.
 * Inheritance is supported for hover messages.
 * This means the message can be something like this:
 * [[http://essencemc.org]{{Click to go to the website!}website}]
 * <<heal>{{Click to heal yourself!}heal}]
 */
public class TextParser {

    private String string = null;
    private String json = null;
    private String error = "";

    public TextParser(String string) {
        this.string = string;

        String[] words = string.split(" ");
        List<String> sections = new ArrayList<String>();

        //First of all combine all words together into sections.
        List<String> curSection = new ArrayList<String>();
        boolean isFormatting = false;
        int endCharsNeeded = 0;
        char format = ' ';
        for (String word : words) {
            //If we're already formatting add the section.
            if (isFormatting) {
                curSection.add(word);
                //Reduce the end characters needed.
                endCharsNeeded -= countChars(word, format);
                //If all end characters have been added end the current section and start a new one.
                if (endCharsNeeded <= 0) {
                    if (!curSection.isEmpty()  & !curSection.get(0).isEmpty()) {
                        sections.add(Util.implode(curSection, " "));
                    }
                    curSection.clear();
                    format = ' ';
                    isFormatting = false;
                }
                continue;
            }

            //Get the type of section. (Space for a regular text word/section)
            char type = ' ';
            char[] chars = word.toCharArray();
            for (char ch : chars) {
                if (ch == '{' || ch == '<' || ch == '[') {
                    type = ch;
                    break;
                }
            }

            //If the type is just regular text, add the word to current section.
            if (type == ' ') {
                curSection.add(word);
                continue;
            }
            //If there is a format symbol, end the current section and start a new formatting section.
            if (!curSection.isEmpty() && !curSection.get(0).isEmpty()) {
                sections.add(Util.implode(curSection, " "));
            }
            curSection.clear();
            curSection.add(word);
            isFormatting = true;
            if (type == '{') {
                format = '}';
            } else if (type == '[') {
                format = ']';
            } else if (type == '<') {
                format = '>';
            }
            endCharsNeeded = countChars(word, type);

            //Check if the current word already contains all the end chars.
            endCharsNeeded -= countChars(word, format);
            if (endCharsNeeded <= 0) {
                if (!word.isEmpty()) {
                    sections.add(word);
                }
                curSection.clear();
                format = ' ';
                isFormatting = false;
            }
        }
        if (!curSection.isEmpty() && !curSection.get(0).isEmpty()) {
            sections.add(Util.implode(curSection, " "));
        }

        //Format all sections to JSON.
        for (int i = 0; i < sections.size(); i++) {
            String text = sections.get(i);
            //Simply convert colors to use § instead of &.
            text = text.replaceAll("&", "§");

            //Get JSON for custom formats.
            if (text.startsWith("{{")) {
                text = formatHover(text);
            } else if (text.startsWith("[[")) {
                text = formatClick(text, "]", 2, "open_url");
            } else if (text.startsWith("<<<")) {
                text = formatClick(text, ">>", 3, "suggest_command");
            } else if (text.startsWith("<<")) {
                text = formatClick(text, ">", 2, "run_command");
            } else {
                //Default format for regular text.
                text = "{\"text\":\"" + text + "\"}";
            }

            //Aply the json text.
            sections.set(i, text);
        }

        json = "[" + Util.implode(sections, ", ") + "]";
    }


    private int countChars(String string, char ch) {
        int count = 0;
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }

    private String formatHover(String text) {
        text = text.substring(2, text.length()-1);
        String[] split = text.split("}");
        return "{\"text\": \"" + split[1] + "\",\"hoverEvent\": {\"action\": \"show_text\",\"value\": {\"text\": \"\",\"extra\": [{\"text\": \"" + split[0] + "\"}]}}}";
    }

    private String[] formatHoverInherit(String text) {
        text = text.substring(2, text.length()-1);
        String[] split = text.split("}");
        split[0] = "\"hoverEvent\": {\"action\": \"show_text\",\"value\": {\"text\": \"\",\"extra\": [{\"text\": \"" + split[0] + "\"}]}}";
        return split;
    }

    private String formatClick(String text, String splitChar, int charCount, String action) {
        text = text.substring(charCount, text.length()-1);
        String[] split = text.split(splitChar);

        String[] hover = null;
        if (split[1].contains("{")) {
            hover = formatHoverInherit(split[1]);
            split[1] = hover[1];
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
