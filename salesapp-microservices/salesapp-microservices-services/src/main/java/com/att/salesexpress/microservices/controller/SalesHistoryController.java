package com.att.salesexpress.microservices.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.att.salesexpress.microservices.service.SalesHistoryService;

@RestController
@RequestMapping("/salesHistory")
public class SalesHistoryController {
	private static final Logger logger = LoggerFactory.getLogger(SalesHistoryController.class);
	
	@Autowired
	private SalesHistoryService objSalesHistoryService;

	@CrossOrigin
	@RequestMapping(value = "/getRecommendationBasedOnSalesHistory", method = RequestMethod.POST, produces = {
			"application/json" })
	public List<Map<String, Object>> getRecommendationBasedOnSalesHistory(@RequestBody Map<String, Object> paramValues,
			HttpServletRequest request) {
		logger.info("Inside getRecommendationBasedOnSalesHistory method");
		List<Map<String, Object>> result = objSalesHistoryService.getRecommendationBasedOnSalesHistory(paramValues);
		logger.info("Returning result from getRecommendationBasedOnSalesHistory method");
		return result;
	}
}
