package com.clashwars.essence.commands.arguments.internal;

import java.util.ArrayList;
import java.util.List;

public class ArgumentParseResults {
    public boolean success = true;
    private List<ArgumentParseResult> parseResults = new ArrayList<ArgumentParseResult>();

    public ArgumentParseResult getValue(int argumentIndex) {
        if (parseResults.size() >= argumentIndex) {
            return parseResults.get(argumentIndex);
        }
        return null;
    }

    public void setValue(int argumentIndex, ArgumentParseResult parseResult) {
        parseResults.add(argumentIndex, parseResult);
    }
}
