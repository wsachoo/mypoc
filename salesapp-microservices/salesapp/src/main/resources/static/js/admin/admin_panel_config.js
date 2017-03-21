var featureIndex = 1;
var optionIndex = 1;
var addUserServFeaturesObj = {};

$(document).ready(function() {
	$("#accessSpeedConfigPlaceholder").on({
		"click" : function(e) {
			var eventSourceName = e.target.name;
			
			switch (eventSourceName) {
				case 'btnAddFeatures':
					handleBtnAddFeatures($(this), e.target);
				 	break;
				case 'btnRemoveFeature':
					handleBtnRemoveFeature($(this), e.target);
					break;
				case 'btnAddOptions':
					handleBtnAddFeatures($(this), e.target);
					/*handleBtnAddOptions($(this), e.target);*/
				 	break;
				case 'btnRemoveOption':
					handleBtnRemoveFeature($(this), e.target);
					break;
				case 'btnAddLabel':
					handleAddLabel($(this), e.target);
					break;
				case 'btnSaveService':
					handleBtnSaveService($(this), e.target);
					break;
				case 'addAdminActivity':
					handleAddServicesAndFeatures($(this), e.target);
					break;
				case 'btnRemoveLabel':
					handleRemoveLabel($(this), e.target);
					break;
				case 'btnDeleteService':
					handleDeleteAdminUserServFeaturesObj($(this), e.target);
					break;
				case 'btnSaveProductConfigData':
					handleSaveProductConfigData($(this), e.target);
					break;
				case 'btnAddPortSpeedDiv':
					handleAddPortSpeedDiv($(this), e.target);
					break;
				case 'btnRemovePortSpeedDiv':
					handleRemovePortSpeedDiv($(this), e.target);
					break;	
				case 'btnAddNewPortSpeedDiv':
					handleAddNewPortSpeedDiv($(this), e.target);
					break;
				case 'btnRemoveNewPortSpeedDiv':
					handleRemoveNewPortSpeedDiv($(this), e.target);
					break;	
				case 'btnAddProductConfigData':
					handleAddProductConfigData($(this), e.target);
					break;
				case 'btnDeleteProductConfigData':
					var productListRefreshNeeded = handleDeleteProductConfigData($(this), e.target);
					
					if (productListRefreshNeeded) {
						handleDeleteProductTab();
						handleProductDelComponentPageChange();
					}
					break;
				case 'btnAddPortSpeedDivDeleteProduct':
					handleAddPortSpeedDivDeleteProduct($(this), e.target);
					break;
				case 'btnRemovePortSpeedDivDeleteProduct':
					handleRemovePortSpeedDivDeleteProduct($(this), e.target);
					break;
				case 'btnContinueDeleteService':
					handleBtnContinueDeleteService();
					break;
				case 'btnContinueDisplayService':
					handleContinueDisplayServices();
				case 'btnDelComponentContinue':
					handleBtnDelComponentContinue($(this), e.target);
					break;
				case 'btnResetModifyProductConfigData':
					try { $('#configureNewComponentForm')[0].reset(); } catch(ex) {}
					try { $('#modifyExistingComponentwForm')[0].reset(); } catch(ex) {}
					break;
			}
		},
		
		"change" : function(e) {
			var eventSourceId = e.target.id;
			
			switch (eventSourceId) {
				case 'productDelComponentPage':
					handleProductDelComponentPageChange($(this), e.target);
				 	break;
				case 'accessTypeDelComponentPage':
					handleAccessTypeDelComponentPage($(this), e.target);
					break;
				case 'selAccessSpeedDelComponentPage':
					handleSelAccessSpeedDelComponentPageChange($(this), e.target);
					break;
				case 'selPortSpeedDelComponentPage':
					var portSpeed = $("#deleteForm #selPortSpeedDelComponentPage").val();
					if (portSpeed == "") {
						$("#deleteForm #btnDeleteProductConfigData").attr("disabled", true);
					}
					else {
						$("#deleteForm #btnDeleteProductConfigData").attr("disabled", false);
					}
					break;
			}
		}
		
	});
	
	$("#btnResetAddProductConfigData").on(
			"click", function (e) {
				$('#addForm')[0].reset();
			}
	);

	$("#btnResetDeleteProductConfigData").on(
			"click", function (e) {
				$('#deleteForm')[0].reset();
			}
	);	

	  $(document).on('show.bs.tab', '.nav-tabs-responsive [data-toggle="tab"]', function(e) {
		    var $target = $(e.target);
		    if($target.attr('id') == 'configureProducts-tab') {
		    	handleConfigureProductTab();
		    }
	  		if($target.attr('id') == 'deleteProducts-tab') {
	  			handleDeleteProductTab();
	  		}
	  		if($target.attr('id') == 'deleteServices-tab') {
	  			showDeleteServicesDropDown();
	  		}
	  		
		    var $tabs = $target.closest('.nav-tabs-responsive');
		    var $current = $target.closest('li');
		    var $parent = $current.closest('li.dropdown');
		    	$current = $parent.length > 0 ? $parent : $current;
		    var $next = $current.next();
		    var $prev = $current.prev();
		    var updateDropdownMenu = function($el, position){
		      $el
		      	.find('.dropdown-menu')
		        .removeClass('pull-xs-left pull-xs-center pull-xs-right')
		      	.addClass( 'pull-xs-' + position );
		    };

		    $tabs.find('>li').removeClass('next prev');
		    $prev.addClass('prev');
		    $next.addClass('next');
		    
		    updateDropdownMenu( $prev, 'left' );
		    updateDropdownMenu( $current, 'center' );
		    updateDropdownMenu( $next, 'right' );
		  });
	  handleConfigureProductTab();
});


