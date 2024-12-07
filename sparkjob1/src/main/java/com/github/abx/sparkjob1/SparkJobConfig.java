package com.github.abx.sparkjob1;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkJobConfig {
	
	@Bean
	@Qualifier("testBean")
	public Object testBean() {
		return new Object();
	}
}
