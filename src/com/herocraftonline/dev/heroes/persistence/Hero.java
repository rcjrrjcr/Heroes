package com.herocraftonline.dev.heroes.persistence;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.classes.HeroClass;

public class Hero {

    protected Player player;
    protected HeroClass playerClass;
    protected int experience;
    protected int mana;

    public Hero(Player player, HeroClass playerClass, int experience, int mana) {
        this.player = player;
        this.playerClass = playerClass;
        this.experience = experience;
        this.mana = mana;
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

    public void setPlayerClass(HeroClass playerClass) {
        this.playerClass = playerClass;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public boolean equals(Object o) {
        if (o instanceof Hero) {
            return player.getName().equalsIgnoreCase(((Hero) o).getPlayer().getName());
        } else {
            return false;
        }
    }
}
