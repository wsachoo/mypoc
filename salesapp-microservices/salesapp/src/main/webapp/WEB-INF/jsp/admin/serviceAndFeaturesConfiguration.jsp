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
		document.write('<link rel="stylesheet" href="${contextPath}/css/jquery-ui.css" type="text/css" />');
		document.write('<link rel="stylesheet" href="${contextPath}/css/bootstrap.css" type="text/css" />');
		document.write('<link rel="stylesheet" href="${contextPath}/css/bootstrap-multiselect.css" type="text/css" />');
		document.write('<link rel="stylesheet" href="${contextPath}/css/bootstrap-theme.min.css" type="text/css" />');
		document.write('<link rel="stylesheet" href="${contextPath}/css/salesexpress.css?id=' +	 Math.floor(Math.random() * 100) + 'type="text/css" />');
		document.write('<link rel="stylesheet" href="${contextPath}/css/sidenav.css?id=' +	 Math.floor(Math.random() * 100) + 'type="text/css" />');
		document.write('<link rel="stylesheet" href="${contextPath}/css/admin-panel.css?id='+ Math.floor(Math.random() * 100) + 'type="text/css" />');
	</script>
	
	<script type="text/javascript" src="${contextPath}/js/lib/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="${contextPath}/js/lib/jquery-ui.1.12.1.js"></script>
	<script type="text/javascript" src="${contextPath}/js/lib/bootstrap.min.js"></script>

	<script>
		document.write('<script src="${contextPath}/js/user/init_salesexpress.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
		document.write('<script src="${contextPath}/js/admin/admin_panel_config.js?dev=' + Math.floor(Math.random() * 100) + '"\><\/script>');
	</script>

</head>
<body>

	<div class="container-fluid" id="accessSpeedConfigPlaceholder">
		<div id="sales_navigation_menu">
			<jsp:include page="../sales_navigation_menu_admin.jsp" />
		</div> 
		<ul id="adminPanelTopMenu" class="nav nav-tabs nav-tabs-responsive" role="tablist">
	        <li role="presentation" class="active">
	              <a href="#addServices" role="tab" id="addServices-tab" data-toggle="tab" aria-controls="addServices" aria-expanded="true">
	                <span>Add New Services</span>
	              </a>
	        </li>      
	            <li role="presentation" class="next">
	              <a href="#deleteServices" role="tab" id="deleteServices-tab" data-toggle="tab" aria-controls="deleteServices">
	                <span>Delete Services</span>
	              </a>
	            </li>
	          </ul>
		<form id="configureForm" data-ajax="false" class="form-inline">
			<div class="wrapper">
			    <div class="bs-example bs-example-tabs" role="tabpanel" data-example-id="togglable-tabs">
			      <div id="serviceFeaturesTabContent" class="tab-content">
			        <div role="tabpanel" class="tab-pane fade in active" id="addServices" aria-labelledby="addServices-tab">
			          <div class="" id="addServiceFeatures" style="display:inline;">
						<label for="name">Add Service Name : 
						<input type="text" id="addService" name="serviceName" class="form-control" required="required"></label>
						<br>
						<input type="button" value="Add Features" id="btnAddFeatures" name="btnAddFeatures" class="btn btn-admin-panel">
						<br>
					</div>
					<br>
					 <div id="divBtnSaveService" class="col-sm-offset-11">
						<input type="button" value="Save Service" id="btnSaveService" name="btnSaveService" class="btn btn-admin-panel">
					</div>
			        </div>
			       
			        <div role="tabpanel" class="tab-pane fade" id="deleteServices" aria-labelledby="deleteServices-tab">
			         <div class="" id="deleteServices" style="display:inline;">
						<label for="name">Service : <select id="serviceToDelete" class="selectType"></select></label>
							<input type="button" value="Delete Service" data-toggle="modal" data-target="#confirmModal" class="btn btn-admin-panel">
						<br>
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
			<div class="chat-box" id="chat_div" style="display: none;"><jsp:include page="../chatBox.jsp"/></div>
			<div class="modal fade" id="confirmModal" role="dialog">
		   	<div class="modal-dialog">
		      <!-- Modal content-->
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
		          <h4 class="modal-title" style="text-align:center;">You have selected to delete a service.</h4>
		        </div>
		        <div class="modal-body" style="text-align:center;">
		          <p>Please click YES to delete a service.</p>
		        </div>
		        <div class="modal-footer" style="text-align:center;">
		          <button type="button" class="btn btn-admin-panel" data-dismiss="modal">NO</button>
		          <button type="button" class="btn btn-danger" id="btnDeleteService" name="btnDeleteService" data-dismiss="modal">YES</button> 
		        </div>
		      </div>
		   	</div>
		  </div>
		</form>
			<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#successModal" id="btnSuccessModal" style="display: none;">Open Modal</button>
		    <div class="modal fade" id="successModal" role="dialog">
		    <div class="modal-dialog">
		      <!-- Modal content-->
		      <div class="modal-content">
		        <div class="modal-body" style="text-align: center;">
		          <button type="button" class="close" data-dismiss="modal">&times;</button>
		          <h4 class="modal-title" id="updateMessage"></h4>
		        </div> 
		      </div> 
		    </div>
		  </div>
		  
		  
	
	</div>
</body>
</html>
