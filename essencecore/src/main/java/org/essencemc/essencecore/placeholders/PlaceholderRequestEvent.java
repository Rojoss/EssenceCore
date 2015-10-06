package org.essencemc.essencecore.placeholders;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.essencemc.essencecore.arguments.ArgumentType;
import org.essencemc.essencecore.util.Util;

public class PlaceholderRequestEvent extends Event {

    private final CommandSender source;
    private final PlaceholderType type;
    private final String placeholder;
    private final Object[] data;
    private String value;

    public PlaceholderRequestEvent(CommandSender source, PlaceholderType type, String placeholder, Object... data) {
        this.source = source;
        this.type = type;
        this.placeholder = placeholder;
        this.data = data;
    }

    public CommandSender getSource() {
        return source;
    }

    public PlaceholderType getType() {
        return type;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public Object[] getData() {
        return data;
    }

    public Object getData(int index) {
        if (data.length > index) {
            return data[index];
        }
        return null;
    }

    public boolean hasData() {
        return data != null && data.length > 0;
    }

    public String getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = Util.objectToString(value);
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
