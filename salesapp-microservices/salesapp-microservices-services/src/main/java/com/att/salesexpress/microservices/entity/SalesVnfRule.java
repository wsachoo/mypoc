package com.att.salesexpress.microservices.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;


/**
 * The persistent class for the SALES_VNF_RULES database table.
 * 
 */
@Entity
@Table(name="SALES_VNF_RULES")
@NamedQuery(name="SalesVnfRule.findAll", query="SELECT s FROM SalesVnfRule s")
public class SalesVnfRule extends ResourceSupport implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private transient String productType = "FLEXWARE-VNF";

	@Column(name="RULE_ID")
	@Id
	private BigDecimal ruleId;

	@Column(name="ACTIVE_YN")
	private String activeYn;

	private String currency;

	@Column(name="EXTERNAL_RATE_ID")
	private String externalRateId;

	@Column(name="MANAGEMENT_TYPE")
	private String managementType;

	private String rate;

	@Column(name="TYPE_OF_RATE")
	private String typeOfRate;

	@Column(name="VIRTUAL_FEATURE_NAME")
	private String virtualFeatureName;

	@Column(name="VNF_ID")
	private String vnfId;

	public SalesVnfRule() {
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

	public String getExternalRateId() {
		return this.externalRateId;
	}

	public void setExternalRateId(String externalRateId) {
		this.externalRateId = externalRateId;
	}

	public String getManagementType() {
		return this.managementType;
	}

	public void setManagementType(String managementType) {
		this.managementType = managementType;
	}

	public String getRate() {
		return this.rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public BigDecimal getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(BigDecimal ruleId) {
		this.ruleId = ruleId;
	}

	public String getTypeOfRate() {
		return this.typeOfRate;
	}

	public void setTypeOfRate(String typeOfRate) {
		this.typeOfRate = typeOfRate;
	}

	public String getVirtualFeatureName() {
		return this.virtualFeatureName;
	}

	public void setVirtualFeatureName(String virtualFeatureName) {
		this.virtualFeatureName = virtualFeatureName;
	}

	public String getVnfId() {
		return this.vnfId;
	}

	public void setVnfId(String vnfId) {
		this.vnfId = vnfId;
	}

	public String getProductType() {
		return productType;
	}
}