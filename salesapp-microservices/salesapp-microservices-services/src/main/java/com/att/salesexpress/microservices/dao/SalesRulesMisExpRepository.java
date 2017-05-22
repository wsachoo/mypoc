package com.att.salesexpress.microservices.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.att.salesexpress.microservices.entity.SalesRulesMisExpDetail;
import com.att.salesexpress.microservices.entity.SalesRulesMisExpDetailPK;

public interface SalesRulesMisExpRepository extends CrudRepository<SalesRulesMisExpDetail, SalesRulesMisExpDetailPK> {
	public SalesRulesMisExpDetail findById(SalesRulesMisExpDetailPK objSalesRulesmisExpDetailPK);

	@Query("SELECT DISTINCT portSpeed FROM SalesRulesMisExpDetail p WHERE p.accessSpeedId = :accessSpeedId)")
	public List<String> findDistinctPortSpeedByAccessSpeedId(@Param("accessSpeedId") String accessSpeedId);
}
