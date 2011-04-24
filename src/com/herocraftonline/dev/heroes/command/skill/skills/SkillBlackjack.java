package com.herocraftonline.dev.heroes.command.skill.skills;

import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillBlackjack extends ActiveSkill {

    public SkillBlackjack(Heroes plugin) {
        super(plugin);
        name = "Blackjack";
        description = "Skill - blackjack";
        usage = "/blackjack";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("blackjack");
        
        registerEvent(Type.ENTITY_DAMAGE, new SkillEntityListener(), Priority.Normal);
        registerEvent(Type.PLAYER_MOVE, new SkillPlayerListener(), Priority.Normal);
    }

    @Override
    public boolean use(Hero hero, String[] args) {
        hero.getEffects().put(name, System.currentTimeMillis() + 60000.0);
        return true;
    }
    
    @Override
    public ConfigurationNode getDefaultConfig() {
        ConfigurationNode node = Configuration.getEmptyNode();
        node.setProperty("stun-duration", 5.0);
        return node;
    }

    public class SkillEntityListener extends EntityListener {

        public void onEntityDamage(EntityDamageEvent event) {
            if (event instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent) event;
                Entity attackingEntity = subEvent.getDamager();
                Entity defendingEntity = subEvent.getEntity();
                if (attackingEntity instanceof Player && defendingEntity instanceof Player) {
                    Hero attackingHero = plugin.getHeroManager().getHero((Player) attackingEntity);
                    Hero defendingHero = plugin.getHeroManager().getHero((Player) defendingEntity);
                    Map<String, Double> effects = attackingHero.getEffects();
                    if (effects.containsKey(name)) {
                        defendingHero.getEffects().put("stunned", System.currentTimeMillis() + config.getDouble("stun-duration", 5.0));
                    }
                }
            }
        }

    }

    public class SkillPlayerListener extends PlayerListener {

        public void onPlayerMove(PlayerMoveEvent event) {
            Player player = event.getPlayer();
            Hero hero = plugin.getHeroManager().getHero(player);
            Map<String, Double> effects = hero.getEffects();
            if (effects.containsKey("stunned")) {
                if (effects.get("stunned") > System.currentTimeMillis()) {
                    event.setCancelled(true);
                } else {
                    effects.remove("stunned");
                }
            }
        }

    }
}
