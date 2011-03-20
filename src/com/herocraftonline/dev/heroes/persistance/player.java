package com.herocraftonline.dev.heroes.persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        String pClass = null;
	        
        try {
            Connection conn = Heroes.sql.getConnection();
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM players");
            r.next();
            pClass = r.getString("class");
            r.close() ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
	    
        return pClass;
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
	    int count = 0;
	    
        try {
            Connection conn = Heroes.sql.getConnection();
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM players WHERE name='" + n + "'");
            r.next();
            count = r.getInt("rowcount") ;
            r.close() ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
	    
	    if(count>0){
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
