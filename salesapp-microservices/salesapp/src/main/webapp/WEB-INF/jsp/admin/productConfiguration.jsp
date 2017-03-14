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

<script type="text/javascript"
	src="${contextPath}/js/lib/jquery-1.12.4.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/lib/jquery-ui.1.12.1.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/lib/bootstrap.min.js"></script>

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
		<ul id="adminPanelTopMenu" class="nav nav-tabs nav-tabs-responsive"
			role="tablist">
			<li role="presentation" class="active"><a
				href="#configureProducts" id="configureProducts-tab" role="tab"
				data-toggle="tab" aria-controls="configureProducts"
				aria-expanded="true"> <span class="text">Configure
						Product</span>
			</a></li>
			<li role="presentation" class="next"><a href="#addProducts"
				role="tab" id="addProducts-tab" data-toggle="tab"
				aria-controls="addProducts"> <span class="text">Add New
						Product</span>
			</a></li>
			<li role="presentation" class="next"><a href="#deleteProducts"
				role="tab" id="deleteProducts-tab" data-toggle="tab"
				aria-controls="deleteProducts"> <span class="text">Delete
						Product</span>
			</a></li>
		</ul>
		<div class="wrapper">
			<div class="bs-example bs-example-tabs" role="tabpanel"
				data-example-id="togglable-tabs">
				<!-- service and features configuration starts -->
				<div id="serviceFeaturesTabContent" class="tab-content">

					<div id="myTabContent" class="tab-content">
						<div role="tabpanel" class="tab-pane fade in active"
							id="configureProducts" aria-labelledby="configureProducts-tab">
							<form id="configureForm" data-ajax="false" class="form-inline">
								<div class="row">
									<div class="marginTopBuffer col-sm-2">
										<label for="name">Access Speed : </label>
									</div>
									<div class="marginTopBuffer col-sm-10">
										<input class="form-control" id="txtAccessSpeed"
											name="txtAccessSpeed" type="number" required="required"
											step="any"> <select name="speedUnit_accessType"
											id="speedUnit_accessType" class="productManagementselectType">
											<option value="bps">bps</option>
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
										<select name="accessType" id="accessType"
											class="productManagementselectType">
											<option value="ETHERNET">ETHERNET</option>
											<option value="IP">IP</option>
										</select>
									</div>
								</div>
								<div class="row classPortSpeed" id="divPortSpeed">
									<div class="marginTopBuffer col-sm-2">
										<label for="name">Port Speed : </label>
									</div>
									<div class="marginTopBuffer col-sm-10">
										<input class="form-control" name="txtSpeed_portType"
											type="number" required="required" step="any"> <select
											name="speedUnit_portType" class="productManagementselectType">
											<option value="bps">bps</option>
											<option value="Kbps">Kbps</option>
											<option value="Mbps">Mbps</option>
											<option value="Gbps">Gbps</option>
										</select> <input type="text" class="form-control"
											name="txtMRC_portType" placeholder="MRC" style="width: 15%"
											required="required"> <input type="text"
											class="form-control" name="txtNRC_portType" placeholder="NRC"
											style="width: 15%" required="required">
										<button type="button" id="btnAddPortSpeedDiv"
											name="btnAddPortSpeedDiv" class="btn btn-admin-panel">
											<span class="glyphicon glyphicon-plus" id=""
												style="font-size: large;"></span>
										</button>
										<button type="button" id="btnRemovePortSpeedDiv"
											name="btnRemovePortSpeedDiv" class="btn btn-admin-panel"
											style="display: none;">
											<span class="glyphicon glyphicon-minus" id=""
												style="font-size: large;"></span>
										</button>
									</div>
								</div>
								<div class="row" id="productsDiv">
									<div class="marginTopBuffer col-sm-2">
										<label for="name">Select Your Product : </label>
									</div>
									<div class="marginTopBuffer col-sm-1">
										<input type="checkbox" name="product" id="chkAVPNProduct"
											value="AVPN">AVPN
									</div>
									<div class="marginTopBuffer col-sm-1">
										<input type="checkbox" name="product" id="chkMISPNTProduct"
											value="MISPNT">MISPNT
									</div> 
									 <div class="clearfix"></div> 
								</div>
								<div class="row">
									<div class="marginTopBuffer col-sm-2"></div>
									<div class="marginTopBuffer col-sm-10">
										<input type="button" class="btn btn-admin-panel" id="btnSaveProductConfigData"
											name="btnSaveProductConfigData" value="Configure Product">
									</div>
								</div>
							</form>
						</div>

						<div role="tabpanel" class="tab-pane fade" id="addProducts"
							aria-labelledby="addProducts-tab">
							<form id="addForm" data-ajax="false" class="form-inline">
								<div class="row marginTopBuffer">
									<div class=" col-sm-2">
										<label for="name">Enter Product Name : </label>
									</div>
									<div class=" col-sm-4">
										<input type="text" class="form-control"
											name="product" placeholder="Product Name"
											required="required">
									</div>
									<div class="clearfix"></div>
								</div>
								<div class="row">
									<div class="marginTopBuffer col-sm-2">
										<label for="name">Access Speed : </label>
									</div>
									<div class="marginTopBuffer col-sm-10">
										<input class="form-control" id="txtAccessSpeed"
											name="txtAccessSpeed" type="number" required="required"
											step="any"> <select name="speedUnit_accessType"
											id="speedUnit_accessType" class="productManagementselectType">
											<option value="bps">bps</option>
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
										<select name="accessType" id="accessType"
											class="productManagementselectType">
											<option value="ETHERNET">ETHERNET</option>
											<option value="IP">IP</option>
										</select>
									</div>
								</div>
								<div class="row classNewPortSpeed" id="divNewPortSpeed">
									<div class="marginTopBuffer col-sm-2">
										<label for="name">Port Speed : </label>
									</div>
									<div class="marginTopBuffer col-sm-10">
										<input class="form-control" name="txtSpeed_portType"
											type="number" required="required" step="any"> <select
											name="speedUnit_portType" class="productManagementselectType">
											<option value="bps">bps</option>
											<option value="Kbps">Kbps</option>
											<option value="Mbps">Mbps</option>
											<option value="Gbps">Gbps</option>
										</select> <input type="text" class="form-control"
											name="txtMRC_portType" placeholder="MRC" style="width: 15%"
											required="required"> <input type="text"
											class="form-control" name="txtNRC_portType" placeholder="NRC"
											style="width: 15%" required="required">
										<button type="button" id="btnAddNewPortSpeedDiv"
											name="btnAddNewPortSpeedDiv" class="btn btn-admin-panel">
											<span class="glyphicon glyphicon-plus" id=""
												style="font-size: large;"></span>
										</button>
										<button type="button" id="btnRemoveNewPortSpeedDiv"
											name="btnRemoveNewPortSpeedDiv" class="btn btn-admin-panel"
											style="display: none;">
											<span class="glyphicon glyphicon-minus" id=""
												style="font-size: large;"></span>
										</button>
									</div>
								</div>
								<div class="row">
									<div class="marginTopBuffer col-sm-2"></div>
									<div class="marginTopBuffer col-sm-10">
										<input type="button" class="btn btn-admin-panel" id="btnAddProductConfigData"
											name="btnAddProductConfigData" value="Add Product">
									</div>
								</div>
							</form>
						</div>
						<div role="tabpanel" class="tab-pane fade" id="deleteProducts"
							aria-labelledby="deleteProducts-tab">
							<p>Development In Progress</p>
						</div>

					</div>
				</div>
			</div>
		</div>

		<div class="row sachbottommenu">
			<div class="col-sm-11 sachfootermenuitem" id="divFooterMessage">
				Need to apply an access and port speed to all sites before moving on</div>
			<div class="col-sm-1 sachfootermenuitem">
				<a href="#" id="open-Chat"></a>
			</div>
		</div>
		<div class="chat-box" id="chat_div" style="display: none;"><jsp:include
				page="../chatBox.jsp" /></div>
		<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
			data-target="#successModal" id="btnSuccessModal"
			style="display: none;">Open Modal</button>
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
