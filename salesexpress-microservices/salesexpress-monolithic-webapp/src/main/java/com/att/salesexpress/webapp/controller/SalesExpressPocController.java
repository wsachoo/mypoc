package com.att.salesexpress.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	private SalesExpressOperationService salesExpressMicroServiceCallerServiceImpl;

	@RequestMapping(value = "/configure", method = RequestMethod.GET)
	public ModelAndView configure(HttpServletRequest request) {
		logger.info("Inside configure() method " + this.getClass());
		ModelAndView view = new ModelAndView("access_configure");
		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("userId");
		Long solutionId = (Long) session.getAttribute("solutionId");
		Integer transactionId = salesExpressMicroServiceCallerServiceImpl.getTransactionIdByUserIdSolutionId(userId,
				solutionId);

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
}
