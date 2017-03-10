package com.att.salesexpress.webapp.entity;

public class SalesRules {
	private String productName;
	private String ratePlan;
	private String portType;
	private Long accessSpeed;
	private Long portSpeed;
	private Double mrc;
	private Double nrc;
	private Double minMbc;
	private Long ruleId;
	private Long Id;
	private boolean blnExitsInDb;

	public boolean isBlnExitsInDb() {
		return blnExitsInDb;
	}

	public void setBlnExitsInDb(boolean blnExitsInDb) {
		this.blnExitsInDb = blnExitsInDb;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRatePlan() {
		return ratePlan;
	}

	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}

	public String getPortType() {
		return portType;
	}

	public void setPortType(String portType) {
		this.portType = portType;
	}

	public Long getAccessSpeed() {
		return accessSpeed;
	}

	public void setAccessSpeed(Long accessSpeed) {
		this.accessSpeed = accessSpeed;
	}

	public Long getPortSpeed() {
		return portSpeed;
	}

	public void setPortSpeed(Long portSpeed) {
		this.portSpeed = portSpeed;
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

	public Double getMinMbc() {
		return minMbc;
	}

	public void setMinMbc(Double minMbc) {
		this.minMbc = minMbc;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}
}
