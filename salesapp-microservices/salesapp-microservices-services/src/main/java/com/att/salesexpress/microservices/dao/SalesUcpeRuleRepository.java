package com.att.salesexpress.microservices.dao;

import java.math.BigDecimal;

import org.springframework.data.repository.CrudRepository;

import com.att.salesexpress.microservices.entity.SalesUcpeRule;

public interface SalesUcpeRuleRepository extends CrudRepository<SalesUcpeRule, Long> {
	public SalesUcpeRule findByRuleId(Long ruleId);
}
