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

package org.essencemc.essencecore;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.essencemc.essencecore.aliases.AliasType;
import org.essencemc.essencecore.commands.Commands;
import org.essencemc.essencecore.config.*;
import org.essencemc.essencecore.config.aliases.AliasesCfg;
import org.essencemc.essencecore.config.aliases.ItemAliases;
import org.essencemc.essencecore.config.data.Warps;
import org.essencemc.essencecore.database.Database;
import org.essencemc.essencecore.database.MySql.MySql;
import org.essencemc.essencecore.database.SqlLite.SqlLite;
import org.essencemc.essencecore.listeners.ModuleListener;
import org.essencemc.essencecore.modules.Modules;
import org.essencemc.essencecore.nms.ISkull;
import org.essencemc.essencecore.nms.ITitle;
import org.essencemc.essencecore.nms.v1_8_R3.SkullUtil_1_8_R3;
import org.essencemc.essencecore.nms.v1_8_R3.Title_1_8_R3;
import org.essencemc.essencecore.player.PlayerManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class EssenceCore extends JavaPlugin {

    private static EssenceCore instance;
    //private Gson gson = new Gson();

    private Database database;
    private Connection sql;

    private DataStorageCfg dataStorageCfg;
    private MessagesCfg messages;
    private ModulesCfg modulesCfg;
    private CommandsCfg commandsCfg;
    private CommandOptionsCfg cmdOptionsCfg;
    private Warps warps;

    private ISkull iSkull = null;
    private ITitle iTitle = null;

    private ItemAliases itemAliases;
    private Map<AliasType, AliasesCfg> aliases = new HashMap<AliasType, AliasesCfg>();

    private Commands commands;
    private Modules modules;

    private PlayerManager playerManager;

    private final Logger log = Logger.getLogger("EssenceCore");


    @Override
    public void onDisable() {
        instance = null;
        log("disabled");
    }

    @Override
    public void onEnable() {
        instance = this;
        log.setParent(this.getLogger());

        dataStorageCfg = new DataStorageCfg("plugins/Essence/DataStorage.yml");
        messages = new MessagesCfg("plugins/Essence/Messages.yml");
        modulesCfg = new ModulesCfg("plugins/Essence/Modules.yml");
        commandsCfg = new CommandsCfg("plugins/Essence/Commands.yml");
        cmdOptionsCfg = new CommandOptionsCfg("plugins/Essence/CommandOptions.yml");

        loadAliases();
        if (!setupDatabase()) {
            return;
        }
        setupNMS();
        registerEvents();

        //TODO: Have a class for modules were it would create the config and such.
        warps = new Warps("plugins/Essence/data/Warps.yml");

        commands = new Commands(this);
        modules = new Modules(this);

        playerManager = new PlayerManager(this);

        log("loaded successfully");
    }

    private void setupNMS() {
        String version;
        boolean compatible;

        try {
            version = getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            return;
        }

        switch (version) {
            case "v1_8_R3" :
                iSkull = new SkullUtil_1_8_R3();
                iTitle = new Title_1_8_R3();
                compatible = true;
                break;
            default:
                compatible = false;
        }

        if (compatible)
            log("This version of EssenceCore is fully compatible with your server version!");
        else {
            warn("This version of EssenceCore is not fully compatible with your server version!");
            warn("Your server version: " + version);
            warn("Earliest compatible version: v1_8_R1");
            warn("Latest compatible version: v1_8_R3");
            warn("If there is a newer server version we will update the plugin as soon as possible.");
            warn("EssenceCore will still work fine but certain features will be disabled.");
        }
    }

    private boolean setupDatabase() {
        if (dataStorageCfg.driver.equalsIgnoreCase("sqllite")) {
            log("Setting up connection with the SqlLite database...");
            database = new SqlLite(this, dataStorageCfg.database);
            if (!openDatabaseConnection()) {
                return false;
            }
        } else if (dataStorageCfg.driver.equalsIgnoreCase("mysql")) {
            log("Setting up connection with the MySql database...");
            database = new MySql(this, dataStorageCfg.host, dataStorageCfg.database, dataStorageCfg.username, dataStorageCfg.password);
            if (!openDatabaseConnection()) {
                return false;
            }
        } else {
            logError("No valid database driver selected!");
            logError("The driver must be one of these options: 'SqlLite' or 'MySql'");
            if (databaseError()) {
                return false;
            }
        }
        return true;
    }

    private boolean openDatabaseConnection() {
        if (database == null) {
            logError("No valid database loaded.");
            if (databaseError()) {
                return false;
            }
            return true;
        }
        try {
            sql = database.openConnection();
        } catch (SQLException e) {
            logError("Unable to establish a connection to your " + database.getType() + " database.");
            logError("Please make sure the details in DataStorage.yml are set up correct.");
            logError(e.getMessage());
            if (databaseError()) {
                return false;
            }
        } catch (ClassNotFoundException e) {
            logError("No database driver found for " + database.getType() + "!");
            logError(e.getMessage());
            if (databaseError()) {
                return false;
            }
        }
        return true;
    }

    public boolean databaseError() {
        if (dataStorageCfg.disable_plugin_without_database_connection) {
            logError("Disabling the plugin because there is no valid database connection!");
            setEnabled(false);
            return true;
        } else {
            logError("No valid database connection found!");
            logError("Because 'disable_plugin_without_database_connection' is set to false the plugin will still work.");
            logError("This is not recommended and you should attempt to fix the database connection as many features will fail to work!");
            return false;
        }
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ModuleListener(), this);
    }

    private void loadAliases() {
        itemAliases = new ItemAliases("plugins/Essence/aliases/Items.yml");

        aliases.put(AliasType.ENCHANTMENT, new AliasesCfg("plugins/Essence/aliases/Enchantments.yml", AliasType.ENCHANTMENT));
        aliases.put(AliasType.POTION_EFFECT, new AliasesCfg("plugins/Essence/aliases/PotionEffects.yml", AliasType.POTION_EFFECT));
        aliases.put(AliasType.ENTITY, new AliasesCfg("plugins/Essence/aliases/Entities.yml", AliasType.ENTITY));
        aliases.put(AliasType.GAME_MODE, new AliasesCfg("plugins/Essence/aliases/GameModes.yml", AliasType.GAME_MODE));
        aliases.put(AliasType.PAINTING, new AliasesCfg("plugins/Essence/aliases/Paintings.yml", AliasType.PAINTING));
        aliases.put(AliasType.BIOME, new AliasesCfg("plugins/Essence/aliases/Biomes.yml", AliasType.BIOME));
        aliases.put(AliasType.DYE_COLOR, new AliasesCfg("plugins/Essence/aliases/DyeColors.yml", AliasType.DYE_COLOR));
        aliases.put(AliasType.FIREWORK_EFFECT, new AliasesCfg("plugins/Essence/aliases/FireworkEffects.yml", AliasType.FIREWORK_EFFECT));
        aliases.put(AliasType.HORSE_VARIANT, new AliasesCfg("plugins/Essence/aliases/HorseVariants.yml", AliasType.HORSE_VARIANT));
        aliases.put(AliasType.HORSE_COLOR, new AliasesCfg("plugins/Essence/aliases/HorseColors.yml", AliasType.HORSE_COLOR));
        aliases.put(AliasType.HORSE_STYLE, new AliasesCfg("plugins/Essence/aliases/HorseStyles.yml", AliasType.HORSE_STYLE));
        aliases.put(AliasType.OCELOT_TYPES, new AliasesCfg("plugins/Essence/aliases/OcelotTypes.yml", AliasType.OCELOT_TYPES));
        aliases.put(AliasType.RABBIT_TYPES, new AliasesCfg("plugins/Essence/aliases/RabbitTypes.yml", AliasType.RABBIT_TYPES));
        aliases.put(AliasType.VILLAGER_TYPES, new AliasesCfg("plugins/Essence/aliases/VillagerTypes.yml", AliasType.VILLAGER_TYPES));
        aliases.put(AliasType.BANNER_PATTERNS, new AliasesCfg("plugins/Essence/aliases/BannerPatterns.yml", AliasType.BANNER_PATTERNS));
        aliases.put(AliasType.TREES, new AliasesCfg("plugins/Essence/aliases/Trees.yml", AliasType.TREES));
        aliases.put(AliasType.SOUNDS, new AliasesCfg("plugins/Essence/aliases/Sounds.yml", AliasType.SOUNDS));
    }

    public void log(Object msg) {
        log.info("[EssenceCore " + getDescription().getVersion() + "] " + msg.toString());
    }
    public void warn(Object msg) {
        log.warning("[EssenceCore " + getDescription().getVersion() + "] " + msg.toString());
    }
    public void logError(Object msg) {
        log.severe("[EssenceCore " + getDescription().getVersion() + "] " + msg.toString());
    }

    
    public static EssenceCore inst() {
        return instance;
    }

    /*public Gson getGson() {
        return gson;
    }*/

    public Database getDB() {
        return database;
    }

    public Connection getSql() {
        return sql;
    }


    public ISkull getISkull() {
        return iSkull;
    }


    public DataStorageCfg getDataStorageCfg() {
        return dataStorageCfg;
    }

    public MessagesCfg getMessages() {
        return messages;
    }

    public ModulesCfg getModuleCfg() {
        return modulesCfg;
    }

    public CommandsCfg getCommandsCfg() {
        return commandsCfg;
    }

    public CommandOptionsCfg getCmdOptions() {
        return cmdOptionsCfg;
    }

    public Warps getWarps() {
        return warps;
    }


    public PlayerManager getPM() {
        return playerManager;
    }


    public Commands getCommands() {
        return commands;
    }

    public Modules getModules() {
        return modules;
    }

    public ITitle getITitle() {
        return iTitle;
    }

}