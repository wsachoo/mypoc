package com.att.salesexpress.webapp.pojos;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDesignSelectionDO {
	private String userId;
	private Long solutionId;
	private Long transactionId;

	@JsonProperty("sites")
	private Map<String, UserSiteDesignDO> siteDesignList = new HashMap<String, UserSiteDesignDO>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Map<String, UserSiteDesignDO> getSiteDesignList() {
		return siteDesignList;
	}

	public void setSiteDesignList(Map<String, UserSiteDesignDO> siteDesignList) {
		this.siteDesignList = siteDesignList;
	}

	@Override
	public String toString() {
		return "UserSolutionDesignDO [userId=" + userId + ", solutionId=" + solutionId + ", transactionId="
				+ transactionId + ", siteDesignList=" + siteDesignList + "]";
	}
}