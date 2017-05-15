package com.att.salesexpress.microservices.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.salesexpress.microservices.dao.SalesHistoryDao;
import com.att.salesexpress.microservices.dao.SalesHistoryDetailRepository;
import com.att.salesexpress.microservices.dao.SalesRulesMisExpRepository;
import com.att.salesexpress.microservices.entity.SalesHistoryDetail;
import com.att.salesexpress.microservices.entity.SalesHistoryDetailPK;
import com.att.salesexpress.microservices.entity.SalesHistoryStripped;
import com.att.salesexpress.microservices.entity.SalesRulesMisExpDetail;
import com.att.salesexpress.microservices.entity.SalesRulesMisExpDetailPK;

@Service
public class SalesHistoryServiceImpl implements SalesHistoryService {

	private static final int DEFAULT_NUMBER_OF_ROWS_TO_RETRIEVE = 10;

	@Autowired
	private SalesHistoryDao objSalesHistoryDao;

	@Autowired
	SalesHistoryDetailRepository objSalesHistoryDetailRepository;
	
	@Autowired
	SalesRulesMisExpRepository objSalesRulesMisExpRepository;

	@Override
	public List<SalesHistoryStripped> getRecommendationBasedOnSalesHistory(Map<String, Object> params) {
		List<Map<String, Object>> result = new ArrayList<>();
		Set<String> keys = params.keySet();
		
		int numberOfRowsToRetrieve = DEFAULT_NUMBER_OF_ROWS_TO_RETRIEVE;

		if (keys.contains("NUMBER_OF_ROWS")) {
			numberOfRowsToRetrieve = Integer.parseInt(params.get("NUMBER_OF_ROWS").toString().trim());
		}


		if (containsAllWithValidValues("ACCESS_TYPE_ID,ACCESS_SPEED_ID,PORT_SPEED_ID", params)) {
		//if (keys.contains("ACCESS_TYPE_ID") && keys.contains("ACCESS_SPEED_ID") && keys.contains("PORT_SPEED_ID")) {
			String accessType = params.get("ACCESS_TYPE_ID").toString().trim();
			Integer accessSpeed = Integer.parseInt(params.get("ACCESS_SPEED_ID").toString().trim());
			Integer portSpeed = Integer.parseInt(params.get("PORT_SPEED_ID").toString().trim());

			result = objSalesHistoryDao.sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed(accessType,accessSpeed, portSpeed, numberOfRowsToRetrieve);
			if(result.isEmpty()){
				result = objSalesHistoryDao.getRecordsByAccessTypeAccessSpeedPortSpeedFromMisExpRules(accessType, accessSpeed, portSpeed, numberOfRowsToRetrieve);
			}
						
		//} else if (keys.contains("ACCESS_TYPE_ID") && keys.contains("ACCESS_SPEED_ID")) {
		  }	else if (containsAllWithValidValues("ACCESS_TYPE_ID,ACCESS_SPEED_ID", params)) {
			String accessType = params.get("ACCESS_TYPE_ID").toString().trim();
			Integer accessSpeed = Integer.parseInt(params.get("ACCESS_SPEED_ID").toString().trim());
			result = objSalesHistoryDao.getRecordsByAccessTypeAndAccessSpeed(accessType, accessSpeed,
					numberOfRowsToRetrieve);
			if(result.isEmpty()) {
				result = objSalesHistoryDao.getRecordsByAccessTypeAndAccessSpeedFromMisExpRules(accessType, accessSpeed, numberOfRowsToRetrieve);
			}
			
		//} else if (keys.contains("ACCESS_TYPE_ID")) {
		  } else if (containsAllWithValidValues("ACCESS_TYPE_ID", params)) {
			String accessType = params.get("ACCESS_TYPE_ID").toString().trim();
			result = objSalesHistoryDao.getRecordsByAccessType(accessType, numberOfRowsToRetrieve);
		}

		List<SalesHistoryStripped> returnValue = new ArrayList<>();

		for (Map<String, Object> map : result) {
			returnValue.add(transformToSalesHistoryStripped(map));
		}
		
		return returnValue;
	}
	
	
	@Override
	public List<Map<String, Object>> getSalesPercentageByAccessType(Map<String, Object> params) {
		List<Map<String, Object>> result = new ArrayList<>();

		Set<String> keys = params.keySet();

		int numberOfRowsToRetrieve = DEFAULT_NUMBER_OF_ROWS_TO_RETRIEVE;

		if (keys.contains("NUMBER_OF_ROWS")) {
			numberOfRowsToRetrieve = Integer.parseInt(params.get("NUMBER_OF_ROWS").toString().trim());
		}
		
		result = objSalesHistoryDao.sqlGetSalesHistoryPercentageRecordsByAccessType(numberOfRowsToRetrieve);
		return result;
	}	

