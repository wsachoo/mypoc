package com.att.salesexpress.microservices.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value = "SalesHistoryDao")
public class SalesHistoryDaoImpl implements SalesHistoryDao {
	static final Logger logger = LoggerFactory.getLogger(SalesHistoryDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private String sqlGetSalesHistoryDataByAccessTypeForOtherAccessType;

	@Autowired
	private String sqlGetSalesHistoryDataByAccessType;

	@Autowired
	private String sqlGetSalesHistoryPercentageRecordsByAccessType;

	@Autowired
	private String sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed;

	@Autowired
	private String sqlGetSalesHistoryDataByAccessTypeAndAccessSpeed;

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
}
