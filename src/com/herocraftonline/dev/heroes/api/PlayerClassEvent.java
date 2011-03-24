package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.classes.HeroClass;

@SuppressWarnings("serial")
public class PlayerClassEvent extends PlayerEvent{
	protected Player player;
	protected HeroClass playerClass;
	public PlayerClassEvent(Type type, Player player, HeroClass playerClass) {
		super(type);
		this.player = player;
		this.playerClass = playerClass;
	}
	
	/**
	 * Returns the player
	 * @return
	 */
	public Player getPlayer(){
		return player;
	}
	
	/**
	 * Gets the player class
	 * @return
	 */
	public HeroClass getPlayerClass(){
		return playerClass;
	}
	
	/**
	 * Set the player class
	 * @param playerClass
	 */
	public void setPlayerClass(HeroClass playerClass){
		this.playerClass = playerClass;
	}
}
