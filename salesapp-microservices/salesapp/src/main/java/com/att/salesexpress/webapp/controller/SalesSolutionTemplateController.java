package com.att.salesexpress.webapp.controller;

import java.util.List;
import java.util.Map;

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

import com.att.salesexpress.webapp.entity.SolutionTmplQuestion;
import com.att.salesexpress.webapp.service.SalesExpressOperationService;
import com.att.salesexpress.webapp.service.db.DbServiceJpa;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class SalesSolutionTemplateController {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@Autowired
	private SalesExpressOperationService salesExpressOperationServiceImpl;

	@Autowired
	private DbServiceJpa dbServiceJpa;

	@RequestMapping(value = "/user/solutionTemplate/sampleTest", method = RequestMethod.GET)
	public ModelAndView sampleTest(HttpServletRequest request) throws JsonProcessingException {
		ModelAndView view = new ModelAndView("test");
		List<SolutionTmplQuestion> result = dbServiceJpa.findAll();
		for (SolutionTmplQuestion solutionTmplQuestion : result) {
			System.out.println(solutionTmplQuestion.toString());
		}
		return view;
	}
	
	@RequestMapping(value = { "/user", "/user/solutionTemplate" }, method = RequestMethod.GET)
	public ModelAndView showMap(HttpServletRequest request) throws JsonProcessingException {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = user.getUsername();
		logger.debug("Solution is retrieved from authentication object is {}", userId);

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
		ModelAndView view = new ModelAndView("solution-template");	
		Map<String, Object> userSitesData = salesExpressOperationServiceImpl.getJsonMetaDataByUserIdSolutionId(userId, solutionId);
		view.addObject("userSitesData", userSitesData);
		view.addObject("userDetail", jacksonObjectMapper.writeValueAsString(userSitesData));
		view.addObject("siteIdNameMap", siteIdNameMap);
		view.addObject("userId", userId);
		view.addObject("solutionId", solutionId);
		view.addObject("transactionId", transactionId);
		return view;
	}
	
	@RequestMapping(value = { "/user/stepWizard"}, method = RequestMethod.GET)
	public ModelAndView getSolutionStepWizard(HttpServletRequest request) throws JsonProcessingException {
		logger.debug("inside getSolutionStepWizard() method");
		ModelAndView view = new ModelAndView("step-wizard");
		return view;
	}
}
