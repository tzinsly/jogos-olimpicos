package com.mine.olimpiada.dao;

import com.mine.olimpiada.bo.CompeticaoBO;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class CompeticaoDAO {
	
	public static String salvar(CompeticaoBO comp){
		int status = 0;
		System.out.println("Step 4 - CompeticaoDAO.salvar: " + comp.getModalidade());
		status = DbOperations.insert("competicao", comp);
		if (status > 0) {
			return "Successful Insert"; 
		} else {
			return "Failed Insert"; 
		}
	}
	
}
