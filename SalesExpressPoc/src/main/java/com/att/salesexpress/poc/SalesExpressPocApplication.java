package com.att.salesexpress.poc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author sw088d
 *
 */
@EnableAutoConfiguration
@SpringBootConfiguration
@ComponentScan(basePackages = { "com.att.salesexpress.poc" })
public class SalesExpressPocApplication implements CommandLineRunner {
	static final Logger logger = LoggerFactory.getLogger(SalesExpressPocApplication.class);

	public static void main(String[] args) {
		logger.info("inside main method ");
		SpringApplication.run(SalesExpressPocApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
	}
}
