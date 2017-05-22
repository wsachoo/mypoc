package com.att.salesexpress.microservices.sqlconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.att.salesexpress.microservices.util.SQLConstantsOracle;

@Configuration
@Profile("default")
public class OracleSqlConfig {
	
	@Bean
	public String sqlGetSalesHistoryDataByAccessType() {
		return SQLConstantsOracle.sqlGetSalesHistoryDataByAccessType;
	}

	@Bean
	public String sqlGetSalesHistoryDataByAccessTypeForOtherAccessType() {
		return SQLConstantsOracle.sqlGetSalesHistoryDataByAccessTypeForOtherAccessType;
	}

	@Bean
	public String sqlGetSalesHistoryDataByAccessTypeAndAccessSpeed() {
		return SQLConstantsOracle.sqlGetSalesHistoryDataByAccessTypeAndAccessSpeed;
	}

	@Bean
	public String sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed() {
		return SQLConstantsOracle.sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed;
	}

	@Bean
	public String sqlGetSalesHistoryPercentageRecordsByAccessType() {
		return SQLConstantsOracle.sqlGetSalesHistoryPercentageRecordsByAccessType;
	}
	
	@Bean
	public String sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeed() {
		return SQLConstantsOracle.sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeed;
	}
	
	@Bean
	public String sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeedAndPortSpeed() {
		return SQLConstantsOracle.sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeedAndPortSpeed;
	}
}
