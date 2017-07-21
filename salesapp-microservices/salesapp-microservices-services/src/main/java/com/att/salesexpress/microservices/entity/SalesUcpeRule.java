package com.att.salesexpress.microservices.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;


/**
 * The persistent class for the SALES_UCPE_RULES database table.
 * 
 */
@Entity
@Table(name="SALES_UCPE_RULES")
@NamedQuery(name="SalesUcpeRule.findAll", query="SELECT s FROM SalesUcpeRule s")
public class SalesUcpeRule extends ResourceSupport implements Serializable {
	private static final long serialVersionUID = 1L;

	private transient String productType = "FLEXWARE-UCPE";
	
	@Id
	@Column(name="RULE_ID")
	private long ruleId;

	@Column(name="ACTIVE_YN")
	private String activeYn;

	private String currency;

	@Column(name="DEVICE_ID")
	private String deviceId;

	@Column(name="EXTERNAL_RATE_ID")
	private String externalRateId;

	@Column(name="MANUFACTURE_NAME")
	private String manufactureName;

	@Column(name="MODEL_NAME")
	private String modelName;

	@Column(name="MRC_RATE")
	private BigDecimal mrcRate;

	@Column(name="NRC_RATE")
	private BigDecimal nrcRate;

	@Column(name="STORAGE")
	private String storage;

	public SalesUcpeRule() {
	}

	public long getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}

	public String getActiveYn() {
		return this.activeYn;
	}

	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getExternalRateId() {
		return this.externalRateId;
	}

	public void setExternalRateId(String externalRateId) {
		this.externalRateId = externalRateId;
	}

	public String getManufactureName() {
		return this.manufactureName;
	}

	public void setManufactureName(String manufactureName) {
		this.manufactureName = manufactureName;
	}

	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public BigDecimal getMrcRate() {
		return this.mrcRate;
	}

	public void setMrcRate(BigDecimal mrcRate) {
		this.mrcRate = mrcRate;
	}

	public BigDecimal getNrcRate() {
		return this.nrcRate;
	}

	public void setNrcRate(BigDecimal nrcRate) {
		this.nrcRate = nrcRate;
	}

	public String getStorage() {
		return this.storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}
	
	public String getProductType() {
		return productType;
	}
}