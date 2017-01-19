package com.att.salesexpress.webapp.pojos;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDesignSelectionDO {
	private String userId;
	private Long solutionId;
	private Long transactionId;

	@JsonProperty("sites")
	private Map<String, UserSiteDesignDO> siteDesignList = new HashMap<String, UserSiteDesignDO>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Map<String, UserSiteDesignDO> getSiteDesignList() {
		return siteDesignList;
	}

	public void setSiteDesignList(Map<String, UserSiteDesignDO> siteDesignList) {
		this.siteDesignList = siteDesignList;
	}

	@Override
	public String toString() {
		return "UserSolutionDesignDO [userId=" + userId + ", solutionId=" + solutionId + ", transactionId="
				+ transactionId + ", siteDesignList=" + siteDesignList + "]";
	}
}

class UserSiteDesignDO {
	@JsonProperty("siteId")
	private Long siteId;

	@JsonProperty("resiliencyConfig")
	private ResiliencyDesignDO resiliencyDesign;

	@JsonProperty("accessConfig")
	private AccessConfigDesignDO accessConfigDesign;

	@JsonProperty("portConfig")
	private PortConfigDesignDO portConfigDesign;

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public ResiliencyDesignDO getResiliencyDesign() {
		return resiliencyDesign;
	}

	public void setResiliencyDesign(ResiliencyDesignDO resiliencyDesign) {
		this.resiliencyDesign = resiliencyDesign;
	}

	public AccessConfigDesignDO getAccessConfigDesign() {
		return accessConfigDesign;
	}

	public void setAccessConfigDesign(AccessConfigDesignDO accessConfigDesign) {
		this.accessConfigDesign = accessConfigDesign;
	}

	public PortConfigDesignDO getPortConfigDesign() {
		return portConfigDesign;
	}

	public void setPortConfigDesign(PortConfigDesignDO portConfigDesign) {
		this.portConfigDesign = portConfigDesign;
	}

	@Override
	public String toString() {
		return "UserSiteDesignDO [siteId=" + siteId + ", resiliencyDesign=" + resiliencyDesign + ", accessConfigDesign="
				+ accessConfigDesign + ", portConfigDesign=" + portConfigDesign + "]";
	}
}

class ResiliencyDesignDO {
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

class PortConfigDesignDO {
	String sliderPortSpeedValue;
	String portType;
	String routingProtocol;

	public String getSliderPortSpeedValue() {
		return sliderPortSpeedValue;
	}

	public void setSliderPortSpeedValue(String sliderPortSpeedValue) {
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

@JsonIgnoreProperties(value = { "accessRequired" })
class AccessConfigDesignDO {
	String selectAccessType;
	String sliderSpeedValue;
	String physicalInterferenceOptions;
	String accessInterconnectTechnology;
	String localAccessManagement;
	String accessTailTechnology;
	String accessArchitecture;

	public String getSelectAccessType() {
		return selectAccessType;
	}

	public void setSelectAccessType(String selectAccessType) {
		this.selectAccessType = selectAccessType;
	}

	public String getSliderSpeedValue() {
		return sliderSpeedValue;
	}

	public void setSliderSpeedValue(String sliderSpeedValue) {
		this.sliderSpeedValue = sliderSpeedValue;
	}

	public String getPhysicalInterferenceOptions() {
		return physicalInterferenceOptions;
	}

	public void setPhysicalInterferenceOptions(String physicalInterferenceOptions) {
		this.physicalInterferenceOptions = physicalInterferenceOptions;
	}

	public String getAccessInterconnectTechnology() {
		return accessInterconnectTechnology;
	}

	public void setAccessInterconnectTechnology(String accessInterconnectTechnology) {
		this.accessInterconnectTechnology = accessInterconnectTechnology;
	}

	public String getLocalAccessManagement() {
		return localAccessManagement;
	}

	public void setLocalAccessManagement(String localAccessManagement) {
		this.localAccessManagement = localAccessManagement;
	}

	public String getAccessTailTechnology() {
		return accessTailTechnology;
	}

	public void setAccessTailTechnology(String accessTailTechnology) {
		this.accessTailTechnology = accessTailTechnology;
	}

	public String getAccessArchitecture() {
		return accessArchitecture;
	}

	public void setAccessArchitecture(String accessArchitecture) {
		this.accessArchitecture = accessArchitecture;
	}

	@Override
	public String toString() {
		return "AccessConfigDesignDO [selectAccessType=" + selectAccessType + ", sliderSpeedValue=" + sliderSpeedValue
				+ ", physicalInterferenceOptions=" + physicalInterferenceOptions + ", accessInterconnectTechnology="
				+ accessInterconnectTechnology + ", localAccessManagement=" + localAccessManagement
				+ ", accessTailTechnology=" + accessTailTechnology + ", accessArchitecture=" + accessArchitecture + "]";
	}

}