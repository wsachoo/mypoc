package com.att.salesexpress.webapp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.att.salesexpress.webapp.bean.admin.ProductConfigBean;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface SalesAdminOperationService {

	public void updateServicesFeaturesConfiguration(Map<String, Object> paramValues)
			throws JsonParseException, JsonMappingException, IOException, SQLException;

	void deleteServicesFeaturesConfiguration(Map<String, Object> paramValues)
			throws JsonParseException, JsonMappingException, IOException, SQLException;

	public void saveProductConfiguration(ProductConfigBean objProductConfigBean) throws SQLException;
	
	public List<String> getAllProductsToConfigure();

	public void deleteProductConfiguration(ProductConfigBean objProductConfigBean);


	Map<String, Object> getAccessSpeedByAccessType(String productType, String accessType);

	public Map<String, Object> getPortSpeedsByAccessSpeed(String productType, String accessType, String accessSpeed);
}
