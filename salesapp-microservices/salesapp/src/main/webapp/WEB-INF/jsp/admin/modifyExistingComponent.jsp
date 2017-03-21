<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
							<form id="configureForm" data-ajax="false" class="form-horizontal">
								<div class="row">
									<div class="col-sm-12">
										<label id="labelModifyProductComponent">Modify Exiting Component</label>
									</div>
								</div>
								<div class="row">
									<div class="marginTopBuffer col-sm-2">
										Access Speed
									</div>
									<div class="marginTopBuffer col-sm-2">
										<input class="form-control" id="txtAccessSpeed"
											name="txtAccessSpeed" type="number" required="required"
											step="any"> 
									</div>
									<div class="marginTopBuffer col-sm-1">
									<select name="speedUnit_accessType"
											id="speedUnit_accessType" class="form-control">
											<option value="bps">bps</option>
											<option value="Kbps">Kbps</option>
											<option value="Mbps">Mbps</option>
											<option value="Gbps">Gbps</option>
										</select>
									</div>
								</div>
								<div class="row">
									<div class="marginTopBuffer col-sm-2">
										Access Type
									</div>
									<div class="marginTopBuffer col-sm-2">
										<select name="accessType" id="accessType"
											class="form-control">
											<option value="">Select Access Type</option>
											<option value="ETHERNET">ETHERNET</option>
											<option value="IP">IP</option>
										</select>
									</div>
								</div>
								<div class="row classPortSpeed" id="divPortSpeed">
									<div class="marginTopBuffer col-sm-2">
										Port Speed
									</div>
									<div class="marginTopBuffer col-sm-2">
										<input class="form-control" name="txtSpeed_portType"
											type="number" required="required" step="any"> 
									</div>
									<div class="marginTopBuffer col-sm-1">
										<select
											name="speedUnit_portType" class="form-control">
											<option value="bps">bps</option>
											<option value="Kbps">Kbps</option>
											<option value="Mbps">Mbps</option>
											<option value="Gbps">Gbps</option>
										</select>
									</div>
									<div class="marginTopBuffer col-sm-2">
										<input type="text" class="form-control"
											name="txtMRC_portType" placeholder="MRC" 
											required="required"> 
									</div>
									<div class="marginTopBuffer col-sm-2">
										<input type="text"
											class="form-control" name="txtNRC_portType" placeholder="NRC"
											required="required">
									</div>
									<div class="marginTopBuffer col-sm-1">
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
								<div class="row">
									<div class="marginTopBuffer col-sm-4">
										Select all the applicable Products from the list
										below, where you would like these
										configurations to be added to:
									</div>
								</div>
								<div class="row" id="productsDiv">
									<div class="marginTopBuffer col-sm-2">
									</div>
									<c:forEach items="${productList}" var="item">
										<div class="marginTopBuffer col-sm-1">
											<input type="checkbox" name="product" id="chk${item}Product"
												value="${item}">${item}
										</div>
									</c:forEach>
									
									<div class="clearfix"></div> 
								</div>
								<div class="row">
									<div class="col-sm-12">
										<hr class="admin_hr_class">
									</div>
								</div>								
								<div class="row">
									<div class="marginTopBuffer col-sm-1"></div>
									<div class="marginTopBuffer col-sm-1">
										<input type="button" class="btn btn-admin-panel btn-block" id="btnResetModifyProductConfigData"
											name="btnResetModifyProductConfigData" value="Cancel">									
									</div>
									<div class="marginTopBuffer col-sm-2">
										<input type="button" class="btn btn-admin-panel btn-block" id="btnSaveProductConfigData" name="btnSaveProductConfigData" value="Configure Product">
									</div>
								</div>
							</form>
