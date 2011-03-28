package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

@SuppressWarnings("serial")
public class CustomPlayerEvent extends Event implements Cancellable {

    protected boolean cancelled = false;
    protected Player player;

    public CustomPlayerEvent(Player player) {
        super("CustomPlayerEvent");
        this.player = player;
    }

    public Player getPlayer() {
        return player;
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
