package com.att.salesexpress.webapp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.salesexpress.webapp.db.DbService;
import com.att.salesexpress.webapp.pojos.AccessSpeedDO;
import com.att.salesexpress.webapp.pojos.PortSpeedDO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SalesExpressOperationServiceImpl implements SalesExpressOperationService {

	private static final Logger logger = LoggerFactory.getLogger(SalesExpressOperationServiceImpl.class);

	private static final Integer DEFAULT_SITE_ID = 1; // Using default value for
	// POC. During actual
	// implementation this
	// default will be taken
	// from request.

	@Autowired
	private DbService dbServiceImpl;

	/*
	 * @Autowired
	 * 
	 * @LoadBalanced protected RestTemplate restTemplate;
	 */

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
		String siteData = dbServiceImpl.getSiteMetaData(siteType);
		logger.debug("Site configuration metadata json received is {}", siteData);

		return siteData;
	}

	@Override
	public String getPortSpeedsByAccessData(String accessType, String accessSpeed) throws JsonProcessingException {
		List<PortSpeedDO> portSpeedList = dbServiceImpl.getPortSpeedsByAccessData(accessType, accessSpeed);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(portSpeedList);
		return jsonString;
	}
	
	@Override
	public String getAllAccessSpeeds() throws JsonProcessingException {
		Map<String, List<AccessSpeedDO>> result = dbServiceImpl.getAllAccessSpeeds();
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(result);
		return jsonString;
	}

	@Override
	public Integer getTransactionIdByUserIdSolutionId(String userId, Long solutionId) {
		Integer transactionId = dbServiceImpl.getTransactionIdByUserIdSolutionId(userId, solutionId);
		logger.debug("Transaction Id retrieved from database is {}", transactionId);
		return transactionId;
	}
	
	@Override
	public Map<String, Object> saveSiteConfigurationData(Map<String, Object> paramValues, Object userId,
			String strTransactionId, Long lSolutionId) throws JsonProcessingException, SQLException {
		Map<String, Object> returnValues = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		// String jsonString = mapper.writeValueAsString(paramValues);
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paramValues);
		Long transactionId = -1L;
		
		if (StringUtils.isBlank(strTransactionId)) {
			transactionId = dbServiceImpl.insertSiteConfigurationData(userId.toString(), lSolutionId, DEFAULT_SITE_ID,
					jsonString);
		} else {
			transactionId = Long.parseLong(strTransactionId);
			dbServiceImpl.updateSiteConfigurationData(transactionId, jsonString);
		}
		returnValues.put("status", "success");
		returnValues.put("transactionId", transactionId);
		return returnValues;
	}

	@Override
	public void updateServiceFeaturesData(String jsonString, Long solutionId, String userId) throws SQLException {
		dbServiceImpl.updateServiceFeaturesData(jsonString, solutionId, userId);
	}
	
	@Override
	public String getResultsData(Long solutionId) throws JSONException, IOException {
		Map<String, String> speedMap = dbServiceImpl.getAccessData(solutionId);

		String resultDataJSON = dbServiceImpl.getResultsData(speedMap.get("accessSpeed"), speedMap.get("portSpeed"));
		return resultDataJSON;
	}

	@Override
	public Long fetchDefaultSolutionIdByUserId(String userId) {
		return dbServiceImpl.fetchDefaultSolutionIdByUserId(userId);
	}	
	
	@Override
	public String getSiteInfoBySolutionId(Long solutionId) throws JsonProcessingException {
		Map<String, String> result = dbServiceImpl.getSiteInfoBySolutionId(solutionId);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(result);
		return jsonString;
	}
}
