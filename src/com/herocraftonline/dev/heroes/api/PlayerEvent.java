package com.herocraftonline.dev.heroes.api;

import org.bukkit.event.Event;

@SuppressWarnings("serial")
public class PlayerEvent extends Event{
	protected boolean cancelled;
	
	protected PlayerEvent(final String type) {
		super(type);
		this.cancelled = false;
	}

	public boolean isCancelled(){
		return cancelled;
	}

	public void setCancelled(Boolean cancelled){
		this.cancelled = cancelled;
	}
}
