package com.att.salesexpress.microservices.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.salesexpress.microservices.dao.SalesHistoryDao;

@Service
public class SalesHistoryServiceImpl implements SalesHistoryService {

	@Autowired
	private SalesHistoryDao objSalesHistoryDao;

	@Override
	public List<Map<String, Object>> getRecommendationBasedOnSalesHistory(Map<String, Object> params) {
		List<Map<String, Object>> result = new ArrayList<>();
		
		Set<String> keys = params.keySet();
		int numberOfRowsToRetrieve = 10;
		
		if (keys.contains("NUMBER_OF_ROWS")) {
			numberOfRowsToRetrieve = Integer.parseInt(params.get("NUMBER_OF_ROWS").toString().trim());
		}
		
		if (keys.contains("ACCESS_TYPE_ID") && keys.contains("ACCESS_SPEED_ID")) {
			String accessType = params.get("ACCESS_TYPE_ID").toString().trim();
			Integer accessSpeed = Integer.parseInt(params.get("ACCESS_SPEED_ID").toString().trim());
			result = objSalesHistoryDao.getRecordsByAccessTypeAndAccessSpeed(accessType, accessSpeed, numberOfRowsToRetrieve);
		}
		else if (keys.contains("ACCESS_TYPE_ID")) {
			String accessType = params.get("ACCESS_TYPE_ID").toString().trim();
			result = objSalesHistoryDao.getRecordsByAccessType(accessType, numberOfRowsToRetrieve);
		}
		
		return result;
	}
}
