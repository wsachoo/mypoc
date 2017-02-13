package com.att.salesexpress.webapp.service.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.att.salesexpress.webapp.entity.SalesSite;
import com.att.salesexpress.webapp.pojos.AccessSpeedDO;
import com.att.salesexpress.webapp.pojos.PortSpeedDO;
import com.att.salesexpress.webapp.pojos.UserDesignSelectionDO;
import com.att.salesexpress.webapp.pojos.UserSiteDesignDO;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * @author sw088d initial version
 *
 */
@Repository
public class DbServiceImpl implements DbService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("hikariOraJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	public String getSiteMetaData(String siteType) {
		logger.debug("inside getSiteMetaData debug mode");
		logger.debug("Inside getSiteMetaData() method with siteType {}", siteType);
		String sql = "select SITE_DATA from SLEXP_SITE_CONFIG where SITE_NAME = ?";
		String siteDataJson = (String) jdbcTemplate.queryForObject(sql, new Object[] { siteType }, String.class);
		return siteDataJson;
	}

	public Map<String, Object> findUserDetailByUserIdSolutionId(String userId, Long solutionId) {
		logger.debug("Inside findUserDetailByUserIdSolutionId() method.");
		String sql = "select a.SITE_ID, a.SITE_NAME, a.ADDRESS_NAME || ', ' || a.CITY || ', ' || a.STATE || ', ' || a.ZIP || ' ' || a.COUNTRY as SITE_ADDR from sales_site a where a.DESIGN_ID = ?";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { solutionId });

		Map<String, Object> returnValues = new HashMap<String, Object>();
		returnValues.put("user_id", userId);
		returnValues.put("solution_id", solutionId);
		returnValues.put("siteAddresses", rows);
		return returnValues;
	}

	public List<SalesSite> findSalesSiteBySiteId(final Long solutionId) {
		logger.debug("Inside findSalesSiteBySiteId for solutionId {}", solutionId);
		String sql = "select * from SALES_SITE where DESIGN_ID= ?";

		List<SalesSite> returnObject = jdbcTemplate.query(sql, new RowMapper<SalesSite>() {

			public SalesSite mapRow(ResultSet rs, int rowNum) throws SQLException {
				SalesSite salesSiteDO = new SalesSite();
				salesSiteDO.setSiteId(rs.getLong("SITE_ID"));
				salesSiteDO.setDesignId(solutionId);
				salesSiteDO.setSiteName(rs.getString("SITE_NAME"));
				salesSiteDO.setAddressName(rs.getString("ADDRESS_NAME"));
				salesSiteDO.setCity(rs.getString("CITY"));
				salesSiteDO.setState(rs.getString("STATE"));
				salesSiteDO.setCountry(rs.getString("COUNTRY"));
				salesSiteDO.setCreatedDate(rs.getDate("CREATED_DATE"));
				salesSiteDO.setCreatedId(rs.getBigDecimal("CREATED_ID"));
				salesSiteDO.setModifiedDate(rs.getDate("MODIFIED_DATE"));
				salesSiteDO.setModifiedId(rs.getBigDecimal("MODIFIED_ID"));
				salesSiteDO.setZip(rs.getString("ZIP"));
				salesSiteDO.setNpanxx(rs.getBigDecimal("NPANXX"));
				return salesSiteDO;
			}
		}, solutionId);

		return returnObject;
	}

	public long insertSiteConfigurationData(final String userId, final long solutionId, final String accessData)
			throws SQLException {
		logger.debug("Inside insertSiteConfigurationData() method.");

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

	public void insertSiteConfigurationDataInRelational(final UserDesignSelectionDO userDesignDo) throws SQLException {
		logger.debug("Inside insertSiteConfigurationDataInRelational() method.");
		final List<UserSiteDesignDO> userSiteDesignDOList = new ArrayList<>(userDesignDo.getSiteDesignList().values());

		final String sqlBatchInsert = "INSERT INTO sales_design (" + "SITE_ID, RATE_PLAN, PORT_TYPE, "
				+ "ACCESS_SPEED, PORT_SPEED, L2_PROTOCOL, " + "HCF_MIN_COMMITMENT_ID, COS_YN, CREATED_DATE, "
				+ "CREATED_ID, MODIFIED_DATE, MODIFIED_ID, SOLUTION_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, SYSDATE, ?, ?)";

		jdbcTemplate.batchUpdate(sqlBatchInsert, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement pstmt, int i) throws SQLException {
				UserSiteDesignDO objUserSiteDesignDO = userSiteDesignDOList.get(i);
				pstmt.setLong(1, objUserSiteDesignDO.getSiteId()); // HCF_MIN_COMMITMENT_ID
				pstmt.setString(2, objUserSiteDesignDO.getPortConfigDesign().getRatePlan()); // RATE_PLAN
				pstmt.setString(3, objUserSiteDesignDO.getPortConfigDesign().getPortType()); // PORT_TYPE
				pstmt.setLong(4, objUserSiteDesignDO.getAccessConfigDesign().getSliderSpeedValue()); // ACCESS_SPEED
				pstmt.setLong(5, objUserSiteDesignDO.getPortConfigDesign().getSliderPortSpeedValue()); // PORT_SPEED
				pstmt.setString(6, StringUtils.EMPTY); // L2_PROTOCOL
				pstmt.setLong(7, -1); // HCF_MIN_COMMITMENT_ID
				pstmt.setString(8, StringUtils.EMPTY); // COS_YN
				pstmt.setString(9, userDesignDo.getUserId()); // CREATED_ID
				pstmt.setString(10, userDesignDo.getUserId()); // MODIFIED_ID
				pstmt.setLong(11, userDesignDo.getSolutionId()); // HCF_MIN_COMMITMENT_ID
			}

			public int getBatchSize() {
				return userDesignDo.getSiteDesignList().keySet().size();
			}
		});
	}

	public void removePreviousSiteConfigurationDataInRelational(UserDesignSelectionDO objUserDesignSelectionDO) {
		logger.debug("deleting previous site configuration data from relation tables");
		String sqlDelete = "delete from sales_design where SOLUTION_ID = ?";
		jdbcTemplate.update(sqlDelete, objUserDesignSelectionDO.getSolutionId());
	}

	public void updateSiteConfigurationDataInRelational(final UserDesignSelectionDO userDesignDo) {
		logger.debug("Inside updateSiteConfigurationDataInRelational() method.");
		final List<UserSiteDesignDO> userSiteDesignDOList = new ArrayList<>(userDesignDo.getSiteDesignList().values());

		final String sqlBatchInsert = "update sales_design set " + "RATE_PLAN = ?, PORT_TYPE= ?, "
				+ "ACCESS_SPEED = ?, PORT_SPEED = ?, L2_PROTOCOL = ?, " + "HCF_MIN_COMMITMENT_ID = ?, COS_YN = ?, "
				+ "MODIFIED_DATE = SYSDATE, MODIFIED_ID = ? where SITE_ID = ?";

		jdbcTemplate.batchUpdate(sqlBatchInsert, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement pstmt, int i) throws SQLException {
				UserSiteDesignDO objUserSiteDesignDO = userSiteDesignDOList.get(i);
				pstmt.setString(1, objUserSiteDesignDO.getPortConfigDesign().getRatePlan()); // RATE_PLAN
				pstmt.setString(2, objUserSiteDesignDO.getPortConfigDesign().getPortType()); // PORT_TYPE
				pstmt.setLong(3, objUserSiteDesignDO.getAccessConfigDesign().getSliderSpeedValue()); // ACCESS_SPEED
				pstmt.setLong(4, objUserSiteDesignDO.getPortConfigDesign().getSliderPortSpeedValue()); // PORT_SPEED
				pstmt.setString(5, StringUtils.EMPTY); // L2_PROTOCOL
				pstmt.setLong(6, -1); // HCF_MIN_COMMITMENT_ID
				pstmt.setString(7, StringUtils.EMPTY); // COS_YN
				pstmt.setString(8, userDesignDo.getUserId()); // MODIFIED_ID
				pstmt.setLong(9, objUserSiteDesignDO.getSiteId()); // SITE_ID
			}

			public int getBatchSize() {
				return userDesignDo.getSiteDesignList().keySet().size();
			}
		});
	}

	public Integer getTransactionIdByUserIdSolutionId(String userId, Long solutionId) {
		String sql = "SELECT ID FROM SLEXP_SITEDETAIL_TX WHERE USER_ID = ? and SOLUTION_ID = ?";
		try {
			String strTransactionId = (String) jdbcTemplate.queryForObject(sql, new Object[] { userId, solutionId },
					String.class);
			return Integer.parseInt(strTransactionId);
		} catch (EmptyResultDataAccessException erdaex) {
			logger.debug("Transaction not found in database for userId: {}, solutionId: {}", userId, solutionId);
			return null;
		}
	}

	public void updateSiteConfigurationData(Long transactionId, String jsonString) throws SQLException {
		logger.debug("Inside updateSiteConfigurationData() method.");
		String sqlUpdate = "UPDATE SLEXP_SITEDETAIL_TX SET ACCESS_DATA = ? WHERE ID = ?";
		int iReturnVal = jdbcTemplate.update(sqlUpdate, jsonString, transactionId);
		logger.debug("Return value after update is {}", iReturnVal);
	}

	public void updateServiceFeaturesData(String jsonString, Long solutionId, String userId) throws SQLException {
		logger.debug("Inside updateServiceFeaturesData");
		String sqlUpdate = "update SLEXP_SITEDETAIL_TX set SERVICE_FEATURE_DATA = ? where SOLUTION_ID = ? and USER_ID = ?";
		int iReturnVal = jdbcTemplate.update(sqlUpdate, jsonString, solutionId, userId);
		logger.debug("Return value after service and features update : " + iReturnVal);
	}

	public List<Map<String, Object>> getResultsData(Long solutionId, String accessSpeed, String portSpeed)
			throws JsonProcessingException, JSONException {
		logger.debug("Inside getResultsData method.");
		/*
		 * String sql =
		 * "select * from SALES_RULES where ACCESS_SPEED_ID = ? and PORT_SPEED_ID = ? order by MRC asc, NRC asc"
		 * ; List<Map<String, Object>> resultList =
		 * jdbcTemplate.queryForList(sql, accessSpeed, portSpeed);
		 */

		String sql = "select a.PRODUCT, a.MRC, a.NRC, LISTAGG(b.SITE_ID, ',') WITHIN GROUP (ORDER BY b.SITE_ID) AS SITE_IDS "
				+ "from SALES_RULES a, sales_design b " + "where a.ACCESS_SPEED_ID = b.ACCESS_SPEED "
				+ "and a.PORT_SPEED_ID=b.PORT_SPEED and b.SOLUTION_ID=?" + "group by a.PRODUCT, a.MRC, a.NRC";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, solutionId);
		return resultList;
	}

	public List<PortSpeedDO> getPortSpeedsByAccessData(final String accessType, final String accessSpeed) {
		logger.debug("Access Speed and Access Type received are {} and {}", accessSpeed, accessType);
		String sql = "select PORT_SPEED_ID, MRC, NRC from SALES_RULES where ACCESS_SPEED_ID = ? and PORT_TYPE = ?";
		List<PortSpeedDO> list = jdbcTemplate.query(sql, new RowMapper<PortSpeedDO>() {

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

	public Long fetchDefaultSolutionIdByUserId(String userId) {
		String sql = "select design_id from sales_user_solution where created_id=(select user_id from fn_user where login_id=?) and rownum=1";
		Long solutionId = jdbcTemplate.queryForObject(sql, Long.class, userId);
		logger.debug("Solution id retrieved from database is  {}", solutionId);
		return solutionId;
	}

	public Map<String, String> getSiteInfoBySolutionId(Long solutionId) {
		Map<String, String> output = new HashMap<>();

		String sql = "select SITE_ID, SITE_NAME from SALES_SITE where DESIGN_ID = ?";
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, solutionId);
		for (Map<String, Object> map : result) {
			output.put(map.get("SITE_NAME").toString(), map.get("SITE_ID").toString());
		}

		return output;
	}

	public void getFinalResultDataByProc() {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sample_procedure");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		SqlParameterSource inParamSource = new MapSqlParameterSource(inParamMap);
		simpleJdbcCall.execute(inParamSource);
		logger.debug("logging resultSet of sample_procedure call");

	}

	@Override
	public String getServiceFeaturesMetaDataBySiteName(String siteType) {
		logger.debug("inside getServiceFeaturesMetaDataBySiteName debug mode");
		logger.info("Inside getServiceFeaturesMetaDataBySiteName() method with siteType {}", siteType);
		String sql = "select SERVICE_FEATURES_METADATA from SLEXP_SITE_CONFIG where SITE_NAME = ?";
		String servFeaturesMDataJson = (String) jdbcTemplate.queryForObject(sql, new Object[] { siteType }, String.class);
		return servFeaturesMDataJson ;
	}


	public void saveIglooResponseInDb(Long transactionId, String iglooResponsString) {
		logger.debug("Inside saveIglooResponseInDb() method.");
		String sqlUpdate = "UPDATE SLEXP_SITEDETAIL_TX SET IGLOO_RESPONSE = ? WHERE ID = ?";
		int iReturnVal = jdbcTemplate.update(sqlUpdate, iglooResponsString, transactionId);
		logger.debug("Return value after saveIglooResponseInDb is {}", iReturnVal);

	}
}
