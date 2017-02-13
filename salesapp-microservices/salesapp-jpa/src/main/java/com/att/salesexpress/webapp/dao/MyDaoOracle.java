package com.att.salesexpress.webapp.dao;

import java.util.List;

import com.att.salesexpress.webapp.entity.oracle.SalesRulesOracle;

public interface MyDaoOracle {
	public List<SalesRulesOracle> findAll();
}
