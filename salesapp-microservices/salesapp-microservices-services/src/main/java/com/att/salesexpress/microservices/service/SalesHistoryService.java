package com.att.salesexpress.microservices.service;

import java.util.List;
import java.util.Map;

import com.att.salesexpress.microservices.entity.SalesHistoryDetail;
import com.att.salesexpress.microservices.entity.SalesHistoryStripped;

public interface SalesHistoryService {
	public List<SalesHistoryStripped> getRecommendationBasedOnSalesHistory(Map<String, Object> params);

	public SalesHistoryDetail getSalesHistoryOrderDetailBySiteIdLeadDesignId(Long siteId, Long leadDesignId);

}
