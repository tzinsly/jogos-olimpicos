package com.mine.olimpiada.dao;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import com.mine.olimpiada.bo.CompeticaoBO;
import com.mine.olimpiada.bo.CompeticaoBO.Etapas;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class CompeticaoDAO {

	public static String salvar(String tableName, CompeticaoBO comp) {
		int status = 0;
		String sql = "insert into " + tableName + " (modalidade, local, pais1, pais2, etapa, dataHoraIni, dataHoraFim) "
				+ "values (?, ?, ?, ?, ?, ?, ?)";

		try {
			status = DbOperations.executeUpdate(sql, comp.getModalidade(), comp.getLocal(), comp.getPais1(),
					comp.getPais2(), comp.getEtapa(), comp.getDataHoraIni(), comp.getDataHoraFim());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (status > 0) {
			return "Successful Insert";
		} else {
			return "Failed Insert";
		}
	}

	public static ArrayList<CompeticaoBO> listar(String tableName, String modalidade) {

		String sql = "select * from " + tableName;
		if (!modalidade.isEmpty()) {
			sql = sql + " where modalidade = ?";
		}

		ArrayList<CompeticaoBO> listArrayComp = null;
		try {
			listArrayComp = DbOperations.executeGetLista(sql, modalidade);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listArrayComp;
	}

	public static boolean verificarDupComp(String tableName, String modalidade, String local, LocalDateTime dataHoraIni,
			LocalDateTime dataHoraFim) {
		boolean result = false;
		String sql = "select * from " + tableName
				+ " where modalidade = ? and local = ? and (dataHoraIni between ? and ?"
				+ "or  dataHoraFim between ? and ?)";

		try {
			result = DbOperations.executeQuery(sql, modalidade, local, dataHoraIni, dataHoraFim );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
