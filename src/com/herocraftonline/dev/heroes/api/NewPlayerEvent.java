package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@SuppressWarnings("serial")
public class NewPlayerEvent extends Event {

    protected boolean cancelled = false;
    protected Player player;

    public NewPlayerEvent(Player player) {
        super("NewPlayerEvent");
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
