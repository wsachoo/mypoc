package com.att.salesexpress.microservices.dao;

import java.math.BigDecimal;

import org.springframework.data.repository.CrudRepository;

import com.att.salesexpress.microservices.entity.SalesVnfRule;

public interface SalesVnfRuleRepository extends CrudRepository<SalesVnfRule, BigDecimal> {
	public SalesVnfRule findByRuleId(BigDecimal ruleId);
}
