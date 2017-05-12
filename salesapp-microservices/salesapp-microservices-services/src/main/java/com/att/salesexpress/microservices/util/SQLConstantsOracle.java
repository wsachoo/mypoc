package com.att.salesexpress.microservices.util;

public interface SQLConstantsOracle {
	String sqlGetSalesHistoryDataByAccessType = "select rankTable.* from ("
			+ "select countTable.*, "
			+ "dense_rank() over (order by countTable.NUMBER_OF_SALES desc) RNK "
			+ "from ("
			+ "select "
			+ "count(*) over (partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID) as NUMBER_OF_SALES, "
			//+ "concat(ROUND(count(*) over () * 100/ totalTrans.cnt, 2), '%')  as MATCHING_ROW_PERCENTAGE, "
			+ "concat(ROUND(count(*) over (partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID) * 100/ totalTrans.cnt, 2), '%')  as MATCHING_ROW_PERCENTAGE, "
			+ "rank() over ( partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID order by sth.SITE_ID ) indexWithinGroup,"
			+ "sth.* "
			+ "from SALES_TRANSACTION_HISTORY sth, (select count(*) cnt from SALES_TRANSACTION_HISTORY) totalTrans "
			+ "where sth.ACCESS_TYPE_ID = :ACCESS_TYPE_ID "
			+ ") countTable "
			+ ") rankTable "
			//+ "where rankTable.RNK = 1 and rownum <= :NUMBER_OF_ROWS"; 
			+ "where rankTable.indexWithinGroup = 1 and rownum <= :NUMBER_OF_ROWS";
	
	String sqlGetSalesHistoryDataByAccessTypeForOtherAccessType = "select rankTable.* from ("
			+ "select countTable.*, "
			+ "dense_rank() over (order by countTable.NUMBER_OF_SALES desc) RNK "
			+ "from ("
			+ "select "
			+ "count(*) over (partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID) as NUMBER_OF_SALES, "
			//+ "concat(ROUND(count(*) over () * 100/ totalTrans.cnt, 2), '%')  as MATCHING_ROW_PERCENTAGE, "
			+ "concat(ROUND(count(*) over (partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID) * 100/ totalTrans.cnt, 2), '%')  as MATCHING_ROW_PERCENTAGE, "
			+ "rank() over ( partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID order by sth.SITE_ID ) indexWithinGroup,"
			+ "sth.* "
			+ "from SALES_TRANSACTION_HISTORY sth, (select count(*) cnt from SALES_TRANSACTION_HISTORY) totalTrans "
			+ "where sth.ACCESS_TYPE_ID NOT IN ('ETHERNET', 'Private Line')"
			+ ") countTable "
			+ ") rankTable "
			//+ "where rankTable.RNK = 1 and rownum <= :NUMBER_OF_ROWS";
			+ "where rankTable.indexWithinGroup = 1 and rownum <= :NUMBER_OF_ROWS";

	String sqlGetSalesHistoryDataByAccessTypeAndAccessSpeed = "select rankTable.* from ("
			+ "select countTable.*, "
			+ "dense_rank() over (order by countTable.NUMBER_OF_SALES desc) RNK "
			+ "from ("
			+ "select "
			+ "count(*) over (partition by sth.PORT_SPEED_ID) as NUMBER_OF_SALES, "
			//+ "concat(ROUND(count(*) over () * 100/ totalTrans.cnt, 2), '%')  as MATCHING_ROW_PERCENTAGE, "
			+ "concat(ROUND(count(*) over (partition by sth.PORT_SPEED_ID) * 100/ totalTrans.cnt, 2), '%')  as MATCHING_ROW_PERCENTAGE, "
			+ "rank() over ( partition by sth.PORT_SPEED_ID order by sth.SITE_ID ) indexWithinGroup,"
			+ "sth.* "
			+ "from SALES_TRANSACTION_HISTORY sth, (select count(*) cnt from SALES_TRANSACTION_HISTORY where ACCESS_TYPE_ID=:ACCESS_TYPE_ID) totalTrans "
			+ "where sth.ACCESS_TYPE_ID = :ACCESS_TYPE_ID "
			+ "and   sth.ACCESS_SPEED_ID = :ACCESS_SPEED_ID "
			+ ") countTable "
			+ ") rankTable "
			//+ "where rankTable.RNK = 1 and rownum <= :NUMBER_OF_ROWS";
			+ "where rankTable.indexWithinGroup = 1 and rownum <= :NUMBER_OF_ROWS";

	String sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed = "select rankTable.* from ("
			+ "select countTable.*, "
			+ "dense_rank() over (order by countTable.NUMBER_OF_SALES desc) RNK "
			+ "from ("
			+ "select "
			+ "count(*) over () as NUMBER_OF_SALES, "
			+ "concat(ROUND(count(*) over () * 100/ totalTrans.cnt, 2), '%')  as MATCHING_ROW_PERCENTAGE, "
			+ "rank() over (order by sth.SITE_ID ) indexWithinGroup,"
			+ "sth.* "
			+ "from SALES_TRANSACTION_HISTORY sth, ("
			+ "select count(*) cnt from SALES_TRANSACTION_HISTORY "
			+ "where ACCESS_TYPE_ID=:ACCESS_TYPE_ID and ACCESS_SPEED_ID=:ACCESS_SPEED_ID) totalTrans "
			+ "where sth.ACCESS_TYPE_ID = :ACCESS_TYPE_ID "
			+ "and   sth.ACCESS_SPEED_ID = :ACCESS_SPEED_ID "
			+ "and   sth.PORT_SPEED_ID = :PORT_SPEED_ID "
			+ ") countTable "
			+ ") rankTable "
			//+ "where rankTable.RNK = 1 and rownum <= :NUMBER_OF_ROWS";
			+ "where rankTable.indexWithinGroup = 1 and rownum <= :NUMBER_OF_ROWS";
	
	String sqlGetSalesHistoryPercentageRecordsByAccessType_OLD = "select ROUND(count(*)*100/(select count(*) from SALES_TRANSACTION_HISTORY), 2) as PERCENTAGE, "
			+ "ACCESS_TYPE_ID from SALES_TRANSACTION_HISTORY "
			+ "where ACCESS_TYPE_ID in ('Private Line', 'ETHERNET') "
			+ "group by ACCESS_TYPE_ID order by ACCESS_TYPE_ID";
	
	String sqlGetSalesHistoryPercentageRecordsByAccessType = "select ROUND(firstTable.total_By_Access_Type * 100/secondTable.bcount, 2) PERCENTAGE, firstTable.ACCESS_TYPE_ID from ("
			  +  "select sum(mycount) total_By_Access_Type, ACCESS_TYPE_ID from ("
			  + "select decode(ACCESS_TYPE_ID, 'ETHERNET', 'ETHERNET', 'Private Line', 'Private Line', 'Other') ACCESS_TYPE_ID, count(*) mycount from SALES_TRANSACTION_HISTORY  group by ACCESS_TYPE_ID) "
			  + "a group by a.ACCESS_TYPE_ID) firstTable, "
			  + "(select count(*) bcount from SALES_TRANSACTION_HISTORY) secondTable";
	
}
