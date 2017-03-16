/*
 * Global variables declaration
 */
var SALESEXPRESS_CONSTANTS; //Object to store URLs for all jQuery templates
var siteMetaData; //Object to store site configuration meta information from sitedetail table. It is populated from onload_salesexpress script.
var gUserDetails = {}; //Object to store user's information from user_detail table.
var gUserConfiguration = {}; //Object to store the configuration choices made by user. Holds the object to be submitted to server for storage in DB.
var gSiteIdNameMapping = {}; //Object to hold site_id and site_name mapping from database table.
var serviceFeaturesMetaData;//Object to hold serviceFeatures meta data 
var guserServiceFeatures = {};

SALESEXPRESS_CONSTANTS = (function() {
	var _jQueryTemplates = {
			"filter_access_type_template" 	 : contextPath + "/templates/filter_access_type_template.html",
			"access_config_options_template" : contextPath + "/templates/access_config_options_template.html",
			"select_speed_configure_options" : contextPath + "/templates/select_speed_configure_options.html",
			"modify_configuration_options" 	 : contextPath + "/templates/modify_configuration_options.html",
			"service_features_template" 	 : contextPath + "/templates/service_features_template.html",
			"service_features_data"			 : contextPath + "/templates/service_features_data.html",
			"service_features_security" 	 : contextPath + "/templates/service_features_security.html",
			"service_features_misc"			 : contextPath + "/templates/service_features_misc.html",
			"show_results_template"			 : contextPath + "/templates/show_results_template.html",
			"port_config_options_template" 	 : contextPath + "/templates/port_config_options_template.html",
			"service_features_init" 	 	 : contextPath + "/templates/service_features_init.html",
			"init_access_config_template" 	 : contextPath + "/templates/init_access_config_template.html",
			"init_gmap_template" 			 : contextPath + "/templates/init_gmap_template.html",
			"generate_contract_template" 	 : contextPath + "/templates/generate_contract.html",
			"common_services_features_template": contextPath + "/templates/common_services_features.html"
	};
	
	var _jsonDataUrls = {
		"testSiteJsonDataUrl" : "getMetaData/testSite",
		"siteConfigurationPostUrl" : "postSiteConfiguration",
		"postServiceFeaturesOptionsUrl":"postServiceFeaturesOptions",
		"resultsPageUrl" : "results",
		"postSpeedsBySelectedAccessSpeedUrl" : "getPortSpeedsByAccessSpeed",
		"getAllAccessSpeedsUrl" : "getAllAccessSpeeds",
		"getServiceFeaturesMetaDataUrl" : "getServiceFeaturesMetaData/testSite",
		"addAdminServiceFeaturesUrl" : "addServicesFeatures",
		"deleteAdminServiceFeaturesUrl" : "deleteServicesFeatures",
		"saveProductConfigurationUrl" : "saveProductConfiguration",
		"deleteProductConfigurationUrl" : "deleteProductConfiguration",
		"IBM_WATSON_CHAT_URL" : "https://watson-sales.mybluemix.net/api/smart",
		"IBM_WATSON_LANGUAGE_TRANSLATOR_URL" : "https://saleslangconvapp.mybluemix.net/salesLanguageTranslator",
		"getAllProductsUrl" : "getAllProducts",
		"getAccessSpeedByAccessTypeUrl": "getAccessSpeedByAccessType",
		"getPortSpeedsByAccessSpeedUrl" : "getPortSpeedsByAccessSpeed",
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
	var userConfigStore = {
			"sites" : {}
	};

	var tempConfigStore = {
			"accessConfig" : {},
			"portConfig" : {},
			"resiliencyConfig" : {}
	};
	
	return {
		addConfigurationToSite : function(siteName) {
			//userConfigStore[siteType] = $.extend(true, {}, tempConfigStore);
			userConfigStore["sites"][siteName] = ($.extend(true, {}, tempConfigStore));
		},
		
		addConfigMetaInformation : function(key, value) {
			userConfigStore[key] = value;
		},
		
		addToSiteConfiguration : function(key, value) {
			tempConfigStore[key] = value;
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
			userConfigStore = {
					"sites" : {}
			};
		},
		clearSiteConfiguration : function(){
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

    $("#salesexpress-side-bar").find(":checkbox").each(function() {
    	if ( $(this).is(':checked') ) {
    		var siteId = $(this).val();
    		var siteName =  $(this).data('name');
    		gUserConfiguration.addToSiteConfiguration("siteId", siteId);
    		gUserConfiguration.addToSiteConfiguration("siteName", siteName);
    		gUserConfiguration.addConfigurationToSite(siteId);    		
    	}
    });
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
	
    $.ajaxSetup({
        headers : { 
        	'X-CSRF-TOKEN' : $('meta[name="_csrf"]').attr('content')
        }
    });
    
    $(document).ajaxStart(function(){ 
        $("body").addClass('ajaxLoading');
    });
    $(document).ajaxStop(function(){ 
        $("body").removeClass('ajaxLoading');
    });
    
  	var postResp = $.ajax({
		url: postUrl,
		data: postData,
		type: 'POST',
		async: 'true',
		dataType: 'json',
        contentType : "application/json"
    });	
  	
  	delete $.ajaxSettings.headers["X-CSRF-TOKEN"];
  	
  	return postResp;
}

function httpAsyncPostWithJsonRequestResponseSynchronous(postUrl, postData) {
	
    $.ajaxSetup({
        headers : { 
        	'X-CSRF-TOKEN' : $('meta[name="_csrf"]').attr('content')
        }
    });
    
    $(document).ajaxStart(function(){ 
        $("body").addClass('ajaxLoading');
    });
    $(document).ajaxStop(function(){ 
        $("body").removeClass('ajaxLoading');
    });
    
  	var postResp = $.ajax({
		url: postUrl,
		data: postData,
		type: 'POST',
		async: 'false',
		dataType: 'json',
        contentType : "application/json"
    });	
  	
  	delete $.ajaxSettings.headers["X-CSRF-TOKEN"];
  	
  	return postResp;
}

function httpAsyncPostWithJsonRequestResponseToBluemix(postUrl, postData) {
	  
	return $.ajax({
		beforeSend: function(xhrObj){
		xhrObj.setRequestHeader("Content-Type","application/json");
		xhrObj.setRequestHeader("Accept","application/json");
		},
		type: "POST",
		url: postUrl,
		data: JSON.stringify(postData),
		dataType: "json",
		});
}

function httpAsyncPostWithJsonRequestResponseToBluemixSynchronous(postUrl, postData) {
	  
	return $.ajax({
		beforeSend: function(xhrObj){
		xhrObj.setRequestHeader("Content-Type","application/json");
		xhrObj.setRequestHeader("Accept","application/json");
		},
		type: "POST",
		url: postUrl,
		data: JSON.stringify(postData),
		dataType: "json",
		async: false
		});
}

