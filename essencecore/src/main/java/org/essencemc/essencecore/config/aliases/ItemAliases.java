/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://essencemc.org>
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

package org.essencemc.essencecore.config.aliases;

import org.essencemc.essencecore.aliases.ItemAlias;
import org.essencemc.essencecore.aliases.Items;
import org.essencemc.essencecore.config.internal.EasyConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemAliases extends EasyConfig {

    public HashMap<String, HashMap<String, String>> aliases = new HashMap<String, HashMap<String, String>>();

    public ItemAliases(String fileName) {
        this.setFile(fileName);
        load();
    }

    public void load() {
        super.load();
        HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();
        List<ItemAlias> items = Items.getItems();
        for (ItemAlias item : items) {
            HashMap<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("name", item.getName());
            dataMap.put("aliases", item.getAliasesStr());
            data.put(item.getKey(), dataMap);
        }

        int changes = 0;

        //Remove old keys
        List<String> keys = new ArrayList<String>(aliases.keySet());
        for (String key : keys) {
            if (!data.containsKey(key)) {
                aliases.remove(key);
                changes++;
                break;
            }
        }

        //Add new keys
        for (String key : data.keySet()) {
            if (!aliases.containsKey(key)) {
                aliases.put(key, data.get(key));
                changes++;
                continue;
            }
        }

        if (changes > 0) {
            save();
        }

        for (ItemAlias item : items) {
            item.setAliases(getAliases(item.getKey(), true));
        }
    }

    public List<String> getKeys() {
        return new ArrayList<String>(aliases.keySet());
    }

    public List<String> getAliases(String key, boolean includeName) {
        if (!aliases.containsKey(key)) {
            return new ArrayList<String>();
        }
        if (!aliases.get(key).containsKey("aliases")) {
            return new ArrayList<String>();
        }

        List<String> aliasesList = new ArrayList<String>();
        if (!aliases.get(key).get("aliases").isEmpty()) {
            aliasesList.addAll(Arrays.asList(aliases.get(key).get("aliases").toLowerCase().replaceAll(" ", "").split(",")));
        }
        if (includeName && aliases.get(key).containsKey("name")) {
            aliasesList.add(aliases.get(key).get("name").toLowerCase().replaceAll(" ", ""));
        }
        return aliasesList;
    }

}
