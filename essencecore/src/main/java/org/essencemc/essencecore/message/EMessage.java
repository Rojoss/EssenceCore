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
import org.essencemc.essencecore.config.internal.MessageConfig;
import org.essencemc.essencecore.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a configurable messsage.
 * In Essence messages are registered in the Message enum but you could also create messages directly.
 * However, an enum is probably the best approach so that you can access the messages easily.
 * To send a message to a player you would do something like:
 * Message.NO_PERM.msg(true).params("essence.example.perm").send(player);
 */
public class EMessage {

    private MsgCat category;
    private String key;
    private String defaultMsg;
    private MessageConfig config;

    private String message;

    /**
     * Registers a new EMessage.
     * The key should be the enum key or it can be any string.
     * The default message should not contain any prefixes it should only contain the message.
     * The message may contain placeholders like {0}, {1} or key based placeholders like {placeholder}, {item}
     * However, these placeholders will only get formatted if the proper getText() method is called.
     * In most cases indexed placeholders should be used since it's the most simple to use.
     */
    public EMessage(MsgCat category, String key, String defaultMsg, MessageConfig config) {
        this.category = category;
        this.key = key;
        this.defaultMsg = defaultMsg;
        this.config = config;
        updateMessage();
    }

    private void updateMessage() {
        Map<String, Map<String, String>> messageMap = config.getMessages();

        MessagesCfg msgCfg = EssenceCore.inst().getMessages();
        String category = getCategory().toString().toLowerCase().replace("_","-");
        String key = getKey().toLowerCase().replace("_","-");

        Map<String, String> messages = new HashMap<String, String>();
        if (messageMap.containsKey(category)) {
            messages = messageMap.get(category);
        }
        if (!messages.containsKey(key)) {
            messages.put(key, getDefault());
            messageMap.put(category, new TreeMap<String, String>(messages));
            config.setMessages(messageMap);
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
     * Get the message from config as {@link EText}.
     */
    public EText getText() {
        return new EText(message);
    }
}
