package com.clashwars.essence.cmd_options;

import com.clashwars.essence.Message;

public interface CommandOption {

    boolean isValid(String input);

    boolean setValue(String input);

    Object getValue();

    Object getDefaultValue();

    Message getInfo();
}
