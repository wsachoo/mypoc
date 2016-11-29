<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Sales Express POC</title>
<link href="/resources/css/jquery-ui.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" href="/resources/css/bootstrap.css"
	type="text/css" />
<link rel="stylesheet" href="/resources/css/bootstrap-multiselect.css"
	type="text/css" />
<link rel="stylesheet" href="/resources/css/bootstrap-theme.min.css"
	type="text/css" />
<link rel="stylesheet" href="/resources/css/salesexpress.css"
	type="text/css" />
<!-- <link rel="stylesheet" href="/resources/css/salesexpress-sidebar.css"
	type="text/css" />
 -->
 <link rel="stylesheet" href="/resources/css/sidenav.css"	type="text/css" />
 
 <script type="text/javascript" src="/resources/js/lib/jquery-1.12.4.js"></script>
<script type="text/javascript"
	src="/resources/js/lib/jquery-ui.1.12.1.js"></script>
<script type="text/javascript" src="/resources/js/lib/jquery.tmpl.js"></script>
<script type="text/javascript"
	src="/resources/js/lib/jquery.serialize-object.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	$("#salesexpress-side-bar").load("/templates/side_menu_bar.html");
});
</script>

<script type="text/javascript" src="/resources/js/lib/bootstrap.min.js"></script>
<script type="text/javascript"
	src="/resources/js/lib/bootstrap-multiselect.js"></script>
<script>
	document.write('<script src="/resources/js/user/init_salesexpress.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
</script>
<script>
	document.write('<script src="/resources/js/user/onload_salesexpress.js?dev='
			+ Math.floor(Math.random() * 100) + '"\><\/script>');
</script>
<script>
	document.write('<script src="/resources/js/user/submit_requests.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
</script>
<script>
	document.write('<script src="/resources/js/user/salesexpress_side_navbar.js?dev='
			+ Math.floor(Math.random() * 100) + '"\><\/script>');
</script>

<script>
	document
			.write('<script type="text/javascript" defer="defer" src="/resources/js/user/configure_salesexpress.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');
</script>

</head>
<body>
	<div id="salesexpress-side-bar"></div>    
	    	
	<div class="container-fluid salesexpress-content-area" id="accessSpeedConfigPlaceholder">
		<form id="configureAccessForm" data-ajax="false">
			<div class="row sachtopmenu">
				<div class="col-sm-3 col-xs-12 sachmenuitem">
					<label for="name">Site Map</label>
				</div>
				<div class="col-sm-3 col-xs-12 sachmenuitemactive">
					<label for="name">Access & Port</label>
				</div>
				<div class="col-sm-3 col-xs-12 sachmenuitem">
					<label for="name">Service & Features</label>
				</div>
				<div class="col-sm-3 col-xs-12 sachmenuitem">
					<label for="name">Results</label>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="row salesexpress-content-margin">
				<div class="col-sm-12">
					<label for="name">Do you have access requirements at any of your sites?</label>
				</div>
			</div>
			<div class="row">
				<div class="radio col-sm-11 col-sm-offset-1">
					<label> <input type="radio" name="accessRequired"
						id="accessRequiredNo" value="false" checked="checked"> No
					</label>
				</div>
				<div class="radio col-sm-11 col-sm-offset-1">
					<label> <input type="radio" name="accessRequired"
						id="accessRequiredYes" value="true"> Yes
					</label>
				</div>
			</div>
 			<div class="row sachbottommenu">
				<div class="col-sm-12 sachfootermenuitem" id="divFooterMessage">
					Need to apply an access and port speed to all sites before moving on
				</div>
			</div>			
		</form>
	</div>
</body>
</html>