package com.att.salesexpress.webapp.service;

public interface SalesExpressMicroServiceCallerService {

	String getJsonMetaDataByUserIdSolutionId(String userId, Long solutionId);

	String getSiteConfigurationMetaDataBySiteType(String siteType);

}