/*
 * Global variables declaration
 */
var SALESEXPRESS_CONSTANTS; //Object to store URLs for all jQuery templates
var siteMetaData; //Object to store site configuration meta information from sitedetail table. It is populated from onload_salesexpress script.
var gUserDetails = {}; //Object to store user's information from user_detail table.
var gUserConfiguration = {}; //Object to store the configuration choices made by user. Holds the object to be submitted to server for storage in DB.
var gSiteIdNameMapping = {}; //Object to hold site_id and site_name mapping from database table.

SALESEXPRESS_CONSTANTS = (function() {
	var _jQueryTemplates = {
			"filter_access_type_template" : "templates/filter_access_type_template.html",
			"access_config_options_template" : "templates/access_config_options_template.html",
			"select_speed_configure_options" : "templates/select_speed_configure_options.html",
			"modify_configuration_options" : "templates/modify_configuration_options.html",
			"service_features_template" : "templates/service_features_template.html",
			"service_features_data"		:"templates/service_features_data.html",
			"service_features_security" : "templates/service_features_security.html",
			"service_features_misc"		: "templates/service_features_misc.html",
			"show_results_template"		: "templates/show_results_template.html",
			"port_config_options_template" : "templates/port_config_options_template.html",
			"service_features_init" : "templates/service_features_init.html"
	};
	
	var _jsonDataUrls = {
		"testSiteJsonDataUrl" : "getMetaData/testSite",
		"siteConfigurationPostUrl" : "postSiteConfiguration",
		"postServiceFeaturesOptionsUrl":"postServiceFeaturesOptions",
		"resultsPageUrl" : "results",
		"postSpeedsBySelectedAccessSpeedUrl" : "getPortSpeedsByAccessSpeed",
		"getAllAccessSpeedsUrl" : "getAllAccessSpeeds"
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

/*
 * Object gUserConfiguration holds all the user configuration selections.
 */
gUserConfiguration = (function() {
	var userConfigStore = {};
	var tempConfigStore = {
			"accessConfig" : {},
			"portConfig" : {},
			"resiliencyConfig" : {}
	};
	
	return {
		addConfigurationToSite : function(siteType) {
			userConfigStore[siteType] = $.extend(true, {}, tempConfigStore);
		},
		
		addConfigMetaInformation : function(key, value) {
			userConfigStore[key] = value;
		},
		
		addAccessConfiguration : function(key, value) {
			tempConfigStore["accessConfig"][key] = value;
		},
		
		addPortConfiguration : function(key, value) {
			tempConfigStore["portConfig"][key] = value;
		},
		
		addResiliencyConfiguration : function(key, value) {
			tempConfigStore["resiliencyConfig"][key] = value;
		},
		
		getConfigurationData : function() {
			return tempConfigStore;
		},
		
		getUserConfigurationData : function() {
			return userConfigStore;
		},
		
		clearConfiguration : function() {
			userConfigStore = {};
			tempConfigStore = {
					"accessConfig" : {},
					"portConfig" : {},
					"resiliencyConfig" : {}
			};
		}
	};
}());

/*
 * This function is used for populating the gUserConfiguration object from elements inside the form passed to it.
 */
function updateInMemoryConfigurationFromFormObject(form) {
	var formData = form.serializeArray();
	
    $.each(formData, function() {
    	var nameArray = this.name.split("-");
    	
    	if (nameArray.length == 1) {
    		gUserConfiguration.addConfigMetaInformation(this.name, this.value);
    	}
    	else if (nameArray[0] === "accessConfig") {
    		gUserConfiguration.addAccessConfiguration(nameArray[1], this.value);
    	}
    	else if (nameArray[0] === "portConfig") {
    		gUserConfiguration.addPortConfiguration(nameArray[1], this.value);
    	}
    	else if (nameArray[0] === "resiliencyConfig") {
    		gUserConfiguration.addResiliencyConfiguration(nameArray[1], this.value);
    	}
    	else {
    		gUserConfiguration.addConfigMetaInformation(this.name, this.value);
    	}
    });

	var chkHeadequarters = $('input[name="chkHeadequarters"').is(":checked");
	var chkAccountReceivables = $('input[name="chkAccountReceivables"').is(":checked");
	var chkDistributionCenter = $('input[name="chkDistributionCenter"').is(":checked");

	if (chkHeadequarters) {
		gUserConfiguration.addConfigurationToSite("headQuarters");
		gUserConfiguration.getUserConfigurationData().headQuarters["siteId"] = $('input[name="chkHeadequarters"').val();
	}	
	if (chkAccountReceivables) {
		gUserConfiguration.addConfigurationToSite("accountReceivables");
		gUserConfiguration.getUserConfigurationData().accountReceivables["siteId"] = $('input[name="chkAccountReceivables"').val();
	}    
	if (chkDistributionCenter) {
		gUserConfiguration.addConfigurationToSite("distributionCenter");
		gUserConfiguration.getUserConfigurationData().distributionCenter["siteId"] = $('input[name="chkDistributionCenter"').val();
	}    
}

function getTemplateDefinition(templatePath) {
    var jqXHR = $.ajax({
    	  method: "GET",
    	  url: templatePath,
    	  async: false
    });
    return jqXHR.responseText;
}

function httpGetWithJsonResponse(path, jsonData) {
    var jqXHR = $.ajax({
    	  method: "GET",
    	  url: path,
    	  data: jsonData,
    	  dataType: 'json',
    	  async: false
    });
    
    if (jqXHR.responseJSON) {
    	return jqXHR.responseJSON;
    }
    else if (jqXHR.responseText) {
    	return jqXHR.responseText;
    }
}

/*
 * Method used for posting the form data to server.
 * postData is the json data that needs to be submitted to the server at postUrl.
 * It returns the promise that holds the json response from the server.
 */
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
