package com.mine.olimpiada.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mine.olimpiada.bo.CompeticaoBO;

/**
 * @author Zinsly, Tatiane
 * @email tatianezinsly@gmail.com
 */

/**
 * The class CompeticaoController is responsible for the mapping for all URL related to competitions
 * 
 */

@Controller
public class CompeticaoController {

	/**
	* Insert a competition
	* 
	* @param data - String from client side representing a data structure with data to be inserted
	* @return String - Response related to the success or fail of the operation
	*/
	@RequestMapping(value = { "/competicao" }, method = RequestMethod.POST)
	@ResponseBody
	public String cadastro(@RequestBody String data) {
		return DataController.salvarCompeticao(data);
	}

	/**
	* List all competition
	* 
	* @return String - Response containing a data structure related to the object requested
	*/
	@RequestMapping(value = { "/competicao/lista", "/competicao/lista/" })
	@ResponseBody
	public String viewAllCompeticao() {
		return this.listaCompeticao("");
	}

	/**
	* View a specific competition filtering by the modularity passed 
	* 
	* @param modalidade - modularity you want to filter
	* @return String - Response containing a data structure related to the object requeste
	*/
	@RequestMapping(value = { "/competicao/lista/{modalidade}", "/competicao/lista/{modalidade}/" })
	@ResponseBody
	public String viewCompeticao(@PathVariable("modalidade") String filter) {
		return this.listaCompeticao(filter);
	}

	public String listaCompeticao(String modalidade) {
		return CompeticaoBO.listarCompeticao(modalidade);
	}
	
}