function handleAddServicesAndFeatures($thisRef, eventSource){
	$("#addServiceFeatures").css('display','inline');
}

function removeNextAllSiblingDivRows($triggerElement) {
	var $closestDiv = $triggerElement.closest("div.row");
	$closestDiv.nextAll('div').not('.sachbottommenu,.chat-box, #chat_window_1,.msg_container').remove();
}

function findLastDivRowOfElement($thisRef) {
	return $thisRef.find("div.row:not('.sachbottommenu, .chat-box, #chat_window_1, .msg_container'):last");
}

var tempFeatureDiv;				

function handleBtnAddFeatures($thisRef, eventSource){
	$("#btnSaveService").css('display','inline');
	if(eventSource.name == 'btnAddFeatures'){
	 tempFeatureDiv = "divAddFeatures_"+ featureIndex++ ;
		var addFeaturesDiv   = 	'<div class="col-sm-12 addFeaturesClass" id='+tempFeatureDiv +'>'+
								'<label for="name">Feature Name:<input type="text" name="serviceName" id="serviceName" value="" class="form-control" required="required"></label>'+
								
								/*'<input type="button" value="Add Options" id="btnAddOptions" name="btnAddOptions" class="btn">'+*/
								'<input type="button" value="[x] remove" id="btnRemoveFeature" name="btnRemoveFeature" class="btn btn-admin-panel" style="float:right;">'+
								'<br>'+
								'<input type="button" value="+ add new Labels" id="btnAddLabel" name="btnAddLabel" class="btn btn-admin-panel">'+
								'<input type="button" value="Add Options" id="btnAddOptions" name="btnAddOptions" class="btn btn-admin-panel">'+
								'<br>'+
								'<label for="name" id="" class="col-sm-offset-1">Label Name:</label>'+
								'<input type="text" name="labelName" class="form-control" id="txtLabelName" required="required">'+
								/*'<select name="optionType" id="optionType" class=""><option value="featureType">choose type of Add-Ons for the associated Feature</option><option value="checkbox">Multiple-AddOns</option>'+
								'<option value="radio-button">Single-AddOn</option>'+
								'</select>'+*/
								'<br>'+
								'<label for="name" class="col-sm-offset-1" name="divFieldLabel" id="divFieldLabel">Field Name:</label>'+
								'<input type="text" name="fieldName" class="form-control" id="txtFieldName" required="required">'+
								'<select name="optionType" id="optionType" class="selectType"><option value="featureType">Select type of Add-Ons </option><option value="checkbox">Multiple-AddOns</option>'+
								'<option value="radio-button">Single-AddOn</option>'+
								'</select>'+
								'</div>';
		$("#addServiceFeatures").append(addFeaturesDiv);
	}else{
		
		var tempOptionDiv = "divAddOptions_"+ optionIndex++ ;
		var addOptionsDiv    =  '<div class="row col-sm-offset-2" id='+ tempOptionDiv+'>'+
								'<label for="name" class="">Option Name:<input type="text" name="txtAddOption" id="txtAddOption" class="form-control" required="required"></label>'+
								/*'<input type="button" value="Remove Option" id="btnRemoveOption" name="btnRemoveOption" class="btn">'+*/
								'<button type="button" id="btnRemoveOption" name="btnRemoveOption" class="btn btn-admin-panel">'+
									'<span class="glyphicon glyphicon-minus" name="btnRemoveOption" id=""></span>'+
								'</button>'+
								/*'<input type="button" value="Add Option" id="btnAddOptions" name="btnAddOptions" class="btn">'+*/
								'<button type="button" id="btnAddOptions" name="btnAddOptions" class="btn btn-admin-panel">'+
									'<span class="glyphicon glyphicon-plus" name="btnAddOptions"></span>'+
								'</button>'+
								'<br>'+
									'<div style="display:none;" class="col-sm-12 addOptionsInternal">'+
									'<label for="name" id="" class="">Label Name:</label>'+
									'<input type="text" name="labelName" class="form-control" style="width:28.25%;" id="txtLabelName" >'+
									'<input type="button" value="Remove Label" id="btnRemoveLabel" name="btnRemoveLabel" style="display:none;" class="btn">'+
									'<br>'+
									'<label for="name" class="" name="divFieldLabel" id="divFieldLabel">Field Name:</label>'+
									'<input type="text" name="fieldName" class="form-control"  style="width:28.25%;" id="txtAddOptionFieldName" >'+
									'<select name="optionType" id="optionType" class="selectType"><option value="featureType">Select type of Add-Ons </option><option value="checkbox">Multiple-AddOns</option>'+
									'<option value="radio-button">Single-AddOn</option>'+
									'</select>'+
									'</div>'+
								'</div>';

		
		var btnAddFeatureDivId = $(eventSource).parent().attr('id');
		var btnAddOptionDivId = $(eventSource).parent().attr('id');
		var lastElemAddOptionStyle =  $("#"+btnAddFeatureDivId).children().last().find('div'); 
				
		if(lastElemAddOptionStyle.css('display') == 'inline' || lastElemAddOptionStyle.css('display') == 'block'){
			lastElemAddOptionStyle.find('input[name="btnRemoveLabel"]').css('display', 'none');
		}
		
		if(btnAddFeatureDivId.match('divAddFeatures')){
			$("#"+btnAddFeatureDivId).append(addOptionsDiv);
			$("#"+btnAddFeatureDivId).find('input[name="btnAddOptions"]').first().css('display', 'none');
		}
		else{
			var labelName;
			var fieldName;
			var chooseType;
			$(addOptionsDiv).insertAfter($("#"+btnAddFeatureDivId));
			if($("#"+btnAddFeatureDivId).find('div').css('display') == 'block' || $("#"+btnAddFeatureDivId).find('div').css('display') == 'inline'){
				labelName = $("#"+btnAddFeatureDivId).find('input[name="labelName"]').val();
				fieldName = $("#"+btnAddFeatureDivId).find('input[name="fieldName"]').val();
				chooseType = $("#"+btnAddFeatureDivId).find('select[name="optionType"]').val();
				$("#"+btnAddFeatureDivId).find('div').css('display','none');
				$("#"+btnAddFeatureDivId).next().find('div').css('display','inline');
				$("#"+btnAddFeatureDivId).next().find('input[name="labelName"]').val(labelName);
				$("#"+btnAddFeatureDivId).next().find('input[name="fieldName"]').val(fieldName);
				$("#"+btnAddFeatureDivId).next().find('select[name="optionType"]').val(chooseType);
			}
		}
	}
}


