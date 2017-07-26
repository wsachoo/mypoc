package com.att.salesexpress.microservices.kubeclient.service;

import java.util.Map;

public interface SalesHistoryMicroServiceCallerService {
	public Map<String, Object> getSalesRecommendationFromHistory(Map<String, Object> paramValues);
	
	public Map<String, Object> getSalesPercentageByAccessType(Map<String, Object> paramValues);

	public String getSalesHistoryOrderDetailBySiteIdLeadDesignId(Long siteId, Long leadDesignId);
}