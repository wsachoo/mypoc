package com.att.salesexpress.poc.microservices.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value="DbServiceImpl.microservice")
public class DbServiceImpl implements DbServiceInterface {
	static final Logger logger = LoggerFactory.getLogger(DbServiceImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public String getSiteMetaData(String siteType) {
		logger.info("Inside getSiteMetaData() method with siteType {}", siteType);
		String sql = "SELECT site_data FROM SiteDetail WHERE site_name = ?";
		String siteDataJson = (String) jdbcTemplate.queryForObject(sql, new Object[] { siteType }, String.class);
		return siteDataJson;
	}
}
