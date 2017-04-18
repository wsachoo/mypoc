package com.att.salesexpress.microservices.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.salesexpress.microservices.dao.SalesHistoryDao;
import com.att.salesexpress.microservices.dao.SalesHistoryDetailRepository;
import com.att.salesexpress.microservices.entity.SalesHistoryDetail;
import com.att.salesexpress.microservices.entity.SalesHistoryDetailPK;
import com.att.salesexpress.microservices.entity.SalesHistoryStripped;

@Service
public class SalesHistoryServiceImpl implements SalesHistoryService {

	private static final int DEFAULT_NUMBER_OF_ROWS_TO_RETRIEVE = 10;
	
	@Autowired
	private SalesHistoryDao objSalesHistoryDao;
	
	@Autowired
	SalesHistoryDetailRepository objSalesHistoryDetailRepository;	

	@Override
	public List<SalesHistoryStripped> getRecommendationBasedOnSalesHistory(Map<String, Object> params) {
		List<Map<String, Object>> result = new ArrayList<>();
		
		Set<String> keys = params.keySet();
		
		int numberOfRowsToRetrieve = DEFAULT_NUMBER_OF_ROWS_TO_RETRIEVE;
		
		if (keys.contains("NUMBER_OF_ROWS")) {
			numberOfRowsToRetrieve = Integer.parseInt(params.get("NUMBER_OF_ROWS").toString().trim());
		}
		
		if (keys.contains("ACCESS_TYPE_ID") && keys.contains("ACCESS_SPEED_ID") && keys.contains("PORT_SPEED_ID")) {
			String accessType = params.get("ACCESS_TYPE_ID").toString().trim();
			Integer accessSpeed = Integer.parseInt(params.get("ACCESS_SPEED_ID").toString().trim());
			Integer portSpeed = Integer.parseInt(params.get("PORT_SPEED_ID").toString().trim());
			result = objSalesHistoryDao.sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed(accessType, accessSpeed, portSpeed, numberOfRowsToRetrieve);			
		}
		else if (keys.contains("ACCESS_TYPE_ID") && keys.contains("ACCESS_SPEED_ID")) {
			String accessType = params.get("ACCESS_TYPE_ID").toString().trim();
			Integer accessSpeed = Integer.parseInt(params.get("ACCESS_SPEED_ID").toString().trim());
			result = objSalesHistoryDao.getRecordsByAccessTypeAndAccessSpeed(accessType, accessSpeed, numberOfRowsToRetrieve);
		}
		else if (keys.contains("ACCESS_TYPE_ID")) {
			String accessType = params.get("ACCESS_TYPE_ID").toString().trim();
			result = objSalesHistoryDao.getRecordsByAccessType(accessType, numberOfRowsToRetrieve);
		}
		
		List<SalesHistoryStripped> returnValue = new ArrayList<>();
		
		for (Map<String, Object> map : result) {
			returnValue.add(transformToSalesHistoryStripped(map));
		}
		
		return returnValue;
	}
	
	
	private SalesHistoryStripped transformToSalesHistoryStripped(Map<String, Object> map) {
		SalesHistoryStripped objSalesHistoryDO = new SalesHistoryStripped();
		
		objSalesHistoryDO.setSiteId(Long.parseLong(map.get("SITE_ID").toString()));
		objSalesHistoryDO.setLeadDesignId(Long.parseLong(map.get("LEAD_DESIGN_ID").toString()));
		objSalesHistoryDO.setAccessArchitecture(map.get("ACCESS_ARCHITECTURE") != null ? map.get("ACCESS_ARCHITECTURE").toString() : "");
		objSalesHistoryDO.setAccessSpeed((String)map.get("ACCESS_SPEED"));
		objSalesHistoryDO.setCpeModel(map.get("CPE_MODEL") != null ? map.get("CPE_MODEL").toString() : "");
		objSalesHistoryDO.setEthernetHandoffInterface(map.get("ETHERNET_HANDOFF_INTERFACE_S") != null ? map.get("ETHERNET_HANDOFF_INTERFACE_S").toString() : "");
		objSalesHistoryDO.setDesignType(map.get("DESIGN_TYPE") != null ? map.get("DESIGN_TYPE").toString() : "");
		objSalesHistoryDO.setPortSpeed(map.get("PORT_SPEED") != null ? map.get("PORT_SPEED").toString() : "");
		objSalesHistoryDO.setManagedRouter(map.get("MANAGED_ROUTER") != null ? map.get("MANAGED_ROUTER").toString() : "");
		objSalesHistoryDO.setRatePlan(map.get("RATE_PLAN") != null ? map.get("RATE_PLAN").toString() : "");
		objSalesHistoryDO.setTailTechnologyId(map.get("TAIL_TECHNOLOGY") != null ? map.get("TAIL_TECHNOLOGY").toString() : "");
		objSalesHistoryDO.setSiteNameAlias(map.get("SITE_NAME_ALIAS") != null ? map.get("SITE_NAME_ALIAS").toString() : "");
		objSalesHistoryDO.setMatchPercentage(map.get("MATCHING_ROW_PERCENTAGE") != null ? map.get("MATCHING_ROW_PERCENTAGE").toString() : "");
		
		return objSalesHistoryDO;
	}


	@Override
	public SalesHistoryDetail getSalesHistoryOrderDetailBySiteIdLeadDesignId(Long siteId, Long leadDesignId) {
		SalesHistoryDetailPK objSalesHistoryDetailPK = new SalesHistoryDetailPK();
		objSalesHistoryDetailPK.setSiteId(siteId);
		objSalesHistoryDetailPK.setLeadDesignId(leadDesignId);
		
		SalesHistoryDetail objSalesHistoryDetail = objSalesHistoryDetailRepository.findById(objSalesHistoryDetailPK);
		return objSalesHistoryDetail;
	}
}
