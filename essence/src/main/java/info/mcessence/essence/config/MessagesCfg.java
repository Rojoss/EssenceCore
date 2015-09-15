/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://www.mc-essence.info>
 * Copyright (c) 2015 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
            List<String> keys = new ArrayList<>(messages.keySet());
            for (String msg : keys) {
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
