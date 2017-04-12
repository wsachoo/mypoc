/*package com.att.salesexpress.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

*//**
 * 
 * @author sw088d initial version
 *
 *//*
@Controller
@ControllerAdvice
public class SalesHistoryConsumerExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(SalesHistoryConsumerExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ExceptionDetail exceptionHandlerAjax(HttpServletRequest request, HttpServletResponse response,
			Exception ex) {
		logger.info("Inside exceptionHandlerAjax() method");
		ResponseStatus responseStatusAnnotation = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
		HttpStatus httpStatus = responseStatusAnnotation != null ? responseStatusAnnotation.value()
				: HttpStatus.INTERNAL_SERVER_ERROR;
		ExceptionDetail exp = new ExceptionDetail();
		exp.setErrorStatus(httpStatus.value());
		exp.setReasonPhrase(httpStatus.getReasonPhrase());
		exp.setErrorText(ex.getMessage());
		String errStackTrace = ExceptionUtils.getStackTrace(ex);
		exp.setStackTrace(errStackTrace);
		logger.info("Exception stacktrace: " + errStackTrace);
		response.setStatus(exp.getErrorStatus());

		return exp;
	}
}

class ExceptionDetail {
	private int errorStatus;
	private String reasonPhrase;
	private String errorText;
	private String stackTrace;

	public int getErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(int errorStatus) {
		this.errorStatus = errorStatus;
	}

	public String getErrorText() {
		return errorText;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
}*/