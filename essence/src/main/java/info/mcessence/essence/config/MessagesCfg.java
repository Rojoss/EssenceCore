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

import info.mcessence.essence.message.Message;
import info.mcessence.essence.message.MsgCat;
import info.mcessence.essence.util.Util;

import java.util.*;

public class MessagesCfg extends EasyConfig {

    public Map<String, Map<String, String>> MESSAGES = new TreeMap<String, Map<String, String>>();

    public MessagesCfg(String fileName) {
        this.setFile(fileName);
        load();
    }

    /*
    @Override
    public void load() {
        super.load();
        int changes = 0;

        //Remove old messages
        List<String> categories = new ArrayList<>(MESSAGES.keySet());
        for (String category : categories) {
            if (MsgCat.fromString(category) == null) {
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
    */



}
