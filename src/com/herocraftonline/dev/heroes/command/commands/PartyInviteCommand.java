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
        usage = "/hero party invite <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("hero party invite");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (!Heroes.Permissions.has((Player) sender, "heroes.party.invite")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to do this");

                return;
            }

            if (plugin.getServer().getPlayer(args[0]) != null) {
                return;
            }

            if (plugin.getHeroManager().getHero((Player) sender).getParty() == null) {
                return;
            }

            Player player = plugin.getServer().getPlayer(args[0]);
            HeroParty newParty = plugin.getHeroManager().getHero((Player) sender).getParty();
            plugin.getHeroManager().getHero(player).getInvites().put(newParty.getName(), newParty);
            player.sendMessage(ChatColor.RED + ((Player) sender).getName() + " has invited you to " + newParty.getName());
        }
    }

}
