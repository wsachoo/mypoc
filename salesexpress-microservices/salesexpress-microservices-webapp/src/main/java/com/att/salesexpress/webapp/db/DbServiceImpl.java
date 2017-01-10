package com.att.salesexpress.webapp.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author sw088d initial version
 *
 */
@Repository
public class DbServiceImpl implements DbService {

	static final Logger logger = LoggerFactory.getLogger(DbServiceImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public long insertSiteConfigurationData(final String userId, final long solutionId, final Integer siteId,
			final String accessData) throws SQLException {
		logger.info("Inside updateAccessTypeData() method.");

		String sqlSeq = "SELECT SLEXP_SITEDETAIL_TX_SEQ.nextval as trxn_seq FROM dual";
		final Long seqNum = jdbcTemplate.queryForObject(sqlSeq, Long.class);
		
		final String sql = "INSERT INTO SLEXP_SITEDETAIL_TX (ID, USER_ID, SOLUTION_ID, SITE_ID, ACCESS_DATA) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, seqNum);
				pstmt.setString(2, userId);
				pstmt.setLong(3, solutionId);
				pstmt.setInt(4, siteId);
				pstmt.setString(5, accessData);

				return pstmt;
			}
		});

		return seqNum;
	}

	@Override
	public Integer getTransactionIdByUserIdSolutionId(String userId, Long solutionId) {
		String sql = "SELECT ID FROM SLEXP_SITEDETAIL_TX WHERE USER_ID = ? and SOLUTION_ID = ?";
		try {
			String strTransactionId = (String) jdbcTemplate.queryForObject(sql, new Object[] { userId, solutionId },
					String.class);
			return Integer.parseInt(strTransactionId);
		} catch (EmptyResultDataAccessException erdaex) {
			logger.info("Transaction not found in database for userId: {}, solutionId: {}", userId, solutionId);
			return null;
		}
	}

	@Override
	public void updateSiteConfigurationData(long transactionId, String jsonString) throws SQLException {
		logger.info("Inside updateSiteConfigurationData() method.");
		String sqlUpdate = "UPDATE SLEXP_SITEDETAIL_TX SET ACCESS_DATA = ? WHERE ID = ?";
		int iReturnVal = jdbcTemplate.update(sqlUpdate, jsonString, transactionId);
		logger.info("Return value after update is {}", iReturnVal);
	}

	public List getServices() {
		logger.info("Inside getServices method");
		String sql = "select SERVICE_NAME from SLEXP_SERVICE_CONFIG";
		List<Map> serviceList = (List) jdbcTemplate.queryForList(sql);
		for (Map x : serviceList) {
			logger.info("" + x.get("service_name"));

		}
		logger.info("" + serviceList);
		return serviceList;
	}

	@Override
	public void updateServiceFeaturesData(String jsonString, Long solutionId, String userId) throws SQLException {
		logger.info("Inside updateServiceFeaturesData");
		String sqlUpdate = "update SLEXP_SITEDETAIL_TX set SERVICE_FEATURE_DATA = ? where SOLUTION_ID = ? and USER_ID = ?";
		int iReturnVal = jdbcTemplate.update(sqlUpdate, jsonString, solutionId, userId);
		logger.info("Return value after service and features update : " + iReturnVal);

	}

	public Map<String, String> getAccessData(Long solutionId) throws IOException, JSONException {
		logger.info("Inside getAccessData() method.");
		logger.info("solutionId :" + solutionId);
		Map<String, String> returnMap = new HashMap<String, String>();
		String sqlQuery = "select ACCESS_DATA from SLEXP_SITEDETAIL_TX where SOLUTION_ID =?";
		String accessDataJSON = (String) jdbcTemplate.queryForObject(sqlQuery, new Object[] { solutionId },
				String.class);
		String accessSpeed = null;
		String portSpeed = null;

		JSONObject json = new JSONObject(accessDataJSON);

		if (null != accessDataJSON && accessDataJSON.contains("headQuarters")) {
			JSONObject jsonHeadQuarters = json.getJSONObject("headQuarters");
			JSONObject accessConfig = jsonHeadQuarters.getJSONObject("accessConfig");
			JSONObject portConfig = jsonHeadQuarters.getJSONObject("portConfig");
			accessSpeed = accessConfig.getString("sliderSpeedValue");
			portSpeed = portConfig.getString("sliderPortSpeedValue");
		} else if (null != accessDataJSON && accessDataJSON.contains("accountReceivables")) {
			JSONObject jsonAccountReceivables = json.getJSONObject("accountReceivables");
			JSONObject accessConfig = jsonAccountReceivables.getJSONObject("accessConfig");
			JSONObject portConfig = jsonAccountReceivables.getJSONObject("portConfig");
			accessSpeed = accessConfig.getString("sliderSpeedValue");
			portSpeed = portConfig.getString("sliderPortSpeedValue");
		} else if (null != accessDataJSON && accessDataJSON.contains("distributionCenter")) {
			JSONObject jsonDistributionCenter = json.getJSONObject("distributionCenter");
			JSONObject accessConfig = jsonDistributionCenter.getJSONObject("accessConfig");
			JSONObject portConfig = jsonDistributionCenter.getJSONObject("portConfig");
			accessSpeed = accessConfig.getString("sliderSpeedValue");
			portSpeed = portConfig.getString("sliderPortSpeedValue");
		}

		logger.info("accessSpeedValue for HeadQuarter is : " + accessSpeed);
		logger.info("portSpeedValue for HeadQUarter is :" + portSpeed);

		/*
		 * ObjectMapper mapper = new ObjectMapper(); AccessConfig
		 * accessConfigFromJSON = mapper.readValue(json, AccessConfig.class);
		 */
		returnMap.put("accessSpeed", accessSpeed.toString());
		returnMap.put("portSpeed", portSpeed.toString());
		return returnMap;

	}

	public String getResultsData(String accessSpeed, String portSpeed) throws JsonProcessingException, JSONException {

		logger.info("getAccessData");
		String sql = "select * from SLEXP_RESULTS_REF where ACCESS_SPEED = ? and PORT_SPEED = ? order by MRC asc, NRC asc";
		List resultList = (List) jdbcTemplate.queryForList(sql, accessSpeed, portSpeed);

		// return resultList;
		ObjectMapper mapper = new ObjectMapper();
		String JSONString = mapper.writeValueAsString(resultList);

		String finalJsonString = "{packageData : " + JSONString + " }";

		/* return JSONString; */
		return finalJsonString;
	}
}
