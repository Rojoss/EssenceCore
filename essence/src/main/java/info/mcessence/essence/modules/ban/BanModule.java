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
import info.mcessence.essence.modules.DataModules;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

public class BanModule extends Module implements SqlStorageModule {

    public Map<UUID, List<Ban>> bans = new HashMap<UUID, List<Ban>>();
    public Map<UUID, List<Ban>> bans_local = new HashMap<UUID, List<Ban>>();

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
        //TODO: Load bans from database.
    }

    @Override
    public void onSave() {
        if (bans_local.size() < 1) {
            return;
        }
        //TODO: Save local bans to database.
    }

    @Override
    public DataModules getDataType() {
        return DataModules.BAN;
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



    // ##################################################
    // ################# IMPLEMENTATION #################
    // ##################################################

    /**
     * Get a Ban instance if the player is currently banned.
     * If the player doesn't have an active ban it will return null.
     * @param uuid The player to get the ban from.
     * @return Ban instance or null.
     */
    public Ban getActiveBan(UUID uuid) {
        if (bans.containsKey(uuid)) {
            Long onlineTime = getOnlineTime(uuid);
            for (Ban ban : bans.get(uuid)) {
                if (ban.isActive(onlineTime)) {
                    return ban;
                }
            }
        }
        return null;
    }

    /**
     * Get a list of all Ban instances of the given player.
     * If the player doesn't have any bans it will return an empty list.
     * @param uuid The player to get the bans from.
     * @return List<Ban> with all bans.
     */
    public List<Ban> getBans(UUID uuid) {
        if (bans.containsKey(uuid)) {
            return bans.get(uuid);
        }
        return new ArrayList<Ban>();
    }

    /**
     * Checks if the given player is banned or not.
     * This only returns true if the player has no ACTIVE ban.
     * If the player is banned before but the time has run out this will be false.
     * @param uuid The player to check.
     * @return true if banned and false if the player has no active ban.
     */
    public boolean isBanned(UUID uuid) {
        return getActiveBan(uuid) != null;
    }

    /**
     * Bans the given player for the specified duration and reason.
     * Please remember that a player can't be banned while he already has an active ban.
     * @param uuid The player to ban.
     * @param duration The duration in milliseconds to ban the player.
     * @param punisher The player that sends the ban. (May be null)
     * @param reason The reason for the ban. (May be null)
     * @return true if the player got banned and false if the player was already banned and it didn't ban the player.
     */
    public boolean ban(UUID uuid, Long duration, UUID punisher, String reason) {
        if (isBanned(uuid)) {
            return false;
        }
        List<Ban> playerBans = new ArrayList<Ban>();
        if (bans.containsKey(uuid)) {
            playerBans = bans.get(uuid);
        }
        playerBans.add(new Ban(new Timestamp(System.currentTimeMillis()), duration, punisher, reason));
        bans.put(uuid, playerBans);
        bans_local.put(uuid, playerBans);
        return true;
    }

    /**
     * Unban the given player if he has an active ban.
     * If the player isn't banned nothing will happen.
     * @param uuid The player to unban.
     * @return true if the player was unbanned and false if not.
     */
    public boolean unban(UUID uuid) {
        if (!isBanned(uuid)) {
            return false;
        }
        getActiveBan(uuid).setRemainingTime(0l);
        bans_local.put(uuid, getBans(uuid));
        return true;
    }

    private Long getOnlineTime(UUID uuid) {
        //TODO: Get the time the player was online.
        return 0l;
    }

}
