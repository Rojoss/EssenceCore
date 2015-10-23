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

package org.essencemc.essencecore.modules;

import org.bukkit.scheduler.BukkitRunnable;
import org.essencemc.essencecore.database.Column;
import org.essencemc.essencecore.database.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class SqlStorageModule extends StorageModule {

    private String table;
    private DataModules dataType;

    public SqlStorageModule(String name, String tableName, DataModules dataType) {
        super(name);
        this.dataType = dataType;
        table = ess.getDataStorageCfg().table_name.replace("{type}", tableName).replace("{suffix}",
                ess.getDataStorageCfg().storage_modules.get(dataType.toString().toLowerCase().replace("_", "-")).get("suffix"));

    }

    protected abstract Column[] getTableColumns();

    protected Database getDatabase() {
        return ess.getDB();
    }

    protected String getTable() {
        return table;
    }

    protected PreparedStatement createStatement(String query) {
        if (ess.getSql() == null) {
            return null;
        }
        try {
            ess.getSql().prepareStatement(query);
        } catch (SQLException e) {
            //TODO: Proper logging with in game warning etc.
            ess.logError("Failed to create sql statement for the " + getName() + " module!");
            ess.logError(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    protected void closeStatement(PreparedStatement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void executeUpdate(final PreparedStatement statement) {
        executeUpdate(statement, new SqlUpdateCallback() {
            @Override
            public void onExecute(int rowsChanged) {}
        });
    }

    protected void executeUpdate(final PreparedStatement statement, final SqlUpdateCallback callback) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    callback.onExecute(statement.executeUpdate());
                    statement.close();
                } catch (SQLException e) {
                    //TODO: Proper logging with in game warning etc.
                    ess.logError("Failed to execute sql statement for the " + getName() + " module!");
                    ess.logError(e.getMessage());
                    ess.log(e.getErrorCode() + " - " + e.getSQLState() + " - " + e.toString());
                }
            }
        }.runTaskAsynchronously(ess);
    }

    protected void executeQuery(final PreparedStatement statement, final SqlQueryCallback callback) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    callback.onExecute(statement.executeQuery());
                    statement.close();
                } catch (SQLException e) {
                    //TODO: Proper logging with in game warning etc.
                    ess.logError("Failed to execute sql statement for the " + getName() + " module!");
                    ess.logError(e.getMessage());
                    ess.log(e.getErrorCode() + " - " + e.getSQLState() + " - " + e.toString());
                }
            }
        }.runTaskAsynchronously(ess);
    }

}
