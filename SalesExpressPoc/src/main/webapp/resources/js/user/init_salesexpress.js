/*
 * Global variables declaration
 */
var SALESEXPRESS_CONSTANTS;
var siteMetaData;
var gUserDetails = {}; //Object to store user information


SALESEXPRESS_CONSTANTS = (function() {
	var _jQueryTemplates = {
			"filter_access_type_template" : "/templates/filter_access_type_template.html",
			"access_config_options_template" : "/templates/access_config_options_template.html",
			"select_speed_configure_options" : "/templates/select_speed_configure_options.html"
	};
	
	var _jsonDataUrls = {
		"testSiteJsonDataUrl" : "/getMetaData/testSite",
		"accessTypePostUrl" : "/sendAccessTypeData"
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
