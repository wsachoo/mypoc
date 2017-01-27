package com.att.salesexpress.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author sw088d
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.att.salesexpress.webapp" })
public class SalesExpressPocApplication extends SpringBootServletInitializer implements CommandLineRunner {
	protected static final Logger logger = LoggerFactory.getLogger(SalesExpressPocApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SalesExpressPocApplication.class);
	}

	public static void main(String[] args) {
		logger.debug("Inside main method ");
		SpringApplication.run(SalesExpressPocApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
