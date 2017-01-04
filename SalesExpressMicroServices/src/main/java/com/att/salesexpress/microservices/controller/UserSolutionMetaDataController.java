package com.att.salesexpress.microservices.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.att.salesexpress.microservices.db.DbServiceInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserSolutionMetaDataController {

	private static final Logger logger = LoggerFactory.getLogger(UserSolutionMetaDataController.class);

	@Resource(name = "DbServiceImpl.microservice")
	DbServiceInterface DbMicroServiceImpl;

	@RequestMapping("/userSolutionMetaData")
	public String getUserSolutionMetaData(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "solutionId") Long solutionId) throws JsonProcessingException {
		logger.info("Inside getUserSolutionMetaData method with userId {} and solutionId {}", userId, solutionId);

		Map<String, Object> objUserDetail = DbMicroServiceImpl.findUserDetailByUserIdSolutionId(userId, solutionId);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(objUserDetail);

		logger.debug("Return user solution json as : " + jsonString);
		return jsonString;
	}
}
