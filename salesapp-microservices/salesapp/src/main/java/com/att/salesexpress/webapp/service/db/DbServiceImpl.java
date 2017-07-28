package com.att.salesexpress.webapp.service.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.att.salesexpress.webapp.entity.SalesRules;
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

	@Value("${DB_TYPE}")
	private String dbType;

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
		logger.debug("Exiting findUserDetailByUserIdSolutionId() method.");
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
		
		if ("POSTGRESQL".equalsIgnoreCase(dbType)) {
			sqlSeq = "SELECT nextval('SLEXP_SITEDETAIL_TX_SEQ')";
		}
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

		String sqlBatchInsert = "INSERT INTO sales_design (" + "SITE_ID, RATE_PLAN, PORT_TYPE, "
				+ "ACCESS_SPEED, PORT_SPEED, L2_PROTOCOL, " + "HCF_MIN_COMMITMENT_ID, COS_YN, CREATED_DATE, "
				+ "CREATED_ID, MODIFIED_DATE, MODIFIED_ID, SOLUTION_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?, SYSDATE, ?, ?)";

		logger.info("The database connected to is: " + dbType);
		if ("POSTGRESQL".equalsIgnoreCase(dbType)) {
			
			sqlBatchInsert = "INSERT INTO sales_design (" + "SITE_ID, RATE_PLAN, PORT_TYPE, "
					+ "ACCESS_SPEED, PORT_SPEED, L2_PROTOCOL, " + "HCF_MIN_COMMITMENT_ID, COS_YN, CREATED_DATE, "
					+ "CREATED_ID, MODIFIED_DATE, MODIFIED_ID, SOLUTION_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, now(), ?, now(), ?, ?)";
		}

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
		
		if ("POSTGRESQL".equalsIgnoreCase(dbType)) {
			sql = "select a.PRODUCT, a.MRC, a.NRC, string_agg(b.SITE_ID::text, ',') AS SITE_IDS "
					+ "from SALES_RULES a, sales_design b " + "where a.ACCESS_SPEED_ID = b.ACCESS_SPEED "
					+ "and a.PORT_SPEED_ID=b.PORT_SPEED and b.SOLUTION_ID=?" + "group by a.PRODUCT, a.MRC, a.NRC";
		}
		
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, solutionId);
		return resultList;
	}

	public List<PortSpeedDO> getPortSpeedsByAccessData(final String accessType, final String accessSpeed) {
		logger.debug("Access Speed and Access Type received are {} and {}", accessSpeed, accessType);
		String sql = "select distinct PORT_SPEED_ID, MRC, NRC from SALES_RULES where ACCESS_SPEED_ID = ? and PORT_TYPE = ? order by PORT_SPEED_ID";
		List<PortSpeedDO> list = jdbcTemplate.query(sql, new RowMapper<PortSpeedDO>() {

			public PortSpeedDO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PortSpeedDO portSpeedDO = new PortSpeedDO();
				portSpeedDO.setPortType(accessType);
				portSpeedDO.setAccessSpeed(accessSpeed);
				portSpeedDO.setPortSpeed(Long.valueOf(rs.getLong("PORT_SPEED_ID")).toString());
				portSpeedDO.setMrc(Long.valueOf(rs.getLong("MRC")).toString());
				portSpeedDO.setNrc(Long.valueOf(rs.getLong("NRC")).toString());
				return portSpeedDO;
			}
		}, Long.parseLong(accessSpeed), accessType);
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
		//String sql = "select design_id from sales_user_solution where created_id=(select user_id from fn_user where login_id=?) and rownum=1";
		String sql = "select design_id from sales_user_solution where created_id=(select user_id from fn_user where login_id=?)";
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
		String servFeaturesMDataJson = (String) jdbcTemplate.queryForObject(sql, new Object[] { siteType },
				String.class);
		return servFeaturesMDataJson;
	}

	public void saveIglooResponseInDb(Long transactionId, String iglooResponsString) {
		logger.debug("Inside saveIglooResponseInDb() method.");
		String sqlUpdate = "UPDATE SLEXP_SITEDETAIL_TX SET IGLOO_RESPONSE = ? WHERE ID = ?";
		int iReturnVal = jdbcTemplate.update(sqlUpdate, iglooResponsString, transactionId);
		logger.debug("Return value after saveIglooResponseInDb is {}", iReturnVal);

	}

	@Override
	public String retrieveServicesFeaturesConfiguration() {
		String strServiceFeaturesConfig = jdbcTemplate.queryForObject(
				"select SERVICE_FEATURES_METADATA from SLEXP_SITE_CONFIG where SITE_NAME='testSite'", String.class);
		return strServiceFeaturesConfig;
	}

	@Override
	public void updateServiceFeaturesConfiguration(String jsonString) throws SQLException {
		logger.debug("Inside updateServiceFeaturesConfiguration() method.");
		String sqlUpdate = "update SLEXP_SITE_CONFIG set SERVICE_FEATURES_METADATA = ? ";
		int iReturnVal = jdbcTemplate.update(sqlUpdate, jsonString);
		logger.debug("Return value after service and features update : " + iReturnVal);
	}

	@Override
	public void checkIfRuleAlreadyExits(final List<SalesRules> salesRulesEntityList) {
		logger.debug("Inside checkIfRuleAlreadyExits() method.");
		String sql = "select count(1) from sales_rules where ACCESS_SPEED_ID=? and PORT_SPEED_ID=? and upper(PRODUCT)=? and port_type=?";

		for (SalesRules salesRules : salesRulesEntityList) {
			Integer iRowCount = jdbcTemplate.queryForObject(sql, Integer.class, salesRules.getAccessSpeed(),
					salesRules.getPortSpeed(), salesRules.getProductName().toUpperCase(), salesRules.getPortType());
			if (iRowCount == 1) {
				salesRules.setBlnExitsInDb(true);
			} else {
				salesRules.setBlnExitsInDb(false);
			}
		}
	}

	@Override
	public void saveProductConfiguration(final List<SalesRules> salesRulesEntityList) throws SQLException {
		logger.debug("Entered saveProductConfiguration() method.");

		String sqlBatchInsert = "INSERT INTO sales_rules ("
				+ "ID, RULE_ID, PRODUCT, RATE_PLAN, PORT_TYPE, ACCESS_SPEED_ID, PORT_SPEED_ID, MIN_MBC, MRC, NRC "
				+ ") VALUES (SALES_RULES_ID_SEQ.NEXTVAL, SALES_RULES_RULE_ID_SEQ.NEXTVAL, ?, NULL, ?, ?, ?, NULL, ?, ?)";

		if ("POSTGRESQL".equalsIgnoreCase(dbType)) {
			sqlBatchInsert = "INSERT INTO sales_rules ("
					+ "ID, RULE_ID, PRODUCT, RATE_PLAN, PORT_TYPE, ACCESS_SPEED_ID, PORT_SPEED_ID, MIN_MBC, MRC, NRC "
					+ ") VALUES (nextval('SALES_RULES_ID_SEQ'), SALES_RULES_RULE_ID_SEQ.NEXTVAL, ?, NULL, ?, ?, ?, NULL, ?, ?)";
		}
		
		jdbcTemplate.batchUpdate(sqlBatchInsert, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement pstmt, int i) throws SQLException {
				SalesRules objSalesRules = salesRulesEntityList.get(i);
				pstmt.setString(1, objSalesRules.getProductName());
				pstmt.setString(2, objSalesRules.getPortType());
				pstmt.setDouble(3, objSalesRules.getAccessSpeed());
				pstmt.setDouble(4, objSalesRules.getPortSpeed());
				pstmt.setDouble(5, objSalesRules.getMrc());
				pstmt.setDouble(6, objSalesRules.getNrc());
			}

			public int getBatchSize() {
				return salesRulesEntityList.size();
			}
		});

		logger.debug("Exiting saveProductConfiguration() method.");
	}

	@Override
	public void updateProductConfiguration(final List<SalesRules> rulesToUpdate) {
		// TODO Auto-generated method stub
		logger.debug("Entered updateProductConfiguration() method.");

		final String sqlBatchUpdate = "UPDATE sales_rules "
				+ "SET PORT_TYPE=?, MRC=?, NRC=? where ACCESS_SPEED_ID=? and PORT_SPEED_ID=? and upper(PRODUCT)=? and PORT_TYPE=?";

		jdbcTemplate.batchUpdate(sqlBatchUpdate, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement pstmt, int i) throws SQLException {
				SalesRules objSalesRules = rulesToUpdate.get(i);
				pstmt.setString(1, objSalesRules.getPortType());
				pstmt.setDouble(2, objSalesRules.getMrc());
				pstmt.setDouble(3, objSalesRules.getNrc());
				pstmt.setDouble(4, objSalesRules.getAccessSpeed());
				pstmt.setDouble(5, objSalesRules.getPortSpeed());
				pstmt.setString(6, objSalesRules.getProductName().toUpperCase());
				pstmt.setString(7, objSalesRules.getPortType());
			}

			public int getBatchSize() {
				return rulesToUpdate.size();
			}
		});

		logger.debug("Exiting updateProductConfiguration() method.");
	}

	@Override
	public List<Map<String, Object>> getDistinctProductsToConfigure() {
		logger.debug("inside getDistinctProductsToConfigure");
		final String getDistinctProducts = "SELECT DISTINCT(PRODUCT) as PRODUCT FROM SALES_RULES ORDER BY PRODUCT";
		return jdbcTemplate.queryForList(getDistinctProducts);
	}

	@Override
	public void deleteProductConfiguration(final List<SalesRules> salesRulesEntityList) {
		logger.debug("Entered deleteProductConfiguration() method.");

		final String sqlBatchInsert = "DELETE FROM sales_rules WHERE PRODUCT=? AND PORT_TYPE=? AND ACCESS_SPEED_ID=? AND PORT_SPEED_ID=?";

		jdbcTemplate.batchUpdate(sqlBatchInsert, new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement pstmt, int i) throws SQLException {
				SalesRules objSalesRules = salesRulesEntityList.get(i);
				pstmt.setString(1, objSalesRules.getProductName());
				pstmt.setString(2, objSalesRules.getPortType());
				pstmt.setDouble(3, objSalesRules.getAccessSpeed());
				pstmt.setDouble(4, objSalesRules.getPortSpeed());
			}

			public int getBatchSize() {
				return salesRulesEntityList.size();
			}
		});

		logger.debug("Exiting deleteProductConfiguration() method.");
	}

	@Override
	public void deleteProductConfiguration(String portType, Long accessSpeed, Long portSpeed) {
		logger.debug("Entered deleteProductConfigurationForAllProducts() method.");

		final String sql = "DELETE FROM sales_rules WHERE PORT_TYPE=? AND ACCESS_SPEED_ID=? AND PORT_SPEED_ID=?";
		jdbcTemplate.update(sql, new Object[] { portType, accessSpeed, portSpeed },
				new int[] { Types.VARCHAR, Types.DOUBLE, Types.DOUBLE });

		logger.debug("Exiting deleteProductConfigurationForAllProducts() method.");
	}

	@Override
	public List<Map<String, Object>> getAccessSpeedByAccessType(String productType, String accessType) {
		String sql = "select distinct(ACCESS_SPEED_ID) as ACCESS_SPEED_ID from sales_rules where PRODUCT=? and port_type=? order by ACCESS_SPEED_ID";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { productType, accessType });
		return rows;
	}

	@Override
	public List<Map<String, Object>> getPortSpeedsByAccessSpeed(String productType, String accessType,
			Long accessSpeed) {
		String sql = "select distinct PORT_SPEED_ID from sales_rules where PRODUCT=? and port_type=? and ACCESS_SPEED_ID=? order by PORT_SPEED_ID";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,
				new Object[] { productType, accessType, accessSpeed });
		return rows;
	}

	@Override
	public List<Map<String, Object>> getDistinctAccessSpeedByAccessType(String accessType) {
		String sql = "select distinct(ACCESS_SPEED_ID) as ACCESS_SPEED_ID from sales_rules where port_type=? order by ACCESS_SPEED_ID";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { accessType });
		return rows;
	}

	@Override
	public List<Map<String, Object>> getDistinctPortSpeedsByAccessSpeed(String accessType, Long lAccessSpeed) {
		String sql = "select distinct PORT_SPEED_ID from sales_rules where port_type=? and ACCESS_SPEED_ID=? order by PORT_SPEED_ID";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { accessType, lAccessSpeed });
		return rows;
	}

	@Override
	public String findSsdfRequestJsonByOpportunityId(String opportunityId) {
		logger.debug("Entered findSsdfRequestJsonByOpportunityId() method.");
		String sql = "select REQ_JSON from SALES_SSDF_CONTRACT_REQ_INFO where OPPORTUNITY_ID = ?";
		String siteDataJson = (String) jdbcTemplate.queryForObject(sql, new Object[] { opportunityId }, String.class);
		logger.debug("Exiting findSsdfRequestJsonByOpportunityId() method.");
		return siteDataJson;
	}

	@Override
	public String findSsdfMicroserviceUrl(String key) {
		logger.debug("Entered findSsdfMicroserviceUrl() method.");
		String sql = "select URL_VALUE from SALES_SSDF_MICROSERVICES_URLS where URL_KEY = ?";
		String url = (String) jdbcTemplate.queryForObject(sql, new Object[] { key }, String.class);
		logger.debug("Exiting findSsdfMicroserviceUrl() method.");
		return url;
	}
}
