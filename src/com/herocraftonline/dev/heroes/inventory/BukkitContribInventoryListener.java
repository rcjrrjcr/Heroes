package com.herocraftonline.dev.heroes.inventory;

import org.bukkitcontrib.event.inventory.InventoryClickEvent;
import org.bukkitcontrib.event.inventory.InventoryCloseEvent;
import org.bukkitcontrib.event.inventory.InventoryListener;

import com.herocraftonline.dev.heroes.Heroes;

public class BukkitContribInventoryListener extends InventoryListener {

    private Heroes plugin;

    public BukkitContribInventoryListener(Heroes heroes) {
        plugin = heroes;
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event){
        // Perform Inventory Check.
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event){

    }
}