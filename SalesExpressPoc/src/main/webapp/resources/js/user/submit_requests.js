$(document).ready(function() {

	$("#configureAccessForm").on('click', '#btnApplyAccessConfigurationOptions', function(e) {
    	e.preventDefault();
    	var url = SALESEXPRESS_CONSTANTS.getUrlPath("accessTypePostUrl");
    	var formData = $("#configureAccessForm").serializeJSON();
    	
    	httpAsyncPostWithJsonRequestResponse(url, formData)
    	.done(function(data, textStatus, jqXHR ) {
    		console.log("Inside done() method: " + data.status);
    	})
    	.fail(function(jqXHR, textStatus, errorThrown) {
    		console.log("Inside fail() method: " + jqXHR.responseText);
    	});
    });
});
