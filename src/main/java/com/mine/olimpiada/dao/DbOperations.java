package com.mine.olimpiada.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONObject;

import com.mine.olimpiada.bo.CompeticaoBO;
import com.mine.olimpiada.bo.CompeticaoBO.Etapas;

import groovyjarjarantlr.collections.List;

/**
 * @author Zinsly, Tatiane
 * @email tatianezinsly@gmail.com
 */

/**
 * The class DbOperations contains generic methods for execute queries and execute updates
 * 
 */
public class DbOperations {

	/**
	* Method used to execute generic updates
	*
	* @param query - sql command to be executed
	* @param Variable arguments - a list of Objects to be used on PreparedStatement
	* @return int - Return 0 if no updated were executed or 1+ if one or more updated were executed
	*/
	public static int executeUpdate(String query, Object... params) throws SQLException {
		int status = 0;
		
		try (Connection conn = DbConnectionSQLite.connectToDb();
				PreparedStatement pstmt = conn.prepareStatement(query);) {

			populateParms(pstmt, params);
			status = pstmt.executeUpdate();
		}
		return status;
	}

	/**
	* Method used to execute generic queries
	*
	* @param query - sql command to be executed
	* @param Variable arguments - a list of Objects to be used on PreparedStatement
	* @return boolean - return if there is ResultSet populated or not
	*/
	public static boolean executeQuery(String sql, Object... params) throws SQLException {
		try (Connection conn = DbConnectionSQLite.connectToDb();
				PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);) {

			System.out.println(sql);
			populateParms(pstmt, params);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	* Method used to execute a generic count(*) query
	*
	* @param sql - sql command to be executed
	* @param Variable arguments - a list of Objects to be used on PreparedStatement
	* @return int - Return the value returning from the sql command
	*/
	public static int executeQueryResult(String sql, Object... params) throws SQLException {
		try (Connection conn = DbConnectionSQLite.connectToDb();
				PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);) {

			System.out.println(sql);
			populateParms(pstmt, params);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				//System.out.println("TEM DADOSSSSSS");
				return rs.getInt(1);
			} else {
				return -1;
			}
		}
	}

	/**
	* Method used to execute a query and return an object
	*
	* @param sql - command to be executed
	* @param Variable arguments - a list of Objects to be used on PreparedStatement
	* @return ArrayList<CompeticaoBO> - Array of object returned from the sql command executed
	*/
	public static ArrayList<CompeticaoBO> executeGetLista(String sql, Object... params) throws SQLException {
		CompeticaoBO comp;
		LocalDateTime ldt = null;
		ArrayList<CompeticaoBO> listArrayComp = new ArrayList<>();
		try (Connection conn = DbConnectionSQLite.connectToDb();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {

			populateParms(pstmt, params);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				comp = new CompeticaoBO();
				comp.setId(rs.getInt("id"));
				comp.setModalidade(rs.getString("modalidade"));
				comp.setLocal(rs.getString("local"));
				comp.setPais1(rs.getString("pais1"));
				comp.setPais2(rs.getString("pais2"));
				comp.setEtapa(Etapas.valueOf(rs.getString("etapa")));

				ldt = LocalDateTime.parse(rs.getString("dataHoraIni"));
				comp.setDataHoraIni(ldt);

				ldt = LocalDateTime.parse(rs.getString("dataHoraFim"));
				comp.setDataHoraFim(ldt);

				listArrayComp.add(comp);
			}

			return listArrayComp;
		}

	}

	/**
	* Method used to populate PreparedStatement with the variable parms
	*
	* @param PreparedStatement - to be filled
	* @param Variable arguments - a list of Objects to be used on PreparedStatement
	* @return String - Message of sucessful or failed operation
	*/
	public static String populateParms(PreparedStatement pstmt, Object... params) throws SQLException{
		int index = 1;
		for (Object obj : params) {
			if (obj instanceof String) {
				if (!((String) obj).isEmpty()) {
					pstmt.setString(index, (String) obj);
					System.out.println(obj);
				}
			} else if (obj instanceof Etapas) {
				System.out.println(obj);
				pstmt.setString(index, ((Etapas) obj).toString());
			} else if (obj instanceof LocalDateTime) {
				System.out.println(obj);
				pstmt.setString(index, ((LocalDateTime) obj).toString());
			} else {
				return "Invalid Parm";
			}
			index++;
		}
		return "Populated parms";
	}

}
