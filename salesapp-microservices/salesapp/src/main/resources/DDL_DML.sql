select 
	ald.*,
	tdv.*
from
	transport_design_v tdv, AD_LEAD_DESIGN ald
where 
	tdv.LEAD_DESIGN_ID = ald.LEAD_DESIGN_ID and 
	tdv.BUNDLE_CD = 'MISPNT' and
	ald.SOLUTION_TYPE IS NULL AND
	ald.STATUS_ID > 40
	and ald.CREATED_DATE > '01-JAN-2016';
---------------------------------------------------------------------------------------------------------------------------------------------
drop table SALES_TRANSACTION_HISTORY;
create table SALES_TRANSACTION_HISTORY AS 
SELECT
    tdv.ACCESS_TYPE, tdv.ACCESS_TYPE_ID, tdv.ACCESS_TYPE_S, tdv.ACCESS_SPEED,
    tdv.ACCESS_SPEED_ID, tdv.ACCESS_SPEED_S, tdv.PORT_TYPE, tdv.PORT_TYPE_ID, tdv.PORT_TYPE_S,
    tdv.PORT_SPEED, tdv.PORT_SPEED_ID, tdv.PORT_SPEED_S, ald.DESIGN_NAME, ald.DESIGN_TYPE,
    ald.ERATE, ald.ERATE_QUICK_QUOTE_IND, ald.SOLUTION_TYPE, ald.SOURCE_DESIGN_ID, ald.STATUS_ID,
    ald.SUB_STATUS_ID, tdv.ACCESS_ACTUAL_SPEED_ID, tdv.ACCESS_ARCHITECTURE, tdv.ACCESS_ARCHITECTURE_S, tdv.ACCESS_INTERCONNECT_TECHNOLOGY,
    tdv.ACCESS_INTERCONNECT_TECH_S, tdv.ACCESS_SERVICE, tdv.ACCESS_SERVICE_ID, tdv.ACCESS_SERVICE_S, tdv.BUNDLE_CD,
    tdv.BVOIP_IP_VERSION, tdv.COUNTRY_CD, tdv.CPE_MODEL, tdv.CPE_MODEL_ID, tdv.CPE_MODEL_S,
    tdv.CPE_REDUNDANT, tdv.CPE_SVC_CONFIG, tdv.CPE_SVC_CONFIG_ID, tdv.CREATE_DATE, tdv.CREATE_DESIGN_TYPE,
    tdv.CREATE_ID, tdv.CUSTOM_INTERFACE, tdv.DESIGN_BUNDLE_SITE_ID, tdv.DESIGN_SITE_OFFER_PORT_ID, tdv.ETHERNET_HANDOFF_INTERFACE,
    tdv.ETHERNET_HANDOFF_INTERFACE_S, tdv.ETHERNET_INTERFACE, tdv.ETHERNET_YN, tdv.HCF_MIN_COMMITMENT, tdv.HCF_MIN_COMMITMENT_ID,
    tdv.HCF_MIN_COMMITMENT_S, tdv.HCF_OVERAGE, tdv.IP_VERSION, tdv.IP_VERSION_LABEL, tdv.MANAGED_ROUTER,
    tdv.MANAGED_ROUTER_ID, tdv.MANAGED_ROUTER_S, tdv.PROTOCOL, tdv.PROTOCOL_ID, tdv.PROTOCOL_S,
    tdv.RATE_PLAN, tdv.RATE_PLAN_ID, tdv.RATE_PLAN_S, tdv.ROUTING_PROTOCOL, tdv.ROUTING_PROTOCOL_ID,
    tdv.STATE, tdv.T1_AVAILABILITY_YN,
    tdv.T3_AVAILABILITY_YN, tdv.TAIL_TECHNOLOGY, tdv.TAIL_TECHNOLOGY_ID, tdv.TAIL_TECHNOLOGY_S, 
    ald.DBOR_SOLUTION_ID, ald.LEAD_DESIGN_ID, ald.LEAD_ID, tdv.SITE_ID, tdv.SITE_NAME_ALIAS,
    tdv.SITE_NAME_ALIAS_S, ald.CREATED_DATE, ald.CREATED_ID
from
    transport_design_v tdv, AD_LEAD_DESIGN ald
where 
    tdv.LEAD_DESIGN_ID = ald.LEAD_DESIGN_ID and 
    tdv.BUNDLE_CD = 'MISPNT' and
    ald.SOLUTION_TYPE IS NULL AND
    ald.STATUS_ID > 40 and 
    ald.CREATED_DATE > '01-JAN-2016' and 
    tdv.ACCESS_TYPE IS NOT NULL and 
    tdv.PORT_TYPE IS NOT NULL and 
    tdv.ACCESS_SPEED_ID IS NOT NULL and
    tdv.PORT_SPEED_ID IS NOT NULL
order by
    tdv.ACCESS_TYPE, tdv.PORT_TYPE,
    tdv.ACCESS_SPEED_ID, tdv.PORT_SPEED_ID;
---------------------------------------------------------------------------------------------------------------------------------------------

alter table SALES_TRANSACTION_HISTORY
add constraint PK_SALES_TRANSACTION_HISTORY primary key (LEAD_DESIGN_ID, SITE_ID);    
---------------------------------------------------------------------------------------------------------------------------------------------
    
select * from (
  select       
      ACCESS_TYPE, PORT_TYPE, ACCESS_SPEED_ID, PORT_SPEED_ID, --and all other fields
      dense_rank() over (order by NUMBER_OF_SALES desc) RNK
  from (
    select 
      count(*) over (partition by a.ACCESS_SPEED_ID,  a.PORT_SPEED_ID) as NUMBER_OF_SALES,
      a.ACCESS_TYPE,
      a.PORT_TYPE,
      a.ACCESS_SPEED_ID,
      a.PORT_SPEED_ID
      --and all other fields
    from SALES_TRANSACTION_HISTORY a
    where a.ACCESS_TYPE='Ethernet'
  )  
) mytable
where mytable.RNK=1 and rownum < 10    
---------------------------------------------------------------------------------------------------------------------------------------------

                        