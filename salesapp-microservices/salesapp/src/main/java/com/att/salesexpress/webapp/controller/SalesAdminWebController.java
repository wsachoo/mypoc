package com.att.salesexpress.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
// @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
public class SalesAdminWebController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = { "/admin", "/admin/home" }, method = RequestMethod.GET)
	public ModelAndView showMap(HttpServletRequest request) throws JsonProcessingException {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId = user.getUsername();
		logger.debug("Solution is retrieved from authentication object is {}", userId);

		ModelAndView view = new ModelAndView("admin");
		return view;
	}
}

