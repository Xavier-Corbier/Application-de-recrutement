package com.jobvxs.pl.connect;
import java.sql.*;

/**
 * Allow a connection to the PostGreSQL database
 */
public class PostGreSqlConnection
{
    /**
     * Get a connection of the database
     * @return
     * @throws SQLException
     */
    public static Connection getInstance() throws SQLException {
        Connection conn = null;
        // load driver class
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // create connect object
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://...", "...", "...");
        }catch (Exception e){
            throw new SQLException("the application is not connected to the database");
        }
        return conn ;
    }
}