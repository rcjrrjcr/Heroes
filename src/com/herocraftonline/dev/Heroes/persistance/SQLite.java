package com.herocraftonline.dev.Heroes.persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.*;

import com.herocraftonline.dev.Heroes.Heroes;

@SuppressWarnings("unused")
public class SQLite {
    private final Heroes pluginMain;
    
    // Change this //
    String dbname = "heroes.db";

    private Connection SQLiteConnection;
    private Statement SQLiteStatement;
    private Driver SQLiteDriver;

    public SQLite(Heroes instance) {
        pluginMain = instance;
        Heroes.Log.info("Running database connection...");
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConnection = DriverManager.getConnection("jdbc:sqlite:" + dbname);
            SQLiteStatement = SQLiteConnection.createStatement();
            SQLiteConnection.setAutoCommit(true);
        } catch (Exception e) {
            Heroes.Log.warning(("SQLite connection failed: " + e.toString()));
        }
    }

    public Connection getConnection() {
        return SQLiteConnection;
    }

    public Statement getStatement() {
        return SQLiteStatement;
    }

    public void tryUpdate(String sqlString) {
        try {
            getStatement().executeUpdate(sqlString);
        } catch (Exception e) {
            Heroes.Log.warning("The following statement failed: "
                    + sqlString);
            Heroes.Log.warning("Statement failed: " + e.toString());
        }
    }

    public ResultSet trySelect(String sqlString) {
        try {
            System.out.println(getStatement().toString());
            return getStatement().executeQuery(sqlString);
        } catch (Exception e) {
            Heroes.Log.warning("Statement failed: " + e.toString());
        }
        return null;
    }
    
    public void createTable(String SQLStat) throws Exception{
    	tryUpdate(SQLStat);
    }
    
    public int tableSize(String table){
    	int size = 0;
		try {
	    	ResultSet pRS = trySelect("SELECT * FROM " + table);
			while(pRS.next()){
				size++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
    }
}
