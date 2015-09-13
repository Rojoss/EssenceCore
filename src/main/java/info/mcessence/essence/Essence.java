package info.mcessence.essence;

import info.mcessence.essence.commands.Commands;
import info.mcessence.essence.config.CommandOptionsCfg;
import info.mcessence.essence.config.CommandsCfg;
import info.mcessence.essence.config.MessagesCfg;
import info.mcessence.essence.config.ModulesCfg;
import info.mcessence.essence.config.aliases.ItemAliases;
import info.mcessence.essence.config.data.Warps;
import com.google.gson.Gson;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Essence extends JavaPlugin {

    private static Essence instance;
    private Gson gson = new Gson();

    private MessagesCfg messages;
    private ModulesCfg modules;
    private CommandsCfg commandsCfg;
    private CommandOptionsCfg cmdOptionsCfg;
    private Warps warps;

    private info.mcessence.essence.nms.NmsUtil nmsUtil;

    private ItemAliases itemAliases;

    private Commands commands;

    private final Logger log = Logger.getLogger("Essence");


    @Override
    public void onDisable() {
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

        itemAliases = new ItemAliases("plugins/Essence/aliases/Items.yml");

        //TODO: Have a class for modules were it would create the config and such.
        warps = new Warps("plugins/Essence/data/Warps.yml");

        commands = new Commands(this);

        log("loaded successfully");
    }

    private void setupNMS() {
        String version;
        try {
            version = getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            return;
        }
        if (version.equals("v1_8_R3")) {
            nmsUtil = new info.mcessence.essence.nms.v1_8_R3.NmsUtil();
        }

        if (nmsUtil == null) {
            warn("This version of Essence is not fully compatible with your server version!");
            warn("Your server version: " + version);
            warn("Earliest compatible version: v1_8_R3");
            warn("Latest compatible version: v1_8_R3");
            warn("If there is a newer server version we will update the plugin as soon as possible.");
            warn("Essence will still work fine but certain features will be disabled.");
        } else {
            log("This version of Essence is fully compatible with your server version!");
        }
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
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

    public Gson getGson() {
        return gson;
    }

    public info.mcessence.essence.nms.NmsUtil getNmsUtil() {
        return nmsUtil;
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


    public Commands getCommands() {
        return commands;
    }
}
