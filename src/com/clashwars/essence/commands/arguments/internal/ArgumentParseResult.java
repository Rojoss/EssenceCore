package com.clashwars.essence.commands.arguments.internal;

public class ArgumentParseResult {
    public boolean success = true;
    private Object value = null;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
