package com.att.salesexpress.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;


/**
 * The persistent class for the SALES_FLEX_TERM_DISCOUNT database table.
 * 
 */
@Entity
@Table(name="SALES_FLEX_TERM_DISCOUNT")
@NamedQuery(name="SalesFlexTermDiscount.findAll", query="SELECT s FROM SalesFlexTermDiscount s")
public class SalesFlexTermDiscount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="DISCOUNT_PERC")
	private BigDecimal discountPerc;

	@Column(name="TERM_IN_MONTHS")
	private BigDecimal termInMonths;

	//bi-directional many-to-one association to SalesFlexDiscountData
	@ManyToOne
	@JoinColumn(name="DEVICE_ID")
	@JsonIgnore
	private SalesFlexDiscountData salesFlexDiscountData;

	public SalesFlexTermDiscount() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

}