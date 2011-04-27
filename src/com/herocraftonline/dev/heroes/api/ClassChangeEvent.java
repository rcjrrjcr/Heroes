package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.classes.HeroClass;

@SuppressWarnings("serial")
public class ClassChangeEvent extends CustomPlayerEvent {

    protected HeroClass playerClass;
    protected final boolean cancelled = false;

    public ClassChangeEvent(Player player, HeroClass playerClass) {
        super(player);
        this.playerClass = playerClass;
    }

    /**
     * Gets the player class
     * 
     * @return
     */
    public HeroClass getPlayerClass() {
        return playerClass;
    }

    /**
     * Set the player class
     * 
     * @param playerClass
     */
    public void setPlayerClass(HeroClass playerClass) {
        this.playerClass = playerClass;
    }

}
