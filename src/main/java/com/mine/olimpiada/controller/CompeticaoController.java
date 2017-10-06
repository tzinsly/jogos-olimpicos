package com.mine.olimpiada.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mine.olimpiada.bo.CompeticaoBO;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

@Controller
public class CompeticaoController {

	/*TESTES*/
	@RequestMapping(value = { "/cadastro-competicao" })
	public String cadastroTest() {
		return "cadastro";
	}

	@RequestMapping(value = { "/tati" }, method = RequestMethod.GET)
	@ResponseBody
	public String teste(@RequestParam String parm, HttpServletRequest request) {
		return "Hello " + parm;
	}

	@RequestMapping(value = { "/competicao" }, method = RequestMethod.POST)
	@ResponseBody
	public String cadastro(@RequestBody String data, HttpServletRequest request) {
		return this.salvarCompeticao(data);
	}

	@RequestMapping(value = { "/competicao/all", "/competicao/all/" })
	@ResponseBody
	public String viewAllCompeticao() {
		return this.listaCompeticao("");
	}

	/*@RequestMapping(value = { "/competicao/{modalidade}", "/competicao/{modalidade}/" })
	@ResponseBody
	public void viewCompeticao(){
		this.listaCompeticao("");
	}*/
	
	public String salvarCompeticao(String data) {
		System.out.println("Step 1 - Controller.salvarCompeticao");
		CompeticaoBO compBO = new CompeticaoBO();
		return compBO.salvarCompeticao(data);
	}

	public String listaCompeticao(String modalidade) {
		CompeticaoBO compBO = new CompeticaoBO();
		return compBO.listarCompeticao(modalidade);
	}

	public void validarCompeticao() {

		/*Duas competições não podem ocorrer no mesmo período, no mesmo local, para a
		mesma modalidade. Ex: Se eu tenho uma partida de futebol que com início às 18:00 e
		término às 20:00 no Estádio 1, eu não poderia ter outra partida de futebol se iniciando
		às 19:30 nesse mesmo estádio
		● O fluxo de cadastro deve permitir que se forneça o mesmo valor, para os 2 países
		envolvidos na disputa, apenas se a etapa for Final ou Semifinal. Para as demais etapas,
		não se deve permitir que se forneça o mesmo valor.
		● A competição deve ter a duração de no mínimo 30 minutos.
		● Para evitar problemas, a organização das olimpíadas que limitar a no máximo 4
		competições por dia num mesmo local
		● Para situações de erro, é necessário que a resposta da requisição seja coerente em
		exibir uma mensagem condizente com o erro.*/

	}

}
