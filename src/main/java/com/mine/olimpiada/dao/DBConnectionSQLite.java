package com.mine.olimpiada.dao;

import java.sql.Connection;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionSQLite {
	
	public static Connection connectToDb() {
		//get from property
        String url = "jdbc:sqlite:C:/sqlite/db/" + "jogos.db";
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
