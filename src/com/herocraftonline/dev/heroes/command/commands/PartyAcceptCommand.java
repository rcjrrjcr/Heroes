package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public class PartyAcceptCommand extends BaseCommand {

    public PartyAcceptCommand(Heroes plugin) {
        super(plugin);
        name = "PartyAccept";
        description = "Respond to the invite for a party";
        usage = "/heroes party accept <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("heroes party accept");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (!Heroes.Permissions.has((Player) sender, "heroes.party.accept")) {
                return;
            }
            
            if(plugin.getServer().getPlayer(args[0]) != null){
                return;
            }

            if(plugin.getHeroManager().getHero((Player) sender).getInvites().containsKey(args[0])){
                plugin.getHeroManager().getHero((Player) sender).setParty(plugin.getHeroManager().getHero((Player) sender).getParty());
                plugin.getPartyManager().dispatchMessage(plugin.getHeroManager().getHero((Player) sender).getParty(), ChatColor.RED + ((Player) sender).getName() + " Has joined the party!");
                plugin.getHeroManager().getHero((Player) sender).getInvites().remove(args[0]);
            }else{
                sender.sendMessage(ChatColor.RED + "Sorry, that party hasn't invited you in yet!");
            }
        }
    }

}