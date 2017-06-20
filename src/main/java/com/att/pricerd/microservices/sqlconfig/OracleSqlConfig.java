package com.att.pricerd.microservices.sqlconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.att.pricerd.microservices.util.SQLConstantsOracle;

@Configuration
@Profile("development")
public class OracleSqlConfig {
	
	@Bean
	public String sqlGetSalesHistoryDataByAccessType() {
		return SQLConstantsOracle.sqlGetSalesHistoryDataByAccessType;
	}
	
	@Bean
	public String sqlGetSalesHistoryDataByAccessTypeIndexWithinGroup() {
		return SQLConstantsOracle.sqlGetSalesHistoryDataByAccessTypeIndexWithinGroup;
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
