package com.att.salesexpress.microservices.kubeclient.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.att.salesexpress.microservices.kubeclient.service.SalesHistoryMicroServiceCallerService;

/**
 * 
 * @author sw088d initial version
 *
 */
@Controller
public class SalesHistoryConsumerController {
	private static final Logger logger = LoggerFactory.getLogger(SalesHistoryConsumerController.class);

	@Autowired
	private SalesHistoryMicroServiceCallerService salesExpressMicroServiceCallerServiceImpl;

	@CrossOrigin
	@ResponseBody
	@RequestMapping(value = "/user/salesHistory/getRecommendationBasedOnSalesHistory", method = RequestMethod.POST, produces = {
			"application/json" })
	public Map<String, Object> getRecommendationBasedOnSalesHistory(@RequestBody Map<String, Object> paramValues,
			HttpServletRequest request) {
		logger.info("Inside getRecommendationBasedOnSalesHistory method");
		Map<String, Object> result = null;
		try {
			result = salesExpressMicroServiceCallerServiceImpl.getSalesRecommendationFromHistory(paramValues);
		} catch (Exception e) {
			logger.error("Error occurred while invoking webservice: {}" + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Returning result from getRecommendationBasedOnSalesHistory method");
		return result;
	}

	@CrossOrigin
	@ResponseBody
	@RequestMapping(value = "/user/salesHistory/getSalesPercentageByAccessType", method = RequestMethod.POST, produces = {
			"application/json" })
	public Map<String, Object> getSalesPercentageByAccessType(@RequestBody Map<String, Object> paramValues,
			HttpServletRequest request) {
		logger.info("Inside getSalesPercentageByAccessType method");
		Map<String, Object> result = null;
		try {
			result = salesExpressMicroServiceCallerServiceImpl.getSalesPercentageByAccessType(paramValues);
		} catch (Exception e) {
			logger.error("Error occurred while invoking webservice: {}" + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Returning result from getSalesPercentageByAccessType method");
		return result;
	}

	@RequestMapping(value = "/user/salesHistory/getSalesHistoryOrderDetailBySiteIdLeadDesignId", method = RequestMethod.GET, produces = {
			"application/json" })
	@CrossOrigin
	public ResponseEntity<String> getSalesHistoryOrderDetailBySiteIdLeadDesignId(
			@RequestParam(value = "siteId", required = true) Long siteId,
			@RequestParam(value = "leadDesignId", required = true) Long leadDesignId) {
		logger.info("Inside getSalesHistoryOrderDetailBySiteId method");
		
		String strResp = salesExpressMicroServiceCallerServiceImpl.getSalesHistoryOrderDetailBySiteIdLeadDesignId(siteId, leadDesignId);

		logger.info("Returning result from getSalesHistoryOrderDetailBySiteId method");
		return new ResponseEntity<String>(strResp, HttpStatus.OK);
	}

}