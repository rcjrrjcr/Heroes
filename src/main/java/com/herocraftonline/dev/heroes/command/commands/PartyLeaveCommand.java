package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;

public class PartyLeaveCommand extends BaseCommand {

    public PartyLeaveCommand(Heroes plugin) {
        super(plugin);
        name = "Party Leave";
        description = "Party leave command";
        usage = "/party leave";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("party leave");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Hero pHero = plugin.getHeroManager().getHero(p);

            Messaging.send(sender, "You now have no party", (String) null);
            if (pHero.getParty() != null) {
                if (pHero.getParty().getLeader() == p) {
                    Messaging.send(sender, "You now have no party", (String) null);
                    for (Player player : pHero.getParty().getMembers()) {
                        plugin.getHeroManager().getHero(player).setParty(null);
                        Messaging.send(sender, "Your party has been disbanded", (String) null);
                    }
                } else {
                    pHero.setParty(null);
                    Messaging.send(sender, "You now have no party", (String) null);
                }
            }
        }
    }

}
