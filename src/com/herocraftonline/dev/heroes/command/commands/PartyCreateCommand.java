package com.herocraftonline.dev.heroes.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.command.BaseCommand;
import com.herocraftonline.dev.heroes.party.HeroParty;
import com.herocraftonline.dev.heroes.party.PartyManager;
import com.herocraftonline.dev.heroes.persistence.Hero;
import com.herocraftonline.dev.heroes.util.Messaging;

public class PartyCreateCommand extends BaseCommand {

    public PartyCreateCommand(Heroes plugin) {
        super(plugin);
        name = "Party Create";
        description = "Create a party";
        usage = "/party create";
        minArgs = 0;
        maxArgs = 0;
        identifiers.add("party create");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Hero pHero = plugin.getHeroManager().getHero(p);
            if (pHero.getParty() != null) {
                Messaging.send(sender, "You need to leave the party your in first", (String) null);
                return;
            }

            PartyManager pManager = plugin.getPartyManager();
            pManager.addHeroParty(new HeroParty(p, pManager.getHeroParties().size() + 1));
            pHero.setParty(pManager.getHeroParty(p));
            sender.sendMessage("  §aYour party has been created!");
        }
    }
}
