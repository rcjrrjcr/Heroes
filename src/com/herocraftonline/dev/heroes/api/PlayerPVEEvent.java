package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class PlayerPVEEvent extends PlayerEvent {
	protected Player player;
	protected Entity monster;
	protected int exp;
	public PlayerPVEEvent(Type type, Player attacker, Entity monster, int exp) {
		super(type);
		this.player = attacker;
		this.monster = monster;
		this.exp = exp;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Entity getMob(){
		return monster;
	}
	
	public int getExp(){
		return exp;
	}
	
	public void setExp(int exp){
		this.exp = exp;
	}

	
	
	
}
