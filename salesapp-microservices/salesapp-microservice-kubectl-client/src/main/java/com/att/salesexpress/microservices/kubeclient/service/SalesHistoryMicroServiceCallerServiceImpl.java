package com.att.salesexpress.microservices.kubeclient.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.att.salesexpress.microservices.kubeclient.config.Dme2ClientWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SalesHistoryMicroServiceCallerServiceImpl implements SalesHistoryMicroServiceCallerService {

	private static final Logger logger = LoggerFactory.getLogger(SalesHistoryMicroServiceCallerServiceImpl.class);

	@Value("${salesappservice_context_GetSalesPercentageByAccessType}")
	private String urlGetSalesPercentageByAccessType;

	@Value("${salesappservice_context_getRecommendationBasedOnSalesHistory}")
	private String urlGetRecommendationBasedOnSalesHistory;
	
	@Value("${salesappservice_context_getSalesHistoryOrderDetailBySiteIdLeadDesignId}")
	private String urlGetSalesHistoryOrderDetailBySiteIdLeadDesignId;
	
	@Value("${salesappservice_GRMEndPoint}")
	private String clientUri;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Dme2ClientWrapper salesDME2Client;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSalesRecommendationFromHistory(Map<String, Object> paramValues) {
		logger.debug("Inside getSalesRecommendationFromHistory() method.");
		Map<String, Object> returnValue = new HashMap<>();
		List<Map<String, Object>> result = new ArrayList<>();

		try {
			salesDME2Client.setGrmEntryUrl(clientUri);
			salesDME2Client.setContextPath(urlGetRecommendationBasedOnSalesHistory);

			Map<String, String> queryParams = new HashMap<>();
			for (Entry<String, Object> entry : paramValues.entrySet()) {
				queryParams.put(entry.getKey(), entry.getValue().toString());
			}

			salesDME2Client.setQueryParams(queryParams);
			String strResult = salesDME2Client.call();
			try {
				result = mapper.readValue(strResult, List.class);
			} catch (IOException e) {
				e.printStackTrace();
			}

			returnValue.put("DATA", result);
			returnValue.put("STATUS", "SUCCESS");

			logger.debug("Exiting getSalesRecommendationFromHistory() method.");
			return returnValue;
		} catch (RuntimeException ex) {
			logger.error("Some exception occurred while calling micro service: {}", ExceptionUtils.getStackTrace(ex));
			return circuitBreakerGetSalesRecommendationFromHistory(paramValues);
		}
	}

	public Map<String, Object> circuitBreakerGetSalesRecommendationFromHistory(Map<String, Object> paramValues) {
		logger.info("Inside circuitBreakerGetSalesRecommendationFromHistory() method.");
		Map<String, Object> returnValue = new HashMap<>();
		List<Map<String, Object>> result = new ArrayList<>();
		returnValue.put("STATUS", "FAILURE");
		returnValue.put("ERROR_DESC", "There was some problem while getting Sales History data");
		returnValue.put("DATA", result);
		logger.info("Exiting circuitBreakerGetSalesRecommendationFromHistory() method.");

		return returnValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSalesPercentageByAccessType(Map<String, Object> paramValues) {
		logger.debug("Inside getSalesRecommendationFromHistory() method.");
		Map<String, Object> returnValue = new HashMap<>();
		List<Map<String, Object>> result = new ArrayList<>();

		try {
			salesDME2Client.setGrmEntryUrl(clientUri);
			salesDME2Client.setContextPath(urlGetSalesPercentageByAccessType);

			Map<String, String> queryParams = new HashMap<>();
			for (Entry<String, Object> entry : paramValues.entrySet()) {
				queryParams.put(entry.getKey(), entry.getValue().toString());
			}

			salesDME2Client.setQueryParams(queryParams);
			String strResult = salesDME2Client.call();
			try {
				result = mapper.readValue(strResult, List.class);
			} catch (IOException e) {
				e.printStackTrace();
			}

			returnValue.put("DATA", result);
			returnValue.put("STATUS", "SUCCESS");

			logger.debug("Exiting getSalesRecommendationFromHistory() method.");
			return returnValue;
		} catch (RuntimeException ex) {
			logger.error("Some exception occurred while calling micro service: {}", ExceptionUtils.getStackTrace(ex));
			return circuitBreakerGetSalesPercentageByAccessType(paramValues);
		}
	}

	public Map<String, Object> circuitBreakerGetSalesPercentageByAccessType(Map<String, Object> paramValues) {
		logger.info("Inside circuitBreakerGetSalesPercentageByAccessType() method.");
		Map<String, Object> returnValue = new HashMap<>();
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Object> tmpEthernet = new HashMap<>();
		tmpEthernet.put("ACCESS_TYPE_ID", "ETHERNET");
		tmpEthernet.put("PERCENTAGE", "% Not Available");

		Map<String, Object> tmpPrivateLine = new HashMap<>();
		tmpPrivateLine.put("ACCESS_TYPE_ID", "Private Line");
		tmpPrivateLine.put("PERCENTAGE", "% Not Available");
		result.add(tmpEthernet);
		result.add(tmpPrivateLine);

		returnValue.put("STATUS", "FAILURE");
		returnValue.put("ERROR_DESC",
				"There was some problem while getting Sales History Percentage information by access type.");
		returnValue.put("DATA", result);
		logger.info("Exiting circuitBreakerGetSalesPercentageByAccessType() method.");

		return returnValue;
	}

	@Override
	public String getSalesHistoryOrderDetailBySiteIdLeadDesignId(Long siteId, Long leadDesignId) {
		logger.debug("Inside getSalesHistoryOrderDetailBySiteIdLeadDesignId() method.");
		String returnValue = null;

		try {
			salesDME2Client.setGrmEntryUrl(clientUri);
			salesDME2Client.setContextPath(urlGetSalesHistoryOrderDetailBySiteIdLeadDesignId);

			Map<String, String> queryParams = new HashMap<>();
			queryParams.put("leadDesignId", leadDesignId.toString());
			queryParams.put("siteId", siteId.toString());
			
			salesDME2Client.setQueryParams(queryParams);
			returnValue = salesDME2Client.call();

			logger.debug("Exiting getSalesHistoryOrderDetailBySiteIdLeadDesignId() method with returnValue: {}", returnValue);
			return returnValue;
		} catch (RuntimeException ex) {
			logger.error("Some exception occurred while calling micro service: {}", ExceptionUtils.getStackTrace(ex));
			return returnValue;
		}
	}
}
