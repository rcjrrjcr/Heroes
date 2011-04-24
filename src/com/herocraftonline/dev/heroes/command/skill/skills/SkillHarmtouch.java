package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillHarmtouch extends TargettedSkill {

    public SkillHarmtouch(Heroes plugin) {
        super(plugin);
        name = "Harmtouch";
        description = "Skill - Harmtouch";
        usage = "/harmtouch [player]";
        minArgs = 0;
        maxArgs = 1;
        identifiers.add("harmtouch");
        maxDistance = 15;
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Player player = hero.getPlayer();
        if (target == player) {
            plugin.getMessager().send(player, "Sorry, you can't target yourself!");
            return false;
        }

        int damage = (int) (plugin.getConfigManager().getProperties().getLevel(hero.getExperience()) * 0.5);
        EntityDamageByEntityEvent damageEntityEvent = new EntityDamageByEntityEvent(player, target, DamageCause.CUSTOM, damage);
        plugin.getServer().getPluginManager().callEvent(damageEntityEvent);
        if (damageEntityEvent.isCancelled()) {
            return false;
        }
        target.setHealth(target.getHealth() - damage);
        return true;
    }

}
