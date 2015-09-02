package com.clashwars.essence;

import com.clashwars.essence.commands.Commands;
import com.clashwars.essence.config.CommandOptionsCfg;
import com.clashwars.essence.config.CommandsCfg;
import com.clashwars.essence.config.MessagesCfg;
import com.clashwars.essence.config.ModulesCfg;
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

        registerEvents();

        messages = new MessagesCfg("plugins/Essence/Messages.yml");
        modules = new ModulesCfg("plugins/Essence/Modules.yml");
        commandsCfg = new CommandsCfg("plugins/Essence/Commands.yml");
        cmdOptionsCfg = new CommandOptionsCfg("plugins/Essence/CommandOptions.yml");

        commands = new Commands(this);

        log("loaded successfully");
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



    public Commands getCommands() {
        return commands;
    }
}
