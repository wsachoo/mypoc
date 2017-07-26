package com.att.salesexpress.microservices.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.att.salesexpress.microservices.entity.SalesHistoryDetail;
import com.att.salesexpress.microservices.entity.SalesHistoryStripped;
import com.att.salesexpress.microservices.entity.SalesRulesMisExpDetail;
import com.att.salesexpress.microservices.entity.SalesUcpeRule;
import com.att.salesexpress.microservices.entity.SalesVnfRule;

public interface SalesHistoryService {
	public List<Object> getRecommendationBasedOnSalesHistory(Map<String, Object> params);

	public SalesHistoryDetail getSalesHistoryOrderDetailBySiteIdLeadDesignId(Long siteId, Long leadDesignId);

	List<Map<String, Object>> getSalesPercentageByAccessType(Map<String, Object> params);

	public SalesRulesMisExpDetail getSalesHistoryOrderDetailByDesignRuleId(int designRuleId);

	List<Object> getRecommendedVnfDevices();

	public SalesVnfRule getRecommendedVnfDeviceByRuleId(BigDecimal ruleId);

	List<Object> getRecommendedUcpeDevices();

	SalesUcpeRule getRecommendedUcpeDeviceByRuleId(Long ruleId);
}
