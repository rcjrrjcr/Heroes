package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class LevelEvent extends ExperienceGainEvent{
    protected Player player;
    protected int experience;
    protected int toLevel;
    protected int fromLevel;
    
    public LevelEvent(Player player, int exp, int toLevel, int fromLevel) {
        super(player, exp);
        this.experience = exp;
        this.toLevel = toLevel;
        this.fromLevel = fromLevel;
    }

    public int getToLevel() {
        return toLevel;
    }

    public void setToLevel(int toLevel) {
        this.toLevel = toLevel;
    }

    public int getFromLevel() {
        return fromLevel;
    }

    public Player getPlayer() {
        return player;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }


}
