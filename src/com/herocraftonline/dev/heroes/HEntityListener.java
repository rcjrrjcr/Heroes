package com.herocraftonline.dev.heroes;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.ExperienceType;

public class HEntityListener extends EntityListener {

    private final Heroes plugin;
    private HashMap<Entity, Player> kills = new HashMap<Entity, Player>();

    public HEntityListener(Heroes plugin) {
        this.plugin = plugin;
    }

    public void onEntityDeath(EntityDeathEvent event) {
        Entity defender = event.getEntity();
        Player attacker = kills.get(defender);
        if (attacker != null) {
            // Get the player's class definition
            HeroClass playerClass = plugin.getPlayerManager().getClass(attacker);
            // Get the sources of experience for the player's class
            Set<ExperienceType> expSources = playerClass.getExperienceSources();
            if (expSources.contains(ExperienceType.KILLING)) {
                // TODO: handle killing experience
            }
        }
        kills.remove(defender);
    }

    public void onEntityDamage(EntityDamageEvent event) {
        Entity defender = event.getEntity();
        if (defender instanceof LivingEntity) {
            if (((LivingEntity) defender).getHealth() - event.getDamage() <= 0) {
                Entity attacker = null;
                if (event instanceof EntityDamageByProjectileEvent) {
                    EntityDamageByProjectileEvent subEvent = (EntityDamageByProjectileEvent) event;
                    attacker = subEvent.getDamager();
                } else if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent) event;
                    attacker = subEvent.getDamager();
                }
                if (attacker != null && attacker instanceof Player) {
                    kills.put(defender, (Player) attacker);
                } else {
                    kills.remove(defender);
                }
            }
        }
    }

}
