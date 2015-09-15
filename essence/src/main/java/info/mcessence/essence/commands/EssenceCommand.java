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

package info.mcessence.essence.commands;

import info.mcessence.essence.Essence;
import info.mcessence.essence.message.EMessage;
import info.mcessence.essence.message.Message;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResult;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.cmd_options.CommandOption;
import info.mcessence.essence.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EssenceCommand implements CommandExecutor, TabExecutor, Listener {

    protected final Essence ess;
    protected final String label;
    protected String description;
    protected List<String> aliases;
    protected String permission;

    protected CmdArgument[] cmdArgs;
    public Map<String, EMessage> modifiers = new HashMap<String, EMessage>();
    public Map<String, CommandOption> cmdOptions = new HashMap<String, CommandOption>();
    public Map<String, CommandOption> optionalArgs = new HashMap<String, CommandOption>();

    protected static CommandMap commandMap;

    public EssenceCommand(Essence ess, String label, String description, String permission, List<String> aliases) {
        this.ess = ess;
        this.label = label;

        modifiers.put("-?", Message.MOD_HELP.msg());
        modifiers.put("-s", Message.MOD_SILENT.msg());

        loadData(description, permission, aliases);
    }

    /** Update the data with the data specified. */
    public void loadData(String description, String permission, List<String> aliases) {
        this.description = description;
        this.permission = permission;
        this.aliases = aliases;
    }

    /** Register the command on the server */
    public void register() {
        ReflectCommand cmd = new ReflectCommand(this.label);
        if (this.aliases != null) cmd.setAliases(this.aliases);
        if (this.description != null) cmd.setDescription(this.description);
        if (getUsage() != null) cmd.setUsage(this.getUsage());
        getCommandMap().register("", cmd);
        cmd.setExecutor(this);
        ess.getServer().getPluginManager().registerEvents(this, ess);
    }

    /** Unregister the command and all aliases from the server */
    public void unregister() {
        HandlerList.unregisterAll(this);
        unregisterCmd(label);
        for (String alias : aliases) {
            unregisterCmd(alias);
        }
    }

    /** Unregister a command or alias */
    private void unregisterCmd(String cmdLabel) {
        Command cmd = getCommandMap().getCommand(cmdLabel);
        if (cmd != null) {
            cmd.unregister(getCommandMap());
        }

        try {
            final Field f = commandMap.getClass().getDeclaredField("knownCommands");
            f.setAccessible(true);
            Map<String, Command> cmds = (Map<String, Command>) f.get(commandMap);
            cmds.remove(cmdLabel);
            f.set(commandMap, cmds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Get the server CommandMap */
    final CommandMap getCommandMap() {
        if (commandMap == null) {
            try {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (CommandMap) f.get(Bukkit.getServer());
                return getCommandMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (commandMap != null) {
            return commandMap;
        }
        return getCommandMap();
    }

    /** Get the main Essence plugin instance */
    public Essence getEss() {
        return ess;
    }


    /** Get the command label/command */
    public String getLabel() {
        return label;
    }

    /** Get the command description */
    public String getDescription() {
        return description;
    }

    /** Get the command usage */
    public String getUsage() {
        return getUsage(null);
    }

    /** Get the command usage */
    public String getUsage(CommandSender sender) {
        List<String> args = new ArrayList<String>();
        if (cmdArgs != null && cmdArgs.length > 0) {
            for (CmdArgument arg : cmdArgs) {
                args.add(arg.getName(sender));
            }
        }
        return "/" + label  + (args.isEmpty() ? "" : " " + Util.implode(args, " "));
    }

    /** Get the base permission */
    public String getPermission() {
        return permission;
    }

    /** Returns if the given CommandSender has permission to run this command */
    public boolean hasPermission(CommandSender sender) {
        return hasPermission(sender, "");
    }

    /**
     * Returns if the given CommandSender has permission to run this command
     * The sub permission will be appended to the permission.
     * Example: If you specify 'others' it would be 'essence.heal.others' on the heal command.
     * If the player has essence.* or essence.heal.* or essence.heal.others it will return true otherwise false.
     */
    public boolean hasPermission(CommandSender sender, String subPermission) {
        if (permission == null || permission.isEmpty()) {
            return true;
        }
        if (sender.hasPermission("essence.*") || sender.hasPermission(permission + ".*") || sender.hasPermission(permission + (subPermission.isEmpty() ? "" : "." + subPermission))) {
            return true;
        }
        return false;
    }

    /** Get a list of all the command aliases */
    public List<String> getAliases() {
        return aliases;
    }


    public ArgumentParseResults parseArgs(EssenceCommand cmd, CommandSender sender, String[] args) {
        ArgumentParseResults result = new ArgumentParseResults();
        List<String> argsList = new ArrayList<String>();
        for (String arg : args) {
            if (arg.startsWith("-") && modifiers.containsKey(arg.toLowerCase())) {
                result.addModifier(arg);
            } else if (arg.contains(":")) {
                String[] split = arg.split(":");
                if (split.length > 1 && !split[0].isEmpty() && !split[1].isEmpty() && optionalArgs.containsKey(split[0])) {
                    if (!optionalArgs.get(split[0]).isValid(split[1])) {
                        sender.sendMessage(Message.INVALID_OPTIONAL_ARGUMENT.msg().getMsg(true, split[0], optionalArgs.get(split[0]).getClass().getSimpleName(), split[1]));
                        result.success = false;
                        return result;
                    }
                    optionalArgs.get(split[0]).setValue(split[1]);
                    result.addOptionalArg(split[0], optionalArgs.get(split[0]).getValue());
                } else {
                    argsList.add(arg);
                }
            } else {
                argsList.add(arg);
            }
        }
        args = argsList.toArray(new String[argsList.size()]);
        result.setArgs(args);

        int index = 0;
        for (CmdArgument cmdArg : cmdArgs) {
            if (args.length > index) {
                ArgumentParseResult parsed = cmdArg.parse(cmd, sender, args.length > index ? args[index] : "");
                if (!parsed.success) {
                    result.success = false;
                    return result;
                }
                result.setValue(index, parsed);
            } else {
                if (cmdArg.isRequired(sender)) {
                    sender.sendMessage(Message.CMD_INVALID_USAGE.msg().getMsg(true, cmd.getUsage(sender)));
                    result.success = false;
                    return result;
                } else {
                    ArgumentParseResult parsed = new ArgumentParseResult();
                    parsed.success = true;
                    parsed.setValue(null);
                    result.setValue(index, parsed);
                }
            }
            index++;
        }
        return result;
    }

    public void addCommandOption(String key, CommandOption optionType) {
        addCommandOption(key, optionType, true);
    }

    public void addCommandOption(String key, CommandOption optionType, boolean addAsArgument) {
        cmdOptions.put(key, optionType);
        if (addAsArgument) {
            optionalArgs.put(key, optionType);
        }
        ess.getCmdOptions().registerOption(this, key);
    }

    public void addOptionalArgument(String key, CommandOption argumentType) {
        optionalArgs.put(key, argumentType);
    }

    public void addModifier(String modifier, EMessage info) {
        if (!modifier.startsWith("-")) {
            modifier = "-" + modifier;
        }
        modifiers.put(modifier, info);
    }

    /** Method to be overwritten by each command */
    @Override
    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    /**
     * Optional method to be overwritten by each command
     * By default it will automatically tab complete all specified arguments if they have tab completion.
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String message, String[] args) {
        if (cmdArgs.length < args.length) {
            return null;
        }
        return cmdArgs[args.length-1].tabComplete(sender, args[args.length-1]);
    }
}