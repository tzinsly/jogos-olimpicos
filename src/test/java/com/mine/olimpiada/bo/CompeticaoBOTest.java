package com.mine.olimpiada.bo;

import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import javax.validation.constraints.AssertFalse;

import org.junit.Assert;
import org.junit.Test;
import org.sqlite.SQLiteConfig.SynchronousMode;

import com.mine.olimpiada.bo.CompeticaoBO.Etapas;

/**
 * @author Zinsly, Tatiane
 * @email tatianezinsly@gmail.com
 */

public class CompeticaoBOTest {

	@Test
	public void testSetEtapa() throws Exception {
		CompeticaoBO compBO = new CompeticaoBO();
		
		try {
			compBO.setEtapa(Etapas.valueOf(""));
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException e) {}		
	}
	
	@Test
	public void testSetId() throws Exception {
		CompeticaoBO competicaoBO = new CompeticaoBO();
		Assert.assertEquals(0, competicaoBO.getId());
	}

	@Test
	public void testSalvar() throws Exception {
		CompeticaoBO competicaoBO = new CompeticaoBO();

		Assert.assertNotNull(competicaoBO);

		try {
			CompeticaoBO.salvar(competicaoBO);
			fail("Expected NullPointerException");
		} catch (NullPointerException e) {}

		competicaoBO.setModalidade("Futebol");
		competicaoBO.setLocal("Ginasio");
		competicaoBO.setPais1("Brasil");
		competicaoBO.setPais2("Uruguai");
		competicaoBO.setEtapa(Etapas.OITAVAS_DE_FINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 17, 13, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 17, 15, 00));

		Assert.assertEquals("Registro Inserido com Sucesso", CompeticaoBO.salvar(competicaoBO));

	}

	@Test
	public void testListarCompeticao() throws Exception {
		CompeticaoBO compBO = new CompeticaoBO();
		compBO.setModalidade("");

		Assert.assertNotNull(CompeticaoBO.listarCompeticao(compBO.getModalidade()));
		Assert.assertNotEquals("", CompeticaoBO.listarCompeticao(compBO.getModalidade()));
		
		compBO.setModalidade("Futebol");

		Assert.assertNotNull(CompeticaoBO.listarCompeticao(compBO.getModalidade()));
		Assert.assertNotEquals("", CompeticaoBO.listarCompeticao(compBO.getModalidade()));
		
		compBO.setModalidade(null);
		try {
			CompeticaoBO.listarCompeticao(compBO.getModalidade());
			fail("Expected NullPointerException");
		} catch (NullPointerException e) {}

	}

	@Test
	public void testVerificarDup() throws Exception {
		CompeticaoBO competicaoBO = new CompeticaoBO();
		
		competicaoBO.setModalidade("Ginastica");
		competicaoBO.setLocal("Ginasio");
		competicaoBO.setPais1("Brasil");
		competicaoBO.setPais2("Franca");
		competicaoBO.setEtapa(Etapas.OITAVAS_DE_FINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 17, 13, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 17, 15, 00));
		
		Assert.assertFalse(CompeticaoBO.verificarDup(competicaoBO));
		
		competicaoBO.setModalidade("Futebol");
		competicaoBO.setLocal("Ginasio");
		competicaoBO.setPais1("Brasil");
		competicaoBO.setPais2("Uruguai");
		competicaoBO.setEtapa(Etapas.OITAVAS_DE_FINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 17, 13, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 17, 15, 00));
		
		Assert.assertTrue(CompeticaoBO.verificarDup(competicaoBO));
	}

	@Test
	public void testVerificarQtdComp() throws Exception {
		CompeticaoBO competicaoBO = new CompeticaoBO();
		
		competicaoBO.setModalidade("Ginastica");
		competicaoBO.setLocal("Ginasio");
		competicaoBO.setPais1("Brasil");
		competicaoBO.setPais2("Franca");
		competicaoBO.setEtapa(Etapas.OITAVAS_DE_FINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 17, 13, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 17, 15, 00));
		
		Assert.assertNotEquals(4, CompeticaoBO.verificarQtdComp(competicaoBO));
		
		competicaoBO.setModalidade("Futebol");
		competicaoBO.setLocal("Ginasio");
		competicaoBO.setPais1("Brasil");
		competicaoBO.setPais2("Uruguai");
		competicaoBO.setEtapa(Etapas.OITAVAS_DE_FINAL);
		competicaoBO.setDataHoraIni(LocalDateTime.of(2017, 10, 17, 13, 00));
		competicaoBO.setDataHoraFim(LocalDateTime.of(2017, 10, 17, 15, 00));
		
		CompeticaoBO.salvar(competicaoBO);
		CompeticaoBO.salvar(competicaoBO);
		CompeticaoBO.salvar(competicaoBO);
		Assert.assertTrue(CompeticaoBO.verificarQtdComp(competicaoBO) >= 4);
	}

}
