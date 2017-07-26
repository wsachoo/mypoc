package com.att.salesexpress.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the SALES_FLEX_DISCOUNT_DATA database table.
 * 
 */
@Entity
@Table(name="SALES_FLEX_DISCOUNT_DATA")
//@NamedQuery(name="SalesFlexDiscountData.findAll", query="SELECT s FROM SalesFlexDiscountData s")
@NamedQuery(name="SalesFlexDiscountData.findAllTopLevels", query="SELECT s FROM SalesFlexDiscountData s where s.salesFlexDiscountData IS NULL")
public class SalesFlexDiscountData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DEVICE_ID")
	private long deviceId;

	@Column(name="DEVICE_NAME")
	private String deviceName;

	@Column(name="DISCOUNT_AUTH_LEVEL")
	private String discountAuthLevel;

	@Column(name="DISCOUNT_PERC")
	private BigDecimal discountPerc;

	@Column(name="TERM_IN_MONTHS")
	private BigDecimal termInMonths;

	//bi-directional many-to-one association to SalesFlexDiscountData
	@ManyToOne
	@JoinColumn(name="PARENT_DEVICE_ID")
	@JsonIgnore
	private SalesFlexDiscountData salesFlexDiscountData;

	//bi-directional many-to-one association to SalesFlexDiscountData
	@OneToMany(mappedBy="salesFlexDiscountData", fetch=FetchType.EAGER)
	private List<SalesFlexDiscountData> childSalesFlexDiscountDataItems;

	//bi-directional many-to-one association to SalesFlexTermDiscount
	@OneToMany(mappedBy="salesFlexDiscountData", fetch=FetchType.EAGER)
	private List<SalesFlexTermDiscount> salesFlexTermDiscounts;

	public SalesFlexDiscountData() {
	}

	public long getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDiscountAuthLevel() {
		return this.discountAuthLevel;
	}

	public void setDiscountAuthLevel(String discountAuthLevel) {
		this.discountAuthLevel = discountAuthLevel;
	}

	public BigDecimal getDiscountPerc() {
		return this.discountPerc;
	}

	public void setDiscountPerc(BigDecimal discountPerc) {
		this.discountPerc = discountPerc;
	}

	public BigDecimal getTermInMonths() {
		return this.termInMonths;
	}

	public void setTermInMonths(BigDecimal termInMonths) {
		this.termInMonths = termInMonths;
	}

	public SalesFlexDiscountData getSalesFlexDiscountData() {
		return this.salesFlexDiscountData;
	}

	public void setSalesFlexDiscountData(SalesFlexDiscountData salesFlexDiscountData) {
		this.salesFlexDiscountData = salesFlexDiscountData;
	}

	public List<SalesFlexDiscountData> getChildSalesFlexDiscountDataItems() {
		return this.childSalesFlexDiscountDataItems;
	}

	public void setChildSalesFlexDiscountDataItems(List<SalesFlexDiscountData> childSalesFlexDiscountDataItems) {
		this.childSalesFlexDiscountDataItems = childSalesFlexDiscountDataItems;
	}

	public SalesFlexDiscountData addSalesFlexDiscountData(SalesFlexDiscountData salesFlexDiscountData) {
		getChildSalesFlexDiscountDataItems().add(salesFlexDiscountData);
		salesFlexDiscountData.setSalesFlexDiscountData(this);

		return salesFlexDiscountData;
	}

	public SalesFlexDiscountData removeSalesFlexDiscountData(SalesFlexDiscountData salesFlexDiscountData) {
		getChildSalesFlexDiscountDataItems().remove(salesFlexDiscountData);
		salesFlexDiscountData.setSalesFlexDiscountData(null);

		return salesFlexDiscountData;
	}

	public List<SalesFlexTermDiscount> getSalesFlexTermDiscounts() {
		return this.salesFlexTermDiscounts;
	}

	public void setSalesFlexTermDiscounts(List<SalesFlexTermDiscount> salesFlexTermDiscounts) {
		this.salesFlexTermDiscounts = salesFlexTermDiscounts;
	}

	public SalesFlexTermDiscount addSalesFlexTermDiscount(SalesFlexTermDiscount salesFlexTermDiscount) {
		getSalesFlexTermDiscounts().add(salesFlexTermDiscount);
		salesFlexTermDiscount.setSalesFlexDiscountData(this);

		return salesFlexTermDiscount;
	}

	public SalesFlexTermDiscount removeSalesFlexTermDiscount(SalesFlexTermDiscount salesFlexTermDiscount) {
		getSalesFlexTermDiscounts().remove(salesFlexTermDiscount);
		salesFlexTermDiscount.setSalesFlexDiscountData(null);

		return salesFlexTermDiscount;
	}

}