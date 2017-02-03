package com.att.salesexpress.webapp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.att.salesexpress.webapp.db.DbService;
import com.att.salesexpress.webapp.pojos.AccessSpeedDO;
import com.att.salesexpress.webapp.pojos.PortSpeedDO;
import com.att.salesexpress.webapp.pojos.UserDesignSelectionDO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SalesExpressOperationServiceImpl implements SalesExpressOperationService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired

	private DbService dbServiceImpl;
	
	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@Override
	@Transactional(readOnly = true)
	public String getJsonMetaDataByUserIdSolutionId(String userId, Long solutionId) throws JsonProcessingException {
		Map<String, String> valuesMap = new HashMap<>();
		valuesMap.put("userId", userId);
		valuesMap.put("solutionId", solutionId.toString());

		Map<String, Object> objUserDetail = dbServiceImpl.findUserDetailByUserIdSolutionId(userId, solutionId);
		String jsonString = jacksonObjectMapper.writeValueAsString(objUserDetail);

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
	@Transactional(readOnly = true)
	public String getSiteConfigurationMetaDataBySiteType(String siteType) {
		String siteData = dbServiceImpl.getSiteMetaData(siteType);
		return siteData;
	}

	@Override
	@Transactional(readOnly = true)
	public String getPortSpeedsByAccessData(String accessType, String accessSpeed) throws JsonProcessingException {
		List<PortSpeedDO> portSpeedList = dbServiceImpl.getPortSpeedsByAccessData(accessType, accessSpeed);
		String jsonString = jacksonObjectMapper.writeValueAsString(portSpeedList);
		return jsonString;
	}

	@Override
	@Transactional(readOnly = true)
	public String getAllAccessSpeeds() throws JsonProcessingException {
		Map<String, List<AccessSpeedDO>> result = dbServiceImpl.getAllAccessSpeeds();
		String jsonString = jacksonObjectMapper.writeValueAsString(result);
		return jsonString;
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getTransactionIdByUserIdSolutionId(String userId, Long solutionId) {
		Integer transactionId = dbServiceImpl.getTransactionIdByUserIdSolutionId(userId, solutionId);
		logger.debug("Transaction Id retrieved from database is {}", transactionId);
		return transactionId;
	}

	@Override
	@Transactional
	public Map<String, Object> saveSiteConfigurationData(Map<String, Object> paramValues, Object userId,
			String strTransactionId, Long lSolutionId) throws SQLException, IOException {
		Map<String, Object> returnValues = new HashMap<String, Object>();
		String jsonString = jacksonObjectMapper.writeValueAsString(paramValues);
		Long transactionId = -1L;

		UserDesignSelectionDO objUserDesignSelectionDO = jacksonObjectMapper.readValue(jsonString,
				new TypeReference<UserDesignSelectionDO>(){});

		if (StringUtils.isBlank(strTransactionId)) {
			transactionId = dbServiceImpl.insertSiteConfigurationData(userId.toString(), lSolutionId, jsonString);
			dbServiceImpl.insertSiteConfigurationDataInRelational(objUserDesignSelectionDO);
		} else {
			transactionId = Long.parseLong(strTransactionId);
			dbServiceImpl.updateSiteConfigurationData(transactionId, jsonString);
			dbServiceImpl.removePreviousSiteConfigurationDataInRelational(objUserDesignSelectionDO);
			dbServiceImpl.insertSiteConfigurationDataInRelational(objUserDesignSelectionDO);
		}
		returnValues.put("status", "success");
		returnValues.put("transactionId", transactionId);
		return returnValues;
	}

	@Override
	@Transactional
	public void updateServiceFeaturesData(Map<String, Object> paramValues, Long solutionId, String userId) throws SQLException, JsonProcessingException {
		String jsonString = jacksonObjectMapper.writeValueAsString(paramValues);
		dbServiceImpl.updateServiceFeaturesData(jsonString, solutionId, userId);
	}

	@Override
	@Transactional(readOnly = true)
	public Long fetchDefaultSolutionIdByUserId(String userId) {
		return dbServiceImpl.fetchDefaultSolutionIdByUserId(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public String getSiteInfoBySolutionId(Long solutionId) throws JsonProcessingException {
		Map<String, String> result = dbServiceImpl.getSiteInfoBySolutionId(solutionId);
		String jsonString = jacksonObjectMapper.writeValueAsString(result);
		return jsonString;
	}

	@Override
	@Transactional(readOnly = true)
	public String getResultsData(Long solutionId, Map<String, Object> paramValues) throws JSONException, IOException {
		String portSpeed = (String) paramValues.get("portSpeed");
		String accessSpeed = (String) paramValues.get("accessSpeed");
		List<Map<String, Object>> finalResultList = dbServiceImpl.getResultsData(solutionId, accessSpeed, portSpeed);
		
/*		JSONArray portSpeedArray = new JSONArray(portSpeed);
		JSONArray accessSpeedArray = new JSONArray(accessSpeed);
		List<Map<String, Object>> resultList = null;
		List<Map<String, Object>> finalResultList = new ArrayList<>(); 
				
		int noOfSites = portSpeedArray.length();
		for (int i = 0; i < noOfSites; i++) {
			portSpeed = portSpeedArray.getString(i);
			accessSpeed = accessSpeedArray.getString(i);
			resultList = dbServiceImpl.getResultsData(solutionId, accessSpeed, portSpeed);
			finalResultList.addAll(resultList);
		}
*/		
		return jacksonObjectMapper.writeValueAsString(finalResultList);
	}

	@Override
	@Transactional(readOnly = true)
	public void getResultDataByProc() {
		
		dbServiceImpl.getFinalResultDataByProc();
		
	}
	

}