	private boolean containsAllWithValidValues(String Keys, Map<String, Object> params) {

		List<String> keyset = Arrays.asList(Keys.split(","));
		
		for (String string : keyset) {
			string = string.trim();
			if (params.get(string) == null) {
				return false;
			}
			
			String value = params.get(string).toString().trim();
			if ("".equals(value)) {
				return false;
			}
		}
		
		return true;
	}

	private SalesHistoryStripped transformToSalesHistoryStripped(Map<String, Object> map) {
		SalesHistoryStripped objSalesHistoryDO = new SalesHistoryStripped();
		
		if(!map.containsKey("DESIGN_RULE_ID")){
			objSalesHistoryDO.setSiteId(Long.parseLong(map.get("SITE_ID").toString()));
			objSalesHistoryDO.setLeadDesignId(Long.parseLong(map.get("LEAD_DESIGN_ID").toString()));
		}
		if(map.containsKey("DESIGN_RULE_ID")){
			objSalesHistoryDO.setRuleDesignId((Integer.parseInt(map.get("DESIGN_RULE_ID").toString())));
		}
		
		objSalesHistoryDO.setAccessArchitecture(
				map.get("ACCESS_ARCHITECTURE") != null ? map.get("ACCESS_ARCHITECTURE").toString() : "");
		objSalesHistoryDO.setAccessSpeed((String) map.get("ACCESS_SPEED"));
		objSalesHistoryDO.setCpeModel(map.get("CPE_MODEL") != null ? map.get("CPE_MODEL").toString() : "");
		objSalesHistoryDO.setEthernetHandoffInterface(map.get("ETHERNET_HANDOFF_INTERFACE_S") != null
				? map.get("ETHERNET_HANDOFF_INTERFACE_S").toString() : "");
		objSalesHistoryDO.setDesignType(map.get("DESIGN_TYPE") != null ? map.get("DESIGN_TYPE").toString() : "");
		objSalesHistoryDO.setPortSpeed(map.get("PORT_SPEED") != null ? map.get("PORT_SPEED").toString() : "");
		objSalesHistoryDO
				.setManagedRouter(map.get("MANAGED_ROUTER") != null ? map.get("MANAGED_ROUTER").toString() : "");
		objSalesHistoryDO.setRatePlan(map.get("RATE_PLAN") != null ? map.get("RATE_PLAN").toString() : "");
		objSalesHistoryDO
				.setTailTechnologyId(map.get("TAIL_TECHNOLOGY") != null ? map.get("TAIL_TECHNOLOGY").toString() : "");
		objSalesHistoryDO
				.setSiteNameAlias(map.get("SITE_NAME_ALIAS") != null ? map.get("SITE_NAME_ALIAS").toString() : "");
		objSalesHistoryDO.setMatchPercentage(
				map.get("MATCHING_ROW_PERCENTAGE") != null ? map.get("MATCHING_ROW_PERCENTAGE").toString() : "");

		objSalesHistoryDO.setMrc(
				map.get("MRC") != null ? map.get("MRC").toString() : "");
		objSalesHistoryDO.setNrc(
				map.get("NRC") != null ? map.get("NRC").toString() : "");
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
	
	@Override
	public SalesRulesMisExpDetail getSalesHistoryOrderDetailByDesignRuleId(int designRuleId) {
		SalesRulesMisExpDetailPK objSalesRulesMisExpDetailPk = new SalesRulesMisExpDetailPK();
		objSalesRulesMisExpDetailPk.setDesignRuleId(designRuleId);
		SalesRulesMisExpDetail objSalesRulesMisExpDetail = objSalesRulesMisExpRepository.findById(objSalesRulesMisExpDetailPk);
		return objSalesRulesMisExpDetail;
	}


}
