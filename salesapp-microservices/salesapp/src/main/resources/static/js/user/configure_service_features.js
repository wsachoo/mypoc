var guserServiceFeatures = {};

$(document).ready(function(){
	
	$("#accessSpeedConfigPlaceholder").on({
		"click" : function(e) {
			var eventSourceName = e.target.name;
			
			switch (eventSourceName) {
				
				case 'btnProceedToResults':
					handleProceedToResults($(this), e.target);
					break;
			}
		},
		
		"change" : function(e) {
			var eventSourceName = e.target.name;
			
			switch (eventSourceName) {
			case 'serviceConfig-serviceRequired':
				handleActionRequiredActionServiceAndFeatures($(this), e.target);
				break;
			case 'dataService' :
				handleDataService($(this), e.target);
				break;
			case 'securityService':
				handleSecurityService($(this), e.target);
				break;	
			case 'miscService':
				handleMiscService($(this), e.target);
				break;
			
			}
		}
		
	});
	
	$("#accessSpeedConfigPlaceholder").on('click', '#btnApplyServiceFeatureOptions', function(e) {
    	e.preventDefault();
    	
    	updateInMemoryServiceAndFeaturesFromFormObject($("#configureForm"));
    	var formData = JSON.stringify(guserServiceFeatures.getServiceAndFeaturesDataStore());
    	
    	var url = SALESEXPRESS_CONSTANTS.getUrlPath("postServiceFeaturesOptionsUrl");
    	var promise = httpAsyncPostWithJsonRequestResponse(url, formData);
    	
    	promise.done(function(data, textStatus, jqXHR ) {
    	/*	alert("success");*/
    		var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
			  "<a href='#' class='close' data-dismiss='alert' data-applybutton='success' aria-label='close'>&times;</a>" +
			  "<strong>Success!</strong> Service and Features data has been submitted successfully</div>";
    		$("#divSubmitAlert").html(successAlertMessage);
    		displayFeaturesAppliedInLeftNav();
    		
    	})
    	.fail(function(jqXHR, textStatus, errorThrown) {
    		var errorObject = $.parseJSON(jqXHR.responseText);
    		var failureAlertMessage = "<div class='alert alert-danger alert-dismissible'>" +
    		  						  "<a href='#' class='close' data-dismiss='alert' data-applybutton='failure' aria-label='close'>&times;</a>" +
    		  						  "<strong>Failure!</strong> The request failed with this error: " + errorObject.reasonPhrase + "</div>";  
    		$("#divSubmitAlert").html(failureAlertMessage);
    	});
    
    });
	
/*	*/
	
});


