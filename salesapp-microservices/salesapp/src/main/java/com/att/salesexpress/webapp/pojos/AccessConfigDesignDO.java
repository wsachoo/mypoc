package com.att.salesexpress.webapp.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessConfigDesignDO {
	String selectAccessType;
	Integer sliderSpeedValue = -1;
	String physicalInterferenceOptions;
	Integer accessInterconnectTechnology;
	String localAccessManagement;
	Integer accessTailTechnology;
	String accessArchitecture;

	public String getSelectAccessType() {
		return selectAccessType;
	}

	public void setSelectAccessType(String selectAccessType) {
		this.selectAccessType = selectAccessType;
	}

	public Integer getSliderSpeedValue() {
		return sliderSpeedValue;
	}

	public void setSliderSpeedValue(Integer sliderSpeedValue) {
		this.sliderSpeedValue = sliderSpeedValue;
	}

	public String getPhysicalInterferenceOptions() {
		return physicalInterferenceOptions;
	}

	public void setPhysicalInterferenceOptions(String physicalInterferenceOptions) {
		this.physicalInterferenceOptions = physicalInterferenceOptions;
	}

	public Integer getAccessInterconnectTechnology() {
		return accessInterconnectTechnology;
	}

	public void setAccessInterconnectTechnology(Integer accessInterconnectTechnology) {
		this.accessInterconnectTechnology = accessInterconnectTechnology;
	}

	public String getLocalAccessManagement() {
		return localAccessManagement;
	}

	public void setLocalAccessManagement(String localAccessManagement) {
		this.localAccessManagement = localAccessManagement;
	}

	public Integer getAccessTailTechnology() {
		return accessTailTechnology;
	}

	public void setAccessTailTechnology(Integer accessTailTechnology) {
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

