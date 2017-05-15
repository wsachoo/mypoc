package com.att.salesexpress.microservices.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.hateoas.ResourceSupport;

@Embeddable
public class SalesRulesMisExpDetailPK extends ResourceSupport implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="DESIGN_RULE_ID")
	private int designRuleId;
	
	public SalesRulesMisExpDetailPK() {}
	
	public int getDesignRuleId() {
		return designRuleId;
	}

	public void setDesignRuleId(int designRuleId) {
		this.designRuleId = designRuleId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + designRuleId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalesRulesMisExpDetailPK other = (SalesRulesMisExpDetailPK) obj;
		if (designRuleId != other.designRuleId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SalesRulesMisExpDetailPK [designRuleId=" + designRuleId + "]";
	}
	
	
}
