package com.clashwars.essence.config;

import com.clashwars.essence.Essence;
import com.clashwars.essence.commands.internal.EssenceCommand;
import com.clashwars.essence.commands.options.CommandOption;

import java.util.*;

public class CommandOptionsCfg extends EasyConfig {

    public Map<String, Map<String, String>> COMMAND_OPTIONS = new HashMap<String, Map<String, String>>();

    public CommandOptionsCfg(String fileName) {
        this.setFile(fileName);
        load();
    }

    @Override
    public void load() {
        super.load();

        if (Essence.inst().getCommands() == null) {
            return;
        }
        List<EssenceCommand> commands = Essence.inst().getCommands().commands;
        if (commands == null || commands.size() <= 0) {
            return;
        }
        for (EssenceCommand cmd : commands) {
            registerOptions(cmd);
        }
    }

    public void registerOptions(EssenceCommand command) {
        Map<String, String> options;
        if (COMMAND_OPTIONS.containsKey(command.getLabel())) {
            options = COMMAND_OPTIONS.get(command.getLabel());
        } else {
            options = new HashMap<String, String>();
            COMMAND_OPTIONS.put(command.getLabel(), options);
            save();
        }

        for (Map.Entry<String, CommandOption> option : command.cmdOptions.entrySet()) {
            registerOption(command, option.getKey(), options);
        }
    }

    public void registerOption(EssenceCommand command, String optionKey) {
        int changes = 0;
        Map<String, String> options;
        if (COMMAND_OPTIONS.containsKey(command.getLabel())) {
            options = COMMAND_OPTIONS.get(command.getLabel());
        } else {
            changes++;
            options = new HashMap<String, String>();
        }

        registerOption(command, optionKey, options);
    }

    public void registerOption(EssenceCommand command, String optionKey, Map<String, String> options) {
        CommandOption cmdOption = command.cmdOptions.get(optionKey);
        if (cmdOption == null) {
            return;
        }
        if (!options.containsKey(optionKey)) {
            //Insert option to config.
            options.put(optionKey, cmdOption.getDefaultValue().toString());
            COMMAND_OPTIONS.put(command.getLabel(), options);
            save();
        } else {
            //Update option value with config value if it's valid.
            if (cmdOption.isValid(options.get(optionKey))) {
                cmdOption.setValue(options.get(optionKey));
            } else {
                //Send an error to the console (it will use the default value)
                Essence.inst().warn("Invalid command option specified for command " + command.getLabel() + "!");
                Essence.inst().warn("Expecting a " + cmdOption.getClass().getName().replace("Option","") + " for the option " + optionKey + " but found '" + options.get(optionKey) + "'");
            }
        }
    }

}
