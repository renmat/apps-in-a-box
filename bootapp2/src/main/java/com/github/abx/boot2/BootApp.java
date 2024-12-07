package com.github.abx.boot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class BootApp {

	public static void main(String[] args) {
		SpringApplication.run(BootApp.class);
	}
}
