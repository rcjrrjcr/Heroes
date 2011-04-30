package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.api.ClassChangeEvent;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.persistence.HeroManager;
import com.herocraftonline.dev.heroes.util.Messaging;
import com.herocraftonline.dev.heroes.util.Properties;
import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.coelho.iConomy.system.Account;

public class SelectProfessionCommand extends BaseCommand {

    public SelectProfessionCommand(Heroes plugin) {
        super(plugin);
        name = "Select Profession";
        description = "Selects a new profession";
        usage = "/hero profession §9<type>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("hero profession");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            HeroClass profession = plugin.getClassManager().getClass(args[0]);
            if (profession != null) {
                if (profession.isPrimary()) {
                    Properties prop = plugin.getConfigManager().getProperties();
                    HeroManager heroManager = plugin.getHeroManager();
                    Hero hero = heroManager.getHero(player);
                    if (hero.getPlayerClass().equals(plugin.getClassManager().getDefaultClass())) {
                        changeClass(hero, profession, 0);
                    } else {
                        changeClass(hero, profession, prop.swapCost);
                    }
                    if (hero.getMasteries().contains(profession.getName())) {
                        hero.setExperience(prop.getExperience(prop.maxLevel));
                    } else {
                        hero.setExperience(0);
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

    public void changeClass(Hero hero, HeroClass newClass, int cost) {
        if (plugin.getConfigManager().getProperties().iConomy == true) {
            String playerName = hero.getPlayer().getName();
            Account account = iConomy.getBank().getAccount(playerName);
            if (account.hasEnough(cost)) {
                account.subtract(cost);
                hero.setPlayerClass(newClass);
                ClassChangeEvent event = new ClassChangeEvent(hero.getPlayer(), newClass);
                plugin.getServer().getPluginManager().callEvent(event);
            } else {
                Messaging.send(hero.getPlayer(), "Sorry, you don't have enough money!");
            }
        } else {
            hero.setPlayerClass(newClass);
            ClassChangeEvent event = new ClassChangeEvent(hero.getPlayer(), newClass);
            plugin.getServer().getPluginManager().callEvent(event);
        }
    }
}
