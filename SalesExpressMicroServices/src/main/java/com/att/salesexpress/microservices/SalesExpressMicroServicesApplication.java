package com.att.salesexpress.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SalesExpressMicroServicesApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "salesexpress-microservices");
		SpringApplication.run(SalesExpressMicroServicesApplication.class, args);
	}
}
