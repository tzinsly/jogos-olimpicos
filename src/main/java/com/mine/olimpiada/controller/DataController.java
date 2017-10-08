package com.mine.olimpiada.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
		
		if(newCompBO != null && validarDados(newCompBO, errorInfo) && validarCompeticao(newCompBO, errorInfo)){
				return CompeticaoBO.salvar(newCompBO);
		} else {
			return Utils.errorJsonResponse(errorInfo);
		}
	}
	
	public static boolean validarDados(CompeticaoBO data, ErrorInfo errorInfo){
		boolean status = false;
		//Etapa
		switch (data.getEtapa()){
		case ELIMINATORIAS:
		case OITAVAS:
		case QUARTAS:
		case SEMIFINAL:
		case FINAL:
			status = true;
			break;
		default:
			errorInfo.setMessage("Erro: Etapa inválida");
			errorInfo.setDetails("Etapas válidas: Eliminatórias, Oitavas, Quartas, Semifinal e Final");
			status = false;
			break;
		}
		
		//Hora
		//Data
		
		return status;
		
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
			newItemBO.setEtapa(Etapas.valueOf(newItemJson.getString("etapa").toUpperCase()));

			LocalDate ld = LocalDate.parse(newItemJson.getString("dataIni"));
			LocalTime lt = LocalTime.parse(newItemJson.getString("horaIni"));
			LocalDateTime ldt = LocalDateTime.of(ld, lt);
			newItemBO.setDataHoraIni(ldt);

			ld = LocalDate.parse(newItemJson.getString("dataFim"));
			lt = LocalTime.parse(newItemJson.getString("horaFim"));
			ldt = LocalDateTime.of(ld, lt);
			newItemBO.setDataHoraFim(ldt);

		} catch (JSONException e) {
			errorInfo.setMessage("Erro: Campo não encontrado "); 
		    errorInfo.setDetails(e.getMessage());
			newItemBO = null;
		} catch (NullPointerException e){
			errorInfo.setMessage("Erro: Campo nulo");
			errorInfo.setDetails(e.getMessage());
			newItemBO = null;
		} catch (IllegalArgumentException e){
			errorInfo.setMessage("Erro: Campo Invalido");
			errorInfo.setDetails(e.getMessage());
			newItemBO = null;
		}

		return newItemBO;
	}

	private static boolean validarDup(CompeticaoBO data, ErrorInfo errorInfo) {
		if (CompeticaoBO.verificarDup(data)) {
			errorInfo.setMessage("Erro: Competição não inserida");
			errorInfo.setDetails("Já existe uma competição com essas características: mesmo horario, local e modalidade");
			return false;
		} else {
			return true;
		}
	}

	private static boolean validarPaises(CompeticaoBO data, ErrorInfo errorInfo) {
		if (data.getPais1().toLowerCase().equals(data.getPais2().toLowerCase()) && !data.getEtapa().equals(Etapas.FINAL)
				&& !data.getEtapa().equals(Etapas.SEMIFINAL)) {
			errorInfo.setMessage("Erro: Competição não inserida");
			errorInfo.setDetails("Essa competição não é Semifinal ou Final para envolver o mesmo país na disputa.");
			return false;
		} else {
			return true;
		}
	}

	private static boolean validarDuracao(CompeticaoBO data, ErrorInfo errorInfo) {
		Duration d = Duration.between(data.getDataHoraIni(), data.getDataHoraFim());
		if (d.getSeconds() < 1800) {
			errorInfo.setMessage("Erro: Competição não inserida");
			errorInfo.setDetails("Duração da competição deve ter no mínimo 30 minutos.");
			return false;
		} else {
			return true;
		}
	}
	
	private static boolean validarQtdCompDia(CompeticaoBO data, ErrorInfo errorInfo) {
		int qtdComp = CompeticaoBO.verificarQtdComp(data);
		if (qtdComp >= LIMIT_COMP) {
			errorInfo.setMessage("Erro: Competição não inserida");
			errorInfo.setDetails("Limite de 4 competições já excedida no dia");
			return false;
		} else if (qtdComp == -1) {
			errorInfo.setMessage("Erro: Problema de consulta de dados");
			errorInfo.setDetails("Parametro passado incorretamente");
			return false;
		} else {
			return true;
		}
	}

}
