package com.att.pricerd.microservices.sqlconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.att.pricerd.microservices.util.SQLConstantsPostgresql;

@Configuration
@Profile("postgresql")
public class PostgresqlSqlConfig {

	@Bean
	public String sqlGetSalesHistoryDataByAccessType() {
		return SQLConstantsPostgresql.sqlGetSalesHistoryDataByAccessType;
	}

	@Bean
	public String sqlGetSalesHistoryDataByAccessTypeIndexWithinGroup() {
		return SQLConstantsPostgresql.sqlGetSalesHistoryDataByAccessTypeIndexWithinGroup;
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

	@Bean
	public String sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeed() {
		return SQLConstantsPostgresql.sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeed;
	}

	@Bean
	public String sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeedAndPortSpeed() {
		return SQLConstantsPostgresql.sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeedAndPortSpeed;
	}
}
