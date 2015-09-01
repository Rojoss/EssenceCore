package com.clashwars.essence.commands.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.commands.arguments.internal.ArgumentParseResult;
import com.clashwars.essence.commands.arguments.internal.ArgumentParseResults;
import com.clashwars.essence.commands.arguments.internal.CmdArgument;
import com.clashwars.essence.commands.options.CommandOption;
import com.clashwars.essence.util.Debug;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class EssenceCommand implements CommandExecutor, TabExecutor, Listener {

    protected final Essence ess;
    protected final String label;
    protected String description;
    protected List<String> aliases;
    protected String usage;
    protected String permission;

    protected CmdArgument[] cmdArgs;
    public Map<String, Message> modifiers = new HashMap<String, Message>();
    public Map<String, CommandOption> cmdOptions = new HashMap<String, CommandOption>();
    public Map<String, CommandOption> optionalArgs = new HashMap<String, CommandOption>();

    protected static CommandMap commandMap;

    public EssenceCommand(Essence ess, String label, String usage, String description, String permission, List<String> aliases) {
        this.ess = ess;
        this.label = label;
        loadData(usage, description, permission, aliases);

        modifiers.put("-?", Message.MOD_HELP);
        modifiers.put("-s", Message.MOD_SILENT);
    }

    /** Update the data with the data specified and register the command. */
    public void loadData(String usage, String description, String permission, List<String> aliases) {
        this.usage = usage;
        this.description = description;
        this.permission = permission;
        this.aliases = aliases;
        register();
    }

    /** Register the command on the server */
    public void register() {
        ReflectCommand cmd = new ReflectCommand(this.label);
        if (this.aliases != null) cmd.setAliases(this.aliases);
        if (this.description != null) cmd.setDescription(this.description);
        if (this.usage != null) cmd.setUsage(this.usage);
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
        return usage;
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
            if (arg.startsWith("-") && arg.length() > 1) {
                result.addModifier(arg);
            } else if (arg.contains(":")) {
                String[] split = arg.split(":");
                if (split.length > 1 && !split[0].isEmpty() && !split[1].isEmpty()) {
                    if (!optionalArgs.get(split[0]).isValid(split[1])) {
                        sender.sendMessage(ess.getMessages().getMsg(Message.INVALID_OPTIONAL_ARGUMENT, true, split[0], optionalArgs.get(split[0]).getClass().getSimpleName(), split[1]));
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
                    sender.sendMessage(cmd.getEss().getMessages().getMsg(Message.CMD_INVALID_USAGE, true, cmd.getUsage()));
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
        cmdOptions.put(key, optionType);
        optionalArgs.put(key, optionType);
        ess.getCmdOptions().registerOption(this, key);
    }

    public void addOptionalArgument(String key, CommandOption argumentType) {
        optionalArgs.put(key, argumentType);
    }

    public void addModifier(String modifier, Message info) {
        if (!modifier.startsWith("-")) {
            modifier = "-" + modifier;
        }
        modifiers.put(modifier, info);
    }

    /** Method to be overwritten by each command */
    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    /** Optional method to be overwritten by each command (recommended to have tab completion for all commands) */
    public List<String> onTabComplete(CommandSender sender, Command cmd, String message, String[] args) {
        return null;
    }
}