package info.mcessence.essence.commands;

import info.mcessence.essence.Essence;
import info.mcessence.essence.ModuleCategory;
import info.mcessence.essence.commands.item.ItemInfoCmd;
import info.mcessence.essence.commands.location.DelWarpCmd;
import info.mcessence.essence.commands.location.SetWarpCmd;
import info.mcessence.essence.commands.location.WarpCmd;
import info.mcessence.essence.commands.location.WarpsCmd;
import info.mcessence.essence.commands.misc.TestCmd;
import info.mcessence.essence.commands.player.NicknameCmd;
import info.mcessence.essence.commands.player.SudoCmd;
import info.mcessence.essence.commands.plugin.MainPluginCmd;
import info.mcessence.essence.commands.teleport.TpCmd;
import info.mcessence.essence.commands.teleport.TpHereCmd;
import info.mcessence.essence.commands.world.LightningCmd;
import info.mcessence.essence.commands.world.TreeCmd;
import info.mcessence.essence.config.CommandsCfg;
import info.mcessence.essence.commands.player_status.*;

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
        registerCommand(TestCmd.class, "test", "", "Command for testing plugin functionality.", new String[] {});
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
        registerCommand(RemoveEffectCmd.class, "removeeffect", "removeeffect", "Remove potion effects", new String[] {"remeffect", "remeffects", "cleareffect", "cleareffects", "removeeffects"});
        registerCommand(ItemInfoCmd.class, "iteminfo", "iteminfo", "Show item detailed item information.", new String[] {"itemdb"});
        registerCommand(BurnCmd.class, "burn", "burn", "Set yourself or another player on fire for the specified amount of seconds. (or ticks)", new String[] {"ignite"});
        registerCommand(FlyCmd.class, "fly", "fly", "Toggle flight on/off.", new String[] {"flight"});
        registerCommand(WalkspeedCmd.class, "walkspeed", "walkspeed", "Change your walking speed.", new String[] {"walkingspeed"});
        registerCommand(FlyspeedCmd.class, "flyspeed", "flyspeed", "Change your flying speed.", new String[] {"flyingspeed"});
        registerCommand(InvseeCmd.class, "invsee", "invsee", "View another player's inventory.", new String[] {});
        registerCommand(EnderchestCmd.class, "enderchest", "enderchest", "View your or another player's enderchest", new String[] {});
        registerCommand(SuicideCmd.class, "suicide", "suicide", "Kill yourself", new String[] {});
        registerCommand(KillCmd.class, "kill", "kill", "Kill someone else", new String[] {"slay"});
        registerCommand(TreeCmd.class, "tree", "tree", "Generate a tree somewhere in the world", new String[] {});
        registerCommand(GodCmd.class, "god", "god", "Turns your or another player's god mode on or off.", new String[] {"immortal", "invulnerable", "immortality", "invulnerability"});
        registerCommand(TpHereCmd.class, "tphere", "tphere", "Teleports a player to your location.", new String[] {});
        registerCommand(SudoCmd.class, "sudo", "sudo", "Execute a command on someone's behalf.", new String[] {});
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
