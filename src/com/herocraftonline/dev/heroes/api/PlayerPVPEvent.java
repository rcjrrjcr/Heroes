package com.herocraftonline.dev.heroes.api;

import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class PlayerPVPEvent extends PlayerEvent {
	protected Player attacker;
	protected Player defender;
	protected int exp;
	public PlayerPVPEvent(Type type, Player attacker, Player defender, int exp) {
		super(type);
		this.attacker = attacker;
		this.defender = defender;
		this.exp = exp;
	}
	
	public Player getDefender(){
		return defender;
	}
	
	public Player getAttacker(){
		return attacker;
	}
	
	public int getExp(){
		return exp;
	}
	
	public void setExp(int exp){
		this.exp = exp;
	}

}
