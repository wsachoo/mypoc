package com.att.salesexpress.webapp.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.att.salesexpress.webapp.pojos.AccessSpeedDO;
import com.att.salesexpress.webapp.pojos.PortSpeedDO;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * @author sw088d initial version
 *
 */
public interface DbService {
	public String getSiteMetaData(String siteType);

	public Map<String, Object> findUserDetailByUserIdSolutionId(String userId, Long solutionId);
	
	public long insertSiteConfigurationData(final String userId, final long solutionId, final Integer siteId,
			final String accessData) throws SQLException;

	public Integer getTransactionIdByUserIdSolutionId(String userId, Long solutionId);

	public void updateSiteConfigurationData(long lTransactionId, String jsonString) throws SQLException;

	public List getServices();
	
	public void updateServiceFeaturesData(String jsonString, Long solutionId, String userId) throws SQLException;

	public String getResultsData( String accessSpeed, String portSpeed) throws JsonProcessingException , JSONException;

	public List<PortSpeedDO> getPortSpeedsByAccessData(String accessType, String accessSpeed);

	public Map<String, List<AccessSpeedDO>> getAllAccessSpeeds();

	public Long fetchDefaultSolutionIdByUserId(String userId);

	Map<String, String> getSiteInfoBySolutionId(Long solutionId);

}
