package com.att.salesexpress.webapp.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.att.salesexpress.webapp.service.SalesHistoryMicroServiceCallerService;
import com.fasterxml.jackson.core.JsonProcessingException;

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
	public List<Map<String, Object>> getRecommendationBasedOnSalesHistory(@RequestBody Map<String, Object> paramValues,
			HttpServletRequest request) {
		logger.info("Inside getRecommendationBasedOnSalesHistory method");
		List<Map<String, Object>> result = null;
		try {
			result = salesExpressMicroServiceCallerServiceImpl.getSalesRecommendationFromHistory(paramValues);
		} catch (JsonProcessingException e) {
			logger.error("Error occurred while invoking webservice: {}" + ExceptionUtils.getStackTrace(e));
		}
		logger.info("Returning result from getRecommendationBasedOnSalesHistory method");
		return result;
	}
}