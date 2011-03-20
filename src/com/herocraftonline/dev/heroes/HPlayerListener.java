package com.herocraftonline.dev.heroes;

import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.herocraftonline.dev.heroes.persistance.PlayerManager;

@SuppressWarnings("unused")
public class HPlayerListener extends PlayerListener {
    private final Heroes plugin;

    public HPlayerListener(Heroes instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerLogin(PlayerLoginEvent e) {
        if (PlayerManager.checkPlayer(e.getPlayer().getName()) == false) {
            PlayerManager.newPlayer(e.getPlayer());
            Heroes.log.info("Created");
        } else {
            Heroes.log.info("Player Found");
        }
    }

}
