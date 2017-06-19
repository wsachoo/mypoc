package com.att.pricerd.microservices.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.hateoas.ResourceSupport;

/**
 * The primary key class for the SALES_TRANSACTION_HISTORY database table.
 * 
 */
@Embeddable
public class SalesHistoryDetailPK extends ResourceSupport implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="LEAD_DESIGN_ID")
	private long leadDesignId;

	@Column(name="SITE_ID")
	private long siteId;

	public SalesHistoryDetailPK() {
	}
	public long getLeadDesignId() {
		return this.leadDesignId;
	}
	public void setLeadDesignId(long leadDesignId) {
		this.leadDesignId = leadDesignId;
	}
	public long getSiteId() {
		return this.siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SalesHistoryDetailPK)) {
			return false;
		}
		SalesHistoryDetailPK castOther = (SalesHistoryDetailPK)other;
		return 
			(this.leadDesignId == castOther.leadDesignId)
			&& (this.siteId == castOther.siteId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.leadDesignId ^ (this.leadDesignId >>> 32)));
		hash = hash * prime + ((int) (this.siteId ^ (this.siteId >>> 32)));
		
		return hash;
	}
}