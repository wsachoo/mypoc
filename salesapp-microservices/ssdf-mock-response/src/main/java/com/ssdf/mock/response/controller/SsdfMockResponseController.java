package com.ssdf.mock.response.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author sw088d initial version
 *
 */
@Controller
public class SsdfMockResponseController {
	private static final Logger logger = LoggerFactory.getLogger(SsdfMockResponseController.class);

	@SuppressWarnings({ "resource", "unchecked" })
	@RequestMapping(value = "/ssdf/pricingschedule/v1/service/previewPricingSchedules", method = RequestMethod.POST, produces = {
			"application/json" })
	@CrossOrigin
	public ResponseEntity<String> previewPricingSchedules(@RequestBody Map<String, Object> paramValues,
			HttpServletRequest request) throws IOException {
		logger.info("Inside getSalesHistoryOrderDetailBySiteId method");

		Map<String, Object> objSol = (Map<String, Object>) paramValues.get("solution");
		String optId = (String) objSol.get("opportunityId");

		File file = new File(getClass().getClassLoader().getResource(optId + ".json").getFile());

		InputStream is = new FileInputStream(file);
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		String line = buf.readLine();
		StringBuilder sb = new StringBuilder();
		while (line != null) {
			sb.append(line).append("\n");
			line = buf.readLine();
		}
		String fileAsString = sb.toString();

		logger.info("Returning result from getSalesHistoryOrderDetailBySiteId method");
		return new ResponseEntity<String>(fileAsString, HttpStatus.OK);
	}

}