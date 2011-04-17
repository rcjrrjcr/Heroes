package com.herocraftonline.dev.heroes.command.skills;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;

public class SkillTame extends Skill {

    // TODO: Register this command in Heroes
    public SkillTame(Heroes plugin) {
        super(plugin);
        name = "Tame";
        description = "Skill - Tame";
        usage = "/tame";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("tame");
    }

    @Override
    public void use(Player user, String[] args) {
        
    }

}
