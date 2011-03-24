package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.util.Properties;

@SuppressWarnings("serial")
public class PlayerLevelEvent extends PlayerEvent{
	private final Heroes plugin;
	protected Player player;
	protected int level;
	
	protected PlayerLevelEvent(String type, Player player, int level, Heroes plugin) {
		super(type);
		this.player = player;
		this.level = level;
		this.plugin = plugin;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void setLevel(int level){
		plugin.getPlayerManager().setExp(player, Properties.level.get(level));
	}
	

}
