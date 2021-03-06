package com.att.salesexpress.microservices.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.att.salesexpress.microservices.entity.SalesHistoryDetail;
import com.att.salesexpress.microservices.entity.SalesHistoryStripped;
import com.att.salesexpress.microservices.entity.SalesRulesMisExpDetail;
import com.att.salesexpress.microservices.service.SalesHistoryService;

@RestController
public class SalesHistoryController {
	private static final Logger logger = LoggerFactory.getLogger(SalesHistoryController.class);

	@Autowired
	private SalesHistoryService objSalesHistoryService;

	@RequestMapping(value = "/salesHistory/getRecommendationBasedOnSalesHistory", method = RequestMethod.GET, produces = {
			"application/json" })
	public HttpEntity<List<SalesHistoryStripped>> getRecommendationBasedOnSalesHistory(
			@RequestParam Map<String, Object> paramValues, HttpServletRequest request) {
		logger.info("Inside getRecommendationBasedOnSalesHistory method");
		List<SalesHistoryStripped> result = objSalesHistoryService.getRecommendationBasedOnSalesHistory(paramValues);
		
		if((!result.isEmpty()) && result.get(0).getRuleDesignId() != 0){
			for (SalesHistoryStripped objSalesHistoryDO : result) {
				Link selfLink = linkTo(
						methodOn(SalesHistoryController.class).getSalesHistoryOrderDetailByDesignRuleId(
								objSalesHistoryDO.getRuleDesignId())).withRel("siteDetail");
				objSalesHistoryDO.add(selfLink);
			}
		}
		else{
			for (SalesHistoryStripped objSalesHistoryDO : result) {
				Link selfLink = linkTo(
						methodOn(SalesHistoryController.class).getSalesHistoryOrderDetailBySiteIdLeadDesignId(
								objSalesHistoryDO.getSiteId(), objSalesHistoryDO.getLeadDesignId())).withRel("siteDetail");
				objSalesHistoryDO.add(selfLink);
			}
		}	
		
		logger.info("Returning result from getRecommendationBasedOnSalesHistory method");
		return new ResponseEntity<List<SalesHistoryStripped>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/salesHistory/getSalesHistoryOrderDetailBySiteIdLeadDesignId", method = RequestMethod.GET, produces = {
			"application/json" })
	@CrossOrigin
	public HttpEntity<SalesHistoryDetail> getSalesHistoryOrderDetailBySiteIdLeadDesignId(
			@RequestParam(value = "siteId", required = true) Long siteId,
			@RequestParam(value = "leadDesignId", required = true) Long leadDesignId) {
		logger.info("Inside getSalesHistoryOrderDetailBySiteId method");

		SalesHistoryDetail objSalesHistoryDetail = objSalesHistoryService
				.getSalesHistoryOrderDetailBySiteIdLeadDesignId(siteId, leadDesignId);
		Link selfLink = linkTo(methodOn(SalesHistoryController.class)
				.getSalesHistoryOrderDetailBySiteIdLeadDesignId(siteId, leadDesignId)).withSelfRel();
		objSalesHistoryDetail.getId().add(selfLink);

		logger.info("Returning result from getSalesHistoryOrderDetailBySiteId method");
		return new ResponseEntity<SalesHistoryDetail>(objSalesHistoryDetail, HttpStatus.OK);
	}

	@RequestMapping(value = "/salesHistory/getSalesPercentageByAccessType", method = RequestMethod.GET, produces = {
			"application/json" })
	@CrossOrigin
	public HttpEntity<List<Map<String, Object>>> getSalesPercentageByAccessType(
			@RequestParam Map<String, Object> paramValues, HttpServletRequest request) {
		logger.info("Inside getSalesHistoryOrderDetailBySiteId method");

		List<Map<String, Object>> stats = objSalesHistoryService.getSalesPercentageByAccessType(paramValues);

		logger.info("Returning result from getSalesHistoryOrderDetailBySiteId method");
		return new ResponseEntity<List<Map<String, Object>>>(stats, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/salesHistory/getSalesHistoryOrderDetailByDesignRuleId", method = RequestMethod.GET, produces = {
	"application/json" })
	@CrossOrigin
	public HttpEntity<SalesRulesMisExpDetail> getSalesHistoryOrderDetailByDesignRuleId(
			
			@RequestParam(value = "designRuleId", required = true) int designRuleId) {
		logger.info("Inside getSalesHistoryOrderDetailByDesignRuleId method");

		SalesRulesMisExpDetail objSalesRulesmisExpDetail = objSalesHistoryService
				.getSalesHistoryOrderDetailByDesignRuleId(designRuleId);
		Link selfLink = linkTo(methodOn(SalesHistoryController.class)
				.getSalesHistoryOrderDetailByDesignRuleId(designRuleId)).withSelfRel();
		objSalesRulesmisExpDetail.getId().add(selfLink);

		logger.info("Returning result from getSalesHistoryOrderDetailByDesignRuleId method");
		return new ResponseEntity<SalesRulesMisExpDetail>(objSalesRulesmisExpDetail, HttpStatus.OK);
	}
}
