package com.att.salesexpress.webapp.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.att.salesexpress.webapp.service.SalesAdminOperationService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class SalesAdminWebController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SalesAdminOperationService salesAdminOperationService;

	@RequestMapping(value = { "/admin", "/admin/home" }, method = RequestMethod.GET)
	public ModelAndView showMap(HttpServletRequest request) throws JsonProcessingException {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = user.getUsername();
		logger.info("Solution is retrieved from authentication object is {}", userId);

		ModelAndView view = new ModelAndView("admin");
		return view;
	}
	
	@RequestMapping(value = { "/admin/adminLandingPage"}, method = RequestMethod.GET)
	public ModelAndView adminLandingPage(HttpServletRequest request) throws JsonProcessingException {
		ModelAndView view = new ModelAndView("adminLandingPage");
		return view;
	}
	
	@RequestMapping(value = { "/admin/productConfiguration"}, method = RequestMethod.GET)
	public ModelAndView productConfiguration(HttpServletRequest request) throws JsonProcessingException {
		ModelAndView view = new ModelAndView("admin/productConfiguration");
		return view;
	}	
	
	@RequestMapping(value = { "/admin/serviceAndFeaturesConfiguration"}, method = RequestMethod.GET)
	public ModelAndView serviceAndFeaturesConfiguration(HttpServletRequest request) throws JsonProcessingException {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = user.getUsername();
		logger.info("Solution is retrieved from authentication object is {}", userId);
		ModelAndView view = new ModelAndView("admin/serviceAndFeaturesConfiguration");
		return view;
	}

	@RequestMapping(value = { "/admin/loadConfigureNewComponentPage"}, method = RequestMethod.GET)
	public ModelAndView p1(HttpServletRequest request) throws JsonProcessingException {
		List<String> products = salesAdminOperationService.getAllProductsToConfigure();
		ModelAndView view = new ModelAndView("admin/configureNewComponent");
		view.addObject("productList", products);		
		return view;
	}	
	@RequestMapping(value = { "/admin/loadModifyExistingComponentPage"}, method = RequestMethod.GET)
	public ModelAndView p2(HttpServletRequest request) throws JsonProcessingException {
		List<String> products = salesAdminOperationService.getAllProductsToConfigure();
		ModelAndView view = new ModelAndView("admin/modifyExistingComponent");
		view.addObject("productList", products);
		return view;
	}	

}