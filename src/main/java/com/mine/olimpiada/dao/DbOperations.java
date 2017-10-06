package com.mine.olimpiada.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
	/*public static int executeUpdate(String query, Object ... params) {
		int status = 0;
		int index = 0;
		
		try (Connection conn = DbConnectionSQLite.connectToDb();){
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			for(Object obj : params){
				if(obj instanceof String){
					pstmt.setString(index, (String) obj);
				} if(obj instanceof LocalDateTime){
					//pstmt.setTimestamp((index, (Timestamp) obj);
				} else {
					return 0;
				}
				index++;
			}
			status =  pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return status;
	}
*/
	public ArrayList<CompeticaoBO> select(String tableName, String modalidade) {
		CompeticaoBO comp = null;
		ArrayList<CompeticaoBO> listComp = new ArrayList<CompeticaoBO>();
		LocalDateTime ldt = null;
		String sql = "select * from " + tableName;
		if(!modalidade.isEmpty()){
			sql = sql + " where modalidade = '" + modalidade + "'";
		}
		try (Connection conn = DbConnectionSQLite.connectToDb();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				//System.out.println(rs.getInt("id") + "\t" + rs.getString("modalidade") + "\t" + rs.getString("local"));
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
				
				listComp.add(comp);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return listComp;
	}

}
