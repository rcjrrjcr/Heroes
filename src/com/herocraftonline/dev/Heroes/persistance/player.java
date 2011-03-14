package com.herocraftonline.dev.Heroes.persistance;

import java.sql.ResultSet;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.Heroes.Heroes;
import com.herocraftonline.dev.Heroes.util.Properties;

/**
 * Player management
 * 
 * @author Herocraft's Plugin Team
 */
public class player {

	public int getExp(Player p) throws Exception{
		ResultSet pRS = Heroes.sql.trySelect("SELECT * FROM players");
		while(pRS.next()){
			if(pRS.getString("name").equalsIgnoreCase(p.getName())){
				return pRS.getInt("exp");
			}
		}
		return -1;
	}

	public static void setExp(Player p, Integer e) throws Exception{
		String n = p.getName();
		ResultSet pRS = Heroes.sql.trySelect("SELECT * FROM players");
		while(pRS.next()){
			if(pRS.getString("name").equalsIgnoreCase(p.getName())){
				Heroes.sql.tryUpdate("UPDATE players SET exp="+ e +" WHERE name=" + n);
			}
		}
	}

	public int getClass(Player p) throws Exception{
		ResultSet pRS = Heroes.sql.trySelect("SELECT * FROM players");
		while(pRS.next()){
			if(pRS.getString("name").equalsIgnoreCase(p.getName())){
				return pRS.getInt("class");
			}
		}
		return -1;
	}

	public static void setClass(Player p, String c) throws Exception{
		String n = p.getName();
		ResultSet pRS = Heroes.sql.trySelect("SELECT * FROM players");
		while(pRS.next()){
			Heroes.sql.tryUpdate("UPDATE players SET class="+ c +" WHERE name=" + n);
		}
	}

	public static void newPlayer(Player p){
		String n = p.getName();
		Heroes.sql.tryUpdate("INSERT INTO players(id, name, class, exp, mana) VALUES('" + Heroes.sql.tableSize("players") + 1 + "', '" + n + "','Vagrant', '0', '0') ");
	}

	public static boolean checkPlayer(String n){
		ResultSet pRS = Heroes.sql.trySelect("SELECT * FROM players");
		try {
			while(pRS.next()){
				if(pRS.getString("name").equalsIgnoreCase(n)){
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static int getLevel(Integer exp){
		int n = 0;
		for(Integer i : Properties.level){
			if(!(exp >= i)){
				return n;
			}
			n++;
		}
		return -1;
	}

}
