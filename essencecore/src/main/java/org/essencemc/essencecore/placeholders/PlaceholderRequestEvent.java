package org.essencemc.essencecore.placeholders;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.essencemc.essencecore.arguments.ArgumentType;
import org.essencemc.essencecore.util.Util;

public class PlaceholderRequestEvent extends Event {

    private PlaceholderType type;
    private String placeholder;
    private Object[] data;
    private String value;

    public PlaceholderRequestEvent(PlaceholderType type, String placeholder, Object... data) {
        this.type = type;
        this.placeholder = placeholder;
        this.data = data;
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
