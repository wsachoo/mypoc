package com.att.salesexpress.microservices.util;

public interface SQLConstantsPostgresql {
	String sqlGetSalesHistoryDataByAccessTypeIndexWithinGroup20170607 = "select rankTable.* from ("
			+ "select countTable.*, "
			+ "dense_rank() over (order by countTable.NUMBER_OF_SALES desc) RNK "
			+ "from ("
			+ "select "
			+ "count(*) over (partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID, sth.BUNDLE_CD) as NUMBER_OF_SALES, "
			+ "ROUND(count(*) over (partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID) * 100::numeric/ totalTrans.cnt, 2) as MATCHING_ROW_PERCENTAGE, "
			+ "rank() over ( partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID, sth.BUNDLE_CD order by sth.SITE_ID ) indexWithinGroup,"
			+ "sth.* "
			+ "from SALES_TRANS_HISTORY_MIS_EXP sth, (select count(*) cnt from SALES_TRANS_HISTORY_MIS_EXP) totalTrans "
			+ "where sth.ACCESS_TYPE_ID = :ACCESS_TYPE_ID "
			+ ") countTable "
			+ ") rankTable "
			+ "where rankTable.indexWithinGroup < :INDEX_WITHIN_GROUP "
			+ "order by MATCHING_ROW_PERCENTAGE desc, MRC asc LIMIT :NUMBER_OF_ROWS ";

	String sqlGetSalesHistoryDataByAccessTypeIndexWithinGroup = "select * from ( select "
			+ "row_number() over(partition by rankTable.DESIGN_NAME,rankTable.ACCESS_SPEED_ID, rankTable.MANAGED_ROUTER, rankTable.MRC, rankTable.NRC order by rankTable.SITE_ID) myrow, "
			+ "rankTable.* from ( "
			+ "        select countTable.*, "
			+ "            dense_rank() over (order by countTable.NUMBER_OF_SALES desc) RNK "
			+ "            from ( select "
			+ "            count(*) over (partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID, sth.BUNDLE_CD) as NUMBER_OF_SALES, "
			+ "            ROUND(count(*) over (partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID) * 100/ totalTrans.cnt, 2) as MATCHING_ROW_PERCENTAGE, "
			+ "            rank() over ( partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID, sth.BUNDLE_CD order by sth.SITE_ID ) indexWithinGroup, "
			+ "            sth.* "
			+ "            from SALES_TRANS_HISTORY_MIS_EXP sth, (select count(*) cnt from SALES_TRANS_HISTORY_MIS_EXP) totalTrans "
			+ "            where sth.ACCESS_TYPE_ID = :ACCESS_TYPE_ID  "
			+ "            ) countTable "
			+ "            ) rankTable "
			+ "            where rankTable.indexWithinGroup < :INDEX_WITHIN_GROUP"
			+ "            order by MATCHING_ROW_PERCENTAGE desc, MRC asc"
			+ ") x where x.myrow=1 LIMIT :NUMBER_OF_ROWS ";

	String sqlGetSalesHistoryDataByAccessType = "select rankTable.* from ("
			+ "select countTable.*, "
			+ "dense_rank() over (order by countTable.NUMBER_OF_SALES desc) RNK "
			+ "from ("
			+ "select "
			+ "count(*) over (partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID, sth.BUNDLE_CD) as NUMBER_OF_SALES, "
			+ "ROUND(count(*) over (partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID) * 100::numeric/ totalTrans.cnt, 2) as MATCHING_ROW_PERCENTAGE, "
			+ "rank() over ( partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID, sth.BUNDLE_CD order by sth.SITE_ID ) indexWithinGroup,"
			+ "sth.* "
			+ "from SALES_TRANS_HISTORY_MIS_EXP sth, (select count(*) cnt from SALES_TRANS_HISTORY_MIS_EXP) totalTrans "
			+ "where sth.ACCESS_TYPE_ID = :ACCESS_TYPE_ID "
			+ ") countTable "
			+ ") rankTable "
			+ "where rankTable.indexWithinGroup = 1 "
			+ "order by MATCHING_ROW_PERCENTAGE desc, MRC asc LIMIT :NUMBER_OF_ROWS";
	
	String sqlGetSalesHistoryDataByAccessTypeForOtherAccessType = "select rankTable.* from ("
			+ "select countTable.*, "
			+ "dense_rank() over (order by countTable.NUMBER_OF_SALES desc) RNK "
			+ "from ("
			+ "select "
			+ "count(*) over (partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID) as NUMBER_OF_SALES, "
			+ "ROUND(count(*) over (partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID) * 100::numeric/ totalTrans.cnt, 2) as MATCHING_ROW_PERCENTAGE, "
			+ "rank() over ( partition by sth.ACCESS_SPEED_ID,  sth.PORT_SPEED_ID order by sth.SITE_ID ) indexWithinGroup,"
			+ "sth.* "
			+ "from SALES_TRANS_HISTORY_MIS_EXP sth, (select count(*) cnt from SALES_TRANS_HISTORY_MIS_EXP) totalTrans "
			+ "where sth.ACCESS_TYPE_ID NOT IN ('ETHERNET', 'Private Line')"
			+ ") countTable "
			+ ") rankTable "
			+ "where rankTable.indexWithinGroup = 1 LIMIT :NUMBER_OF_ROWS";

