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

                        
create table SALES_SOL_TMPL_QUES
(
  ID INTEGER PRIMARY KEY,
  QUES_SEQ_ID INTEGER,
  QUES_COL_NAME VARCHAR2(60) NOT NULL,
  QUES_DESC VARCHAR2(240) NOT NULL,
  QUES_TYPE VARCHAR2(30) NOT NULL
  constraint CHK_QUES_TYPE CHECK (QUES_TYPE in ('DROP_DOWN', 'CHECK_BOX', 'RADIO_BUTTON'))
)
insert into SALES_SOL_TMPL_QUES values(1, 1, 'PRODUCT', 'Please select product', 'RADIO_BUTTON');
insert into SALES_SOL_TMPL_QUES values(2, 2, 'ACCESS_TYPE', 'Please select access type for your site', 'RADIO_BUTTON');
insert into SALES_SOL_TMPL_QUES values(3, 3, 'ACCESS_SPEED_ID', 'Please select access speed for your site', 'DROP_DOWN');
insert into SALES_SOL_TMPL_QUES values(4, 4, 'PORT_SPEED_ID', 'Please select port speed for your site', 'DROP_DOWN');



create table SALES_SOL_TMPL_ANS
(
  ID INTEGER PRIMARY KEY,
  ANS_SEQ_ID INTEGER,
  ANS_DESC VARCHAR2(240) NOT NULL,
  QUES_ID INTEGER,
  constraint FK_SALES_SOL_TMPL_ANS foreign key (QUES_ID) REFERENCES SALES_SOL_TMPL_QUES(ID)
)


insert into SALES_SOL_TMPL_ANS values(1, 1, 'MISPNT', 1);
insert into SALES_SOL_TMPL_ANS values(2, 2, 'AVPN', 1);

insert into SALES_SOL_TMPL_ANS values(3, 1, 'ETHERNET', 2);
insert into SALES_SOL_TMPL_ANS values(4, 2, 'IP', 2);


declare 
	vANS_ID_START INTEGER;
begin
	select max(ID) into vANS_ID_START from SALES_SOL_TMPL_ANS;

	insert into SALES_SOL_TMPL_ANS
	select vANS_ID_START + rownum, rownum, ACCESS_SPEED_ID, 3 from (
  		select distinct ACCESS_SPEED_ID from SALES_TRANSACTION_HISTORY where ACCESS_SPEED_ID <> 0 order by ACCESS_SPEED_ID
	);
  
	select max(ID) into vANS_ID_START from SALES_SOL_TMPL_ANS;

	insert into SALES_SOL_TMPL_ANS
	select vANS_ID_START + rownum, rownum, PORT_SPEED_ID, 4 from (
  		select distinct PORT_SPEED_ID from SALES_TRANSACTION_HISTORY where PORT_SPEED_ID <> 0 order by PORT_SPEED_ID
	);  
end;
/

