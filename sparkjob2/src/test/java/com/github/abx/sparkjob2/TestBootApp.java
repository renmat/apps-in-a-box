package com.github.abx.sparkjob2;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("testsparkapp")
@SpringBootTest
public class TestBootApp {

	@Autowired
	@Qualifier("testBean")
	private Object testBean;

	@Test
	void testJobApp() throws SQLException {
		
	}
	
}
