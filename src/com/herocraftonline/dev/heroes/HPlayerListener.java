package com.herocraftonline.dev.heroes;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.herocraftonline.dev.heroes.persistence.HeroManager;
import com.herocraftonline.dev.heroes.classes.HeroClass.Spells;

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
    
    public void onPlayerMove(PlayerMoveEvent event){
        if(plugin.getHeroManager().getHero(event.getPlayer()).getEffects().containsKey("blackjack")){
            event.getPlayer().setVelocity(event.getPlayer().getVelocity().multiply(0.1));
        }
    }
    
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
            for(Spells n : Spells.values()){
                // TODO: Finish this
            }
        }
    }

}
