package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public class PartyInfoCommand extends BaseCommand {

    public PartyInfoCommand(Heroes plugin) {
        super(plugin);
        name = "Party Info";
        description = "Display party information";
        usage = "/heroes party info";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("heroes party info");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args[0].equalsIgnoreCase("me")) {
                if (plugin.getHeroManager().getHero(p).getParty() != null) {
                    sender.sendMessage(ChatColor.RED + "You're in the party " + ChatColor.BLUE + plugin.getHeroManager().getHero(p).getParty().getName());
                    sender.sendMessage(ChatColor.RED + "The owner of the party is " + ChatColor.BLUE + plugin.getHeroManager().getHero(p).getParty().getLeader().getName());
                    return;
                } else {
                    sender.sendMessage(ChatColor.RED + "You aren't in a party");
                }
            } else if (plugin.getServer().getPlayer(args[0]) != null) {
                p = plugin.getServer().getPlayer(args[0]);
                if (plugin.getHeroManager().getHero(p).getParty() != null) {
                    sender.sendMessage(ChatColor.RED + "They're in the party " + ChatColor.BLUE + plugin.getHeroManager().getHero(p).getParty().getName());
                    sender.sendMessage(ChatColor.RED + "The owner of the party is " + ChatColor.BLUE + plugin.getHeroManager().getHero(p).getParty().getLeader().getName());
                    return;
                } else {
                    sender.sendMessage(ChatColor.RED + "They aren't in a party");
                }
            }
        }
    }

}
