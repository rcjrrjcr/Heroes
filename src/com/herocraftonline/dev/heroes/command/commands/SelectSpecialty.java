package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.ClassManager;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistance.PlayerManager;
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
            PlayerManager playerManager = plugin.getPlayerManager();
            ClassManager classManager = plugin.getClassManager();
            HeroClass playerClass = playerManager.getClass(player);
            if (playerClass.isPrimary()) {
                HeroClass subClass = classManager.getClass(args[0]);
                if (subClass != null) {
                    if (subClass.getParent() == playerClass) {
                        playerManager.setClass(player, subClass);
                        Messaging.send(player, "Well done $1!", subClass.getName());
                    } else {
                        Messaging.send(player, "Sorry, that class doesn't belong to $1.", playerClass.getName());
                    }
                } else {
                    Messaging.send(player, "Sorry, that isn't a class!");
                }
            } else {
                Messaging.send(player, "You have already selected a secondary class!");
            }
        }
    }
}