function handleBtnRemoveFeature($thisRef, eventSource) {
	
	var closestDiv = $(eventSource).closest('div');
	var closestDivId = closestDiv.attr('id');
	var previousDiv = $("#"+ closestDivId).prev();
	eventSource.closest('div').remove();
	
	var childDivStyle = closestDiv.find('div').css('display');
	//copy text from options div label to feature div label
	//when feature level label options are being removed 
	if(previousDiv != undefined && childDivStyle == 'inline'){
		var labelValue = closestDiv.find('div').find('input[name="labelName"]').val();
		var fieldValue = closestDiv.find('div').find('input[name="fieldName"]').val();
		var chooseType = closestDiv.find('div').find('select[name="optionType"]').val();
		if(previousDiv.is('input') || previousDiv.is('select')){
			var thisFeatureDiv = previousDiv.parent();
			thisFeatureDiv.find('input[name="labelName"]').first().val(labelValue);
			thisFeatureDiv.find('input[name="fieldName"]').first().val(fieldValue);
			thisFeatureDiv.find('select[name="optionType"]').first().val(chooseType);
			$("#btnAddOptions").css('display','inline');
		}else{
			previousDiv.find('div').css('display','inline');
			previousDiv.find('div').find('input[name="labelName"]').val(labelValue);
			previousDiv.find('div').find('input[name="fieldName"]').val(fieldValue);
			previousDiv.find('div').find('select[name="optionType"]').val(chooseType);
		}
	}
	if($(eventSource).attr('id') == 'btnRemoveOption'){
		var prevoptionChildDiv = $(previousDiv).find('div');
		var prevoptionChildDivStyle = $(prevoptionChildDiv).css('display');
		if(prevoptionChildDivStyle == 'inline' || prevoptionChildDivStyle == 'block'){
			$(prevoptionChildDiv).find('input[name="btnRemoveLabel"]').css('display','inline');
		}
	}
}

function handleAddLabel($thisRef, eventSource) {
	/*var divElement = $("#configureForm input[name='btnRemoveOption']:last").next();*/
	var currentDiv = $(eventSource).closest('div').attr('id');
	/*var divElement = $("#"+currentDiv+" "+"input[name='btnRemoveOption']:last").closest('div').find('div');*/
	var divElement = $("#"+currentDiv+" "+"button[name='btnRemoveOption']:last").closest('div').find('div');
	var divElementDisplayStyle = divElement.css('display');
	if(divElementDisplayStyle == 'none'){
		divElement.css('display', 'inline');
		divElement.addClass('addOptionsInternal');
		divElement.addClass('addOptionsInternal');
		divElement.find('input[name="btnRemoveLabel"]').css('display','inline');
	}
	$(eventSource).siblings('input[name="btnAddOptions"]').css('display','inline');
}

function handleRemoveLabel($thisRef, eventSource) {
	$(eventSource).closest('div').css('display','none');
}

