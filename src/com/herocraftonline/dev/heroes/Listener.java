package com.herocraftonline.dev.heroes;

import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.herocraftonline.dev.heroes.persistance.player;

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
