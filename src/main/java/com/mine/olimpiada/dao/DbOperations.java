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
 * @email tzinsly@br.ibm.com
 */

public class DbOperations {

	public static int executeUpdate(String query, Object... params) throws SQLException {
		int status = 0;
		//int index = 1;

		try (Connection conn = DbConnectionSQLite.connectToDb();
				PreparedStatement pstmt = conn.prepareStatement(query);) {

			populateParms(pstmt, params);
			System.out.println("sql: " + query);
			System.out.println("parms: " + pstmt.getParameterMetaData().getParameterCount());
			status = pstmt.executeUpdate();
		}
		return status;
	}

	public static boolean executeQuery(String sql, Object... params) throws SQLException {
		try (Connection conn = DbConnectionSQLite.connectToDb();
				PreparedStatement pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);) {

			populateParms(pstmt, params);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static ArrayList<CompeticaoBO> executeGetLista(String sql, Object... params) throws SQLException {
		CompeticaoBO comp;
		LocalDateTime ldt = null;
		ArrayList<CompeticaoBO> listArrayComp = new ArrayList<>();
		try (Connection conn = DbConnectionSQLite.connectToDb();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {

			populateParms(pstmt, params);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("There is data");
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
