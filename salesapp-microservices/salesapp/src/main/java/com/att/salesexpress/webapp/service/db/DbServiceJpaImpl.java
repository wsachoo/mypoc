package com.att.salesexpress.webapp.service.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.att.salesexpress.webapp.entity.SolutionTmplQuestion;

@Repository
@Transactional(value = "oracleTransactionManager")
public class DbServiceJpaImpl implements DbServiceJpa {
	@PersistenceContext(unitName = "oracle")
	@Qualifier(value = "oracleEntityManagerFactory")
	private EntityManager em;

	@Override
	public List<SolutionTmplQuestion> findAll() {
		TypedQuery<SolutionTmplQuestion> q = em.createNamedQuery("SolutionTmplQuestion.findAll",
				SolutionTmplQuestion.class);
		List<SolutionTmplQuestion> result = q.getResultList();
		return result;
	}

}
