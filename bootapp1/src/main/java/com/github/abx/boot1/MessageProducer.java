package com.github.abx.boot1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.github.abx.common.jpa.db4.model.Payment;

@Component
public class MessageProducer {

	@Autowired
	private KafkaTemplate<String, Payment> kafkaTemplate;

	public void send(Payment payment) {
		kafkaTemplate.send("PAYMENT_TOPIC", payment);
	}
}