guserServiceFeatures = (function() {
	var serviceAndFeaturesStore = {
			"Services And Features" : {}
	};
	var serviceAndFeatures = {
			"Data Service" : { 
								"Performance" :{},
								"ManagedRouter" :{},
								"Service Diversity Options" : {},
							},
			"Miscellaneous Service" : 	{
											"Reporting" : {},
										},
			"Security Service" : {
								"Virtual Private Network" : {},
								"Additional Features" : {},
								},
	};
	
	return {
		addServiceAndFeaturesMetaInformation : function(key, value) {
			serviceAndFeaturesStore[key] = value;
		},
		
		//data service and features starts
		addDataServiceConfiguration : function(key, value) {
			serviceAndFeatures["Data Service"][key] = value;
		},
		addDataServicePerformanceConfiguration :function(key, value){
			serviceAndFeatures["Data Service"]["Performance"][key] = value;
			
		},
		addDataServiceManagedRouterConfiguration :function(key, value){
			serviceAndFeatures["Data Service"]["ManagedRouter"][key] = value;
			
		},
		addDataServiceDiversityOptionsConfiguration :function(key, value){
			serviceAndFeatures["Data Service"]["Service Diversity Options"][key] = value;
			
		},
		
		//data service and features ends
		//misc service and features starts
		addMiscServiceConfiguration : function(key, value) {
			serviceAndFeatures["Miscellaneous Service"][key] = value;
		},
		
		
		addMiscServiceMPLSReportingConfiguration : function(key, value){
			serviceAndFeatures["Miscellaneous Service"]["Reporting"][key] = value;
		},
		addMiscServiceManagedRouterReportingConfiguration : function(key, value){
			serviceAndFeatures["Miscellaneous Service"]["Reporting"][key] = value;
		},
		addMiscServiceEnhancedReportingConfiguration : function(key, value){
			serviceAndFeatures["Miscellaneous Service"]["Reporting"][key] = value;
		},
		addMiscServiceCOSReportingConfiguration : function(key, value){
			serviceAndFeatures["Miscellaneous Service"]["Reporting"][key] = value;
		},
		addMiscServiceFirewallReportingConfiguration : function(key, value){
			serviceAndFeatures["Miscellaneous Service"]["Reporting"][key] = value;
		},
		//misc service and features ends
		//security service and feature starts
		addSecurityServiceConfiguration : function(key, value) {
			serviceAndFeatures["Security Service"][key] = value;
		},
		
		addSecurityServiceVPNConfiguration : function(key, value) {
			serviceAndFeatures["Security Service"]["Virtual Private Network"][key] = value;
		},
		addSecurityServiceCBSConfiguration : function(key, value){
			serviceAndFeatures["Security Service"]["Additional Features"][key] = value;
		},
		addSecurityServiceFirewallConfiguration : function(key, value){
			serviceAndFeatures["Security Service"]["Additional Features"][key] = value;
		},
		addSecurityServiceWBSConfiguration : function(key, value){
			serviceAndFeatures["Security Service"]["Additional Features"][key] = value;
		},
		
		//security service and feature ends
		//new changes to put service and features config details inside sites
		addServiceFeaturesConfigToSite : function(siteName) {
			/*serviceAndFeaturesStore["Services And Features"] = {};*/
			serviceAndFeaturesStore["Services And Features"][siteName] = ($.extend(true, {}, serviceAndFeatures));
		},
		addToSiteConfiguration : function(key, value) {
			serviceAndFeatures[key] = value;
		},
		
		getServiceAndFeaturesData : function() {
			return serviceAndFeatures;
		},
		getServiceAndFeaturesDataStore : function() {
			return serviceAndFeaturesStore;
		},
		
		clearConfiguration : function() {
			serviceAndFeaturesStore = {
					"Services And Features" : {}
			};
		},
		clearSiteConfiguration : function(){
			serviceAndFeatures = {
					"Data Service" : { 
						"Performance" :{},
						"ManagedRouter" :{},
						"Service Diversity Options" : {},
					},
					"Miscellaneous Service" : {
						"Reporting" : {},
					},
					"Security Service" : {
						"Virtual Private Network" : {},
						"Additional Features" : {},
					},
			};
		}
	};
}());

function updateInMemoryServiceAndFeaturesFromFormObject(form) {
	var formData = form.serializeArray();
	
    $.each(formData, function() {
    	var nameArray = this.name;
    	
    	 if (nameArray === "miscService") {
    		/*guserServiceFeatures.addMiscServiceConfiguration(nameArray, this.value);*/
    	}
    	 else if(nameArray === "miscService_selectMPLSPort"){
    		 guserServiceFeatures.addMiscServiceMPLSReportingConfiguration("MPLS Port Reports", "Yes");
    	 }
    	 else if(nameArray === "miscService_selectManagedRouter"){
    		 guserServiceFeatures.addMiscServiceManagedRouterReportingConfiguration("Managed Router Reports", "Yes");
    	 }
    	 else if(nameArray === "miscService_selectEnhanced"){
    		 guserServiceFeatures.addMiscServiceEnhancedReportingConfiguration("Enhanced Reports", "Yes");
    	 }
    	 else if(nameArray === "miscService_selectCos"){
    		 guserServiceFeatures.addMiscServiceCOSReportingConfiguration("Class of Service Reports", "Yes");
    	 }
    	 else if(nameArray === "miscService_selectFirewall"){
    		 guserServiceFeatures.addMiscServiceFirewallReportingConfiguration("Firewall Reports", "Yes");
    	 }
    	 //misc service and features ends
    	//security service and feature starts 
    	/*else if (nameArray === "securityService") {
    		guserServiceFeatures.addSecurityServiceConfiguration(nameArray, this.value);
    	}*/
    	else if (nameArray === "securityService_selectVPN_required") {
    		guserServiceFeatures.addSecurityServiceVPNConfiguration("VPN Required", this.value);
    	}
    	else if (nameArray === "securityService_selectVPN_required") {
    		guserServiceFeatures.addSecurityServiceVPNConfiguration("VPN Required", this.value);
    	}
    	else if (nameArray === "securityService_af_cbs"){
    		guserServiceFeatures.addSecurityServiceCBSConfiguration("CloudBasedSecurity", "Yes");
    	}
    	else if(nameArray === "securityService_af_fw"){
    		guserServiceFeatures.addSecurityServiceFirewallConfiguration("FireWall", "Yes");
    	}
    	else if(nameArray === "securityService_af_wbs"){
    		guserServiceFeatures.addSecurityServiceWBSConfiguration("WireLessBasedSecurity", "Yes");
    	}
    	//security service and features ends 
    	//data service and features starts 
    	else if(nameArray === "dataService_performanceCos_type"){
    		
    		guserServiceFeatures.addDataServicePerformanceConfiguration("CosType", this.value);
    	}
    	else if(nameArray === "dataService_performanceCos_package"){
    		guserServiceFeatures.addDataServicePerformanceConfiguration("CosPackage", this.value);
    	}
    	else if(nameArray === "dataService_managedRouter_required"){
    		guserServiceFeatures.addDataServiceManagedRouterConfiguration("RouterOption", this.value);
    	}
    	else if(nameArray === "dataService_diversityOption_required"){
    		guserServiceFeatures.addDataServiceDiversityOptionsConfiguration("DiversityOption", this.value);
    	}
    	 
    	//data service and features ends 
    	 
    	else if ((nameArray === "solutionId") || (nameArray === "userId")||(nameArray === "transactionId")){
    		guserServiceFeatures.addServiceAndFeaturesMetaInformation(this.name, this.value);
    	}
    });
    
    var chkHeadequarters = $('input[name="chkHeadequarters"').is(":checked");
    var chkAccountReceivables = $('input[name="chkAccountReceivables"').is(":checked");
	var chkDistributionCenter = $('input[name="chkDistributionCenter"').is(":checked");
	
	if (chkHeadequarters) {
		guserServiceFeatures.addToSiteConfiguration("siteId", $('input[name="chkHeadequarters"').val());
		guserServiceFeatures.addServiceFeaturesConfigToSite("headQuarters");
}
	if (chkAccountReceivables) {
		guserServiceFeatures.addToSiteConfiguration("siteId", $('input[name="chkAccountReceivables"').val());
		guserServiceFeatures.addServiceFeaturesConfigToSite("accountReceivables");
	}    
	if (chkDistributionCenter) {
		guserServiceFeatures.addToSiteConfiguration("siteId", $('input[name="chkDistributionCenter"').val());
		guserServiceFeatures.addServiceFeaturesConfigToSite("distributionCenter");
	} 
}

