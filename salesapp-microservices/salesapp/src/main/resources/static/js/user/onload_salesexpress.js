$(document).ready(function() {
	loadUserDetail();
	
	//Load templates and site metadata only if it is not login page. (this may change in future)
    var pageLoaded = $(location).attr('href');
    if (pageLoaded.indexOf("/login/") == -1) {
    	loadJQueryTemplates();
    	loadSiteMetaData();
    }
});

function loadJQueryTemplates() {
	//This is a global variable map with key as template identifier and value as script of the jQuery template.
	var jQueryTemplatesIdScriptMap = {};
	
	var jQueryTemplates = SALESEXPRESS_CONSTANTS.getJQueryTemplates();
	
	for (var templateId in jQueryTemplates) {
		jQueryTemplatesIdScriptMap[templateId] = getTemplateDefinition(jQueryTemplates[templateId]);
		$.template(templateId, jQueryTemplatesIdScriptMap[templateId]);
	}	
}

function loadSiteMetaData() {
	var siteUrl = SALESEXPRESS_CONSTANTS.getUrlPath('testSiteJsonDataUrl')
	siteMetaData = httpGetWithJsonResponse(siteUrl);
	
	attachAccessSpeedsToSiteMetaData();
}

function attachAccessSpeedsToSiteMetaData() {
	var getAllAccessSpeedsUrl = SALESEXPRESS_CONSTANTS.getUrlPath('getAllAccessSpeedsUrl')
	var allAccessSpeedsByPortTypeJson = httpGetWithJsonResponse(getAllAccessSpeedsUrl);
	
	$.each(allAccessSpeedsByPortTypeJson, function(k, v) {
		siteMetaData.accessSpeeds[k].range = v;
	});	
}

function loadUserDetail() {
	try {
		gUserDetails = $.parseJSON($("#userDetail").html());
		
		if (typeof gUserDetails === 'undefined') {
			gUserDetails = JSON.parse($("#userDetail").html());
		}
	} 
	catch (ex) {
	}
}