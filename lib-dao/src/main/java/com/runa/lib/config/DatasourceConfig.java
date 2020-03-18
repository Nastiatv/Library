package com.runa.lib.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DatasourceConfig {
	@Bean
	public DataSource datasource() {
		return DataSourceBuilder.create().driverClassName("com.mysql.cj.jdbc.Driver").url(
				"jdbc:mysql://localhost:3306/library?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Jakarta&useSSL=false")
				.username("root").password("root").build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.runa.lib.entities");
		factory.setDataSource(datasource());
		return factory;
	}

	@Bean 
	@Autowired 
	public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(factory);
	    return txManager;
	}
}