function handleBtnSaveService($thisRef, eventSource) {
	if(! document.forms.configureForm.reportValidity()) {
		return false;
	}
	 var serviceDiv = $("#addServiceFeatures");
	 var serviceName = $("#addService").val();
		addUserServFeaturesObj.id = serviceName.toLowerCase();
		addUserServFeaturesObj.displayValue = serviceName;
		addUserServFeaturesObj.children = {};
		addUserServFeaturesObj.children.id =  serviceName.toLowerCase() + "Service";
		addUserServFeaturesObj.children.label = $("#addService").val();
		addUserServFeaturesObj.children.values = [];
		
		var featureDivArray = serviceDiv.children('div');
		$.each(featureDivArray, function(key, value) {
				var featureName = $(value).find("input[name='serviceName']").val();
				addUserServFeaturesObj.children.values[key] = {};
				addUserServFeaturesObj.children.values[key].id = featureName.toLowerCase().replace(/\s/g, ''); 
				addUserServFeaturesObj.children.values[key].displayValue = featureName;
				addUserServFeaturesObj.children.values[key].children = [];
				
				var optionsDivArray = $(value).children('div');
				var tempIndex = 0;
				var tempOptionsArray = [];
				var labelName;
				var fieldName;
				var chooseType;
				$.each(optionsDivArray, function(optionKey, optionValue) {
					
					if(tempIndex == 0){
						labelName = $(value).find("input[name='labelName']").val();
						fieldName = $(value).find("input[name='fieldName']").val();
						chooseType = $(value).find("select[name ='optionType']").val();
					}
					/*if(tempIndex > 0 && ($(optionValue).prev('div').css('display') == 'block'))*/
					if(tempIndex > 0 && ($(optionValue).prev().find('div').css('display') == 'block')){
						labelName = $(optionValue).prev('div').find("input[name ='labelName']").val();
						fieldName = $(optionValue).prev('div').find("input[name ='fieldName']").val();
						chooseType = $(optionValue).prev('div').find("select[name ='optionType']").val();
					}
				
					if( $(optionValue).children('div').css('display') != 'none' ||  $(optionValue).next().length == 0){
						addUserServFeaturesObj.children.values[key].children[tempIndex]={};
						addUserServFeaturesObj.children.values[key].children[tempIndex].label = labelName;
						addUserServFeaturesObj.children.values[key].children[tempIndex].type = chooseType;
						addUserServFeaturesObj.children.values[key].children[tempIndex].name = fieldName;
						addUserServFeaturesObj.children.values[key].children[tempIndex].options = {};
						var optionNameVal = $(optionValue).find("input[type=text]").val();
						var optionNameAsKey = optionNameVal.toLowerCase();
						var tempOptionIndex;
						$.each(tempOptionsArray, function(temOptionsArrayKey, temOptionsArrayValue){
							addUserServFeaturesObj.children.values[key].children[tempIndex].options[temOptionsArrayValue.toLowerCase().replace(/\s/g, '')] = temOptionsArrayValue;
							tempOptionIndex = temOptionsArrayKey;
						});
						var optionNameValAsKey = optionNameVal.toLowerCase();
						optionNameValAsKey = optionNameValAsKey.replace(/\s/g, '');
						addUserServFeaturesObj.children.values[key].children[tempIndex].options[optionNameValAsKey] = optionNameVal;
						tempIndex++;
						tempOptionsArray = [];
					}else{
						var optionNameVal = $(optionValue).find("input[type=text]").val();
						tempOptionsArray.push(optionNameVal);
					}
				});	
			});	
		console.log("addUserServFeaturesObj " + JSON.stringify(addUserServFeaturesObj));
		saveAdminUserServFeatures(addUserServFeaturesObj);
}

function saveAdminUserServFeatures(addUserServFeaturesObj) {
	var  addAdminUserAddServFeaturesUrl = SALESEXPRESS_CONSTANTS.getUrlPath('addAdminServiceFeaturesUrl');
	var data = JSON.stringify(addUserServFeaturesObj);
	var promise = httpAsyncPostWithJsonRequestResponse(addAdminUserAddServFeaturesUrl, data);
	promise.done(function(data, textStatus, jqXHR) {
		$("#updateMessage").text('Updated successfully.');
		$("#btnSuccessModal").trigger('click');
	}).fail(function(jqXHR, textStatus, errorThrown) {
		$("#updateMessage").text('Failed To Update.');
		$("#btnSuccessModal").trigger('click');
	});
	console.log("after adding addUserServFeaturesObj");
}

function handleDeleteAdminUserServFeaturesObj($thisRef, eventSource) {
	var serviceToBeDeleted = { "id" : $("#serviceToDelete").val() };
	var deleteAdminUserServFeaturesUrl = SALESEXPRESS_CONSTANTS.getUrlPath('deleteAdminServiceFeaturesUrl');
	var promise = httpAsyncPostWithJsonRequestResponse(deleteAdminUserServFeaturesUrl, JSON.stringify(serviceToBeDeleted));
	promise.done(function(data, textStatus, jqXHR) {
		$("#updateMessage").text('Deleted successfully.');
		$("#btnSuccessModal").trigger('click');
		showDeleteServicesDropDown();
		//$("#showDeleteService").find().empty();
		$("#showDeleteService span:first-child").empty();
		$("#showDeleteService #showDeleteServiceLabel").empty();
		$("#btnContinueDeleteService").css('display','none');
	}).fail(function(jqXHR, textStatus, errorThrown) {
		$("#updateMessage").text('Failed To Delete.');
		$("#btnSuccessModal").trigger('click');
	});
	
}

