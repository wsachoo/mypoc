var userSolTmplSelectionObject = []; //global object for user selection on step wizard
var dataToGenContract = {}; //global object to hold the data to display the contract wizard
var portSpeedsToCustomizeGCData = {};

var customizeGCDataFields = {
		ipVersionDropDown  : function() {
			return '<select name="ipVersion" class="form-control" style="width:60%;">'+
						'<option value="IP V4/V6 Dual Stack">IP V4/V6 Dual Stack'+
						'<option value="IP V4">IP V4'+
					'</select>';
		},		
		managedRouterDropDown : function() {
			
			return '<select name="managedRouterForGC" class="form-control" style="width:60%;">'+
					'<option value="AT&T Managed Router">AT&T Managed Router'+
					'<option value="Customer Managed Router">Customer Managed Router'+
				   '</select>';
		},
		portSpeedsDropDown : function() {
			return '<select name="portSpeedListForGC" class="form-control" style="width:60%;">'+
					'</select>';
		},
		contractTermDropDowm : function() {
			return '<select name="contractTermForGC" class="form-control" style="width:60%;" onchange="javascript:onChangeTermOnOfferViewPopup()">'+
						'<option value="12 Months">12 Months'+
						'<option value="24 Months">24 Months'+
						'<option value="36 Months">36 Months'+
					'</select>';
		}
	};

function convertOjectArrayToObject(paramObject) {
	var reqObject = {};
	$.each(paramObject, function(i, v) {
	    $.each(v, function(key, val) {
			reqObject[key] = val;
		});
	});	
	
	return reqObject;
}

function displayDataGrid(data, templateFormat) {
	var gridTable = '<table id="grid-data" class="table table-condensed table-hover" style="background-color : #F5F5F5;">'+
	'<thead>'+
	'<tr>'+
	'<th data-column-id="commands" data-formatter="commands" data-sortable="false">View</th>'+
	'<th data-column-id="accessSpeed" >ACCESS SPEED</th>'+
	/*'<th data-column-id="designType">DESIGN TYPE</th>'+*/
	'<th data-column-id="portSpeed">PORT SPEED</th>'+
	'<th data-column-id="bundleCd">PRODUCT</th>'+
	'<th data-column-id="managedRouter">MANAGED ROUTER</th>'+
	'<th data-column-id="mrc">MRC</th>'+
	'<th data-column-id="nrc">NRC</th>'+
	/*'<th data-column-id="ratePlan">RATE PLAN</th>'+*/
	'</tr>'+
	'</thead>'+
	'</table>';
	$("#solutionTemplateBottomFrame").append(gridTable);
	var data = data["DATA"];
	$.each(data, function(k,v) {
		var gridRow = 	'<tr >'+
		'<td data-column-id="commands"></td>'+
		'<td data-column-id="accessSpeed">'+ data[k].accessSpeed +'</td>'+
		//'<td data-column-id="designType" data-order="desc">'+ data[k].designType +'</td>'+
		'<td data-column-id="portSpeed">'+ data[k].portSpeed +'</td>'+
		'<td data-column-id="bundleCd">'+ data[k].bundleCd +'</td>'+
		'<td data-column-id="managedRouter">'+ data[k].managedRouter +'</td>'+
		'<td data-column-id="mrc">'+ '$'+ data[k].mrc +'</td>'+
		'<td data-column-id="nrc">'+'$'+ data[k].nrc +'</td>'+
		//'<td data-column-id="ratePlan">'+ data[k].ratePlan +'</td>'+
		'</tr>'
		$("#grid-data").append(gridRow);			
	});
	var grid = $("#grid-data");
	
	var gridLayoutAttributeObject = {
		selection: true,
		multiSelect: true,
		rowSelect: true,
		keepSelection: true,
		formatters: {
			"commands": function(column, row)
			{
				return "<button type='button' class='btn btn-xs btn-default openLink'><span class='glyphicon glyphicon-pencil'></span></button>";
			}
		}			
	};
	
	if (templateFormat) {
		gridLayoutAttributeObject.templates = templateFormat;
	}

	var gridLayout = $("#grid-data").bootgrid(gridLayoutAttributeObject);
	
	gridLayout.on("loaded.rs.jquery.bootgrid", function(){
		//used openLink class just to select the current button to call the click event
		$("#grid-data").find(".openLink").on("click", function(e) {
			var thisElementId = $(this);
			var rowId = thisElementId.closest('tr').attr('data-row-id'); //data-row-id attr is dynamically created by bootgrid
			var url = data[rowId].links[0].href;
			var matchpercentage = data[rowId].matchPercentage;
			displaySelectedRowModal(url, matchpercentage);
		});

	});	
}

