package com.mine.olimpiada.controller;

import static org.junit.Assert.assertTrue;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Zinsly, Tatiane
 * @email tatianezinsly@gmail.com
 */

public class CompeticaoControllerTest {

	@Test
	public void testCadastro() throws Exception {
		CompeticaoController compControl = new CompeticaoController();
		Assert.assertEquals("Erro: Dados de entrada nulo", compControl.cadastro(null));

		JSONObject data = new JSONObject();

		data.put("modalidade", "Volei");
		data.put("local", "GinasioA");
		data.put("etapa", "Final");
		data.put("pais1", "Portugal");
		data.put("pais2", "Equador");
		data.put("dataIni", "2017-10-23");
		data.put("dataFim", "2017-10-23");
		data.put("horaIni", "15:00");
		data.put("horaFim", "17:00");
		
		assertTrue(compControl.cadastro(data.toString()).equals("Registro Inserido com Sucesso") ||
				compControl.cadastro(data.toString()).contains("Já existe uma competição com essas características"));
		
		assertTrue(compControl.cadastro("").contains("Campo não encontrado"));		
	}

	@Test
	public void testViewAllCompeticao() throws Exception {
		CompeticaoController compControl = new CompeticaoController();
		assertTrue(compControl.viewAllCompeticao().contains("Jogos"));
	}

	@Test
	public void testViewCompeticao() throws Exception {
		CompeticaoController compControl = new CompeticaoController();
		assertTrue(compControl.viewCompeticao("futebol").contains("futebol") || compControl.viewCompeticao("futebol").contains("Jogos"));
	}

}
