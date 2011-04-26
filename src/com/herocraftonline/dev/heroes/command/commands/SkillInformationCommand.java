package com.herocraftonline.dev.heroes.command.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.command.skill.Skill;

public class SkillInformationCommand extends BaseCommand {

    public SkillInformationCommand(Heroes plugin) {
        super(plugin);
        name = "SkillInformation";
        description = "Player Skill information";
        usage = "/hskills";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("heroes skills");
        identifiers.add("h skills");
        identifiers.add("hskills");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        Player p = (Player) sender;

        List<BaseCommand> sortSkills = plugin.getCommandManager().getCommands();
        List<Skill> skills = new ArrayList<Skill>();

        // Filter out Skills from the command list.
        for (BaseCommand skill : sortSkills) {
            if (skill instanceof Skill) {
                skills.add((Skill) skill);
            }
        }

        sender.sendMessage("§c-----[ " + "§fYour Skills§c ]-----");

        for (Skill skill : skills) {
            if (plugin.getHeroManager().getHero(p).getPlayerClass().hasSkill(skill.getName())) {
                sender.sendMessage("  §a" + skill.getName());
            }
        }
    }
}