function handleSaveProductConfigData($thisRef, eventSource) {
	
	if(! document.forms.configureForm.reportValidity()) {
		return false;
	}
	
	var portSpeeds = [];
	$(".classPortSpeed").each(function() {
	    var portSpeedObj = {};
	    portSpeedObj.speed = $(this).find("input[name='txtSpeed_portType']").val();
	    portSpeedObj.speedUnit = $(this).find("select[name='speedUnit_portType']").val();
	    portSpeedObj.MRC = $(this).find("input[name='txtMRC_portType']").val();
	    portSpeedObj.NRC = $(this).find("input[name='txtNRC_portType']").val();
	    portSpeeds.push(portSpeedObj);
	});
	console.log("portSpeeds : " + JSON.stringify(portSpeeds));
	var productConfigObj = {};
	productConfigObj.accessSpeed = $("#configureForm #txtAccessSpeed").val();
	productConfigObj.accessSpeedUnit = $("#configureForm #speedUnit_accessType").val();	
	productConfigObj.accessType = $("#configureForm #accessType").val();
	productConfigObj.portSpeeds = portSpeeds;
	var products = [];
	$('#configureForm input[name="product"]:checked').each(function() {
		   products.push($(this).val());
		});
	productConfigObj.products = products;
	saveProductConfiguration(productConfigObj);
	console.log("productConfigObj : " + JSON.stringify(productConfigObj));
}

function handleAddProductConfigData($thisRef, eventSource) {
	
	if(! document.forms.addForm.reportValidity()) {
		return false;
	}
	
	var portSpeeds = [];
	$(".classNewPortSpeed").each(function() {
	    var portSpeedObj = {};
	    portSpeedObj.speed = $(this).find("input[name='txtSpeed_portType']").val();
	    portSpeedObj.speedUnit = $(this).find("select[name='speedUnit_portType']").val();
	    portSpeedObj.MRC = $(this).find("input[name='txtMRC_portType']").val();
	    portSpeedObj.NRC = $(this).find("input[name='txtNRC_portType']").val();
	    portSpeeds.push(portSpeedObj);
	});
	console.log("portSpeeds : " + JSON.stringify(portSpeeds));
	var productAddObj = {};
	productAddObj.accessSpeed = $("#addForm #txtAccessSpeed").val();
	productAddObj.accessSpeedUnit = $("#addForm #speedUnit_accessType").val();	
	productAddObj.accessType = $("#addForm #accessType").val();
	productAddObj.portSpeeds = portSpeeds;
	var products = [];
	products.push($("#addForm input[name='product'").val());
	productAddObj.products = products;
	saveProductConfiguration(productAddObj);
	console.log("productAddObj : " + JSON.stringify(productAddObj));
}


function handleAddPortSpeedDiv($thisRef, eventSource) {
	var divAddPortSpeeds = $("#divPortSpeed").wrap('<p/>').parent().html();
	$("#divPortSpeed").unwrap();
	var currentDiv = $(eventSource).closest('div').parent();
	$(divAddPortSpeeds).insertAfter(currentDiv);
	$("button[name='btnRemovePortSpeedDiv']:not(:first)").css('display','inline');
}

function handleAddNewPortSpeedDiv($thisRef, eventSource) {
	var divAddPortSpeeds = $("#divNewPortSpeed").wrap('<p/>').parent().html();
	$("#divNewPortSpeed").unwrap();
	var currentDiv = $(eventSource).closest('div').parent();
	$(divAddPortSpeeds).insertAfter(currentDiv);
	$("button[name='btnRemoveNewPortSpeedDiv']:not(:first)").css('display','inline');
}

function handleRemovePortSpeedDiv($thisRef, eventSource) {
	$(eventSource).closest('div').parent().remove();
}

function handleRemoveNewPortSpeedDiv($thisRef, eventSource) {
	$(eventSource).closest('div').parent().remove();
}


function saveProductConfiguration(productConfigObj) {
	var url = SALESEXPRESS_CONSTANTS.getUrlPath('saveProductConfigurationUrl');
	var data = JSON.stringify(productConfigObj);
	var promise = httpAsyncPostWithJsonRequestResponse(url, data);
	promise.done(function(data, textStatus, jqXHR) {
		$("#updateMessage").text('Updated successfully.');
		$("#btnSuccessModal").trigger('click');
	}).fail(function(jqXHR, textStatus, errorThrown) {
		$("#updateMessage").text('Failed To Update Product Info');
		$("#btnSuccessModal").trigger('click');
	});
}


function handleConfigureProductTab() {
	var url = SALESEXPRESS_CONSTANTS.getUrlPath('getAllProductsUrl');
	var productsList = httpGetWithJsonResponse(url);
	console.log("productsList :" + productsList);
	prepareProductsDiv(productsList);
	return productsList;
}

function prepareProductsDiv(productsList) {
	$("#productsDiv").find('div').not(':first').remove();
	for(var i = 0; i <productsList.length; i++){
		var product = '<div class="marginTopBuffer col-sm-1">'+
						'<input type="checkbox" name="product" id="chk'+productsList[i]+'Product" value="'+productsList[i]+'">'+productsList[i]+
					  '</div>';
		$("#productsDiv").append(product);
	}
	
}

