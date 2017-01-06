package com.att.salesexpress.poc.service;

public interface SalesExpressMicroServiceCallerService {

	String getJsonMetaDataByUserIdSolutionId(String userId, Long solutionId);

	String getSiteConfigurationMetaDataBySiteType(String siteType);

}