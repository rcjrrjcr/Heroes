package com.herocraftonline.dev.heroes;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.herocraftonline.dev.heroes.persistence.HeroManager;

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
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        HeroManager heroManager = plugin.getHeroManager();
        heroManager.saveHeroFile(player);
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.plugin.swapNetServerHandler(player);
        this.plugin.inventoryCheck(player);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.getHeroManager().getHero(player).getBinds().containsKey(event.getMaterial())) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                String[] args = plugin.getHeroManager().getHero(player).getBinds().get(event.getMaterial());
                plugin.onCommand(player, null, args[0], Arrays.copyOf(args, 1));
            }
        }
    }
}