$(document).ready(function() {
    $("#adminPanelTopMenu a").each(function(i, a) {
    	if ("addProducts-tab" == a.id ||  "addServices-tab" == a.id) {
	        $(this).css("background-color","white");
	        $(this).css("color","#337ab7");
	        $(this).css("font-weight","bold");
    	}
    	else {
    		$(this).css("background-color","#337ab7");
    		$(this).css("color","white");
    		$(this).css("font-weight","bold");
    	}
    });

	$("#adminPanelTopMenu a").click(function(e) {
	    $("#adminPanelTopMenu a").each(function(i, a) {
	    	if (e.currentTarget.id == a.id) {
    	        $(this).css("background-color","white");
    	        $(this).css("color","#337ab7");
	    	}
	    	else {
	    		$(this).css("background-color","#337ab7");
	    		$(this).css("color","white");
	    	}
	    });
	});	
});

/*function handleDeleteProductConfigData($thisRef, eventSource) {
	var portSpeeds = [];
	$(".classDeletePortSpeed").each(function() {
	    var portSpeedObj = {};
	    portSpeedObj.speed = $(this).find("input[name='txtSpeed_portType']").val();
	    portSpeedObj.speedUnit = $(this).find("select[name='speedUnit_portType']").val();
	    portSpeeds.push(portSpeedObj);
	});
	console.log("portSpeeds : " + JSON.stringify(portSpeeds));
	var productDeleteObj = {};
	productDeleteObj.accessSpeed = $("#deleteForm #txtAccessSpeed").val();
	productDeleteObj.accessSpeedUnit = $("#deleteForm #speedUnit_accessType").val();	
	productDeleteObj.accessType = $("#deleteForm #accessType").val();
	productDeleteObj.portSpeeds = portSpeeds;
	var products = [];
	$('#deleteForm input[name="product"]:checked').each(function() {
		   products.push($(this).val());
		});
	productDeleteObj.products = products;
	deleteProductConfigData(productDeleteObj);
	console.log("productDeleteObj : " + JSON.stringify(productDeleteObj));
}*/

function handleDeleteProductConfigData($thisRef, eventSource) {
	
	if(! document.forms.deleteForm.reportValidity()) {
		return false;
	}
	
	var productListRefreshNeeded = false;
	var portSpeeds = [];
    var portSpeedObj = {};
    portSpeedObj.speed = $("#deleteForm select[name='selPortSpeed']").val();
    portSpeedObj.speedUnit = "";
    portSpeeds.push(portSpeedObj);

	var productDeleteObj = {};
	productDeleteObj.accessSpeed = $("#deleteForm #selAccessSpeedDelComponentPage").val();
	productDeleteObj.accessSpeedUnit = "";	
	productDeleteObj.accessType = $("#deleteForm #accessTypeDelComponentPage").val();
	productDeleteObj.portSpeeds = portSpeeds;
	
	var products = [];
	products.push($("#deleteForm #productDelComponentPage").val());
	productDeleteObj.products = products;

	var returnVal = deleteProductConfigData(productDeleteObj);
	console.log("Deletion success: " + returnVal);
	
	if (returnVal) { //If deletion is successful and there are no values inside port speed drop down then product refresh is needed.
		var existingProductCount = $("#selPortSpeedDelComponentPage option").length;
		if (existingProductCount == 1) {
			productListRefreshNeeded = true;
		}
	}
	return productListRefreshNeeded; 

}

function handleDeleteProductTab() {
	changeProductLabel();
	/*
	var productsList = handleConfigureProductTab();
	$("#divProductNameToDelete").find('div').not(':first').remove();
	for(var i = 0; i <productsList.length; i++){
		var product = '<div class="col-sm-4">'+
						'<input type="checkbox" name="product" id="chk'+productsList[i]+'Product" value="'+productsList[i]+'">'+productsList[i]+
					  '</div>';
		$("#divProductNameToDelete").append(product);
	}
	*/
	
	var productsList = handleConfigureProductTab();
	console.log("productsList: " + productsList)
	
	$("#deleteForm #productDelComponentPage").find("option:gt(0)").remove();
	
	for(var i = 0; i <productsList.length; i++){
		var itemval= "<option value='" + productsList[i] + "'>" + productsList[i] + "</option>";
		$("#deleteForm #productDelComponentPage").append(itemval);
	}
}

function handleAddPortSpeedDivDeleteProduct($thisRef, eventSource) {
	var divDeleteProducts = $("#divDeleteProducts").wrap('<p/>').parent().html();
	$("#divDeleteProducts").unwrap();
	var currentDiv = $(eventSource).closest('div').parent();
	$(divDeleteProducts).insertAfter(currentDiv);
	$("button[name='btnRemovePortSpeedDivDeleteProduct']:not(:first)").css('display','inline');
}

function handleRemovePortSpeedDivDeleteProduct($thisRef, eventSource) {
	$(eventSource).closest('div').parent().remove();
}

function changeProductLabel() {
	var productForDelete = $("#deleteForm #productDelComponentPage").val();
	productForDelete = productForDelete || "&nbsp;";
	$("#deleteForm #delSelectedProductLabel").html(productForDelete);	
}

