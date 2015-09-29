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

package org.essencemc.essencecore.commands;

import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.ModuleCategory;
import org.essencemc.essencecore.commands.item.ItemInfoCmd;
import org.essencemc.essencecore.commands.location.DelWarpCmd;
import org.essencemc.essencecore.commands.location.SetWarpCmd;
import org.essencemc.essencecore.commands.location.WarpCmd;
import org.essencemc.essencecore.commands.location.WarpsCmd;
import org.essencemc.essencecore.commands.misc.SummonCmd;
import org.essencemc.essencecore.commands.misc.TestCmd;
import org.essencemc.essencecore.commands.player.MessageCmd;
import org.essencemc.essencecore.commands.player.NicknameCmd;
import org.essencemc.essencecore.commands.player.SudoCmd;
import org.essencemc.essencecore.commands.player_status.*;
import org.essencemc.essencecore.commands.plugin.MainPluginCmd;
import org.essencemc.essencecore.commands.punishments.BanCmd;
import org.essencemc.essencecore.commands.teleport.TpCmd;
import org.essencemc.essencecore.commands.teleport.TpHereCmd;
import org.essencemc.essencecore.commands.world.LightningCmd;
import org.essencemc.essencecore.commands.world.TreeCmd;
import org.essencemc.essencecore.config.CommandsCfg;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Commands {

    private EssenceCore ess;
    private CommandsCfg cfg;
    public List<EssenceCommand> commands = new ArrayList<EssenceCommand>();


    public Commands(EssenceCore ess) {
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
        registerCommand(BanCmd.class, "ban", "ban", "Bans a player from the server.", new String[] {});
        registerCommand(SummonCmd.class, "summon", "summon", "Summons any entity with any specified data.", new String[] {"spawnmob", "sm", "spawnentity", "se"});
        registerCommand(MessageCmd.class, "message", "message", "Sends a private message to another online player.", new String[] {"msg", "tell"});
    }

    /**
     * Register a command.
     * If the command is already registered it will update the data based of the config values.
     * It will only register the command if it's enabled in the config.
     * If the command is already registered and disabled in the config it will be unregistered.
     */
    public void registerCommand(Class<? extends EssenceCommand> clazz, String label, String module, String description, String[] aliases) {
        if (!module.isEmpty()) {
            ess.getModuleCfg().registerModule(ModuleCategory.COMMAND, module);
        }
        for (EssenceCommand cmd : commands) {
            if (cmd.getLabel().equals(label)) {
                cmd.unregister();
                if (module.isEmpty() || ess.getModuleCfg().isEnabled(ModuleCategory.COMMAND, module)) {
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
        if (!module.isEmpty() && !ess.getModuleCfg().isEnabled(ModuleCategory.COMMAND, module)) {
            return;
        }

        try {
            EssenceCommand cmd = clazz.getConstructor(EssenceCore.class, String.class, String.class, String.class, List.class).newInstance(ess, label, cfg.getDescription(label), cfg.getPermission(label), cfg.getAliases(label));
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
