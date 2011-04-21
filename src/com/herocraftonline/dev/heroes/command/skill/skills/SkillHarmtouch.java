package com.herocraftonline.dev.heroes.command.skill.skills;

import java.util.Map;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

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
    public void use(Player player, LivingEntity target, String[] args) {
        if (target == player) {
            plugin.getMessager().send(player, "Sorry, you need a target!");
            return;
        }

        Hero hero = plugin.getHeroManager().getHero(player);
        HeroClass heroClass = plugin.getClassManager().getClass(hero.toString());

        Properties properties = plugin.getConfigManager().getProperties();
        Map<String, Long> cooldowns = hero.getCooldowns();
        if (cooldowns.containsKey(getName())) {
            if (cooldowns.get(getName()) - System.currentTimeMillis() >= Long.parseLong(properties.skillInfo.get(getName() + "cooldown"))) {
                cooldowns.put(getName(), System.currentTimeMillis());
            } else {
                plugin.getMessager().send(player, "Sorry, that skill is still on cooldown!");
                return;
            }
        }

        if (!(heroClass.getSkills().contains("HARMTOUCH"))) {
            plugin.getMessager().send(player, "Sorry, that ability isn't for your class!");
            return;
        }

        double dx = player.getLocation().getX() - target.getLocation().getX();
        double dz = player.getLocation().getZ() - target.getLocation().getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        if (distance < 15) {
            int damage = (int) (target.getHealth() - (plugin.getConfigManager().getProperties().getLevel(hero.getExperience()) * 0.5));
            EntityDamageByEntityEvent damageEntity = new EntityDamageByEntityEvent(player, target, DamageCause.CUSTOM, damage);
            plugin.getServer().getPluginManager().callEvent(damageEntity);
            if (damageEntity.isCancelled() == false) {
                target.setHealth(damageEntity.getDamage());
            }
        } else {
            plugin.getMessager().send(player, "Sorry, that person isn't close enough!");
        }
    }

}
