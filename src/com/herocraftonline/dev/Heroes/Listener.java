package com.herocraftonline.dev.Heroes;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.herocraftonline.dev.Heroes.persistance.player;
@SuppressWarnings("unused")
public class Listener extends PlayerListener {
	private final Heroes plugin;

	public Listener(Heroes instance) {
		plugin = instance;
	}

	public void onPlayerLogin(PlayerLoginEvent e){
		if(player.checkPlayer(e.getPlayer().getName()) == false){
			player.newPlayer(e.getPlayer());
			Heroes.Log.info("Created");
		}else{
			Heroes.Log.info("Player Found");
		}
	}


}
