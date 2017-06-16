package com.att.pricerd.service.restlet;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestletServiceImpl implements RestletService {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	public RestletServiceImpl() {
	}

	public final void speak(Exchange e) {
		e.setOut(e.getIn());
		e.getOut().setBody("Hello World From SalesAppService!. Going to connect to Oracle DB at : " + dbUrl);
	}
}
