package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@SuppressWarnings("serial")
public class InventoryCloseEvent extends Event {

    protected Player player;

    public InventoryCloseEvent(Player player) {
        super("InventoryCloseEvent");
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
