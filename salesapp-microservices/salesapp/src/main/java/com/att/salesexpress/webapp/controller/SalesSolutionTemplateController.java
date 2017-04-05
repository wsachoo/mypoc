package com.att.salesexpress.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.att.salesexpress.webapp.entity.SolutionTmplQuestion;
import com.att.salesexpress.webapp.service.db.DbServiceJpa;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class SalesSolutionTemplateController {

	@Autowired
	private DbServiceJpa dbServiceJpa;

	@RequestMapping(value = "/user/solutionTemplate", method = RequestMethod.GET)
	public ModelAndView configure(HttpServletRequest request) throws JsonProcessingException {
		ModelAndView view = new ModelAndView("test");
		List<SolutionTmplQuestion> result = dbServiceJpa.findAll();
		for (SolutionTmplQuestion solutionTmplQuestion : result) {
			System.out.println(solutionTmplQuestion.toString());
		}
		return view;
	}
}
