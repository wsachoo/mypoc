package com.att.salesexpress.poc.db;

import java.sql.SQLException;

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
}
