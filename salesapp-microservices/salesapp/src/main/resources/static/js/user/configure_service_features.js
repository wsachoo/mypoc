
var tempServiceAndFeatures = {};
	var serviceAndFeatures = {
			"sites" : {}
	};
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
			case 'serviceConfigServiceRequired':
				handleActionRequiredActionServiceAndFeatures($(this), e.target);
				break;
			
			default :
				handleCommonService($(this), e.target);
				break;
			}
		}
		
	});
	
	$("#accessSpeedConfigPlaceholder").on('click', '#btnApplyServiceFeatureOptions', function(e) {
    	e.preventDefault();
    	
    	updateInMemoryServiceAndFeaturesFromFormObject($("#configureForm"));
    	var formData = JSON.stringify(serviceAndFeatures);
    	//var formData = JSON.stringify(guserServiceFeatures.getServiceFeaturesConfigurationData);
    	console.log("formData : ", formData);
    	var url = SALESEXPRESS_CONSTANTS.getUrlPath("postServiceFeaturesOptionsUrl");
    	var promise = httpAsyncPostWithJsonRequestResponse(url, formData);
    	
    	promise.done(function(data, textStatus, jqXHR ) {
    		if(isAllSitesConfigCompleteForServiceFeatures()){
	    	//user configured all the sites
    			var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
				  "<a href='#' class='close' data-dismiss='alert' data-applybutton='success' aria-label='close'>&times;</a>" +
				  "<strong>Success!</strong> Service and Features data has been submitted successfully</div>";
    			$("#divSubmitAlert").html(successAlertMessage);
    		}
    		else{
    			//user should configure remaining sites
	    		var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
				  "<a href='#' class='close' data-dismiss='alert alert-warning' data-applybutton='success' aria-label='close'>&times;</a>" +
				  "<strong>Success!</strong> Please configure remaining sites to proceed further</div>";
	    		$("#divSubmitAlert").html(successAlertMessage);
    		}
    		displayFeaturesAppliedInLeftNav();
    	})
    	.fail(function(jqXHR, textStatus, errorThrown) {
    		var errorObject = $.parseJSON(jqXHR.responseText);
    		var failureAlertMessage = "<div class='alert alert-danger alert-dismissible'>" +
    		  						  "<a href='#' class='close' data-dismiss='alert alert-danger' data-applybutton='failure' aria-label='close'>&times;</a>" +
    		  						  "<strong>Failure!</strong> The request failed with this error: " + errorObject.reasonPhrase + "</div>";  
    		$("#divSubmitAlert").html(failureAlertMessage);
    	});
    
    });
	
/*	*/
	
});


guserServiceFeatures = (function() {
	
	/*var tempServiceAndFeatures = {};
	var serviceAndFeatures = {
			"sites" : {}
	};*/
	return {
		addToSiteConfiguration : function(siteName) {
			//userConfigStore[siteType] = $.extend(true, {}, tempConfigStore);
			serviceAndFeatures["sites"][siteName] = ($.extend(true, {}, tempServiceAndFeatures));
		},
		
		addServiceFeaturesConfigToSite : function(key, value) {
			tempServiceAndFeatures[key] = value;
		},
		
		
		getServiceFeaturesData : function() {
			return tempServiceAndFeatures;
		},
		
		getServiceFeaturesConfigurationData : function() {
			return serviceAndFeatures;
		},
		
		clearSiteConfiguration : function() {
			serviceAndFeatures = {
					"sites" : {}
			};
		},
		clearServiceFeaturesConfiguration : function(){
			tempServiceAndFeatures = {}; //to empty the current object while clubbing
		}
	};
}());

