package com.att.salesexpress.igloo.consumer.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class HikariH2Config {

	@Bean(destroyMethod = "close", name = "hikariH2Datasource")
	@ConfigurationProperties(prefix = "hikari.h2")
	public DataSource dataSourceOracle() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "hikariH2JdbcTemplate")
	public JdbcTemplate jdbcTemplateOracle(@Qualifier("hikariH2Datasource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}