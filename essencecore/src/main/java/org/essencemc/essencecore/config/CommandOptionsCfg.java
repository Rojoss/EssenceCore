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

package org.essencemc.essencecore.config;

import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.arguments.internal.Argument;
import org.essencemc.essencecore.commands.CommandOption;
import org.essencemc.essencecore.commands.EssenceCommand;
import org.essencemc.essencecore.config.internal.EasyConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandOptionsCfg extends EasyConfig {

    public Map<String, Map<String, String>> COMMAND_OPTIONS = new HashMap<String, Map<String, String>>();

    public CommandOptionsCfg(String fileName) {
        this.setFile(fileName);
        load();
    }

    @Override
    public void load() {
        super.load();

        if (EssenceCore.inst().getCommands() == null) {
            return;
        }
        List<EssenceCommand> commands = EssenceCore.inst().getCommands().commands;
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
        Map<String, String> options;
        if (COMMAND_OPTIONS.containsKey(command.getLabel())) {
            options = COMMAND_OPTIONS.get(command.getLabel());
        } else {
            options = new HashMap<String, String>();
        }

        registerOption(command, optionKey, options);
    }

    public void registerOption(EssenceCommand command, String optionKey, Map<String, String> options) {
        Argument cmdOption = command.cmdOptions.get(optionKey).getArg();
        if (cmdOption == null) {
            return;
        }
        if (!options.containsKey(optionKey)) {
            //Insert option to config.'-'
            options.put(optionKey, cmdOption.toString());
            COMMAND_OPTIONS.put(command.getLabel(), options);
            save();
        }
        //Update option value with config value if it's valid.
        cmdOption.parse(options.get(optionKey));
        if (!cmdOption.isValid()) {
            //Send an error to the console (it will use the default value)
            EssenceCore.inst().warn("Invalid command option specified for command " + command.getLabel() + "!");
            EssenceCore.inst().warn(cmdOption.getError());
        }
        if (command.optionalArgs.containsKey(optionKey)) {
            command.optionalArgs.get(optionKey).getArg().setDefault(cmdOption.getValue());
        }
    }

}
