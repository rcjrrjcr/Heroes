package com.herocraftonline.dev.heroes.command.skill.skills;

import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillBladegrasp extends ActiveSkill {

    public SkillBladegrasp(Heroes plugin) {
        super(plugin);
        name = "Bladegrasp";
        description = "Skill - Bladegrasp";
        usage = "/bladegrasp";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("bladegrasp");
        
        registerEvent(Type.ENTITY_DAMAGE, new SkillEntityListener(), Priority.Normal);
    }

    @Override
    public boolean use(Hero hero, String[] args) {
        hero.getEffects().put(name, System.currentTimeMillis() + 60000.0);
        return true;
    }
    
    public class SkillEntityListener extends EntityListener {
        
        @Override
        public void onEntityDamage(EntityDamageEvent event) {
            Entity defender = event.getEntity();
            if (defender instanceof Player) {
                Player player = (Player) defender;
                Map<String, Double> effects = plugin.getHeroManager().getHero(player).getEffects();
                if (effects.containsKey(name)) {
                    if (event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.ENTITY_EXPLOSION) {
                        event.setCancelled(true);
                        effects.remove(name);
                    }
                }
            }
        }
        
    }
}
