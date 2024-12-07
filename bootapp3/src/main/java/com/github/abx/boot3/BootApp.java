package com.github.abx.boot3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BootApp {

	public static void main(String[] args) {
		SpringApplication.run(BootApp.class);
	}
}
