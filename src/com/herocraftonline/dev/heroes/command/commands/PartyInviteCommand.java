package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.party.HeroParty;

public class PartyInviteCommand extends BaseCommand {

    public PartyInviteCommand(Heroes plugin) {
        super(plugin);
        name = "PartyInvite";
        description = "Invite a player to the party";
        usage = "/heroes party invite <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("heroes party invite");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (!Heroes.Permissions.has((Player) sender, "heroes.party.create")) {
                return;
            }
            
            if(plugin.getServer().getPlayer(args[0]) != null){
                return;
            }
            
            if(plugin.getPartyManager().getHeroParty((Player) sender) != null){
                return;
            }
            
            Player player = plugin.getServer().getPlayer(args[0]);
            HeroParty newParty = plugin.getPartyManager().getHeroParty((Player) sender);
            Player[] pl = {player, (Player) sender};
            plugin.getPartyManager().getInvites().put(pl, newParty);
        }
    }

}