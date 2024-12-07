package com.github.abx.boot3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.abx.common.Util;

@ActiveProfiles("testbootapp")
@SpringBootTest
public class TestBootApp {

	@Autowired
	@Qualifier("db1DataSource")
	private DataSource db1DataSource;

	@Autowired
	@Qualifier("db2DataSource")
	private DataSource db2DataSource;

	@Autowired
	@Qualifier("db3DataSource")
	private DataSource db3DataSource;


	@Test
	void testBootApp() throws SQLException {
		assertTrue(Util.testConnection(db1DataSource));
		assertTrue(Util.testConnection(db2DataSource));
		assertTrue(Util.testConnection(db3DataSource));
	}

}
