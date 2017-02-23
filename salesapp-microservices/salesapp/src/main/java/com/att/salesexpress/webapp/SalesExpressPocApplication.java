package com.att.salesexpress.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author sw088d
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.att.salesexpress.webapp", "com.att.cio.commonheader.v3",
		"com.att.salesexpress.igloo.consumer", "com.att.edb.accessquote" })
public class SalesExpressPocApplication extends SpringBootServletInitializer implements CommandLineRunner {
	static final Logger logger = LoggerFactory.getLogger(SalesExpressPocApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SalesExpressPocApplication.class);
	}

	public static void main(String[] args) {
		logger.info("Inside main method ");
		ApplicationContext ctx = SpringApplication.run(SalesExpressPocApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
	}
}
