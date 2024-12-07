package com.github.abx.boxtest.svc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaZKBroker;

@Configuration
public class BootKafkaConfig {

	@Bean
	public EmbeddedKafkaBroker embeddedKafkaBroker(
			@Value("${boxapp.boxkafka.port:8088}") int kafkaPort,
			@Value("${boxapp.boxkafka.topics:boxapp.boxkafka.topic}") String topics) {
		EmbeddedKafkaBroker kafkaBroker = new EmbeddedKafkaZKBroker(1,false,1,topics);
		kafkaBroker.kafkaPorts(kafkaPort);
		return kafkaBroker;
	}
}
