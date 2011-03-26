package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.ClassManager;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistance.Hero;
import com.herocraftonline.dev.heroes.persistance.HeroManager;
import com.herocraftonline.dev.heroes.util.Messaging;

public class SelectSpecialty extends BaseCommand {

    public SelectSpecialty(Heroes plugin) {
        super(plugin);
        name = "Select Specialty";
        description = "Allows you to advance from a primary class to it's specialty";
        usage = "/heroes specialty §9<type>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("heroes specialty");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            HeroManager heroManager = plugin.getHeroManager();
            ClassManager classManager = plugin.getClassManager();
            Hero hero = heroManager.getHero(player);
            HeroClass playerClass = hero.getPlayerClass();
            if (playerClass.isPrimary()) {
                HeroClass subClass = classManager.getClass(args[0]);
                if (subClass != null) {
                    if (subClass.getParent() == playerClass) {
                        hero.setPlayerClass(subClass);
                        Messaging.send(player, "Well done $1!", subClass.getName());
                    } else {
                        Messaging.send(player, "Sorry, that specialty doesn't belong to $1.", playerClass.getName());
                    }
                } else {
                    Messaging.send(player, "Sorry, that isn't a specialty!");
                }
            } else {
                Messaging.send(player, "You have already selected a specialty!");
            }
        }
    }
}
