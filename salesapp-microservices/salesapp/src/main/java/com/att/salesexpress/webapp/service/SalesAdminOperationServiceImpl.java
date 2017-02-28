package com.att.salesexpress.webapp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.att.salesexpress.webapp.service.db.DbService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SalesAdminOperationServiceImpl implements SalesAdminOperationService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DbService dbServiceImpl;

	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void updateServicesFeaturesConfiguration(Map<String, Object> paramValues)
			throws JsonParseException, JsonMappingException, IOException, SQLException {
		logger.debug("Inside updateServicesFeaturesConfiguration() method.");
		String strServiceFeaturesConfig = dbServiceImpl.retrieveServicesFeaturesConfiguration();

		Map<String, Object> servicesFeaturesMap = jacksonObjectMapper.readValue(strServiceFeaturesConfig,
				new TypeReference<Map<String, Object>>() {
				});

		List<Map<String, Object>> servicesFeaturesList = (List<Map<String, Object>>) servicesFeaturesMap
				.get("serviceAndFeatures");
		servicesFeaturesList.add(paramValues);

		String strUpdServicesFeaturesConfig = jacksonObjectMapper.writeValueAsString(servicesFeaturesMap);
		dbServiceImpl.updateServiceFeaturesConfiguration(strUpdServicesFeaturesConfig);
		logger.debug("Exiting successfully from updateServicesFeaturesConfiguration() method.");
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void deleteServicesFeaturesConfiguration(Map<String, Object> paramValues)
			throws JsonParseException, JsonMappingException, IOException, SQLException {
		logger.debug("Inside deleteServicesFeaturesConfiguration() method.");
		String strServiceFeaturesConfig = dbServiceImpl.retrieveServicesFeaturesConfiguration();

		Map<String, Object> servicesFeaturesMap = jacksonObjectMapper.readValue(strServiceFeaturesConfig,
				new TypeReference<Map<String, Object>>() {
				});

		String idToDelete = (String) paramValues.get("id");
		logger.debug("idToDelete : " + idToDelete);

		List<Map<String, Object>> servicesFeaturesList = (List<Map<String, Object>>) servicesFeaturesMap
				.get("serviceAndFeatures");

		Iterator<Map<String, Object>> iterator = servicesFeaturesList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> map = iterator.next();
			String id = (String) map.get("id");
			logger.debug("Comparing idToDelete : " + id);
			if (idToDelete.equalsIgnoreCase(id.trim())) {
				iterator.remove();
				break;
			}
		}
		
		String strUpdServicesFeaturesConfig = jacksonObjectMapper.writeValueAsString(servicesFeaturesMap);
		dbServiceImpl.updateServiceFeaturesConfiguration(strUpdServicesFeaturesConfig);
		logger.debug("Exiting successfully from deleteServicesFeaturesConfiguration() method.");
	}
}
