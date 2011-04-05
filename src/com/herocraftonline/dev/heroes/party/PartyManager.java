package com.herocraftonline.dev.heroes.party;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.persistence.Hero;



public class PartyManager {
    
    private Heroes plugin;
    private Set<HeroParty> parties;
    
    public PartyManager(Heroes plugin){
        this.plugin = plugin;
        this.parties = new HashSet<HeroParty>();
    }
    
    public HeroParty createHeroParty(Player leader){
        return new HeroParty(leader);
    }
    
    public void addHeroParty(HeroParty party){
        parties.add(party);
    }
    
    public void removeHeroParty(HeroParty party){
        parties.remove(party);
    }
    
}
