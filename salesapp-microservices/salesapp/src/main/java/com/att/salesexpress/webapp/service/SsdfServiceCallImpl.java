package com.att.salesexpress.webapp.service;

import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.salesexpress.webapp.service.db.DbService;

@Service
public class SsdfServiceCallImpl implements SsdfServiceCall {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DbService dbServiceImpl;

	@Override
	public String getSsdfContractMicroserviceRequestInfo(Map<String, Object> paramValues) {
		logger.debug("Entered getSsdfContractMicroserviceRequestInfo() method.");
		String opportunityId = paramValues.get("OPPORTUNITY_ID").toString();
		String reqJson = dbServiceImpl.findSsdfRequestJsonByOpportunityId(opportunityId);
		
		StrSubstitutor sub = new StrSubstitutor(paramValues);
		reqJson = sub.replace(reqJson);
		
		String url = dbServiceImpl.findSsdfMicroserviceUrl("SSDF_CONTRACT_MICROSERVICE_URL");

		logger.debug("Exiting getSsdfContractMicroserviceRequestInfo() method.");
		return "{ \"REQUEST_URL\" : \"" + url + "\", \"REQUEST_JSON\" : " + reqJson + " }";
	}
}
