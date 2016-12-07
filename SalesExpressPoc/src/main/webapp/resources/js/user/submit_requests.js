$(document).ready(function() {

	$("#configureForm").on('click', '#btnApplyAccessConfigurationOptions', function(e) {
    	e.preventDefault();
    	
    	updateInMemoryConfigurationFromFormObject($("#configureForm"));
    	var formData = JSON.stringify(gUserConfiguration.getConfigurationData());
    	
    	var url = SALESEXPRESS_CONSTANTS.getUrlPath("siteConfigurationPostUrl");
    	var promise = httpAsyncPostWithJsonRequestResponse(url, formData);
    	
    	promise.done(function(data, textStatus, jqXHR ) {
    		var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
    								  "<a href='#' class='close' data-dismiss='alert' data-applybutton='success' aria-label='close'>&times;</a>" +
    								  "<strong>Success!</strong> The request has been submitted successfully</div>";
    		$("#divAccessTypeclickApplyMessage").html(successAlertMessage);
    		$("#transactionId").val(data.transactionId);
    	})
    	.fail(function(jqXHR, textStatus, errorThrown) {
    		var errorObject = $.parseJSON(jqXHR.responseText);
    		var failureAlertMessage = "<div class='alert alert-danger alert-dismissible'>" +
			  						  "<a href='#' class='close' data-dismiss='alert' data-applybutton='failure' aria-label='close'>&times;</a>" +
			  						  "<strong>Failure!</strong> The request failed with this error: " + errorObject.reasonPhrase + "</div>";  
    		$("#divAccessTypeclickApplyMessage").html(failureAlertMessage);
    	});
    });
	
	$("#configureForm").on("click", ".alert-success", function(e) {
		var result = $(e.target).data("applybutton");
		if (result === 'success') {
			//removeAllDivRowsAfterDivId("divAccessConfigOptions");
			//configureModifyUserOptionsAfterAccessApplySuccess();
		}
	});
});
