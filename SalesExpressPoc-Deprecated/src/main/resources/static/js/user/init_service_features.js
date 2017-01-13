var guserServiceFeatures = {};

$(document).ready(function(){
	
	$("#serviceAndFeaturesForm").on({
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
				handleActionRequiredAction($(this), e.target);
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
	
	$("#serviceAndFeaturesForm").on('click', '#btnApplyServiceFeatureOptions', function(e) {
    	e.preventDefault();
    	
    	updateInMemoryServiceAndFeaturesFromFormObject($("#serviceAndFeaturesForm"));
    	var formData = JSON.stringify(guserServiceFeatures.getServiceAndFeaturesData());
    	
    	var url = SALESEXPRESS_CONSTANTS.getUrlPath("postServiceFeaturesOptionsUrl");
    	var promise = postServiceFeaturesData(url, formData);
    	
    	promise.done(function(data, textStatus, jqXHR ) {
    		alert("success");
    		$("#btnApplyServiceFeatureOptions").attr("disabled", true);
    		$("#btnProceedToResults").removeAttr("disabled");
    		
    	})
    	.fail(function(jqXHR, textStatus, errorThrown) {
    		alert("failure");
    	});
    
    });
	
/*	*/
	
});



guserServiceFeatures = (function() {
	var serviceAndFeatures = {
			"data_Service" : { 
								"Performance" :{},
								"ManagedRouter" :{},
								"Service Diversity Options" : {},
							},
			"misc_Service" : {
								"Reporting" : {}
							},
			"security_Service" : {
								"Virtual Private Network" : {},
								},
	};
	
	return {
		addServiceAndFeaturesMetaInformation : function(key, value) {
			serviceAndFeatures[key] = value;
		},
		
		//data service and features starts
		addDataServiceConfiguration : function(key, value) {
			serviceAndFeatures["data_Service"][key] = value;
		},
		addDataServicePerformanceConfiguration :function(key, value){
			serviceAndFeatures["data_Service"]["Performance"][key] = value;
			
		},
		addDataServiceManagedRouterConfiguration :function(key, value){
			serviceAndFeatures["data_Service"]["ManagedRouter"][key] = value;
			
		},
		addDataServiceDiversityOptionsConfiguration :function(key, value){
			serviceAndFeatures["data_Service"]["Service Diversity Options"][key] = value;
			
		},
		
		//data service and features ends
		addMiscServiceConfiguration : function(key, value) {
			serviceAndFeatures["misc_Service"][key] = value;
		},
		
		
		addMiscServiceReportingConfiguration : function(key, value){
			serviceAndFeatures["misc_Service"]["Reporting"][key] = value;
		},
		
		//security service and feature starts
		addSecurityServiceConfiguration : function(key, value) {
			serviceAndFeatures["security_Service"][key] = value;
		},
		
		addSecurityServiceVPNConfiguration : function(key, value) {
			serviceAndFeatures["security_Service"]["Virtual Private Network"][key] = value;
		},
		
		//security service and feature ends
		getServiceAndFeaturesData : function() {
			return serviceAndFeatures;
		},
		
		clearConfiguration : function() {
			serviceAndFeatures = {
					"data_Service" : { 
						"Performance" :{},
						"ManagedRouter" :{},
						"Service Diversity Options" : {},
					},
					"misc_Service" : {
						"Reporting" : {}
					},
					"security_Service" : {
						"Virtual Private Network" : {},
					},
			};
		}
	};
}());

function updateInMemoryServiceAndFeaturesFromFormObject(form) {
	var formData = form.serializeArray();
	
    $.each(formData, function() {
    	//alert(this.name);
    	var nameArray = this.name;
    	/*
    	if (nameArray.length == 1) {
    		guserServiceFeatures.addServiceAndFeaturesMetaInformation(this.name, this.value);
    	}*/
    	/*else if (nameArray[0] === "dataService") {
    		guserServiceFeatures.addDataServiceConfiguration(nameArray[1], this.value);
    	}*/
    	 if (nameArray === "miscService") {
    		guserServiceFeatures.addMiscServiceConfiguration(nameArray, this.value);
    	}
    	 else if(nameArray === "miscService_selectMPLSPort"){
    		 guserServiceFeatures.addMiscServiceReportingConfiguration("Reporting Type", this.value);
    	 }
    	//security service and feature starts 
    	else if (nameArray === "securityService") {
    		guserServiceFeatures.addSecurityServiceConfiguration(nameArray, this.value);
    	}
    	else if (nameArray === "securityService_selectVPN_required") {
    		guserServiceFeatures.addSecurityServiceVPNConfiguration(nameArray, this.value);
    	}
    	else if (nameArray === "securityService_selectVPN_required") {
    		guserServiceFeatures.addSecurityServiceVPNConfiguration(nameArray, this.value);
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
    	 
    	else {
    		guserServiceFeatures.addServiceAndFeaturesMetaInformation(this.name, this.value);
    	}
    });
}

function handleActionRequiredAction($thisRef, eventSource) {
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
			$thisRef.find('.div_dataService_tmpl').remove();
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
		$("#serviceAndFeaturesForm .div_securityService_tmpl").remove();
	}
}

function handleMiscService($thisRef, eventSource){
	if(($("#miscService").is(':checked'))){
		var miscServiceOptions = $.tmpl("service_features_misc", siteMetaData);
		var lastDiv = findLastDivRowOfElement($thisRef);
		$(miscServiceOptions).insertBefore('.sachbottommenu');
		$("#serviceFeaturesApplyBtnDiv").insertBefore('.sachbottommenu');
	}else{
		$("#serviceAndFeaturesForm .div_miscService_tmpl").remove();
	}
}

function handleProceedToResults($thisRef, eventSource){
	var url = $("#resultsPage").data('url');
    location.replace(url); 
}

function postServiceFeaturesData(postUrl, postData){
	
	return $.ajax({
		url: postUrl,
		data: postData,
		type: 'POST',
		async: 'true',
		dataType: 'json',
        contentType : "application/json"
    });	
}


