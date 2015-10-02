package org.essencemc.essencecore.config.internal;

import java.util.Map;

public interface MessageConfig {

    Map<String, Map<String, String>> getMessages();
    void setMessages(Map<String, Map<String, String>> messages);
}
