package com.att.salesexpress.microservices.kubeclient.config;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.att.aft.dme2.api.DME2Client;
import com.att.aft.dme2.api.DME2Manager;
import com.att.aft.dme2.internal.apache.commons.lang3.exception.ExceptionUtils;

@Service
@ConfigurationProperties
@PropertySource("classpath:dme2config.properties")
public class Dme2ClientWrapper {
	private String httpMethod = "GET";
	private String grmEntryUrl;
	private String contextPath;
	private Map<String, String> queryParams = new HashMap<>();

	public String getGrmEntryUrl() {
		return grmEntryUrl;
	}

	public void setGrmEntryUrl(String grmEntryUrl) {
		this.grmEntryUrl = grmEntryUrl;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}

	public String call() {
		Properties props = new Properties();
		System.setProperty("platform", platform);
		System.setProperty("AFT_DME2_HTTP_EXCHANGE_TRACE_ON", aft_dme2_http_exchange_trace_on);
		System.setProperty("AFT_LATITUDE", aft_latitude);
		System.setProperty("AFT_LONGITUDE", aft_longitude);
		System.setProperty("AFT_ENVIRONMENT", aft_environment);
		System.setProperty("AFT_DME2_CONN_IDLE_TIMEOUTMS", aft_dme2_conn_idle_timeoutms);
		System.setProperty("DME2.DEBUG", dme2debug);

		try {
			DME2Manager mgr = new DME2Manager("JettyClient", props);

			DME2Client dmeClient = new DME2Client(mgr, new URI(grmEntryUrl), GRM_URL_CONNECT_WAIT_TIME);
			dmeClient.setPayload("");
			dmeClient.setMethod(httpMethod);
			dmeClient.setSubContext(contextPath);
			dmeClient.setQueryParams(queryParams, true);
			String response = dmeClient.sendAndWait(MICROSERVICE_RESPONSE_WAIT_TIME);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			String response = ExceptionUtils.getRootCauseMessage(e);
			return response;
		}
	}

	@Value("${platform}")
	private String platform;

	@Value("${AFT_DME2_HTTP_EXCHANGE_TRACE_ON}")
	private String aft_dme2_http_exchange_trace_on;

	@Value("${AFT_LATITUDE}")
	private String aft_latitude;

	@Value("${AFT_LONGITUDE}")
	private String aft_longitude;

	@Value("${AFT_ENVIRONMENT}")
	private String aft_environment;

	@Value("${AFT_DME2_CONN_IDLE_TIMEOUTMS}")
	private String aft_dme2_conn_idle_timeoutms;

	@Value("${DME2.DEBUG}")
	private String dme2debug;

	@Value("${MICROSERVICE_RESPONSE_WAIT_TIME}")
	private long MICROSERVICE_RESPONSE_WAIT_TIME; // in mS

	@Value("${GRM_URL_CONNECT_WAIT_TIME}")
	private long GRM_URL_CONNECT_WAIT_TIME; // in mS
}
