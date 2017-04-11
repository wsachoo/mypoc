package com.att.salesexpress.microservices.db;

import java.util.Map;

public interface DbService {
	public String getSiteMetaData(String siteType);

	public Map<String, Object> findUserDetailByUserIdSolutionId(String userId, Long solutionId);
}