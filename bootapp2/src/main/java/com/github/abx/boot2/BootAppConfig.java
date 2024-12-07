package com.github.abx.boot2;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.github.abx.common.jpa.db4.model.Payment;

@Configuration
public class BootAppConfig {

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, Payment> kafkaListenerContainerFactory(
			ConsumerFactory<String, Payment> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, Payment> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}

	@Bean
	public ConsumerFactory<String, Payment> consumerFactory(
			@Value("${bootapp.kafka.bootstrap-servers}") String bootstrapServers) {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
				new JsonDeserializer<>(Payment.class, false));
	}
}
