package com.clashwars.essence.commands.options;

import com.clashwars.essence.Message;

public class BoolOption implements CommandOption {

    private Message info;
    private Boolean defaultValue;
    private Boolean value;

    public BoolOption(boolean defaultValue, Message info) {
        this.defaultValue = defaultValue;
        this.info = info;
    }

    public boolean isValid(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("1") || input.equalsIgnoreCase("v")) {
            return true;
        }
        if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("0") || input.equalsIgnoreCase("x")) {
            return true;
        }
        return false;
    }

    public boolean setValue(String input) {
        if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("1") || input.equalsIgnoreCase("v")) {
            this.value = true;
            return true;
        }
        if (input.equalsIgnoreCase("false") || input.equalsIgnoreCase("no") || input.equalsIgnoreCase("0") || input.equalsIgnoreCase("x")) {
            this.value = false;
            return true;
        }
        return false;
    }

    public Object getValue() {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public Message getInfo() {
        return info;
    }
}
