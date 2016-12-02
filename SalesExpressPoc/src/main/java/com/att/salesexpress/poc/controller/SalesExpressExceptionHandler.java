package com.att.salesexpress.poc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author sw088d initial version
 *
 */
@Controller
@ControllerAdvice
public class SalesExpressExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ExceptionDetail exceptionHandlerAjax(HttpServletRequest request, HttpServletResponse response,
			Exception ex) {
		System.out.println("Inside exceptionHandlerAjax() method");
		ResponseStatus responseStatusAnnotation = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
		HttpStatus httpStatus = responseStatusAnnotation != null ? responseStatusAnnotation.value()
				: HttpStatus.INTERNAL_SERVER_ERROR;
		ExceptionDetail exp = new ExceptionDetail();
		exp.setErrorStatus(httpStatus.value());
		exp.setReasonPhrase(httpStatus.getReasonPhrase());
		exp.setErrorText(ex.getMessage());
		response.setStatus(exp.getErrorStatus());
		return exp;
	}
}

class ExceptionDetail {
	private int errorStatus;
	private String reasonPhrase;
	private String errorText;

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

	@Override
	public String toString() {
		return "ExceptionDetail [errorStatus=" + errorStatus + ", reasonPhrase=" + reasonPhrase + ", errorText="
				+ errorText + "]";
	}

}