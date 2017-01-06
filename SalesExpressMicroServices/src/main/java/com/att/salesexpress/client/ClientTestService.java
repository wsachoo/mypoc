package com.att.salesexpress.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientTestService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;
	
	public static final String SALESEXPRESS_SERVICE_URL = "http://salesexpress-service";
	
	public ClientTestService() {
		
	}

	public ClientTestService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
				: "http://" + serviceUrl;
	}

	public String test() {
		System.out.println("Getting value");
		String val = restTemplate.getForObject(SALESEXPRESS_SERVICE_URL + "/siteConfigurationMetaData", String.class);
		System.out.println("value is: " + val);
		return val;
	}
}
