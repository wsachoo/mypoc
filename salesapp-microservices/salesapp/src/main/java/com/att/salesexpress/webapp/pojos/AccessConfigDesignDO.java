package com.att.salesexpress.webapp.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessConfigDesignDO {
	String selectAccessType;
	Long sliderSpeedValue = -1L;
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

	public Long getSliderSpeedValue() {
		return sliderSpeedValue;
	}

	public void setSliderSpeedValue(Long sliderSpeedValue) {
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

