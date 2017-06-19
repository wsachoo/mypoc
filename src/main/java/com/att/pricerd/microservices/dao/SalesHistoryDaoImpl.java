package com.att.pricerd.microservices.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value = "SalesHistoryDao")
public class SalesHistoryDaoImpl implements SalesHistoryDao {
	
	@Value("${DB_TYPE}")
	private String dbType;
	
	static final Logger logger = LoggerFactory.getLogger(SalesHistoryDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private String sqlGetSalesHistoryDataByAccessTypeForOtherAccessType;

	@Autowired
	private String sqlGetSalesHistoryDataByAccessType;
	
	@Autowired
	private String sqlGetSalesHistoryDataByAccessTypeIndexWithinGroup;
	
	@Autowired
	private String sqlGetSalesHistoryPercentageRecordsByAccessType;

	@Autowired
	private String sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed;

	@Autowired
	private String sqlGetSalesHistoryDataByAccessTypeAndAccessSpeed;
	
	@Autowired
	private String sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeed;
	
	@Autowired
	private String sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeedAndPortSpeed;

	@Override
	public List<Map<String, Object>> getRecordsByAccessType(String accessType, int numberOfRows) {
		logger.info("Inside getRecordsByAccessType() method.");
		Map<String, Object> namedParameters = new HashMap<>();

		String sql = null;

		if ("Other".equals(accessType)) {
			sql = sqlGetSalesHistoryDataByAccessTypeForOtherAccessType;
		} else {
			sql = sqlGetSalesHistoryDataByAccessType;
		}

		namedParameters.put("NUMBER_OF_ROWS", numberOfRows);
		namedParameters.put("ACCESS_TYPE_ID", accessType);

		List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(sql, namedParameters);
		logger.info("Exiting getRecordsByAccessType() method.");
		return rows;
	}
	
	@Override
	public List<Map<String, Object>> getRecordsByAccessType(String accessType, Integer indexWithinGroup, int numberOfRows) {
		logger.info("Inside getRecordsByAccessType() method.");
		Map<String, Object> namedParameters = new HashMap<>();

		String sql = null;

		if ("Other".equals(accessType)) {
			sql = sqlGetSalesHistoryDataByAccessTypeForOtherAccessType;
		} else {
			sql = sqlGetSalesHistoryDataByAccessTypeIndexWithinGroup;
		}

		namedParameters.put("NUMBER_OF_ROWS", numberOfRows);
		namedParameters.put("ACCESS_TYPE_ID", accessType);
		namedParameters.put("INDEX_WITHIN_GROUP", indexWithinGroup);

		List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(sql, namedParameters);
		logger.info("Exiting getRecordsByAccessType() method.");
		return rows;
	}
	
	@Override
	public List<Map<String, Object>> getRecordsByAccessTypeAndAccessSpeed(String accessType, int accessSpeed,
			int numberOfRows) {
		logger.info("Inside getRecordsByAccessTypeAndAccessSpeed() method.");

		String sql = sqlGetSalesHistoryDataByAccessTypeAndAccessSpeed;

		Map<String, Object> namedParameters = new HashMap<>();
		namedParameters.put("NUMBER_OF_ROWS", numberOfRows);
		namedParameters.put("ACCESS_TYPE_ID", accessType);
		namedParameters.put("ACCESS_SPEED_ID", accessSpeed);

		List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(sql, namedParameters);

		logger.info("Exiting getRecordsByAccessTypeAndAccessSpeed() method.");
		return rows;
	}

	@Override
	public List<Map<String, Object>> sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed(String accessType,
			int accessSpeed, int portSpeed, int numberOfRows) {
		logger.info("Inside getRecordsByAccessTypeAndAccessSpeed() method.");

		String sql = sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed;

		Map<String, Object> namedParameters = new HashMap<>();
		namedParameters.put("NUMBER_OF_ROWS", numberOfRows);
		namedParameters.put("ACCESS_TYPE_ID", accessType);
		namedParameters.put("ACCESS_SPEED_ID", accessSpeed);
		namedParameters.put("PORT_SPEED_ID", portSpeed);

		List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(sql, namedParameters);

		logger.info("Exiting getRecordsByAccessTypeAndAccessSpeed() method.");
		return rows;
	}

	@Override
	public List<Map<String, Object>> sqlGetSalesHistoryPercentageRecordsByAccessType(int numberOfRows) {
		logger.info("Inside sqlGetSalesHistoryPercentageRecordsByAccessType() method.");

		String sql = sqlGetSalesHistoryPercentageRecordsByAccessType;

		Map<String, Object> namedParameters = new HashMap<>();
		namedParameters.put("NUMBER_OF_ROWS", numberOfRows);
		List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(sql, namedParameters);

		logger.info("Exiting sqlGetSalesHistoryPercentageRecordsByAccessType() method.");
		return rows;
	}

	@Override
	public List<Map<String, Object>> getRecordsByAccessTypeAndAccessSpeedFromMisExpRules(String accessType, int accessSpeed,
			int numberOfRows) {
		logger.info("Inside getRecordsByAccessTypeAndAccessSpeedFromMisExpRules() method.");

		String sql = sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeed;
		
		Map<String, Object> namedParameters = new HashMap<>();
		namedParameters.put("NUMBER_OF_ROWS", numberOfRows);
		namedParameters.put("ACCESS_TYPE_ID", accessType);
		if(("POSTGRESQL").equalsIgnoreCase(dbType)) {
			String accessSpeedAsString = Integer.toString(accessSpeed);
			namedParameters.put("ACCESS_SPEED_ID_STRING", accessSpeedAsString);
			namedParameters.put("ACCESS_SPEED_ID", accessSpeed);
		}else{
			namedParameters.put("ACCESS_SPEED_ID", accessSpeed);
		} 

		List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(sql, namedParameters);
		
		logger.info("Exiting getRecordsByAccessTypeAndAccessSpeedFromMisExpRules() method.");
		return rows;
	}
	
	@Override
	public List<Map<String, Object>> getRecordsByAccessTypeAccessSpeedPortSpeedFromMisExpRules(String accessType, int accessSpeed, int portSpeed,
			int numberOfRows) {
		logger.info("Inside getRecordsByAccessTypeAccessSpeedPortSpeedFromMisExpRules() method.");

		String sql = sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeedAndPortSpeed;
		
		Map<String, Object> namedParameters = new HashMap<>();
		namedParameters.put("NUMBER_OF_ROWS", numberOfRows);
		namedParameters.put("ACCESS_TYPE_ID", accessType);
		if(("POSTGRESQL").equalsIgnoreCase(dbType)) {
			namedParameters.put("ACCESS_SPEED_ID", String.valueOf(accessSpeed));
		}else{
			namedParameters.put("ACCESS_SPEED_ID", accessSpeed);
		}
		namedParameters.put("PORT_SPEED_ID", portSpeed);

		List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(sql, namedParameters);
		
		logger.info("Exiting getRecordsByAccessTypeAccessSpeedPortSpeedFromMisExpRules() method.");
		return rows;
	}

}
