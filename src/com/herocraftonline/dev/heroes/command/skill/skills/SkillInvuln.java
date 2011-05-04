package com.herocraftonline.dev.heroes.command.skill.skills;

import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillInvuln extends ActiveSkill {
    
    private int duration;

    public SkillInvuln(Heroes plugin) {
        super(plugin);
        name = "Invuln";
        description = "Skill - Invuln";
        usage = "/skill invuln";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("skill invuln");
        
        registerEvent(Type.ENTITY_DAMAGE, new SkillEntityListener(), Priority.Normal);
    }
    
    @Override
    public void init() {
        duration = config.getInt("duration", 10000);
    }

    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = Configuration.getEmptyNode();
        node.setProperty("duration", 10000);
        return node;
    }

    @Override
    public boolean use(Hero hero, String[] args) {
        Player player = hero.getPlayer();
        final String playerName = player.getName();
        hero.getEffects().put(name, System.currentTimeMillis() + (double)duration);

        notifyNearbyPlayers(player.getLocation().toVector(), "$1 used $2!", playerName, name);
        plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                Player player = plugin.getServer().getPlayer(playerName);
                if (player != null) {
                    notifyNearbyPlayers(player.getLocation().toVector(), "$1 lost $2.", playerName, name);
                }
            }
        }, (long)(duration * 0.02));
        return true;
    }

    public class SkillEntityListener extends EntityListener {

        @Override
        public void onEntityDamage(EntityDamageEvent event) {
            if (event.isCancelled()) {
                return;
            }
            
            Entity defender = event.getEntity();
            if (defender instanceof Player) {
                Player player = (Player) defender;
                Map<String, Double> effects = plugin.getHeroManager().getHero(player).getEffects();
                if (effects.containsKey(name) && effects.get(name) > System.currentTimeMillis()) {
                    event.setCancelled(true);
                }
            }
        }

    }
}
