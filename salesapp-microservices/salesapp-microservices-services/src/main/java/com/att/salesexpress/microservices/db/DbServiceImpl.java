package com.att.salesexpress.microservices.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value = "DbServiceImpl.microservice")
public class DbServiceImpl implements DbService {
	static final Logger logger = LoggerFactory.getLogger(DbServiceImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String getSiteMetaData(String siteType) {
		logger.info("Inside getSiteMetaData() method with siteType {}", siteType);
		String sql = "select SITE_DATA from SLEXP_SITE_CONFIG where SITE_NAME = ?";
		String siteDataJson = (String) jdbcTemplate.queryForObject(sql, new Object[] { siteType }, String.class);
		return siteDataJson;
	}

	@Override
	public Map<String, Object> findUserDetailByUserIdSolutionId(String userId, Long solutionId) {
		logger.info("Inside findUserDetailByUserIdSolutionId() method.");
		String sql = "select * from SLEXP_USER_DETAIL where USER_ID = ? and SOLUTION_ID = ?";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { userId, solutionId });

		Map<String, Object> returnValues = new HashMap<String, Object>();

		returnValues.put("user_id", userId);
		returnValues.put("solution_id", solutionId);
		returnValues.put("siteAddresses", new ArrayList<Map<String, Object>>());

		for (Map<String, Object> map : rows) {
			Map<String, Object> siteMap = new HashMap<String, Object>();
			siteMap.put("site_id", map.get("site_id").toString());
			siteMap.put("site_addr", map.get("site_addr").toString());
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> siteAddresses = (List<Map<String, Object>>) returnValues.get("siteAddresses");
			siteAddresses.add(siteMap);
		}
		return returnValues;
	}
}
