package com.mine.olimpiada.dao;

import java.sql.Connection;

/**
 * @author Zinsly, Tatiane
 * @email tatianezinsly@gmail.com
 */

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The class DbConnectionSQLite is responsible to connect specifically with a SQLite database
 * 
 */
public class DbConnectionSQLite {
	
	/**
	* Method to connect to a SQLite database
	*
	* @return conn - Connection to a SQLite database
	*/
	public static Connection connectToDb() {
		//get from property
        String url = "jdbc:sqlite:C:\\dev\\sqlite\\db\\" + "jogos.db";
        Connection conn = null;
        
        try {
        	Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } 
        return conn;
    }

}
