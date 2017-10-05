package com.mine.olimpiada.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
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
	
	@RequestMapping(value = {"/competicao"}, method=RequestMethod.POST)
	public String cadastro(@RequestParam String data, HttpServletRequest request){
		return this.salvarCompeticao(data);
		//return comp.getRecordCountJSON().toString();
		//return "cadastro";
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
		CompeticaoBO compBO = new CompeticaoBO();
		return compBO.salvarCompeticao(data);
	}
	
}
