<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="_csrf" content="${_csrf.token}" />
<title>Sales App</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script>
	var contextPath = "${pageContext.request.contextPath}";
	/* document.write('<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />'); */
	document
			.write('<link href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />');

	document
			.write('<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" type="text/css" />');
	/* document.write('<link rel="stylesheet" href="${contextPath}/css/bootstrap.css" type="text/css" />'); */
	document
			.write('<link rel="stylesheet" href="${contextPath}/css/bootstrap-multiselect.css" type="text/css" />');
	document
			.write('<link rel="stylesheet" href="${contextPath}/css/bootstrap-theme.min.css" type="text/css" />');
	document
			.write('<link rel="stylesheet" href="${contextPath}/css/salesexpress.css?id='
					+ Math.floor(Math.random() * 100) + 'type="text/css" />');
	document
			.write('<link rel="stylesheet" href="${contextPath}/css/sidenav.css?id='
					+ Math.floor(Math.random() * 100) + 'type="text/css" />');
	document
			.write('<link rel="stylesheet" href="${contextPath}/css/chatbox.css?id='
					+ Math.floor(Math.random() * 100) + 'type="text/css" />');
 	document
			.write('<link rel="stylesheet" href="${contextPath}/css/step-wizard.css?id='
					+ Math.floor(Math.random() * 100) + 'type="text/css" />');
					 
	document
			.write('<link rel="stylesheet" href="${contextPath}/css/jquery.bootgrid.css?id='
					+ Math.floor(Math.random() * 100) + 'type="text/css" />');				 
					
</script>

<script type="text/javascript"
	src="${contextPath}/js/lib/jquery-1.12.4.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/lib/jquery-ui.1.12.1.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/lib/jquery.tmpl.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/lib/jquery.serialize-object.js"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyATDwoHFXe3mRsNJZfFMtMiltwSRTZcRFA"></script>
<script type="text/javascript"
	src="${contextPath}/js/lib/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/lib/bootstrap-multiselect.js"></script>
	<script type="text/javascript"
	src="${contextPath}/js/lib/jquery.bootgrid.min.js"></script>
<%-- <script type="text/javascript"
	src="${contextPath}/js/lib/step-wizard.js"></script> --%>



</head>
<body>
	<div id="userDetail" style="display: none;">${userDetail}</div>
	<div id="siteIdNameMap" style="display: none;">${siteIdNameMap}</div>

	<div id="sales_navigation_menu">
		<jsp:include page="sales_navigation_menu.jsp" />
	</div>

	<div id="wrapper">

		<div id="salesexpress-side-bar"><jsp:include
				page="side_menu_bar.jsp" /></div>

		<div id="main-wrapper">
			<div class="container-fluid salesexpress-content-area"
				id="accessSpeedConfigPlaceholder">
					<div class="row sachtopmenu">
						<div class="col-sm-6 col-xs-12 sachmenuitemactive"
							id="sachtopmenu_gMap">
							<%-- <a href="#" style="color: white" data-name="siteMap" data-url="${pageContext.request.contextPath}/login/${userId}/${solutionId}">Site Map</a> --%>
							<a href="#" style="color: white" data-name="siteMap"
								data-url="solutionTemplate">Site Map</a>
						</div>
						<div class="col-sm-6 col-xs-12 sachmenuitem"
							id="sachtopmenu_solutionTemplate">
							<a href="${pageContext.request.contextPath}/user/solutionTemplate/stepWizard" style="color: white" data-name="solutionTemplate"
								data-url="stepWizard">Solution Template</a>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="row sachgooglemapdivrow">
						<div id="sachgooglemapdiv" class="sachgooglemapdivclass"></div>
					</div>
					<div class="clearfix"></div>
					<div id="mainpagecontainer" style="margin-left: 15px;">
					<div id="over_map" class="sachbuttonsonmap"
						style="text-align: center; display: none;">
						<div class="button-wrapper">
							<button type="button" class="btn btn-primary">Solution Template</button>
							&nbsp; &nbsp;
							<button type="button" class="btn btn-primary">Product/Bundle
								Filter</button>
							&nbsp; &nbsp;
							<button type="button" class="btn btn-primary">Guided
								Selling</button>
						</div>
					</div>
					</div>
					
					<div class="row sachbottommenu">
						<div class="col-sm-11 sachfootermenuitem" id="divFooterMessage">
							Need to apply an access and port speed to all sites before moving
							on</div>
						<div class="col-sm-1 sachfootermenuitem">
							<a href="#" id="open-Chat"></a>
						</div>
					</div>
					<div class="chat-box" id="chat_div" style="display: none;">
						<jsp:include page="chatBox.jsp" />
					</div>
			</div>
		</div>
	</div>
</body>
<script>

	document
			.write('<script src="${contextPath}/js/user/init_salesexpress.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');
	document
			.write('<script src="${contextPath}/js/user/onload_salesexpress.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');
	document
			.write('<script src="${contextPath}/js/user/submit_requests.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');
	document
			.write('<script src="${contextPath}/js/user/salesexpress_navigation.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');
	document
			.write('<script type="text/javascript" defer="defer" src="${contextPath}/js/user/configure_salesexpress.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');
	document
			.write('<script type="text/javascript" defer="defer" src="${contextPath}/js/user/configure_service_features.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');
	document
			.write('<script type="text/javascript" defer="defer" src="${contextPath}/js/user/service_features.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');
	document
			.write('<script src="${contextPath}/js/user/salesexpress_gmap.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');
	document
			.write('<script src="${contextPath}/js/user/sales_utility_functions.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');
	document.write('<script src="${contextPath}/js/user/chatbox.js?dev='
			+ Math.floor(Math.random() * 100) + '"\><\/script>');
 	document.write('<script src="${contextPath}/js/lib/step-wizard.js?dev='    
			+ Math.floor(Math.random() * 100) + '"\><\/script>');		

	document
			.write('<script src="${contextPath}/js/solutionTemplate/solution-template-navigation.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');
	document
			.write('<script src="${contextPath}/js/solutionTemplate/step_wizard_config.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');				
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$(document).on("click", "#btnGuidedSelling", function(){
			location.replace("${contextPath}/user/home");
		
		});
	});
</script>
</html>

