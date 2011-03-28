package com.herocraftonline.dev.heroes;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.herocraftonline.dev.heroes.persistence.HeroManager;

@SuppressWarnings("unused")
public class HPlayerListener extends PlayerListener {
    private final Heroes plugin;

    public HPlayerListener(Heroes instance) {
        plugin = instance;
    }

    @Override
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        HeroManager heroManager = plugin.getHeroManager();
        heroManager.loadHeroFile(player);
    }
    
    @Override
    public void onPlayerQuit(PlayerEvent event) {
        Player player = event.getPlayer();
        HeroManager heroManager = plugin.getHeroManager();
        heroManager.saveHeroFile(player);
    }

}
