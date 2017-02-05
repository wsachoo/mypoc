package com.att.salesexpress.igloo.consumer.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.att.edb.accessquote.GetAccessQuote;
import com.att.edb.accessquote.GetAccessQuoteAlternate;
import com.att.edb.accessquote.GetAccessQuoteResponse;

public class IglooWSConsumerService extends WebServiceGatewaySupport {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String soapActionGetAccessQuoteUrl;
	private String soapActionGetAccessQuoteAlternateUrl;

	public GetAccessQuoteResponse getAccessQuote(GetAccessQuote quote) {
		WebServiceTemplate objWebServiceTemplate = getWebServiceTemplate();
		GetAccessQuoteResponse quoteResp = null;
		SoapActionCallback soapActionCallback = new SoapActionCallback(soapActionGetAccessQuoteUrl);
		try {
			quoteResp = (GetAccessQuoteResponse) objWebServiceTemplate.marshalSendAndReceive(quote, soapActionCallback);
		} 
		catch (SoapFaultClientException e) {
			logger.info("Exception occurred: {}", ExceptionUtils.getStackTrace(e));
			throw e;
		}
		return quoteResp;
	}

	public GetAccessQuoteResponse getAccessQuoteAlternate(GetAccessQuoteAlternate quoteAlternate) {
		WebServiceTemplate objWebServiceTemplate = getWebServiceTemplate();
		GetAccessQuoteResponse quoteResp = (GetAccessQuoteResponse) objWebServiceTemplate
				.marshalSendAndReceive(quoteAlternate, new SoapActionCallback(soapActionGetAccessQuoteAlternateUrl));
		return quoteResp;
	}

	public String getSoapActionGetAccessQuoteUrl() {
		return soapActionGetAccessQuoteUrl;
	}

	public void setSoapActionGetAccessQuoteUrl(String soapActionGetAccessQuoteUrl) {
		this.soapActionGetAccessQuoteUrl = soapActionGetAccessQuoteUrl;
	}

	public String getSoapActionGetAccessQuoteAlternateUrl() {
		return soapActionGetAccessQuoteAlternateUrl;
	}

	public void setSoapActionGetAccessQuoteAlternateUrl(String soapActionGetAccessQuoteAlternateUrl) {
		this.soapActionGetAccessQuoteAlternateUrl = soapActionGetAccessQuoteAlternateUrl;
	}

}
