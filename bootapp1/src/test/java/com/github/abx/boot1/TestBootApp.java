package com.github.abx.boot1;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.github.abx.common.Util;
import com.github.abx.common.jpa.db4.model.Payment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ActiveProfiles("testbootapp")
@SpringBootTest
@EmbeddedKafka(topics = { "PAYMENT_TOPIC" })
@TestPropertySource(properties = { "bootapp.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
public class TestBootApp {
	
	@PersistenceContext 
	private EntityManager entityManager;

	@Autowired
	@Qualifier("db1DataSource")
	private DataSource db1DataSource;

	@Autowired
	@Qualifier("db2DataSource")
	private DataSource db2DataSource;
	
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
	
    @Autowired
    private MessageProducer messageProducer;

	@Test
	void testBootApp() throws SQLException {
		assertTrue(Util.testConnection(db1DataSource));
		assertTrue(Util.testConnection(db2DataSource));
	}

	@Test
	void testKafkaProducer() throws SQLException {
        Map<String, Object> consumerConfig = KafkaTestUtils.consumerProps("test_group_id", "false", embeddedKafkaBroker);
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        
        try(Consumer<String, Payment> consumer = new DefaultKafkaConsumerFactory<>(consumerConfig, new StringDeserializer(), new JsonDeserializer<>(Payment.class, false)).createConsumer()){

            embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "PAYMENT_TOPIC");
            
            messageProducer.send(new Payment());
            ConsumerRecord<String, Payment> out = KafkaTestUtils.getSingleRecord(consumer, "PAYMENT_TOPIC");
            assertNotNull(out);
            

        }
        
	}
}
