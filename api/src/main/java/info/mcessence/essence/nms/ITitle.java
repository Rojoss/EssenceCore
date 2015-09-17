package info.mcessence.essence.nms;

import org.bukkit.entity.Player;

/**
 * Interface for handling Titles and Subtitles
 */
public interface ITitle {

    /**
     * Send the player only the title message
     */
    void sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Player player);

    /**
     * Send the players only the title message
     */
    void sendTitle(String titleMessage, int fadeIn, int stay, int fadeOut, Player[] players);

    /**
     * Send the player only the subtitle message
     */
    void sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Player player);

    /**
     * Send the players only the subtitle message
     */
    void sendSubtitle(String subtitleMessage, int fadeIn, int stay, int fadeOut, Player[] player);

    /**
     * Send the player the title and subtitle messages
     */
    void sendWholeTitle(String titleMessage, String subtitleMessage, int fadeIn, int stay, int fadeOut, Player player);

    /**
     * Send the players the title and subtitle messages
     */
    void sendWholeTitle(String titleMessage, String subtitleMessage, int fadeIn, int stay, int fadeOut, Player[] player);
}
