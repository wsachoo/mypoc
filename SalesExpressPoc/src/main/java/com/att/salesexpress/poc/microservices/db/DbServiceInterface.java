package com.att.salesexpress.poc.microservices.db;

import java.util.Map;

public interface DbServiceInterface {
	public String getSiteMetaData(String siteType);

	public Map<String, Object> findUserDetailByUserIdSolutionId(String userId, Long solutionId);
}