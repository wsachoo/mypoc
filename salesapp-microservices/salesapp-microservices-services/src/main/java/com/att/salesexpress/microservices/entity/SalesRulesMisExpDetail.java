package com.att.salesexpress.microservices.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "sales_rules_mis_exp")
@NamedQueries({
	@NamedQuery(name = "SalesRulesMisExpDetail.findAll", query = "SELECT s FROM SalesRulesMisExpDetail s"),
	@NamedQuery(name = "SalesRulesMisExpDetail.findAllPortSpeedsByAccessSpeed", query = "SELECT s FROM SalesRulesMisExpDetail s where s.accessSpeedId=:accessSpeedId")
})
public class SalesRulesMisExpDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SalesRulesMisExpDetailPK id;

	@Column(name = "BUNDLE_CD")
	private String bundleCd;

	@Column(name = "ACCESS_SPEED")
	private String accessSpeed;

	@Column(name = "ACCESS_SPEED_ID")
	private String accessSpeedId;

	@Column(name = "ACCESS_TYPE")
	private String accessType;

	@Column(name = "ACCESS_SERVICE")
	private String accessService;

	@Column(name = "IP_VERSION_LABEL")
	private String ipVersionLabel;

	@Column(name = "MRC")
	private int mrc;

	@Column(name = "NRC")
	private int nrc;

	@Column(name = "PORT_SPEED")
	private String portSpeed;

	@Column(name = "PORT_SPEED_ID ")
	private Integer portSpeedId;

	@Column(name = "PROTOCOL")
	private String protocol;

	@Column(name = "TAIL_TECHNOLOGY")
	private String tailTechnology;

	@Column(name = "RATE_PLAN")
	private String ratePlan;

	@Column(name = "ETHERNET_HANDOFF_INTERFACE_S")
	private String ethernetHandoffInterfaceS;

	@Column(name = "DESIGN_TYPE")
	private String designType;

	@Column(name = "MANAGED_ROUTER")
	private String managedRouter;

	@Column(name = "DESIGN_NAME")
	private String designName;

	@Column(name = "SITE_NAME_ALIAS")
	private String siteNameALias;

	public String getAccessSpeedId() {
		return accessSpeedId;
	}

	public void setAccessSpeedId(String accessSpeedId) {
		this.accessSpeedId = accessSpeedId;
	}

	public Integer getPortSpeedId() {
		return portSpeedId;
	}

	public void setPortSpeedId(Integer portSpeedId) {
		this.portSpeedId = portSpeedId;
	}

	public String getIpVersionLabel() {
		return ipVersionLabel;
	}

	public void setIpVersionLabel(String ipVersionLabel) {
		this.ipVersionLabel = ipVersionLabel;
	}

	public String getBundleCd() {
		return bundleCd;
	}

	public void setBundleCd(String bundleCd) {
		this.bundleCd = bundleCd;
	}

	public String getAccessSpeed() {
		return accessSpeed;
	}

	public void setAccessSpeed(String accessSpeed) {
		this.accessSpeed = accessSpeed;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getAccessService() {
		return accessService;
	}

	public void setAccessService(String accessService) {
		this.accessService = accessService;
	}

	public int getMrc() {
		return mrc;
	}

	public void setMrc(int mrc) {
		this.mrc = mrc;
	}

	public int getNrc() {
		return nrc;
	}

	public void setNrc(int nrc) {
		this.nrc = nrc;
	}

	public String getPortSpeed() {
		return portSpeed;
	}

	public void setPortSpeed(String portSpeed) {
		this.portSpeed = portSpeed;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getTailTechnology() {
		return tailTechnology;
	}

	public void setTailTechnology(String tailTechnology) {
		this.tailTechnology = tailTechnology;
	}

	public String getRatePlan() {
		return ratePlan;
	}

	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}

	public String getEthernetHandoffInterfaceS() {
		return ethernetHandoffInterfaceS;
	}

	public void setEthernetHandoffInterfaceS(String ethernetHandoffInterfaceS) {
		this.ethernetHandoffInterfaceS = ethernetHandoffInterfaceS;
	}

	public String getDesignType() {
		return designType;
	}

	public void setDesignType(String designType) {
		this.designType = designType;
	}

	public String getManagedRouter() {
		return managedRouter;
	}

	public void setManagedRouter(String managedRouter) {
		this.managedRouter = managedRouter;
	}

	public String getSiteNameALias() {
		return siteNameALias;
	}

	public void setSiteNameALias(String siteNameALias) {
		this.siteNameALias = siteNameALias;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SalesRulesMisExpDetailPK getId() {
		return id;
	}

	public void setId(SalesRulesMisExpDetailPK id) {
		this.id = id;
	}

	public String getDesignName() {
		return designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

}
