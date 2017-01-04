package com.att.salesexpress.microservices.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.att.salesexpress.microservices.db.DbServiceInterface;

@RestController
public class SiteConfigMetaDataController {

	private static final Logger logger = LoggerFactory.getLogger(SiteConfigMetaDataController.class);

	@Resource(name = "DbServiceImpl.microservice")
	DbServiceInterface DbMicroServiceImpl;

	@RequestMapping("/siteConfigurationMetaData")
	public String getSiteMetaData(@RequestParam(value = "siteType", defaultValue = "testSite") String siteType) {
		logger.info("Inside getSiteMetaData method, sitename : " + siteType);
		String siteData = DbMicroServiceImpl.getSiteMetaData(siteType);
		return siteData;
	}
}
