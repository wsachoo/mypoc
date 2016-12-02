package com.att.salesexpress.poc.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.att.salesexpress.poc.service.DbServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author sw088d initial version
 *
 */
@Controller
public class SalesExpressPocController {
	static final Logger logger = LoggerFactory.getLogger(SalesExpressPocController.class);
	@Autowired
	DbServiceInterface dbServiceImpl;

	@RequestMapping(value = "/configureAccess", method = RequestMethod.GET)
	public ModelAndView firstPage(HttpServletRequest request) {
		logger.info("inside firstPage method " + this.getClass());
		ModelAndView view = new ModelAndView("access_configure");
		HttpSession session = request.getSession();
		
		view.addObject("userId", session.getAttribute("userId"));
		view.addObject("solutionId", session.getAttribute("solutionId"));

		return view;
	}

	/*
	 * @RequestMapping(value = "/login/{userId}", method = RequestMethod.GET)
	 * public ModelAndView showMap(@PathVariable String userId) { ModelAndView
	 * view = new ModelAndView("show_map"); String objUserDetail =
	 * dbServiceImpl.findUserDetailByUserId(userId);
	 * view.addObject("userDetail", objUserDetail); return view; }
	 */

	@RequestMapping(value = "/login/{userId}/{solutionId}", method = RequestMethod.GET)
	public ModelAndView showMap(HttpServletRequest request, @PathVariable String userId,
			@PathVariable Integer solutionId) throws JsonProcessingException {
		System.out.println("Enter showMap with user id and solution id.");

		HttpSession session = request.getSession();
		session.setAttribute("userId", userId);
		session.setAttribute("loginId", userId);
		session.setAttribute("solutionId", solutionId);

		ModelAndView view = new ModelAndView("show_map");
		Map<String, Object> objUserDetail = dbServiceImpl.findUserDetailByUserIdSolutionId(userId, solutionId);

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(objUserDetail);
		System.out.println("Return site json as : " + jsonString);
		view.addObject("userDetail", jsonString);

		return view;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "{sitename}")
	public Map<String, Object> getSiteDetailBySiteName(@PathVariable String sitename) {
		logger.info("inside getSiteDetailsBySiteName method, site name :" + sitename);
		Map<String, Object> siteDetail = dbServiceImpl.getSiteDetailEntityBySiteName(sitename);
		return siteDetail;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getJson/{sitename}")
	public String getSiteDataBySiteName(@PathVariable String sitename) {
		logger.info("inside getSiteDataBySiteName method, sitename : " + sitename);
		String siteData = dbServiceImpl.getSiteDataByName(sitename);
		return siteData;
	}

	@RequestMapping(value = "/sendAccessTypeData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getValues(@RequestBody Map<String, Object> paramValues, final HttpServletRequest request,
			final HttpServletResponse response) throws SQLException, JsonProcessingException {
		logger.info("inside getValues method ");
		Map<String, Object> returnValues = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		// String jsonString = mapper.writeValueAsString(paramValues);
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paramValues);
		long transactionId = dbServiceImpl.updateAccessTypeData(1, jsonString);
		returnValues.put("status", "success");
		returnValues.put("transactionId", transactionId);
		return returnValues;
	}
}
