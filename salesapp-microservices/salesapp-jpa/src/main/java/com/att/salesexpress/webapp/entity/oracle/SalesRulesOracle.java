package com.att.salesexpress.webapp.entity.oracle;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SALES_RULES")
@NamedQueries({ @NamedQuery(name = "SalesRulesOracle.findAll", query = "SELECT s FROM SalesRulesOracle s") })
public class SalesRulesOracle implements Serializable {

	@Column(name = "ID")
	@Id
	private Integer id;

	private static final long serialVersionUID = 1L;

	@Column(name = "ACCESS_SPEED_ID")
	private BigDecimal accessSpeedId;

	@Column(name = "MIN_MBC")
	private BigDecimal minMbc;

	private BigDecimal mrc;

	private BigDecimal nrc;

	@Column(name = "PORT_SPEED_ID")
	private BigDecimal portSpeedId;

	@Column(name = "PORT_TYPE")
	private String portType;

	private String product;

	@Column(name = "RATE_PLAN")
	private String ratePlan;

	@Column(name = "RULE_ID")
	private BigDecimal ruleId;

	public SalesRulesOracle() {
	}

	public BigDecimal getAccessSpeedId() {
		return this.accessSpeedId;
	}

	public void setAccessSpeedId(BigDecimal accessSpeedId) {
		this.accessSpeedId = accessSpeedId;
	}

	public BigDecimal getMinMbc() {
		return this.minMbc;
	}

	public void setMinMbc(BigDecimal minMbc) {
		this.minMbc = minMbc;
	}

	public BigDecimal getMrc() {
		return this.mrc;
	}

	public void setMrc(BigDecimal mrc) {
		this.mrc = mrc;
	}

	public BigDecimal getNrc() {
		return this.nrc;
	}

	public void setNrc(BigDecimal nrc) {
		this.nrc = nrc;
	}

	public BigDecimal getPortSpeedId() {
		return this.portSpeedId;
	}

	public void setPortSpeedId(BigDecimal portSpeedId) {
		this.portSpeedId = portSpeedId;
	}

	public String getPortType() {
		return this.portType;
	}

	public void setPortType(String portType) {
		this.portType = portType;
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getRatePlan() {
		return this.ratePlan;
	}

	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}

	public BigDecimal getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(BigDecimal ruleId) {
		this.ruleId = ruleId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SalesRules [accessSpeedId=" + accessSpeedId + ", minMbc=" + minMbc + ", mrc=" + mrc + ", nrc=" + nrc
				+ ", portSpeedId=" + portSpeedId + ", portType=" + portType + ", product=" + product + ", ratePlan="
				+ ratePlan + ", ruleId=" + ruleId + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}