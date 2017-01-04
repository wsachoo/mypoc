package com.att.salesexpress.microservices.util;

public class Constants {
	public static final String MICROSERVICE_URL_SITE_METADATA = "http://localhost:${server.port}/siteConfigurationMetaData?siteType=${siteType}";
	public static final String MICROSERVICE_URL_USERID_SOLUTION_METADATA = "http://localhost:${server.port}/userSolutionMetaData?userId=${userId}&solutionId=${solutionId}";
}
