package info.mcessence.essence.nms.v1_8_R3;

import info.mcessence.essence.nms.ITitle;
import info.mcessence.essence.nms.v1_8_R3.util.PacketHandler;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.entity.Player;

/**
 * Handles the titles and subtitles for v1_8R3
 */
public class Title_1_8_R3 implements ITitle {

    // TODO Make a ChatBuilder to allow for more flexible building

    // TODO Implement the ChatBuilder

    private PacketPlayOutTitle packet =  null;
    private IChatBaseComponent icbc = null;

    /**
     * Send the player only the title message
     *
     * @param titleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param player
     */
    @Override
    public void sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Player player) {

        icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + titleMessage + "\"}");

        packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, icbc, fadeIn, stay, fadeOut);

        PacketHandler.sendPacket(player, packet);
    }

    /**
     * Send the players only the title message
     *
     * @param titleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param players
     */
    @Override
    public void sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Player[] players) {
        for (Player p = players[0]; p == players[players.length - 1];) {
            sendTitle(titleMessage, fadeIn, stay, fadeOut, p);
        }
    }

    /**
     * Send the player only the subtitle message
     *
     * @param subtitleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param player
     */
    @Override
    public void sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Player player) {
        icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitleMessage + "\"}");

        packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, icbc, fadeIn, stay, fadeOut);

        PacketHandler.sendPacket(player, packet);
    }

    /**
     * Send the players only the subtitle message
     *
     * @param subtitleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param players
     */
    @Override
    public void sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Player[] players) {
        for (Player p = players[0]; p == players[players.length - 1];) {
            sendSubtitle(subtitleMessage, fadeIn, stay, fadeOut, p);
        }
    }

    /**
     * Send the player the title and subtitle messages
     *
     * @param titleMessage
     * @param subtitleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param player
     */
    @Override
    public void sendWholeTitle(String titleMessage, String subtitleMessage, int fadeIn, int stay, int fadeOut, Player player) {
        sendTitle(titleMessage, fadeIn, stay, fadeOut, player);
        sendSubtitle(subtitleMessage, fadeIn, stay, fadeOut, player);
    }

    /**
     * Send the players the title and subtitle messages
     *
     * @param titleMessage
     * @param subtitleMessage
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param players
     */
    @Override
    public void sendWholeTitle(String titleMessage, String subtitleMessage, int fadeIn, int stay, int fadeOut, Player[] players) {
        sendTitle(titleMessage, fadeIn, stay, fadeOut, players);
        sendSubtitle(subtitleMessage, fadeIn, stay, fadeOut, players);
    }
}
