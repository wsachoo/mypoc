package com.att.salesexpress.webapp.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSiteDesignDO {
	@JsonProperty("siteName")
	private String siteName;

	@JsonProperty("siteId")
	private Long siteId;

	@JsonProperty("resiliencyConfig")
	private ResiliencyDesignDO resiliencyDesign;

	@JsonProperty("accessConfig")
	private AccessConfigDesignDO accessConfigDesign;

	@JsonProperty("portConfig")
	private PortConfigDesignDO portConfigDesign;
	
	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteId) {
		this.siteName = siteId;
	}

	public ResiliencyDesignDO getResiliencyDesign() {
		return resiliencyDesign;
	}

	public void setResiliencyDesign(ResiliencyDesignDO resiliencyDesign) {
		this.resiliencyDesign = resiliencyDesign;
	}

	public AccessConfigDesignDO getAccessConfigDesign() {
		return accessConfigDesign;
	}

	public void setAccessConfigDesign(AccessConfigDesignDO accessConfigDesign) {
		this.accessConfigDesign = accessConfigDesign;
	}

	public PortConfigDesignDO getPortConfigDesign() {
		return portConfigDesign;
	}

	public void setPortConfigDesign(PortConfigDesignDO portConfigDesign) {
		this.portConfigDesign = portConfigDesign;
	}

	@Override
	public String toString() {
		return "UserSiteDesignDO [siteId=" + siteName + ", resiliencyDesign=" + resiliencyDesign + ", accessConfigDesign="
				+ accessConfigDesign + ", portConfigDesign=" + portConfigDesign + "]";
	}
}