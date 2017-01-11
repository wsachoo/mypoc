package com.att.salesexpress.webapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SalesExpressMicroServiceCallerService {

	String getJsonMetaDataByUserIdSolutionId(String userId, Long solutionId) throws JsonProcessingException;

	String getSiteConfigurationMetaDataBySiteType(String siteType);

}