package com.herocraftonline.dev.heroes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.WeaponItems;
import com.herocraftonline.dev.heroes.inventory.InventoryChangeEvent;
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
        if (event instanceof InventoryChangeEvent) {
            InventoryChangeEvent e = (InventoryChangeEvent) event;
            Player p = e.getPlayer();

            @SuppressWarnings("unused")
            ItemStack slot = e.getSlot();
            ItemStack cursor = e.getCursor();
            int slotNumber = e.getSlotNumber();

            if (cursor.getType() == Material.AIR) {
                return;
            }

            Hero hero = plugin.getHeroManager().getHero(p);
            HeroClass clazz = hero.getPlayerClass();

            // The following will check the players class armor and weapon restrictions.
            if (slotNumber == 5 || slotNumber == 6 || slotNumber == 7 || slotNumber == 8) {
                // 5 = Helm
                // 6 = Chest
                // 7 = Legs
                // 8 = Shoes
                String item = cursor.getType().toString();
                if (!(clazz.getAllowedArmor().contains(item))) {
                    plugin.getMessager().send(p, "You cannot equip that! - $1", item);
                    e.setCancelled(true);
                    return;
                }
            }

            if (slotNumber >= 36 && slotNumber <= 44) {
                // Slots 36->44 are the Hotbar Slots.
                String item = cursor.getType().toString();
                // If it doesn't contain a '_' then it definitely isn't a Weapon.
                if (!(item.contains("_"))) {
                    return;
                }
                // Perform a check to see if what we have is a Weapon.
                @SuppressWarnings("unused")
                WeaponItems itemCheck = null;
                try {
                    // Get the value of the item.
                    itemCheck = WeaponItems.valueOf(item.substring(item.indexOf("_") + 1, item.length()));
                } catch (IllegalArgumentException e1) {
                    // If it isn't a Weapon then we exit out here.
                    return;
                }
                // Check if the Players HeroClass allows this WEAPON to be equipped.
                if (!(clazz.getAllowedWeapons().contains(item))) {
                    plugin.getMessager().send(p, "You cannot equip that! - $1", item);
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
