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

import org.bukkit.ChatColor;

/**
 * Handles the json building for Titles and Actionbars
 */
public class FloatMessageBuilder {

    private String finalMessage;
    private boolean building;
    private boolean grouping;

    public FloatMessageBuilder() {}

    public FloatMessageBuilder startGrouping() {
        BuilderCommons.startGrouping(finalMessage, grouping);
        return this;
    }

    public FloatMessageBuilder startBuilding() {
        BuilderCommons.startBuilding(finalMessage, building);
        return this;
    }

    public FloatMessageBuilder finishGrouping() {
        BuilderCommons.finishGrouping(finalMessage, grouping);
        return this;
    }

    public FloatMessageBuilder finishBuilding() {
        BuilderCommons.finishBuilding(finalMessage, building);
        return this;
    }

    public FloatMessageBuilder append(String message) {
        BuilderCommons.append(finalMessage);
        return this;
    }


    public String getMessage() {
        return BuilderCommons.getMessage(finalMessage, building, grouping);
    }

    public FloatMessageBuilder addMessage(String message) {
        BuilderCommons.addMessage(finalMessage, message);
        return this;
    }

    public FloatMessageBuilder addColor(ChatColor chatColor) {
       BuilderCommons.addColor(finalMessage, chatColor);
        return this;
    }

    public FloatMessageBuilder formatBold(boolean bold) {
        BuilderCommons.formatBold(finalMessage, bold);
        return this;
    }

    public FloatMessageBuilder formatItalic(boolean italic) {
        BuilderCommons.formatItalic(finalMessage, italic);
        return this;
    }

    public FloatMessageBuilder formatUnderlined(boolean underlined) {
        BuilderCommons.formatUnderlined(finalMessage, underlined);
        return this;
    }

    public FloatMessageBuilder formatStrikethrough(boolean strikethrough) {
        BuilderCommons.formatStrikethrough(finalMessage, strikethrough);
        return this;
    }

    public FloatMessageBuilder formatObfuscated(boolean obfuscated) {
        BuilderCommons.formatObfuscated(finalMessage, obfuscated);
        return this;
    }

    public FloatMessageBuilder resetFormats() {
        addColor(ChatColor.WHITE).formatBold(false).formatItalic(false).formatUnderlined(false).formatStrikethrough(false).formatObfuscated(false);
        return this;
    }
}