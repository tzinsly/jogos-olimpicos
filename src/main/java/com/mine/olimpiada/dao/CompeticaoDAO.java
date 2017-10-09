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
import com.mine.olimpiada.controller.DataController;
import com.mine.olimpiada.utils.ErrorInfo;

/**
 * @author Zinsly, Tatiane
 * @email tatianezinsly@gmail.com
 */

/**
 * The class CompeticaoDAO is responsible for all the data access object operation related to the CompeticaoBO object
 * 
 */
public class CompeticaoDAO {

	/**
	* Method used to communicate between Controller and DAO layer to save object
	*
	* @param tableName - table name to be used on the operation
	* @param CompeticaoBO - object to be saved
	* @return String - Message indicating if the operation was completed successfully or if it failed
	*/
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

	/**
	* Method used to communicate between Controller and DAO layer to list an object
	*
	* @param tableName - table name to be used on the operation
	* @param modalidade - String representing the modularity to be filtered
	* @return ArrayList<CompeticaoBO> - containing all the objects to be listed
	*/
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

	/**
	* Method used to communicate between Controller and DAO layer to verify duplicated objects
	*
	* @param tableName - table name to be used on the operation
	* @param modalidade - String representing the modularity of the competition to be checked
	* @param local - place where the competition will occur
	* @param dataHoraIni - Date/time of the competition beginning to be checked
	* @param dataHoraFim - Date/time of the competition end to be checked 
	* @return boolean - Returning if the competition to be inserted already exists or not
	*/
	public static boolean verificarDupComp(String tableName, String modalidade, String local, LocalDateTime dataHoraIni,
			LocalDateTime dataHoraFim) {
		boolean result = false;
		String sql = "select * from " + tableName
				+ " where modalidade = ? and local = ? and (dataHoraIni between ? and ?"
				+ " or dataHoraFim between ? and ? "
				+ "or (dataHoraIni < ? and dataHoraFim > ?))";

		try {
			result = DbOperations.executeQuery(sql, modalidade.toLowerCase(), local, dataHoraIni, dataHoraFim, dataHoraIni, dataHoraFim, dataHoraIni, dataHoraFim);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	* Method to verify quantity of competition per day
	* @see {@code DataController.validarQtdCompDia(CompeticaoBO data, ErrorInfo errorInfo) }
	*
	* @param tableName - table name to be used on the operation
	* @param local - place where the competition will occur
	* @param dataHoraIni - Date to be checked
	* @return int - Line number of the competitions those attend these requirements
	*/
	public static int verificarQtdComp(String tableName, String local, LocalDateTime dataHoraIni) {
		int qtdComp = -1;
		LocalDate ld = LocalDate.from(dataHoraIni);
		//System.out.println(ld);
		String sql = "select count(*) from " + tableName + " where local = ? and dataHoraIni like ?";

		try {
			qtdComp = DbOperations.executeQueryResult(sql, local, "%"+ld+"%");
			//System.out.println("> Qtd: " + qtdComp);
		} catch (SQLException e) {
			e.printStackTrace();
			return qtdComp;
		}

		return qtdComp;
	}
}
