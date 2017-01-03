package com.att.salesexpress.poc.db;

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

	public Integer getTransactionIdByUserIdSolutionId(String userId, Long solutionId);

	public void updateSiteConfigurationData(long lTransactionId, String jsonString) throws SQLException;
}
