package com.herocraftonline.dev.heroes.command.skill.skills;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.skill.ActiveSkill;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class SkillJump extends ActiveSkill {

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
    public boolean use(Hero hero, String[] args) {
        Player player = hero.getPlayer();
        Vector velocity = player.getVelocity();
        player.setVelocity(velocity.setY((velocity.getY() * 2)));
        return true;
    }
}
