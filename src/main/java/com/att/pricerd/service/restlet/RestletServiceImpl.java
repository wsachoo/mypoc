package com.att.pricerd.service.restlet;

import org.apache.camel.Exchange;

import org.springframework.stereotype.Component;

@Component
public class RestletServiceImpl implements RestletService {
	public RestletServiceImpl() {
	}

	public final void speak(Exchange e) {
		e.setOut(e.getIn());
		e.getOut().setBody("Hello World From SalesAppService!");
	}
}