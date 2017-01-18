package com.att.salesexpress.webapp.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessSpeedDO {

	private String portType;
	
	@JsonProperty(value = "speed")
	private String accessSpeed;

	@JsonProperty(value = "MRC")
	private String mrc;

	@JsonProperty(value = "NRC")
	private String nrc;

	public String getAccessSpeed() {
		return accessSpeed;
	}

	public String getPortType() {
		return portType;
	}

	public void setPortType(String portType) {
		this.portType = portType;
	}

	public String getMrc() {
		return mrc;
	}

	public void setMrc(String mrc) {
		this.mrc = mrc;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public void setAccessSpeed(String accessSpeed) {
		this.accessSpeed = accessSpeed;
	}
}
