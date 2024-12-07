package com.github.abx.boot1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.github.abx.common.jpa.db1"},entityManagerFactoryRef = "entityManagerFactoryBoot1",transactionManagerRef = "transactionManagerBoot1")
@EnableKafka
public class BootApp {

	public static void main(String[] args) {
		SpringApplication.run(BootApp.class);
	}
}
