package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistance.PlayerManager;
import com.herocraftonline.dev.heroes.util.Properties;
import com.nijiko.coelho.iConomy.iConomy;

public class CClassCommand extends BaseCommand {
    public CClassCommand(Heroes plugin) {
        super(plugin);
        name = "Class";
        description = "Changes a persons professions";
        usage = "/class change <class>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("class change");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (Properties.validateClass(args[0]) == "") {
                Heroes.sendMessage((Player) sender, "Sorry, that isn't a class!");
                return;
            }
            String cClass = Properties.validateClass(args[0]);
            if (Properties.primaryClass(cClass)) {
                if (PlayerManager.getClass((Player) sender) == "Vagrant") {
                    PlayerManager.setClass((Player) sender, cClass);
                    Heroes.sendMessage((Player) sender, "Welcome to the path of the " + cClass);
                } else {
                    changeClass((Player) sender, cClass);
                    Heroes.sendMessage((Player) sender, "Welcome to the path of the " + cClass);
                }
            } else {
                Heroes.sendMessage((Player) sender, "Sorry " + cClass + " isn't a primary class!");
            }
        }
    }

    public static void changeClass(Player p, String c) {
        if (Properties.iConomy == true) {
            // Heroes.log.info("iConomy setting found!");
            if (iConomy.getBank().getAccount(p.getName()).hasOver(Properties.swapcost)) {
                iConomy.getBank().getAccount(p.getName()).add(Properties.swapcost * -1);
            }
            PlayerManager.setClass(p, c);
        } else {
            PlayerManager.setClass(p, c);
        }
    }
}
