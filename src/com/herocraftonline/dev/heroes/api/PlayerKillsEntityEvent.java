package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class PlayerKillsEntityEvent extends CustomPlayerEvent {
    protected Player player;
    protected Entity entity;
    protected int exp;

    public PlayerKillsEntityEvent(Player attacker, Entity defender, int exp) {
        this.player = attacker;
        this.entity = defender;
        this.exp = exp;
    }

    public Player getPlayer() {
        return player;
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
