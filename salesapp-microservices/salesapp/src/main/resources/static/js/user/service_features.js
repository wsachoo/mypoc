$(document).ready(function(){
	
	$("#accessSpeedConfigPlaceholder").on({
		
		"click" : function(e) {
			var eventSourceName = e.target.name;
			switch (eventSourceName) {
			case 'btnGenerateContract':
				handleGenerateContract($(this), e.target);
				break;
			}
		},
		"change" : function(e) {
			var eventSourceName = e.target.name;
			
			switch (eventSourceName) {
			case 'Data-Service-Performance-Test' :
				//handleDataServicePerformanceFeature($(this), e.target);
				handleCommonServiceFeatures($(this), e.target);
				break;
			/*case 'dataService-performance' :
				handleCommonServiceFeatures($(this), e.target);
				break;*/
			/*default :
				handleCommonServiceFeatures($(this), e.target);
				break;*/
			}
		}
		
	});
});

function handleCommonServiceFeatures($thisRef, eventSource){
	var commonServiceFeatures = $(eventSource).attr('id');
	if (($(eventSource).is(':checked'))){
		$("#"+commonServiceFeatures+"-Features").css("display", "inline");
	}else{
		$("#"+commonServiceFeatures+"-Features").css("display", "none");
	}
}

function handleGenerateContract(){
	var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
	"<a href='#' class='close' data-dismiss='alert' data-applybutton='success' aria-label='close'>&times;</a>" +
	"<strong>Success!</strong> Contract has been generated successfully</div>";
	$("#divGenerateContractAlert").html(successAlertMessage);
}