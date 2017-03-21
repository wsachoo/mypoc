package com.att.salesexpress.webapp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.att.salesexpress.webapp.bean.admin.ProductConfigBean;
import com.att.salesexpress.webapp.entity.SalesRules;
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

	@Override
	@Transactional
	public void saveProductConfiguration(ProductConfigBean objProductConfigBean) throws SQLException {
		logger.debug("Inside saveProductConfiguration() method.");
		List<SalesRules> salesRulesEntityList = objProductConfigBean.transformToSalesRules();
		dbServiceImpl.checkIfRuleAlreadyExits(salesRulesEntityList);

		List<SalesRules> rulesToInsert = new ArrayList<>();
		List<SalesRules> rulesToUpdate = new ArrayList<>();
		
		for (SalesRules salesRules : salesRulesEntityList) {
			if (salesRules.isBlnExitsInDb()) {
				rulesToUpdate.add(salesRules);
			}
			else {
				rulesToInsert.add(salesRules);
			}
		}
		
		dbServiceImpl.saveProductConfiguration(rulesToInsert);
		dbServiceImpl.updateProductConfiguration(rulesToUpdate);
		logger.debug("Exiting successfully from saveProductConfiguration() method.");
	}
	
	@Override
	public List<String> getAllProductsToConfigure(){
		logger.debug("Inside getAllProductsToConfigure() method");
		List<Map<String,Object>> productsList = dbServiceImpl.getDistinctProductsToConfigure();
		List<String> productsListAsString = new ArrayList<String>();
		for(Map<String, Object> product : productsList){
			productsListAsString.add(String.valueOf(product.get("product")));
		}
		return productsListAsString;
	}

	@Override
	public void deleteProductConfiguration(ProductConfigBean objProductConfigBean) {
		logger.debug("Inside deleteProductConfiguration() method.");
		List<SalesRules> salesRulesEntityList = objProductConfigBean.transformToSalesRules();
		dbServiceImpl.deleteProductConfiguration(salesRulesEntityList);
		logger.debug("Exiting successfully from deleteProductConfiguration() method.");
	}

	@Override
	public Map<String, Object> getAccessSpeedByAccessType(String productType, String accessType) {
		Map<String, Object> returnValues = new LinkedHashMap<>();
		List<Map<String, Object>> accessSpeeds = dbServiceImpl.getAccessSpeedByAccessType(productType, accessType);
		for (Map<String, Object> map : accessSpeeds) {
			String accessSpeed =map.get("ACCESS_SPEED_ID").toString();
			String transFormedAccessSpeed = transformSpeed(map.get("ACCESS_SPEED_ID").toString()).toString();
			logger.debug("Access Speed {}, Transformed Access Speed {}", accessSpeed, transFormedAccessSpeed);
			returnValues.put(accessSpeed, transFormedAccessSpeed);
		}
		
		return returnValues;
	}

	private Object transformSpeed(String object) {
		Double speed = Double.valueOf(object);
		if (speed < 1000) {
			return speed  + " bps";
		}
		else if (speed < (1000 * 1000)) {
			return (speed/1000) + " Kbps";
		}
		else if (speed < (1000 * 1000 * 1000)) {
			return (speed/(1000 * 1000)) + " Mbps";
		}
		else if (speed < (1000 * 1000 * 1000 * 1000)) {
			return (speed/(1000 * 1000 * 1000)) + " Gbps";
		}
		
		return null;
	}

	@Override
	public Map<String, Object> getPortSpeedsByAccessSpeed(String productType, String accessType, String accessSpeed) {
		Map<String, Object> returnValues = new LinkedHashMap<>();
		Long lAccessSpeed = Long.parseLong(accessSpeed);
		List<Map<String, Object>> accessSpeeds = dbServiceImpl.getPortSpeedsByAccessSpeed(productType, accessType, lAccessSpeed);
		for (Map<String, Object> map : accessSpeeds) {
			String portSpeed = map.get("PORT_SPEED_ID").toString();
			String transFormedPortSpeed = transformSpeed(portSpeed).toString();
			logger.debug("Port Speed {}, Transformed port Speed {}", portSpeed, transFormedPortSpeed);
			returnValues.put(portSpeed, transFormedPortSpeed);
		}
		
		return returnValues;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String getServiceFeaturesMetaData(String siteType) {
		return dbServiceImpl.getServiceFeaturesMetaDataBySiteName(siteType);
	}

	@Override
	public Map<String, Object> getDistinctAccessSpeedByAccessType(String accessType) {
		Map<String, Object> returnValues = new LinkedHashMap<>();
		List<Map<String, Object>> accessSpeeds = dbServiceImpl.getDistinctAccessSpeedByAccessType(accessType);
		for (Map<String, Object> map : accessSpeeds) {
			String accessSpeed =map.get("ACCESS_SPEED_ID").toString();
			String transFormedAccessSpeed = transformSpeed(map.get("ACCESS_SPEED_ID").toString()).toString();
			logger.debug("Access Speed {}, Transformed Access Speed {}", accessSpeed, transFormedAccessSpeed);
			returnValues.put(accessSpeed, transFormedAccessSpeed);
		}
		
		return returnValues;
	}

	@Override
	public Map<String, Object> getDistinctPortSpeedsByAccessSpeed(String accessType, String accessSpeed) {
		Map<String, Object> returnValues = new LinkedHashMap<>();
		Long lAccessSpeed = Long.parseLong(accessSpeed);
		List<Map<String, Object>> accessSpeeds = dbServiceImpl.getDistinctPortSpeedsByAccessSpeed(accessType, lAccessSpeed);
		
		for (Map<String, Object> map : accessSpeeds) {
			String portSpeed = map.get("PORT_SPEED_ID").toString();
			String transFormedPortSpeed = transformSpeed(portSpeed).toString();
			logger.debug("Port Speed {}, Transformed port Speed {}", portSpeed, transFormedPortSpeed);
			returnValues.put(portSpeed, transFormedPortSpeed);
		}
		
		return returnValues;
	}

	@Override
	public void updateProductConfiguration(ProductConfigBean objProductConfigBean) throws SQLException {
		logger.debug("Entered successfully from updateProductConfiguration() method.");
		List<SalesRules> salesRulesEntityList = objProductConfigBean.transformToSalesRules();		
		SalesRules salesRule = salesRulesEntityList.get(0);
		dbServiceImpl.deleteProductConfiguration(salesRule.getPortType(), salesRule.getAccessSpeed(), salesRule.getPortSpeed());
		dbServiceImpl.saveProductConfiguration(salesRulesEntityList);
		logger.debug("Exiting successfully from updateProductConfiguration() method.");
	}
}
