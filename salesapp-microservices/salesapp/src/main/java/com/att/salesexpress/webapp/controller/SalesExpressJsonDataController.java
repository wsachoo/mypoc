package com.att.salesexpress.webapp.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.att.salesexpress.webapp.service.SalesExpressOperationService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class SalesExpressJsonDataController {
	private static final Logger logger = LoggerFactory.getLogger(SalesExpressJsonDataController.class);

	@Autowired
	private SalesExpressOperationService salesExpressOperationServiceImpl;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getMetaData/{siteType}")
	public ResponseEntity<String> getSiteDataBySiteName(@PathVariable String siteType) {
		logger.info("Inside getSiteDataBySiteName method, sitename : " + siteType);
		String siteMetaData = salesExpressOperationServiceImpl.getSiteConfigurationMetaDataBySiteType(siteType);
		return new ResponseEntity<String>(siteMetaData, HttpStatus.OK);
	}

	@RequestMapping(value = "/postSiteConfiguration", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> postSiteConfiguration(@RequestBody Map<String, Object> paramValues,
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletRequestBindingException, SQLException, IOException {
		logger.info("Inside postSiteConfiguration() method ");

		Object userId = (String) paramValues.get("userId");
		Object solutionId = paramValues.get("solutionId");
		String strTransactionId = (String) paramValues.get("transactionId");

		logger.info("Request parameters are userId: {}, solutionId: {}, transactionId: {}", userId, solutionId,
				strTransactionId);

		Long lSolutionId = Long.parseLong(solutionId.toString());
		Map<String, Object> returnValues = new HashMap<>();
		returnValues = salesExpressOperationServiceImpl.saveSiteConfigurationData(paramValues, userId, strTransactionId,
				lSolutionId);
		return new ResponseEntity<Map<String, Object>>(returnValues, HttpStatus.OK);

	}

	@RequestMapping(value = "/postServiceFeaturesOptions", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> saveServiceFeaturesData(@RequestBody Map<String, Object> paramValues,
			HttpServletRequest request) throws JsonProcessingException, SQLException {
		logger.info("Inside saveServiceFeaturesData method");
		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("userId");
		Long solutionId = (Long) session.getAttribute("solutionId");
		logger.info("Inside saveServiceFeaturesData, user_id : " + userId + " solutionId :" + solutionId);

		Map<String, Object> returnValues = new HashMap<String, Object>();
		salesExpressOperationServiceImpl.updateServiceFeaturesData(paramValues, solutionId, userId);
		returnValues.put("status", "success");
		return new ResponseEntity<Map<String, Object>>(returnValues, HttpStatus.OK);

	}

	@ResponseBody
	@RequestMapping(value = "results", method = RequestMethod.GET)
	public ResponseEntity<String> getResults(HttpServletRequest request, @RequestParam Map<String, Object> paramValues)
			throws IOException, JSONException {
		logger.info("Inside results() method " + this.getClass());
		HttpSession session = request.getSession();
		Long solutionId = (Long) session.getAttribute("solutionId");
		logger.debug("Solution is retrieved from session is {}", solutionId);
		String resultDataJSON = salesExpressOperationServiceImpl.getResultsData(solutionId, paramValues);
		/* salesExpressOperationServiceImpl.getResultDataByProc(); */
		return new ResponseEntity<String>(resultDataJSON, HttpStatus.OK);

	}

	@ResponseBody
	@RequestMapping(value = "getPortSpeedsByAccessSpeed", method = RequestMethod.GET)
	public ResponseEntity<String> getPortSpeedsByAccessSpeed(@RequestParam Map<String, Object> paramValues)
			throws IOException, JSONException {
		logger.info("Inside getPortSpeedsByAccessSpeed() method " + this.getClass());
		String accessType = (String) paramValues.get("accessType");
		String accessSpeed = (String) paramValues.get("accessSpeed");

		String resultDataJSON = salesExpressOperationServiceImpl.getPortSpeedsByAccessData(accessType, accessSpeed);
		logger.debug("Port speeds retrieved are {}", resultDataJSON);
		return new ResponseEntity<String>(resultDataJSON, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "getAllAccessSpeeds", method = RequestMethod.GET)
	public ResponseEntity<String> getAllAccessSpeeds() throws JsonProcessingException {
		String accessSpeedsJson = salesExpressOperationServiceImpl.getAllAccessSpeeds();
		return new ResponseEntity<String>(accessSpeedsJson, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "getServiceFeaturesMetaData/{siteType}")
	public ResponseEntity<String> getServFeaturesMDataBySiteName(@PathVariable String siteType) {
		logger.info("Inside getServFeaturesMDataBySiteName method, sitename : " + siteType);
		String servFeaturesMetaData = salesExpressOperationServiceImpl.getServiceFeaturesMetaDataBySiteName(siteType);
		return new ResponseEntity<String>(servFeaturesMetaData, HttpStatus.OK);
	}
}
