package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class KillExperienceEvent extends CustomPlayerEvent {

    protected Entity entity;
    protected int exp;

    public KillExperienceEvent(Player attacker, Entity defender, int exp) {
        super(attacker);
        this.entity = defender;
        this.exp = exp;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

}
