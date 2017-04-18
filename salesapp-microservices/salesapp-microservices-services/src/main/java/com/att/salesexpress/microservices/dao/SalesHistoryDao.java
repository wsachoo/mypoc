package com.att.salesexpress.microservices.dao;

import java.util.List;
import java.util.Map;

public interface SalesHistoryDao {
	public List<Map<String, Object>> getRecordsByAccessType(String accessType, int numberOfRows);

	public List<Map<String, Object>> getRecordsByAccessTypeAndAccessSpeed(String accessType, int accessSpeed, int numberOfRows);
	
	public List<Map<String, Object>> sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed(String accessType, int accessSpeed, int portSpeed, int numberOfRows);
}
