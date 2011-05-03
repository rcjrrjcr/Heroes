package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.ClassManager;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.persistence.HeroManager;
import com.herocraftonline.dev.heroes.util.Messaging;
import com.herocraftonline.dev.heroes.util.Properties;

public class SelectSpecialtyCommand extends BaseCommand {
    Properties prop = plugin.getConfigManager().getProperties();

    public SelectSpecialtyCommand(Heroes plugin) {
        super(plugin);
        name = "Select Specialty";
        description = "Allows you to advance from a primary class to it's specialty";
        usage = "/hero spec(ialty) §9<type>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("hero specialty");
        identifiers.add("hero spec");
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
                if (prop.getLevel(hero.getExperience()) >= prop.maxLevel) {
                    HeroClass specialty = classManager.getClass(args[0]);
                    if (specialty != null) {
                        if (specialty.getParent() == playerClass) {
                            hero.setPlayerClass(specialty);
                            if (hero.getMasteries().contains(specialty.getName())) {
                                hero.setExperience(prop.getExperience(prop.maxLevel));
                            } else {
                                hero.setExperience(0);
                            }
                            Messaging.send(player, "Well done $1!", specialty.getName());
                        } else {
                            Messaging.send(player, "Sorry, that specialty doesn't belong to $1.", playerClass.getName());
                        }
                    } else {
                        Messaging.send(player, "Sorry, that isn't a specialty!");
                    }
                } else {
                    Messaging.send(player, "You must master your profession before choosing a specialty.");
                }
            } else {
                Messaging.send(player, "You have already selected a specialty!");
            }
        }
    }
}
