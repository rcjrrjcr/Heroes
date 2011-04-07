package com.herocraftonline.dev.heroes.party;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;



import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;



public class PartyManager {

    private Heroes plugin;
    private Set<HeroParty> parties;
    private HashMap<Player[], HeroParty> invites = new HashMap<Player[], HeroParty>();

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
    
    public HeroParty getHeroParty(Player owner){
        for(HeroParty party : parties){
            if(party.getLeader().equals(owner)) continue;
            return party;
        }
        return null;
    }
    
    public Set<HeroParty> getHeroParties(){
        return parties;
    }
    
    public HashMap<Player[], HeroParty> getInvites(){
        return invites;
    }
    
    
}
