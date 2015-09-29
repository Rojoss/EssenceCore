/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://essencemc.org>
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

package org.essencemc.essencecore.message;

import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.config.MessagesCfg;
import org.essencemc.essencecore.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a configurable messsage that can have placeholders and formatting.
 * In Essence messages are registered in the Message enum but you could also create messages directly.
 * However, an enum is probably the best approach so that you can access the messages easily.
 * To send a message to a player you would do something like:
 * Message.NO_PERM.msg().getMsg(true, "essence.example.perm");
 */
public class EMessage {

    private MsgCat category;
    private String key;
    private String defaultMsg;

    private String message;

    /**
     * Registers a new EMessage.
     * The key should be the enum key or it can be any string.
     * The default message should not contain any prefixes it should only contain the message.
     * The message may contain placeholders like {0}, {1} or key based placeholders like {placeholder}, {item}
     * However, these placeholders will only get formatted if the proper getMsg() method is called.
     * In most cases indexed placeholders should be used since it's the most simple to use.
     */
    public EMessage(MsgCat category, String key, String defaultMsg) {
        this.category = category;
        this.key = key;
        this.defaultMsg = defaultMsg;
        updateMessage();
    }

    private void updateMessage() {
        MessagesCfg msgCfg = EssenceCore.inst().getMessages();
        String category = getCategory().toString().toLowerCase().replace("_","-");
        String key = getKey().toLowerCase().replace("_","-");

        Map<String, String> messages = new HashMap<String, String>();
        if (msgCfg.MESSAGES.containsKey(category)) {
            messages = msgCfg.MESSAGES.get(category);
        }
        if (!messages.containsKey(key)) {
            messages.put(key, getDefault());
            msgCfg.MESSAGES.put(category, new TreeMap<String, String>(messages));
            msgCfg.save();
        }
        message = messages.get(key);
    }


    /** Get the message category */
    public MsgCat getCategory() {
        return category;
    }

    /** Get the message key */
    public String getKey() {
        return key;
    }

    /** Get the default message */
    public String getDefault() {
        return defaultMsg;
    }


    /**
     * Get the message from config.
     * @param format if set set to true it will add the prefix in front of the message and colorize the message.
     */
    public String getMsg(boolean format) {
        if (message == null || message.isEmpty()) {
            updateMessage();
        }
        String resultMsg = message;
        if (format && !resultMsg.isEmpty()) {
            resultMsg = Util.color(Message.PREFIX.msg().getMsg(false) + resultMsg);
        }
        return resultMsg;
    }

    /**
     * Get the message from config from the specified Message
     * It will replace all numbered args based on the specified args.
     * For example a message like 'You have been given {0} {1}.'
     * If you would call getMsg(Message.GIVE_ITEM, "1", "stone")
     * You will get 'You have been given 1 stone.'
     * @param format if set set to true it will add the prefix in front of the message and colorize the message.
     * @param args A list of strings that will be replaced in the message by index.
     */
    public String getMsg(boolean format, String... args) {
        String msg = getMsg(format);
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
     * @param format if set set to true it will add the prefix in front of the message and colorize the message.
     * @param args A hashmap with keys and values where all {keys} will be replaced in the message with the value in the hashmap.
     */
    public String getMsg(boolean format, HashMap<String, String> args) {
        String msg = getMsg(format);
        for (Map.Entry<String, String> arg : args.entrySet()) {
            msg = msg.replace("{" + arg.getKey() + "}", arg.getValue());
        }
        return msg;
    }

    /**
     * Combination of {@link #getMsg(boolean, String...)} and {@link #getMsg(boolean, HashMap)}
     * @param format if set set to true it will add the prefix in front of the message and colorize the message.
     * @param keyArgs A hashmap with keys and values where all {keys} will be replaced in the message with the value in the hashmap.
     * @param numberArgs A list of strings that will be replaced in the message by index.
     */
    public String getMsg(boolean format, HashMap<String, String> keyArgs, String... numberArgs) {
        String msg = getMsg(format);
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
