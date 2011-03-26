package com.herocraftonline.dev.heroes.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

@SuppressWarnings("serial")
public class CustomPlayerEvent extends Event implements Cancellable {

    protected boolean cancelled = false;
    
    public CustomPlayerEvent() {
        super(Type.CUSTOM_EVENT);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
    
}
