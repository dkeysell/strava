package com.djk.komhunter.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class PersistenceConfiguration {
	Logger logger = LoggerFactory.getLogger(PersistenceConfiguration.class);
	
	@Bean
	public DataSource dataSource() {
		logger.info("DataSource builder called");
		DataSourceBuilder<DataSource> builder = (DataSourceBuilder<DataSource>) DataSourceBuilder.create();
		builder.url("jdbc:postgresql://0.0.0.0:5432/kom_hunter");
		builder.username("postgres");
		builder.password("Welcome");
		return builder.build();
	}
}
