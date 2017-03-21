$(document).ready(function() {
	$("#configureForm").on({
		"click" : function(e) {
			var eventSourceName = e.target.name;
			
			switch (eventSourceName) {
				case 'btnApplyAccessConfigurationOptions':
					handleBtnApplyAccessConfigurationOptionsClick($(this), e.target);
					break;
				case 'btnApplyPortConfigurationOptions':
					handleBtnApplyPortConfigurationOptionsClick($(this), e.target);
				
			}
		}
	});
/*	
	$("#configureForm").on("click", ".alert-success", function(e) {
		var result = $(e.target).data("applybutton");
	});*/
});

function handleBtnApplyPortConfigurationOptionsClick($thisRef, eventSource) {
	updateInMemoryConfigurationFromFormObject($("#configureForm"));

	if (gUserConfiguration.getConfigurationData().accessConfig.radiofilteredAccessTypes) {
		gUserConfiguration.getConfigurationData().accessConfig.selectAccessType = gUserConfiguration.getConfigurationData().accessConfig.radiofilteredAccessTypes;
	}
	
	var formData = JSON.stringify(gUserConfiguration.getUserConfigurationData());
	
	var url = SALESEXPRESS_CONSTANTS.getUrlPath("siteConfigurationPostUrl");
	var promise = httpAsyncPostWithJsonRequestResponse(url, formData);
	
	promise.done(function(data, textStatus, jqXHR) {
		handlePortConfigurationSuccess(data, textStatus, jqXHR);
		$("#btnProceedToFeatures").css('display','inline');
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		var errorObject = $.parseJSON(jqXHR.responseText);
		var failureAlertMessage = "<div class='alert alert-danger alert-dismissible'>" +
		  						  "<a href='#' class='close' data-dismiss='alert' data-applybutton='failure' aria-label='close'>&times;</a>" +
		  						  "<strong>Failure!</strong> The request failed with this error: " + errorObject.reasonPhrase + "</div>";  
		$("#divPortConfigClickApplyMessage").html(failureAlertMessage);
	});	
	
}

function handlePortConfigurationSuccess(data, textStatus, jqXHR)
{
	displayPostSpeedSelectionInLeftNavigation();
	if (isAllSitesConfigurationCompleted()) {
		var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
		  "<a href='#' class='close' data-dismiss='alert' data-applybutton='success' aria-label='close'>&times;</a>" +
		  "<strong>Success!</strong> The request has been submitted successfully</div>";
		$("#btnProceedToFeatures").css("display", "inline");
		$("#divPortConfigClickApplyMessage").html(successAlertMessage);
	}
	else {
		var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
		  "<a href='#' class='close' data-dismiss='alert' data-applybutton='success' aria-label='close'>&times;</a>" +
		  /*"<strong>Success!</strong> Please configure remaining sites to proceed further</div>";*/
		  "<strong>Success!</strong> The request has been submitted successfully.</div>";
		$("#divPortConfigClickApplyMessage").html(successAlertMessage);		
	}	
}

