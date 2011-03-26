package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class NewPlayerEvent extends CustomPlayerEvent {
    
    public NewPlayerEvent(Player player) {
        super(player);
    }
    
}
