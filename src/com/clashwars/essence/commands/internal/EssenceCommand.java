package com.clashwars.essence.commands.internal;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.clashwars.essence.Essence;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * Reflection and command registration code by: Goblom
 */
public abstract class EssenceCommand implements CommandExecutor, TabExecutor, Listener {

    protected final Essence ess;
    protected final String label;
    protected String description;
    protected List<String> aliases;
    protected String usage;
    protected String permission;

    protected static CommandMap commandMap;

    public EssenceCommand(Essence ess, String label, String usage, String description, String permission, List<String> aliases) {
        this.ess = ess;
        this.label = label;
        loadData(usage, description, permission, aliases);
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



    /** Method to be overwritten by each command */
    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    /** Optional method to be overwritten by each command (recommended to have tab completion for all commands) */
    public List<String> onTabComplete(CommandSender sender, Command cmd, String message, String[] args) {
        return null;
    }
}