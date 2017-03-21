
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
				case 'btnRefreshServicesAndFeatures':
					refreshServicesAndFeatures($(this));
					break;
				case 'btnRefreshResults':
					//calling the same function as of proceed to results to show the 
					//updated results when product is added from admin panel
					handleProceedToResults($(this), e.target);
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
				if(eventSourceName.includes("-"))
					handleCommonServiceFeatures($(this), e.target);
				else
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
				  /*"<strong>Success!</strong> Please configure remaining sites to proceed further</div>";*/
				  "<strong>Success!</strong> Request has been submitted successfully.</div>";
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
	

	var chkNames = [];

	form.find(':checkbox').each(function(i) {	
        chkNames[i] = ($(this).attr('name'));
	});

	var tempArr = {};
	var checkBoxElements = $.unique(chkNames);
	checkBoxElements.forEach(function(v) {

		var temp = "input[name='" + v + "']";

		var arr = [];
		$(temp).filter(':checked').each(function(i) {
			arr[i] = ($(this).val());
		});
		tempArr[v] = arr.join();
	});
	
	formData.forEach(function(ob) {
		if (tempArr[ob.name]) {
			ob.value = tempArr[ob.name];
		}
	});
	
	var newObj = null;
	$.each(formData, function(index, formLevelValue) {
		var keyArr = formLevelValue.name.split('-'); // change to id
		if (keyArr.length > 1) {
			newObj = tempServiceAndFeatures;
			$.each((keyArr), function(keyArrIndex, keyArrValue) {

				if (keyArrIndex != keyArr.length - 1) {
					if (!newObj[keyArrValue]) {
						newObj[keyArrValue] = {};
					}
					newObj = newObj[keyArrValue];
				} else {
					newObj[keyArrValue] = formLevelValue;
				}
			});
		}
	});
	
	 $("#salesexpress-side-bar").find(":checkbox").each(function() {
	    	if ( $(this).is(':checked') ) {
	    		var siteId = $(this).val();
	    		var siteName =  $(this).data('name');
	    		guserServiceFeatures.addServiceFeaturesConfigToSite("siteId", siteId);
	    		guserServiceFeatures.addToSiteConfiguration(siteName);
	    		/*guserServiceFeatures.addServiceFeaturesConfigToSite(siteId);*/ 
	    	
	    	}
	    });
	}
    

function handleActionRequiredActionServiceAndFeatures($thisRef, eventSource) {
	var isServiceRequiredValue = $(eventSource).val();
	if ('true' === isServiceRequiredValue) {
		var serviceOptions = serviceFeaturesMetaData.serviceAndFeatures;
		var servicesOffered = $.tmpl("service_features_template", {"serviceOptionsKeys" : serviceOptions});
		var lastDiv = findLastDivRowOfElement($thisRef);
		lastDiv.after(servicesOffered);
	} else {
		removeNextAllSiblingDivRows($(eventSource));
	}	
}

function refreshServicesAndFeatures($thisRef) {
	//performTabChangeAction("serviceAndFeatures");
	handleProceedToFeatures(); // added to test being on the same page
    var servFeaturesMDataUrl = SALESEXPRESS_CONSTANTS.getUrlPath('getServiceFeaturesMetaDataUrl');
    serviceFeaturesMetaData = httpGetWithJsonResponse(servFeaturesMDataUrl);
	
	$("input[name='serviceConfigServiceRequired']").prop('checked', true)
	var serviceOptions = serviceFeaturesMetaData.serviceAndFeatures;
	var servicesOffered = $.tmpl("service_features_template", {"serviceOptionsKeys" : serviceOptions});
	var lastDiv = findLastDivRowOfElement($thisRef);
	lastDiv.after(servicesOffered);	
}

function removeNextAllSiblingDivRows($triggerElement) {
	var $closestDiv = $triggerElement.closest("div.row");
	$closestDiv.nextAll('div').not('.sachbottommenu, .chat-box').remove();
}

function findLastDivRowOfElement($thisRef) {
	return $thisRef.find("div.row:not('.sachbottommenu,.chat-box, #chat_window_1, .msg_container'):last");
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
	formElement.children('div').not('.sachtopmenu,.sachbottommenu,.chat-box').remove();
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
				 ulElemProductList.append("<li>" + productArrayPerSite[i].PRODUCT + thumbsUpIcon+ "</li>");					 
			 }
			 else {
				 ulElemProductList.css("font-weight", "bold");
				 ulElemProductList.append("<li>" + productArrayPerSite[i].PRODUCT + "</li>");					 
			 }
		 }
	 });
}

function isAllSitesConfigCompleteForServiceFeatures(){
	var numberOfSitesToConfigure = gUserDetails.siteAddresses.length;
	var numberOfSitesConfigured = Object.keys(serviceAndFeatures.sites).length;
	return (numberOfSitesToConfigure == numberOfSitesConfigured);
}


function handleCommonService($thisRef, eventSource){
	var objectToServiceFeaturesTemplate;
	var isServiceRequiredValue = $(eventSource).val();
	var divServiceRequired = $(eventSource).attr('id');
	if (($(eventSource).is(':checked'))) {
		for(var i=0; i < serviceFeaturesMetaData["serviceAndFeatures"].length; i++){
			if(serviceFeaturesMetaData.serviceAndFeatures[i]["displayValue"] == isServiceRequiredValue){
				objectToServiceFeaturesTemplate = serviceFeaturesMetaData.serviceAndFeatures[i]["children"];
			}
		}
		//var objectToServiceFeaturesTemplate = serviceFeaturesMetaData.serviceAndFeatures[isServiceRequiredValue];
		var securityServiceOptions = $.tmpl("common_services_features_template", {"objectToServiceFeaturesTemplate":objectToServiceFeaturesTemplate});
		var lastDiv = findLastDivRowOfElement($thisRef);
		$(securityServiceOptions).insertBefore('.sachbottommenu');
		$("#serviceFeaturesApplyBtnDiv").insertBefore('.sachbottommenu');
	}else{
		$("#div_FeatureService_"+divServiceRequired).remove();
	}
}

function handleProceedToGenerateContract($thisRef, eventSource) {

    $("#sachtopmenu_serviceFeatures").removeClass('col-sm-3 col-xs-12 sachmenuitemactive').addClass('col-sm-2 col-xs-12 sachmenuitem'); //changes span of existing tabs
    $("#sachtopmenu_results").removeClass('col-sm-3 col-xs-12 sachmenuitemactive').addClass('col-sm-2 col-xs-12 sachmenuitem'); //changes span of existing tabs

    $("#sachtopmenu_generateContract").css("display", "inline");
    $("#sachtopmenu_generateContract").addClass('col-sm-2 col-xs-12 sachmenuitemactive');

    var formElement = $("form");
    formElement.children('div').not('.sachtopmenu,.sachbottommenu,.chat-box, #chat_window_1, .msg_container').remove();
    var generateContractInit = $.tmpl("generate_contract_template");
    var topmenudiv = formElement.find("div.sachtopmenu");
    topmenudiv.after(generateContractInit);
    formElement.trigger('create');
}
