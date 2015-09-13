package info.mcessence.essence.config;

import info.mcessence.essence.Message;
import info.mcessence.essence.util.Util;

import java.util.*;

public class MessagesCfg extends EasyConfig {

    public Map<String, Map<String, String>> MESSAGES = new TreeMap<String, Map<String, String>>();

    public MessagesCfg(String fileName) {
        this.setFile(fileName);
        load();
    }

    @Override
    public void load() {
        super.load();
        int changes = 0;

        //Remove old messages
        List<String> categories = new ArrayList<>(MESSAGES.keySet());
        for (String category : categories) {
            if (Message.MsgCat.fromString(category) == null) {
                MESSAGES.remove(category);
                changes++;
                break;
            }
            Map<String, String> messages = MESSAGES.get(category);
            for (String msg : messages.keySet()) {
                if (Message.fromString(msg) == null) {
                    messages.remove(msg);
                    changes++;
                }
            }
            MESSAGES.put(category, messages);
        }

        //Add new messages
        for (Message message : Message.values()) {
            String cat = message.getCat().toString().toLowerCase().replace("_","-");
            String msg = message.toString().toLowerCase().replace("_","-");
            Map<String, String> messages = new HashMap<String, String>();
            if (MESSAGES.containsKey(cat)) {
                messages = MESSAGES.get(cat);
            }
            if (!messages.containsKey(msg)) {
                messages.put(msg, message.getDefault());
                MESSAGES.put(cat, messages);
                changes++;
            }
        }

        if (changes > 0) {
            //Sort messages
            for (String category : MESSAGES.keySet()) {
                MESSAGES.put(category, new TreeMap<String, String>(MESSAGES.get(category)));
            }
            save();
        }
    }

    /** Get the message from config from the specified Message */
    public String getMsg(Message message, boolean format) {
        String cat = message.getCat().toString().toLowerCase().replace("_","-");
        String msg = message.toString().toLowerCase().replace("_","-");
        Map<String, String> messages = new HashMap<String, String>();
        if (MESSAGES.containsKey(cat)) {
            messages = MESSAGES.get(cat);
        }
        if (!messages.containsKey(msg)) {
            messages.put(msg, message.getDefault());
            MESSAGES.put(cat, new TreeMap<String, String>(messages));
            save();
        }
        String resultMsg = messages.get(msg);
        if (format && !resultMsg.isEmpty()) {
            resultMsg = Util.color(getMsg(Message.PREFIX, false) + resultMsg);
        }
        return resultMsg;
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
