<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
							<form id="configureForm" data-ajax="false" class="form-horizontal">
								<div class="row">
									<div class="col-sm-12">
										<label id="labelModifyProductComponent">Modify Exiting Component</label>
									</div>
								</div>
								<div class="row">
									<div class="marginTopBuffer col-sm-2">
										Access Type
									</div>
									<div class="marginTopBuffer col-sm-2">
										<select name="accessType" id="accessTypeManageComponentPage" required="required"
											class="form-control">
											<option value="">Select Access Type</option>
											<option value="ETHERNET">ETHERNET</option>
											<option value="IP">IP</option>
										</select>
									</div>
								</div>
								<div class="row">
									<div class="marginTopBuffer col-sm-2">
										Access Speed
									</div>
									<div class="marginTopBuffer col-sm-2">
										<select class="form-control" id="selAccessSpeedManageComponentPage" disabled="disabled" required="required" 
											name="txtAccessSpeed"  required="required"> 
											<option value="">Select Access Speed</option>
										</select>											
									</div>
<!-- 									<div class="marginTopBuffer col-sm-1">
									<select name="speedUnit_accessType"  disabled="disabled"
											id="speedUnit_accessType" class="form-control">
											<option value="bps">bps</option>
											<option value="Kbps">Kbps</option>
											<option value="Mbps">Mbps</option>
											<option value="Gbps">Gbps</option>
										</select>
									</div>
 -->								</div>
								<div class="row classPortSpeed" id="divPortSpeed">
									<div class="marginTopBuffer col-sm-2">
										Port Speed
									</div>
									<div class="marginTopBuffer col-sm-2">
										<select class="form-control" name="txtSpeed_portType" id="selPortSpeedManageComponentPage" disabled="disabled" 
											required="required"> 
											<option value="">Select Port Speed</option>
										</select>									
									</div>
<!-- 									<div class="marginTopBuffer col-sm-1">
										<select disabled="disabled"
											name="speedUnit_portType" class="form-control">
											<option value="bps">bps</option>
											<option value="Kbps">Kbps</option>
											<option value="Mbps">Mbps</option>
											<option value="Gbps">Gbps</option>
										</select>
									</div>
 -->									<div class="marginTopBuffer col-sm-2">
										<input type="text" class="form-control"
											name="txtMRC_portType" placeholder="MRC"
											required="required"> 
									</div>
									<div class="marginTopBuffer col-sm-2">
										<input type="text"
											class="form-control" name="txtNRC_portType" placeholder="NRC"
											required="required">
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
									<c:forEach items="${productList}" var="item">
										<div class="marginTopBuffer col-sm-1 col-sm-offset-2">
											<input type="checkbox" name="product" id="chk${item}Product"
												value="${item}">${item}
										</div>
										<div class="clearfix"></div> 
									</c:forEach>
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
										<!-- <input type="button" class="btn btn-admin-panel btn-block" disabled="disabled"
										id="btnUpdateProductConfiguration" name="btnUpdateProductConfiguration" value="Configure Product"> -->
										<input type="button" class="btn btn-admin-panel btn-block" data-toggle="modal"  id="btnConfirmUpdateProduct" disabled="disabled"
											name="btnConfirmUpdateProduct" value="Update Configurations">
									</div>
								</div>
							</form>
							
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
							
							<div class="modal fade" id="confirmModal" role="dialog">
							   	<div class="modal-dialog">
							      <!-- Modal content-->
							      <div class="modal-content" id="confirmUpdateProductDiv">
							        <div class="modal-body" style="text-align:center;">
							          <p></p>
							        </div>
							        <div class="modal-footer" style="text-align:center;">
							          <button type="button" class="btn btn-danger" id="btnUpdateProductConfiguration" name="btnUpdateProductConfiguration" data-dismiss="modal">Update</button>
							          <button type="button" class="btn btn-admin-panel" data-dismiss="modal">Cancel</button>
							        </div>
							      </div>
							   	</div>
							  </div>
