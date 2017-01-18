package com.att.salesexpress.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SalesExpressMicroServicesApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SalesExpressMicroServicesApplication.class);
	}
	
	public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SalesExpressMicroServicesApplication.class);
        //System.setProperty("spring.config.name", "salesexpress-microservices");
        application.run(args);
	}
}
