package com.herocraftonline.dev.heroes.inventory;

import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("serial")
public class InventoryChangeEvent extends Event implements Cancellable {

    protected ItemStack slot;
    protected ItemStack cursor;
    protected Player player;
    protected int slotNumber;
    protected boolean cancelled = false;
    protected InventorySlotType type;

    public InventoryChangeEvent(Player player, InventorySlotType type, CraftItemStack slot, CraftItemStack cursor, int slotNumber) {
        super("InventoryChangeEvent");
        this.player = player;
        this.slot = slot;
        this.cursor = cursor;
        this.slotNumber = slotNumber;
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public InventorySlotType getSlotType() {
        return type;
    }

    public int getSlotNumber() {
        return this.slotNumber;
    }

    public ItemStack getSlot() {
        if (this.slot != null) {
            return this.slot;
        } else {
            return new ItemStack(0);
        }
    }

    public ItemStack getCursor() {
        if (this.cursor != null) {
            return this.cursor;
        } else {
            return new ItemStack(0);
        }
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