package com.att.salesexpress.poc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author sw088d initial version
 *
 */
@Repository
public class DbServiceImpl implements DbServiceInterface {

	static final Logger logger = LoggerFactory.getLogger(DbServiceImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getSiteDataByName(String name) {
		logger.info("Inside getSiteDataByName() method.");
		logger.info("Site Name:" + name);
		String sql = "SELECT site_data FROM SiteDetail WHERE site_name = ?";
		String siteDataJson = (String) jdbcTemplate.queryForObject(sql, new Object[] { name }, String.class);
		return siteDataJson;
	}

	@Override
	public Map<String, Object> getSiteDetailEntityBySiteName(String sitename) {
		logger.info("Inside getSiteDetailEntityBySiteName() method.");
		logger.info("sitename: " + sitename);
		String sql = "SELECT * FROM SiteDetail WHERE site_name = ?";
		Map<String, Object> row = jdbcTemplate.queryForMap(sql, sitename);
		return row;
	}

	public long insertSiteConfigurationData(final String userId, final long solutionId, final Integer siteId,
			final String accessData) throws SQLException {
		logger.info("Inside updateAccessTypeData() method.");

		String sqlSeq = "SELECT nextval('sitedetail_txn_seq') as trxn_seq";
		final Long seqNum = jdbcTemplate.queryForObject(sqlSeq, Long.class);

		final String sql = "INSERT INTO sitedetail_transactions (id, user_id, solution_id, site_id, access_data) VALUES (?, ?, ?, ?, to_json(?::json))";
		final PGobject jsonObject = new PGobject();
		jsonObject.setType("json");
		jsonObject.setValue(accessData);

		jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, seqNum);
				pstmt.setString(2, userId);
				pstmt.setLong(3, solutionId);
				pstmt.setInt(4, siteId);
				pstmt.setObject(5, jsonObject);

				return pstmt;
			}
		});

		return seqNum;
	}

	@Override
	public String findUserDetailByUserId(String userId) {
		logger.info("Inside findUserDetailByUserId() method.");
		logger.info("userId :" + userId);
		String sql = "SELECT site_data FROM user_detail WHERE user_id = ?";
		String siteDataJson = (String) jdbcTemplate.queryForObject(sql, new Object[] { userId }, String.class);
		return siteDataJson;
	}

	@Override
	public Integer getTransactionIdByUserIdSolutionId(String userId, Long solutionId) {
		String sql = "SELECT id FROM sitedetail_transactions WHERE user_id = ? and solution_id = ?";
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
		final PGobject jsonObject = new PGobject();
		jsonObject.setType("json");
		jsonObject.setValue(jsonString);

		String sqlUpdate = "UPDATE sitedetail_transactions SET access_data = ? WHERE id = ?";
		int iReturnVal = jdbcTemplate.update(sqlUpdate, jsonObject, transactionId);
		logger.info("Return value after update is {}", iReturnVal);
	}
}
