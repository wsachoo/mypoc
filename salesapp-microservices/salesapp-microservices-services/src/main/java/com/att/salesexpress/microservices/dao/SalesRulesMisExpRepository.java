package com.att.salesexpress.microservices.dao;

import org.springframework.data.repository.CrudRepository;

import com.att.salesexpress.microservices.entity.SalesRulesMisExpDetail;
import com.att.salesexpress.microservices.entity.SalesRulesMisExpDetailPK;

public interface SalesRulesMisExpRepository extends CrudRepository<SalesRulesMisExpDetail, SalesRulesMisExpDetailPK>{
	public SalesRulesMisExpDetail findById(SalesRulesMisExpDetailPK objSalesRulesmisExpDetailPK);
}
