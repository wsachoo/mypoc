$(document).ready(function() {

	$("#configureAccessForm").on('click', '#btnApplyAccessConfigurationOptions', function(e) {
    	e.preventDefault();
    	var url = SALESEXPRESS_CONSTANTS.getUrlPath("accessTypePostUrl");
    	var formData = $("#configureAccessForm").serializeJSON();
    	
    	var promise = httpAsyncPostWithJsonRequestResponse(url, formData);
    	
    	promise.done(function(data, textStatus, jqXHR ) {
    		console.log("Inside done() method: " + data.status);
    		var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
    								  "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>" +
    								  "<strong>Success!</strong> The request has been submitted successfully</div>";
    		$("#divAccessTypeclickApplyMessage").html(successAlertMessage);
    	})
    	.fail(function(jqXHR, textStatus, errorThrown) {
    		var successAlertMessage = "<div class='alert alert-danger alert-dismissible'>" +
			  						  "<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>" +
			  						  "<strong>Failure!</strong> The request failed with this error: " + $.parseJSON(jqXHR.responseText).reasonPhrase + "</div>";  
    		$("#divAccessTypeclickApplyMessage").html(successAlertMessage);
    	});
    });
});
