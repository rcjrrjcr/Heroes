package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class AssignSkillCommand extends BaseCommand {

    public AssignSkillCommand(Heroes plugin) {
        super(plugin);
        name = "AssignSkill";
        description = "Assigns a skill to an item";
        usage = "/assign <spell>";
        minArgs = 0;
        maxArgs = 1000;
        identifiers.add("assign");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Hero hero = plugin.getHeroManager().getHero(player);
            HeroClass heroClass = hero.getPlayerClass();
            Material material = player.getItemInHand().getType();
            if (args.length > 0) {
                if (heroClass.hasSkill(args[0])) {
                    hero.bind(material, args);
                    plugin.getMessager().send(sender, "That has been assigned as your skill.");
                } else {
                    plugin.getMessager().send(sender, "That skill does not exist for your class.");
                }
            } else {
                hero.unbind(material);
                plugin.getMessager().send(sender, "Your equipped item is no longer bound to a skill.");
            }
        }
    }
}
