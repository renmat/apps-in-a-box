package com.github.abx.boxtest.svc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("testbootkafka")
@SpringBootTest
public class TestBootKafka {
	
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Test
	void testKafka() throws SQLException {
       assertNotNull(embeddedKafkaBroker.getBrokersAsString());
	}
}
