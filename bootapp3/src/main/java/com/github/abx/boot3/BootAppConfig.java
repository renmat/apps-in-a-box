package com.github.abx.boot3;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

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
	public DataSource db3DataSource() {
		return db3DataSourceProperties().initializeDataSourceBuilder().build();
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
	@ConfigurationProperties("spring.datasource.db3")
	public DataSourceProperties db3DataSourceProperties() {
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
	public JdbcTemplate db3JdbcTemplate(@Qualifier("db3DataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("db1DataSource") DataSource dataSource) {
	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setPackagesToScan("com.github.abx.common.jpa.db1");
	    factory.setDataSource(dataSource);
	    return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory);
	    return txManager;
	}
}
