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

package org.essencemc.essencecore.database;

import org.essencemc.essencecore.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Column {

    public List<String> column = new ArrayList<String>();

    public Column(String name) {
        column.add(name);
    }

    public Column type(String type) {
        column.add(type);
        return this;
    }

    public Column type(String type, int size) {
        column.add(type + "(" + size + ")");
        return this;
    }

    public Column notNull() {
        column.add("NOT NULL");
        return this;
    }

    public Column unique() {
        column.add("UNIQUE");
        return this;
    }

    public Column primaryKey() {
        column.add("PRIMARY KEY");
        return this;
    }

    public Column foreignKey() {
        column.add("FOREIGN KEY");
        return this;
    }

    public Column autoIncrement() {
        column.add("AUTO_INCREMENT");
        return this;
    }

    public Column defaultValue(Object value) {
        if (value instanceof String) {
            column.add("DEFAULT '" + value + "'");
        } else {
            column.add("DEFAULT " + value);
        }
        return this;
    }

    public String get() {
        return Util.implode(column, " ");
    }
}
