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

package org.essencemc.essencecore.aliases;

import org.bukkit.Material;
import org.essencemc.essencecore.util.Util;

import java.util.Arrays;
import java.util.List;

public class ItemAlias {

    private String name;
    private Material type;
    private int id;
    private short data;
    private List<String> aliases;

    public ItemAlias(String name, Material type, short data, String[] aliases) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.aliases = Arrays.asList(aliases);
        id = type.getId();
        type.name();
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return type.toString().toLowerCase() + "-" + data;
    }

    public Material getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public short getData() {
        return data;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getAliasesStr() {
        return Util.implode(aliases,",");
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public void addAlias(String alias) {
        aliases.add(alias);
    }

    public boolean hasAlias(String alias) {
        return aliases.contains(alias);
    }
}