function displayDataGridWithTop5Records(accessType, accessSpeed) {
	var tmpObj = {};
	tmpObj["ACCESS_TYPE_ID"] = accessType;
	tmpObj["NUMBER_OF_ROWS"] = 6;
	tmpObj["ACCESS_SPEED_ID"] = accessSpeed;
	
	var jsonData = JSON.stringify(tmpObj);
	var promise = httpAsyncPostWithJsonRequestResponse(SALESEXPRESS_CONSTANTS.getUrlPath("ZUUL_GATEWAY_RECOMMENDATION_URL"), jsonData);
	promise.done(function(data, textStatus, jqXHR){
		
	$("#solutionTemplateBottomFrame").empty();
		if(data["STATUS"] != null && data["STATUS"] != "" && data["STATUS"] == "SUCCESS" && data["DATA"].length>0){
		/*		var gridTemplateFormat = {
				header : "",
				pagination : ""
			}
			displayDataGrid(data, gridTemplateFormat);*/
			var templatePath = contextPath + "/templates/top_results_template.html";
			var topResultsTemplate = getTemplateDefinition(templatePath);
			$.template("top_results_template", topResultsTemplate);
			var topResultsTemplateToDisplay = $.tmpl("top_results_template", {"data":data});
			
			$("#solutionTemplateBottomFrame").append(topResultsTemplateToDisplay);
		}
		else{
			$("#solutionTemplateBottomFrame").empty();
			$("#solutionTemplateBottomFrame").html(data["ERROR_DESC"])
		}

	}).fail(function(jqXHR, textStatus, errorThrown) {
		console.log(textStatus);
	}); 
	
} 


function showSalesHistoryGrid() {
	var reqObject = convertOjectArrayToObject(userSolTmplSelectionObject);
	//console.log("userSolTmplSelectionObject : from showSalesHistoryGrid" + JSON.stringify(userSolTmplSelectionObject));
	var jsonData = JSON.stringify(reqObject);
	var promise = httpAsyncPostWithJsonRequestResponse(SALESEXPRESS_CONSTANTS.getUrlPath("ZUUL_GATEWAY_RECOMMENDATION_URL"), jsonData);
	promise.done(function(data, textStatus, jqXHR){
		//console.log("Data :" + JSON.stringify(data));
		$("#solutionTemplateBottomFrame").empty();
		if(data["STATUS"] != null && data["STATUS"] != "" && data["STATUS"] == "SUCCESS" && data["DATA"].length>0){
			displayDataGrid(data);
		}
		else{
			$("#solutionTemplateBottomFrame").empty();
			$("#solutionTemplateBottomFrame").html(data["ERROR_DESC"])
		}

	}).fail(function(jqXHR, textStatus, errorThrown) {
		console.log(textStatus);
	}); 
} 

function highlightActiveStep(stepNumber) {
	
	$("#stepWizardId").find(".stepwizard-step").each(function(i) {
		var anchorObject = $(this).find('a');
		if ((i+1) == stepNumber) {
			anchorObject.addClass('btn-primary');
			anchorObject.removeClass('btn-default');
	    }
	    else {
	    	anchorObject.addClass('btn-default');
	    	anchorObject.removeClass('btn-primary');
	    }
	});
}

function initializeStepWizard() {
	var promise = httpAsyncPostWithJsonRequestResponse(contextPath + "/user/solutionTemplate/getAllQuestions", "{}");
	promise.done(function(data, textStatus, jqXHR ) {
		data[0].mydesc = "Access Type";
		data[1].mydesc = "Access Speed";
		data[2].mydesc = "Port Speed";
		data[3].mydesc = "Final Result";
		var stepWizardData = $("#stepwizard-step-template").tmpl(data);
		stepWizardData.appendTo("#stepWizardId");
		
		var userSelectCriteriaLength = Object.keys(userSolTmplSelectionObject).length;
		populateWizardElement(data[userSelectCriteriaLength].quesSeqId);
		
		$("input[name='numberOfQuestion']").val(data.length);
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		console.log(textStatus);
	});
};

