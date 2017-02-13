package com.att.salesexpress.webapp.dao;

import java.util.List;

import com.att.salesexpress.webapp.entity.postgres.SalesRulesPostgreSql;

public interface MyDaoPostgreSql {
	public List<SalesRulesPostgreSql> findAll();
}
