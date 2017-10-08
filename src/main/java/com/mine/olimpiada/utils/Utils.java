package com.mine.olimpiada.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class Utils {

	public static String errorJsonResponse(ErrorInfo errorInfo) {
		JSONObject jsonErrorResponse = new JSONObject();

		try {
			jsonErrorResponse.put("erro", errorInfo.getMessage());
			jsonErrorResponse.put("detalhe", errorInfo.getDetails());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonErrorResponse.toString();
	}

}
