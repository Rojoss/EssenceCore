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

package org.essencemc.essencecore.config;

import org.essencemc.essencecore.util.Util;

import java.util.*;

public class CommandsCfg extends EasyConfig {

    public Map<String, Map<String, String>> COMMANDS = new HashMap<String, Map<String, String>>();

    public CommandsCfg(String fileName) {
        this.setFile(fileName);
        load();
    }

    public void registerCommand(String label, String description, String permission, String[] aliases) {
        if (!COMMANDS.containsKey(label)) {
            Map<String, String> cmdData = new HashMap<String, String>();
            cmdData.put("description", description);
            cmdData.put("permission", permission);
            cmdData.put("aliases", Util.implode(aliases, ","));
            COMMANDS.put(label, cmdData);
            save();
        }
    }

    public String getDescription(String label) {
        if (COMMANDS.containsKey(label) && COMMANDS.get(label).containsKey("description")) {
            return COMMANDS.get(label).get("description").trim();
        }
        return "";
    }

    public String getPermission(String label) {
        if (COMMANDS.containsKey(label) && COMMANDS.get(label).containsKey("permission")) {
            return COMMANDS.get(label).get("permission").trim();
        }
        return "";
    }

    public List<String> getAliases(String label) {
        if (COMMANDS.containsKey(label) && COMMANDS.get(label).containsKey("aliases")) {
            return Arrays.asList(COMMANDS.get(label).get("aliases").trim().replace(" ", "").split(","));
        }
        return new ArrayList<String>();
    }
}
