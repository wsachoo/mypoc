package com.att.salesexpress.microservices.util;

public interface SQLConstants {
	String sqlGetSalesHistoryDataByAccessType = "select rankTable.* from ("
			+ "select countTable.*, "
			+ "dense_rank() over (order by countTable.NUMBER_OF_SALES desc) RNK "
			+ "from ("
			+ "select "
			+ "count(*) over (partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID) as NUMBER_OF_SALES, "
			+ "concat(ROUND(count(*) over () * 100/ totalTrans.cnt, 2), '%')  as MATCHING_ROW_PERCENTAGE, "
			+ "sth.* "
			+ "from SALES_TRANSACTION_HISTORY sth, (select count(*) cnt from SALES_TRANSACTION_HISTORY) totalTrans "
			+ "where sth.ACCESS_TYPE_ID = :ACCESS_TYPE_ID "
			+ ") countTable "
			+ ") rankTable "
			+ "where rankTable.RNK = 1 and rownum <= :NUMBER_OF_ROWS"; 

	String sqlGetSalesHistoryDataByAccessTypeAndAccessSpeed = "select rankTable.* from ("
			+ "select countTable.*, "
			+ "dense_rank() over (order by countTable.NUMBER_OF_SALES desc) RNK "
			+ "from ("
			+ "select "
			+ "count(*) over (partition by sth.PORT_SPEED_ID) as NUMBER_OF_SALES, "
			+ "concat(ROUND(count(*) over () * 100/ totalTrans.cnt, 2), '%')  as MATCHING_ROW_PERCENTAGE, "
			+ "sth.* "
			+ "from SALES_TRANSACTION_HISTORY sth, (select count(*) cnt from SALES_TRANSACTION_HISTORY where ACCESS_TYPE_ID=:ACCESS_TYPE_ID) totalTrans "
			+ "where sth.ACCESS_TYPE_ID = :ACCESS_TYPE_ID "
			+ "and   sth.ACCESS_SPEED_ID = :ACCESS_SPEED_ID "
			+ ") countTable "
			+ ") rankTable "
			+ "where rankTable.RNK = 1 and rownum <= :NUMBER_OF_ROWS"; 

	String sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed = "select rankTable.* from ("
			+ "select countTable.*, "
			+ "dense_rank() over (order by countTable.NUMBER_OF_SALES desc) RNK "
			+ "from ("
			+ "select "
			+ "count(*) over () as NUMBER_OF_SALES, "
			+ "concat(ROUND(count(*) over () * 100/ totalTrans.cnt, 2), '%')  as MATCHING_ROW_PERCENTAGE, "
			+ "sth.* "
			+ "from SALES_TRANSACTION_HISTORY sth, ("
			+ "select count(*) cnt from SALES_TRANSACTION_HISTORY "
			+ "where ACCESS_TYPE_ID=:ACCESS_TYPE_ID and ACCESS_SPEED_ID=:ACCESS_SPEED_ID) totalTrans "
			+ "where sth.ACCESS_TYPE_ID = :ACCESS_TYPE_ID "
			+ "and   sth.ACCESS_SPEED_ID = :ACCESS_SPEED_ID "
			+ "and   sth.PORT_SPEED_ID = :PORT_SPEED_ID "
			+ ") countTable "
			+ ") rankTable "
			+ "where rankTable.RNK = 1 and rownum <= :NUMBER_OF_ROWS"; 

}
