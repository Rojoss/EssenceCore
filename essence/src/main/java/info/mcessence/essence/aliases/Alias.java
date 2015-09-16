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

package info.mcessence.essence.aliases;

import info.mcessence.essence.util.Util;

import java.util.Arrays;
import java.util.List;

public class Alias {

    private AliasType type;
    private String key;
    private String name;
    private List<String> aliases;

    public Alias(AliasType type, String key, String name, String[] aliases) {
        this.type = type;
        this.key = key;
        this.name = name;
        this.aliases = Arrays.asList(aliases);
    }

    public AliasType getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getAliasesStr() {
        return Util.implode(aliases, ",");
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
