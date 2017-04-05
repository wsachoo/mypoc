package com.att.salesexpress.webapp.service.db;

import java.util.List;

import com.att.salesexpress.webapp.entity.SolutionTmplQuestion;

public interface DbServiceJpa {
	public List<SolutionTmplQuestion> findAll();

}
