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

package info.mcessence.essence.database;

import info.mcessence.essence.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Query {

    private List<String> query = new ArrayList<String>();

    public Query createTable(String table, boolean ifNotExists, Column[] columns) {
        String str = "CREATE TABLE" + (ifNotExists?" IF NOT EXISTS":"") + " " + table + "(";
        for (Column column : columns) {
            str += column.get() + ",";
        }
        str = str.substring(0, str.length()-1);
        str += ")";
        query.add(str);
        return this;
    }

    public Query select(String... columns) {
        query.add("SELECT " + Util.implode(columns, ","));
        return this;
    }

    public Query selectDistinct(String... columns) {
        query.add("SELECT DISTINCT " + Util.implode(columns, ","));
        return this;
    }

    public Query insertInto(String table) {
        query.add("INSERT INTO " + table);
        return this;
    }

    public Query update(String table) {
        query.add("UPDATE " + table);
        return this;
    }

    public Query delete(String table) {
        query.add("DELETE");
        return this;
    }

    public Query from(String table) {
        query.add("FROM " + table);
        return this;
    }

    public Query values(List<String> columns, List<Object> values) {
        String columns_str = "(";
        String values_str = "(";
        for (int i = 0; i < columns.size(); i++) {
            columns_str += columns.get(i) + ",";
            if (values.get(i) instanceof String) {
                values_str += "'" + values.get(i) + "',";
            } else {
                values_str += values.get(i) + ",";
            }
        }
        columns_str = columns_str.substring(0, columns_str.length()-1);
        columns_str += ")";
        values_str = values_str.substring(0, values_str.length()-1);
        values_str += ")";
        query.add(columns_str + " VALUES " + values_str);
        return this;
    }

    public Query set(List<String> columns, List<Object> values) {
        String str = "SET ";
        for (int i = 0; i < columns.size(); i++) {
            if (values.get(i) instanceof String) {
                str += columns.get(i) + "='" + values.get(i) + "',";
            } else {
                str += columns.get(i) + "=" + values.get(i) + ",";
            }
        }
        str = str.substring(0, str.length()-1);
        query.add(str);
        return this;
    }

    public Query where(String column, Operator operator, Object value) {
        if (value instanceof String) {
            query.add("WHERE " + column + " " + operator.get() + " '" + value + "'");
        } else {
            query.add("WHERE " + column + " " + operator.get() + " " + value);
        }
        return this;
    }

    public Query and(String column, Operator operator, Object value) {
        if (value instanceof String) {
            query.add("AND " + column + " " + operator.get() + " '" + value + "'");
        } else {
            query.add("AND " + column + " " + operator.get() + " " + value);
        }
        return this;
    }

    public Query or(String column, Operator operator, Object value) {
        if (value instanceof String) {
            query.add("OR " + column + " " + operator.get() + " '" + value + "'");
        } else {
            query.add("OR " + column + " " + operator.get() + " " + value);
        }
        return this;
    }

    public Query orderBy(String column, String direction) {
        query.add("ORDER BY " + column + " " + direction);
        return this;
    }

    public Query orderBy(List<String> columns, List<String> directions) {
        String str = "ORDER BY";
        for (int i = 0; i < columns.size(); i++) {
            str += " " + columns.get(i) + " " + directions.get(i) + ",";
        }
        str = str.substring(0, str.length()-1);
        query.add(str);
        return this;
    }

    public Query limit(int rows) {
        query.add("LIMIT " + rows);
        return this;
    }

    public Query innerJoin(String table, String column1, String column2) {
        query.add("INNER JOIN " + table + " ON " + column1 + "=" + column2);
        return this;
    }

    public Query leftJoin(String table, String column1, String column2) {
        query.add("LEFT JOIN " + table + " ON " + column1 + "=" + column2);
        return this;
    }

    public Query rightJoin(String table, String column1, String column2) {
        query.add("RIGHT JOIN " + table + " ON " + column1 + "=" + column2);
        return this;
    }

    public Query fullOutherJoin(String table, String column1, String column2) {
        query.add("FULL OUTER JOIN " + table + " ON " + column1 + "=" + column2);
        return this;
    }

    public String get() {
        return Util.implode(query, " ") + ";";
    }
}