function populateWizardElement(quesSeqId) {

	var jsonData = JSON.stringify({
		"QUES_SEQ_ID" : quesSeqId,
		"PRODUCT" : "MISPNT"
	});

	var promise = httpAsyncPostWithJsonRequestResponse(contextPath + "/user/solutionTemplate/getNextQuestion", jsonData);
	promise.done(function(data, textStatus, jqXHR) {
		data["numberOfQuestion"] = $("input[name='numberOfQuestion']").val();
		generateUserSelectObject();
		if (data.quesType != null && data.quesType == "RADIO_BUTTON") {
			var stepWizardDataContent = $("#stepwizard-step-template-content-radio").tmpl(data);
			$("#formId").empty();
			stepWizardDataContent.appendTo("#formId");
			//$('.nextbtn').trigger('create');
		}
		else if (data.quesType != null && data.quesType == "DROP_DOWN") {
			var stepWizardDataContent = $("#stepwizard-step-template-content-dropdown").tmpl(data);
			$("#formId").empty();
			stepWizardDataContent.appendTo("#formId");
		}
		else if (data.quesType != null && data.quesType == "CHECK_BOX") {
			var stepWizardDataContent = $("#stepwizard-step-template-content-checkbox").tmpl(data);
			$("#formId").empty();
			stepWizardDataContent.appendTo("#formId");
		}
		else {
			var stepWizardDataContent = $("#stepwizard-step-template-content-finalresult").tmpl(data);
			$("#formId").empty();
			stepWizardDataContent.appendTo("#formId");
		}
		highlightActiveStep(quesSeqId);
		showSalesHistoryGrid();
	}).fail(function(jqXHR, textStatus, errorThrown) {
		console.log(textStatus);
	});
}

function validateSelectionElement() {
	var isElemSel = true;
	$("#formId input[type='radio']").each(function() {
		var radioElName = $(this).attr('name');	
		if(!($("input[name='"+radioElName+"']").is(":checked"))) {
			isElemSel = false;
		}
	});
	
	$("#formId select").each(function() {
		var selectElName = $(this).attr('name');
		if ($("select[name='"+ selectElName +"']").val() == "") {
			isElemSel = false;
		}		
	});
	
	$('#formId input[type="checkbox"]').each(function() {
		var checkElName = $(this).attr('name');
		$("input[name='"+checkElName+"']").each( function () {
			if(!($(this).is(":checked"))) {
				isElemSel = false;
			}
		});
	});
	return isElemSel;
}

function goBackToStepWizard() {
	window.history.back();
}

function generateUserSelectObject() {
	$("#formId input[type='radio']:checked").each(function() {
		var radioElName = $(this).attr('name');	
		//var radioElValue = $("input[name='"+ radioElName +"']").val();
		var radioElValue = $(this).val();
		///userSolTmplSelectionObject[radioElName] = radioElValue;
		
		var tmpObject = new Object();
		tmpObject[radioElName] = radioElValue;
		userSolTmplSelectionObject.push(tmpObject);
	});

	$("#formId select").each(function() {
		var selectElName = $(this).attr('name');
		var selectElValue = $("select[name='"+ selectElName +"'] option:selected").val().trim();
		///userSolTmplSelectionObject[selectElName] = selectElValue;
		
		if (selectElValue) {
			var tmpObject = new Object();
			tmpObject[selectElName] = selectElValue;
			userSolTmplSelectionObject.push(tmpObject);
		}		
	});

	var checkElNames = []
	$('#formId input[type="checkbox"]:checked').each(function() {
		checkElNames.push($(this).attr('name'));
		//iterate over each name of checked checkbox
		//and read the value of it and push it into an array
		$.each(checkElNames, function(k,elName) {
			var checkedVal = [];
			$('input[name="'+ elName +'"]').each(function(){
				checkedVal.push($(this).val());
			});
			///userSolTmplSelectionObject[elName] = checkedVal
			var tmpObject = new Object();
			tmpObject[elName] = checkedVal;
			userSolTmplSelectionObject.push(tmpObject);
			
		});
	});
}

function onStepContentFormClick(e) {
	var eventSourceId = e.target.id;
	if (eventSourceId.indexOf("btnNext-") >= 0) {
		var quesSeqId = eventSourceId.substring(eventSourceId.indexOf("-") + 1);
		quesSeqId++;
		var isElemSelected = validateSelectionElement();
		if(isElemSelected){
			populateWizardElement(quesSeqId);
		}
		else{
			$("#divIsElemSelectAlert").css("display","inline");
		}
	}

	if (eventSourceId.indexOf("btnPrev-") >= 0) {
		var quesSeqId = eventSourceId.substring(eventSourceId.indexOf("-") + 1);
		quesSeqId--;
		userSolTmplSelectionObject.splice(-1);
		populateWizardElement(quesSeqId);
	}  
	
	$("#formId").find("input").on('change', function(e) {
		$("#divIsElemSelectAlert").css("display","none");
	});
	$("#formId").find("select").on('change', function(e) {
		$("#divIsElemSelectAlert").css("display","none");
	});
	
}

