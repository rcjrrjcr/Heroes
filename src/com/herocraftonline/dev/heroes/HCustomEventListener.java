package com.herocraftonline.dev.heroes;

import org.bukkit.Location;
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;

import com.herocraftonline.dev.heroes.inventory.InventoryChangedEvent;
import com.herocraftonline.dev.heroes.inventory.InventoryCloseEvent;

public class HCustomEventListener extends CustomEventListener {
    protected Heroes plugin;

    public HCustomEventListener(Heroes plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCustomEvent(Event event) {
        /*
         * Handle Inventory Rules
         */
        if (event instanceof InventoryCloseEvent) {
            InventoryCloseEvent e = (InventoryCloseEvent) event;
            this.plugin.inventoryCheck(e.getPlayer());
        }
        if (event instanceof InventoryChangedEvent) {
            InventoryChangedEvent e = (InventoryChangedEvent) event;
            this.plugin.inventoryCheck(e.getPlayer());
        }
    }

    public double distance(Location p, Location q) {
        double dx = p.getX() - q.getX();
        double dy = p.getY() - q.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);
        return dist;
    }
}