function handleActionRequiredActionServiceAndFeatures($thisRef, eventSource) {
	var isServiceRequiredValue = $(eventSource).val();
	if ('true' === isServiceRequiredValue) {
		var servicesOffered = $.tmpl("service_features_template", siteMetaData);
		var lastDiv = findLastDivRowOfElement($thisRef);
		lastDiv.after(servicesOffered);
	} else {
		removeNextAllSiblingDivRows($(eventSource));
	}	
}

function removeNextAllSiblingDivRows($triggerElement) {
	var $closestDiv = $triggerElement.closest("div.row");
	$closestDiv.nextAll('div').not('.sachbottommenu').remove();
}

function findLastDivRowOfElement($thisRef) {
	return $thisRef.find("div.row:not('.sachbottommenu'):last");
}

function handleDataService($thisRef, eventSource){
	
	var isDataServiceRequiredValue = $(eventSource).val();
	
	if('data' == isDataServiceRequiredValue){
		
		if (($("#dataService").is(':checked'))) {
			var dataServiceOptions = $.tmpl("service_features_data", siteMetaData);
			var lastDiv = findLastDivRowOfElement($thisRef);
			$(dataServiceOptions).insertBefore('.sachbottommenu');
			$("#serviceFeaturesApplyBtnDiv").insertBefore('.sachbottommenu');
		}else{
			$thisRef.find("#div_dataService").remove();
		}
			
}
}

function handleSecurityService($thisRef, eventSource){
	if (($("#securityService").is(':checked'))) {
		var securityServiceOptions = $.tmpl("service_features_security", siteMetaData);
		var lastDiv = findLastDivRowOfElement($thisRef);
		$(securityServiceOptions).insertBefore('.sachbottommenu');
		$("#serviceFeaturesApplyBtnDiv").insertBefore('.sachbottommenu');
	}else{
		$("#div_securityService").remove();
	}
}

function handleMiscService($thisRef, eventSource){
	if(($("#miscService").is(':checked'))){
		var miscServiceOptions = $.tmpl("service_features_misc", siteMetaData);
		var lastDiv = findLastDivRowOfElement($thisRef);
		$(miscServiceOptions).insertBefore('.sachbottommenu');
		$("#serviceFeaturesApplyBtnDiv").insertBefore('.sachbottommenu');
	}else{
		$("#div_miscService").remove();
		
	}
}

