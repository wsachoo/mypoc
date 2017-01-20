package com.att.salesexpress.webapp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SalesExpressOperationService {

	String getJsonMetaDataByUserIdSolutionId(String userId, Long solutionId) throws JsonProcessingException;

	String getSiteConfigurationMetaDataBySiteType(String siteType);
	
	public String getPortSpeedsByAccessData(String accessType, String accessSpeed) throws JsonProcessingException;

	Integer getTransactionIdByUserIdSolutionId(String userId, Long solutionId);

	Map<String, Object> saveSiteConfigurationData(Map<String, Object> paramValues, Object userId,
			String strTransactionId, Long lSolutionId) throws JsonProcessingException, SQLException, IOException;

	void updateServiceFeaturesData(Map<String, Object> paramValues, Long solutionId, String userId) throws SQLException, JsonProcessingException;

	String getResultsData(Long solutionId, Map<String, Object> paramValues) throws JSONException, IOException;

	String getAllAccessSpeeds() throws JsonProcessingException;

	Long fetchDefaultSolutionIdByUserId(String userId);

	String getSiteInfoBySolutionId(Long solutionId) throws JsonProcessingException;
	
	public void getResultDataByProc();
}