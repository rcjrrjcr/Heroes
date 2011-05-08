package com.herocraftonline.dev.heroes.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@SuppressWarnings("serial")
public class InventoryChangedEvent extends Event {

    protected Player player;

    public InventoryChangedEvent(Player player) {
        super("InventoryChangedEvent");
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
