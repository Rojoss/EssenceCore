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

import com.google.gson.JsonSyntaxException;
import info.mcessence.essence.nms.util.Util;
import org.bukkit.ChatColor;

/**
 * Handles the json building for Titles and Actionbars
 */
public class FloatMessageBuilder {

    private String finalMessage;
    private boolean building;
    private boolean grouping;

    public FloatMessageBuilder() {
    }

    public FloatMessageBuilder startGrouping() {
        if (building) {
            return this;
        } else {
            finalMessage += "[\"\"";
            grouping = true;
            return this;
        }
    }

    public FloatMessageBuilder startBuilding() {

        if (building) {
            return this;
        } else {
            finalMessage += "{\"\"";
            building = true;
            return this;
        }
    }

    public FloatMessageBuilder finishGrouping() {

        if (building) {
            finalMessage += "]";
            grouping = false;
            return this;
        } else {
            return this;
        }
    }

    public FloatMessageBuilder finishBuilding() {

        if (building) {
            finalMessage += "}";
            building = false;
            return this;
        } else {
            return this;
        }
    }

    public FloatMessageBuilder append(String message) {
        finalMessage += "}, " + message;
        return this;
    }


    public String getMessage() throws Exception {
        if (grouping) {
            throw new JsonSyntaxException("The message grouping is not complete. Add the finishGrouping() method before using getMessage() method");
        } else {
            if (building) {
                throw new JsonSyntaxException("The message is still being built. Add the finishBuilding(), then finishGrouping() methods before using getMessage() method");
            } else {
                return finalMessage;
            }
        }
    }

    public FloatMessageBuilder addMessage(String message) {
        finalMessage += ", \"text\": \"" + message + "\"";
        return this;
    }

    public FloatMessageBuilder addColor(ChatColor chatColor) {
        String color = null;
        Util.handleColor(chatColor, color);

        finalMessage = ", \"color\": \"" + color + "\"";

        return this;
    }

    public FloatMessageBuilder formatBold(boolean bold) {
        finalMessage += ", \"bold\": " + bold;
        return this;
    }

    public FloatMessageBuilder formatItalic(boolean italic) {
        finalMessage += ", \"italic\": " + italic;
        return this;
    }

    public FloatMessageBuilder formatUnderlined(boolean underlined) {
        finalMessage += ", \"underlined\": " + underlined;
        return this;
    }

    public FloatMessageBuilder formatStrikethrough(boolean strikethrough) {
        finalMessage += ", \"strikethough\": " + strikethrough;
        return this;
    }

    public FloatMessageBuilder formatObfuscated(boolean obfuscated) {
        finalMessage += ", \"obfuscated\": " + obfuscated;
        return this;
    }

}