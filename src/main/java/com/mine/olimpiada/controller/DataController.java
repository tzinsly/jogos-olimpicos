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
 * @email tzinsly@br.ibm.com
 */

public class DataController {
	
	private static final int LIMIT_COMP = 4;

	public static String salvarCompeticao(String data) {
		ErrorInfo errorInfo = new ErrorInfo();
		CompeticaoBO newCompBO = getRecord(data, errorInfo);
		
		if(newCompBO != null && validarCamposObrigatorios(newCompBO, errorInfo) && validarCompeticao(newCompBO, errorInfo)){
				return CompeticaoBO.salvar(newCompBO);
		} else {
			return Utils.errorJsonResponse(errorInfo);
		}
	}
	
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

	public static boolean validarCompeticao(CompeticaoBO data, ErrorInfo errorInfo) {

		/*Duas competições não podem ocorrer no mesmo período, no mesmo local, para a
		mesma modalidade. Ex: Se eu tenho uma partida de futebol que com início às 18:00 e
		término às 20:00 no Estádio 1, eu não poderia ter outra partida de futebol se iniciando
		às 19:30 nesse mesmo estádio*/
		if (!validarDup(data, errorInfo)) {
			return false;
		}

		/*
		● O fluxo de cadastro deve permitir que se forneça o mesmo valor, para os 2 países
		envolvidos na disputa, apenas se a etapa for Final ou Semifinal. Para as demais etapas,
		não se deve permitir que se forneça o mesmo valor.*/
		if (!validarPaises(data, errorInfo)) {
			return false;
		}

		/*
		● A competição deve ter a duração de no mínimo 30 minutos.*/
		if (!validarDuracao(data, errorInfo)) {
			return false;
		}

		/*
		● Para evitar problemas, a organização das olimpíadas que limitar a no máximo 4
		competições por dia num mesmo local*/
		if(!validarQtdCompDia(data, errorInfo)){
			return false;
		}

		return true;

	}

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
			newItemBO.setEtapa(Etapas.valueOf(etapa.toUpperCase()));

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

	//passar para o BO ???
	private static boolean validarDup(CompeticaoBO data, ErrorInfo errorInfo) {
		if (CompeticaoBO.verificarDup(data)) {
			errorInfo.setMessage("Competição não inserida");
			errorInfo.setDetails("Já existe uma competição com essas características: mesmo horario, local e modalidade");
			return false;
		} else {
			return true;
		}
	}

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
