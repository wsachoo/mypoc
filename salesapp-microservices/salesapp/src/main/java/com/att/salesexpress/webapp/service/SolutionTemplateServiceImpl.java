package com.att.salesexpress.webapp.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.salesexpress.webapp.entity.SolutionTmplQuestion;
import com.att.salesexpress.webapp.service.db.DbServiceJpa;

@Service
public class SolutionTemplateServiceImpl implements SolutionTemplateService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DbServiceJpa dbServiceJpa;

	@Override
	public SolutionTmplQuestion findByQuesSeqId(Map<String, Object> paramValues) {
		logger.debug("Entered findByQuesSeqId(); method");
		Long queSeqId = Long.parseLong(paramValues.get("QUES_SEQ_ID").toString());
		String product = paramValues.get("PRODUCT").toString();
		logger.debug("Question Id received: {}, product name: {}", queSeqId, product);
		SolutionTmplQuestion solutionTmplQuestion = dbServiceJpa.findByQuesSeqId(queSeqId);
		logger.debug("Solution Template Question retrieved is: " + solutionTmplQuestion);
		return solutionTmplQuestion;
	}

	@Override
	public List<SolutionTmplQuestion> findAllQuestions(Map<String, Object> paramValues) {
		logger.debug("Entered findAllQuestions(); method");
		List<SolutionTmplQuestion> solutionTmplQuestions = dbServiceJpa.findAllQuestions();
		for (SolutionTmplQuestion solutionTmplQuestion : solutionTmplQuestions) {
			solutionTmplQuestion.setSalesSolTmplAns(null);
		}
		logger.debug("Solution Template Questions retrieved are: " + solutionTmplQuestions);
		return solutionTmplQuestions;
	}
}
