package com.att.salesexpress.webapp.service.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.att.edb.accessquote.GetAccessQuoteResponse;
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
	
	public List<SalesSite> findSalesSiteBySiteId(final Long solutionId);

	public void saveIglooResponseInDb(Long lSolutionId, String iglooResponsString);

}