function updateInMemoryServiceAndFeaturesFromFormObject(form) {
	var formData = form.serializeArray();
	
	$.each(formData, function(k, vx) {
	    var keyArr = vx.name.split('-');
	    if(keyArr.length > 1){
	    var newObj = tempServiceAndFeatures;
	    $.each((keyArr), function(i, v) {
	    	
	      if (i != keyArr.length - 1) { 
	        if (!newObj[v]) {
	          newObj[v] = {};
	        }
	        newObj = newObj[v];
	      } else {
	        newObj[v] = vx;
	      }
	    });
	}
	  });
	
	var chkHeadequarters = $('input[name="chkHeadequarters"').is(":checked");
    var chkAccountReceivables = $('input[name="chkAccountReceivables"').is(":checked");
	var chkDistributionCenter = $('input[name="chkDistributionCenter"').is(":checked");
	
	if (chkHeadequarters) {
		guserServiceFeatures.addServiceFeaturesConfigToSite("siteId", $('input[name="chkHeadequarters"').val());
		guserServiceFeatures.addToSiteConfiguration("headQuarters");
	}
	if (chkAccountReceivables) {
		guserServiceFeatures.addServiceFeaturesConfigToSite("siteId", $('input[name="chkAccountReceivables"').val());
		guserServiceFeatures.addToSiteConfiguration("accountReceivables");
	}    
	if (chkDistributionCenter) {
		guserServiceFeatures.addServiceFeaturesConfigToSite("siteId", $('input[name="chkDistributionCenter"').val());
		guserServiceFeatures.addToSiteConfiguration("distributionCenter");
	} 
	
	
	}

function handleActionRequiredActionServiceAndFeatures($thisRef, eventSource) {
	var isServiceRequiredValue = $(eventSource).val();
	if ('true' === isServiceRequiredValue) {
		var serviceOptions = Object.keys(serviceFeaturesMetaData.serviceAndFeatures);
		console.log(serviceOptions);
		var servicesOffered = $.tmpl("service_features_template", {"serviceOptionsKeys" : serviceOptions});
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

function handleProceedToGenerateContract($thisRef, eventSource) {
	
	$("#sachtopmenu_serviceFeatures").removeClass('col-sm-3 col-xs-12 sachmenuitemactive').addClass('col-sm-2 col-xs-12 sachmenuitem');//changes span of existing tabs
	$("#sachtopmenu_results").removeClass('col-sm-3 col-xs-12 sachmenuitemactive').addClass('col-sm-2 col-xs-12 sachmenuitem');//changes span of existing tabs
	
	$("#sachtopmenu_generateContract").css("display","inline");
	$("#sachtopmenu_generateContract").addClass('col-sm-2 col-xs-12 sachmenuitemactive');
	
	var formElement = $("form");
	formElement.children('div').not('.sachtopmenu,.sachbottommenu').remove();
	var generateContractInit= $.tmpl("generate_contract_template");
	var topmenudiv = formElement.find("div.sachtopmenu");
	topmenudiv.after(generateContractInit);
	formElement.trigger('create');    	
}

function isAllSitesConfigCompleteForServiceFeatures(){
	var numberOfSitesToConfigure = Object.keys(gSiteIdNameMapping).length;
	var numberOfSitesConfigured = Object.keys(serviceAndFeatures.sites).length;
	return (numberOfSitesToConfigure == numberOfSitesConfigured);
}


function handleCommonService($thisRef, eventSource){
	var isServiceRequiredValue = $(eventSource).val();
	if (($(eventSource).is(':checked'))) {
		var objectToServiceFeaturesTemplate = serviceFeaturesMetaData.serviceAndFeatures[isServiceRequiredValue];
		var securityServiceOptions = $.tmpl("common_services_features_template", {"objectToServiceFeaturesTemplate":objectToServiceFeaturesTemplate});
		var lastDiv = findLastDivRowOfElement($thisRef);
		$(securityServiceOptions).insertBefore('.sachbottommenu');
		$("#serviceFeaturesApplyBtnDiv").insertBefore('.sachbottommenu');
	}else{
		$("#div_FeatureService_"+isServiceRequiredValue).remove();
	}
}



















