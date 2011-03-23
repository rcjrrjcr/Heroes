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

public class SelectProfession extends BaseCommand {
    
    public SelectProfession(Heroes plugin) {
        super(plugin);
        name = "Select Profession";
        description = "Selects a new profession";
        usage = "/heroes profession §9<type>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("heroes profession");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            HeroClass profession = plugin.getClassManager().getClass(args[0]);
            if (profession != null) {
                if (profession.isPrimary()) {
                    PlayerManager playerManager = plugin.getPlayerManager();
                    if (playerManager.getClass(player).equals(plugin.getClassManager().getDefaultClass())) {
                        playerManager.setClass(player, profession);
                    } else {
                        changeClass(player, profession);
                    }
                    Messaging.send(player, "Welcome to the path of the $1!", profession.getName());
                } else {
                    Messaging.send(player, "Sorry, $1 isn't a profession!", profession.getName());
                }
            } else {
                Messaging.send(player, "Sorry, that isn't a profession!");
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