function onStepWizardClick(e) {
	var eventSourceName = e.target.name;

	if ("btnSolutionTemplateSteps" == eventSourceName) {
		e.preventDefault();	
		///var stepNumber = e.target.text; //stepNumber is designed to be same ad question sequence number
		///populateWizardElement(stepNumber);
	}
}

function transformDisplayKeyName(v) {
	if ("BUNDLE CD" == v) {
		return "OFFER NAME";
	}
	else if ("ACCESS SPEED" == v) {
		return "ACCESS BANDWIDTH";
	}
	else if ("PORT TYPE" == v) {
		return "ACCESS TYPE";
	}
	else if ("IP VERSION LABEL" == v) {
		return "IP VERSION";
	}	
	else {
		return v;
	}
}

function displaySelectedRowModal(url, matchPercentage) {

	var data = httpGetWithJsonResponse(url, "");
	storeDataToGenerateContract(data);//this method stores the data info into object required to show contract wizard
	$("body").find("#displaySelectedRowModal").remove();
	var DATA = {};
	//var objectKeysArray = ["bundleCd", "accessSpeed", "portType", "accessService", "ipVersionLabel", "mrc", "nrc", "SUCCESS RATIO", "accessType", "portSpeed",  "managedRouter", "designName", "protocol",  "tailTechnology", "ratePlan"];
	var objectKeysArray = ["bundleCd", "accessSpeed", "accessType", "accessService", "portType",  "portSpeed", "managedRouter", "ipVersionLabel", "protocol", "tailTechnology", "designName", "term", "mrc", "nrc", "SUCCESS RATIO"];
	//var objectKeysArray = ["accessService", "ipVersionLabel", "bundleCd", "mrc", "nrc"];
	//remove routingProtocol from the above objectKeysArray 5/10/2017
	$.each(objectKeysArray, function(k, value) {
		
		var key = value.replace(/([a-z])([A-Z])/g, '$1 $2').toUpperCase();
		key = transformDisplayKeyName(key);
		
		if(key == "MRC" || key == "NRC"){
			data[value] = "$ "+ data[value];
			DATA[key] = data[value];
		}
		else if (key == "SUCCESS RATIO") {
			if(matchPercentage == null || matchPercentage == '') {
				DATA["SUCCESS RATIO"] = 'This is brand new solution based upon MIS Express Rules Data';
			}else {
				DATA["SUCCESS RATIO"] = matchPercentage;
			}
		}
		else {
			DATA[key] = data[value];
		}
	});
	portSpeedsToCustomizeGCData = data["portSpeedList"];//store port speed list  
	//var modalDataContent = $("#stepwizard-display-selected-row-modal").tmpl(DATA);
	var templatePath = contextPath + "/templates/select_row_modal.html";
	var modalTemplate = getTemplateDefinition(templatePath);
	$.template("select_row_modal", modalTemplate);
	//console.log("displaySelectedRowModal:" + JSON.stringify(DATA));
	defaultTermPeriod = DATA['TERM'];
	defaultTermPeriodMRC = DATA['MRC'];
	defaultTermPeriodNRC = DATA['NRC'];
	var modalTemplateToDisplay = $.tmpl("select_row_modal", DATA);

	$('body').append(modalTemplateToDisplay);
	$("#btnDisplayRowSelectModal").trigger('click');
}

var defaultTermPeriod = "";
var defaultTermPeriodMRC = "";
var defaultTermPeriodNRC = "";

function storeDataToGenerateContract(genContractWizardData) {
	dataToGenContract = {}; //clearing the object before we copy it
	dataToGenContract = genContractWizardData;
}

