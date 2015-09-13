package info.mcessence.essence.cmd_options;

import info.mcessence.essence.Message;
import info.mcessence.essence.util.NumberUtil;

public class IntOption implements CommandOption {

    private Message info;
    private Integer defaultValue;
    private Integer value;

    public IntOption(int defaultValue, Message info) {
        this.defaultValue = defaultValue;
        this.info = info;
    }

    public boolean isValid(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        if (NumberUtil.getInt(input) == null) {
            return false;
        }
        return true;
    }

    public boolean setValue(String input) {
        if (NumberUtil.getInt(input) != null) {
            this.value = NumberUtil.getInt(input);
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
