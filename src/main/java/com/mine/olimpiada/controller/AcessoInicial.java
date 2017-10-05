package com.mine.olimpiada.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

@Controller
public class AcessoInicial {
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
}
