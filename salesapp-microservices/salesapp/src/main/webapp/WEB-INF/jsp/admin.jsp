<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="_csrf" content="${_csrf.token}" />
<title>Sales App</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script>
	var contextPath = "${pageContext.request.contextPath}";
	document
			.write('<link href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />');
	document
			.write('<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" type="text/css" />');
	document
			.write('<link rel="stylesheet" href="${contextPath}/css/bootstrap-theme.min.css" type="text/css" />');
</script>

<script type="text/javascript"
	src="${contextPath}/js/lib/jquery-1.12.4.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/lib/jquery-ui.1.12.1.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/lib/bootstrap.min.js"></script>

<script>
	document
			.write('<script src="${contextPath}/js/user/init_salesexpress.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');
	document
			.write('<script src="${contextPath}/js/user/submit_requests.js?dev='
					+ Math.floor(Math.random() * 100) + '"\><\/script>');
</script>

<script type="text/javascript">
	$(document).ready(
			function() {

				$("#btnAddService").on( "click", function() 
					{
						var promise = httpAsyncPostWithJsonRequestResponse("/admin/updateServicesFeatures", $("#addServiceData").val());

						promise.done(function(data, textStatus, jqXHR) {
							alert("Updated successfully.");
						}).fail(function(jqXHR, textStatus, errorThrown) {
							alert("Failed to update.");
						});
					}
				);

				$("#btnDeleteService").on( "click", function() 
					{
						var promise = httpAsyncPostWithJsonRequestResponse("/admin/deleteServicesFeatures", $("#deleteServiceData").val());

						promise.done(function(data, textStatus, jqXHR) {
							alert("Deleted successfully.");
						}).fail(function(jqXHR, textStatus, errorThrown) {
							alert("Failed to delete.");
						});
					}
				);
			});
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<h3>Admin Screen</h3>
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#home">Add Service</a></li>
				<li><a data-toggle="tab" href="#menu1">Delete Service</a></li>
				<li><a data-toggle="tab" href="#menu2">Add Site</a></li>
				<li><a data-toggle="tab" href="#menu3">Delete Site</a></li>
			</ul>

			<div class="tab-content">
				<div id="home" class="tab-pane fade in active">
					<div class="row">
						<div class="col-sm-offset-3">
							<h4>Copy Service JSON inside following area</h4>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-offset-3">
							<textarea id="addServiceData" rows="30" cols="100">
{
	"id": "voice2",
	"displayValue": "Voice2",
	"children": {
		"id": "voiceService2",
		"label": "Voice2",
		"values": [{
				"id": "voip2",
				"displayValue": "VOIP2",
				"children": [{
						"label": "Do you want to add VOIP?2",
						"type": "radio-button",
						"name": "addVOIP2",
						"options": {
							"yes": "Yes",
							"no": "No"
						}
					}
				]
			}
		]
	}
}
							
							</textarea>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-offset-3">
							<button id="btnAddService">Add Service</button>
						</div>
					</div>
				</div>
				<div id="menu1" class="tab-pane fade">
					<div class="row">
						<div class="col-sm-offset-3">
							<h4>Copy Service JSON inside following area</h4>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-offset-3">
							<textarea id="deleteServiceData" rows="5" cols="100">
{
	"id": "voice2"
}							
							</textarea>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-offset-3">
							<button id="btnDeleteService">Delete Service</button>
						</div>
					</div>
				</div>
				<div id="menu2" class="tab-pane fade">
					<h3>Execute following command in SB2 database</h3>
					<h5>insert into sales_site values ( 
						120,810000,'ATT Test Site', '106 1st Street','East Polson','MT','US',SYSDATE,NULL,SYSDATE,NULL,59860 ,406883 
						)
					</h5>
				</div>
				<div id="menu3" class="tab-pane fade">
					<h3>Execute following command in SB2 database</h3>
					<h5>delete from sales_site where site_id=120</h5>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
