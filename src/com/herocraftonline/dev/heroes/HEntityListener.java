package com.herocraftonline.dev.heroes;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import com.herocraftonline.dev.heroes.api.KillExperienceEvent;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.ExperienceType;
import com.herocraftonline.dev.heroes.party.HeroParty;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class HEntityListener extends EntityListener {

    private final Heroes plugin;
    private HashMap<Entity, Player> kills = new HashMap<Entity, Player>();

    public HEntityListener(Heroes plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        Entity defender = event.getEntity();
        Player attacker = kills.get(defender);
        if (attacker != null) {
            // Get the Hero representing the player
            Hero hero = plugin.getHeroManager().getHero(attacker);
            // Get the player's class definition
            HeroClass playerClass = hero.getPlayerClass();
            // Get the sources of experience for the player's class
            Set<ExperienceType> expSources = playerClass.getExperienceSources();
            // If the player gains experience from killing
            if (expSources.contains(ExperienceType.KILLING)) {
                if (defender instanceof LivingEntity) {
                    int addedExp = 0;
                    // If the dying entity is a Player
                    if (defender instanceof Player) {
                        Hero heroDefender = plugin.getHeroManager().getHero((Player) defender);
                        heroDefender.setExperience((int) (heroDefender.getExperience() * 0.95));
                        addedExp = plugin.getConfigManager().getProperties().playerKillingExp;
                        KillExperienceEvent pvpEvent = new KillExperienceEvent(attacker, defender, addedExp);
                        plugin.getServer().getPluginManager().callEvent(pvpEvent);
                        if (!pvpEvent.isCancelled()) {
                            addedExp = pvpEvent.getExp();
                        }
                    } else {
                        // Get the dying entity's CreatureType
                        CreatureType type = null;
                        try {
                            Class<?>[] interfaces = defender.getClass().getInterfaces();
                            for (Class<?> c : interfaces) {
                                if (LivingEntity.class.isAssignableFrom(c)) {
                                    type = CreatureType.fromName(c.getSimpleName());
                                    break;
                                }
                            }
                        } catch (IllegalArgumentException e) {
                        }
                        if (type != null) {
                            addedExp = plugin.getConfigManager().getProperties().creatureKillingExp.get(type);
                            KillExperienceEvent pveEvent = new KillExperienceEvent(attacker, defender, addedExp);
                            plugin.getServer().getPluginManager().callEvent(pveEvent);
                            if (!pveEvent.isCancelled()) {
                                addedExp = pveEvent.getExp();
                            }
                        }
                    }
                    // Add the experience to the player
                    int exp = hero.getExperience();
                    // Only perform an experience update if we're actually
                    // adding or subtracting from their experience.
                    if (addedExp != 0) {
                        hero.setExperience(exp + addedExp);
                        plugin.getMessager().send(attacker, "$1: $2 Exp (+$3)", playerClass.getName(), String.valueOf(exp), String.valueOf(addedExp));
                    }
                }
            }
        }
        kills.remove(defender);
    }

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        Entity defender = event.getEntity();
        if (defender instanceof LivingEntity) {
            if (((LivingEntity) defender).getHealth() - event.getDamage() <= 0) {
                // Grab the Attacker regardless of the Event.
                Entity attacker = null;
                if (event instanceof EntityDamageByProjectileEvent) {
                    EntityDamageByProjectileEvent subEvent = (EntityDamageByProjectileEvent) event;
                    attacker = subEvent.getDamager();
                } else if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent) event;
                    attacker = subEvent.getDamager();
                }
                // Check if the Attacker is in the Defenders Party.
                if (attacker instanceof Player && defender instanceof Player) {
                    HeroParty party = plugin.getHeroManager().getHero((Player) attacker).getParty();
                    if (party != null && party.getMembers().contains(defender)) {
                        event.setCancelled(true);
                        return;
                    }
                }
                // If it's a legitimate attack then we add it to the Kills list.
                if (attacker != null && attacker instanceof Player) {
                    kills.put(defender, (Player) attacker);
                } else {
                    kills.remove(defender);
                }
            }
        }
    }
}
