package com.mine.olimpiada.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class UtilsTest {

	@Test
	public void testErrorJsonResponse() throws Exception {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setDetails("Details");
		errorInfo.setMessage("Message");
		
		assertTrue(Utils.errorJsonResponse(errorInfo).contains("Details"));
		
	}

}