function handleBtnDelComponentContinue($thisRef, eventSource) {
	changeProductLabel();
	$("#deleteForm #accessTypeDelComponentPage").attr("disabled", false);
}

function deleteProductConfigData(productDeleteObj) {
	var returnVal = true;
	var url = SALESEXPRESS_CONSTANTS.getUrlPath('deleteProductConfigurationUrl');
	var data = JSON.stringify(productDeleteObj);
	var promise = httpAsyncPostWithJsonRequestResponseSynchronous(url, data);
	promise.done(function(data, textStatus, jqXHR) {
		$("#updateMessage").text('Deleted Successfully');
		$("#btnSuccessModal").trigger('click');
		
		//START: Remove the option from port speed drop down On Success
		$("#deleteForm select[name='selPortSpeed'] option").each(function() {
		    if ($(this).val() == productDeleteObj.portSpeeds[0].speed) {
		        $(this).remove();
		    }
		});
		//END
		returnVal = true;
	}).fail(function(jqXHR, textStatus, errorThrown) {
		$("#updateMessage").text('Failed To Delete Product Info');
		$("#btnSuccessModal").trigger('click');
		returnVal = false;
	});
	
	return returnVal;
}

function handleAccessTypeDelComponentPage($thisRef, eventSource) {
	var productType = $("#deleteForm #productDelComponentPage").val();
	var accessType = $(eventSource).val();

	$("#deleteForm #selAccessSpeedDelComponentPage").find("option:gt(0)").remove();
	$("#deleteForm #selAccessSpeedDelComponentPage").attr("disabled", true);

	$("#deleteForm #selPortSpeedDelComponentPage").find("option:gt(0)").remove();
	$("#deleteForm #selPortSpeedDelComponentPage").attr("disabled", true);
	
	$("#deleteForm #btnDeleteProductConfigData").attr("disabled", true);
	
	if (accessType != "") {
		$("#deleteForm #selAccessSpeedDelComponentPage").find("option:gt(0)").remove();
		$("#deleteForm #selAccessSpeedDelComponentPage").attr("disabled", false);

		var url = SALESEXPRESS_CONSTANTS.getUrlPath('getAccessSpeedByAccessTypeUrl');
		var productsList = httpGetWithJsonResponse(url, {
			"accessType" : accessType,
			"productType" : productType
		});
		
		$.each(productsList, function(k, v) {
			var itemval= "<option value='" + k + "'>" + v + "</option>";
			$("#deleteForm #selAccessSpeedDelComponentPage").append(itemval);
		});
	}
}

function handleProductDelComponentPageChange($thisRef, eventSource) {
	changeProductLabel();
	if (eventSource == undefined || $(eventSource).val() == "") {
		$("#btnDelComponentContinue").attr("disabled", true);
	}
	else {
		$("#btnDelComponentContinue").attr("disabled", false);
	}

	$("#deleteForm #accessTypeDelComponentPage").val("");
	$("#deleteForm #accessTypeDelComponentPage").attr("disabled", true);

	$("#deleteForm #selAccessSpeedDelComponentPage").find("option:gt(0)").remove();
	$("#deleteForm #selAccessSpeedDelComponentPage").attr("disabled", true);

	$("#deleteForm #selPortSpeedDelComponentPage").find("option:gt(0)").remove();
	$("#deleteForm #selPortSpeedDelComponentPage").attr("disabled", true);
	
	$("#deleteForm #btnDeleteProductConfigData").attr("disabled", true);
}

function handleSelAccessSpeedDelComponentPageChange($thisRef, eventSource) {
	var accessSpeed = $(eventSource).val();
	var accessType = $("#deleteForm #accessTypeDelComponentPage").val();
	var productType = $("#deleteForm #productDelComponentPage").val();
	
	$("#deleteForm #btnDeleteProductConfigData").attr("disabled", true);
	$("#deleteForm #selPortSpeedDelComponentPage").find("option:gt(0)").remove();
	
	if (accessSpeed == "") {
		$("#deleteForm #selPortSpeedDelComponentPage").attr("disabled", true);
	}
	else {
		$("#deleteForm #selPortSpeedDelComponentPage").attr("disabled", false);
		
	}

	var url = SALESEXPRESS_CONSTANTS.getUrlPath('getPortSpeedsByAccessSpeedUrl');
	var productsList = httpGetWithJsonResponse(url, {
		"productType" : productType,
		"accessType" : accessType,
		"accessSpeed" : accessSpeed		 
	});
	
	$.each(productsList, function(k, v) {
		var itemval= "<option value='" + k + "'>" + v + "</option>";
		$("#deleteForm #selPortSpeedDelComponentPage").append(itemval);
	});
}

function showDeleteServicesDropDown() {
	 var servFeaturesMDataUrl = SALESEXPRESS_CONSTANTS.getUrlPath('getServiceFeaturesMetaDataUrl');
	 var serviceFeaturesMetaDataForAdmin = httpGetWithJsonResponse(servFeaturesMDataUrl);
	 var services= {};
	 var serviceName;
	 for(var i = 0; i < serviceFeaturesMetaDataForAdmin.serviceAndFeatures.length; i++){
		 serviceName = serviceFeaturesMetaDataForAdmin.serviceAndFeatures[i]["displayValue"]
		 services[serviceName.toLowerCase().replace(/\s/g, '')] = serviceName;
	 }
	 $("#serviceToDelete").find('option').not(':first').remove();
	 $.each(services, function(key, value) {
         var option = $('<option />').prop('value', key).text(value);
         $("#serviceToDelete").append(option);
       });
}

