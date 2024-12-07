package com.github.abx.boot2;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.github.abx.common.Util;
import com.github.abx.common.jpa.db4.model.Payment;

@ActiveProfiles("testbootapp")
@SpringBootTest
@EmbeddedKafka(topics = { "PAYMENT_TOPIC" })
@TestPropertySource(properties = { "bootapp.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
public class TestBootApp {

	@Autowired
	private DataSource dbDataSource;

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Test
	void testBootApp() throws SQLException {
		assertTrue(Util.testConnection(dbDataSource));
	}

	@Test
	void testKafkaConsumer() throws SQLException {
		Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker.getBrokersAsString());
		try (Producer<String, Payment> producerTest = new KafkaProducer<>(producerProps, new StringSerializer(),
				new JsonSerializer<Payment>())) {
			producerTest.send(new ProducerRecord<String, Payment>("PAYMENT_TOPIC", "", new Payment()));
		}		
	}

}
