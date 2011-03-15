package com.herocraftonline.dev.tmp;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.herocraftonline.dev.tmp.persistance.player;

@SuppressWarnings("unused")
public class Listener extends PlayerListener {
    private final Heroes plugin;

    public Listener(Heroes instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerLogin(PlayerLoginEvent e) {
        if (player.checkPlayer(e.getPlayer().getName()) == false) {
            player.newPlayer(e.getPlayer());
            Heroes.log.info("Created");
        } else {
            Heroes.log.info("Player Found");
        }
    }

}
