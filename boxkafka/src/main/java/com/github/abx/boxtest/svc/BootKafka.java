package com.github.abx.boxtest.svc;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@SpringBootApplication
public class BootKafka {

	public static void main(String[] args) {		
		EmbeddedKafkaBroker kafka = new SpringApplicationBuilder(BootKafka.class)
				.web(WebApplicationType.NONE).run(args).getBean(EmbeddedKafkaBroker.class);
		System.setProperty(System.getProperty("boxapp.boxkafka.brokers.propkey", "boxapp.boxkafka.brokers"), kafka.getBrokersAsString());
	}
}
