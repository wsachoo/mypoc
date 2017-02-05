package com.att.salesexpress.webapp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the SALES_SITE database table.
 * 
 */
public class SalesSite implements Serializable {
	private static final long serialVersionUID = 1L;

	private String addressName;

	private String city;

	private String country;

	private Date createdDate;

	private BigDecimal createdId;

	private Long designId;

	private Date modifiedDate;

	private BigDecimal modifiedId;

	private BigDecimal npanxx;

	private Long siteId;

	private String siteName;

	private String state;

	private String zip;

	public SalesSite() {
	}

	public String getAddressName() {
		return this.addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getCreatedId() {
		return this.createdId;
	}

	public void setCreatedId(BigDecimal createdId) {
		this.createdId = createdId;
	}

	public Long getDesignId() {
		return this.designId;
	}

	public void setDesignId(Long designId) {
		this.designId = designId;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public BigDecimal getModifiedId() {
		return this.modifiedId;
	}

	public void setModifiedId(BigDecimal modifiedId) {
		this.modifiedId = modifiedId;
	}

	public BigDecimal getNpanxx() {
		return this.npanxx;
	}

	public void setNpanxx(BigDecimal npanxx) {
		this.npanxx = npanxx;
	}

	public Long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

}