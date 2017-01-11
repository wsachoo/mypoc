package com.att.salesexpress.webapp.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.att.salesexpress.webapp.db.DbService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SalesExpressMicroServiceCallerServiceImpl implements SalesExpressMicroServiceCallerService {

	private static final Logger logger = LoggerFactory.getLogger(SalesExpressMicroServiceCallerServiceImpl.class);

/*	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;
*/	
	@Autowired
	DbService dbServiceImpl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.att.salesexpress.poc.service.SalesExpressMicroServiceCallerInterface#
	 * getJsonMetaDataByUserIdSolutionId(java.lang.String, java.lang.Long)
	 */
	@Override
	public String getJsonMetaDataByUserIdSolutionId(String userId, Long solutionId) throws JsonProcessingException {
		Map<String, String> valuesMap = new HashMap<>();
		valuesMap.put("userId", userId);
		valuesMap.put("solutionId", solutionId.toString());

/*		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		String url = SalesExpressConstants.MICROSERVICE_EUREKA_REGISTERED_NAME
				+ sub.replace(SalesExpressConstants.MICROSERVICE_URL_USERID_SOLUTION_METADATA);
		logger.info("Invoking microservice with URL: " + url);

		String jsonString = restTemplate.getForObject(url, String.class);*/
		
		Map<String, Object> objUserDetail = dbServiceImpl.findUserDetailByUserIdSolutionId(userId, solutionId);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(objUserDetail);
		
		logger.debug("User site metadata json received is {}", jsonString);

		return jsonString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.att.salesexpress.poc.service.SalesExpressMicroServiceCallerInterface#
	 * getSiteConfigurationMetaDataBySiteType(java.lang.String)
	 */
	@Override
	public String getSiteConfigurationMetaDataBySiteType(String siteType) {
/*		Map<String, String> valuesMap = new HashMap<>();
		valuesMap.put("siteType", siteType);

		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		String url = SalesExpressConstants.MICROSERVICE_EUREKA_REGISTERED_NAME
				+ sub.replace(SalesExpressConstants.MICROSERVICE_URL_SITE_METADATA);
		logger.info("Invoking microservice with URL: " + url);

		String siteData = restTemplate.getForObject(url, String.class);
		*/
		String siteData = dbServiceImpl.getSiteMetaData(siteType);
		logger.debug("Site configuration metadata json received is {}", siteData);

		return siteData;
	}
}
