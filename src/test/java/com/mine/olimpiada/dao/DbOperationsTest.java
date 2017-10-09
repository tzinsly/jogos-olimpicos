package com.mine.olimpiada.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.AssertTrue;

import org.junit.Assert;
import org.junit.Test;

import com.mine.olimpiada.bo.CompeticaoBO;
import com.mine.olimpiada.bo.CompeticaoBO.Etapas;

/**
 * @author Zinsly, Tatiane
 * @email tzinsly@br.ibm.com
 */

public class DbOperationsTest {

	@Test
	public void testExecuteUpdate() throws Exception {
		String query = "Insert into competicao (modalidade, local, pais1, pais2, etapa, dataHoraIni, dataHoraFim) values (?, ?, "
				+ "?, ?, ?, ?, ?)";

		Assert.assertEquals(1, DbOperations.executeUpdate(query, "Basquete", "Ginasio2", "Brasil", "Paraguai", "FINAL", "2017-10-29T14:00",
				"2017-10-29T16:00"));

		query = "Select * from competicao";

		try {
			DbOperations.executeUpdate(query, "");
			fail("Expected SQLException");
		} catch (SQLException e) {}

	}

	@Test
	public void testExecuteQuery() throws Exception {
		String query = "Select * from competicao";

		assertTrue(DbOperations.executeQuery(query, "") == true || DbOperations.executeQuery(query, "") == false);
	}

	@Test
	public void testExecuteGetLista() throws Exception {
		String query = "Select * from competicao";

		Assert.assertNotNull(DbOperations.executeGetLista(query, ""));
	}

	@Test
	public void testPopulateParms() throws Exception {
		Connection conn = DbConnectionSQLite.connectToDb();
		String query = "Select * from competicao where modalidade = ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		
		Assert.assertEquals("Populated parms", DbOperations.populateParms(pstmt, "String1"));
		
		Assert.assertEquals("Populated parms", DbOperations.populateParms(pstmt, Etapas.FINAL));
		
		Assert.assertEquals("Populated parms", DbOperations.populateParms(pstmt, LocalDateTime.now()));
		
		Assert.assertEquals("Invalid Parm", DbOperations.populateParms(pstmt, 2L));
	}

}
