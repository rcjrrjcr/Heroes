package com.herocraftonline.dev.heroes.command.skill.skills;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;

public class SkillPiggify extends TargettedSkill {

    private List<Entity> pigs = Collections.synchronizedList(new LinkedList<Entity>());

    public SkillPiggify(Heroes plugin) {
        super(plugin);
        name = "Piggify";
        description = "Skill - Piggify";
        usage = "/skill piggify <target>";
        minArgs = 0;
        maxArgs = 1;
        identifiers.add("skill piggify");
        maxDistance = 20;

        registerEvent(Type.ENTITY_DAMAGE, new SkillEntityListener(), Priority.Normal);
    }

    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = Configuration.getEmptyNode();
        node.setProperty("duration", 10000);
        return node;
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Player player = hero.getPlayer();
        if (target == player) {
            Messaging.send(player, "You need a target.");
            return false;
        }

        // Throw a dummy damage event to make it obey PvP restricting plugins
        EntityDamageEvent event = new EntityDamageByEntityEvent(player, target, DamageCause.ENTITY_ATTACK, 0);
        plugin.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }

        Entity pig = target.getWorld().spawnCreature(target.getLocation(), CreatureType.PIG);
        pig.setPassenger(target);
        pigs.add(pig);
        plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                pigs.get(0).remove();
                pigs.remove(0);
            }
        }, (long) (config.getInt("duration", 10000) * 0.02));

        String targetName = (target instanceof Player) ? ((Player) target).getName() : target.getClass().getSimpleName().substring(5);
        notifyNearbyPlayers(player.getLocation().toVector(), "$1 used $2 on $3!", player.getName(), name, targetName);
        return true;
    }

    public class SkillEntityListener extends EntityListener {

        public void onEntityDamage(EntityDamageEvent event) {
            Entity entity = event.getEntity();
            if (pigs.contains(entity)) {
                event.setCancelled(true);
            }
        }

    }

}
