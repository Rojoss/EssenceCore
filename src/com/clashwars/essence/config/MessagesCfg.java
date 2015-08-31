package com.clashwars.essence.config;

import com.clashwars.essence.Message;
import com.clashwars.essence.util.Util;

import java.util.*;

public class MessagesCfg extends EasyConfig {

    public Map<String, String> MESSAGES = new HashMap<String, String>();

    public MessagesCfg(String fileName) {
        this.setFile(fileName);
        load();
    }

    @Override
    public void load() {
        super.load();
        int changes = 0;

        //Add new messages
        for (Message message : Message.values()) {
            if (!MESSAGES.containsKey(message.toString())) {
                MESSAGES.put(message.toString(), message.getDefault());
                changes++;
            }
        }

        //Remove old messages
        List<String> messages = new ArrayList<>(MESSAGES.keySet());
        for (String message : messages) {
            if (Message.fromString(message) == null) {
                MESSAGES.remove(message);
                changes++;
            }
        }

        if (changes > 0) {
            save();
        }
    }

    /** Get the message from config from the specified Message */
    public String getMsg(Message message, boolean format) {
        if (!MESSAGES.containsKey(message.toString())) {
            MESSAGES.put(message.toString(), message.getDefault());
            save();
        }
        String msg = MESSAGES.get(message.toString());
        if (format && !msg.isEmpty()) {
            msg = Util.color(getMsg(Message.PREFIX, false) + msg);
        }
        return msg;
    }

    /**
     * Get the message from config from the specified Message
     * It will replace all numbered args based on the specified args.
     * For example a message like 'You have been given {0} {1}.'
     * If you would call getMsg(Message.GIVE_ITEM, "1", "stone")
     * You will get 'You have been given 1 stone.'
     */
    public String getMsg(Message message, boolean format, String... args) {
        String msg = getMsg(message, format);
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                continue;
            }
            msg = msg.replace("{" + i + "}", args[i]);
        }
        return msg;
    }

    /**
     * Get the message from config from the specified Message
     * It will replace all specified args with the value from the map.
     * For example a message like 'You have been given {amt} {item}.'
     * You would pass in a hasmap with "amt" as key and like "1" as value and "item" : "stone"
     * You will get 'You have been given 1 stone.'
     */
    public String getMsg(Message message, boolean format, HashMap<String, String> args) {
        String msg = getMsg(message, format);
        for (Map.Entry<String, String> arg : args.entrySet()) {
            msg = msg.replace("{" + arg.getKey() + "}", arg.getValue());
        }
        return msg;
    }

    /**
     * Combination of {@link #getMsg(Message, boolean, String...)} and {@link #getMsg(Message, boolean, HashMap)}
     */
    public String getMsg(Message message, boolean format, HashMap<String, String> keyArgs, String... numberArgs) {
        String msg = getMsg(message, format);
        for (Map.Entry<String, String> arg : keyArgs.entrySet()) {
            msg = msg.replace("{" + arg.getKey() + "}", arg.getValue());
        }
        for (int i = 0; i < numberArgs.length; i ++) {
            if (numberArgs[i] == null) {
                continue;
            }
            msg = msg.replace("{" + i + "}", numberArgs[i]);
        }
        return msg;
    }

}
