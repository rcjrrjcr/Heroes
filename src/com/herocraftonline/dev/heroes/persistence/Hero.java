package com.herocraftonline.dev.heroes.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.classes.HeroClass;

public class Hero {

    protected Player player;
    protected HeroClass playerClass;
    protected int experience;
    protected int mana;
    protected List<String> masteries;
    private HashMap<String, Long> cooldowns;


    public Hero(Player player, HeroClass playerClass, int experience, int mana, List<String> masteries) {
        this.player = player;
        this.playerClass = playerClass;
        this.experience = experience;
        this.mana = mana;
        this.masteries = masteries;
        this.cooldowns = new HashMap<String, Long>();
    }

    public Player getPlayer() {
        return player;
    }

    public HeroClass getPlayerClass() {
        return playerClass;
    }

    public int getExperience() {
        return experience;
    }

    public int getMana() {
        return mana;
    }

    public List<String> getMasteries() {
        return masteries;
    }

    public void setPlayerClass(HeroClass playerClass) {
        this.playerClass = playerClass;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMasteries(ArrayList<String> masterys) {
        this.masteries = masterys;
    }
    
    public HashMap<String, Long> getCooldowns(){
		return cooldowns;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Hero) {
            return player.getName().equalsIgnoreCase(((Hero) o).getPlayer().getName());
        } else {
            return false;
        }
    }
}