function onClickProceedToGenContract(e) {
	
	location.hash = "contractGeneration";
	
	$('body').find("#displayContractWizard").remove();
	//console.log("dataToGenContract:"+JSON.stringify(dataToGenContract));
	var DATA = {};
	//var objectKeysArray = ["accessType", "accessSpeed", "portType", "portSpeed", "designName", "accessService", "ipVersionLabel", "protocol", "routingProtocol", "tailTechnology", "bundleCd","ratePlan", "mrc", "nrc"];
	var objectKeysArray = ["term", "mrc", "nrc"];

	$.each(objectKeysArray, function(k, value) {
		var key = value.replace(/([a-z])([A-Z])/g, '$1 $2').toUpperCase();
		DATA[key] = dataToGenContract[value];
	});
	//var contractWizardDataContent = $("#stepwizard-display-contract-data").tmpl(DATA);
	//passing the customized fields values to contract data template.
		DATA["IP VERSION"] = $("#displayIpVersionTd").html();
		DATA["PORT SPEED"] = $("#displayPortSpeedList").html();
		DATA["MANAGED ROUTER"] = $("#displayManagedRouterType").html();
	
	$('<div class="row" id="displayContractWizard"></div>').insertAfter("div.sachtopmenu");
	addContractGenTab();
		
	var templatePath = contextPath + "/templates/contract_data_wizard.html";
	var contractDataTemplate = getTemplateDefinition(templatePath);
	$.template("contract_data_wizard", contractDataTemplate);
	var contractWizardData = $.tmpl("contract_data_wizard", DATA);
	
	$("#displayContractWizard").append(contractWizardData);
	var topMenuDiv = $("#displayContractWizard");
	removeNextAllSiblingDivRows(topMenuDiv);

	showConfiguredSitesList();

}

function addContractGenTab() {
	$("#sachtopmenu_gMap").removeClass('col-sm-6 col-xs-12 sachmenuitemactive').addClass('col-sm-4 col-xs-12 sachmenuitem'); //changes span of existing tabs
	$("#sachtopmenu_solutionTemplate").removeClass('col-sm-6 col-xs-12 sachmenuitemactive').addClass('col-sm-4 col-xs-12 sachmenuitem'); //changes span of existing tabs
	$("#sachtopmenu_generateContract").css("display", "inline");
	$("#sachtopmenu_generateContract").addClass('col-sm-4 col-xs-12 sachmenuitemactive');
}




function onNextButtonClick($thisRef) {
	var currentButton = $thisRef
	var nextButtonId = $(currentButton).attr('id'); //Next button id in form btnNext-questionId
	var quesSeqId = nextButtonId.substring(nextButtonId.indexOf("-")+1);
	populateWizardElement(quesSeqId);
}

function drawPieGraphOnTopSolutionTemplatePage(data) {
	var tmpData = data["DATA"];
	
	var graph_data = {};
	graph_data.labels = [];
	graph_data.datasets = [];
	graph_data.attr = [];
	graph_data.datasets.push({});
	
	graph_data.datasets[0].backgroundColor = [ "red", "green", "blue", "yellow", "maroon", "magenta", "black", "purple"];
	graph_data.datasets[0].data = [];
	
	$.each(tmpData, function(i, value) {

		graph_data.labels.push((value.ACCESS_TYPE_ID.split("_")[1])/1000 + " Mbps");
		graph_data.datasets[0].data.push(value.PERCENTAGE || value.percentage);
		graph_data.attr.push();
	});
	
    var canvas = document.getElementById("myChart");
    var ctx = canvas.getContext("2d");
    var myNewChart = new Chart(ctx, {
      type: 'pie',
      data: graph_data
    });
    
    canvas.onclick = function (evt) {
        var activePoints = myNewChart.getElementsAtEvent(evt);
        var chartData = activePoints[0]['_chart'].config.data;
        var idx = activePoints[0]['_index'];

        var label = chartData.labels[idx];
        //displayDataGridWithTop5Records(label);
        
        $('input[name="ACCESS_TYPE_ID"][value="' + label + '"]').prop('checked', true);

      };
}

function showConfiguredSitesList() {
	$("#showConfiguredSitesList tr").remove();
	var checkSiteNames = []
	$('#sales_side_bar input[type="checkbox"]:checked').each(function() {
		checkSiteNames.push($(this).attr('data-name'));
	});
	
	$.each(checkSiteNames, function(index, value) {
		var siteName = "<tr><td>"+ value +"</td></tr>";
		$("#showConfiguredSites").find('table').append(siteName);
	});
}

var checkSitesSelection = function() {
	var checkSiteNames = []
	$('#sales_side_bar input[type="checkbox"]:checked').each(function() {
		checkSiteNames.push($(this).attr('data-name'));
	});
	if(checkSiteNames.length > 0){
		return true;
	}
	else{
		return false;
	}
};

