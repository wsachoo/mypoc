package com.att.salesexpress.microservices.entity;

import org.springframework.hateoas.ResourceSupport;

public class SalesHistoryStripped extends ResourceSupport {
	private long leadDesignId;

	private long siteId;

	private String accessArchitecture;

	private String accessSpeed;

	private String cpeModel;

	private String ethernetHandoffInterface;

	private String designType;

	private String portSpeed;

	private String managedRouter;

	private String siteNameAlias;

	private String ratePlan;

	private String tailTechnologyId;
	
	private String matchPercentage;
	
	private String mrc;
	
	private String nrc;
	
	public long getLeadDesignId() {
		return leadDesignId;
	}

	public void setLeadDesignId(long leadDesignId) {
		this.leadDesignId = leadDesignId;
	}

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getAccessArchitecture() {
		return accessArchitecture;
	}

	public void setAccessArchitecture(String accessArchitecture) {
		this.accessArchitecture = accessArchitecture;
	}

	public String getAccessSpeed() {
		return accessSpeed;
	}

	public void setAccessSpeed(String accessSpeed) {
		this.accessSpeed = accessSpeed;
	}

	public String getCpeModel() {
		return cpeModel;
	}

	public void setCpeModel(String cpeModel) {
		this.cpeModel = cpeModel;
	}

	public String getEthernetHandoffInterface() {
		return ethernetHandoffInterface;
	}

	public void setEthernetHandoffInterface(String ethernetHandoffInterface) {
		this.ethernetHandoffInterface = ethernetHandoffInterface;
	}

	public String getDesignType() {
		return designType;
	}

	public void setDesignType(String designType) {
		this.designType = designType;
	}

	public String getPortSpeed() {
		return portSpeed;
	}

	public void setPortSpeed(String portSpeed) {
		this.portSpeed = portSpeed;
	}

	public String getManagedRouter() {
		return managedRouter;
	}

	public void setManagedRouter(String managedRouter) {
		this.managedRouter = managedRouter;
	}

	public String getSiteNameAlias() {
		return siteNameAlias;
	}

	public void setSiteNameAlias(String siteNameAlias) {
		this.siteNameAlias = siteNameAlias;
	}

	public String getRatePlan() {
		return ratePlan;
	}

	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}

	public String getTailTechnologyId() {
		return tailTechnologyId;
	}

	public void setTailTechnologyId(String tailTechnologyId) {
		this.tailTechnologyId = tailTechnologyId;
	}
	
	public String getMatchPercentage() {
		return matchPercentage;
	}

	public void setMatchPercentage(String matchPercentage) {
		this.matchPercentage = matchPercentage;
	}
	
	

	public String getMrc() {
		return mrc;
	}

	public void setMrc(String mrc) {
		this.mrc = mrc;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	@Override
	public String toString() {
		return "SalesHistoryStripped [leadDesignId=" + leadDesignId + ", siteId=" + siteId + ", accessArchitecture="
				+ accessArchitecture + ", accessSpeed=" + accessSpeed + ", cpeModel=" + cpeModel
				+ ", ethernetHandoffInterface=" + ethernetHandoffInterface + ", designType=" + designType
				+ ", portSpeed=" + portSpeed + ", managedRouter=" + managedRouter + ", siteNameAlias=" + siteNameAlias
				+ ", ratePlan=" + ratePlan + ", tailTechnologyId=" + tailTechnologyId + "]";
	}

}
