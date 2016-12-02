package com.att.salesexpress.poc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller("error")
@ControllerAdvice
public class SalesExpressExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		ResponseStatus responseStatusAnnotation = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);

		return buildModelAndViewErrorPage(request, response, ex,
				responseStatusAnnotation != null ? responseStatusAnnotation.value() : HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping("*")
	public ModelAndView fallbackHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return buildModelAndViewErrorPage(request, response, null, HttpStatus.NOT_FOUND);
	}

	private ModelAndView buildModelAndViewErrorPage(HttpServletRequest request, HttpServletResponse response,
			Exception ex, HttpStatus httpStatus) {
		response.setStatus(httpStatus.value());

		ModelAndView mav = new ModelAndView("error");
		if (ex != null) {
			mav.addObject("exception", ex);
		}
		mav.addObject("url", request.getRequestURL());
		return mav;
	}
}