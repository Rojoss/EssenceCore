package org.essencemc.essencecore.message;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.parsers.TextParser;
import org.essencemc.essencecore.placeholders.Placeholder;
import org.essencemc.essencecore.util.Util;

/**
 * Text builder to parse text in multiple ways and sending text easily.
 * It supports formatting colors, adding a prefix, formatting it to json using the TextParser and it supports replacing placeholders.
 * When you want to parse multiple things the following order is recommended:
 * new EText(message).addPrefix().parsePlaceholders().toJSON().send(player);
 * If you don't want to parse it to JSON use color() instead of toJSON().
 * Just make sure you don't add a prefix after formatting it to JSON!
 */
public class EText {

    private String text;

    /**
     * Starts building a new EText based of the specified string.
     * @param text The text that needs to be build/parsed.
     */
    public EText(String text) {
        this.text = text;
    }

    /**
     * Color the string by replacing all & symbols to
     * @return EText instance.
     */
    public EText color() {
        text = Util.color(text);
        return this;
    }

    /**
     * Converts the string to a JSON string which supports hover messages and click events etc.
     * This return string can be used in tellraw.
     * For formatting rules see {@link TextParser}
     * @return EText instance.
     */
    public EText toJSON() {
        text = new TextParser(text).getJSON();
        return this;
    }

    /**
     * Add the default configured Essence prefix in front of the string.
     * <b>Do not call this after converting the text to JSON!</b>
     * @return EText instance.
     */
    public EText addPrefix() {
        text = Util.color(Message.PREFIX.msg().getMsg(false)) + " " + text;
        return this;
    }

    /**
     * Add the specified prefix in front of the string.
     * Do not call this after converting the text to JSON!
     * @param prefix The string used as prefix. (Supports color codes)
     * @return EText instance.
     */
    public EText addPrefix(String prefix) {
        text = Util.color(prefix) + " " + text;
        return this;
    }

    /**
     * Parses all placeholders in the text to their values.
     * When a player is specified the placeholder source will be based on the given player if there is no source specified.
     * So for example if there is a placeholder with $playerloc it would be the location of this player specified.
     * Set to null if there is no known source. This will make $playerloc not work though and you would have to specify a source like $playerloc(Worstboy)
     * @param player The source for the placeholder value. (See description)
     * @return EText instance.
     */
    public EText parsePlaceholders(Player player) {
        text = Placeholder.parse(text, player);
        return this;
    }

    /**
     * Get the formatted text result.
     * If you want to send messages you should use one of the send/broadcast methods.
     * @return String with the formatted text.
     */
    public String getText() {
        return text;
    }

    /**
     * Send the text to the specified player.
     * If the text is JSON it will send it as a JSON message with proper formatting.
     * @param player The player to send the text to.
     * @return EText instance.
     */
    public EText send(Player player) {
        if (text.contains("{}")) {
            EssenceCore.inst().getChat().sendChat(text, player);
        } else {
            player.sendMessage(text);
        }
        return this;
    }

    /**
     * Broadcast the text to the entire server.
     * If the text is JSON it will send it as a JSON message with proper formatting.
     * @return EText instance.
     */
    public EText broadcast() {
        if (text.contains("{}")) {
            EssenceCore.inst().getChat().sendChat(text, Bukkit.getServer().getOnlinePlayers());
        } else {
            Bukkit.getServer().broadcastMessage(text);
        }
        return this;
    }

    /**
     * Broadcast the text to the specified worlds.
     * If the text is JSON it will send it as a JSON message with proper formatting.
     * @return EText instance.
     */
    public EText broadcast(World... worlds) {
        if (text.contains("{}")) {
            for (World world : worlds) {
                EssenceCore.inst().getChat().sendChat(text, world.getPlayers());
            }
        } else {
            for (World world : worlds) {
                for (Player player : world.getPlayers()) {
                    player.sendMessage(text);
                }
            }
        }
        return this;
    }

    /**
     * Send the text to the specified players action bar. (above the hotbar)
     * This doesn't support JSON formatting as you can't hover over the text and/or click on it.
     * @param player The player to send the text to.
     * @return EText instance.
     */
    public EText sendBar(Player player) {
        EssenceCore.inst().getChat().sendActionbar(text, player);
        return this;
    }

    /**
     * Broadcast the text to all players their actionbar.
     * This doesn't support JSON formatting as you can't hover over the text and/or click on it.
     * @return EText instance.
     */
    public EText broadcastBar() {
        EssenceCore.inst().getChat().sendActionbar(text, Bukkit.getServer().getOnlinePlayers());
        return this;
    }

    /**
     * Broadcast the text to all players their actionbar in the specified worlds.
     * This doesn't support JSON formatting as you can't hover over the text and/or click on it.
     * @return EText instance.
     */
    public EText broadcastBar(World... worlds) {
        for (World world : worlds) {
            EssenceCore.inst().getChat().sendActionbar(text, world.getPlayers());
        }
        return this;
    }
}
