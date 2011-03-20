package com.herocraftonline.dev.heroes.persistance;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.herocraftonline.dev.heroes.Heroes;

@SuppressWarnings("unused")
public class SQLite {

    // Change this //
    private final String dbname = "./plugins/Heroes/heroes.db";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbname);
            connection.setAutoCommit(true);
        } catch (Exception e) {
            Heroes.log.warning(("SQLite connection failed: " + e.toString()));
        }
        return connection;
    }

    public void tryUpdate(String sqlString) {
        try {
            System.out.println(sqlString);
            
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            st.executeUpdate(sqlString);
            st.close();
            conn.close();
        } catch (Exception e) {
            Heroes.log.warning("The following statement failed: " + sqlString);
            Heroes.log.warning("Statement failed: " + e.toString());
        }

    }

    public ResultSet trySelect(String sqlString) {
        try {
            System.out.println(sqlString);
            
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(sqlString);
            st.close();
            conn.close();
            return result;
        } catch (Exception e) {
            Heroes.log.warning("Statement failed: " + e.toString());
        }
        return null;
    }

    public void createTable(String SQLStat) throws Exception {
        tryUpdate(SQLStat);
    }

    public int tableSize(String table) {
        int size = 0;
        try {
            ResultSet pRS = trySelect("SELECT * FROM " + table);
            while (pRS.next()) {
                size++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
