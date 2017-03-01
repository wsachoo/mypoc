<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="_csrf" content="${_csrf.token}" />
<title>Sales App Admin</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script>
	var contextPath = "${pageContext.request.contextPath}";
		/* document.write('<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />'); */
		document.write('<link href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />');
		
		document.write('<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" type="text/css" />');
		/* document.write('<link rel="stylesheet" href="css/bootstrap.css" type="text/css" />'); */
		document.write('<link rel="stylesheet" href="/css/bootstrap-multiselect.css" type="text/css" />');
		document.write('<link rel="stylesheet" href="/css/bootstrap-theme.min.css" type="text/css" />');
		document.write('<link rel="stylesheet" href="/css/salesexpress.css?id=' +	 Math.floor(Math.random() * 100) + 'type="text/css" />');
		document.write('<link rel="stylesheet" href="/css/sidenav.css?id=' +	 Math.floor(Math.random() * 100) + 'type="text/css" />');
		document.write('<link rel="stylesheet" href="/css/chatbox.css?id='+ Math.floor(Math.random() * 100) + 'type="text/css" />');
		document.write('<link rel="stylesheet" href="/css/admin-panel.css?id='+ Math.floor(Math.random() * 100) + 'type="text/css" />');
	</script>
	
	<script type="text/javascript" src="/js/lib/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="/js/lib/jquery-ui.1.12.1.js"></script>
	<script type="text/javascript" src="/js/lib/jquery.tmpl.js"></script>
	<script type="text/javascript" src="/js/lib/jquery.serialize-object.js"></script>
	<script type="text/javascript" src="/js/lib/bootstrap.min.js"></script>
	<script type="text/javascript" src="/js/lib/bootstrap-multiselect.js"></script>

	<script>
		document.write('<script src="${contextPath}/js/user/init_salesexpress.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
		document.write('<script src="${contextPath}/js/admin/admin_panel_config.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
	</script>

</head>
<body>
	<div class="container"
		id="accessSpeedConfigPlaceholder">
		<form id="configureForm" data-ajax="false" class="form-horizontal">
		<div class="tabbable boxed parentTabs">
			    <ul class="nav nav-tabs">
			        <li class="active"><a href="#set1">Services and Features</a>
			        </li>
			        <li><a href="#set2">Sites</a>
			        </li>
			    </ul>
		    <div id="myTabContent" class="tab-content">
		        <div class="tab-pane fade active in" id="set1">
		            <div class="tabbable">
		                <ul class="nav nav-tabs">
		                    <li class="active"><a href="#sub11">Add New Service</a>
		                    </li>
		                    <li><a href="#sub12">Delete Existing Service</a>
		                    </li>
		                </ul>
		                <div id="myTabContent" class="tab-content">
				             <div class="tab-pane fade active in" id="sub11">
					             <div class=" salesexpress-content-margin ">
									<div class="" id="addServices" style="display:inline;">
										<label for="name">Add Service Name : <input type="text" id="addService" name="serviceName" class="form-control"></label>
										<input type="button" value="Add Features" id="btnAddFeatures" name="btnAddFeatures" class="btn btn-primary">
										<input type="button" value="Save Service" id="btnSaveService" name="btnSaveService" class="btn btn-primary">
										<br>
									</div>
									<br>
								</div>
				            </div>
		                    <div class="tab-pane fade" id="sub12">
		                          <div class=" salesexpress-content-margin ">
									<div class="" id="deleteServices" style="display:inline;">
										<label for="name">Enter Service Name To be Deleted:<input type="text" id="labelDeleteService" name="labelDeleteService" class="form-control"></label>
										<input type="button" value="Delete Service" id="btnDeleteService" name="btnDeleteService" class="btn btn-primary">
										<br>
									</div>
									<br>
								</div>
		                    </div>
		                </div>
		            </div>
		        </div>
		        <div class="tab-pane fade" id="set2">
		            <div class="tabbable">
		                <ul class="nav nav-tabs">
		                    <li class="active"><a href="#sub21">Add New Sites</a>
		                    </li>
		                    <li><a href="#sub22">Delete Existing Sites</a>
		                    </li>
		                </ul>
		                <div id="myTabContent" class="tab-content">
		                    <div class="tab-pane fade active in" id="sub21">
		                        <p>Tab 2.1</p>
		                    </div>
		                    <div class="tab-pane fade" id="sub22">
		                        <p>Tab 2.2</p>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
			 
			<div class="row sachbottommenu">
				<div class="col-sm-11 sachfootermenuitem" id="divFooterMessage">
					Need to apply an access and port speed to all sites before moving on
				</div>
				 <div class="col-sm-1 sachfootermenuitem">
					 <a href="#"  id="open-Chat"></a>
				</div> 
			</div>	
			 <div class="chat-box" id="chat_div" style="display: none;"><jsp:include page="chatBox.jsp"/></div>
		</form>
		</div>
</body>
</html>
