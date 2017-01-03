package com.att.salesexpress.poc.microservices.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.att.salesexpress.poc.microservices.db.DbServiceInterface;

@RestController
public class SiteMetaDataController {

	private static final Logger logger = LoggerFactory.getLogger(SiteMetaDataController.class);

	@Resource(name="DbServiceImpl.microservice")
	DbServiceInterface DbMicroServiceImpl;

	@RequestMapping("/siteMetaData")
	public String getSiteMetaData(@RequestParam(value = "siteType", defaultValue = "testSite") String siteType) {
		logger.info("Inside getSiteMetaData method, sitename : " + siteType);
		String siteData = DbMicroServiceImpl.getSiteMetaData(siteType);
		return siteData;
	}
}