	String sqlGetSalesHistoryDataByAccessTypeAndAccessSpeed = "select rankTable.* from ("
			+ "select countTable.*, "
			+ "dense_rank() over (order by countTable.NUMBER_OF_SALES desc) RNK "
			+ "from ("
			+ "select "
			+ "count(*) over (partition by sth.PORT_SPEED_ID, sth.BUNDLE_CD) as NUMBER_OF_SALES, "
			+ " ROUND(count(*) over (partition by sth.PORT_SPEED_ID) * 100::numeric/ totalTrans.cnt, 2)  as MATCHING_ROW_PERCENTAGE, "
			+ "rank() over ( partition by sth.PORT_SPEED_ID, sth.BUNDLE_CD order by sth.SITE_ID ) indexWithinGroup,"
			+ "sth.* "
			+ "from SALES_TRANS_HISTORY_MIS_EXP sth, (select count(*) cnt from SALES_TRANS_HISTORY_MIS_EXP where ACCESS_TYPE_ID=:ACCESS_TYPE_ID) totalTrans "
			+ "where sth.ACCESS_TYPE_ID = :ACCESS_TYPE_ID "
			+ "and   sth.ACCESS_SPEED_ID = :ACCESS_SPEED_ID "
			+ ") countTable "
			+ ") rankTable "
			+ "where rankTable.indexWithinGroup <= (:NUMBER_OF_ROWS)/2 LIMIT :NUMBER_OF_ROWS";

	String sqlGetSalesHistoryDataByAccessTypeAndPortSpeedAndAccessSpeed = "select rankTable.* from ("
			+ "select countTable.*, "
			+ "dense_rank() over (order by countTable.NUMBER_OF_SALES desc) RNK "
			+ "from ("
			+ "select "
			+ "count(*) over (partition by sth.BUNDLE_CD) as NUMBER_OF_SALES, "
			+ "ROUND(count(*) over () * 100::numeric/ totalTrans.cnt, 2) as MATCHING_ROW_PERCENTAGE, "
			+ "rank() over (partition by sth.BUNDLE_CD order by sth.SITE_ID ) indexWithinGroup,"
			+ "sth.* "
			+ "from SALES_TRANS_HISTORY_MIS_EXP sth, ("
			+ "select count(*) cnt from SALES_TRANS_HISTORY_MIS_EXP "
			+ "where ACCESS_TYPE_ID=:ACCESS_TYPE_ID and ACCESS_SPEED_ID=:ACCESS_SPEED_ID) totalTrans "
			+ "where sth.ACCESS_TYPE_ID = :ACCESS_TYPE_ID "
			+ "and   sth.ACCESS_SPEED_ID = :ACCESS_SPEED_ID "
			+ "and   sth.PORT_SPEED_ID = :PORT_SPEED_ID "
			+ ") countTable "
			+ ") rankTable "
			+ "where rankTable.indexWithinGroup <= (:NUMBER_OF_ROWS)/2 LIMIT :NUMBER_OF_ROWS";
	
	String sqlGetSalesHistoryPercentageRecordsByAccessType = "select ROUND(firstTable.total_By_Access_Type * 100/secondTable.bcount, 2) PERCENTAGE, firstTable.ACCESS_TYPE_ID || '_' || firstTable.ACCESS_SPEED_ID  as ACCESS_TYPE_ID"
			+ ",(select distinct ACCESS_SPEED_S from SALES_TRANS_HISTORY_MIS_EXP where ACCESS_TYPE_ID=firstTable.ACCESS_TYPE_ID and ACCESS_SPEED_ID=firstTable.ACCESS_SPEED_ID) ACCESS_SPEED_S "
			+ "from ( select sum(mycount) total_By_Access_Type, ACCESS_TYPE_ID, ACCESS_SPEED_ID  from ( "
			+ " select ACCESS_TYPE_ID, ACCESS_SPEED_ID, count(*) mycount from SALES_TRANS_HISTORY_MIS_EXP  group by ACCESS_TYPE_ID, ACCESS_SPEED_ID )  "
			+ "a group by a.ACCESS_TYPE_ID, a.ACCESS_SPEED_ID ) firstTable, "
			+ "(select count(*) bcount from SALES_TRANS_HISTORY_MIS_EXP) secondTable "
			+ "order by PERCENTAGE desc";
	
	String sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeed = "select * from sales_rules_mis_exp where  access_type = :ACCESS_TYPE_ID and"
			  + "  ACCESS_SPEED_ID = :ACCESS_SPEED_ID_STRING and MRC is not null and PORT_SPEED_ID <= :ACCESS_SPEED_ID LIMIT :NUMBER_OF_ROWS";
	
	String sqlGetSalesRulesForMISEXPByAccessTypeAndAccessSpeedAndPortSpeed = "select * from sales_rules_mis_exp where  access_type = :ACCESS_TYPE_ID and"
			  + "  ACCESS_SPEED_ID = :ACCESS_SPEED_ID and PORT_SPEED_ID =:PORT_SPEED_ID  and MRC is not null LIMIT 1";
	
	
}
