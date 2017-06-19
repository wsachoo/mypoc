package com.att.pricerd.microservices.service;

import java.util.List;
import java.util.Map;

import com.att.pricerd.microservices.entity.SalesHistoryDetail;
import com.att.pricerd.microservices.entity.SalesHistoryStripped;
import com.att.pricerd.microservices.entity.SalesRulesMisExpDetail;

public interface SalesHistoryService {
	public List<SalesHistoryStripped> getRecommendationBasedOnSalesHistory(Map<String, Object> params);

	public SalesHistoryDetail getSalesHistoryOrderDetailBySiteIdLeadDesignId(Long siteId, Long leadDesignId);

	List<Map<String, Object>> getSalesPercentageByAccessType(Map<String, Object> params);
	
	public SalesRulesMisExpDetail getSalesHistoryOrderDetailByDesignRuleId(int designRuleId);

}
