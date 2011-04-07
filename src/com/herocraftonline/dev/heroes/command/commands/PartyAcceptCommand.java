package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.party.HeroParty;

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
            if (!Heroes.Permissions.has((Player) sender, "heroes.party.create")) {
                return;
            }
            
            if(plugin.getServer().getPlayer(args[0]) != null){
                return;
            }
            

        }
    }

}