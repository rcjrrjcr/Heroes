package com.herocraftonline.dev.heroes.command.skills;

import org.bukkit.command.CommandSender;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseSkill;

public class SkillTame extends BaseSkill {

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
    public void execute(CommandSender sender, String[] args) {
        // TODO Bukkit doesn't allow for wolf taming to do done via this. Yet.
    }

}
