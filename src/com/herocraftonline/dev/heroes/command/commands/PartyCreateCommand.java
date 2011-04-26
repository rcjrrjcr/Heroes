package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.party.HeroParty;

public class PartyCreateCommand extends BaseCommand {

    public PartyCreateCommand(Heroes plugin) {
        super(plugin);
        name = "PartyCreate";
        description = "Creates a party";
        usage = "/hero party create <name>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("hero party create");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (!Heroes.Permissions.has((Player) sender, "heroes.party.create")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to do this");
                return;
            }
            Player p = (Player) sender;
            if (plugin.getHeroManager().getHero(p).getParty() != null) {
                sender.sendMessage(ChatColor.RED + "You're already in a party");
                return;
            }

            plugin.getPartyManager().addHeroParty(new HeroParty(p, args[0]));
            sender.sendMessage(ChatColor.RED + "You're now the owner of a party!");
        }
    }

}
