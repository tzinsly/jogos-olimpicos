package com.mine.olimpiada.dao;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.mine.olimpiada.bo.CompeticaoBO;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class CompeticaoDAO {
	
	public static String salvar(CompeticaoBO comp){
		int status = 0;
		status = DbOperations.insert("competicao", comp);
		if (status > 0) {
			return "Successful Insert"; 
		} else {
			return "Failed Insert"; 
		}
	}
	
	public static ArrayList<CompeticaoBO> listar(String modalidade){
		DbOperations dbOp = new DbOperations();
		ArrayList<CompeticaoBO> array = dbOp.select("competicao", modalidade);
		/*System.out.println("comp dao: " + array.get(0).getId());
		System.out.println("comp dao: " + array.get(1).getId());*/
		return array;
	}
	
}
