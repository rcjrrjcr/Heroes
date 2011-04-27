package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.api.ClassChangeEvent;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.persistence.HeroManager;
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
                    HeroManager heroManager = plugin.getHeroManager();
                    Hero hero = heroManager.getHero(player);
                    if (hero.getPlayerClass().equals(plugin.getClassManager().getDefaultClass())) {
                        hero.setPlayerClass(profession);
                        ClassChangeEvent event = new ClassChangeEvent(hero.getPlayer(), profession);
                        plugin.getServer().getPluginManager().callEvent(event);
                    } else {
                        changeClass(hero, profession);
                    }
                    plugin.getMessager().send(player, "Welcome to the path of the $1!", profession.getName());
                } else {
                    plugin.getMessager().send(player, "Sorry, $1 isn't a profession!", profession.getName());
                }
            } else {
                plugin.getMessager().send(player, "Sorry, that isn't a profession!");
            }
        }
    }

    public void changeClass(Hero hero, HeroClass newClass) {
        if (plugin.getConfigManager().getProperties().iConomy == true) {
            String playerName = hero.getPlayer().getName();
            Account account = iConomy.getBank().getAccount(playerName);
            int swapcost = plugin.getConfigManager().getProperties().swapcost;
            if (account.hasEnough(swapcost)) {
                account.subtract(swapcost);
                hero.setPlayerClass(newClass);
                ClassChangeEvent event = new ClassChangeEvent(hero.getPlayer(), newClass);
                plugin.getServer().getPluginManager().callEvent(event);
            } else {
                plugin.getMessager().send(hero.getPlayer(), "Sorry, you don't have enough money!");
            }
        } else {
            hero.setPlayerClass(newClass);
            ClassChangeEvent event = new ClassChangeEvent(hero.getPlayer(), newClass);
            plugin.getServer().getPluginManager().callEvent(event);
        }
    }
}
