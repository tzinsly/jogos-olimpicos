package com.mine.olimpiada.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mine.olimpiada.dao.CompeticaoDAO;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class CompeticaoBO {

	public enum Etapas {
		ELIMINATORIAS,
		OITAVAS,
		QUARTAS,
		SEMIFINAL,
		FINAL;
	}

	private int id;
	private String modalidade;
	private String local;
	private LocalDateTime dataHoraIni;
	private LocalDateTime dataHoraFim;
	private String pais1;
	private String pais2;
	private Etapas etapa;
	protected JSONObject resultData;

	public String salvarCompeticao(String data) {
		System.out.println("Step 2 - BO.salvarCompeticao");
		try {
			JSONObject newItem = new JSONObject(data);
			this.setModalidade(newItem.getString("modalidade"));
			this.setLocal(newItem.getString("local"));
			this.setPais1(newItem.getString("pais1"));
			this.setPais2(newItem.getString("pais2"));
			this.setEtapa(Etapas.valueOf(newItem.getString("etapa").toUpperCase()));

			LocalDate ld = LocalDate.parse(newItem.getString("dataIni"));
			LocalTime lt = LocalTime.parse(newItem.getString("horaIni"));
			LocalDateTime ldt = LocalDateTime.of(ld, lt);
			this.setDataHoraIni(ldt);

			ld = LocalDate.parse(newItem.getString("dataFim"));
			lt = LocalTime.parse(newItem.getString("horaFim"));
			ldt = LocalDateTime.of(ld, lt);
			this.setDataHoraFim(ldt);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.validarCompeticao();
		return CompeticaoDAO.salvar(this);
	}

	public String listarCompeticao(String modalidade) {

		ArrayList<CompeticaoBO> listComp = CompeticaoDAO.listar(modalidade);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		
		try {
			for (CompeticaoBO item : listComp) {

				JSONObject jsonComp = new JSONObject();
				System.out.println("id? " + item.getId());
				jsonComp.put("id", item.getId());
				jsonComp.put("modalidade", item.getModalidade());
				jsonComp.put("local", item.getLocal());
				jsonComp.put("pais1", item.getPais1());
				jsonComp.put("pais2", item.getPais2());
				jsonComp.put("etapa", item.getEtapa());
				jsonComp.put("dataIni", item.getDataHoraIni().toLocalDate());
				jsonComp.put("horaIni", item.getDataHoraIni().toLocalTime());
				jsonComp.put("dataFim", item.getDataHoraFim().toLocalDate());
				jsonComp.put("horaFim", item.getDataHoraFim().toLocalTime());
				
				jsonArray.put(jsonComp);

			}
			jsonObj.put("Jogos", jsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObj.toString();
	}
	
	public int validarCompeticao() {
		
		/*Duas competições não podem ocorrer no mesmo período, no mesmo local, para a
		mesma modalidade. Ex: Se eu tenho uma partida de futebol que com início às 18:00 e
		término às 20:00 no Estádio 1, eu não poderia ter outra partida de futebol se iniciando
		às 19:30 nesse mesmo estádio*/
		
		//CompeticaoDAO.validarDupComp(this.getModalidade(), this.getLocal(), this.getDataHoraIni(), this.getDataHoraFim());
		
		
		
		/*
		● O fluxo de cadastro deve permitir que se forneça o mesmo valor, para os 2 países
		envolvidos na disputa, apenas se a etapa for Final ou Semifinal. Para as demais etapas,
		não se deve permitir que se forneça o mesmo valor.
		● A competição deve ter a duração de no mínimo 30 minutos.
		● Para evitar problemas, a organização das olimpíadas que limitar a no máximo 4
		competições por dia num mesmo local
		● Para situações de erro, é necessário que a resposta da requisição seja coerente em
		exibir uma mensagem condizente com o erro.*/
		
		return 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public String getLocal() {
		return local;
	}

	public LocalDateTime getDataHoraIni() {
		return dataHoraIni;
	}

	public void setDataHoraIni(LocalDateTime dataHoraIni) {
		this.dataHoraIni = dataHoraIni;
	}

	public LocalDateTime getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(LocalDateTime dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getPais1() {
		return pais1;
	}

	public void setPais1(String pais1) {
		this.pais1 = pais1;
	}

	public String getPais2() {
		return pais2;
	}

	public void setPais2(String pais2) {
		this.pais2 = pais2;
	}

	public Etapas getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapas etapa) {
		this.etapa = etapa;
	}
}
