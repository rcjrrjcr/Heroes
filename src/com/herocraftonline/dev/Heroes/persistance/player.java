package com.herocraftonline.dev.Heroes.persistance;

import java.sql.ResultSet;

import org.bukkit.entity.Player;

public class player {
	private SQLite sql;
	
	
	public int getExp(Player p) throws Exception{
		ResultSet pRS = sql.trySelect("SELECT * FROM players");
		while(pRS.next()){
			if(pRS.getString("name").equalsIgnoreCase(p.getName())){
				return pRS.getInt("exp");
			}
		}
		return -1;
	}
	
	public int getLevel(Player p) throws Exception{
		
		
		return 0;
	}
}
