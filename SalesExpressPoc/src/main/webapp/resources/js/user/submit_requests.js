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
					break;
				case 'proceedToFeatures' :
					handleProceedToFeatures($(this), e.target);
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
	var formData = JSON.stringify(gUserConfiguration.getUserConfigurationData());
	
	var url = SALESEXPRESS_CONSTANTS.getUrlPath("siteConfigurationPostUrl");
	var promise = httpAsyncPostWithJsonRequestResponse(url, formData);
	
	promise.done(function(data, textStatus, jqXHR ) {
		var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
								  "<a href='#' class='close' data-dismiss='alert' data-applybutton='success' aria-label='close'>&times;</a>" +
								  "<strong>Success!</strong> The request has been submitted successfully</div>";
		$("#divPortConfigClickApplyMessage").html(successAlertMessage);
		
		displayPostSpeedSelectionInLeftNavigation();

	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		var errorObject = $.parseJSON(jqXHR.responseText);
		var failureAlertMessage = "<div class='alert alert-danger alert-dismissible'>" +
		  						  "<a href='#' class='close' data-dismiss='alert' data-applybutton='failure' aria-label='close'>&times;</a>" +
		  						  "<strong>Failure!</strong> The request failed with this error: " + errorObject.reasonPhrase + "</div>";  
		$("#divPortConfigClickApplyMessage").html(failureAlertMessage);
	});	
	$("#proceedToFeatures").css("display", "inline");
	
}

function handleBtnApplyAccessConfigurationOptionsClick($thisRef, eventSource) {
	updateInMemoryConfigurationFromFormObject($("#configureForm"));
	var formData = JSON.stringify(gUserConfiguration.getUserConfigurationData());
	
	var url = SALESEXPRESS_CONSTANTS.getUrlPath("siteConfigurationPostUrl");
	var promise = httpAsyncPostWithJsonRequestResponse(url, formData);
	
	promise.done(function(data, textStatus, jqXHR ) {
		var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
								  "<a href='#' class='close' data-dismiss='alert' data-applybutton='success' aria-label='close'>&times;</a>" +
								  "<strong>Success!</strong> The request has been submitted successfully</div>";
		configureModifyUserOptionsAfterAccessApplySuccess();
		$("#divAccessTypeclickApplyMessage").html(successAlertMessage);
		$("#transactionId").val(data.transactionId);
		
		displayAccessSelectionInLeftNavigation();
		updateFooterMessage("Access configuration options have been defaulted for the sites selected. Access options are too varied across sites to be configured together.");
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		var errorObject = $.parseJSON(jqXHR.responseText);
		var failureAlertMessage = "<div class='alert alert-danger alert-dismissible'>" +
		  						  "<a href='#' class='close' data-dismiss='alert' data-applybutton='failure' aria-label='close'>&times;</a>" +
		  						  "<strong>Failure!</strong> The request failed with this error: " + errorObject.reasonPhrase + "</div>";  
		$("#divAccessTypeclickApplyMessage").html(failureAlertMessage);
	});	
}


function configureModifyUserOptionsAfterAccessApplySuccess() {
	var $divAccessConfigOptions = $("#divAccessConfigOptions");
	$divAccessConfigOptions.nextAll('div').not('.sachbottommenu').remove();
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
		
	if ($('input[name="chkHeadequarters"').is(":checked")) {
		var hrefAccess = $('#hqAccessId');
		hrefAccess.css("font-weight", "bold");
		hrefAccess.html("Access: " + sliderSpeedValue + " " + accessType);
	}
	if ($('input[name="chkAccountReceivables"').is(":checked")) {
		var hrefAccess = $('#arAccessId');
		hrefAccess.css("font-weight", "bold");
		hrefAccess.html("Access: " + sliderSpeedValue + " " + accessType);
	}
	if ($('input[name="chkDistributionCenter"').is(":checked")) {
		var hrefAccess = $('#dcAccessId');
		hrefAccess.css("font-weight", "bold");
		hrefAccess.html("Access: " + sliderSpeedValue + " " + accessType);
	}
}

function displayPostSpeedSelectionInLeftNavigation() {
	var sliderPortSpeedValue = gUserConfiguration.getConfigurationData().portConfig.sliderPortSpeedValue;
	
	if ($('input[name="chkHeadequarters"').is(":checked")) {
		var hrefAccess = $('#hqPortId');
		hrefAccess.css("font-weight", "bold");
		hrefAccess.html("Port: " + sliderPortSpeedValue);
	}
	if ($('input[name="chkAccountReceivables"').is(":checked")) {
		var hrefAccess = $('#arPortId');
		hrefAccess.css("font-weight", "bold");
		hrefAccess.html("Port: " + sliderPortSpeedValue);
	}
	if ($('input[name="chkDistributionCenter"').is(":checked")) {
		var hrefAccess = $('#dcPortId');
		hrefAccess.css("font-weight", "bold");
		hrefAccess.html("Port: " + sliderPortSpeedValue);
	}	
}

function handleProceedToFeatures(){
	
	window.location.href =  "/serviceFeatures"; 
}