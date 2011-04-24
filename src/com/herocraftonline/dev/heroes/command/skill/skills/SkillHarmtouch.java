package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillHarmtouch extends TargettedSkill {

    // TODO: Register this command in Heroes
    public SkillHarmtouch(Heroes plugin) {
        super(plugin);
        name = "Harmtouch";
        description = "Skill - Harmtouch";
        usage = "/harmtouch [player]";
        minArgs = 0;
        maxArgs = 1;
        identifiers.add("harmtouch");
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Player player = hero.getPlayer();
        if (target == player) {
            plugin.getMessager().send(player, "Sorry, you can't target yourself!");
            return false;
        }

        double dx = player.getLocation().getX() - target.getLocation().getX();
        double dz = player.getLocation().getZ() - target.getLocation().getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        if (distance < 15) {
            int damage = (int) (plugin.getConfigManager().getProperties().getLevel(hero.getExperience()) * 0.5);
            EntityDamageByEntityEvent damageEntityEvent = new EntityDamageByEntityEvent(player, target, DamageCause.CUSTOM, damage);
            plugin.getServer().getPluginManager().callEvent(damageEntityEvent);
            if (damageEntityEvent.isCancelled()) {
                return false;
            }
            target.setHealth(target.getHealth() - damage);
            return true;
        } else {
            plugin.getMessager().send(player, "Sorry, that person isn't close enough!");
            return false;
        }
    }

}
