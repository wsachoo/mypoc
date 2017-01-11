package com.att.salesexpress.webapp.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.att.salesexpress.webapp.db.DbService;
import com.att.salesexpress.webapp.service.SalesExpressMicroServiceCallerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author sw088d initial version
 *
 */
@Controller
public class SalesExpressPocController {
	private static final Logger logger = LoggerFactory.getLogger(SalesExpressPocController.class);

	private static final Integer DEFAULT_SITE_ID = 1; // Using default value for
														// POC. During actual
														// implementation this
														// default will be taken
														// from request.

	@Autowired
	DbService dbServiceImpl;

	@Autowired
	private SalesExpressMicroServiceCallerService salesExpressMicroServiceCallerServiceImpl;

	@RequestMapping(value = "/configure", method = RequestMethod.GET)
	public ModelAndView configure(HttpServletRequest request) {
		logger.info("Inside configure() method " + this.getClass());
		ModelAndView view = new ModelAndView("access_configure");
		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("userId");
		Long solutionId = (Long) session.getAttribute("solutionId");
		Integer transactionId = dbServiceImpl.getTransactionIdByUserIdSolutionId(userId, solutionId);

		view.addObject("userId", userId);
		view.addObject("solutionId", solutionId);
		view.addObject("transactionId", transactionId);

		return view;
	}

	@RequestMapping(value = "/login/{userId}/{solutionId}", method = RequestMethod.GET)
	public ModelAndView showMap(HttpServletRequest request, @PathVariable String userId, @PathVariable Long solutionId)
			throws JsonProcessingException {
		logger.debug("Enter showMap with user id and solution id.");

		HttpSession session = request.getSession();
		session.setAttribute("userId", userId);
		session.setAttribute("loginId", userId);
		session.setAttribute("solutionId", solutionId);

		String jsonString = salesExpressMicroServiceCallerServiceImpl.getJsonMetaDataByUserIdSolutionId(userId,
				solutionId);

		ModelAndView view = new ModelAndView("show_map");
		view.addObject("userDetail", jsonString);

		return view;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getMetaData/{siteType}")
	public String getSiteDataBySiteName(@PathVariable String siteType) {
		logger.info("Inside getSiteDataBySiteName method, sitename : " + siteType);
		String siteMetaData = salesExpressMicroServiceCallerServiceImpl
				.getSiteConfigurationMetaDataBySiteType(siteType);
		return siteMetaData;
	}

	@RequestMapping(value = "/postSiteConfiguration", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getValues(@RequestBody Map<String, Object> paramValues, final HttpServletRequest request,
			final HttpServletResponse response)
			throws SQLException, JsonProcessingException, ServletRequestBindingException {
		logger.info("Inside getValues() method ");

		Object userId = (String) paramValues.get("userId");
		Object solutionId = paramValues.get("solutionId");
		String strTransactionId = (String) paramValues.get("transactionId");

		logger.info("Request parameters are userId: {}, solutionId: {}, transactionId: {}", userId, solutionId,
				strTransactionId);

		Long lSolutionId = Long.parseLong(solutionId.toString());
		Map<String, Object> returnValues = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		// String jsonString = mapper.writeValueAsString(paramValues);
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paramValues);
		long transactionId = -1;

		if (StringUtils.isBlank(strTransactionId)) {
			transactionId = dbServiceImpl.insertSiteConfigurationData(userId.toString(), lSolutionId, DEFAULT_SITE_ID,
					jsonString);
		} else {
			transactionId = Long.parseLong(strTransactionId);
			dbServiceImpl.updateSiteConfigurationData(transactionId, jsonString);
		}
		returnValues.put("status", "success");
		returnValues.put("transactionId", transactionId);
		return returnValues;
	}

	@RequestMapping(value = "/serviceFeatures", method = RequestMethod.GET)
	public ModelAndView serviceFeatures(HttpServletRequest request) {
		logger.info("Inside serviceFeatures(0 method " + this.getClass());
		ModelAndView view = new ModelAndView("service_features");
		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("userId");
		List service_list = dbServiceImpl.getServices();

		view.addObject("service_list", service_list);

		return view;
	}

	@RequestMapping(value = "/postServiceFeaturesOptions", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveServiceFeaturesData(@RequestBody Map<String, Object> paramValues,
			HttpServletRequest request) throws JsonProcessingException, SQLException {
		logger.info("Inside saveServiceFeaturesData " + paramValues);
		ModelAndView view = new ModelAndView("service_features");
		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("userId");
		Long solutionId = (Long) session.getAttribute("solutionId");
		logger.info("Inside saveServiceFeaturesData, user_id : " + userId + " solutionId :" + solutionId);
		ObjectMapper mapper = new ObjectMapper();

		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(paramValues);
		logger.info("JSON string for service features : " + jsonString);

		dbServiceImpl.updateServiceFeaturesData(jsonString, solutionId, userId);

		Map<String, Object> returnValues = new HashMap<String, Object>();
		returnValues.put("status", "success");

		return returnValues;
	}

	@RequestMapping(value = "/results", method = RequestMethod.GET)
	public ModelAndView results(HttpServletRequest request) throws IOException, JSONException {
		logger.info("Inside serviceFeatures(0 method " + this.getClass());
		ModelAndView view = new ModelAndView("salesexpress_results");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		Long solutionId = (Long) session.getAttribute("solutionId");

		Map<String, String> speedMap = dbServiceImpl.getAccessData(solutionId);

		String resultDataJSON = dbServiceImpl.getResultsData(speedMap.get("accessSpeed"), speedMap.get("portSpeed"));

		// List resultDataJSON = dbServiceImpl.getResultsData(accessSpeed);

		view.addObject("resultData", resultDataJSON);
		return view;
	}

}
