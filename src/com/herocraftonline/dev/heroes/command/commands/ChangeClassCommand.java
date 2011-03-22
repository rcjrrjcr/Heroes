package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistance.PlayerManager;
import com.herocraftonline.dev.heroes.util.Messaging;
import com.herocraftonline.dev.heroes.util.Properties;
import com.nijiko.coelho.iConomy.iConomy;

public class ChangeClassCommand extends BaseCommand {
    public ChangeClassCommand(Heroes plugin) {
        super(plugin);
        name = "Class Change";
        description = "Changes a persons professions";
        usage = "/class change §9<class>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("class change");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            HeroClass newClass = plugin.getClassManager().getClass(args[0]);
            if (newClass != null) {
                if (newClass.isPrimary()) {
                    PlayerManager playerManager = plugin.getPlayerManager();
                    if (playerManager.getClass(player).isStarterClass()) {
                        playerManager.setClass(player, newClass);
                    } else {
                        changeClass(player, newClass);
                    }
                    Messaging.send(player, "Welcome to the path of the $1!", newClass.getName());
                } else {
                    Messaging.send(player, "Sorry, $1 isn't a primary class!", newClass.getName());
                }
            } else {
                Messaging.send(player, "Sorry, that isn't a class!");
            }
        }
    }

    public void changeClass(Player player, HeroClass newClass) {
        if (Properties.iConomy == true) {
            if (iConomy.getBank().getAccount(player.getName()).hasOver(Properties.swapcost)) {
                iConomy.getBank().getAccount(player.getName()).add(Properties.swapcost * -1);
            }
            plugin.getPlayerManager().setClass(player, newClass);
        } else {
            plugin.getPlayerManager().setClass(player, newClass);
        }
    }
}
