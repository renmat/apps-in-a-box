package com.github.abx.sparkjob1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SparkJob {

	public static void main(String[] args) {
		try(ConfigurableApplicationContext context = SpringApplication.run(SparkJob.class,args)){
			System.out.println("[main] "+SparkJob.class.getName());
		}
	}
}
