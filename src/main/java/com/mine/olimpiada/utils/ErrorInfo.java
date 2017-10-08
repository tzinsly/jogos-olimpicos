package com.mine.olimpiada.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class ErrorInfo {
	
	private String message;
	private String details;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
