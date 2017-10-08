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

import com.mine.olimpiada.bo.CompeticaoBO;
import com.mine.olimpiada.bo.CompeticaoBO.Etapas;
import com.mine.olimpiada.dao.CompeticaoDAO;
import com.mine.olimpiada.utils.ErrorInfo;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class DataController {

	public static String salvarCompeticao(String data) {
		CompeticaoBO newCompBO = getRecord(data);
		ErrorInfo errorInfo = new ErrorInfo();

		if (validarCompeticao(newCompBO, errorInfo)) {
			return CompeticaoBO.salvar(newCompBO);
		} else {
			return errorInfo.getMessage();
		}
	}

	public static boolean validarCompeticao(CompeticaoBO data, ErrorInfo errorInfo) {

		/*Duas competições não podem ocorrer no mesmo período, no mesmo local, para a
		mesma modalidade. Ex: Se eu tenho uma partida de futebol que com início às 18:00 e
		término às 20:00 no Estádio 1, eu não poderia ter outra partida de futebol se iniciando
		às 19:30 nesse mesmo estádio*/
		if (CompeticaoBO.verificarDup(data)) {
			errorInfo.setMessage(
					"Erro: Competição não inserida - Já existe uma competição com essas características (mesmo horario, local e modalidade)");
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
		/*if (CompeticaoBO.verificarQtdComp(data)) {
			errorInfo.setMessage(
					"Erro: Competição não inserida - Limite de 4 competições já excedida no dia)");
			return false;
		}*/
		
		errorInfo.setMessage("Operação realizada com Sucesso");
		return true;

	}

	public static CompeticaoBO getRecord(String data) {
		CompeticaoBO newItemBO = new CompeticaoBO();
		try {
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
			e.printStackTrace();
		}

		return newItemBO;
	}

	static boolean validarPaises(CompeticaoBO data, ErrorInfo errorInfo) {
		if (data.getPais1().toLowerCase().equals(data.getPais2().toLowerCase()) && !data.getEtapa().equals(Etapas.FINAL)
				&& !data.getEtapa().equals(Etapas.SEMIFINAL)) {
			errorInfo.setMessage(
					"Erro: Competição não inserida - Essa competição não é Semifinal ou Final para envolver o mesmo país na disputa.");
			return false;
		} else {
			return true;
		}
	}

	private static boolean validarDuracao(CompeticaoBO data, ErrorInfo errorInfo) {
		Duration d = Duration.between(data.getDataHoraIni(), data.getDataHoraFim());
		if (d.getSeconds() < 1800){
			errorInfo.setMessage(
					"Erro: Competição não inserida - Duração da competição deve ser maior que 30 minutos.");
			return false;
		} else {
			return true;
		}
	}

}
