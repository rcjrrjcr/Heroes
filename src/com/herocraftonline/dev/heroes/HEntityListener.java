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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

import com.herocraftonline.dev.heroes.api.KillExperienceEvent;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.ExperienceType;
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
                Entity attacker = null;
                if (event instanceof EntityDamageByProjectileEvent) {
                    EntityDamageByProjectileEvent subEvent = (EntityDamageByProjectileEvent) event;
                    attacker = subEvent.getDamager();
                } else if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent) event;
                    attacker = subEvent.getDamager();
                    if (attacker instanceof Player && defender instanceof Player) {
                        Player p = (Player) attacker;
                        Hero d = plugin.getHeroManager().getHero((Player) defender);
                        if (plugin.getHeroManager().getHero(p).getParty().getMembers().contains((Player) defender)) {
                            event.setCancelled(true);
                            return;
                        } else if (d.getEffects().containsKey("Invuln")) {
                            if (d.getEffects().get("Invuln") > System.currentTimeMillis()) {
                                event.setCancelled(true);
                                return;
                            } else {
                                return;
                            }
                        }
                    }
                }
                if (attacker != null && attacker instanceof Player) {
                    kills.put(defender, (Player) attacker);
                } else {
                    kills.remove(defender);
                }
            }
        }
        if (defender instanceof Player) {
            if (plugin.getHeroManager().getHero((Player) defender).getEffects().get("Bladegrasp") != null) {
                if (event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.ENTITY_EXPLOSION) {
                    event.setCancelled(true);
                }
            }
        }

    }
}
