package com.mine.olimpiada.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Zinsly, Tatiane
 * @email tatianezinsly@gmail.com
 */

/**
 * Utils class contains generic methods to help on the overall process of the application
 * 
 */
public class Utils {

	/**
	* Method used to populate a Json response with an ErrorInfo structure infos
	* @see {@code ErrorInfo}
	*
	* @param errorInfo - Structured to be used to populate response Json
	* @return String - Representing new Json structure created
	*/
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
