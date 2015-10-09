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

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.commands.arguments.ArgumentParseResult;
import org.essencemc.essencecore.commands.arguments.ArgumentParseResults;
import org.essencemc.essencecore.commands.arguments.ArgumentRequirement;
import org.essencemc.essencecore.commands.arguments.CmdArgument;
import org.essencemc.essencecore.commands.links.*;
import org.essencemc.essencecore.commands.links.internal.CommandLink;
import org.essencemc.essencecore.message.EText;
import org.essencemc.essencecore.message.Message;
import org.essencemc.essencecore.util.Util;

import java.lang.reflect.Field;
import java.util.*;

public abstract class EssenceCommand implements CommandExecutor, TabExecutor, Listener {

    protected final Plugin plugin;
    protected final String label;
    protected String description;
    protected List<String> aliases;
    protected String permission;

    protected CmdArgument[] cmdArgs;
    public Map<String, CommandModifier> modifiers = new HashMap<String, CommandModifier>();
    public Map<String, CommandOption> cmdOptions = new HashMap<String, CommandOption>();
    public Map<String, CommandOptionalArg> optionalArgs = new HashMap<String, CommandOptionalArg>();
    public List<CommandLink> links = new ArrayList<CommandLink>();

    protected static CommandMap commandMap;

    public EssenceCommand(Plugin plugin, String label, String description, String permission, List<String> aliases) {
        this.plugin = plugin;
        this.label = label;

        addModifier("-?", Message.MOD_HELP.msg());
        addModifier("-s", Message.MOD_SILENT.msg());

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
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
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

    /** Get the main EssenceCore plugin instance */
    public Plugin getPlugin() {
        return plugin;
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
     * If the sub permission starts with essence. it will not append the permission and it's a regular permission check.
     * So if you pass in essence.meta.name it would return true if the player has essence.meta.name or essence.*
     */
    public boolean hasPermission(CommandSender sender, String subPermission) {
        if (subPermission.contains("essence.")) {
            if (sender.hasPermission("essence.*") || sender.hasPermission(subPermission)) {
                return true;
            }
            return false;
        }
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
        //Add optional args with default values from command options.
        for (Map.Entry<String, CommandOptionalArg> entry : optionalArgs.entrySet()) {
            if (entry.getValue().getArg().hasDefault()) {
                result.addOptionalArg(entry.getKey(), entry.getValue().getArg().getDefault());
            }
        }

        List<String> argsList = new ArrayList<String>();
        List<String> keys = new ArrayList<String>();
        //Loop through all the args filtering out all modifiers and optional arguments.
        for (String arg : args) {
            if (arg.startsWith("-") && modifiers.containsKey(arg.toLowerCase())) {
                //Show help when the -? modifier is used.
                if (arg.equalsIgnoreCase("-?")) {
                    showHelp(sender);
                    result.success = false;
                    return result;
                }
                String perm = modifiers.get(arg).getPerm();
                if (!hasPermission(sender, perm)) {
                    Message.NO_PERM.msg(true, true, castPlayer(sender)).parseArgs(perm.startsWith("essence.") ? perm : permission + "." + perm).send(sender);
                    result.success = false;
                    return result;
                }
                //Add the modifier to the result.
                result.addModifier(arg);
                keys.add(arg);
            } else {
                String[] split = arg.split(":");
                //Make sure the argument specified is a valid argument for the command.
                if (optionalArgs.containsKey(split[0].toLowerCase())) {
                    //Get the arg and parse it.
                    Argument optionalArg = optionalArgs.get(split[0]).getArg();
                    optionalArg.parse(split.length > 1 ? split[1] : "");
                    //If he parsing wasn't successful send an error and fail.
                    //It will allow empty arguments if there is a default or cached value.
                    if (!optionalArg.isValid()) {
                        if (!optionalArg.hasDefault() || (split.length > 1 && !split[1].isEmpty())) {
                            optionalArg.getError().addPrefix().parsePlaceholders(castPlayer(sender)).toJSON().send(sender);
                            result.success = false;
                            return result;
                        }
                    }
                    keys.add(split[0]);
                    //Set the optional argument in the result.
                    if (optionalArg.getValue() != null) {
                        result.addOptionalArg(split[0], optionalArg.getValue());
                    } else {
                        result.addOptionalArg(split[0], optionalArg.getDefault());
                    }
                } else {
                    //Add it to args list if it's not a modifier or optional arg.
                    argsList.add(arg);
                }
            }
        }
        //Set the args back to an array with arguments that don't have any modifiers/optional args in it.
        args = argsList.toArray(new String[argsList.size()]);
        result.setArgs(args);

        //Handle all links that modify command arguments before parsing them.
        List<CmdArgument> cmdArgsClone = new ArrayList<CmdArgument>(Arrays.asList(cmdArgs.clone()));
        for (CommandLink link : links) {
            //Skip all links that don't modify command args.
            if (!(link instanceof RemoveLink) && !(link instanceof ShiftLink) && !(link instanceof MakeOptionalLink)) {
                continue;
            }
            //Make sure the first value for the link has been specified.
            if (!keys.contains(link.getFirst())) {
                continue;
            }
            //Go through all args and check if any match with the second value of the link.
            for (int i = 0; i < cmdArgs.length; i++) {
                CmdArgument cmdArg = cmdArgs[i];
                if (link.getSecond().equalsIgnoreCase(cmdArg.getName(false))) {
                    //Remove the argument
                    if (link instanceof RemoveLink) {
                        cmdArgsClone.remove(i);
                    }
                    //Make optional
                    if (link instanceof MakeOptionalLink) {
                        cmdArg.setRequirement(ArgumentRequirement.OPTIONAL);
                    }
                    //Shift to other index.
                    if (link instanceof ShiftLink) {
                        cmdArgsClone.remove(i);
                        cmdArgsClone.add(((ShiftLink)link).getIndex(), cmdArg);
                    }
                    break;
                }
            }
        }

        //Go through all the main command args and parse them.
        int index = 0;
        for (CmdArgument cmdArg : cmdArgsClone) {
            //Check if an argument is specified in the command for this argument.
            if (args.length > index) {
                //Parse the value specified.
                ArgumentParseResult parsed = cmdArg.parse(cmd, sender, args.length > index ? args[index] : "");
                if (!parsed.success) {
                    result.success = false;
                    return result;
                }
                keys.add(cmdArg.getName(false));
                result.setArg(cmdArg.getName(false), parsed.getValue());
            } else {
                //If there is no value specified for this argument and it's a required argument send and error.
                //If it's an optional argument the value will be set to null.
                if (cmdArg.isRequired(sender)) {
                    Message.CMD_INVALID_USAGE.msg(true, true, castPlayer(sender)).parseArgs(cmd.getUsage(sender)).send(sender);
                    result.success = false;
                    return result;
                } else {
                    ArgumentParseResult parsed = new ArgumentParseResult();
                    parsed.success = true;
                    parsed.setValue(null);
                    result.setArg(cmdArg.getName(false), parsed.getValue());
                }
            }
            index++;
        }

        //Lastly we need to check for any conflicts with links that have conflict or link mode.
        for (CommandLink link : links) {
            if (link instanceof ConflictLink) {
                //If both are specified send an error.
                if (keys.contains(link.getFirst()) && keys.contains(link.getSecond())) {
                    Message.CMD_LINK_CONFLICT.msg(true, true, castPlayer(sender)).parseArgs(link.getFirst(), link.getSecond()).send(sender);
                    result.success = false;
                    return result;
                }
            }
            if (link instanceof LinkLink) {
                //If one of these isn't specified and the other is send an error.
                if (keys.contains(link.getFirst()) && !keys.contains(link.getSecond())) {
                    Message.CMD_LINK_LINK.msg(true, true, castPlayer(sender)).parseArgs(link.getFirst(), link.getSecond()).send(sender);
                    result.success = false;
                    return result;
                } else if (keys.contains(link.getSecond()) && !keys.contains(link.getFirst())) {
                    Message.CMD_LINK_LINK.msg(true, true, castPlayer(sender)).parseArgs(link.getSecond(), link.getFirst()).send(sender);
                    result.success = false;
                    return result;
                }
            }
        }

        return result;
    }

    public void addCommandOption(String key, EText infoMessage, Argument optionType) {
        addCommandOption(key, infoMessage, optionType, true);
    }

    public void addCommandOption(String key, EText infoMessage, Argument argument, boolean addAsArgument) {
        cmdOptions.put(key, new CommandOption(infoMessage, argument));
        if (addAsArgument) {
            addOptionalArgument(key, argument.clone(), infoMessage);
        }
        EssenceCore.inst().getCmdOptions().registerOption(this, key);
    }

    public void addOptionalArgument(String key, Argument argumentType) {
        addOptionalArgument(key, argumentType, null, "");
    }

    public void addOptionalArgument(String key, Argument argumentType, String permission) {
        addOptionalArgument(key, argumentType, null, permission);
    }

    public void addOptionalArgument(String key, Argument argumentType, EText info) {
        addOptionalArgument(key, argumentType, info, "");
    }

    public void addOptionalArgument(String key, Argument argumentType, EText info, String permission) {
        optionalArgs.put(key, new CommandOptionalArg(info, argumentType, permission));
    }

    public void addModifier(String modifier, EText info) {
        addModifier(modifier, info, "");
    }

    public void addModifier(String modifier, EText info, String permission) {
        if (!modifier.startsWith("-")) {
            modifier = "-" + modifier;
        }
        modifiers.put(modifier, new CommandModifier(info, permission));
    }

    public void addLink(CommandLink link) {
        links.add(link);
    }

    private List<CommandLink> getLinks(String key) {
        List<CommandLink> commandLinks = new ArrayList<CommandLink>();
        for (CommandLink link : links) {
            if (link.getFirst().equalsIgnoreCase(key) || link.getSecond().equalsIgnoreCase(key)) {
                commandLinks.add(link);
            }
        }
        return commandLinks;
    }

    public void showHelp(CommandSender sender) {
        String str_aliases = Util.implode(getAliases(), Message.CMD_HELP_SEPARATOR.msg().getText());

        List<String> argList = new ArrayList<String>();
        for (CmdArgument arg : cmdArgs) {
            argList.add(Message.CMD_HELP_ARG.msg().parseArgs(arg.getName(sender), arg.getDescription().getText()).getText());
        }
        String str_usage = label + " " + Util.implode(argList, " ");

        List<String> modifierList = new ArrayList<String>();
        for (Map.Entry<String, CommandModifier> entry : modifiers.entrySet()) {
            modifierList.add(Message.CMD_HELP_MODIFIER.msg().parseArgs(entry.getKey(), entry.getValue().getInfo().getText()).getText());
        }
        String str_modifiers = Util.implode(modifierList, Message.CMD_HELP_SEPARATOR.msg().getText());

        List<String> optArgList = new ArrayList<String>();
        for (Map.Entry<String, CommandOptionalArg> entry : optionalArgs.entrySet()) {
            Argument arg = entry.getValue().getArg();
            optArgList.add(Message.CMD_HELP_OPT_ARG.msg().parseArgs(entry.getKey(), arg.getDescription().getText(), arg.getDefault() == null ? "&c&o&mundefined" : arg.getDefault().toString()).getText());
        }
        String str_optargs = Util.implode(optArgList, Message.CMD_HELP_SEPARATOR.msg().getText());

        List<String> optList = new ArrayList<String>();
        for (Map.Entry<String, CommandOption> entry : cmdOptions.entrySet()) {
            optList.add(Message.CMD_HELP_OPTION.msg().parseArgs(entry.getKey(), entry.getValue().getInfo().getText(), entry.getValue().getArg().getValue().toString()).getText());
        }
        String str_options = Util.implode(optList, Message.CMD_HELP_SEPARATOR.msg().getText());

        String none = Message.CMD_HELP_NONE.msg().color().getText();

        HashMap<String, String> values = new HashMap<String, String>();
        values.put("cmd", label);
        values.put("desc", Util.color(description));
        values.put("usage", Util.color(str_usage));
        values.put("perm", permission.isEmpty() ? none : permission);
        values.put("aliases", str_aliases.isEmpty() ? none : Util.color(str_aliases));
        values.put("modifiers", str_modifiers.isEmpty() ? none : Util.color(str_modifiers));
        values.put("opt-args", str_optargs.isEmpty() ? none : Util.color(str_optargs));
        values.put("options", str_options.isEmpty() ? none : Util.color(str_options));

        Bukkit.broadcastMessage(Util.removeColor(Message.CMD_HELP_ESSENCE.msg().parseArgs(values).getText()));
        Bukkit.broadcastMessage(Util.removeColor(Message.CMD_HELP_ESSENCE.msg().parseArgs(values).toJSON().getText()));
        Message.CMD_HELP_ESSENCE.msg().parseArgs(values).toJSON().send(sender);
    }

    public Player castPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return (Player)sender;
        }
        return null;
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