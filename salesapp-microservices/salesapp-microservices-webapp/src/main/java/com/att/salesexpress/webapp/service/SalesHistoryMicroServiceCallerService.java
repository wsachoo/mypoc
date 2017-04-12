package com.att.salesexpress.webapp.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SalesHistoryMicroServiceCallerService {
	public List<Map<String, Object>> getSalesRecommendationFromHistory(Map<String, Object> paramValues) throws JsonProcessingException;
}