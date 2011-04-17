package com.herocraftonline.dev.heroes.command.skills;

import java.util.HashMap;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Properties;

public class SkillLayhands extends TargettedSkill {

    // TODO: Register this command in Heroes
    public SkillLayhands(Heroes plugin) {
        super(plugin);
        name = "Layhands";
        description = "Skill - Layhands";
        usage = "/layhands <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("layhands");
    }

    @Override
    public void use(Player player, LivingEntity target, String[] args) {
        Hero hero = plugin.getHeroManager().getHero(player);
        HeroClass heroClass = plugin.getClassManager().getClass(hero.toString());

        Properties properties = plugin.getConfigManager().getProperties();
        HashMap<String, Long> cooldowns = hero.getCooldowns();
        if (cooldowns.containsKey(getName())) {
            if (cooldowns.get(getName()) - System.currentTimeMillis() >= properties.layhandscooldown) {
                cooldowns.put(getName(), System.currentTimeMillis());
            } else {
                plugin.getMessager().send(player, "Sorry, that skill is still on cooldown!");
                return;
            }
        }

        if (!(heroClass.getSpells().contains(Spells.LAYHANDS))) {
            plugin.getMessager().send(player, "Sorry, that ability isn't for your class!");
            return;
        }
        
        target.setHealth(20);
    }
}
