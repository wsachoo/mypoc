package com.att.salesexpress.poc.service;

import java.sql.SQLException;
import java.util.Map;
/**
 * 
 * @author sw088d initial version
 *
 */
public interface DbServiceInterface {
	public String getSiteDataByName(String name);

	public Map<String, Object> getSiteDetailEntityBySiteName(String sitename);
	
	public long insertSiteConfigurationData(final String userId, final long solutionId, final Integer siteId, final String accessData) throws SQLException;

	public String findUserDetailByUserId(String userId);

	public Map<String, Object> findUserDetailByUserIdSolutionId(String userId, Integer solutionId);

	public Integer getTransactionIdByUserIdSolutionId(String userId, Integer solutionId);

	public void updateSiteConfigurationData(long lTransactionId, String jsonString) throws SQLException;
}
