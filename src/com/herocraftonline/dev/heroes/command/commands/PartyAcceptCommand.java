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
            Player argPlayer = plugin.getServer().getPlayer(args[0]);
            Hero pHero = plugin.getHeroManager().getHero(p);
            if (pHero.getInvites().containsKey(argPlayer)) {
                pHero.setParty(pHero.getInvites().get(argPlayer));
                pHero.getInvites().remove(argPlayer);
            } else {
                sender.sendMessage("§aSorry, you don't have an invite from that player");
            }
        }
    }

}
