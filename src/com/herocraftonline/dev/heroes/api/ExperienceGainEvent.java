package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class ExperienceGainEvent extends CustomPlayerEvent {
    protected int exp;

    public ExperienceGainEvent(Player player, int exp) {
        super(player);
        this.exp = exp;
    }

    /**
     * Returns the players experience
     * 
     * @return
     */
    public int getExp() {
        return exp;
    }

    /**
     * Sets the players experience
     * 
     * @param exp
     */
    public void setExp(int exp) {
        this.exp = exp;
    }

}
