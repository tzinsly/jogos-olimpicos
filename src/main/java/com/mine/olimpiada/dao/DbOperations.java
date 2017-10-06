package com.mine.olimpiada.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import com.mine.olimpiada.bo.CompeticaoBO;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class DbOperations {

	public static int insert(String tableName, CompeticaoBO comp) {
		int status = 0;
		System.out.println("HoraIni no DAO? " + comp.getDataHoraIni());
		String sql = "insert into " + tableName + " (modalidade, local, pais1, pais2, etapa, dataHoraIni, dataHoraFim) "
				+ "values ('" + comp.getModalidade() + "', '" + comp.getLocal() + "', '" + comp.getPais1() + "', '" + comp.getPais2()
				+ "', '" + comp.getEtapa() + "', '" + comp.getDataHoraIni() + "', '" + comp.getDataHoraFim() + "')";
		System.out.println(sql);
		try (Connection conn = DbConnectionSQLite.connectToDb();
			Statement stmt = conn.createStatement();) {
			status =  stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return status;
	}

	public void select(String tableName) {
		String sql = "select * from " + tableName;
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
