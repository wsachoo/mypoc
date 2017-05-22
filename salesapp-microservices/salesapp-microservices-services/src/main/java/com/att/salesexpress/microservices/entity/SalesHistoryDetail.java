package com.att.salesexpress.microservices.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SALES_TRANSACTION_HISTORY database table.
 * 
 */
@Entity
//@Table(name="SALES_TRANSACTION_HISTORY")
@Table(name="SALES_TRANS_HISTORY_MIS_EXP")
@NamedQuery(name="SalesHistoryDetail.findAll", query="SELECT s FROM SalesHistoryDetail s")
public class SalesHistoryDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SalesHistoryDetailPK id;

	@Column(name="ACCESS_ACTUAL_SPEED_ID")
	private BigDecimal accessActualSpeedId;

	@Column(name="ACCESS_ARCHITECTURE")
	private BigDecimal accessArchitecture;

	@Column(name="ACCESS_ARCHITECTURE_S")
	private String accessArchitectureS;

	@Column(name="ACCESS_INTERCONNECT_TECH_S")
	private String accessInterconnectTechS;

	@Column(name="ACCESS_INTERCONNECT_TECHNOLOGY")
	private String accessInterconnectTechnology;

	@Column(name="ACCESS_SERVICE")
	private String accessService;

	@Column(name="ACCESS_SERVICE_ID")
	private String accessServiceId;

	@Column(name="ACCESS_SERVICE_S")
	private String accessServiceS;

	@Column(name="ACCESS_SPEED")
	private String accessSpeed;

	@Column(name="ACCESS_SPEED_ID")
	private BigDecimal accessSpeedId;

	@Column(name="ACCESS_SPEED_S")
	private String accessSpeedS;

	@Column(name="ACCESS_TYPE")
	private String accessType;

	@Column(name="ACCESS_TYPE_ID")
	private String accessTypeId;

	@Column(name="ACCESS_TYPE_S")
	private String accessTypeS;

	@Column(name="BUNDLE_CD")
	private String bundleCd;

	@Column(name="BVOIP_IP_VERSION")
	private String bvoipIpVersion;

	@Column(name="COUNTRY_CD")
	private String countryCd;

	@Column(name="CPE_MODEL")
	private String cpeModel;

	@Column(name="CPE_MODEL_ID")
	private String cpeModelId;

	@Column(name="CPE_MODEL_S")
	private String cpeModelS;

	@Column(name="CPE_REDUNDANT")
	private String cpeRedundant;

	@Column(name="CPE_SVC_CONFIG")
	private String cpeSvcConfig;

	@Column(name="CPE_SVC_CONFIG_ID")
	private String cpeSvcConfigId;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_DATE")
	private Date createDate;

	@Column(name="CREATE_DESIGN_TYPE")
	private String createDesignType;

	@Column(name="CREATE_ID")
	private BigDecimal createId;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column(name="CREATED_ID")
	private BigDecimal createdId;

	@Column(name="CUSTOM_INTERFACE")
	private BigDecimal customInterface;

	@Column(name="DBOR_SOLUTION_ID")
	private BigDecimal dborSolutionId;

	@Column(name="DESIGN_BUNDLE_SITE_ID")
	private BigDecimal designBundleSiteId;

	@Column(name="DESIGN_NAME")
	private String designName;

	@Column(name="DESIGN_SITE_OFFER_PORT_ID")
	private BigDecimal designSiteOfferPortId;

	@Column(name="DESIGN_TYPE")
	private String designType;

	private String erate;

	@Column(name="ERATE_QUICK_QUOTE_IND")
	private String erateQuickQuoteInd;

	@Column(name="ETHERNET_HANDOFF_INTERFACE")
	private BigDecimal ethernetHandoffInterface;

	@Column(name="ETHERNET_HANDOFF_INTERFACE_S")
	private String ethernetHandoffInterfaceS;

	@Column(name="ETHERNET_INTERFACE")
	private BigDecimal ethernetInterface;

	@Column(name="ETHERNET_YN")
	private String ethernetYn;

	@Column(name="HCF_MIN_COMMITMENT")
	private String hcfMinCommitment;

	@Column(name="HCF_MIN_COMMITMENT_ID")
	private BigDecimal hcfMinCommitmentId;

	@Column(name="HCF_MIN_COMMITMENT_S")
	private String hcfMinCommitmentS;

	@Column(name="HCF_OVERAGE")
	private BigDecimal hcfOverage;

	@Column(name="IP_VERSION")
	private String ipVersion;

	@Column(name="IP_VERSION_LABEL")
	private String ipVersionLabel;

	@Column(name="LEAD_ID")
	private BigDecimal leadId;

	@Column(name="MANAGED_ROUTER")
	private String managedRouter;

	@Column(name="MANAGED_ROUTER_ID")
	private String managedRouterId;

	@Column(name="MANAGED_ROUTER_S")
	private String managedRouterS;

	@Column(name="PORT_SPEED")
	private String portSpeed;

	@Column(name="PORT_SPEED_ID")
	private BigDecimal portSpeedId;

	@Column(name="PORT_SPEED_S")
	private String portSpeedS;

	@Column(name="PORT_TYPE")
	private String portType;

	@Column(name="PORT_TYPE_ID")
	private String portTypeId;

	@Column(name="PORT_TYPE_S")
	private String portTypeS;

	private String protocol;

	@Column(name="PROTOCOL_ID")
	private String protocolId;

	@Column(name="PROTOCOL_S")
	private String protocolS;

	@Column(name="RATE_PLAN")
	private String ratePlan;

	@Column(name="RATE_PLAN_ID")
	private String ratePlanId;

	@Column(name="RATE_PLAN_S")
	private String ratePlanS;

	@Column(name="ROUTING_PROTOCOL")
	private String routingProtocol;

	@Column(name="ROUTING_PROTOCOL_ID")
	private String routingProtocolId;

	@Column(name="SITE_NAME_ALIAS")
	private String siteNameAlias;

	@Column(name="SITE_NAME_ALIAS_S")
	private String siteNameAliasS;

	@Column(name="SOLUTION_TYPE")
	private String solutionType;

	@Column(name="SOURCE_DESIGN_ID")
	private BigDecimal sourceDesignId;

	@Column(name="STATE")
	private String state;

	@Column(name="STATUS_ID")
	private BigDecimal statusId;

	@Column(name="SUB_STATUS_ID")
	private BigDecimal subStatusId;

	@Column(name="T1_AVAILABILITY_YN")
	private String t1AvailabilityYn;

	@Column(name="T3_AVAILABILITY_YN")
	private String t3AvailabilityYn;

	@Column(name="TAIL_TECHNOLOGY")
	private String tailTechnology;

	@Column(name="TAIL_TECHNOLOGY_ID")
	private String tailTechnologyId;

	@Column(name="TAIL_TECHNOLOGY_S")
	private String tailTechnologyS;
	
	@Column(name="MRC")
	private String mrc;

	@Column(name="NRC")
	private String nrc;
	
	private transient List<String> portSpeedList = new ArrayList<>();

	public SalesHistoryDetail() {
	}

	public SalesHistoryDetailPK getId() {
		return this.id;
	}

	public void setId(SalesHistoryDetailPK id) {
		this.id = id;
	}

	public BigDecimal getAccessActualSpeedId() {
		return this.accessActualSpeedId;
	}

	public void setAccessActualSpeedId(BigDecimal accessActualSpeedId) {
		this.accessActualSpeedId = accessActualSpeedId;
	}

	public BigDecimal getAccessArchitecture() {
		return this.accessArchitecture;
	}

	public void setAccessArchitecture(BigDecimal accessArchitecture) {
		this.accessArchitecture = accessArchitecture;
	}

	public String getAccessArchitectureS() {
		return this.accessArchitectureS;
	}

	public void setAccessArchitectureS(String accessArchitectureS) {
		this.accessArchitectureS = accessArchitectureS;
	}

	public String getAccessInterconnectTechS() {
		return this.accessInterconnectTechS;
	}

	public void setAccessInterconnectTechS(String accessInterconnectTechS) {
		this.accessInterconnectTechS = accessInterconnectTechS;
	}

	public String getAccessInterconnectTechnology() {
		return this.accessInterconnectTechnology;
	}

	public void setAccessInterconnectTechnology(String accessInterconnectTechnology) {
		this.accessInterconnectTechnology = accessInterconnectTechnology;
	}

	public String getAccessService() {
		return this.accessService;
	}

	public void setAccessService(String accessService) {
		this.accessService = accessService;
	}

	public String getAccessServiceId() {
		return this.accessServiceId;
	}

	public void setAccessServiceId(String accessServiceId) {
		this.accessServiceId = accessServiceId;
	}

	public String getAccessServiceS() {
		return this.accessServiceS;
	}

	public void setAccessServiceS(String accessServiceS) {
		this.accessServiceS = accessServiceS;
	}

	public String getAccessSpeed() {
		return this.accessSpeed;
	}

	public void setAccessSpeed(String accessSpeed) {
		this.accessSpeed = accessSpeed;
	}

	public BigDecimal getAccessSpeedId() {
		return this.accessSpeedId;
	}

	public void setAccessSpeedId(BigDecimal accessSpeedId) {
		this.accessSpeedId = accessSpeedId;
	}

	public String getAccessSpeedS() {
		return this.accessSpeedS;
	}

	public void setAccessSpeedS(String accessSpeedS) {
		this.accessSpeedS = accessSpeedS;
	}

	public String getAccessType() {
		return this.accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getAccessTypeId() {
		return this.accessTypeId;
	}

	public void setAccessTypeId(String accessTypeId) {
		this.accessTypeId = accessTypeId;
	}

	public String getAccessTypeS() {
		return this.accessTypeS;
	}

	public void setAccessTypeS(String accessTypeS) {
		this.accessTypeS = accessTypeS;
	}

	public String getBundleCd() {
		return this.bundleCd;
	}

	public void setBundleCd(String bundleCd) {
		this.bundleCd = bundleCd;
	}

	public String getBvoipIpVersion() {
		return this.bvoipIpVersion;
	}

	public void setBvoipIpVersion(String bvoipIpVersion) {
		this.bvoipIpVersion = bvoipIpVersion;
	}

	public String getCountryCd() {
		return this.countryCd;
	}

	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}

	public String getCpeModel() {
		return this.cpeModel;
	}

	public void setCpeModel(String cpeModel) {
		this.cpeModel = cpeModel;
	}

	public String getCpeModelId() {
		return this.cpeModelId;
	}

	public void setCpeModelId(String cpeModelId) {
		this.cpeModelId = cpeModelId;
	}

	public String getCpeModelS() {
		return this.cpeModelS;
	}

	public void setCpeModelS(String cpeModelS) {
		this.cpeModelS = cpeModelS;
	}

	public String getCpeRedundant() {
		return this.cpeRedundant;
	}

	public void setCpeRedundant(String cpeRedundant) {
		this.cpeRedundant = cpeRedundant;
	}

	public String getCpeSvcConfig() {
		return this.cpeSvcConfig;
	}

	public void setCpeSvcConfig(String cpeSvcConfig) {
		this.cpeSvcConfig = cpeSvcConfig;
	}

	public String getCpeSvcConfigId() {
		return this.cpeSvcConfigId;
	}

	public void setCpeSvcConfigId(String cpeSvcConfigId) {
		this.cpeSvcConfigId = cpeSvcConfigId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateDesignType() {
		return this.createDesignType;
	}

	public void setCreateDesignType(String createDesignType) {
		this.createDesignType = createDesignType;
	}

	public BigDecimal getCreateId() {
		return this.createId;
	}

	public void setCreateId(BigDecimal createId) {
		this.createId = createId;
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

	public BigDecimal getCustomInterface() {
		return this.customInterface;
	}

	public void setCustomInterface(BigDecimal customInterface) {
		this.customInterface = customInterface;
	}

	public BigDecimal getDborSolutionId() {
		return this.dborSolutionId;
	}

	public void setDborSolutionId(BigDecimal dborSolutionId) {
		this.dborSolutionId = dborSolutionId;
	}

	public BigDecimal getDesignBundleSiteId() {
		return this.designBundleSiteId;
	}

	public void setDesignBundleSiteId(BigDecimal designBundleSiteId) {
		this.designBundleSiteId = designBundleSiteId;
	}

	public String getDesignName() {
		return this.designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

	public BigDecimal getDesignSiteOfferPortId() {
		return this.designSiteOfferPortId;
	}

	public void setDesignSiteOfferPortId(BigDecimal designSiteOfferPortId) {
		this.designSiteOfferPortId = designSiteOfferPortId;
	}

	public String getDesignType() {
		return this.designType;
	}

	public void setDesignType(String designType) {
		this.designType = designType;
	}

	public String getErate() {
		return this.erate;
	}

	public void setErate(String erate) {
		this.erate = erate;
	}

	public String getErateQuickQuoteInd() {
		return this.erateQuickQuoteInd;
	}

	public void setErateQuickQuoteInd(String erateQuickQuoteInd) {
		this.erateQuickQuoteInd = erateQuickQuoteInd;
	}

	public BigDecimal getEthernetHandoffInterface() {
		return this.ethernetHandoffInterface;
	}

	public void setEthernetHandoffInterface(BigDecimal ethernetHandoffInterface) {
		this.ethernetHandoffInterface = ethernetHandoffInterface;
	}

	public String getEthernetHandoffInterfaceS() {
		return this.ethernetHandoffInterfaceS;
	}

	public void setEthernetHandoffInterfaceS(String ethernetHandoffInterfaceS) {
		this.ethernetHandoffInterfaceS = ethernetHandoffInterfaceS;
	}

	public BigDecimal getEthernetInterface() {
		return this.ethernetInterface;
	}

	public void setEthernetInterface(BigDecimal ethernetInterface) {
		this.ethernetInterface = ethernetInterface;
	}

	public String getEthernetYn() {
		return this.ethernetYn;
	}

	public void setEthernetYn(String ethernetYn) {
		this.ethernetYn = ethernetYn;
	}

	public String getHcfMinCommitment() {
		return this.hcfMinCommitment;
	}

	public void setHcfMinCommitment(String hcfMinCommitment) {
		this.hcfMinCommitment = hcfMinCommitment;
	}

	public BigDecimal getHcfMinCommitmentId() {
		return this.hcfMinCommitmentId;
	}

	public void setHcfMinCommitmentId(BigDecimal hcfMinCommitmentId) {
		this.hcfMinCommitmentId = hcfMinCommitmentId;
	}

	public String getHcfMinCommitmentS() {
		return this.hcfMinCommitmentS;
	}

	public void setHcfMinCommitmentS(String hcfMinCommitmentS) {
		this.hcfMinCommitmentS = hcfMinCommitmentS;
	}

	public BigDecimal getHcfOverage() {
		return this.hcfOverage;
	}

	public void setHcfOverage(BigDecimal hcfOverage) {
		this.hcfOverage = hcfOverage;
	}

	public String getIpVersion() {
		return this.ipVersion;
	}

	public void setIpVersion(String ipVersion) {
		this.ipVersion = ipVersion;
	}

	public String getIpVersionLabel() {
		return this.ipVersionLabel;
	}

	public void setIpVersionLabel(String ipVersionLabel) {
		this.ipVersionLabel = ipVersionLabel;
	}

	public BigDecimal getLeadId() {
		return this.leadId;
	}

	public void setLeadId(BigDecimal leadId) {
		this.leadId = leadId;
	}

	public String getManagedRouter() {
		return this.managedRouter;
	}

	public void setManagedRouter(String managedRouter) {
		this.managedRouter = managedRouter;
	}

	public String getManagedRouterId() {
		return this.managedRouterId;
	}

	public void setManagedRouterId(String managedRouterId) {
		this.managedRouterId = managedRouterId;
	}

	public String getManagedRouterS() {
		return this.managedRouterS;
	}

	public void setManagedRouterS(String managedRouterS) {
		this.managedRouterS = managedRouterS;
	}

	public String getPortSpeed() {
		return this.portSpeed;
	}

	public void setPortSpeed(String portSpeed) {
		this.portSpeed = portSpeed;
	}

	public BigDecimal getPortSpeedId() {
		return this.portSpeedId;
	}

	public void setPortSpeedId(BigDecimal portSpeedId) {
		this.portSpeedId = portSpeedId;
	}

	public String getPortSpeedS() {
		return this.portSpeedS;
	}

	public void setPortSpeedS(String portSpeedS) {
		this.portSpeedS = portSpeedS;
	}

	public String getPortType() {
		return this.portType;
	}

	public void setPortType(String portType) {
		this.portType = portType;
	}

	public String getPortTypeId() {
		return this.portTypeId;
	}

	public void setPortTypeId(String portTypeId) {
		this.portTypeId = portTypeId;
	}

	public String getPortTypeS() {
		return this.portTypeS;
	}

	public void setPortTypeS(String portTypeS) {
		this.portTypeS = portTypeS;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getProtocolId() {
		return this.protocolId;
	}

	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}

	public String getProtocolS() {
		return this.protocolS;
	}

	public void setProtocolS(String protocolS) {
		this.protocolS = protocolS;
	}

	public String getRatePlan() {
		return this.ratePlan;
	}

	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}

	public String getRatePlanId() {
		return this.ratePlanId;
	}

	public void setRatePlanId(String ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public String getRatePlanS() {
		return this.ratePlanS;
	}

	public void setRatePlanS(String ratePlanS) {
		this.ratePlanS = ratePlanS;
	}

	public String getRoutingProtocol() {
		return this.routingProtocol;
	}

	public void setRoutingProtocol(String routingProtocol) {
		this.routingProtocol = routingProtocol;
	}

	public String getRoutingProtocolId() {
		return this.routingProtocolId;
	}

	public void setRoutingProtocolId(String routingProtocolId) {
		this.routingProtocolId = routingProtocolId;
	}

	public String getSiteNameAlias() {
		return this.siteNameAlias;
	}

	public void setSiteNameAlias(String siteNameAlias) {
		this.siteNameAlias = siteNameAlias;
	}

	public String getSiteNameAliasS() {
		return this.siteNameAliasS;
	}

	public void setSiteNameAliasS(String siteNameAliasS) {
		this.siteNameAliasS = siteNameAliasS;
	}

	public String getSolutionType() {
		return this.solutionType;
	}

	public void setSolutionType(String solutionType) {
		this.solutionType = solutionType;
	}

	public BigDecimal getSourceDesignId() {
		return this.sourceDesignId;
	}

	public void setSourceDesignId(BigDecimal sourceDesignId) {
		this.sourceDesignId = sourceDesignId;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public BigDecimal getStatusId() {
		return this.statusId;
	}

	public void setStatusId(BigDecimal statusId) {
		this.statusId = statusId;
	}

	public BigDecimal getSubStatusId() {
		return this.subStatusId;
	}

	public void setSubStatusId(BigDecimal subStatusId) {
		this.subStatusId = subStatusId;
	}

	public String getT1AvailabilityYn() {
		return this.t1AvailabilityYn;
	}

	public void setT1AvailabilityYn(String t1AvailabilityYn) {
		this.t1AvailabilityYn = t1AvailabilityYn;
	}

	public String getT3AvailabilityYn() {
		return this.t3AvailabilityYn;
	}

	public void setT3AvailabilityYn(String t3AvailabilityYn) {
		this.t3AvailabilityYn = t3AvailabilityYn;
	}

	public String getTailTechnology() {
		return this.tailTechnology;
	}

	public void setTailTechnology(String tailTechnology) {
		this.tailTechnology = tailTechnology;
	}

	public String getTailTechnologyId() {
		return this.tailTechnologyId;
	}

	public void setTailTechnologyId(String tailTechnologyId) {
		this.tailTechnologyId = tailTechnologyId;
	}

	public String getTailTechnologyS() {
		return this.tailTechnologyS;
	}

	public void setTailTechnologyS(String tailTechnologyS) {
		this.tailTechnologyS = tailTechnologyS;
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

	public List<String> getPortSpeedList() {
		return portSpeedList;
	}

	public void setPortSpeedList(List<String> portSpeedList) {
		this.portSpeedList = portSpeedList;
	}	
}