function handleBtnContinueDeleteService() {
	$("#confirmDeleteServiceDiv").first('div').find('p').html("Are you sure you want to delete " + $("#serviceToDelete").val().toUpperCase() + " service and its features ?");
	
}

function handleContinueDisplayServices() {
	$("#showDeleteServiceLabel").empty();
	
	var serviceNameToDelete = $("#serviceToDelete").val();
	if(serviceNameToDelete == 'serviceName'){
		$("#updateMessage").text('Please select a valid Service Name to continue');
		$("#btnSuccessModal").trigger('click');
		$("#btnContinueDeleteService").css('display','none');
		$("#showDeleteService span:first-child").empty();
		return;
	}else if(serviceNameToDelete != 'serviceName'){
		$("#btnContinueDeleteService").css('display','inline');
	}
	var servFeaturesMDataUrl = SALESEXPRESS_CONSTANTS.getUrlPath('getServiceFeaturesMetaDataUrl');
	 var serviceFeaturesMetaDataForAdmin = httpGetWithJsonResponse(servFeaturesMDataUrl);
	 var serviceNameId;
	 $.each(serviceFeaturesMetaDataForAdmin.serviceAndFeatures, function(key, value) {
		 serviceNameId = serviceFeaturesMetaDataForAdmin.serviceAndFeatures[key]["id"];
		 if( serviceNameId != null && serviceNameId == serviceNameToDelete){
			 $("#showDeleteService span:first-child").html('<strong>Service name : </strong>'+value.displayValue.toUpperCase() +'<br>');
			 $("#showDeleteService").find("#showDeleteServiceLabel").append('<hr style="color:black;"/>');
			 var featureTagValues = serviceFeaturesMetaDataForAdmin.serviceAndFeatures[key].children.values;
			 $.each(featureTagValues,function(featureTagKey, featureTagValue){
				 $("#showDeleteService").find("#showDeleteServiceLabel").append('<div class="row">'+
																					'<div class="col-sm-3" style="background-color:lavender;"><span><strong>Features :</strong></span></div>'+
																					'<div class="col-sm-9" style="background-color:lavender;"><span><strong>'+ featureTagValue.displayValue +'</strong></span></div>'+
																				'</div>');
				  var labelTagValues = serviceFeaturesMetaDataForAdmin.serviceAndFeatures[key].children.values[featureTagKey].children;
				 $.each(labelTagValues, function(labelTagKey, labelTagValue){
					 $("#showDeleteService").find("#showDeleteServiceLabel").append('<div class="row">'+
																						'<div class="col-sm-3" style="background-color:lavender;"><span><strong>Label Name :</strong></span></div>'+
																						'<div class="col-sm-9" style="background-color:lavender;"><span>'+ labelTagValue.label +'</span></div>'+
																					'</div>');
					 $("#showDeleteService").find("#showDeleteServiceLabel").append('<div class="row">'+
																						'<div class="col-sm-3" style="background-color:lavender;text-align:center;"><span><strong>Field Name :</strong></span></div>'+
																						'<div class="col-sm-9" style="background-color:lavender;"><span>'+ labelTagValue.name +'<strong>'+' ('+ labelTagValue.type +')'+'</strong></span></div>'+
																					'</div>');
					 var optionTagValues = serviceFeaturesMetaDataForAdmin.serviceAndFeatures[key].children.values[featureTagKey].children[labelTagKey].options;
					 $.each(optionTagValues, function(optionTagKey, optionTagValue){
						 $("#showDeleteService").find("#showDeleteServiceLabel").append('<div class="row">'+
																							'<div class="col-sm-3" style="background-color:lavender;text-align:right;"><span><strong>Option Name :</strong></span></div>'+
																							'<div class="col-sm-9" style="background-color:lavender;"><span>'+ optionTagValue +'</span></div>'+
																						'</div>');
					 });
				 });
				 $("#showDeleteService").find("#showDeleteServiceLabel").append('<hr style="color:black;"/>');
			 });
		 }
	 });
}

$(document).ready(function() {

	$("#hrefConfigureNewComponent").on("click", function () {
        $("#modifyConfigurationContentArea").load(contextPath + "/admin/loadConfigureNewComponentPage");
        $(this).css("background-color", "#337ab7");
        $("#hrefModifyExistingComponent").css("background-color", "white");
        $("#hrefModifyExistingComponent").css("color", "#337ab7");
        $(this).css("color", "white");
    });
	
    $("#hrefModifyExistingComponent").on("click", function () {
    	$("#modifyConfigurationContentArea").load(contextPath + "/admin/loadModifyExistingComponentPage");
    	$(this).css("background-color", "#337ab7");
    	$("#hrefConfigureNewComponent").css("background-color", "white");
    	$("#hrefConfigureNewComponent").css("color", "#337ab7");
    	$(this).css("color", "white");
    });
    
    $("#hrefConfigureNewComponent").trigger('click');
});
