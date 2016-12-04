$(document).ready(function() {
	loadJQueryTemplates();
	loadAvpnSiteJsonData();
	loadUserDetail();
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

function loadAvpnSiteJsonData() {
	var avpnUrl = SALESEXPRESS_CONSTANTS.getUrlPath('testSiteJsonDataUrl')
	siteMetaData = httpGetWithJsonResponse(avpnUrl);	
}

function loadUserDetail() {
	try {
		gUserDetails = $.parseJSON($("#userDetail").html());
		
		if (typeof gUserDetails === 'undefined') {
			gUserDetails = JSON.parse($("#userDetail").html());
		}
	} catch (ex) {
	}
}