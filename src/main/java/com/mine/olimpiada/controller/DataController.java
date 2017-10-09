package com.mine.olimpiada.controller;

import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import org.json.JSONException;
import org.json.JSONObject;
import org.sqlite.SQLiteConfig.SynchronousMode;

import com.mine.olimpiada.bo.CompeticaoBO;
import com.mine.olimpiada.bo.CompeticaoBO.Etapas;
import com.mine.olimpiada.dao.CompeticaoDAO;
import com.mine.olimpiada.utils.ErrorInfo;
import com.mine.olimpiada.utils.Utils;

/**
 * @author Zinsly, Tatiane
 * @email tatianezinsly@gmail.com
 */

/**
 * The class DataController is responsible to communicate between the routes and business object, validating data before
 * sending to DAO layer
 * 
 */
public class DataController {
	
	private static final int LIMIT_COMP = 4;

	/**
	* Save competition
	* 
	* @param data - String to be parsed in a Json structure
	* @return String - Response related to the success or fail of the operation
	*/
	public static String salvarCompeticao(String data) {
		ErrorInfo errorInfo = new ErrorInfo();
		CompeticaoBO newCompBO = getRecord(data, errorInfo);
		
		if(newCompBO != null && validarCamposObrigatorios(newCompBO, errorInfo) && validarCompeticao(newCompBO, errorInfo)){
			return CompeticaoBO.salvar(newCompBO);
		} else {
			return Utils.errorJsonResponse(errorInfo);
		}
	}
	
	/**
	* Validate required fields to avoid null, empty or invalid values
	* 
	* @param data - CompeticaoBO structure containing data information
	* @param errorInfo - Structure containing error message and error detailed to be filled in case of unsuccessful results
	* @return boolean - returning if validation failed or not
	*/
	public static boolean validarCamposObrigatorios(CompeticaoBO data, ErrorInfo errorInfo){
		if(data.getModalidade().equals("null")){
			errorInfo.setMessage("Campo 'modalidade' não pode ser vazio");
			errorInfo.setDetails("Campos obrigatórios: modalidade, local, pais1, pais2, etapa, dataIni, dataFim, horaIni, horaFim");
			return false;
		}
		if(data.getLocal().equals("null")){
			errorInfo.setMessage("Campo 'local' não pode ser vazio");
			errorInfo.setDetails("Campos obrigatórios: modalidade, local, pais1, pais2, etapa, dataIni, dataFim, horaIni, horaFim");
			return false;
		}
		if(data.getPais1().equals("null") || data.getPais2().equals("null")){
			errorInfo.setMessage("Campo 'pais1' e 'pais2' não podem ser vazios");
			errorInfo.setDetails("Campos obrigatórios: modalidade, local, pais1, pais2, etapa, dataIni, dataFim, horaIni, horaFim");
			return false;
		}
		return true;
	}

	/**
	* Validate competition according to business rules
	* 
	* @param data - CompeticaoBO structure containing data information
	* @param errorInfo - Structure containing error message and error detailed to be filled in case of unsuccessful results
	* @return boolean - returning if validation failed or not
	*/
	public static boolean validarCompeticao(CompeticaoBO data, ErrorInfo errorInfo) {

		if (!validarDup(data, errorInfo)) {
			return false;
		}

		if (!validarPaises(data, errorInfo)) {
			return false;
		}

		if (!validarDuracao(data, errorInfo)) {
			return false;
		}

		if(!validarQtdCompDia(data, errorInfo)){
			return false;
		}

		return true;

	}

