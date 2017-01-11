<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Sales Express POC</title>
	
	<script>
		document.write('<link href="../../css/jquery-ui.css" rel="stylesheet" type="text/css" />');
		document.write('<link rel="stylesheet" href="../../css/bootstrap.css" type="text/css" />');
		document.write('<link rel="stylesheet" href="../../css/bootstrap-multiselect.css" type="text/css" />');
		document.write('<link rel="stylesheet" href="../../css/bootstrap-theme.min.css" type="text/css" />');
		document.write('<link rel="stylesheet" href="../../css/salesexpress.css?id=' +	 Math.floor(Math.random() * 100) + 'type="text/css" />');
		document.write('<link rel="stylesheet" href="../../css/sidenav.css?id=' +	 Math.floor(Math.random() * 100) + 'type="text/css" />');
	</script>
	
	<script type="text/javascript" src="../../js/lib/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="../../js/lib/jquery-ui.1.12.1.js"></script>
	<script type="text/javascript" src="../../js/lib/jquery.tmpl.js"></script>
	<script type="text/javascript"src="../../js/lib/jquery.serialize-object.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyATDwoHFXe3mRsNJZfFMtMiltwSRTZcRFA"></script>
	<script type="text/javascript" src="../../js/lib/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../js/lib/bootstrap-multiselect.js"></script>
	
	<script>
		document.write('<script src="../../js/user/init_salesexpress.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
		document.write('<script src="../../js/user/onload_salesexpress.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
		document.write('<script src="../../js/user/salesexpress_gmap.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
		document.write('<script src="../../js/user/submit_requests.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
		document.write('<script src="../../js/user/salesexpress_navigation.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
		document.write('<script type="text/javascript" defer="defer" src="../../js/user/configure_salesexpress.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
	</script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#salesexpress-side-bar").load("../../templates/side_menu_bar.html");
		});
	</script>
</head>
<body>
	<div id="userDetail" style="display: none;">${userDetail}</div>
	
	<div id="salesexpress-side-bar"></div>    
	<div class="container-fluid salesexpress-content-area" id="accessSpeedConfigPlaceholder">
		<form id="configureForm" data-ajax="false">
			<div class="row sachtopmenu">
				<div class="col-sm-3 col-xs-12 sachmenuitemactive">
					<a href="#" style="color: white" data-name="siteMap" data-url="${pageContext.request.contextPath}/login/${userId}/${solutionId}">Site Map</a>
				</div>
				<div class="col-sm-3 col-xs-12 sachmenuitem">
					<a href="#" style="color: white" data-name="accessAndPort" data-url="${pageContext.request.contextPath}/configure">Access & Port</a>
				</div>
				<div class="col-sm-3 col-xs-12 sachmenuitem">
					<a href="#" id="serviceFeaturesPage" style="color: white" data-name="serviceAndFeatures" data-url="${pageContext.request.contextPath}/serviceFeatures">Service & Features</a>
				</div>
				<div class="col-sm-3 col-xs-12 sachmenuitem">
					<a href="#" id="#resultsPage" style="color: white" data-name="results" data-url="${pageContext.request.contextPath}/results">Results</a>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="row sachgooglemapdivrow">
				<div id="sachgooglemapdiv" class="sachgooglemapdivclass"></div>
			</div>
			<div class="clearfix"></div>
			<div class="row sachbottommenu">
				<div class="col-sm-12 sachfootermenuitem" id="divFooterMessage">
					Need to apply an access and port speed to all sites before moving on
				</div>
			</div>			
		</form>
		
		<div id="over_map" class="sachbuttonsonmap" style="text-align: center;">
		<div class="button-wrapper">
		<button type="button" class="btn btn-primary">Solution Template</button>
		&nbsp;
		&nbsp;
		<button type="button" class="btn btn-primary">Product/Bundle Filter</button>
		&nbsp;
		&nbsp;
		<button type="button" class="btn btn-primary">Guided Selling</button>
		</div>
		</div>
	</div> 
</body>
</html>