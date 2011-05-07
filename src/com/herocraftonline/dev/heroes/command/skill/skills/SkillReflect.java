package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveEffectSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.persistence.HeroEffects;

public class SkillReflect extends ActiveEffectSkill {

    private int duration;

    public SkillReflect(Heroes plugin) {
        super(plugin);
        name = "Reflect";
        description = "Skill - Reflect";
        usage = "/skill reflect";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("skill reflect");

        registerEvent(Type.ENTITY_DAMAGE, new SkillEntityListener(), Priority.Normal);
    }

    @Override
    public void init() {
        super.init();
        duration = config.getInt("duration", 10000);
    }

    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = super.getDefaultConfig();
        node.setProperty("duration", 10000);
        return node;
    }

    @Override
    public boolean use(Hero hero, String[] args) {
        Player player = hero.getPlayer();
        String playerName = player.getName();
        hero.getEffects().putEffect(name, (double) duration);

        if (useText != null) {
            notifyNearbyPlayers(player.getLocation(), useText, playerName, name);
        }
        return true;
    }

    public class SkillEntityListener extends EntityListener {

        @Override
        public void onEntityDamage(EntityDamageEvent event) {
            if (event.isCancelled() || event instanceof EntityDamageByEntityEvent) {
                return;
            }

            EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) event;
            Entity defender = edbe.getEntity();
            Entity attacker = edbe.getDamager();
            if (attacker instanceof LivingEntity && defender instanceof Player) {
                Player player = (Player) defender;

                HeroEffects effects = plugin.getHeroManager().getHero(player).getEffects();
                if (effects.hasEffect(name)) {
                    if (attacker instanceof Player) {
                        Player att = (Player) attacker;
                        HeroEffects attEffects = plugin.getHeroManager().getHero(att).getEffects();
                        if (attEffects.hasEffect(name)) {
                            event.setCancelled(true);
                            return;
                        }
                    }
                    LivingEntity atk = (LivingEntity) attacker;
                    atk.damage(event.getDamage(), defender);
                    event.setCancelled(true);
                }
            }
        }
    }
}
