package com.att.salesexpress.webapp.service;

import java.util.List;
import java.util.Map;

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

@Service
public class SalesHistoryMicroServiceCallerServiceImpl implements SalesHistoryMicroServiceCallerService {

	private static final Logger logger = LoggerFactory.getLogger(SalesHistoryMicroServiceCallerServiceImpl.class);

	@Autowired
	protected RestTemplate restTemplate;

	@Override
	public List<Map<String, Object>> getSalesRecommendationFromHistory(Map<String, Object> paramValues) throws JsonProcessingException {
		String url = Constants.MICROSERVICE_SALES_HISTORY_DISCOVERY_NAME
				+ Constants.MICROSERVICE_SALES_HISTORY_RECOMMENDATION_URL;

		ObjectMapper mapperObj = new ObjectMapper();
		String requestJson = mapperObj.writeValueAsString(paramValues);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

		List<Map<String, Object>> answer = restTemplate.postForObject(url, entity, List.class);
		return answer;
	}
}
