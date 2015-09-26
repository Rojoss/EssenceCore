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

package info.mcessence.essence.config;

import info.mcessence.essence.modules.DataModules;

import java.util.HashMap;
import java.util.Map;

public class DataStorageCfg extends EasyConfig {

    public String driver = "sqllite";
    public String host = "localhost";
    public String database = "essence";
    public String table_name = "ess_{type}{suffix}";
    public String username = "";
    public String password = "";
    public boolean disable_plugin_without_database_connection = true;
    public Map<String, Map<String, String>> storage_modules = new HashMap<String, Map<String, String>>();

    public DataStorageCfg(String fileName) {
        this.setFile(fileName);
        load();
    }

    @Override
    public void load() {
        super.load();

        int changes = 0;

        for (DataModules type : DataModules.values()) {
            String key = type.toString().toLowerCase().replaceAll("_", "-");
            if (!storage_modules.containsKey(key)) {
                Map<String, String> data = new HashMap<String, String>();
                data.put("suffix", "");
                data.put("save-delay", Integer.toString(type.getSaveDelay()));
                storage_modules.put(key, data);
                changes++;
            }
        }

        if (changes > 0) {
            save();
        }
    }
}
