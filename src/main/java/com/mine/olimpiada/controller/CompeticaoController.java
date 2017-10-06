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
	@RequestMapping(value = {"/cadastro-competicao"})
	public String cadastroTest(){
		return "cadastro";
	}
	
	@RequestMapping(value = {"/tati"}, method=RequestMethod.GET)
	@ResponseBody
	public String teste(@RequestParam String parm, HttpServletRequest request){
		return "Hello " + parm;
	}
	
	@RequestMapping(value = {"/competicao"}, method=RequestMethod.POST)
	@ResponseBody
	public String cadastro(@RequestBody String data, HttpServletRequest request){
		return this.salvarCompeticao(data);
	}
	
	@RequestMapping(value = { "/competicao/{id}", "/competicao/{id}/" })
	public String viewCompeticao(){
		return "";
	}
	
	@RequestMapping(value = { "/competicao/all", "/competicao/all/" })
	public String viewAllCompeticao(){
		return "";
	}
	
	public String salvarCompeticao(String data){
		System.out.println("Step 1 - Controller.salvarCompeticao");
		CompeticaoBO compBO = new CompeticaoBO();
		return compBO.salvarCompeticao(data);
	}
	
	public void validarCompeticao(){
		
	}
	
}
