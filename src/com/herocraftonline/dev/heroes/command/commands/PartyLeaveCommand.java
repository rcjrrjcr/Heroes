package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;

public class PartyLeaveCommand extends BaseCommand {

    public PartyLeaveCommand(Heroes plugin) {
        super(plugin);
        name = "Party Leave";
        description = "Leaves a party";
        usage = "/heroes party leave";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("heroes party leave");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {

            Player p = (Player) sender;
            plugin.getPartyManager().dispatchMessage(plugin.getHeroManager().getHero(p).getParty(), ChatColor.BLUE + p.getName() + ChatColor.RED + " has left the party");
            plugin.getHeroManager().getHero(p).setParty(null);
        }
    }

}
