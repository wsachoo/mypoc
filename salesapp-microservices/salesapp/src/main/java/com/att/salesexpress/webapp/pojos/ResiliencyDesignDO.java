package com.att.salesexpress.webapp.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResiliencyDesignDO {
	@JsonProperty("selectResiliencyOption")
	private String resiliencyOption;

	public String getResiliencyOption() {
		return resiliencyOption;
	}

	public void setResiliencyOption(String resiliencyOption) {
		this.resiliencyOption = resiliencyOption;
	}

	@Override
	public String toString() {
		return "ResiliencyDesignDO [resiliencyOption=" + resiliencyOption + "]";
	}
}


