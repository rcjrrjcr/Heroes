package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.util.Properties;

@SuppressWarnings("serial")
public class PlayerExpEvent extends PlayerEvent{
	protected Player player;
	protected int level;
	protected int exp;
	
	public PlayerExpEvent(Type type, Player player, int level, int exp) {
		super(type);
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
