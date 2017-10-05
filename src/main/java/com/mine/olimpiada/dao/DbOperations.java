package com.mine.olimpiada.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class DbOperations {

	public void insert(String tableName, String data) {
		String sql = "INSERT INTO " + tableName + "(fields) values (";
		try (Connection conn = DbConnectionSQLite.connectToDb();
				Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void select(String tableName) {
		String sql = "SELECT * FROM " + tableName;
		try (Connection conn = DbConnectionSQLite.connectToDb();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			// loop through the result set
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getDouble("capacity"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

}
