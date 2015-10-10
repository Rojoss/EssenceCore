package org.essencemc.essencecore.message;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.essencemc.essencecore.EssenceCore;
import org.essencemc.essencecore.parsers.TextParser;
import org.essencemc.essencecore.placeholders.Placeholder;
import org.essencemc.essencecore.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Text builder to parse text in multiple ways and sending text easily.
 * It supports formatting colors, adding a prefix, formatting it to json using the TextParser and it supports replacing placeholders.
 * When you want to parse multiple things the following order is recommended:
 * new EText(message).addPrefix().parsePlaceholders().toJSON().send(player);
 * If you don't want to parse it to JSON use color() instead of toJSON().
 * Just make sure you don't add a prefix after formatting it to JSON!
 */
public class EText {

    private String originalText = "";
    private String text = "";

    /**
     * Starts building a new EText based of the specified string.
     * @param text The text that needs to be build/parsed.
     */
    public EText(String text) {
        this.originalText = originalText;
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
     * Parse the prefix {p} in a message to the Essence prefix.
     * @return EText instance.
     */
    public EText parsePrefix() {
        params(Param.P("p", Message.PREFIX.msg().getText()));
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
     * Replace all parameters in the text with the specified parameters.
     * For example a message like 'You have been given {0} {item}.'
     * If you pass in '1' as the first argument and 'diamond' as the second {item} argument.
     * the result message would be 'You have been given 1 diamond.'
     * @param params A list of Param instances with names and values.
     * @return EText instance.
     */
    public EText params(Param... params) {
        for (int i = 0; i < params.length; i++) {
            if (params[i] == null) {
                continue;
            }
            text = text.replace("{" + params[i].getName() + "}", params[i].getValue());
            text = text.replace("{" + i + "}", params[i].getValue());
        }
        return this;
    }

    /**
     * Format the text completely.
     * @param sender The sender used as source for placeholders.
     * @param parsePlaceholders When set to true placeholders will be parsed.
     * @param json When set to true the text will be converted to JSON and implement hover messages etc.
     * @param params List of parameters that need to be replaced.
     * @return
     */
    public EText format(CommandSender sender, boolean parsePlaceholders, boolean json, Param... params) {
        params(Param.P("p", Message.PREFIX.msg().getText()));
        if (params.length > 0) {
            params(params);
        }
        if (parsePlaceholders) {
            parsePlaceholders(sender instanceof Player ? (Player)sender : null);
        }
        if (json) {
            toJSON();
        }
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
     * Send the text to the specified command sender.
     * It will add the default Essence prefix, parse placeholders and format to json.
     * @param sender The CommandSender to send the text to.
     * @return EText instance.
     */
    public EText send(CommandSender sender) {
        send(sender, true, true);
        return this;
    }

    /**
     * Send the text to the specified command sender.
     * It will add the default Essence prefix, parse placeholders, format to json and parse all specified params.
     * @param sender The CommandSender to send the text to.
     * @param params List of parameters that need to be replaced.
     * @return EText instance.
     */
    public EText send(CommandSender sender, Param... params) {
        send(sender, true, true, params);
        return this;
    }

    /**
     * Send the text to the specified command sender.
     * If json is true, the text is json and the sender is a player the message will be send like tellraw.
     * @param sender The CommandSender to send the text to.
     * @param parsePlaceholders When set to true placeholders will be parsed.
     * @param json When set to true the text will be converted to JSON and implement hover messages etc.
     * @param params List of parameters that need to be replaced.
     * @return EText instance.
     */
    public EText send(CommandSender sender, boolean parsePlaceholders, boolean json, Param... params) {
        if (sender instanceof Player) {
            format(sender, parsePlaceholders, json, params);
        } else {
            format(sender, parsePlaceholders, false, params);
        }

        if (text.contains("{")) {
            if (sender instanceof Player) {
                EssenceCore.inst().getChat().sendChat(text, (Player)sender);
            } else {
                sender.sendMessage(text);
            }
        } else {
            sender.sendMessage(text);
        }
        return this;
    }



    /**
     * Send the text to the specified command sender.
     * It will add the default Essence prefix, parse placeholders and format to json.
     * @param players List of players to send the message to..
     * @return EText instance.
     */
    public EText send(List<Player> players) {
        send(players, true, true);
        return this;
    }



    /**
     * Send the text to the specified command sender.
     * It will add the default Essence prefix, parse placeholders, format to json and parse all specified params.
     * @param players List of players to send the message to.
     * @param params List of parameters that need to be replaced.
     * @return EText instance.
     */
    public EText send(List<Player> players, Param... params) {
        send(players, true, true, params);
        return this;
    }

    /**
     * Send the text to the specified command sender.
     * If json is true and the text is jso the message will be send like tellraw.
     * @param players List of players to send the message to.
     * @param parsePlaceholders When set to true placeholders will be parsed.
     * @param json When set to true the text will be converted to JSON and implement hover messages etc.
     * @param params List of parameters that need to be replaced.
     * @return EText instance.
     */
    public EText send(List<Player> players, boolean parsePlaceholders, boolean json, Param... params) {
        for (Player player : players) {
            format(player, parsePlaceholders, json, params);

            if (text.contains("{")) {
                EssenceCore.inst().getChat().sendChat(text, player);
            } else {
                player.sendMessage(text);
            }
        }
        return this;
    }



    /**
     * Broadcast the text to the entire server.
     * It will add the default Essence prefix, parse placeholders, format to json and parse all specified params.
     * @param params List of parameters that need to be replaced.
     * @return EText instance.
     */
    public EText broadcast(Param... params) {
        broadcast(true, true, params);
        return this;
    }

    /**
     * Broadcast the text to the entire server.
     * If json is true and the text is jso the message will be send like tellraw.
     * @param parsePlaceholders When set to true placeholders will be parsed.
     * @param json When set to true the text will be converted to JSON and implement hover messages etc.
     * @param params List of parameters that need to be replaced.
     * @return EText instance.
     */
    public EText broadcast(boolean parsePlaceholders, boolean json, Param... params) {
        send(new ArrayList<Player>(Bukkit.getOnlinePlayers()), parsePlaceholders, json, params);
        return this;
    }



    /**
     * Send the text to the specified players action bar. (above the hotbar)
     * This doesn't support JSON formatting as you can't hover over the text and/or click on it.
     * It will add the default Essence prefix and parse placeholders.
     * @param player The CommandSender to send the text to.
     * @return EText instance.
     */
    public EText sendBar(Player player) {
        sendBar(player, true);
        return this;
    }

    /**
     * Send the text to the specified players action bar. (above the hotbar)
     * This doesn't support JSON formatting as you can't hover over the text and/or click on it.
     * It will add the default Essence prefix, parse placeholders and parse all specified params.
     * @param player The CommandSender to send the text to.
     * @param params List of parameters that need to be replaced.
     * @return EText instance.
     */
    public EText sendBar(Player player, Param... params) {
        sendBar(player, true, params);
        return this;
    }

    /**
     * Send the text to the specified players action bar. (above the hotbar)
     * This doesn't support JSON formatting as you can't hover over the text and/or click on it.
     * @param player The CommandSender to send the text to.
     * @param parsePlaceholders When set to true placeholders will be parsed.
     * @param params List of parameters that need to be replaced.
     * @return EText instance.
     */
    public EText sendBar(Player player, boolean parsePlaceholders, Param... params) {
        format(player, parsePlaceholders, false, params);
        EssenceCore.inst().getChat().sendActionbar(text, player);
        return this;
    }



    /**
     * Send the text to the specified players action bar. (above the hotbar)
     * This doesn't support JSON formatting as you can't hover over the text and/or click on it.
     * It will add the default Essence prefix and parse placeholders.
     * @param players The CommandSender to send the text to.
     * @return EText instance.
     */
    public EText sendBar(List<Player> players) {
        sendBar(players, true);
        return this;
    }

    /**
     * Send the text to the specified players action bar. (above the hotbar)
     * This doesn't support JSON formatting as you can't hover over the text and/or click on it.
     * It will add the default Essence prefix, parse placeholders and parse all specified params.
     * @param players The CommandSender to send the text to.
     * @param params List of parameters that need to be replaced.
     * @return EText instance.
     */
    public EText sendBar(List<Player> players, Param... params) {
        sendBar(players, true, params);
        return this;
    }

    /**
     * Send the text to the specified players action bar. (above the hotbar)
     * This doesn't support JSON formatting as you can't hover over the text and/or click on it.
     * @param players The CommandSender to send the text to.
     *
     * @param parsePlaceholders When set to true placeholders will be parsed.
     * @param params List of parameters that need to be replaced.
     * @return EText instance.
     */
    public EText sendBar(List<Player> players, boolean parsePlaceholders, Param... params) {
        for (Player player : players) {
            format(player, parsePlaceholders, false, params);
            EssenceCore.inst().getChat().sendActionbar(text, player);
        }
        return this;
    }
}
