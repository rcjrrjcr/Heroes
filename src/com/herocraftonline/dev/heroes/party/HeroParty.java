package com.herocraftonline.dev.heroes.party;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

public class HeroParty {

    protected HashMap<Player, Integer> members;
    protected List<String> modes;
    protected Player leader;
    protected int level;
    protected String name;
    
    public HeroParty(Player leader, String name){
        this.leader = leader;
        this.members = new HashMap<Player, Integer>();
        this.modes = null;
        this.name = name;
    }
    
    public Set<Player> getMembers(){
        return members.keySet();
    }
    
    public List<String> getModes(){
        return modes;
    }
    
    public Player getLeader(){
        return leader;
    }
    
    public int getLevel(){
        return level;
    }
    
    public String getName(){
        return name;
    }
    
    public void addMember(Player member, int permissions){
        members.put(member, permissions);
    }
    
    public void removeMember(Player member){
        members.remove(member);
    }
    
    public void setMode(String mode){
        modes.add(mode);
    }
    
    public void unsetMode(String mode){
        modes.remove(mode);
    }
    
    public void setLeader(Player leader){
        this.leader = leader;
    }
    
    public void setLevel(int level){
        this.level = level;
    }
    
    public void setName(String name){
        this.name = name;
    }
}
