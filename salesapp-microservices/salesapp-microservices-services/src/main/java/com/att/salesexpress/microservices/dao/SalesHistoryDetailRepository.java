package com.att.salesexpress.microservices.dao;

import org.springframework.data.repository.CrudRepository;

import com.att.salesexpress.microservices.entity.SalesHistoryDetail;
import com.att.salesexpress.microservices.entity.SalesHistoryDetailPK;

public interface SalesHistoryDetailRepository extends CrudRepository<SalesHistoryDetail, SalesHistoryDetailPK> {
	public SalesHistoryDetail findById(SalesHistoryDetailPK objSalesHistoryDetailPK);
}
