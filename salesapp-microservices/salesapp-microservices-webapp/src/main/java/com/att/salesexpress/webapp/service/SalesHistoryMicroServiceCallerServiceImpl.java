package com.att.salesexpress.webapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.att.salesexpress.webapp.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class SalesHistoryMicroServiceCallerServiceImpl implements SalesHistoryMicroServiceCallerService {

	private static final Logger logger = LoggerFactory.getLogger(SalesHistoryMicroServiceCallerServiceImpl.class);

	@Autowired
	protected RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	@Override
	@HystrixCommand(fallbackMethod = "circuitBreakerGetSalesRecommendationFromHistory")
	public Map<String, Object> getSalesRecommendationFromHistory(Map<String, Object> paramValues) {
		logger.debug("Inside getSalesRecommendationFromHistory() method.");
		String url = Constants.MICROSERVICE_SALES_HISTORY_DISCOVERY_NAME
				+ Constants.MICROSERVICE_SALES_HISTORY_RECOMMENDATION_URL;
		Map<String, Object> returnValue = new HashMap<>();
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			ObjectMapper mapperObj = new ObjectMapper();
			String requestJson = mapperObj.writeValueAsString(paramValues);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

			result = restTemplate.postForObject(url, entity, List.class);
			
			returnValue.put("DATA", result);
			returnValue.put("STATUS", "SUCCESS");
			
			logger.debug("Exiting getSalesRecommendationFromHistory() method.");
			return returnValue;
		} 
		catch (RuntimeException ex) {
			logger.error("Some exception occurred while calling micro service: {}", ExceptionUtils.getStackTrace(ex));
			throw ex;
		}
		catch (JsonProcessingException jpe) {
			logger.error("Some exception occurred while parsing request data: {}", ExceptionUtils.getStackTrace(jpe));
			throw new RuntimeException(jpe);
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
}

