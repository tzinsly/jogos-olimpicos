package com.mine.olimpiada.dao;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.util.SystemPropertyUtils;

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
			status = DbOperations.executeUpdate(sql, comp.getModalidade().toLowerCase(), comp.getLocal(),
					comp.getPais1(), comp.getPais2(), comp.getEtapa(), comp.getDataHoraIni(), comp.getDataHoraFim());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (status > 0) {
			return "Registro Inserido com Sucesso";
		} else {
			return "Erro: Falha ao Inserir o Registro";
		}
	}

	public static ArrayList<CompeticaoBO> listar(String tableName, String modalidade) {

		StringBuilder sql = new StringBuilder("select * from " + tableName);
		if (!modalidade.isEmpty()) {
			sql.append(" where modalidade = ?");
		}
		sql.append(" order by dataHoraIni");

		ArrayList<CompeticaoBO> listArrayComp = null;
		try {
			listArrayComp = DbOperations.executeGetLista(sql.toString(), modalidade.toLowerCase());
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
				+ " or dataHoraFim between ? and ?)";

		try {
			result = DbOperations.executeQuery(sql, modalidade.toLowerCase(), local, dataHoraIni, dataHoraFim);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static int verificarQtdComp(String tableName, String local, LocalDateTime dataHoraIni) {
		int qtdComp = -1;
		LocalDate ld = LocalDate.from(dataHoraIni);
		//System.out.println(ld);
		String sql = "select count(*) from " + tableName + " where local = ? and dataHoraIni like ?";

		try {
			qtdComp = DbOperations.executeQueryResult(sql, local, "%"+ld+"%");
			System.out.println("> Qtd: " + qtdComp);
		} catch (SQLException e) {
			e.printStackTrace();
			return qtdComp;
		}

		return qtdComp;
	}
}
