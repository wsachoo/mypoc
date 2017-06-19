package com.att.pricerd.microservices.dao;

import org.springframework.data.repository.CrudRepository;

import com.att.pricerd.microservices.entity.SalesHistoryDetail;
import com.att.pricerd.microservices.entity.SalesHistoryDetailPK;

public interface SalesHistoryDetailRepository extends CrudRepository<SalesHistoryDetail, SalesHistoryDetailPK> {
	public SalesHistoryDetail findById(SalesHistoryDetailPK objSalesHistoryDetailPK);
}
