package info.mcessence.essence.cmd_options;

import info.mcessence.essence.Message;

public class StringOption implements CommandOption {

    private Message info;
    private String defaultValue;
    private String value;

    public StringOption(String defaultValue, Message info) {
        this.defaultValue = defaultValue;
        this.info = info;
    }

    public boolean isValid(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean setValue(String input) {
        this.value = input;
        return true;
    }

    public Object getValue() {
        if (value == null || value.isEmpty()) {
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
