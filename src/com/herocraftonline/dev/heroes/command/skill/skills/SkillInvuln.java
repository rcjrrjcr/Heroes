package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveEffectSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.persistence.HeroEffects;

public class SkillInvuln extends ActiveEffectSkill {

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
        String playerName = player.getName();
        hero.getEffects().putEffect(name, (double) duration);

        notifyNearbyPlayers(player.getLocation().toVector(), "$1 used $2!", playerName, name);
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
                HeroEffects effects = plugin.getHeroManager().getHero(player).getEffects();
                if (effects.hasEffect(name)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
