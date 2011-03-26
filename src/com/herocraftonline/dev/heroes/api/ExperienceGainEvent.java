package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;


@SuppressWarnings("serial")
public class ExperienceGainEvent extends CustomPlayerEvent {
	protected Player player;
	protected int level;
	protected int exp;
	
	public ExperienceGainEvent(Player player, int level, int exp) {
		this.player = player;
		this.level = level;
		this.exp = exp;
	}
	/**
	 * Returns Player
	 * @return
	 */
	public Player getPlayer(){
		return player;
	}
	
	/**
	 * Returns the players level
	 * @return
	 */
	public int getLevel(){
		return level;
	}
	
	/**
	 * Returns the players experience
	 * @return
	 */
	public int getExp(){
		return exp;
	}
	
	/**
	 * Sets the players experience
	 * @param exp
	 */
	public void setExp(int exp){
		this.exp = exp;
	}
	
	

}
