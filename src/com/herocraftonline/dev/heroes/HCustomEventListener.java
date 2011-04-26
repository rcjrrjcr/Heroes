package com.herocraftonline.dev.heroes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.inventory.InventoryChangeEvent;
import com.herocraftonline.dev.heroes.inventory.InventoryCloseEvent;
import com.herocraftonline.dev.heroes.inventory.InventorySlotType;
import com.herocraftonline.dev.heroes.persistence.Hero;

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
            Player p = e.getPlayer();
            this.plugin.inventoryCheck(p);
        }
        if (event instanceof InventoryChangeEvent) {
            InventoryChangeEvent e = (InventoryChangeEvent) event;
            Player p = e.getPlayer();

            ItemStack cursor = e.getCursor();

            if (cursor.getType() == Material.AIR) {
                return;
            }

            Hero hero = plugin.getHeroManager().getHero(p);
            HeroClass clazz = hero.getPlayerClass();

            if(e.getSlotType()==InventorySlotType.ARMOR){
                String item = cursor.getType().toString();
                if (!(clazz.getAllowedArmor().contains(item))) {
                    plugin.getMessager().send(p, "You cannot equip that armor - $1", item);
                    e.setCancelled(true);
                    return;
                }
            }
            if(e.getSlotType()==InventorySlotType.QUICKBAR){
                String item = cursor.getType().toString();
                // If it doesn't contain a '_' and it isn't a Bow then it definitely isn't a Weapon.
                if (!(item.contains("_")) && !(item.equalsIgnoreCase("BOW"))) {
                    return;
                }
                // Perform a check to see if what we have is a Weapon.
                if (!(item.equalsIgnoreCase("BOW"))) {
                    try {
                        // Get the value of the item.
                        HeroClass.WeaponItems.valueOf(item.substring(item.indexOf("_") + 1, item.length()));
                    } catch (IllegalArgumentException e1) {
                        // If it isn't a Weapon then we exit out here.
                        return;
                    }
                }
                // Check if the Players HeroClass allows this WEAPON to be equipped.
                if (!(clazz.getAllowedWeapons().contains(item))) {
                    plugin.getMessager().send(p, "You cannot equip that item - $1", item);
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    public double distance(Location p, Location q) {
        double dx = p.getX() - q.getX();
        double dy = p.getY() - q.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);
        return dist;
    }
}
