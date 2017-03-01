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
			}
		}
		
	});
	
	$("ul.nav-tabs a").click(function (e) {
		  e.preventDefault();
		  $(this).tab('show');
		});
});


function handleAddServicesAndFeatures($thisRef, eventSource){
	/*var isAdditionalServices = $(eventSource).val();
	if ('Add' === isAdditionalServices) {
		var initServicesTemplate = $.tmpl("init_add_services_template");
		var lastDiv = findLastDivRowOfElement($thisRef);
		lastDiv.after(initServicesTemplate);
	} else {
		removeNextAllSiblingDivRows($(eventSource));
	}*/	
	
	$("#addServices").css('display','inline');
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
	
	if(eventSource.name == 'btnAddFeatures'){
	 tempFeatureDiv = "divAddFeatures_"+ featureIndex++ ;
		var addFeaturesDiv   = 	'<div class="col-sm-12" id='+tempFeatureDiv +'>'+
								'<label for="name">Add Feature name:<input type="text" name="serviceName" id="serviceName" value="" class="form-control"></label>'+
								'<select name="optionType" id="optionType"><option value="featureType">choose type of Add-Ons for the associated Feature</option><option value="checkbox">Multiple-AddOns</option>'+
								'<option value="radio-button">Single-AddOn</option>'+
								'</select>'+
								'<input type="button" value="Add Options" id="btnAddOptions" name="btnAddOptions" class="btn btn-primary">'+
								'<input type="button" value="Remove Feature" id="btnRemoveFeature" name="btnRemoveFeature" class="btn btn-primary">'+
								'<br>'+
								'<input type="button" value="Add Label" id="btnAddLabel" name="btnAddLabel" class="btn btn-primary">'+
								'<br>'+
								'<label for="name" id="" class="col-sm-offset-1">Add Label name:</label>'+
								'<input type="text" name="labelName" class="form-control col-sm-offset-1" style="width:30%;" id="txtLabelName">'+
								'<label for="name" class="col-sm-offset-1" name="divFieldLabel" id="divFieldLabel">Add Field name:</label>'+
								'<input type="text" name="fieldName" class="form-control col-sm-offset-1" style="width:30%;" id="txtFieldName">'+
								'</div>';
		$("#addServices").append(addFeaturesDiv);
	}else{
		
		var tempOptionDiv = "divAddOptions_"+ optionIndex++ ;
		var addOptionsDiv    =  '<div class="row  col-sm-12 col-sm-offset-2" id='+ tempOptionDiv+'>'+
								'<label for="name" class="">Option name:<input type="text" name="txtAddOption" id="txtAddOption" class="form-control"></label>'+
								'<input type="button" value="Remove Option" id="btnRemoveOption" name="btnRemoveOption" class="btn btn-primary">'+
								/*'<input type="button" value="Save Option" id="btnSaveOption" name="btnSaveOption" class="btn btn-primary">'+*/
								'<br>'+
								'<div style="display:none; left=-90px;" class="col-sm-12">'+
								'<label for="name" class="" id="divOptionsLabel">Add Label name:</label>'+
								'<input type="text" class=" form-control" name="labelName" id="txtAddOptionLabelName">'+
								'<label for="name" class="" name="divFieldLabel" id="divFieldLabel">Add Field name:</label>'+
								'<input type="text" name="fieldName" class="form-control"  id="txtAddOptionFieldName">'+
								'<input type="button" value="Remove Label" id="btnRemoveLabel" name="btnRemoveLabel" style="display:none;" class="btn btn-primary">'+
								'</div>'+
								'</div>';
		
		var btnAddFeatureDivId = $(eventSource).parent().attr('id');
		//var btnAddFeatureDiv = $(eventSource).parent();
		var lastElemAddOptionStyle =  $("#"+btnAddFeatureDivId).children().last().find('div'); 
				
		if(lastElemAddOptionStyle.css('display') == 'inline' || lastElemAddOptionStyle.css('display') == 'block'){
			lastElemAddOptionStyle.find('input[name="btnRemoveLabel"]').css('display', 'none');
		}
		$("#"+btnAddFeatureDivId).append(addOptionsDiv);
		
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
		if(previousDiv.is('input')){
			var labelValue = closestDiv.find('div').find('input[name="labelName"]').val();
			/*previousDiv.val(labelValue);*/
			previousDiv.prev().prev().val(labelValue);
			var fieldValue = closestDiv.find('div').find('input[name="fieldName"]').val();
			previousDiv.val(fieldValue);
		}else{
			var divPrevLabelName = previousDiv.find('div');
			previousDiv.find('div').find('input[name="labelName"]').val(labelValue);
			divPrevLabelName.css('display','inline');
			previousDiv.find('div').find('input[name="fieldName"]').val(fieldValue);
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
	var divElement = $("#"+currentDiv+" "+"input[name='btnRemoveOption']:last").closest('div').find('div');
	var divElementDisplayStyle = divElement.css('display');
	if(divElementDisplayStyle == 'none'){
		divElement.css('display', 'inline');
		divElement.css('left', '-128px' );
		divElement.find('input[name="btnRemoveLabel"]').css('display','inline');
	}
}

function handleRemoveLabel($thisRef, eventSource) {
	$(eventSource).closest('div').css('display','none');
	
}

function handleBtnSaveService($thisRef, eventSource) {
	
	 var serviceDiv = $("#addServices");
	 
	 var serviceName = $("#addService").val();
		addUserServFeaturesObj.id = serviceName.toLowerCase();
		addUserServFeaturesObj.displayValue = serviceName;
		addUserServFeaturesObj.children = {};
		addUserServFeaturesObj.children.id =  serviceName.toLowerCase() + "Service";
		addUserServFeaturesObj.children.label = $("#addService").val();
		addUserServFeaturesObj.children.values = [];
		
		var featureDivArray = serviceDiv.children('div');
		$.each(featureDivArray, function(key, value) {
				var featureName = $(value).find("input[type='text']").val();
				addUserServFeaturesObj.children.values[key] = {};
				addUserServFeaturesObj.children.values[key].id = featureName.toLowerCase(); 
				addUserServFeaturesObj.children.values[key].displayValue = featureName;
				addUserServFeaturesObj.children.values[key].children = [];
				
				
				var optionsDivArray = $(value).children('div');
				var tempIndex = 0;
				var tempOptionsArray = [];
				var labelName;
				var fieldName;
				$.each(optionsDivArray, function(optionKey, optionValue) {
					
					if(tempIndex == 0){
						labelName = $(value).find("input[name='labelName']").val();
						fieldName = $(value).find("input[name='fieldName']").val();
					}
					/*if(tempIndex > 0 && ($(optionValue).prev('div').css('display') == 'block'))*/
					if(tempIndex > 0 && ($(optionValue).prev().find('div').css('display') == 'block')){
						labelName = $(optionValue).prev('div').find("input[name ='labelName']").val();
						fieldName = $(optionValue).prev('div').find("input[name ='fieldName']").val();
					}
					
					if( $(optionValue).children('div').css('display') != 'none' ||  $(optionValue).next().length == 0){
						
						addUserServFeaturesObj.children.values[key].children[tempIndex]={};
						addUserServFeaturesObj.children.values[key].children[tempIndex].label = labelName;
						addUserServFeaturesObj.children.values[key].children[tempIndex].type = $(value).find('select[name="optionType"]').val();
						addUserServFeaturesObj.children.values[key].children[tempIndex].name = fieldName;
						addUserServFeaturesObj.children.values[key].children[tempIndex].options = {};
						var optionNameVal = $(optionValue).find("input[type=text]").val();
						var optionNameAsKey = optionNameVal.toLowerCase();
						var tempOptionIndex;
						$.each(tempOptionsArray, function(temOptionsArrayKey, temOptionsArrayValue){
							addUserServFeaturesObj.children.values[key].children[tempIndex].options[temOptionsArrayValue.toLowerCase()] = temOptionsArrayValue;
							tempOptionIndex = temOptionsArrayKey;
						});
						addUserServFeaturesObj.children.values[key].children[tempIndex].options[optionNameVal.toLowerCase()] = optionNameVal;
						tempIndex++;
						tempOptionsArray = [];
					}else{
						var optionNameVal = $(optionValue).find("input[type=text]").val();
						var optionNameAsKey = optionNameVal.toLowerCase();
						tempOptionsArray.push(optionNameAsKey); 
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
		alert("Updated successfully.");
	}).fail(function(jqXHR, textStatus, errorThrown) {
		alert("Failed to update.");
	});
	console.log("after adding addUserServFeaturesObj");
}

function handleDeleteAdminUserServFeaturesObj($thisRef, eventSource){
	
	var serviceToBeDeleted = { "id" : $("#labelDeleteService").val() };

	var deleteAdminUserServFeaturesUrl = SALESEXPRESS_CONSTANTS.getUrlPath('deleteAdminServiceFeaturesUrl');
	var promise = httpAsyncPostWithJsonRequestResponse(deleteAdminUserServFeaturesUrl, JSON.stringify(serviceToBeDeleted));
	promise.done(function(data, textStatus, jqXHR) {
		alert("Deleted successfully.");
	}).fail(function(jqXHR, textStatus, errorThrown) {
		alert("Failed to delete.");
	});
}

