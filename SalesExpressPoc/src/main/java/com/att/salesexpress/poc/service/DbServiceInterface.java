package com.att.salesexpress.poc.service;

import java.sql.SQLException;
import java.util.Map;

public interface DbServiceInterface {
	public String getSiteDataByName(String name);

	public Map<String, Object> getSiteDetailEntityBySiteName(String sitename);
	
	public long updateAccessTypeData(final long siteId, final String accessData) throws SQLException;

	public String findUserDetailByUserId(String userId);

	public Map<String, Object> findUserDetailByUserIdSolutionId(String userId, Integer solutionId);
}
