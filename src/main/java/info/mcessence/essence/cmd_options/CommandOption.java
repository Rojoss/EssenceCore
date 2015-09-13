package info.mcessence.essence.cmd_options;

import info.mcessence.essence.Message;

public interface CommandOption {

    boolean isValid(String input);

    boolean setValue(String input);

    Object getValue();

    Object getDefaultValue();

    Message getInfo();
}
