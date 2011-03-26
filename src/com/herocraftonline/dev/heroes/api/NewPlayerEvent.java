package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class NewPlayerEvent extends CustomPlayerEvent {
    protected Player player;

    public NewPlayerEvent(Player player) {
        this.player = player;
    }

    /**
     * Returns the player
     * 
     * @return
     */
    public Player getPlayer() {
        return player;
    }
}
