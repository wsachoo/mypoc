package com.att.salesexpress.webapp.bean.admin;

import java.util.ArrayList;
import java.util.List;

import com.att.salesexpress.webapp.entity.SalesRules;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductConfigBean {

	@JsonProperty(value = "products")
	private List<String> productNameList;

	@JsonProperty(value = "accessSpeed")
	private Long accessSpeed;

	@JsonProperty(value = "accessSpeedUnit")
	private String accessSpeedUnit;

	@JsonProperty(value = "accessType")
	private String accessType;

	@JsonProperty(value = "portSpeeds")
	private List<PortSpeedConfigBean> portSpeedBeans;

	public List<String> getProductNameList() {
		return productNameList;
	}

	public void setProductNameList(List<String> productNameList) {
		this.productNameList = productNameList;
	}

	public String getAccessSpeedUnit() {
		return accessSpeedUnit;
	}

	public void setAccessSpeedUnit(String accessSpeedUnit) {
		this.accessSpeedUnit = accessSpeedUnit;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public List<PortSpeedConfigBean> getPortSpeedBeans() {
		return portSpeedBeans;
	}

	public void setPortSpeedBeans(List<PortSpeedConfigBean> portSpeedBeans) {
		this.portSpeedBeans = portSpeedBeans;
	}

	public Long getAccessSpeed() {
		return accessSpeed;
	}

	public void setAccessSpeed(Long accessSpeed) {
		this.accessSpeed = accessSpeed;
	}
	
	@Override
	public String toString() {
		return "ProductConfigBean [productNameList=" + productNameList + ", accessSpeed=" + accessSpeed
				+ ", accessSpeedUnit=" + accessSpeedUnit + ", accessType=" + accessType + ", portSpeedBeans="
				+ portSpeedBeans + "]";
	}

	public List<SalesRules> transformToSalesRules() {
		List<SalesRules> salesRulesEntityList = new ArrayList<>();
		
		List<String> products = this.getProductNameList();

		for (String product : products) {
			
			for (PortSpeedConfigBean portSpeedConfigBean : this.getPortSpeedBeans()) {
				SalesRules objSalesRules = new SalesRules();
				objSalesRules.setProductName(product);
				objSalesRules.setRatePlan("");
				objSalesRules.setRuleId(null);
				objSalesRules.setId(null);
				objSalesRules.setMinMbc(null);
				objSalesRules.setAccessSpeed(transformSpeedUnits(this.getAccessSpeed(), this.getAccessSpeedUnit()));
				objSalesRules.setPortType(this.getAccessType());
				objSalesRules.setPortSpeed(transformSpeedUnits(portSpeedConfigBean.getSpeed(), portSpeedConfigBean.getSpeedUnit()));
				objSalesRules.setMrc(portSpeedConfigBean.getMrc());
				objSalesRules.setNrc(portSpeedConfigBean.getNrc());
				salesRulesEntityList.add(objSalesRules);
			}
		}
		
		return salesRulesEntityList;
	}

	private Long transformSpeedUnits(Long accessSpeed, String accessSpeedUnit) {
		// TODO Auto-generated method stub
		if ("Kbps".equalsIgnoreCase(accessSpeedUnit)) {
			return accessSpeed * 1000;
		}
		else if ("Mbps".equalsIgnoreCase(accessSpeedUnit)) {
			return accessSpeed * 1000 * 1000;
		}
		else if ("Gbps".equalsIgnoreCase(accessSpeedUnit)) {
			return accessSpeed * 1000 * 1000 * 1000;
		}
		
		return accessSpeed;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PortSpeedConfigBean {

	@JsonProperty(value = "speed")
	private Long speed;

	@JsonProperty(value = "speedUnit")
	private String speedUnit;

	@JsonProperty(value = "MRC")
	private Double mrc;

	@JsonProperty(value = "NRC")
	private Double nrc;

	public Long getSpeed() {
		return speed;
	}

	public void setSpeed(Long speed) {
		this.speed = speed;
	}

	public String getSpeedUnit() {
		return speedUnit;
	}

	public void setSpeedUnit(String speedUnit) {
		this.speedUnit = speedUnit;
	}

	public Double getMrc() {
		return mrc;
	}

	public void setMrc(Double mrc) {
		this.mrc = mrc;
	}

	public Double getNrc() {
		return nrc;
	}

	public void setNrc(Double nrc) {
		this.nrc = nrc;
	}

	@Override
	public String toString() {
		return "PortSpeedConfigBean [speed=" + speed + ", speedUnit=" + speedUnit + ", mrc=" + mrc + ", nrc=" + nrc
				+ "]";
	}
}
