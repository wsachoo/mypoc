package com.att.salesexpress.webapp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface SalesAdminOperationService {

	public void updateServicesFeaturesConfiguration(Map<String, Object> paramValues) throws JsonParseException, JsonMappingException, IOException, SQLException;

	void deleteServicesFeaturesConfiguration(Map<String, Object> paramValues)
			throws JsonParseException, JsonMappingException, IOException, SQLException;
}
