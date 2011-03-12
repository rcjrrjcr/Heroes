package com.herocraftonline.dev.Heroes.persistance;

import java.sql.ResultSet;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.Heroes.Properties;

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

	public static int getLevel(int exp){
		int v = 0;
		for(Integer n : Properties.levelexp){
			Properties.levelexp.get(v);
			v++;
		}
		return v;
	}
}
