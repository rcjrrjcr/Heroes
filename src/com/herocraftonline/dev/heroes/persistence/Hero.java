package com.herocraftonline.dev.heroes.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;

public class Hero {

    protected Player player;
    protected HeroClass playerClass;
    protected int experience;
    protected int mana;
    protected List<String> masteries;
    protected HashMap<String, Long> cooldowns;
    protected HashMap<Entity, CreatureType> summons;
    protected HashMap<String, Long> effects;
    protected Spells skill;


    public Hero(Player player, HeroClass playerClass, int experience, int mana, List<String> masteries) {
        this.player = player;
        this.playerClass = playerClass;
        this.experience = experience;
        this.mana = mana;
        this.masteries = masteries;
        this.cooldowns = new HashMap<String, Long>();
        this.summons = new HashMap<Entity, CreatureType>();
        this.skill = null;
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
    
    public HashMap<Entity, CreatureType> getSummons(){
        return summons;
    }
    
    public HashMap<String, Long> getEffects(){
        return effects;
    }
    
    public Spells getSkill(){
        return skill;
    }
    
    public void setSkill(Spells skill){
        this.skill = skill;
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