function handleProceedToResults($thisRef, eventSource){
/*	var url = $("#resultsPage").data('url');
    location.replace(url); */
	performTabChangeAction("results");
	
	var resultData = httpGetWithJsonResponse(SALESEXPRESS_CONSTANTS.getUrlPath('resultsPageUrl'), {
		"portSpeed" : gUserConfiguration.getConfigurationData().portConfig.sliderPortSpeedValue,
		"accessSpeed" : gUserConfiguration.getConfigurationData().accessConfig.sliderSpeedValue
	});	
	
	displayAvailProdInLeftNav(resultData);
	
	var formElement = $("form");
	formElement.children('div').not('.sachtopmenu,.sachbottommenu').remove();
	var serviceFeaturesInit= $.tmpl("show_results_template", { "packageData" : resultData});
	var topmenudiv = formElement.find("div.sachtopmenu");
	topmenudiv.after(serviceFeaturesInit);
	formElement.trigger('create');    	
}

function displayFeaturesAppliedInLeftNav(){
	var glyphIconInfo = "<span class='badge'>i</span>";
	if ($('input[name="chkHeadequarters"').is(":checked")) {
		var hrefHqFeatures = $('#hqFeaturesAppliedId');
		hrefHqFeatures.css("font-weight", "bold");
		hrefHqFeatures.html("Features: " + "Applied" + "  "+glyphIconInfo);
	}
	if ($('input[name="chkAccountReceivables"').is(":checked")) {
		var hrefArFeatures = $('#arFeaturesAppliedId');
		hrefArFeatures.css("font-weight", "bold");
		hrefArFeatures.html("Features: " + "Applied");
		hrefArFeatures.html("Features: " + "Applied" + "  "+glyphIconInfo);
	}
	if ($('input[name="chkDistributionCenter"').is(":checked")) {
		var hrefDcFeatures = $('#dcFeaturesAppliedId');
		hrefDcFeatures.css("font-weight", "bold");
		hrefDcFeatures.html("Access: " + "Applied");
		hrefDcFeatures.html("Features: " + "Applied" + "  "+glyphIconInfo);
	}
	
}

function displayAvailProdInLeftNav(returnResultData){
		var hq_availableProducts= $("#hq_availableProducts");
		var ar_availableProducts = $("#ar_availableProducts");
		var dc_availableProducts = $("#dc_availableProducts");
		var chkHeadequarters = $('input[name="chkHeadequarters"').is(":checked");
		var chkAccountReceivables = $('input[name="chkAccountReceivables"').is(":checked");
		var chkDistributionCenter = $('input[name="chkDistributionCenter"').is(":checked");
		var thumbsUpIcon = "<span class='glyphicon glyphicon-thumbs-up' aria-hidden='true'></span>";
		
		if (chkHeadequarters) {
			hq_availableProducts.find("li").remove();
		}
		if(chkAccountReceivables){
			ar_availableProducts.find("li").remove();
		}
		if(chkDistributionCenter){
			dc_availableProducts.find("li").remove();
		}

	
	for(var i =0; i < returnResultData.length; i++){
		if(i ==0){
		if(chkHeadequarters){
			hq_availableProducts.css("font-weight", "bold");
			hq_availableProducts.append("<li>"+ returnResultData[i]["PRODUCT"] +"  "+thumbsUpIcon+"</li>");
		}
		if(chkAccountReceivables){
			ar_availableProducts.css("font-weight", "bold");
			ar_availableProducts.append("<li>"+ returnResultData[i]["PRODUCT"] +"  "+thumbsUpIcon+"</li>");
		}
		if(chkDistributionCenter){
			dc_availableProducts.css("font-weight", "bold");
			dc_availableProducts.append("<li>"+ returnResultData[i]["PRODUCT"] +"  "+thumbsUpIcon+"</li>");
		}	
	}
	else{
		if(chkHeadequarters){
			hq_availableProducts.css("font-weight", "bold");
			hq_availableProducts.append("<li>"+ returnResultData[i]["PRODUCT"] +"</li>");
		}
		if(chkAccountReceivables){
			ar_availableProducts.css("font-weight", "bold");
			ar_availableProducts.append("<li>"+ returnResultData[i]["PRODUCT"] +"</li>");
		}
		if(chkDistributionCenter){
			dc_availableProducts.css("font-weight", "bold");
			dc_availableProducts.append("<li>"+ returnResultData[i]["PRODUCT"] +"</li>");
		}
	}
	}
}
