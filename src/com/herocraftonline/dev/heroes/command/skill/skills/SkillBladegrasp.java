package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveEffectSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.persistence.HeroEffects;

public class SkillBladegrasp extends ActiveEffectSkill {

    public SkillBladegrasp(Heroes plugin) {
        super(plugin);
        name = "Bladegrasp";
        description = "Skill - Bladegrasp";
        usage = "/skill bladegrasp";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("skill bladegrasp");

        registerEvent(Type.ENTITY_DAMAGE, new SkillEntityListener(), Priority.Normal);
    }

    @Override
    public boolean use(Hero hero, String[] args) {
        hero.getEffects().putEffect(name, 60000.0);
        if(useText != null) notifyNearbyPlayers(hero.getPlayer().getLocation().toVector(), useText, hero.getPlayer().getName(), name);
        return true;
    }

    public class SkillEntityListener extends EntityListener {

        @Override
        public void onEntityDamage(EntityDamageEvent event) {
            Entity defender = event.getEntity();
            if (defender instanceof Player) {
                Player player = (Player) defender;
                HeroEffects effects = plugin.getHeroManager().getHero(player).getEffects();
                if (effects.hasEffect(name)) {
                    if (event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.ENTITY_EXPLOSION) {
                        event.setCancelled(true);
                        effects.expireEffect(name);
                    }
                }
            }
        }

    }
}
