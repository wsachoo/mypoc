package com.att.salesexpress.poc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
@ComponentScan(basePackages = { "com.att.salesexpress.poc" })
@EnableDiscoveryClient
public class SalesExpressPocApplication implements CommandLineRunner {
	static final Logger logger = LoggerFactory.getLogger(SalesExpressPocApplication.class);

	public static void main(String[] args) {
		logger.info("inside main method ");
		SpringApplication.run(SalesExpressPocApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
	}
	
	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}	
}
