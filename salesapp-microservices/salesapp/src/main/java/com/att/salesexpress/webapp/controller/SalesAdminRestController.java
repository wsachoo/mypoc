package com.att.salesexpress.webapp.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.att.salesexpress.webapp.bean.admin.ProductConfigBean;
import com.att.salesexpress.webapp.service.SalesAdminOperationService;

@Controller
public class SalesAdminRestController {

	private static final Logger logger = LoggerFactory.getLogger(SalesAdminRestController.class);

	@Autowired
	private SalesAdminOperationService salesAdminOperationService;

	@RequestMapping(value = "/admin/addServicesFeatures", method = RequestMethod.POST, produces = {
			"application/json" })
	@ResponseBody
	public ResponseEntity<Map<String, Object>> updateServicesFeatures(@RequestBody Map<String, Object> paramValues,
			HttpServletRequest request) throws SQLException, IOException {
		logger.debug("Inside addServicesFeatures() method");
		salesAdminOperationService.updateServicesFeaturesConfiguration(paramValues);
		Map<String, Object> returnValues = new HashMap<String, Object>();
		returnValues.put("status", "success");
		logger.debug("Services and features configuration added successfully.");
		return new ResponseEntity<Map<String, Object>>(returnValues, HttpStatus.OK);

	}

	@RequestMapping(value = "/admin/deleteServicesFeatures", method = RequestMethod.POST, produces = {
			"application/json" })
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteServicesFeatures(@RequestBody Map<String, Object> paramValues,
			HttpServletRequest request) throws SQLException, IOException {
		logger.debug("Inside deleteServicesFeatures() method");
		salesAdminOperationService.deleteServicesFeaturesConfiguration(paramValues);
		Map<String, Object> returnValues = new HashMap<String, Object>();
		returnValues.put("status", "success");
		logger.debug("Services and features configuration deleted successfully.");
		return new ResponseEntity<Map<String, Object>>(returnValues, HttpStatus.OK);

	}

	@RequestMapping(value = "/admin/saveProductConfiguration", method = RequestMethod.POST, produces = {
			"application/json" }, consumes = { "application/json" })
	@ResponseBody
	public ResponseEntity<Map<String, Object>> postProductConfiguration(
			@RequestBody ProductConfigBean objProductConfigBean, HttpServletRequest request)
			throws SQLException, IOException {
		logger.debug("Inside postProductConfiguration() method");

		salesAdminOperationService.saveProductConfiguration(objProductConfigBean);

		Map<String, Object> returnValues = new HashMap<String, Object>();
		returnValues.put("status", "success");
		logger.debug("Product Configuration saved successfully.");
		return new ResponseEntity<Map<String, Object>>(returnValues, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/deleteProductConfiguration", method = RequestMethod.POST, produces = {
			"application/json" }, consumes = { "application/json" })
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteProductConfiguration(
			@RequestBody ProductConfigBean objProductConfigBean, HttpServletRequest request)
			throws SQLException, IOException {
		logger.debug("Inside deleteProductConfiguration() method");

		salesAdminOperationService.deleteProductConfiguration(objProductConfigBean);

		Map<String, Object> returnValues = new HashMap<String, Object>();
		returnValues.put("status", "success");
		logger.debug("Product Configuration deleted successfully.");
		return new ResponseEntity<Map<String, Object>>(returnValues, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/getAllProducts", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getAllProducts() {
		logger.debug("Inside getAllProducts() method");
		return salesAdminOperationService.getAllProductsToConfigure();
	}
}
