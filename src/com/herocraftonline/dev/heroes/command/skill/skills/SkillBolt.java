package com.herocraftonline.dev.heroes.command.skill.skills;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;

public class SkillBolt extends TargettedSkill {

    public SkillBolt(Heroes plugin) {
        super(plugin);
        name = "Bolt";
        description = "Skill - Bolt";
        usage = "/skill bolt <target>";
        minArgs = 0;
        maxArgs = 1;
        identifiers.add("skill bolt");
    }

    @Override
    public void init() {
        maxDistance = config.getInt("max-distance", 20);
    }

    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = Configuration.getEmptyNode();
        node.setProperty("max-distance", 20);
        return node;
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Player player = hero.getPlayer();

        if (target == player) {
            Messaging.send(player, "Sorry, you can't target yourself!");
            return false;
        }

        EntityDamageByEntityEvent damageEntityEvent = new EntityDamageByEntityEvent(player, target, DamageCause.CUSTOM, 0);
        plugin.getServer().getPluginManager().callEvent(damageEntityEvent);
        if (damageEntityEvent.isCancelled()) {
            return false;
        }

        List<Entity> entityList = target.getNearbyEntities(10, 10, 10);
        for (Entity n : entityList) {
            if (n instanceof LivingEntity) {
                if (n != player) {
                    target.getWorld().strikeLightning(n.getLocation());
                }
            }
        }
        target.getWorld().strikeLightning(target.getLocation());

        String targetName = target instanceof Player ? ((Player) target).getName() : target.getClass().getSimpleName().substring(5);
        notifyNearbyPlayers(player.getLocation().toVector(), "$1 used $2 on $3!", player.getName(), name, targetName);
        return false;
    }
}
