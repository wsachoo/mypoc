package com.att.salesexpress.webapp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.att.salesexpress.webapp.pojos.AccessSpeedDO;
import com.att.salesexpress.webapp.pojos.PortSpeedDO;
import com.att.salesexpress.webapp.pojos.UserDesignSelectionDO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author sw088d initial version
 *
 */
@Repository
public class DbServiceImpl implements DbService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("hikariOraJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional(readOnly = true)
	public String getSiteMetaData(String siteType) {
		logger.debug("inside getSiteMetaData debug mode");
		logger.info("Inside getSiteMetaData() method with siteType {}", siteType);
		String sql = "select SITE_DATA from SLEXP_SITE_CONFIG where SITE_NAME = ?";
		String siteDataJson = (String) jdbcTemplate.queryForObject(sql, new Object[] { siteType }, String.class);
		return siteDataJson;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> findUserDetailByUserIdSolutionId(String userId, Long solutionId) {
		logger.info("Inside findUserDetailByUserIdSolutionId() method.");
		String sql = "select a.SITE_ID, a.ADDRESS_NAME || ', ' || a.CITY || ', ' || a.STATE || ', ' || a.ZIP || ' ' || a.COUNTRY as site_addr from sales_site a where a.DESIGN_ID = ?";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { solutionId });

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

	@Override
	public long insertSiteConfigurationData(final String userId, final long solutionId,
			final String accessData) throws SQLException {
		logger.info("Inside insertSiteConfigurationData() method.");

		String sqlSeq = "SELECT SLEXP_SITEDETAIL_TX_SEQ.nextval as trxn_seq FROM dual";
		final Long seqNum = jdbcTemplate.queryForObject(sqlSeq, Long.class);

		final String sql = "INSERT INTO SLEXP_SITEDETAIL_TX (ID, USER_ID, SOLUTION_ID, ACCESS_DATA) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, seqNum);
				pstmt.setString(2, userId);
				pstmt.setLong(3, solutionId);
				pstmt.setString(4, accessData);

				return pstmt;
			}
		});

		return seqNum;
	}

	@Override
	public void insertSiteConfigurationDataInRelational(final UserDesignSelectionDO userDesignDo) throws SQLException {
		logger.info("Inside insertSiteConfigurationDataInRelational() method.");

/*		String sqlSeq = "SELECT SLEXP_SITEDETAIL_TX_SEQ.nextval as trxn_seq FROM dual";
		final Long seqNum = jdbcTemplate.queryForObject(sqlSeq, Long.class);
*/
		final String sql = "INSERT INTO sales_design (SITE_ID, ACCESS_SPEED, PORT_SPEED, PORT_TYPE, CREATED_DATE) VALUES (?, ?, ?, ?, SYSDATE)";
		jdbcTemplate.update(new PreparedStatementCreator() {

			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, 10);
				pstmt.setLong(2, 11);
				pstmt.setLong(3, 12);
				pstmt.setString(4, "ETHERNET");
				return pstmt;
			}
		});
	}
	
	@Override
	@Transactional(readOnly = true)
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
	@Transactional
	public void updateSiteConfigurationData(long transactionId, String jsonString) throws SQLException {
		logger.info("Inside updateSiteConfigurationData() method.");
		String sqlUpdate = "UPDATE SLEXP_SITEDETAIL_TX SET ACCESS_DATA = ? WHERE ID = ?";
		int iReturnVal = jdbcTemplate.update(sqlUpdate, jsonString, transactionId);
		logger.info("Return value after update is {}", iReturnVal);
	}

	@Transactional(readOnly = true)
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
	@Transactional
	public void updateServiceFeaturesData(String jsonString, Long solutionId, String userId) throws SQLException {
		logger.info("Inside updateServiceFeaturesData");
		String sqlUpdate = "update SLEXP_SITEDETAIL_TX set SERVICE_FEATURE_DATA = ? where SOLUTION_ID = ? and USER_ID = ?";
		int iReturnVal = jdbcTemplate.update(sqlUpdate, jsonString, solutionId, userId);
		logger.info("Return value after service and features update : " + iReturnVal);

	}

	@Override
	@Transactional(readOnly = true)
	public String getResultsData(String accessSpeed, String portSpeed) throws JsonProcessingException, JSONException {
		logger.info("Inside getResultsData method.");
		String sql = "select * from SALES_RULES where ACCESS_SPEED_ID = ? and PORT_SPEED_ID = ? order by MRC asc, NRC asc";
		List resultList = (List) jdbcTemplate.queryForList(sql, accessSpeed, portSpeed);
		ObjectMapper mapper = new ObjectMapper();
		String JSONString = mapper.writeValueAsString(resultList);
		return JSONString;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PortSpeedDO> getPortSpeedsByAccessData(final String accessType, final String accessSpeed) {
		logger.info("Access Speed and Access Type received are {} and {}", accessSpeed, accessType);
		String sql = "select PORT_SPEED_ID, MRC, NRC from SALES_RULES where ACCESS_SPEED_ID = ? and PORT_TYPE = ?";
		List<PortSpeedDO> list = jdbcTemplate.query(sql, new RowMapper<PortSpeedDO>() {
			@Override
			public PortSpeedDO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PortSpeedDO portSpeedDO = new PortSpeedDO();
				portSpeedDO.setPortType(accessType);
				portSpeedDO.setAccessSpeed(accessSpeed);
				portSpeedDO.setPortSpeed(rs.getString("PORT_SPEED_ID"));
				portSpeedDO.setMrc(rs.getString("MRC"));
				portSpeedDO.setNrc(rs.getString("NRC"));
				return portSpeedDO;
			}
		}, accessSpeed, accessType);
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, List<AccessSpeedDO>> getAllAccessSpeeds() {
		String sql = "select PORT_TYPE, ACCESS_SPEED_ID, min(MRC) || '-' || max(MRC) as MRC, min(NRC) || '-' || max(NRC) as NRC "
				+ "from sales_rules  group by PORT_TYPE, ACCESS_SPEED_ID  order by PORT_TYPE, ACCESS_SPEED_ID";
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

		Map<String, List<AccessSpeedDO>> outMap = new HashMap<>();
		List<AccessSpeedDO> outList = null;

		for (Map<String, Object> map : result) {
			AccessSpeedDO accessSpeedDO = new AccessSpeedDO();
			accessSpeedDO.setPortType(map.get("PORT_TYPE").toString());
			accessSpeedDO.setMrc(map.get("MRC").toString());
			accessSpeedDO.setNrc(map.get("NRC").toString());
			accessSpeedDO.setAccessSpeed(map.get("ACCESS_SPEED_ID").toString());

			outList = outMap.get(accessSpeedDO.getPortType());

			if (outList == null) {
				outList = new ArrayList<AccessSpeedDO>();
				outMap.put(accessSpeedDO.getPortType(), outList);
			}

			outList.add(accessSpeedDO);
		}

		return outMap;
	}

	@Override
	@Transactional(readOnly = true)
	public Long fetchDefaultSolutionIdByUserId(String userId) {
		String sql = "select design_id from sales_user_solution where created_id=(select user_id from fn_user where login_id=?) and rownum=1";
		Long solutionId = jdbcTemplate.queryForObject(sql, Long.class, userId);
		logger.info("Solution id retrieved from database is  {}", solutionId);
		return solutionId;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, String> getSiteInfoBySolutionId(Long solutionId) {
		Map<String, String> output = new HashMap<>();

		String sql = "select SITE_ID, SITE_NAME from SALES_SITE where DESIGN_ID = ?";
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, solutionId);
		for (Map<String, Object> map : result) {
			output.put(map.get("SITE_NAME").toString(), map.get("SITE_ID").toString());
		}

		return output;
	}
}
