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

package info.mcessence.essence.nms.util.builders;


import info.mcessence.essence.nms.util.Util;
import org.bukkit.ChatColor;

/**
 * All methods common between all builders
 */
public class BuilderCommons {

    public static void startGrouping(String message, boolean grouping) {
        if (!grouping) {
            message += "[\"\"";
            grouping = true;
        }
    }

    public static void startBuilding(String message, boolean building) {
        if (!building) {
            message += "{\"\"";
            building = true;
        }
    }

    public static void append(String message) {
        message += "}, " + message;
    }

    public static void finishBuilding(String message, boolean building) {
        if (building) {
            message += "}";
            building = false;
        }
    }

    public static void finishGrouping(String message, boolean grouping) {
        if (grouping) {
            message += "]";
            grouping = false;
        }
    }

    public static String getMessage(String message, boolean building, boolean grouping) {
        try {
            if (grouping) {
                throw new Exception("The message grouping is not complete. Add the finishGrouping() method before using getMessage() method");
            } else {
                if (building) {
                    throw new Exception("The message is still being built. Add the finishBuilding(), then finishGrouping() methods before using getMessage() method");
                } else {
                    return message;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return message;
        }
    }

    public static void addMessage(String message, String text) {
        message += ", \"text\": \"" + text + "\"";
    }

    public static void addColor(String message, ChatColor chatColor) {
        String color = null;
        Util.handleColor(chatColor, color);

        message = ", \"color\": \"" + color + "\"";
    }

    public static void formatBold(String message, boolean bold) {
        message += ", \"bold\": " + bold;
    }

    public static void formatItalic(String message, boolean italic) {
        message += ", \"italic\": " + italic;
    }

    public static void formatUnderlined(String message, boolean underlined) {
        message += ", \"underlined\": " + underlined;
    }

    public static void formatStrikethrough(String message, boolean strikethrough) {
        message += ", \"strikethough\": " + strikethrough;
    }

    public static void formatObfuscated(String message, boolean obfuscated) {
        message += ", \"obfuscated\": " + obfuscated;
    }

}
