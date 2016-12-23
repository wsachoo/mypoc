$(document).ready(function() {
	$("#configureForm").on({
		"click" : function(e) {
			var eventSourceName = e.target.name;
			console.log("eventSourceName:" + eventSourceName);
			
			switch (eventSourceName) {
				case 'btnApplyAccessConfigurationOptions':
					handleBtnApplyAccessConfigurationOptionsClick($(this), e.target);
					break;
				case 'btnApplyPortConfigurationOptions':
					handleBtnApplyPortConfigurationOptionsClick($(this), e.target);
					break;
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
	var formData = JSON.stringify(gUserConfiguration.getConfigurationData());
	
	var url = SALESEXPRESS_CONSTANTS.getUrlPath("siteConfigurationPostUrl");
	var promise = httpAsyncPostWithJsonRequestResponse(url, formData);
	
	promise.done(function(data, textStatus, jqXHR ) {
		var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
								  "<a href='#' class='close' data-dismiss='alert' data-applybutton='success' aria-label='close'>&times;</a>" +
								  "<strong>Success!</strong> The request has been submitted successfully</div>";
		$("#divPortConfigClickApplyMessage").html(successAlertMessage);
		configureResiliencyOptionsAfterPortConfigurationSuccess();
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		var errorObject = $.parseJSON(jqXHR.responseText);
		var failureAlertMessage = "<div class='alert alert-danger alert-dismissible'>" +
		  						  "<a href='#' class='close' data-dismiss='alert' data-applybutton='failure' aria-label='close'>&times;</a>" +
		  						  "<strong>Failure!</strong> The request failed with this error: " + errorObject.reasonPhrase + "</div>";  
		$("#divPortConfigClickApplyMessage").html(failureAlertMessage);
	});	
}

function handleBtnApplyAccessConfigurationOptionsClick($thisRef, eventSource) {
	updateInMemoryConfigurationFromFormObject($("#configureForm"));
	var formData = JSON.stringify(gUserConfiguration.getConfigurationData());
	
	var url = SALESEXPRESS_CONSTANTS.getUrlPath("siteConfigurationPostUrl");
	var promise = httpAsyncPostWithJsonRequestResponse(url, formData);
	
	promise.done(function(data, textStatus, jqXHR ) {
		var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
								  "<a href='#' class='close' data-dismiss='alert' data-applybutton='success' aria-label='close'>&times;</a>" +
								  "<strong>Success!</strong> The request has been submitted successfully</div>";
		configureModifyUserOptionsAfterAccessApplySuccess();
		$("#divAccessTypeclickApplyMessage").html(successAlertMessage);
		$("#transactionId").val(data.transactionId);
		
		var accessType = gUserConfiguration.getConfigurationData().accessConfig.radiofilteredAccessTypes || gUserConfiguration.getConfigurationData().accessConfig.selectAccessType;
		var sliderSpeedValue = gUserConfiguration.getConfigurationData().accessConfig.sliderSpeedValue;

		$.each(siteMetaData.accessType, function(i, obj) {
		    if (obj.key == accessType) {
		    	accessType = obj.value;
		    }
		});
		
		displayAccessSelectionInLeftNavigation("hqAccessId", sliderSpeedValue, accessType);
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

function configureResiliencyOptionsAfterPortConfigurationSuccess() {
	var $divPortConfigOptionsData = $("#divPortConfigOptionsData");
	$divPortConfigOptionsData.nextAll('div').not('.sachbottommenu').remove();

	var resiliencyOptions = $.tmpl("resiliency_options_template", siteMetaData.resiliencyOptions);
	lastDiv = findLastDivRowOfElement($("#accessSpeedConfigPlaceholder"));
	lastDiv.after(resiliencyOptions);
	
	configureDefaultResiliencySpeedSlider();
	lastDiv.trigger('create');
}

function configureDefaultResiliencySpeedSlider() {
	var allResiliencySpeeds = siteMetaData.resiliencyOptions.resiliencyOptionsDetail.default;
    $("#divSliderResiliencySpeed").slider({
        min: 0,
        orientation: "horizontal",
        range: "min",
        max: allResiliencySpeeds.range.length-1,   
        slide : function(e, ui) {
    		$(this).slider('value', 0);
    		$("#sliderResiliencySpeedValue").val(allResiliencySpeeds.range[ui.value]);
        }
    });
    $("#divSliderResiliencySpeed").slider("value", 0);
    $("#divSliderResiliencySpeed").find(".ui-slider-range").css("background", "#337ab7");
    
    setResiliencyOptionSpeedSliderLimit(allResiliencySpeeds);
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

function displayAccessSelectionInLeftNavigation(accessLocation, sliderSpeedValue, accessType) {
	var hrefAccess = $('#' + accessLocation);
	hrefAccess.css("font-weight", "bold");
	hrefAccess.html("Access: " + sliderSpeedValue + " " + accessType);
}