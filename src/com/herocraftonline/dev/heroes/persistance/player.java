package com.herocraftonline.dev.heroes.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.util.Properties;

/**
 * Player management
 * 
 * @author Herocraft's Plugin Team
 */
public class player {

	public static int getExp(Player p){
		ResultSet pRS = Heroes.sql.trySelect("SELECT * FROM players WHERE name='" + p.getName() + "'");
		try {
		    if(pRS.getFetchSize()>0){
		        return pRS.getInt("exp");
		    } else {
		        return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void setExp(Player p, Integer e) throws Exception {
		String n = p.getName();
        Heroes.sql.tryUpdate("UPDATE players SET `exp`=" + e + " WHERE `name`=" + n);
	}

	public static String getClass(Player p) {
	    String test = null;
	    try{
            ResultSet pRS = Heroes.sql.trySelect("SELECT * FROM players");
            while (pRS.next()) {
                if (pRS.getString("name").equalsIgnoreCase(p.getName())) {
                    test = pRS.getString("class");
                    System.out.print(test);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return test;
	}

	public static void setClass(Player p, String c) {
		String n = p.getName();
		Heroes.sql.tryUpdate("UPDATE players SET class='" + c + "' WHERE name='" + n + "'");
	}

	public static void newPlayer(Player p) {
		String n = p.getName();
		Heroes.sql.tryUpdate("INSERT INTO 'players' (name, class, exp, mana) VALUES ('" + n + "','Vagrant', '0', '0') ");
	}

	public static boolean checkPlayer(String n) {
		ResultSet pRS = Heroes.sql.trySelect("SELECT * FROM players WHERE name='" + n + "'");
		
		int i = 0;
		try {
	        while(pRS.next()){
	            i++;
	        }
        } catch (SQLException e) { e.printStackTrace(); }
		
        System.out.print(pRS.toString());
        
	    if(i>0){
            return true;
        } else {
            return false;
        }
	}

	public static int getLevel(Integer exp) {
		int n = 0;
		for (Integer i : Properties.level) {
			if (!(exp >= i)) {
				return n;
			}
			n++;
		}
		return -1;
	}
	
	


}
