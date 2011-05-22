package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;

public class PartyInviteCommand extends BaseCommand {

    public PartyInviteCommand(Heroes plugin) {
        super(plugin);
        name = "Party Invite";
        description = "Invite a player to your party";
        usage = "/party invite <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("party invite");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Hero pHero = plugin.getHeroManager().getHero(p);

            if (pHero.getParty() == null) {
                Messaging.send(sender, "To invite someone to a party, you need to be in a party", (String) null);
                return;
            }

            if (pHero.getParty().getLeader() != p) {
                Messaging.send(sender, "To invite someone to a party, you need to be the party leader", (String) null);
                return;
            }

            if (plugin.getServer().getPlayer(args[0]) != null) {
                Player invitee = plugin.getServer().getPlayer(args[0]);
                Hero inviteeHero = plugin.getHeroManager().getHero(invitee);
                inviteeHero.getInvites().put(p, pHero.getParty());
                sender.sendMessage("§aThat player has been invited to the party");
                invitee.sendMessage("§a" + p.getName() + " has invited you their party. /party accept " + p.getName());
            }
        }
    }
}