	/**
	* Method used to parser String receiving to a Json Object and then into the CompeticaoBO object 
	*
	* @param data - String representing a Json structure
	* @param errorInfo - Structure containing error message and error detailed to be filled in case of unsuccessful results
	* @return CompeticaoBO - return a parsed CompeticaoBO object
	*/
	public static CompeticaoBO getRecord(String data, ErrorInfo errorInfo) {
		CompeticaoBO newItemBO = null;
		try {
			newItemBO = new CompeticaoBO();
			JSONObject newItemJson = new JSONObject(data);
			newItemBO.setModalidade(newItemJson.getString("modalidade"));
			newItemBO.setLocal(newItemJson.getString("local"));
			newItemBO.setPais1(newItemJson.getString("pais1"));
			newItemBO.setPais2(newItemJson.getString("pais2"));
			
			String etapa = newItemJson.getString("etapa");
			etapa = Normalizer.normalize(etapa, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
			newItemBO.setEtapa(Etapas.valueOf(etapa.toUpperCase().replace(" ", "_")));

			LocalDate ld = LocalDate.parse(newItemJson.getString("dataIni"));
			LocalTime lt = LocalTime.parse(newItemJson.getString("horaIni"));
			LocalDateTime ldt = LocalDateTime.of(ld, lt);
			newItemBO.setDataHoraIni(ldt);

			ld = LocalDate.parse(newItemJson.getString("dataFim"));
			lt = LocalTime.parse(newItemJson.getString("horaFim"));
			ldt = LocalDateTime.of(ld, lt);
			newItemBO.setDataHoraFim(ldt);

		} catch (JSONException e) {
			errorInfo.setMessage("Campo não encontrado "); 
		    errorInfo.setDetails(e.getMessage());
			newItemBO = null;
		} catch (NullPointerException e){
			errorInfo.setMessage("Campo nulo");
			errorInfo.setDetails(e.getMessage());
			newItemBO = null;
		} catch (IllegalArgumentException e){
			errorInfo.setMessage("Campo Invalido");
			errorInfo.setDetails(e.getMessage());
			newItemBO = null;
		} catch (DateTimeParseException e){
			errorInfo.setMessage("Data/Hora Invalida. Para Data use 'yyyy-mm-dd'; Para Hora use: 'HH:mm'");
			errorInfo.setDetails(e.getMessage());
			newItemBO = null;
		}

		return newItemBO;
	}
	
	/**
	* Validate if there are duplicated competition:
	* Two competitions can not occur in the same period of time, local for the same modularity
	*
	* @param data - CompeticaoBO structure containing data information
	* @param errorInfo - Structure containing error message and error detailed to be filled in case of unsuccessful results
	* @return boolean - returning if validation failed or not
	*/
	private static boolean validarDup(CompeticaoBO data, ErrorInfo errorInfo) {
		if (CompeticaoBO.verificarDup(data)) {
			errorInfo.setMessage("Competição não inserida");
			errorInfo.setDetails("Já existe uma competição com essas características: mesmo horario, local e modalidade");
			return false;
		} else {
			return true;
		}
	}

	/**
	* Validate if the countries involved in the game are valid:
	* The user can provide the same value for 'pais1' and 'pais2' fields only if the step is the 'Final' or 'Semifinal'.
	*
	* @param data - CompeticaoBO structure containing data information
	* @param errorInfo - Structure containing error message and error detailed to be filled in case of unsuccessful results
	* @return boolean - returning if validation failed or not
	*/
	private static boolean validarPaises(CompeticaoBO data, ErrorInfo errorInfo) {
		if (data.getPais1().toLowerCase().equals(data.getPais2().toLowerCase()) && !data.getEtapa().equals(Etapas.FINAL)
				&& !data.getEtapa().equals(Etapas.SEMIFINAL)) {
			errorInfo.setMessage("Competição não inserida");
			errorInfo.setDetails("Essa competição não é Semifinal ou Final para envolver o mesmo país na disputa.");
			return false;
		} else {
			return true;
		}
	}

	/**
	* Validate the time of the competition:
	* The competition should last at least 30 minutes
	*
	* @param data - CompeticaoBO structure containing data information
	* @param errorInfo - Structure containing error message and error detailed to be filled in case of unsuccessful results
	* @return boolean - returning if validation failed or not
	*/
	private static boolean validarDuracao(CompeticaoBO data, ErrorInfo errorInfo) {
		Duration d = Duration.between(data.getDataHoraIni(), data.getDataHoraFim());
		if (d.getSeconds() < 1800) {
			errorInfo.setMessage("Competição não inserida");
			errorInfo.setDetails("Duração da competição deve ter no mínimo 30 minutos.");
			return false;
		} else {
			return true;
		}
	}
	
	/**
	* Validate the quantity of competition for a day in the same local:
	* The maximum number of a competition for a day in the same local is 4
	*
	* @param data - CompeticaoBO structure containing data information
	* @param errorInfo - Structure containing error message and error detailed to be filled in case of unsuccessful results
	* @return boolean - returning if validation failed or not
	*/
	private static boolean validarQtdCompDia(CompeticaoBO data, ErrorInfo errorInfo) {
		int qtdComp = CompeticaoBO.verificarQtdComp(data);
		if (qtdComp >= LIMIT_COMP) {
			errorInfo.setMessage("Competição não inserida");
			errorInfo.setDetails("Limite de 4 competições já excedida no dia");
			return false;
		} else if (qtdComp == -1) {
			errorInfo.setMessage("Problema de consulta de dados");
			errorInfo.setDetails("Parametro passado incorretamente");
			return false;
		} else {
			return true;
		}
	}

}
