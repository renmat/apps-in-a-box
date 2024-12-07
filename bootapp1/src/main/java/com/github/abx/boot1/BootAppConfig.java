package com.github.abx.boot1;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.github.abx.common.jpa.db1.model.Agent;
import com.github.abx.common.jpa.db1.repo.AgentRepository;
import com.github.abx.common.jpa.db4.model.Payment;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class BootAppConfig {

	@Bean
	public DataSource db1DataSource() {
		return db1DataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean
	public DataSource db2DataSource() {
		return db2DataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.db1")
	public DataSourceProperties db1DataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.db2")
	public DataSourceProperties db2DataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public JdbcTemplate db1JdbcTemplate(@Qualifier("db1DataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public JdbcTemplate db2JdbcTemplate(@Qualifier("db2DataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBoot1(
			@Qualifier("db1DataSource") DataSource dataSource) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(Agent.class.getPackageName(),AgentRepository.class.getPackageName());
		factory.setDataSource(dataSource);
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.physical_naming_strategy",CamelCaseToUnderscoresNamingStrategy.class.getName());
		factory.setJpaProperties(jpaProperties );
		return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManagerBoot1(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}

	@Bean
	public ProducerFactory<String, Payment> producerFactory(
			@Value("${bootapp.kafka.bootstrap-servers}") String bootstrapServers) {
		Map<String, Object> props = new HashMap<>();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<>(props);
	}

	@Bean
	public KafkaTemplate<String, Payment> kafkaTemplate(ProducerFactory<String, Payment> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}

}
