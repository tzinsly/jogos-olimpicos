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

import groovyjarjarcommonscli.ParseException;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class CompeticaoBO {

	public enum Etapas {
		ELIMINATORIAS,
		OITAVAS_DE_FINAL,
		QUARTAS_DE_FINAL,
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

	public CompeticaoBO() {
		id = 0;
	}

	private static final String TABLENAME = "competicao";

	public static String salvar(CompeticaoBO data) {
		return CompeticaoDAO.salvar(TABLENAME, data);
	}

	public static String listarCompeticao(String modalidade) {
		ArrayList<CompeticaoBO> listComp = CompeticaoDAO.listar(TABLENAME, modalidade);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObj = new JSONObject();

		try {
			for (CompeticaoBO item : listComp) {

				JSONObject jsonComp = new JSONObject();
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

	public static boolean verificarDup(CompeticaoBO data) {
		return CompeticaoDAO.verificarDupComp(TABLENAME, data.getModalidade(), data.getLocal(), data.getDataHoraIni(),
				data.getDataHoraFim());
	}
	
	public static int verificarQtdComp(CompeticaoBO data) {
		return CompeticaoDAO.verificarQtdComp(TABLENAME, data.getLocal(), data.getDataHoraIni());
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
