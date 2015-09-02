package com.clashwars.essence.commands.internal;

import com.clashwars.essence.Essence;
import com.clashwars.essence.ModuleCategory;
import com.clashwars.essence.commands.*;
import com.clashwars.essence.commands.EssenceCmd;
import com.clashwars.essence.commands.FeedCmd;
import com.clashwars.essence.commands.GamemodeCmd;
import com.clashwars.essence.commands.HealCmd;
import com.clashwars.essence.config.CommandsCfg;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Commands {

    private Essence ess;
    private CommandsCfg cfg;
    public List<EssenceCommand> commands = new ArrayList<EssenceCommand>();


    public Commands(Essence ess) {
        this.ess = ess;
        this.cfg = ess.getCommandsCfg();
        registerCommands();
    }

    /** Register all Essence commands */
    public void registerCommands() {
        registerCommand(EssenceCmd.class, "essence", "", "/essence [reload]", "Main plugin command and config reloading", new String[] {"essentials", "essential"});
        registerCommand(HealCmd.class, "heal", "heal", "/heal [player] [max]", "Heal a player", new String[] {"health", "sethealth"});
        registerCommand(FeedCmd.class, "feed", "feed", "/feed [player] [amount]", "Feed a player", new String[] {"hunger", "eat"});
        registerCommand(LightningCmd.class, "lightning", "lightning", "/lightning {location|player}", "Strike lightning somewhere", new String[] {"smite"});
        registerCommand(GamemodeCmd.class, "gamemode", "gamemode", "/gamemode {mode} [player]", "Change a player his gamemmode", new String[] {"gm"});
    }

    /**
     * Register a command.
     * If the command is already registered it will update the data based of the config values.
     * It will only register the command if it's enabled in the config.
     * If the command is already registered and disabled in the config it will be unregistered.
     */
    //TODO: Create the usage string based on arguments
    public void registerCommand(Class<? extends EssenceCommand> clazz, String label, String module, String usage, String description, String[] aliases) {
        if (!module.isEmpty()) {
            ess.getmodules().registerModule(ModuleCategory.COMMAND, module);
        }
        for (EssenceCommand cmd : commands) {
            if (cmd.getLabel().equals(label)) {
                cmd.unregister();
                if (module.isEmpty() || ess.getmodules().isEnabled(ModuleCategory.COMMAND, module)) {
                    cmd.loadData(usage, cfg.getDescription(label), cfg.getPermission(label), cfg.getAliases(label));
                } else {
                    commands.remove(cmd);
                }
                return;
            }
        }

        if (clazz == null) {
            ess.logError("Failed to register the command " + label);
            ess.logError("No class found for this command or it doesn't extend EssenceCommand.");
            return;
        }

        cfg.registerCommand(label, description, "essence." + label, aliases);
        if (!module.isEmpty() && !ess.getmodules().isEnabled(ModuleCategory.COMMAND, module)) {
            return;
        }

        try {
            EssenceCommand cmd = clazz.getConstructor(Essence.class, String.class, String.class, String.class, String.class, List.class).newInstance(ess, label, usage, cfg.getDescription(label), cfg.getPermission(label), cfg.getAliases(label));
            commands.add(cmd);
        } catch (InstantiationException e) {
            ess.logError("Failed to register the command " + label);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            ess.logError("Failed to register the command " + label);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            ess.logError("Failed to register the command " + label);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            ess.logError("Failed to register the command " + label);
            e.printStackTrace();
        }
    }

}
