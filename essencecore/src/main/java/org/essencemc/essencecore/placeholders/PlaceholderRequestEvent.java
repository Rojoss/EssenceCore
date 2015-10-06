package org.essencemc.essencecore.placeholders;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.essencemc.essencecore.util.Util;

import java.util.List;

public class PlaceholderRequestEvent extends Event {

    private final PlaceholderType type;
    private final String placeholder;
    private final Object source;
    private final List<Object> data;
    private String value;

    public PlaceholderRequestEvent(PlaceholderType type, String placeholder, Object source, List<Object> data) {
        this.type = type;
        this.placeholder = placeholder;
        this.source = source;
        this.data = data;
    }

    public Object getSource() {
        return source;
    }

    public PlaceholderType getType() {
        return type;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public List<Object> getData() {
        return data;
    }

    public Object getData(int index) {
        if (data.size() > index) {
            return data.get(index);
        }
        return null;
    }

    public boolean hasData() {
        return data != null && !data.isEmpty();
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
