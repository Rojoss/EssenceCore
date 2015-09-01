package com.clashwars.essence.commands.arguments.internal;

import com.clashwars.essence.Essence;
import com.clashwars.essence.Message;
import com.clashwars.essence.commands.options.CommandOption;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgumentParseResults {
    public boolean success = true;
    private List<ArgumentParseResult> parseResults = new ArrayList<ArgumentParseResult>();
    private String[] args = null;
    private List<String> modifiers = new ArrayList<String>();
    private Map<String, Object> optionalArgs = new HashMap<String, Object>();

    public ArgumentParseResult getValue(int argumentIndex) {
        if (parseResults.size() >= argumentIndex) {
            return parseResults.get(argumentIndex);
        }
        return null;
    }

    public void setValue(int argumentIndex, ArgumentParseResult parseResult) {
        parseResults.add(argumentIndex, parseResult);
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public void addModifier(String modifier) {
        if (!modifiers.contains(modifier)) {
            modifiers.add(modifier.toLowerCase());
        }
    }

    public boolean hasModifier(String modifier) {
        return modifiers.contains(modifier.toLowerCase());
    }

    public void addOptionalArg(String arg, Object value) {
        optionalArgs.put(arg.toLowerCase(), value);
    }

    public boolean hasOptionalArg(String arg) {
        return optionalArgs.containsKey(arg.toLowerCase());
    }

    public Object getOptionalArg(String arg) {
        if (optionalArgs.containsKey(arg.toLowerCase())) {
            return optionalArgs.get(arg.toLowerCase());
        }
        return "";
    }
}
