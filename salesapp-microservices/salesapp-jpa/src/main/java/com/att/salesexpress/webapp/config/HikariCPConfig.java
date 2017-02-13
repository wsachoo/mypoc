package com.att.salesexpress.webapp.config;

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
public class HikariCPConfig {

	@Bean(destroyMethod = "close", name = "hikariOraDatasource")
	@Primary
	@ConfigurationProperties(prefix = "hikari.oracle")
	public DataSource dataSourceOracle() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean(name = "hikariOraJdbcTemplate")
	public JdbcTemplate jdbcTemplateOracle(@Qualifier("hikariOraDatasource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean(destroyMethod = "close", name = "hikariPostgreSqlDatasource")
	@ConfigurationProperties(prefix = "hikari.postgres")
	public DataSource dataSourcePostgreSql() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
}