package com.github.abx.boot2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.github.abx.common.jpa.db4.model.Payment;

@Component
public class MessageConsumer {

	@KafkaListener(topics = "PAYMENT_TOPIC", groupId = "consumer_group_id")
	public void consumeExampleDTO(Payment payment) {
		
	}

}
