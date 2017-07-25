package com.att.salesexpress.microservices.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.salesexpress.microservices.dao.SalesHistoryDao;
import com.att.salesexpress.microservices.dao.SalesHistoryDaoImpl;
import com.att.salesexpress.microservices.dao.SalesHistoryDetailRepository;
import com.att.salesexpress.microservices.dao.SalesRulesMisExpRepository;
import com.att.salesexpress.microservices.dao.SalesUcpeRuleRepository;
import com.att.salesexpress.microservices.dao.SalesVnfRuleRepository;
import com.att.salesexpress.microservices.entity.SalesHistoryDetail;
import com.att.salesexpress.microservices.entity.SalesHistoryDetailPK;
import com.att.salesexpress.microservices.entity.SalesHistoryStripped;
import com.att.salesexpress.microservices.entity.SalesRulesMisExpDetail;
import com.att.salesexpress.microservices.entity.SalesRulesMisExpDetailPK;
import com.att.salesexpress.microservices.entity.SalesUcpeRule;
import com.att.salesexpress.microservices.entity.SalesVnfRule;

@Service
public class SalesHistoryServiceImpl implements SalesHistoryService {

	static final Logger logger = LoggerFactory.getLogger(SalesHistoryServiceImpl.class);
	
	private static final int DEFAULT_NUMBER_OF_ROWS_TO_RETRIEVE = 10;

	private static final int NUMBER_OF_PRODUCTS_TO_DISPLAY = 10;

	DecimalFormat df2 = new DecimalFormat(".00");

	@Autowired
	private SalesHistoryDao objSalesHistoryDao;

	@Autowired
	SalesHistoryDetailRepository objSalesHistoryDetailRepository;

	@Autowired
	SalesRulesMisExpRepository objSalesRulesMisExpRepository;

	@Autowired
	SalesVnfRuleRepository objSalesVnfRuleRepository;

	@Autowired
	SalesUcpeRuleRepository objSalesUcpeRuleRepository;

	@Override
	public List<Object> getRecommendationBasedOnSalesHistory(Map<String, Object> params) {
		logger.info("Entered getRecommendationBasedOnSalesHistory() method");
		List<Map<String, Object>> result = new ArrayList<>();
		Set<String> keys = params.keySet();

		int numberOfRowsToRetrieve = DEFAULT_NUMBER_OF_ROWS_TO_RETRIEVE;

		if (keys.contains("NUMBER_OF_ROWS")) {
			numberOfRowsToRetrieve = Integer.parseInt(params.get("NUMBER_OF_ROWS").toString().trim());
		}

		if (containsAllWithValidValues("ACCESS_TYPE_ID,ACCESS_SPEED_ID,PORT_SPEED_ID", params)) {
			String accessType = params.get("ACCESS_TYPE_ID").toString().trim();
			Integer accessSpeed = Integer.parseInt(params.get("ACCESS_SPEED_ID").toString().trim());
			Integer portSpeed = Integer.parseInt(params.get("PORT_SPEED_ID").toString().trim());

			result = objSalesHistoryDao.sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed(accessType,
					accessSpeed, portSpeed, numberOfRowsToRetrieve);
			if (result.isEmpty()) {
				result = objSalesHistoryDao.getRecordsByAccessTypeAccessSpeedPortSpeedFromMisExpRules(accessType,
						accessSpeed, portSpeed, numberOfRowsToRetrieve);
			}
		} else if (containsAllWithValidValues("ACCESS_TYPE_ID,ACCESS_SPEED_ID", params)) {
			String accessType = params.get("ACCESS_TYPE_ID").toString().trim();
			Integer accessSpeed = Integer.parseInt(params.get("ACCESS_SPEED_ID").toString().trim());
			result = objSalesHistoryDao.getRecordsByAccessTypeAndAccessSpeed(accessType, accessSpeed,
					numberOfRowsToRetrieve);
			if (result.isEmpty()) {
				result = objSalesHistoryDao.getRecordsByAccessTypeAndAccessSpeedFromMisExpRules(accessType, accessSpeed,
						numberOfRowsToRetrieve);
			}
		//###################################################################################################################
		//START: Following 2 conditions are being called on the recommendations page
		} else if (containsAllWithValidValues("ACCESS_TYPE_ID,indexWithinGroup", params)) {
			String accessType = params.get("ACCESS_TYPE_ID").toString().trim();
			Integer indexWithinGroup = Integer.parseInt(params.get("indexWithinGroup").toString().trim());
			result = objSalesHistoryDao.getRecordsByAccessType(accessType, indexWithinGroup, numberOfRowsToRetrieve);
		} else if (containsAllWithValidValues("ACCESS_TYPE_ID", params)) {
			String accessType = params.get("ACCESS_TYPE_ID").toString().trim();
			result = objSalesHistoryDao.getRecordsByAccessType(accessType, numberOfRowsToRetrieve);
		}
		//END: Following 2 conditions are being called on the recommendations page
		//###################################################################################################################
		List<Object> salesHistoryStrippedList = new ArrayList<>();

		for (Map<String, Object> map : result) {
			salesHistoryStrippedList.add(transformToSalesHistoryStripped(map));
		}
		
		List<Object> returnValue = shuffleAndArrangeProducts(salesHistoryStrippedList, objSalesHistoryDao.getRecommendedVnfDevices(), objSalesHistoryDao.getRecommendedUcpeDevices());

		logger.info("Exiting getRecommendationBasedOnSalesHistory() method");
		return returnValue;
		//return salesHistoryStrippedList;
	}

