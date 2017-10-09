package com.mine.olimpiada.dao;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.mine.olimpiada.bo.CompeticaoBO;
import com.mine.olimpiada.bo.CompeticaoBO.Etapas;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class CompeticaoDAOTest {

	@Test
	public void testSalvar() throws Exception {
		CompeticaoBO competicaoBO = new CompeticaoBO();
		
		competicaoBO.setModalidade("Volei");
		competicaoBO.setLocal("Ginasio");
		competicaoBO.setPais1("Brasil");
		competicaoBO.setPais2("Uruguai");
		competicaoBO.setEtapa(Etapas.OITAVAS_DE_FINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 17, 13, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 17, 15, 00));
		
		Assert.assertEquals("Registro Inserido com Sucesso", CompeticaoDAO.salvar("competicao", competicaoBO));
		
		Assert.assertEquals("Erro: Falha ao Inserir o Registro", CompeticaoDAO.salvar("", competicaoBO));
	}

	@Test
	public void testListar() throws Exception {
		//Assert.assertNotNull(CompeticaoDAO.listar("competicao", ""));
		
		Assert.assertNotNull(CompeticaoDAO.listar("competicao", "Volei"));
		
		Assert.assertNull(CompeticaoDAO.listar("", ""));
		
		Assert.assertNull(CompeticaoDAO.listar("", "Volei"));
	}
}
