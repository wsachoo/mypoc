package com.att.salesexpress.webapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.att.salesexpress.webapp.entity.postgres.SalesRulesPostgreSql;

@Repository
@Transactional(value = "postgresTransactionManager")

public class MyDaoPostgreSqlImpl implements MyDaoPostgreSql {

	@PersistenceContext(unitName = "postgres")
	//@Qualifier(value = "postgresEntityManagerFactory")
	private EntityManager em;

	@Override
	public List<SalesRulesPostgreSql> findAll() {
		TypedQuery<SalesRulesPostgreSql> q = em.createNamedQuery("SalesRulesPostgreSql.findAll", SalesRulesPostgreSql.class);
		List<SalesRulesPostgreSql> result = q.getResultList();
		return result;
	}

}
