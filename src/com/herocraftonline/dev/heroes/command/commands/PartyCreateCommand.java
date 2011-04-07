package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.party.HeroParty;

public class PartyCreateCommand extends BaseCommand {

    public PartyCreateCommand(Heroes plugin) {
        super(plugin);
        name = "PartyCreate";
        description = "Creates a party";
        usage = "/heroes party create";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("heroes party create");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (!Heroes.Permissions.has((Player) sender, "heroes.party.create")) {
                return;
            }
            
            for(HeroParty heroParty : plugin.getPartyManager().getHeroParties()){
                if(heroParty.getMembers().contains(((Player) sender).getName())){
                    return;
                }
            }
            plugin.getPartyManager().addHeroParty(new HeroParty((Player) sender));
            sender.sendMessage(ChatColor.RED + "You're now the owner of a party!");
        }
    }

}
