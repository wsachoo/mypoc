package com.att.salesexpress.webapp.service.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.att.salesexpress.webapp.entity.SalesRules;
import com.att.salesexpress.webapp.entity.SalesSite;
import com.att.salesexpress.webapp.pojos.AccessSpeedDO;
import com.att.salesexpress.webapp.pojos.PortSpeedDO;
import com.att.salesexpress.webapp.pojos.UserDesignSelectionDO;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * @author sw088d initial version
 *
 */
public interface DbService {
	public String getSiteMetaData(String siteType);

	public Map<String, Object> findUserDetailByUserIdSolutionId(String userId, Long solutionId);

	public long insertSiteConfigurationData(final String userId, final long solutionId, final String accessData)
			throws SQLException;

	public Integer getTransactionIdByUserIdSolutionId(String userId, Long solutionId);

	public void updateSiteConfigurationData(Long lTransactionId, String jsonString) throws SQLException;

	public void updateServiceFeaturesData(String jsonString, Long solutionId, String userId) throws SQLException;

	public List<Map<String, Object>> getResultsData(Long solutionId, String accessSpeed, String portSpeed) throws JsonProcessingException, JSONException;

	public List<PortSpeedDO> getPortSpeedsByAccessData(String accessType, String accessSpeed);

	public Map<String, List<AccessSpeedDO>> getAllAccessSpeeds();

	public Long fetchDefaultSolutionIdByUserId(String userId);

	Map<String, String> getSiteInfoBySolutionId(Long solutionId);

	void insertSiteConfigurationDataInRelational(UserDesignSelectionDO userDesignDo) throws SQLException;
	
	public void getFinalResultDataByProc();

	public void updateSiteConfigurationDataInRelational(UserDesignSelectionDO objUserDesignSelectionDO);

	public void removePreviousSiteConfigurationDataInRelational(UserDesignSelectionDO objUserDesignSelectionDO);
	

	public String getServiceFeaturesMetaDataBySiteName(String siteType);

	public List<SalesSite> findSalesSiteBySiteId(final Long solutionId);

	public void saveIglooResponseInDb(Long lSolutionId, String iglooResponsString);

	public String retrieveServicesFeaturesConfiguration();

	void updateServiceFeaturesConfiguration(String jsonString) throws SQLException;

	void saveProductConfiguration(List<SalesRules> salesRulesEntityList) throws SQLException;

	void checkIfRuleAlreadyExits(List<SalesRules> salesRulesEntityList);

	public void updateProductConfiguration(List<SalesRules> rulesToInsert);
	
	public List<Map<String,Object>> getDistinctProductsToConfigure();

	public void deleteProductConfiguration(List<SalesRules> salesRulesEntityList);

	List<Map<String, Object>> getAccessSpeedByAccessType(String productType, String accessType);

	List<Map<String, Object>> getPortSpeedsByAccessSpeed(String productType, String accessType, Long accessSpeed);

	public List<Map<String, Object>> getDistinctAccessSpeedByAccessType(String accessType);

	public List<Map<String, Object>> getDistinctPortSpeedsByAccessSpeed(String accessType, Long lAccessSpeed);

	void deleteProductConfiguration(String portType, Long long1, Long long2);
}