function onClickCustomizeGCData() {
	var ipVersionDropDownTmpl = customizeGCDataFields.ipVersionDropDown();
	var managedRouterDropDownTmpl = customizeGCDataFields.managedRouterDropDown();
	customizePortSpeedsGCData();
	customizeContractTermGCData();
	
	$("#btnModalGenContract").attr('disabled','true');
	
	var thisElement = $("#btnCustomizeGCData");
	thisElement.attr('value','Apply');
	thisElement.attr('id','btnApplyGCData');
	thisElement.attr('onclick','onClickApplyGCData();');
	$("#displayIpVersionTd").empty();
	$("#displayIpVersionTd").append(ipVersionDropDownTmpl);
	$("#displayManagedRouterType").empty();
	$("#displayManagedRouterType").append(managedRouterDropDownTmpl);
}

function onClickApplyGCData() {
	$("#btnModalGenContract").removeAttr('disabled');
	
	var ipVersionValue = $('select[name="ipVersion"]').val();
	var managedRouterValue = $('select[name="managedRouterForGC"]').val();
	var portSpeedValue = $('select[name="portSpeedListForGC"]').val();
	var contractTermValue = $('select[name="contractTermForGC"]').val();
	
	var thisElement = $("#btnApplyGCData");
	
	$("#displayIpVersionTd").html(ipVersionValue);
	$("#displayManagedRouterType").html(managedRouterValue);
	$("#displayPortSpeedList").html(portSpeedValue);
	$("#displayContractTermList").html(contractTermValue);
	
	var customizeButton = '<input type="button" class="btn btn-primary" id="btnCustomizeGCData" value="Customize" onclick="onClickCustomizeGCData();">';
	
	thisElement.after(customizeButton);
	thisElement.remove();
}

function customizePortSpeedsGCData() {
	var portSpeedsDropDownElem = customizeGCDataFields.portSpeedsDropDown();
	var displayPortSpeedList = $("#displayPortSpeedList");
	displayPortSpeedList.html(portSpeedsDropDownElem);
	
	 $.each(portSpeedsToCustomizeGCData, function(key, value) {
        var option = $('<option />').prop('value', value).text(value);
        $('#displayPortSpeedList select').append(option);
      });
}

function customizeContractTermGCData() {
	var contractTermDropDownElem = customizeGCDataFields.contractTermDropDowm();
	var displayContractTermList = $("#displayContractTermList");
	displayContractTermList.html(contractTermDropDownElem);
	
	
}

function updateMRCAndNRC(updatedContractTermValue, updatedContractTerm) {
	var mrc = defaultTermPeriodMRC.split("$ ")[1];
	mrc = Number(mrc);
	var nrc = defaultTermPeriodNRC.split("$ ")[1];
	nrc = Number(nrc);
	
	if (updatedContractTermValue == 12) {
		$("#displayMRC").html("$ " + (mrc * 1));
		$("#displayNRC").html("$ " + (nrc * 1));
	}
	else if (updatedContractTermValue == 24) {
		$("#displayMRC").html("$ " + (mrc * 0.9).toFixed(2));
		$("#displayNRC").html("$ " + (nrc * 0.9).toFixed(2));
	}
	else if (updatedContractTermValue == 36) {
		$("#displayMRC").html("$ " + (mrc * 0.8).toFixed(2));
		$("#displayNRC").html("$ " + (nrc * 0.8).toFixed(2));		
	}
	
	dataToGenContract["mrc"] = $("#displayMRC").html();
	dataToGenContract["nrc"] = $("#displayNRC").html();
	dataToGenContract["term"] = updatedContractTerm;
}

/*$(document).ready(function() {
	$("select[name='contractTermForGC']").on('change', function() {
		var updatedContractTerm = $("#displayContractTermList").html();
		var updatedContractTermValue = updatedContractTerm.split(" ")[0];
		updatedContractTermValue = Number(updatedContractTermValue);
		updateMRCAndNRC(updatedContractTermValue);
	});


	$("#dispRowSelectModal").on('click', function(e) {
		alert('clicked');
	});
});
*/

function onChangeTermOnOfferViewPopup() {
	var updatedContractTerm = $("#displayContractTermList option:selected").text();
	var updatedContractTermValue = updatedContractTerm.split(" ")[0];
	updatedContractTermValue = Number(updatedContractTermValue);
	updateMRCAndNRC(updatedContractTermValue, updatedContractTerm);	
}
