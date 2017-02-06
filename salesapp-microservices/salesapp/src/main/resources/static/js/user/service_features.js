$(document).ready(function(){
	
	$("#accessSpeedConfigPlaceholder").on({

		"change" : function(e) {
			var eventSourceName = e.target.name;
			
			switch (eventSourceName) {
			case 'dataService_performance' :
				handleDataServicePerformanceFeature($(this), e.target);
				break;	
			case 'securityService_vpn' :
				handleSecurityServiceVpnFeature($(this), e.target);
				break;
			case 'dataService_managedRouter' :
				handleDataServiceManagedRouterFeature($(this), e.target);
				break;
			case 'dataService_diversityOptions' :
				handleDataServiceDiversityOptionsFeature($(this), e.target);
				break;
			case 'miscService_Reporting' :
				handleMiscServiceReporting($(this), e.target);
				break;
			}
		},
	
		"click" : function(e) {
			var eventSourceName = e.target.name;
			switch (eventSourceName) {
			case 'btnGenerateContract':
			handleGenerateContract($(this), e.target);
			break;
			}
		},	
	});
	
});

function handleDataServicePerformanceFeature($thisRef, eventSource){
	
	if (($(eventSource).is(':checked'))){
		$("#dataService_performance_features").css("display", "inline");
	}else{
		$("#dataService_performance_features").css("display", "none");
	}
	
}

function handleSecurityServiceVpnFeature($thisRef, eventSource){
	if (($("#securityService_vpn").is(':checked'))){
		$("#securityService_vpn_features").css("display", "inline");
	}else{
		$("#securityService_vpn_features").css("display", "none");
	}
}

function handleDataServiceManagedRouterFeature($thisRef, eventSource){
	if (($("#dataService_managedRouter").is(':checked'))){
		$("#dataService_managedRouter_features").css("display", "inline");
	}else{
		$("#dataService_managedRouter_features").css("display", "none");
	}
}

function handleDataServiceDiversityOptionsFeature($thisRef, eventSource){
	if (($("#dataService_diversityOptions").is(':checked'))){
		$("#dataService_diversityOptions_features").css("display", "inline");
	}else{
		$("#dataService_diversityOptions_features").css("display", "none");
	}
}

function handleMiscServiceReporting($thisRef, eventSource){
	if (($("#miscService_Reporting").is(':checked'))){
		$("#miscService_reporting_features").css("display", "inline");
	}else{
		$("#miscService_reporting_features").css("display", "none");
	}
}

function handleGenerateContract(){
	var successAlertMessage = "<div class='alert alert-success alert-dismissible'>" +
	"<a href='#' class='close' data-dismiss='alert' data-applybutton='success' aria-label='close'>&times;</a>" +
	"<strong>Success!</strong> Contract has been generated successfully</div>";
	$("#divGenerateContractAlert").html(successAlertMessage);
}