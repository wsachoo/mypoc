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
	<div id="narrow-browser-alert" class="alert alert-info text-center">
		<strong>Sales App</strong> <br>Admin Panel</div>
		<ul id=adminPanelTopMenu class="nav nav-tabs nav-tabs-responsive" role="tablist">
	        <li role="presentation" class="dropdown">
	          <a href="#" id=serviceFeaturesManagement class="dropdown-toggle" data-toggle="dropdown" aria-controls="serviceFeaturesManagement-contents">
	            <span class="text">Service And Features</span>
	            <span class="caret">
	            </span>
	          </a>
	          <ul class="dropdown-menu" role="menu" aria-labelledby="addServiceFeatures" id="addServiceFeatures-contents">
	            <li>
	              <a href="#addServices" tabindex="-1" role="tab" id="addServices-tab" data-toggle="tab" aria-controls="addServices">
	                <span>Add New Services</span>
	              </a>
	            </li>
	            <li>
	              <a href="#deleteServices" tabindex="-1" role="tab" id="deleteServices-tab" data-toggle="tab" aria-controls="deleteServices">
	                <span>Delete Services</span>
	              </a>
	            </li>
	          </ul>
	        </li>
	        <li role="presentation" class="dropdown">
	          <a href="#" id="productManagement" class="dropdown-toggle" data-toggle="dropdown" aria-controls="productManagement-contents">
	            <span class="text">Products Management</span>
	            <span class="caret">
	            </span>
	          </a>
	          <ul class="dropdown-menu" role="menu" aria-labelledby="productManagement" id="productManagement-contents">
	            <li>
	              <a href="#configureProducts" tabindex="-1" role="tab" id="configureProducts-tab" data-toggle="tab" aria-controls="configureProducts">
	                <span>Configure Products</span>
	              </a>
	            </li>
	            <li>
	              <a href="#deleteSites" tabindex="-1" role="tab" id="deleteSites-tab" data-toggle="tab" aria-controls="deleteSites">
	                <span>Delete Sites</span>
	              </a>
	            </li>
	          </ul>
	        </li>
		</ul>
		<form id="configureForm" data-ajax="false" class="form-inline">
			<div class="wrapper">
			    <div class="bs-example bs-example-tabs" role="tabpanel" data-example-id="togglable-tabs">
			      <div id="serviceFeaturesTabContent" class="tab-content">
			        <div role="tabpanel" class="tab-pane fade" id="addServices" aria-labelledby="addServices-tab">
			          <div class="" id="addServiceFeatures" style="display:inline;">
						<label for="name">Add Service Name : 
						<input type="text" id="addService" name="serviceName" class="form-control"></label>
						<br>
						<input type="button" value="Add Features" id="btnAddFeatures" name="btnAddFeatures" class="btn btn-primary">
						<br>
					</div>
					<br>
					 <div id="divBtnSaveService" class="col-sm-offset-11">
						<input type="button" value="Save Service" id="btnSaveService" name="btnSaveService" class="btn btn-primary">
					</div>
			        </div>
			       
			        <div role="tabpanel" class="tab-pane fade" id="deleteServices" aria-labelledby="deleteServices-tab">
			         <div class="" id="deleteServices" style="display:inline;">
						<label for="name">Enter Service Name To be Deleted:<input type="text" id="labelDeleteService" name="labelDeleteService" class="form-control"></label>
							<input type="button" value="Delete Service" id="btnDeleteService" name="btnDeleteService" class="btn btn-primary">
						<br>
					 </div>
			        </div>
			        <div role="tabpanel" class="tab-pane fade" id="configureProducts" aria-labelledby="configureProducts-tab">
			        <div class="row">
			          <div class="marginTopBuffer col-sm-2">
			          	<label for="name">Product : </label>
			          </div>
			          <div class="marginTopBuffer col-sm-10">
			          	<select name="productType" id="productType" class="productManagementselectType">
			          			<option value="AVPN">AVPN</option>
				          		<option value="misPnt">MIS-PNT</option>
						</select>
			          </div>
			         </div> 
			         <div class="row">
				          <div class="marginTopBuffer col-sm-2">
				          	<label for="name">Access Speed : </label>
				          </div>
				          <div class="marginTopBuffer col-sm-10">
				          	<input type="text" class="form-control" id="txtAccessSpeed" name="txtAccessSpeed">
				          	<select name="speedUnit_accessType" id="speedUnit_accessType" class="productManagementselectType">
				          			<option value="Kbps">Kbps</option>
					          		<option value="Mbps">Mbps</option>
					          		<option value="Gbps">Gbps</option>
							</select>
				          </div>
			          </div>
			          <div class="row">
				           <div class="marginTopBuffer col-sm-2">
				          	<label for="name">Access Type : </label>
				          </div>
				          <div class="marginTopBuffer col-sm-10">
				          	<select name="accessType" id="accessType" class="productManagementselectType">
				          			<option value="Ethernet">Ethernet</option>
					          		<option value="IP">IP</option>
							</select>
				          </div>
			          </div>
			          <div class="row classPortSpeed" id="divPortSpeed">
				          <div class="marginTopBuffer col-sm-2">
				          	<label for="name">Port Speed : </label>
				          </div>
				          <div class="marginTopBuffer col-sm-10">
				          	<input type="text" class="form-control" name="txtSpeed_portType">
				          	<select name="speedUnit_portType" class="productManagementselectType">
				          			<option value="Kbps">Kbps</option>
					          		<option value="Mbps">Mbps</option>
					          		<option value="Gbps">Gbps</option>
							</select>
							<input type="text" class="form-control" name="txtMRC_portType" placeholder="MRC" style="width:15%">
							<input type="text" class="form-control" name="txtNRC_portType" placeholder="NRC" style="width:15%">
							<input type="button" class="btn" name="btnAddPortSpeedDiv" id="btnAddPortSpeedDiv" value="Add PortSpeeds">
				          </div>
			          </div>
			     
			          <div class="row">
				          <div class="marginTopBuffer col-sm-2">
				          	<label for="name">Port Type : </label>
				          </div>
				          <div class="marginTopBuffer col-sm-10">
				          	<select name="portType" id="portType" class="productManagementselectType">
				          			<option value="Ethernet">Ethernet</option>
					          		<option value="IP">IP</option>
							</select>
				          </div>
			          </div>
			          <div class="row">
			           <div class="marginTopBuffer col-sm-2"></div>
			           <div class="marginTopBuffer col-sm-10">
				          <input type="button" class="btn" id="btnSaveProductConfigData" name="btnSaveProductConfigData" value="Save Product">
				       </div>
			          </div>
			        </div>
			        <div role="tabpanel" class="tab-pane fade" id="deleteSites" aria-labelledby="deleteSites-tab">
			          <p>
			            Deleting Sites
			          </p>
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
