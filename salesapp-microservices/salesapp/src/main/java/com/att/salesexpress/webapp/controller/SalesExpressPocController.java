package com.att.salesexpress.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.att.salesexpress.webapp.service.SalesExpressOperationService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * @author sw088d initial version
 *
 */
@Controller
public class SalesExpressPocController {
	private static final Logger logger = LoggerFactory.getLogger(SalesExpressPocController.class);

	@Autowired
	private SalesExpressOperationService salesExpressOperationServiceImpl;

	@RequestMapping(value = "/configure", method = RequestMethod.GET)
	public ModelAndView configure(HttpServletRequest request) throws JsonProcessingException {
		logger.info("Inside configure() method " + this.getClass());
		ModelAndView view = new ModelAndView("access_configure");
		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("userId");
		Long solutionId = (Long) session.getAttribute("solutionId");
		String siteIdNameMap = (String) session.getAttribute("siteIdNameMap");
		Integer transactionId = salesExpressOperationServiceImpl.getTransactionIdByUserIdSolutionId(userId, solutionId);

		view.addObject("userId", userId);
		view.addObject("solutionId", solutionId);
		view.addObject("transactionId", transactionId);
		view.addObject("siteIdNameMap", siteIdNameMap);

		return view;
	}

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public ModelAndView showMap(HttpServletRequest request) throws JsonProcessingException {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = user.getUsername();
		logger.info("Solution is retrieved from authentication object is {}", userId);

		HttpSession session = request.getSession();
		session.setAttribute("userId", userId);
		session.setAttribute("loginId", userId);

		Long solutionId = (Long) session.getAttribute("solutionId");
		if (solutionId == null) {
			solutionId = salesExpressOperationServiceImpl.fetchDefaultSolutionIdByUserId(userId);
			session.setAttribute("solutionId", solutionId);
		}
		logger.debug("Solution is saved in session is: {}", solutionId);

		String siteIdNameMap = (String) session.getAttribute("siteIdNameMap");
		if (siteIdNameMap == null) {
			siteIdNameMap = salesExpressOperationServiceImpl.getSiteInfoBySolutionId(solutionId);
			session.setAttribute("siteIdNameMap", siteIdNameMap);
		}
		
		Integer transactionId = (Integer) session.getAttribute("transactionId");
		if (transactionId == null) {
			transactionId = salesExpressOperationServiceImpl.getTransactionIdByUserIdSolutionId(userId, solutionId);
			session.setAttribute("transactionId", transactionId);
		}

		ModelAndView view = new ModelAndView("home");
		String jsonString = salesExpressOperationServiceImpl.getJsonMetaDataByUserIdSolutionId(userId, solutionId);
		view.addObject("userDetail", jsonString);
		view.addObject("siteIdNameMap", siteIdNameMap);
		view.addObject("userId", userId);
		view.addObject("solutionId", solutionId);
		view.addObject("transactionId", transactionId);

		return view;
	}
}
