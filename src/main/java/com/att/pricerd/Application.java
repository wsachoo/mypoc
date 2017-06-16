package com.att.pricerd;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.Servlet;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.restlet.ext.spring.SpringServerServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.att.ajsc.common.utility.SystemPropertiesLoader;

@SpringBootApplication
@ComponentScan(basePackages = "com")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class Application extends SpringBootServletInitializer {

	private static final String RESTLET_SERVLET_NAME = "RestletServlet";
	private static final String RESTLET_INIT_PARAM_NAME = "org.restlet.component";
	private static final String RESTLET_INIT_PARAM_VALUE = "restletComponent";
	private static final String RESTLET_URL_MAPPING = "/*";

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		SystemPropertiesLoader.addSystemProperties(); 
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ServletRegistrationBean RestletServletRegistrationBean() {
		
		ServletRegistrationBean registration = new ServletRegistrationBean();
		registration.setName(RESTLET_SERVLET_NAME);
		registration.setServlet((Servlet) new SpringServerServlet());
		registration.addInitParameter(RESTLET_INIT_PARAM_NAME, RESTLET_INIT_PARAM_VALUE);
		Collection<String> urlMappings = new ArrayList<String>();
		urlMappings.add(RESTLET_URL_MAPPING);
		registration.setUrlMappings(urlMappings);
		return registration;
	}

	@Bean
	public Client restClient() {
		return ClientBuilder.newClient();
	}

}