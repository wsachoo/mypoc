package com.att.salesexpress.microservices.dao;

import java.util.List;
import java.util.Map;

import com.att.salesexpress.microservices.entity.SalesUcpeRule;
import com.att.salesexpress.microservices.entity.SalesVnfRule;

public interface SalesHistoryDao {
	public List<Map<String, Object>> getRecordsByAccessType(String accessType, int numberOfRows);

	public List<Map<String, Object>> getRecordsByAccessTypeAndAccessSpeed(String accessType, int accessSpeed,
			int numberOfRows);

	public List<Map<String, Object>> sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed(String accessType,
			int accessSpeed, int portSpeed, int numberOfRows);

	List<Map<String, Object>> sqlGetSalesHistoryPercentageRecordsByAccessType(int numberOfRows);

	List<Map<String, Object>> getRecordsByAccessTypeAndAccessSpeedFromMisExpRules(String accessType, int accessSpeed,
			int numberOfRows);

	List<Map<String, Object>> getRecordsByAccessTypeAccessSpeedPortSpeedFromMisExpRules(String accessType,
			int accessSpeed, int portSpeed, int numberOfRows);

	public List<Map<String, Object>> getRecordsByAccessType(String accessType, Integer indexWithinGroup,
			int numberOfRowsToRetrieve);

	List<Object> getRecommendedVnfDevices();

	List<Object> getRecommendedUcpeDevices();
}
