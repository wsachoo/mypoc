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
								
								/*'<input type="button" value="Add Options" id="btnAddOptions" name="btnAddOptions" class="btn btn-primary">'+*/
								'<input type="button" value="Remove Feature" id="btnRemoveFeature" name="btnRemoveFeature" class="btn btn-primary">'+
								'<br>'+
								'<input type="button" value="Add Label" id="btnAddLabel" name="btnAddLabel" class="btn btn-primary">'+
								'<input type="button" value="Add Options" id="btnAddOptions" name="btnAddOptions" class="btn btn-primary">'+
								'<br>'+
								'<label for="name" id="" class="">Add Label name:</label>'+
								'<input type="text" name="labelName" class="form-control" style="width:30%;" id="txtLabelName">'+
								/*'<select name="optionType" id="optionType" class=""><option value="featureType">choose type of Add-Ons for the associated Feature</option><option value="checkbox">Multiple-AddOns</option>'+
								'<option value="radio-button">Single-AddOn</option>'+
								'</select>'+*/
								'<br>'+
								'<label for="name" class="" name="divFieldLabel" id="divFieldLabel">Add Field name:</label>'+
								'<input type="text" name="fieldName" class="form-control" style="width:30%;" id="txtFieldName">'+
								'<select name="optionType" id="optionType" class=""><option value="featureType">choose type of Add-Ons for the associated Feature</option><option value="checkbox">Multiple-AddOns</option>'+
								'<option value="radio-button">Single-AddOn</option>'+
								'</select>'+
								'</div>';
		$("#addServices").append(addFeaturesDiv);
	}else{
		
		var tempOptionDiv = "divAddOptions_"+ optionIndex++ ;
		var addOptionsDiv    =  '<div class="row  col-sm-12 col-sm-offset-2" id='+ tempOptionDiv+'>'+
								'<label for="name" class="">Option name:<input type="text" name="txtAddOption" id="txtAddOption" class="form-control"></label>'+
								/*'<input type="button" value="Remove Option" id="btnRemoveOption" name="btnRemoveOption" class="btn btn-primary">'+*/
								'<button type="button" id="btnRemoveOption" name="btnRemoveOption" class="btn">'+
									'<span class="glyphicon glyphicon-minus" name="btnRemoveOption" id="btnRemoveOptionSpan"></span>'+
								'</button>'+
								/*'<input type="button" value="Add Option" id="btnAddOptions" name="btnAddOptions" class="btn btn-primary">'+*/
								'<button type="button" id="btnAddOptions" name="btnAddOptions" class="btn">'+
									'<span class="glyphicon glyphicon-plus" name="btnAddOptions"></span>'+
								'</button>'+
								'<br>'+
									'<div style="display:none; left=-212px;" class="col-sm-12 addOptionsInternal">'+
									'<label for="name" id="" class="">Add Label name:</label>'+
									'<input type="text" name="labelName" class="form-control" style="width:30%;" id="txtLabelName">'+
									/*'<select name="optionType" id="optionType" class=""><option value="featureType">choose type of Add-Ons for the associated Feature</option><option value="checkbox">Multiple-AddOns</option>'+
									'<option value="radio-button">Single-AddOn</option>'+
									'</select>'+*/
									'<br>'+
									'<label for="name" class="" name="divFieldLabel" id="divFieldLabel">Add Field name:</label>'+
									'<input type="text" name="fieldName" class="form-control"  style="width:30%;" id="txtAddOptionFieldName">'+
									'<select name="optionType" id="optionType" class=""><option value="featureType">choose type of Add-Ons for the associated Feature</option><option value="checkbox">Multiple-AddOns</option>'+
									'<option value="radio-button">Single-AddOn</option>'+
									'</select>'+
									'<input type="button" value="Remove Label" id="btnRemoveLabel" name="btnRemoveLabel" style="display:none;" class="btn btn-primary">'+
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
		if(previousDiv.is('input')){
			var thisFeatureDiv = previousDiv.parent();
			thisFeatureDiv.find('input[name="labelName"]').first().val(labelValue);
			thisFeatureDiv.find('input[name="fieldName"]').first().val(fieldValue);
			thisFeatureDiv.find('select[name="optionType"]').first().val(chooseType);
			
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
		divElement.css('left', '-212px' );
		divElement.find('input[name="btnRemoveLabel"]').css('display','inline');
	}
	$(eventSource).siblings('input[name="btnAddOptions"]').css('display','inline');
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
				var featureName = $(value).find("input[name='serviceName']").val();
				addUserServFeaturesObj.children.values[key] = {};
				addUserServFeaturesObj.children.values[key].id = featureName.toLowerCase(); 
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
							addUserServFeaturesObj.children.values[key].children[tempIndex].options[temOptionsArrayValue.toLowerCase()] = temOptionsArrayValue;
							tempOptionIndex = temOptionsArrayKey;
						});
						var optionNameValAsKey = optionNameVal.toLowerCase();
						optionNameValAsKey = optionNameValAsKey.replace(/\s/g, '');
						addUserServFeaturesObj.children.values[key].children[tempIndex].options[optionNameValAsKey] = optionNameVal;
						tempIndex++;
						tempOptionsArray = [];
					}else{
						var optionNameVal = $(optionValue).find("input[type=text]").val();
						var optionNameAsKey = optionNameVal.toLowerCase();
						optionNameAsKey = optionNameAsKey.replace(/\s/g, '');
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

function handleDeleteAdminUserServFeaturesObj($thisRef, eventSource) {
	
	var serviceToBeDeleted = { "id" : $("#labelDeleteService").val() };

	var deleteAdminUserServFeaturesUrl = SALESEXPRESS_CONSTANTS.getUrlPath('deleteAdminServiceFeaturesUrl');
	var promise = httpAsyncPostWithJsonRequestResponse(deleteAdminUserServFeaturesUrl, JSON.stringify(serviceToBeDeleted));
	promise.done(function(data, textStatus, jqXHR) {
		alert("Deleted successfully.");
	}).fail(function(jqXHR, textStatus, errorThrown) {
		alert("Failed to delete.");
	});
}

