var guserServiceFeatures = {};

$(document).ready(function(){
	
	$("#accessSpeedConfigPlaceholder").on({
		"click" : function(e) {
			var eventSourceName = e.target.name;
			
			switch (eventSourceName) {
				
				case 'btnProceedToResults':
					handleProceedToResults($(this), e.target);
					break;
					
				case 'btnProceedToContractGeneration':
					handleProceedToGenerateContract($(this), e.target);
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
    
    $("#salesexpress-side-bar").find(":checkbox").each(function() {
    	if ( $(this).is(':checked') ) {
    		var siteId = $(this).val();
    		var siteName =  $(this).data('name');
    		guserServiceFeatures.addToSiteConfiguration("siteId", siteId);
    		guserServiceFeatures.addToSiteConfiguration("siteName", siteName);
    		guserServiceFeatures.addServiceFeaturesConfigToSite(siteId);    		
    	}
    });
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
	
	var requestParam = {
			//"portSpeed" : gUserConfiguration.getConfigurationData().portConfig.sliderPortSpeedValue,
			//"accessSpeed" : gUserConfiguration.getConfigurationData().accessConfig.sliderSpeedValue
			
			"accessSpeed" : JSON.stringify($.map(gUserConfiguration.getUserConfigurationData().sites, function(v, k) {
			    return v.accessConfig.sliderSpeedValue;
			})),
			"portSpeed" : JSON.stringify($.map(gUserConfiguration.getUserConfigurationData().sites, function(v, k) {
			    return v.portConfig.sliderPortSpeedValue;
			}))			
	};
	
	var resultData = httpGetWithJsonResponse(SALESEXPRESS_CONSTANTS.getUrlPath('resultsPageUrl'), requestParam);
	
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
	
    $("#salesexpress-side-bar").find(":checkbox").each(function() {
    	if ( $(this).is(':checked') ) {
    		var siteId = $(this).val();
    		var siteName =  $(this).data('name');
    		var hrefHqFeatures = $('#feature_tag_id_' + siteId);
    		hrefHqFeatures.css("font-weight", "bold");
    		hrefHqFeatures.html("Features: " + "Applied" + "  " + glyphIconInfo);
    	}
    });
}

function displayAvailProdInLeftNav(returnResultData) {
	var thumbsUpIcon = "<span class='glyphicon glyphicon-thumbs-up' aria-hidden='true'></span>";
	var productsBySiteId = {};

	$.each(gUserDetails.siteAddresses, function(i, value) {
		var productsFound = 
			$.grep(returnResultData, function(site, ind) {
	  		    var productExists = site['SITE_IDS'].split(",").indexOf("" + value.SITE_ID) != -1;
	            return productExists;
		});
		productsBySiteId[value.SITE_ID] = productsFound;
	});
	
	 $.each(productsBySiteId, function(k, productArrayPerSite) {
		 
		 var ulElemProductList = $("ul").find("[data-menu_site_id='" + k + "']");
		 ulElemProductList.find("li").remove();
		 
		 for(var i=0; i < productArrayPerSite.length; i++){
			 if (i == 0) {
				 ulElemProductList.css("font-weight", "bold");
				 ulElemProductList.append("<li>" + productArrayPerSite[i].PRODUCT + productArrayPerSite[i].MRC + thumbsUpIcon+ "</li>");					 
			 }
			 else {
				 ulElemProductList.css("font-weight", "bold");
				 ulElemProductList.append("<li>" + productArrayPerSite[i].PRODUCT + productArrayPerSite[i].MRC  + "</li>");					 
			 }
		 }
	 });
}


function handleProceedToGenerateContract($thisRef, eventSource) {

    $("#sachtopmenu_serviceFeatures").removeClass('col-sm-3 col-xs-12 sachmenuitemactive').addClass('col-sm-2 col-xs-12 sachmenuitem'); //changes span of existing tabs
    $("#sachtopmenu_results").removeClass('col-sm-3 col-xs-12 sachmenuitemactive').addClass('col-sm-2 col-xs-12 sachmenuitem'); //changes span of existing tabs

    $("#sachtopmenu_generateContract").css("display", "inline");
    $("#sachtopmenu_generateContract").addClass('col-sm-2 col-xs-12 sachmenuitemactive');

    var formElement = $("form");
    formElement.children('div').not('.sachtopmenu,.sachbottommenu').remove();
    var generateContractInit = $.tmpl("generate_contract_template");
    var topmenudiv = formElement.find("div.sachtopmenu");
    topmenudiv.after(generateContractInit);
    formElement.trigger('create');
}