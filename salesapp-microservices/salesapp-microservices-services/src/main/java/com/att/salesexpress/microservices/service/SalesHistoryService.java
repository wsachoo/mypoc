package com.att.salesexpress.microservices.service;

import java.util.List;
import java.util.Map;

public interface SalesHistoryService {
	public List<Map<String, Object>> getRecommendationBasedOnSalesHistory(Map<String, Object> params);

}
