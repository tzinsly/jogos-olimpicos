package com.mine.olimpiada.controller;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import javax.xml.crypto.Data;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.mine.olimpiada.bo.CompeticaoBO;
import com.mine.olimpiada.bo.CompeticaoBO.Etapas;
import com.mine.olimpiada.utils.ErrorInfo;

/**
 * @author Zinsly, Tatiane
 * @email tatianezinsly@gmail.com
 */

public class DataControllerTest {

	@Test
	public void testSalvarCompeticao() throws Exception {
		JSONObject data = new JSONObject();

		data.put("modalidade", "Volei");
		data.put("local", "Quadra 2");
		data.put("etapa", "Final");
		data.put("pais1", "Portugal");
		data.put("pais2", "Equador");
		data.put("dataIni", "2017-10-23");
		data.put("dataFim", "2017-10-23");
		data.put("horaIni", "15:00");
		data.put("horaFim", "17:00");
		
		assertTrue(DataController.salvarCompeticao(data.toString()).equals("Registro Inserido com Sucesso") || 
				DataController.salvarCompeticao(data.toString()).contains("erro"));
	}

	@Test
	public void testValidarCamposObrigatorios() throws Exception {
		CompeticaoBO competicaoBO = new CompeticaoBO();
		ErrorInfo errorInfo = new ErrorInfo();
		
		competicaoBO.setModalidade("null");
		competicaoBO.setLocal("Ginasio");
		competicaoBO.setPais1("Brasil");
		competicaoBO.setPais2("Uruguai");
		competicaoBO.setEtapa(Etapas.OITAVAS_DE_FINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 17, 13, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 17, 15, 00));
		
		Assert.assertFalse(DataController.validarCamposObrigatorios(competicaoBO, errorInfo));
		
		competicaoBO.setModalidade("Volei");
		competicaoBO.setLocal("null");
		competicaoBO.setPais1("Brasil");
		competicaoBO.setPais2("Uruguai");
		competicaoBO.setEtapa(Etapas.OITAVAS_DE_FINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 17, 13, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 17, 15, 00));
		
		Assert.assertFalse(DataController.validarCamposObrigatorios(competicaoBO, errorInfo));
		
		competicaoBO.setModalidade("Volei");
		competicaoBO.setLocal("Ginasio");
		competicaoBO.setPais1("null");
		competicaoBO.setPais2("null");
		competicaoBO.setEtapa(Etapas.OITAVAS_DE_FINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 17, 13, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 17, 15, 00));
		
		Assert.assertFalse(DataController.validarCamposObrigatorios(competicaoBO, errorInfo));
		
		competicaoBO.setModalidade("Volei");
		competicaoBO.setLocal("Ginasio");
		competicaoBO.setPais1("null");
		competicaoBO.setPais2("Brasil");
		competicaoBO.setEtapa(Etapas.OITAVAS_DE_FINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 17, 13, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 17, 15, 00));
		
		Assert.assertFalse(DataController.validarCamposObrigatorios(competicaoBO, errorInfo));
		
		competicaoBO.setModalidade("Natação");
		competicaoBO.setLocal("Ginasio");
		competicaoBO.setPais1("Brasil");
		competicaoBO.setPais2("Uruguai");
		competicaoBO.setEtapa(Etapas.OITAVAS_DE_FINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 17, 13, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 17, 15, 00));
		
		Assert.assertTrue(DataController.validarCamposObrigatorios(competicaoBO, errorInfo));
	}

	@Test
	public void testValidarCompeticao() throws Exception {
		CompeticaoBO competicaoBO = new CompeticaoBO();
		ErrorInfo errorInfo = new ErrorInfo();
		
		competicaoBO.setModalidade("Volei");
		competicaoBO.setLocal("Quadra 2");
		competicaoBO.setPais1("Portugal");
		competicaoBO.setPais2("Equador");
		competicaoBO.setEtapa(Etapas.FINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 23, 15, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 23, 17, 00));
	
		Assert.assertFalse(DataController.validarCompeticao(competicaoBO, errorInfo));
		
		competicaoBO.setModalidade("Salto a Distancia");
		competicaoBO.setLocal("Quadra 2");
		competicaoBO.setPais1("Portugal");
		competicaoBO.setPais2("Portugal");
		competicaoBO.setEtapa(Etapas.OITAVAS_DE_FINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 23, 15, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 23, 17, 00));
	
		Assert.assertFalse(DataController.validarCompeticao(competicaoBO, errorInfo));
		
		competicaoBO.setModalidade("Salto a Distancia");
		competicaoBO.setLocal("Quadra 2");
		competicaoBO.setPais1("Portugal");
		competicaoBO.setPais2("Portugal");
		competicaoBO.setEtapa(Etapas.SEMIFINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 23, 15, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 23, 15, 10));
	
		Assert.assertFalse(DataController.validarCompeticao(competicaoBO, errorInfo));
		
	}

	@Test
	public void testGetRecord() throws Exception {
		JSONObject data = new JSONObject();
		ErrorInfo errorInfo = new ErrorInfo();
		
		data.put("local", "Quadra 2");
		data.put("etapa", "Final");
		data.put("pais1", "Portugal");
		data.put("pais2", "Equador");
		data.put("dataIni", "2017-10-23");
		data.put("dataFim", "2017-10-23");
		data.put("horaIni", "15:00");
		data.put("horaFim", "17:00");
		
		Assert.assertNull(DataController.getRecord(data.toString(), errorInfo));
		assertTrue(errorInfo.getMessage().contains("Campo não encontrado"));
		
		data.put("modalidade", "Volei");
		data.put("local", "Quadra 2");
		data.put("etapa", "Final");
		data.put("pais1", "Portugal");
		data.put("pais2", "Equador");
		data.put("dataIni", "2017-10-23");
		data.put("dataFim", "2017-10-23");
		data.put("horaFim", "17:00");
		data.put("horaFim", "xxx");
		
		Assert.assertNull(DataController.getRecord(data.toString(), errorInfo));
		assertTrue(errorInfo.getMessage().contains("Data/Hora Invalida."));
		
		data.put("modalidade", "Volei");
		data.put("local", "Quadra 2");
		data.put("etapa", "Final");
		data.put("pais1", "Portugal");
		data.put("pais2", "Equador");
		data.put("dataIni", "2017-10-23");
		data.put("dataFim", "2017-10-23");
		data.put("horaIni", "15:00");
		data.put("horaFim", "17:00");
		
		Assert.assertNotNull(DataController.getRecord(data.toString(), errorInfo));
	}
}
