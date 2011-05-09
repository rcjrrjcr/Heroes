package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;

public class PartyAcceptCommand extends BaseCommand {

    public PartyAcceptCommand(Heroes plugin) {
        super(plugin);
        name = "Party Accept";
        description = "Accept a players party invite";
        usage = "/party accept <player>";
        minArgs = 1;
        maxArgs = 1;
        identifiers.add("party accept");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Hero pHero = plugin.getHeroManager().getHero(p);

            if (pHero.getInvites().containsKey(args[0])) {
                pHero.setParty(pHero.getInvites().get(args[0]));
                pHero.getInvites().remove(args[0]);
            } else {
                sender.sendMessage("§aSorry, you don't have an invite from that player");
            }
        }
    }

}
