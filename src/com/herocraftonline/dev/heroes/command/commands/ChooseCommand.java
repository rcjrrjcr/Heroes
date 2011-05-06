package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.api.ClassChangeEvent;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;
import com.herocraftonline.dev.heroes.util.Properties;
import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.coelho.iConomy.system.Account;

public class ChooseCommand extends BaseCommand {

    public ChooseCommand(Heroes plugin) {
        super(plugin);
        name = "Choose Class";
        description = "Selects a new profession or specialty";
        usage = "/hero choose §9<type>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("hero choose");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }

        Player player = (Player) sender;
        Hero hero = plugin.getHeroManager().getHero(player);
        HeroClass currentClass = hero.getHeroClass();
        HeroClass newClass = plugin.getClassManager().getClass(args[0]);
        Properties prop = plugin.getConfigManager().getProperties();

        if (newClass == null) {
            Messaging.send(player, "Class not found.");
            return;
        }

        if (newClass == currentClass) {
            Messaging.send(player, "Y U DO? AMNESIA? NO GOOD!");
            return;
        }

        if (!newClass.isPrimary()) {
            if (newClass.getParent() != currentClass) {
                Messaging.send(player, "Sorry, that specialty doesn't belong to $1.", currentClass.getName());
                return;
            }

            if (!hero.getMasteries().contains(currentClass.getName())) {
                Messaging.send(player, "You must master $1 before specializing!", currentClass.getName());
                return;
            }
        }

        if (Heroes.Permissions != null && newClass != plugin.getClassManager().getDefaultClass()) {
            if (!Heroes.Permissions.has(player, "heroes.classes." + newClass.getName().toLowerCase())) {
                Messaging.send(player, "You don't have permission for $1.", newClass.getName());
                return;
            }
        }

        int cost = currentClass == plugin.getClassManager().getDefaultClass() ? 0 : prop.swapCost;

        if (prop.iConomy && Heroes.iConomy != null && cost > 0) {
            Account account = iConomy.getBank().getAccount(player.getName());
            if (!account.hasEnough(cost)) {
                Messaging.send(hero.getPlayer(), "Not enough money (costs " + iConomy.getBank().format(cost) + ")! ");
                return;
            }
        }

        int currentExp = hero.getExp();
        hero.setHeroClass(newClass);

        ClassChangeEvent event = new ClassChangeEvent(hero, currentClass, newClass);
        plugin.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            hero.setHeroClass(currentClass);
            hero.setExp(currentExp);
            return;
        }

        if (prop.iConomy && Heroes.iConomy != null && cost > 0) {
            Account account = iConomy.getBank().getAccount(player.getName());
            account.subtract(cost);
        }

        Messaging.send(player, "Welcome to the path of the $1!", newClass.getName());
    }

}
