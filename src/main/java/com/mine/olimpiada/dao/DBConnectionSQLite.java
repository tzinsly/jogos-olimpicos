package com.mine.olimpiada.dao;

import java.sql.Connection;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionSQLite {
	
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
