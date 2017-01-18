package com.att.salesexpress.webapp.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * @author sw088d initial version
 *
 */
public interface DbService {
	public long insertSiteConfigurationData(final String userId, final long solutionId, final Integer siteId,
			final String accessData) throws SQLException;

	public Integer getTransactionIdByUserIdSolutionId(String userId, Long solutionId);

	public void updateSiteConfigurationData(long lTransactionId, String jsonString) throws SQLException;

	public List getServices();
	
	public void updateServiceFeaturesData(String jsonString, Long solutionId, String userId) throws SQLException;

	public Map<String, String> getAccessData(Long solutionId) throws IOException,  JSONException;
	
	public String getResultsData( String accessSpeed, String portSpeed) throws JsonProcessingException , JSONException;
}
