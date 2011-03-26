package com.herocraftonline.dev.heroes.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import com.herocraftonline.dev.heroes.Heroes;

public class SQLiteManager {
    
    // Create a String to hold the Database location.
    private final String dbname = Heroes.dataFolder + "/heroes.db";
    private final Heroes plugin;

    public SQLiteManager(Heroes plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Grab a new Database Connection
     * @return
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbname);
            connection.setAutoCommit(true);
        } catch (Exception e) {
            plugin.log(Level.WARNING, "SQLite connection failed: " + e.toString());
        }
        return connection;
    }

    /**
     * This function performs Inserts & Updates.
     * @param sqlString
     */
    public void tryUpdate(String sqlString) {
        try {
            plugin.log(Level.INFO, sqlString);

            Connection conn = plugin.getDatabaseConnection();
            Statement st = conn.createStatement();
            st.executeUpdate(sqlString);
            st.close();
        } catch (Exception e) {
            plugin.log(Level.WARNING, "The following statement failed: " + sqlString);
            plugin.log(Level.WARNING, "Statement failed: " + e.toString());
        }

    }

    /**
     * This function performs Selects and returns a ResultSet.
     * @param sqlString
     * @return
     */
    public ResultSet trySelect(String sqlString) {
        try {
            plugin.log(Level.INFO, sqlString);

            Connection conn = plugin.getDatabaseConnection();
            Statement st = conn.createStatement();
            return st.executeQuery(sqlString);
        } catch (Exception e) {
            plugin.log(Level.WARNING, "Statement failed: " + e.toString());
        }
        return null;
    }
    
    /**
     * Return the Row Count of the Query.
     * @param query
     * @return
     */
    public int rowCount(String query){
        int count = 0;
        try {
            ResultSet r = trySelect(query);
            while(r.next()) {
                count++;
            }
            r.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
