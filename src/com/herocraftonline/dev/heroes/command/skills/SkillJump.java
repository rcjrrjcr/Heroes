package com.herocraftonline.dev.heroes.command.skills;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public class SkillJump extends Skill {

    // TODO: Register this command in Heroes
    public SkillJump(Heroes plugin) {
        super(plugin);
        name = "Jump";
        description = "Skill - Jump";
        usage = "/jump";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("jump");
    }

    @Override
    public void use(Player player, String[] args) {
        Hero hero = plugin.getHeroManager().getHero(player);
        HeroClass heroClass = hero.getPlayerClass();

        Properties properties = plugin.getConfigManager().getProperties();
        Map<String, Long> cooldowns = hero.getCooldowns();
        if (cooldowns.containsKey(getName())) {
            if (cooldowns.get(getName()) - System.currentTimeMillis() >= properties.jumpcooldown) {
                cooldowns.put(getName(), System.currentTimeMillis());
            } else {
                plugin.getMessager().send(player, "Sorry, that skill is still on cooldown!");
                return;
            }
        }

        if (!(heroClass.getSkills().contains("JUMP"))) {
            plugin.getMessager().send(player, "Sorry, that ability isn't for your class!");
            return;
        }

        Vector velocity = player.getVelocity();
        player.setVelocity(velocity.setY((velocity.getY() * 2)));
    }
}
