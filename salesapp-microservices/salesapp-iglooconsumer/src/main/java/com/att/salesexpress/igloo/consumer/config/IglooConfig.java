package com.att.salesexpress.igloo.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.att.salesexpress.igloo.consumer.service.IglooWSConsumerService;

@Configuration
@PropertySource("classpath:application.properties")
public class IglooConfig {

	@Autowired
	private Environment env;

	@Bean
	public Jaxb2Marshaller getMarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPaths("com.att.cio.commonheader.v3", "com.att.edb.accessquote");
		return marshaller;
	}

	@Bean
	public IglooWSConsumerService getIglooWebServiceClientSupport() {
		IglooWSConsumerService wsclient = new IglooWSConsumerService();
		wsclient.setMarshaller(getMarshaller());
		wsclient.setUnmarshaller(getMarshaller());
		wsclient.setDefaultUri(env.getProperty("iglooWebServiceUrl"));

		wsclient.setSoapActionGetAccessQuoteUrl(env.getProperty("iglooSoapActionGetAccessQuoteUrl"));
		wsclient.setSoapActionGetAccessQuoteAlternateUrl(env.getProperty("iglooSoapActionGetAccessQuoteAlternateUrl"));
		return wsclient;
	}
}
