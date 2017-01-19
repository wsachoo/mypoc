package com.att.salesexpress.webapp.pojos;

public class PortConfigDesignDO {
	Long sliderPortSpeedValue = -1L;
	String portType;
	String routingProtocol;

	public Long getSliderPortSpeedValue() {
		return sliderPortSpeedValue;
	}

	public void setSliderPortSpeedValue(Long sliderPortSpeedValue) {
		this.sliderPortSpeedValue = sliderPortSpeedValue;
	}

	public String getPortType() {
		return portType;
	}

	public void setPortType(String portType) {
		this.portType = portType;
	}

	public String getRoutingProtocol() {
		return routingProtocol;
	}

	public void setRoutingProtocol(String routingProtocol) {
		this.routingProtocol = routingProtocol;
	}

	public String getPortProtocol() {
		return portProtocol;
	}

	public void setPortProtocol(String portProtocol) {
		this.portProtocol = portProtocol;
	}

	public String getIpVersion() {
		return ipVersion;
	}

	public void setIpVersion(String ipVersion) {
		this.ipVersion = ipVersion;
	}

	public String getRatePlan() {
		return ratePlan;
	}

	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}

	String portProtocol;
	String ipVersion;
	String ratePlan;

	@Override
	public String toString() {
		return "PortConfigDesignDO [sliderPortSpeedValue=" + sliderPortSpeedValue + ", portType=" + portType
				+ ", routingProtocol=" + routingProtocol + ", portProtocol=" + portProtocol + ", ipVersion=" + ipVersion
				+ ", ratePlan=" + ratePlan + "]";
	}
}
