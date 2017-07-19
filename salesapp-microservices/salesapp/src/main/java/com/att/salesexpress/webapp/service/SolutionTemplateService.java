package com.att.salesexpress.webapp.service;

import java.util.List;
import java.util.Map;

import com.att.salesexpress.webapp.entity.SalesFlexDiscountData;
import com.att.salesexpress.webapp.entity.SolutionTmplQuestion;

public interface SolutionTemplateService {
	public SolutionTmplQuestion findByQuesSeqId(Map<String, Object> paramValues);

	public List<SolutionTmplQuestion> findAllQuestions(Map<String, Object> paramValues);

	public List<SalesFlexDiscountData> findAllSalesFlexDiscountData();
}
