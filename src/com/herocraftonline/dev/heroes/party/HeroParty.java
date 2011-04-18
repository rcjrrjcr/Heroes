package com.herocraftonline.dev.heroes.party;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.entity.Player;

public class HeroParty {

    protected HashMap<Player, Integer> members;
    protected boolean pvp;
    protected boolean exp;
    protected Player leader;
    protected int level;
    protected String name;

    public HeroParty(Player leader, String name) {
        this.leader = leader;
        this.members = new HashMap<Player, Integer>();
        this.pvp = false;
        this.exp = false;
        this.name = name;
    }

    public Set<Player> getMembers() {
        return members.keySet();
    }

    public Player getLeader() {
        return leader;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public void addMember(Player member, int permissions) {
        members.put(member, permissions);
    }

    public void removeMember(Player member) {
        members.remove(member);
    }

    public void setLeader(Player leader) {
        this.leader = leader;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getPvp() {
        return pvp;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }

    public boolean getExp() {
        return exp;
    }

    public void setExp(boolean exp) {
        this.exp = exp;
    }

}
