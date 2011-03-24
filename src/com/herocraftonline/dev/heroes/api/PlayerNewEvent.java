package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class PlayerNewEvent extends PlayerEvent{
	protected Player player;
	
	public PlayerNewEvent(Type type, Player player) {
		super(type);
		this.player = player;
	}
	
	/**
	 * Returns the player
	 * @return
	 */
	public Player getPlayer(){
		return player;
	}
}
