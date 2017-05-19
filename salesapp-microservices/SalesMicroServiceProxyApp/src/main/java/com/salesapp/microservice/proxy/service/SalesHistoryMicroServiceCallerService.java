package com.salesapp.microservice.proxy.service;
import java.util.Map;

public interface SalesHistoryMicroServiceCallerService {
	public Map<String, Object> getSalesRecommendationFromHistory(Map<String, Object> paramValues);
	
	public Map<String, Object> getSalesPercentageByAccessType(Map<String, Object> paramValues);
}