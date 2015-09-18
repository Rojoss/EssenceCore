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

package info.mcessence.essence;

import info.mcessence.essence.aliases.AliasType;
import info.mcessence.essence.commands.Commands;
import info.mcessence.essence.config.CommandOptionsCfg;
import info.mcessence.essence.config.CommandsCfg;
import info.mcessence.essence.config.MessagesCfg;
import info.mcessence.essence.config.ModulesCfg;
import info.mcessence.essence.config.aliases.AliasesCfg;
import info.mcessence.essence.config.aliases.ItemAliases;
import info.mcessence.essence.config.data.Warps;
import info.mcessence.essence.nms.ISkull;
import info.mcessence.essence.nms.ITitle;
import info.mcessence.essence.nms.v1_8_R1.SkullUtil_v1_8_R1;
import info.mcessence.essence.nms.v1_8_R1.Title_1_8_R1;
import info.mcessence.essence.nms.v1_8_R2.SkullUtil_1_8_R2;
import info.mcessence.essence.nms.v1_8_R2.Title_1_8_R2;
import info.mcessence.essence.nms.v1_8_R3.SkullUtil_1_8_R3;
import info.mcessence.essence.nms.v1_8_R3.Title_1_8_R3;
import info.mcessence.essence.player.PlayerManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Essence extends JavaPlugin {

    private static Essence instance;
    //private Gson gson = new Gson();

    private MessagesCfg messages;
    private ModulesCfg modules;
    private CommandsCfg commandsCfg;
    private CommandOptionsCfg cmdOptionsCfg;
    private Warps warps;

    private ISkull iSkull = null;
    private ITitle iTitle = null;

    private ItemAliases itemAliases;
    private Map<AliasType, AliasesCfg> aliases = new HashMap<AliasType, AliasesCfg>();

    private Commands commands;

    private PlayerManager playerManager;

    private final Logger log = Logger.getLogger("Essence");


    @Override
    public void onDisable() {
        instance = null;
        log("disabled");
    }

    @Override
    public void onEnable() {
        instance = this;
        log.setParent(this.getLogger());

        setupNMS();

        registerEvents();

        messages = new MessagesCfg("plugins/Essence/Messages.yml");
        modules = new ModulesCfg("plugins/Essence/Modules.yml");
        commandsCfg = new CommandsCfg("plugins/Essence/Commands.yml");
        cmdOptionsCfg = new CommandOptionsCfg("plugins/Essence/CommandOptions.yml");

        loadAliases();

        //TODO: Have a class for modules were it would create the config and such.
        warps = new Warps("plugins/Essence/data/Warps.yml");

        commands = new Commands(this);

        playerManager = new PlayerManager(this);

        log("loaded successfully");
    }

    private void setupNMS() {
        String version;
        try {
            version = getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            return;
        }

        switch (version) {
            case "v1_8_R1" :
                iSkull = new SkullUtil_v1_8_R1();
                iTitle = new Title_1_8_R1();
                log("This version of Essence is fully compatible with your server version!");
                break;
            case "v1_8_R2" :
                iSkull = new SkullUtil_1_8_R2();
                iTitle = new Title_1_8_R2();
                log("This version of Essence is fully compatible with your server version!");
                break;
            case "v1_8_R3" :
                iSkull = new SkullUtil_1_8_R3();
                iTitle = new Title_1_8_R3();
                log("This version of Essence is fully compatible with your server version!");
                break;
            default:
                warn("This version of Essence is not fully compatible with your server version!");
                warn("Your server version: " + version);
                warn("Earliest compatible version: v1_8_R1");
                warn("Latest compatible version: v1_8_R3");
                warn("If there is a newer server version we will update the plugin as soon as possible.");
                warn("Essence will still work fine but certain features will be disabled.");
        }
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
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
        aliases.put(AliasType.HORSE_COLOR, new AliasesCfg("plugins/Essence/aliases/HorseColors.yml", AliasType.HORSE_COLOR));
        aliases.put(AliasType.HORSE_STYLE, new AliasesCfg("plugins/Essence/aliases/HorseStyles.yml", AliasType.HORSE_STYLE));
        aliases.put(AliasType.OCELOT_TYPES, new AliasesCfg("plugins/Essence/aliases/OcelotTypes.yml", AliasType.OCELOT_TYPES));
        aliases.put(AliasType.RABBIT_TYPES, new AliasesCfg("plugins/Essence/aliases/RabbitTypes.yml", AliasType.RABBIT_TYPES));
        aliases.put(AliasType.BANNER_PATTERNS, new AliasesCfg("plugins/Essence/aliases/BannerPatterns.yml", AliasType.BANNER_PATTERNS));
        aliases.put(AliasType.TREES, new AliasesCfg("plugins/Essence/aliases/Trees.yml", AliasType.TREES));
        aliases.put(AliasType.SOUNDS, new AliasesCfg("plugins/Essence/aliases/Sounds.yml", AliasType.SOUNDS));
    }

    public void log(Object msg) {
        log.info("[Essence " + getDescription().getVersion() + "] " + msg.toString());
    }
    public void warn(Object msg) {
        log.warning("[Essence " + getDescription().getVersion() + "] " + msg.toString());
    }
    public void logError(Object msg) {
        log.severe("[Essence " + getDescription().getVersion() + "] " + msg.toString());
    }

    
    public static Essence inst() {
        return instance;
    }

    /*public Gson getGson() {
        return gson;
    }*/


    public ISkull getISkull() {
        return iSkull;
    }


    public MessagesCfg getMessages() {
        return messages;
    }

    public ModulesCfg getmodules() {
        return modules;
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

    public ITitle getITitle() {
        return iTitle;
    }

}
