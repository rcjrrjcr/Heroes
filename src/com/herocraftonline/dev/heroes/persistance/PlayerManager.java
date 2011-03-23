package com.herocraftonline.dev.heroes.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import com.herocraftonline.dev.heroes.Heroes;
import com.herocraftonline.dev.heroes.classes.ClassManager;
import com.herocraftonline.dev.heroes.classes.HeroClass;
import com.herocraftonline.dev.heroes.util.Properties;

/**
 * Player management
 * 
 * @author Herocraft's Plugin Team
 */
public class PlayerManager {

    private Heroes plugin;
    
    public PlayerManager(Heroes plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Grab the given Players current Experience.
     * @param player
     * @return
     */
    public int getExp(Player player) {
        String name = player.getName(); // Grab the players name.
        int value = -1;
        try {
            ResultSet rs = plugin.getSqlManager().trySelect("SELECT * FROM players WHERE name='" + name + "'");
            rs.next();
            value = rs.getInt("exp");
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
    
    /**
     * Set the given Players Exp to the value given.
     * @param player
     * @param exp
     * @throws Exception
     */
    public void setExp(Player player, Integer exp) throws Exception {
        String name = player.getName();
        plugin.getSqlManager().tryUpdate("UPDATE players SET `exp`=" + exp + " WHERE `name`=" + name);
    }

    /**
     * Grab the given Players Class.
     * @param player
     * @return
     */
    public HeroClass getClass(Player player) {
        String pClass = null;
        String name = player.getName();
        try {
            ResultSet rs = plugin.getSqlManager().trySelect("SELECT * FROM players WHERE name='" + name + "'");
            rs.next();
            pClass = rs.getString("class");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        plugin.log(Level.SEVERE, pClass);
        return pClass == null ? null : plugin.getClassManager().getClass(pClass);
    }

    /**
     * Change the Players Class to the specified Class.
     * @param player
     * @param playerClass
     */
    public void setClass(Player player, HeroClass playerClass) {
        String name = player.getName();
        plugin.getSqlManager().tryUpdate("UPDATE players SET class='" + playerClass.getName() + "' WHERE name='" + name + "'");
    }

    /**
     * Insert the Player into the Database with the default class.
     * @param player
     */
    public void newPlayer(Player player) {
        String name = player.getName();
        String className = plugin.getClassManager().getDefaultClass().getName();
        plugin.getSqlManager().tryUpdate("INSERT INTO 'players' (name, class, exp, mana) VALUES ('" + name + "','" + className + "', '0', '0') ");
        plugin.log(Level.INFO, "New player stored in db: " + name);
    }

    /**
     * Check if the Player already exists within the Database.
     * @param name
     * @return
     */
    public boolean checkPlayer(String name) {
        String query = "SELECT * FROM players WHERE name='" + name + "'";
        if (plugin.getSqlManager().rowCount(query) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Convert the given Exp into the correct Level.
     * @param exp
     * @return
     */
    public int getLevel(Integer exp) {
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
