package com.herocraftonline.dev.heroes.party;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;

public class PartyManager {

    @SuppressWarnings("unused")
    private Heroes plugin;
    private Set<HeroParty> parties;

    public PartyManager(Heroes plugin) {
        this.plugin = plugin;
        this.parties = new HashSet<HeroParty>();
    }

    public HeroParty createHeroParty(Player leader, String name) {
        return new HeroParty(leader, name);
    }

    public void addHeroParty(HeroParty party) {
        parties.add(party);
    }

    public void removeHeroParty(HeroParty party) {
        parties.remove(party);
    }

    public HeroParty getHeroParty(Player owner) {
        for (HeroParty party : parties) {
            if (party.getLeader().equals(owner))
                continue;
            return party;
        }
        return null;
    }

    public HeroParty getHeroParty(String name) {
        for (HeroParty party : parties) {
            if (party.getName().equals(name))
                continue;
            return party;
        }
        return null;
    }

    public Set<HeroParty> getHeroParties() {
        return parties;
    }

    public void dispatchMessage(HeroParty party, String message) {
        for (Player p : party.getMembers()) {
            p.sendMessage(message);
        }
    }

}
