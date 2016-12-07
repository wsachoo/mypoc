/*
 * Global variables declaration
 */
var SALESEXPRESS_CONSTANTS;
var siteMetaData;
var gUserDetails = {}; //Object to store user information
var gUserConfiguration = {}; //Object to store the configuration choices made by user. Holds the object to be submitted to server for storage in DB.

gUserConfiguration = (function() {
	var configStore = {
			"accessConfig" : {},
			"speedConfig" : {},
	};
	
	return {
		addConfigMetaInformation : function(key, value) {
			configStore[key] = value;
		},
		addAccessConfiguration : function(key, value) {
			configStore["accessConfig"][key] = value;
		},
		
		addSpeedConfiguration : function(key, value) {
			configStore["speedConfig"][key] = value;
		},
		
		getConfigurationData : function() {
			return configStore;
		}
	};
}());

SALESEXPRESS_CONSTANTS = (function() {
	var _jQueryTemplates = {
			"filter_access_type_template" : "/templates/filter_access_type_template.html",
			"access_config_options_template" : "/templates/access_config_options_template.html",
			"select_speed_configure_options" : "/templates/select_speed_configure_options.html"
	};
	
	var _jsonDataUrls = {
		"testSiteJsonDataUrl" : "/getMetaData/testSite",
		"siteConfigurationPostUrl" : "/postSiteConfiguration"
	};
	
	return {
		getJQueryTemplates : function() {
			return _jQueryTemplates;
		},
		
		getUrlPath : function(key) {
			return _jsonDataUrls[key];
		}		
	};
}());

function getTemplateDefinition(templatePath) {
    var jqXHR = $.ajax({
    	  method: "GET",
    	  url: templatePath,
    	  async: false
    });
    return jqXHR.responseText;
}

function httpGetWithJsonResponse(path) {
    var jqXHR = $.ajax({
    	  method: "GET",
    	  url: path,
    	  dataType: 'json',
    	  async: false
    });
    return jqXHR.responseJSON;
}

function httpAsyncPostWithJsonRequestResponse(postUrl, postData) {
  	return $.ajax({
		url: postUrl,
		data: postData,
		type: 'POST',
		async: 'true',
		dataType: 'json',
        contentType : "application/json"
    });	
}
