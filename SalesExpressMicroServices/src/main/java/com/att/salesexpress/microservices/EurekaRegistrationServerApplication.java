package com.att.salesexpress.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEurekaServer
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class EurekaRegistrationServerApplication {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "eureka-registration-server");
		SpringApplication.run(EurekaRegistrationServerApplication.class, args);
	}
}
