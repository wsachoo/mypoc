package com.att.salesexpress.microservices.sqlconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.att.salesexpress.microservices.util.SQLConstantsPostgresql;

@Configuration
@Profile("bluemix")
public class PostgresqlSqlConfig {
	
	@Bean
	public String sqlGetSalesHistoryDataByAccessType() {
		return SQLConstantsPostgresql.sqlGetSalesHistoryDataByAccessType;
	}

	@Bean
	public String sqlGetSalesHistoryDataByAccessTypeForOtherAccessType() {
		return SQLConstantsPostgresql.sqlGetSalesHistoryDataByAccessTypeForOtherAccessType;
	}

	@Bean
	public String sqlGetSalesHistoryDataByAccessTypeAndAccessSpeed() {
		return SQLConstantsPostgresql.sqlGetSalesHistoryDataByAccessTypeAndAccessSpeed;
	}

	@Bean
	public String sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed() {
		return SQLConstantsPostgresql.sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed;
	}

	@Bean
	public String sqlGetSalesHistoryPercentageRecordsByAccessType() {
		return SQLConstantsPostgresql.sqlGetSalesHistoryPercentageRecordsByAccessType;
	}
}
