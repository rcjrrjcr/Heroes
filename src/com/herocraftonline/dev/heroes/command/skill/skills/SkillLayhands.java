package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.TargettedSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillLayhands extends TargettedSkill {

    public SkillLayhands(Heroes plugin) {
        super(plugin);
        name = "Layhands";
        description = "Skill - Layhands";
        usage = "/skill layhands <player>";
        minArgs = 0;
        maxArgs = 1;
        identifiers.add("skill layhands");
    }

    @Override
    public boolean use(Hero hero, LivingEntity target, String[] args) {
        Player player = hero.getPlayer();
        target.setHealth(20);
        String targetName = target instanceof Player ? ((Player) target).getName() : target.getClass().getSimpleName().substring(5);
        notifyNearbyPlayers(player.getLocation(), useText, player.getName(), name, target == player ? "himself" : targetName);
        return true;
    }
}