function handleBtnApplyAccessConfigurationOptionsClick($thisRef, eventSource) {
	updateInMemoryConfigurationFromFormObject($("#configureForm"));
	
	if (gUserConfiguration.getConfigurationData().accessConfig.radiofilteredAccessTypes) {
		gUserConfiguration.getConfigurationData().accessConfig.selectAccessType = gUserConfiguration.getConfigurationData().accessConfig.radiofilteredAccessTypes;
	}
	
	gUserConfiguration.addConfigMetaInformation("iglooCallRequired", "Y"); //Add parameter iglooCallRequired to indicate that IGLOO call is needed on server side
	var formData = JSON.stringify(gUserConfiguration.getUserConfigurationData());
	
	var url = SALESEXPRESS_CONSTANTS.getUrlPath("siteConfigurationPostUrl");
	var promise = httpAsyncPostWithJsonRequestResponse(url, formData);
	
	var textBeforeLoading = $(eventSource).text();
	$(eventSource).html("<b>Processing....</b>")
	
	promise.done(function(data, textStatus, jqXHR ) {
		$(eventSource).text(textBeforeLoading);
		var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
								  "<a href='#' class='close' data-dismiss='alert' data-applybutton='success' aria-label='close'>&times;</a>" +
								  "<strong>Success!</strong> The request has been submitted successfully</div>";
		configureModifyUserOptionsAfterAccessApplySuccess();
		$("#divAccessTypeclickApplyMessage").html(successAlertMessage);
		$("#transactionId").val(data.transactionId);
		
		displayAccessSelectionInLeftNavigation();
		updateFooterMessage("Access configuration options have been defaulted for the sites selected. Access options are too varied across sites to be configured together.");
		fetchPortSpeedsForSelectedAccessSpeed();
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		$(eventSource).text(textBeforeLoading);
		//var errorObject = $.parseJSON(jqXHR.responseText);
		var errorObject = jqXHR.responseJSON;
		
		var failureAlertMessage = "<div class='alert alert-danger alert-dismissible'>" +
		  						  "<a href='#' class='close' data-dismiss='alert' data-applybutton='failure' aria-label='close'>&times;</a>" +
		  						  "<strong>Failure!</strong> The request failed with this error: " + errorObject.reasonPhrase + "</div>";  
		$("#divAccessTypeclickApplyMessage").html(failureAlertMessage);
		console.log(errorObject.stackTrace);
	});
	
	gUserConfiguration.addConfigMetaInformation("iglooCallRequired", "N"); //Remove parameter iglooCallRequired after IGLOO call is done.
}

function fetchPortSpeedsForSelectedAccessSpeed() {
	var urlPortSpeedsByAccessSpeed = SALESEXPRESS_CONSTANTS.getUrlPath("postSpeedsBySelectedAccessSpeedUrl");
	
	var portSpeedsJson = httpGetWithJsonResponse(urlPortSpeedsByAccessSpeed, {
		"accessType" : gUserConfiguration.getConfigurationData().accessConfig.selectAccessType,
		"accessSpeed" : gUserConfiguration.getConfigurationData().accessConfig.sliderSpeedValue
	});
	
	//Replace siteMetaData json portSpeed range with this fetched values
	//This is temporary fix
	siteMetaData.portSpeeds[gUserConfiguration.getConfigurationData().accessConfig.selectAccessType].range = portSpeedsJson;
}

function configureModifyUserOptionsAfterAccessApplySuccess() {
	var $divAccessConfigOptions = $("#divAccessConfigOptions");
	$divAccessConfigOptions.nextAll('div').not('.sachbottommenu, .chat-box').remove();
	$divAccessConfigOptions.remove();			
	$('#divOnBtnCustomize').remove();
	
	var accessConfigOptions = $.tmpl("modify_configuration_options", siteMetaData);
	lastDiv = findLastDivRowOfElement($("#accessSpeedConfigPlaceholder"));
	lastDiv.after(accessConfigOptions);
}

function displayAccessSelectionInLeftNavigation() {
	var accessType = gUserConfiguration.getConfigurationData().accessConfig.radiofilteredAccessTypes || gUserConfiguration.getConfigurationData().accessConfig.selectAccessType;
	var sliderSpeedValue = gUserConfiguration.getConfigurationData().accessConfig.sliderSpeedValue;

	$.each(siteMetaData.accessType, function(i, obj) {
	    if (obj.key == accessType) {
	    	accessType = obj.value;
	    }
	});
	
    $("#salesexpress-side-bar").find(":checkbox").each(function() {
    	if ( $(this).is(':checked') ) {
    		var siteId = $(this).val();
    		var siteName =  $(this).data('name');
    		var hrefAccess = $('#access_id_' + siteId);
    		hrefAccess.css("font-weight", "bold");
    		hrefAccess.html("Access: " + sliderSpeedValue + " " + accessType);
    	}
    });
}

function displayPostSpeedSelectionInLeftNavigation() {
	var sliderPortSpeedValue = gUserConfiguration.getConfigurationData().portConfig.sliderPortSpeedValue;

    $("#salesexpress-side-bar").find(":checkbox").each(function() {
    	if ( $(this).is(':checked') ) {
    		var siteId = $(this).val();
    		var siteName =  $(this).data('name');
    		var hrefPort = $('#port_id_' + siteId);
    		hrefPort.css("font-weight", "bold");
    		hrefPort.html("Port: " + sliderPortSpeedValue);
    	}
    });
}

