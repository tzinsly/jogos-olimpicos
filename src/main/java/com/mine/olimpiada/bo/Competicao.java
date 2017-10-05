package com.mine.olimpiada.bo;

import java.time.LocalDate;
import java.time.LocalTime;
import org.json.JSONObject;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class Competicao {
	
	public enum Etapas{
		ELIMINATORIAS, OITAVAS, QUARTAS, SEMIFINAL, FINAL;
	}
	
	private String modalidade;
	private String local;
	private LocalDate date;
	private LocalTime time;
	private String pais1;
	private String pais2;
	private Etapas etapas;
	protected JSONObject resultData;
	
	public String getModalidade() {
		return modalidade;
	}
	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
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
	public Etapas getEtapas() {
		return etapas;
	}
	public void setEtapas(Etapas etapas) {
		this.etapas = etapas;
	}
}
