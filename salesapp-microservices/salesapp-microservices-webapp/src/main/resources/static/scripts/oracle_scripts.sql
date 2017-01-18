CREATE TABLE SLEXP_SITE_CONFIG
(
  ID INTEGER PRIMARY KEY,
  SITE_NAME VARCHAR2(120),
  SITE_DATA CLOB
)

CREATE TABLE SLEXP_USER_DETAIL
(
  ID INTEGER PRIMARY KEY,
  USER_ID VARCHAR2(8),
  SITE_DATA CLOB,
  SOLUTION_ID INTEGER,
  SITE_ID INTEGER,
  SITE_ADDR VARCHAR2(60)
 )

 CREATE TABLE SLEXP_SITEDETAIL_TX
(
  ID INTEGER PRIMARY KEY,
  SITE_ID INTEGER,
  ACCESS_DATA CLOB,
  USER_ID VARCHAR2(8),
  SERVICE_FEATURE_DATA CLOB,
  SOLUTION_ID INTEGER
)

CREATE TABLE SLEXP_SERVICE_CONFIG
(
  SERVICE_ID INTEGER PRIMARY KEY,
  SERVICE_NAME VARCHAR2(20) NOT NULL,
  CREATED_DATE DATE NOT NULL
)

CREATE TABLE SLEXP_RESULTS_REF
(
  RESULT_ID INTEGER PRIMARY KEY,
  PRODUCT_ID VARCHAR2(50),
  MRC INTEGER,
  NRC INTEGER,
  ACCESS_SPEED VARCHAR2(50),
  PORT_SPEED VARCHAR2(50),
  CREATED_DATE DATE
)

CREATE SEQUENCE SLEXP_SITEDETAIL_TX_SEQ
  MINVALUE 1
  START WITH 1
  INCREMENT BY 1
  CACHE 20;