package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

@SuppressWarnings("serial")
public class InventoryChangeEvent extends Event implements Cancellable {

    protected int slot;
    protected Player player;
    protected boolean cancelled = false;

    public InventoryChangeEvent(Player player, int slot) {
        super("InventoryChangeEvent");
        this.player = player;
        this.slot = slot;
    }

    public Player getPlayer() {
        return player;
    }

    public int getSlot() {
        return slot;
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
