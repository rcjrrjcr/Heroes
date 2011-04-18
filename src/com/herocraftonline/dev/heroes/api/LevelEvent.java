package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class LevelEvent extends ExperienceGainEvent{
    protected Player player;
    protected int experience;
    protected int level;
    
    public LevelEvent(Player player, int exp, int level) {
        super(player, exp);
        
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