	/**
	 * Arranges the products in proper order fir displaying on recommendation page.
	 * 
	 * @param salesHistoryStrippedList
	 * @param recommendedVnfDevices
	 * @param recommendedUcpeDevices
	 * @return
	 */
	private List<Object> shuffleAndArrangeProducts(List<Object> salesHistoryStrippedList,
			List<Object> recommendedVnfDevices, List<Object> recommendedUcpeDevices) {
		logger.info("Entered shuffleAndArrangeProducts() method");
		List<Object> returnValue = new ArrayList<>();
		
		int i = 0;
		while (returnValue.size() <= NUMBER_OF_PRODUCTS_TO_DISPLAY) {
			if (salesHistoryStrippedList.size() > i) {
				returnValue.add(salesHistoryStrippedList.get(i));
			}
			
			if (recommendedVnfDevices.size() > i && (i % 2 == 0)) {
				returnValue.add(recommendedVnfDevices.get(i));
			}
			
			if (recommendedUcpeDevices.size() > i && (i % 2 == 1)) {
				returnValue.add(recommendedUcpeDevices.get(i));
			}
			
			i++;
		}
		
		logger.info("Exiting shuffleAndArrangeProducts() method");
		return returnValue;
	}

	@Override
	public List<Map<String, Object>> getSalesPercentageByAccessType(Map<String, Object> params) {
		logger.info("Entered getSalesPercentageByAccessType() method");
		List<Map<String, Object>> result = new ArrayList<>();

		Set<String> keys = params.keySet();

		int numberOfRowsToRetrieve = DEFAULT_NUMBER_OF_ROWS_TO_RETRIEVE;

		if (keys.contains("NUMBER_OF_ROWS")) {
			numberOfRowsToRetrieve = Integer.parseInt(params.get("NUMBER_OF_ROWS").toString().trim());
		}

		result = objSalesHistoryDao.sqlGetSalesHistoryPercentageRecordsByAccessType(numberOfRowsToRetrieve);
		logger.info("Exiting getSalesPercentageByAccessType() method");
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

		if (!map.containsKey("DESIGN_RULE_ID")) {
			objSalesHistoryDO.setSiteId(Long.parseLong(map.get("SITE_ID").toString()));
			objSalesHistoryDO.setLeadDesignId(Long.parseLong(map.get("LEAD_DESIGN_ID").toString()));
		}
		if (map.containsKey("DESIGN_RULE_ID")) {
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

		objSalesHistoryDO
				.setMrc(map.get("MRC") != null ? df2.format(Double.parseDouble(map.get("MRC").toString())) : "");
		objSalesHistoryDO
				.setNrc(map.get("NRC") != null ? df2.format(Double.parseDouble(map.get("NRC").toString())) : "");

		objSalesHistoryDO.setBundleCd(map.get("BUNDLE_CD") != null ? map.get("BUNDLE_CD").toString() : "");
		objSalesHistoryDO.setTerm(map.get("TERM") != null ? map.get("TERM").toString() : "");

		return objSalesHistoryDO;
	}

	@Override
	public SalesHistoryDetail getSalesHistoryOrderDetailBySiteIdLeadDesignId(Long siteId, Long leadDesignId) {
		logger.info("Entered getSalesHistoryOrderDetailBySiteIdLeadDesignId() method");
		SalesHistoryDetailPK objSalesHistoryDetailPK = new SalesHistoryDetailPK();
		objSalesHistoryDetailPK.setSiteId(siteId);
		objSalesHistoryDetailPK.setLeadDesignId(leadDesignId);

		SalesHistoryDetail objSalesHistoryDetail = objSalesHistoryDetailRepository.findById(objSalesHistoryDetailPK);

		String accessSpeedId = objSalesHistoryDetail.getAccessSpeedId().toString();

		List<String> portSpeedList = objSalesRulesMisExpRepository.findDistinctPortSpeedByAccessSpeedId(accessSpeedId);
		objSalesHistoryDetail.setPortSpeedList(portSpeedList);
		
		logger.info("Exiting getSalesHistoryOrderDetailBySiteIdLeadDesignId() method");
		return objSalesHistoryDetail;
	}

	@Override
	public SalesRulesMisExpDetail getSalesHistoryOrderDetailByDesignRuleId(int designRuleId) {
		logger.info("Entered getSalesHistoryOrderDetailByDesignRuleId() method");
		SalesRulesMisExpDetailPK objSalesRulesMisExpDetailPk = new SalesRulesMisExpDetailPK();
		objSalesRulesMisExpDetailPk.setDesignRuleId(designRuleId);
		SalesRulesMisExpDetail objSalesRulesMisExpDetail = objSalesRulesMisExpRepository
				.findById(objSalesRulesMisExpDetailPk);

		String accessSpeedId = objSalesRulesMisExpDetail.getAccessSpeedId().toString();

		List<String> portSpeedList = objSalesRulesMisExpRepository.findDistinctPortSpeedByAccessSpeedId(accessSpeedId);
		objSalesRulesMisExpDetail.setPortSpeedList(portSpeedList);

		logger.info("Exiting getSalesHistoryOrderDetailByDesignRuleId() method");
		return objSalesRulesMisExpDetail;
	}

	@Override
	public List<Object> getRecommendedVnfDevices() {
		return objSalesHistoryDao.getRecommendedVnfDevices();
	}

	@Override
	public SalesVnfRule getRecommendedVnfDeviceByRuleId(BigDecimal ruleId) {
		SalesVnfRule objSalesVnfRule = objSalesVnfRuleRepository.findByRuleId(ruleId);
		return objSalesVnfRule;
	}

	@Override
	public List<Object> getRecommendedUcpeDevices() {
		return objSalesHistoryDao.getRecommendedUcpeDevices();
	}

	@Override
	public SalesUcpeRule getRecommendedUcpeDeviceByRuleId(Long ruleId) {
		SalesUcpeRule objSalesUcpeRule = objSalesUcpeRuleRepository.findByRuleId(ruleId);
		return objSalesUcpeRule;
	}
}
