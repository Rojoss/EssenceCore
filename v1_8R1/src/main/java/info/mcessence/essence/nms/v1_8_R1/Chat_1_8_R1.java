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

package info.mcessence.essence.nms.v1_8_R1;

import info.mcessence.essence.nms.IChat;
import info.mcessence.essence.nms.v1_8_R1.util.PacketHandler;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import org.bukkit.entity.Player;

/**
 * Handles the chat and actionbar for v1_8R1
 */
public class Chat_1_8_R1 implements IChat {

    // TODO Make a ChatBuilder to allow for more flexible building

    // TODO Implement the ChatBuilder

    private PacketPlayOutChat packet =  null;
    private IChatBaseComponent icbc = null;
    /**
     * Send actionbar to the player
     *
     * @param message
     * @param player
     */
    @Override
    public void sendActionbar(String message, Player player) {
        icbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        
        packet = new PacketPlayOutChat(icbc, (byte) 2);

        PacketHandler.sendPacket(player, packet);
    }

    /**
     * Send actionbar to the players
     *
     * @param message
     * @param players
     */
    @Override
    public void sendActionbar(String message, Player[] players) {
        for (Player p = players[0]; p == players[players.length - 1];) {
            sendActionbar(message, p);
        }
    }
}
