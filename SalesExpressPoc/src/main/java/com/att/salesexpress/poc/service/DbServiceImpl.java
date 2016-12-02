package com.att.salesexpress.poc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

@Repository
public class DbServiceImpl implements DbServiceInterface {
	
	static final Logger logger = LoggerFactory.getLogger(DbServiceImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getSiteDataByName(String name) {
		logger.info("inside getSiteDataByName method ");
		logger.info("name entered :" + name);
		String sql = "SELECT site_data FROM SiteDetail WHERE site_name = ?";
		String siteDataJson = (String) jdbcTemplate.queryForObject(sql, new Object[] { name }, String.class);
		return siteDataJson;
	}

	@Override
	public Map<String, Object> getSiteDetailEntityBySiteName(String sitename) {
		logger.info("inside getSiteDetailEntityBySiteName method ");
		logger.info("sitename entered : " + sitename);
		String sql = "SELECT * FROM SiteDetail WHERE site_name = ?";
		Map<String, Object> row = jdbcTemplate.queryForMap(sql, sitename);
		return row;
	}

	public long updateAccessTypeData(final long siteId, final String accessData) throws SQLException {
		logger.info("inside updateAccessTypeData method");
		logger.info("siteId entered :" + siteId + "accessData entered :" + accessData);
		String sqlSeq = "SELECT nextval('sitedetail_txn_seq') as trxn_seq";
		final Long seqNum = jdbcTemplate.queryForObject(sqlSeq, Long.class);
		System.out.println(seqNum);

		final String sql = "INSERT INTO sitedetail_transactions (id, site_id, access_data) VALUES (?, ?, to_json(?::json))";
		final PGobject jsonObject = new PGobject();
		jsonObject.setType("json");
		jsonObject.setValue(accessData);

		jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, seqNum);
				pstmt.setLong(2, siteId);
				pstmt.setObject(3, jsonObject);

				return pstmt;
			}
		});

		return seqNum;
	}

	@Override
	public String findUserDetailByUserId(String userId) {
		logger.info("inside findUserDetailByUserId method ");
		logger.info("userId :" + userId);
		String sql = "SELECT site_data FROM user_detail WHERE user_id = ?";
		String siteDataJson = (String) jdbcTemplate.queryForObject(sql, new Object[] { userId }, String.class);
		return siteDataJson;
	}

	@Override
	public Map<String, Object> findUserDetailByUserIdSolutionId(String userId, Integer solutionId) {
		logger.info("inside findUserDetailByUserIdSolutionId");
		logger.info("userId : " + userId);
		logger.info("solutionId :"+ solutionId);
		String sql = "SELECT * FROM user_detail WHERE user_id = ? and solution_id = ?";
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
