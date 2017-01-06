package com.att.salesexpress.poc.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
/**
 * 
 * @author sw088d initial version
 *
 */
public interface DbServiceInterface {
	/*public String getSiteDataByName(String name);*/

	/*public Map<String, Object> getSiteDetailEntityBySiteName(String sitename);*/
	
	public long insertSiteConfigurationData(final String userId, final long solutionId, final Integer siteId, final String accessData) throws SQLException;

	/*public String findUserDetailByUserId(String userId);*/

	public Integer getTransactionIdByUserIdSolutionId(String userId, Long solutionId);

	public void updateSiteConfigurationData(long lTransactionId, String jsonString) throws SQLException;
	
	public List getServices();
	
	public void updateServiceFeaturesData(String jsonString, int solutionId, String userId )  throws SQLException;

	public Map<String, String> getAccessData(int solutionId) throws IOException,  JSONException;
	
	public String getResultsData( String accessSpeed, String portSpeed) throws JsonProcessingException , JSONException;
}
