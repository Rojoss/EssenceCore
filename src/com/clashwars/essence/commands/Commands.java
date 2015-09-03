package com.clashwars.essence.commands;

import com.clashwars.essence.Essence;
import com.clashwars.essence.ModuleCategory;
import com.clashwars.essence.commands.location.DelWarpCmd;
import com.clashwars.essence.commands.location.SetWarpCmd;
import com.clashwars.essence.commands.location.WarpCmd;
import com.clashwars.essence.commands.location.WarpsCmd;
import com.clashwars.essence.commands.player.NicknameCmd;
import com.clashwars.essence.commands.player_status.*;
import com.clashwars.essence.commands.plugin.MainPluginCmd;
import com.clashwars.essence.commands.teleport.TpCmd;
import com.clashwars.essence.commands.world.LightningCmd;
import com.clashwars.essence.config.CommandsCfg;
import com.clashwars.essence.config.data.Warps;

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
        registerCommand(MainPluginCmd.class, "essence", "", "Main plugin command and config reloading", new String[] {"essentials", "essential"});
        registerCommand(HealCmd.class, "heal", "heal", "Heal a player", new String[] {"health", "sethealth"});
        registerCommand(FeedCmd.class, "feed", "feed", "Feed a player", new String[] {"hunger", "eat"});
        registerCommand(LightningCmd.class, "lightning", "lightning", "Strike lightning somewhere", new String[] {"smite"});
        registerCommand(GamemodeCmd.class, "gamemode", "gamemode", "Change a player his gamemmode", new String[] {"gm"});
        registerCommand(SetWarpCmd.class, "setwarp", "warps", "Set a warp with the given name", new String[] {"addwarp", "warpset"});
        registerCommand(DelWarpCmd.class, "delwarp", "warps", "Delete a warp with the given name", new String[] {"warpdel", "deletewarp", "rmwarp", "removewarp", "warpdelete", "warprm", "warpremove"});
        registerCommand(WarpsCmd.class, "warps", "warps", "List all the warps (for a world)", new String[] {"warplist"});
        registerCommand(WarpCmd.class, "warp", "warps", "Teleport to a warp", new String[] {});
        registerCommand(TpCmd.class, "tp", "tp", "Teleport to a player", new String[] {"teleport", "tele"});
        registerCommand(NicknameCmd.class, "nickname", "nickname", "Change your nickname", new String[] {"nick", "displayname", "name"});
        registerCommand(RemoveEffectCmd.class, "removeeffect", "removeeffect", "Remove potion effects", new String[] {"remeffect"});
    }

    /**
     * Register a command.
     * If the command is already registered it will update the data based of the config values.
     * It will only register the command if it's enabled in the config.
     * If the command is already registered and disabled in the config it will be unregistered.
     */
    public void registerCommand(Class<? extends EssenceCommand> clazz, String label, String module, String description, String[] aliases) {
        if (!module.isEmpty()) {
            ess.getmodules().registerModule(ModuleCategory.COMMAND, module);
        }
        for (EssenceCommand cmd : commands) {
            if (cmd.getLabel().equals(label)) {
                cmd.unregister();
                if (module.isEmpty() || ess.getmodules().isEnabled(ModuleCategory.COMMAND, module)) {
                    cmd.loadData(cfg.getDescription(label), cfg.getPermission(label), cfg.getAliases(label));
                    cmd.register();
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
            EssenceCommand cmd = clazz.getConstructor(Essence.class, String.class, String.class, String.class, List.class).newInstance(ess, label, cfg.getDescription(label), cfg.getPermission(label), cfg.getAliases(label));
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
