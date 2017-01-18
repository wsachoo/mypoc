<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Sales Express POC</title>
	
	<script>
		document.write('<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />');
		document.write('<link rel="stylesheet" href="css/bootstrap.css" type="text/css" />');
		document.write('<link rel="stylesheet" href="css/bootstrap-multiselect.css" type="text/css" />');
		document.write('<link rel="stylesheet" href="css/bootstrap-theme.min.css" type="text/css" />');
		document.write('<link rel="stylesheet" href="css/salesexpress.css?id=' +	 Math.floor(Math.random() * 100) + 'type="text/css" />');
		document.write('<link rel="stylesheet" href="css/sidenav.css?id=' +	 Math.floor(Math.random() * 100) + 'type="text/css" />');
	</script>
	
	<script type="text/javascript" src="js/lib/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="js/lib/jquery-ui.1.12.1.js"></script>
	<script type="text/javascript" src="js/lib/jquery.tmpl.js"></script>
	<script type="text/javascript"src="js/lib/jquery.serialize-object.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyATDwoHFXe3mRsNJZfFMtMiltwSRTZcRFA"></script>
	<script type="text/javascript" src="js/lib/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/lib/bootstrap-multiselect.js"></script>
	
	
	
	<script>
		 document.write('<script src="js/user/init_salesexpress.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
		document.write('<script src="js/user/onload_salesexpress.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
		document.write('<script src="js/user/submit_requests.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
		document.write('<script src="js/user/salesexpress_navigation.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
		 document.write('<script type="text/javascript" defer="defer" src="js/user/configure_salesexpress.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>'); 
	 	document.write('<script type="text/javascript" defer="defer" src="js/user/init_service_features.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
		document.write('<script type="text/javascript" defer="defer" src="js/user/service_features.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');  
	</script>
	
	<script type="text/javascript">
	var resultData = ${resultData};
		$(document).ready(function() {
	 
			$("#salesexpress-side-bar").load("templates/side_menu_bar.html");
			
			
    		handlePackageResults();
				
		});
		
		function handlePackageResults(){
			
			var showResults = $.tmpl("show_results_template", resultData);
			
			$("#showPackages").append(showResults);
			
		}
	</script>
	
</head>
<body>
	<div id="salesexpress-side-bar"></div>    
	    	
	<div class="container-fluid salesexpress-content-area" id="serviceFeaturesConfigPlaceHolder">
		<form id="resultsForm" data-ajax="false" action="${pageContext.request.contextPath}/getResults" method="get">
			<%-- <input type="hidden" value="${userId}" name="userId" id="userId">
			<input type="hidden" value="${solutionId}" name="solutionId" id="solutionId">
			<input type="hidden" value="${transactionId}" name="transactionId" id="transactionId"> --%>
		
			<div class="row sachtopmenu">
				<div class="col-sm-3 col-xs-12 sachmenuitem">
					<a href="#" style="color: white" data-name="siteMap" data-url="${pageContext.request.contextPath}/login/${userId}/${solutionId}">Site Map</a>
				</div>
				<div class="col-sm-3 col-xs-12 sachmenuitem">
					<a href="#" style="color: white" data-name="accessAndPort" data-url="${pageContext.request.contextPath}/configure">Access & Port</a>
				</div>
				<div class="col-sm-3 col-xs-12 sachmenuitem">
					<a href="#" id="serviceFeaturesPage" style="color: white" data-name="serviceAndFeatures" data-url="${pageContext.request.contextPath}/serviceFeatures">Service & Features</a>
				</div>
				<div class="col-sm-3 col-xs-12 sachmenuitemactive">
					<a href="#" id="resultsPage" style="color: white" data-name="results" data-url="${pageContext.request.contextPath}/results">Results</a>
				</div>				
			</div>
			
			<div class="clearfix"></div>
			
    		<div id="showPackages"></div>
    			
    				
		</form>
	</div>
</body>
</html>