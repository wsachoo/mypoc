package com.ssdf.mock.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author sw088d
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.ssdf.mock.response" })
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class SsdfApp implements CommandLineRunner {
	static final Logger logger = LoggerFactory.getLogger(SsdfApp.class);

	public static void main(String[] args) {
		SpringApplication.run(SsdfApp.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
	}
}
