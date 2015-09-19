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

package info.mcessence.essence.modules.ban;

import info.mcessence.essence.database.Column;
import info.mcessence.essence.database.Database;
import info.mcessence.essence.modules.Module;
import info.mcessence.essence.modules.SqlStorageModule;
import info.mcessence.essence.player.data.internal.DataType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BanModule extends Module implements SqlStorageModule {

    public BanModule(String name) {
        super(name);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void onReload() {

    }


    @Override
    public void onLoad() {

    }

    @Override
    public void onSave() {

    }


    @Override
    public DataType getDataType() {
        return DataType.BAN;
    }

    @Override
    public void createTable() {
        Connection sql = ess.getSql();
        if (sql == null) {
            ess.warn("Disabling the ban module because there is no database connection.");
            onDisable();
            return;
        }
        Database db = ess.getDB();
        try {
            String table = ess.getDataStorageCfg().table_name.replace("{type}", "ban").replace("{suffix}", ess.getDataStorageCfg().storage_modules.get("ban").get("suffix"));
            String query = db.createQuery().createTable(table, true, new Column[]{
                    db.createColumn("id").type("INT").notNull().primaryKey().autoIncrement(),
                    db.createColumn("uuid").type("CHAR", 36).notNull(),
                    db.createColumn("timestamp").type("TIMESTAMP").notNull(),
                    db.createColumn("duration").type("BIGINT").notNull(),
                    db.createColumn("punisher").type("CHAR", 36),
                    db.createColumn("reason").type("VARCHAR", 255)
            }).get();

            Statement statement = sql.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            onDisable();
            ess.logError("Disabling the ban module because it failed to create the database table.");
            e.printStackTrace();
        }
    }
}
