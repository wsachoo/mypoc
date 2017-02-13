package com.att.salesexpress.webapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.att.salesexpress.webapp.entity.oracle.SalesRulesOracle;

@Repository
@Transactional(value = "oracleTransactionManager")
public class MyDaoOracleImpl implements MyDaoOracle {

	@PersistenceContext(unitName = "oracle")
	//@Qualifier(value = "oracleEntityManagerFactory")
	private EntityManager em;

	@Override
	public List<SalesRulesOracle> findAll() {
		TypedQuery<SalesRulesOracle> q = em.createNamedQuery("SalesRulesOracle.findAll", SalesRulesOracle.class);
		List<SalesRulesOracle> result = q.getResultList();
		return result;
	}
	
}
