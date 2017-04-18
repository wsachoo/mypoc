var userSolTmplSelectionObject = {}; //global object for user selection on step wizard

function showSalesHistoryGrid(userSolTmplSelectionObject) {

	//console.log("userSolTmplSelectionObject : from showSalesHistoryGrid" + JSON.stringify(userSolTmplSelectionObject));
	var jsonData = JSON.stringify(userSolTmplSelectionObject);
	var promise = httpAsyncPostWithJsonRequestResponse("http://localhost:8080/user/salesHistory/getRecommendationBasedOnSalesHistory", jsonData);
	promise.done(function(data, textStatus, jqXHR){
		console.log("Data :" + JSON.stringify(data));
		$("#solutionTemplateBottomFrame").empty();
		if(data["STATUS"] != null && data["STATUS"] != "" && data["STATUS"] == "SUCCESS" && data["DATA"].length>0){
			var gridTable = '<table id="grid-data" class="table table-condensed table-hover" style="background-color : #F5F5F5;">'+
			'<thead>'+
			'<tr>'+
			'<th data-column-id="commands" data-formatter="commands" data-sortable="false">View</th>'+
			'<th data-column-id="accessSpeed" >ACCESS SPEED</th>'+
			'<th data-column-id="ethernetHandoffInterface">ETHERNET HANDOFF INTERFACE</th>'+
			'<th data-column-id="designType">DESIGN TYPE</th>'+
			'<th data-column-id="portSpeed">PORT SPEED</th>'+
			'<th data-column-id="managedRouter">MANAGED ROUTER</th>'+
			'<th data-column-id="siteNameAlias">SITE NAME ALIAS</th>'+
			'<th data-column-id="ratePlan">RATE PLAN</th>'+
			/* '<th data-column-id="tailTechnologyId">TAIL TECHNOLOGY</th>'+*/
			'</tr>'+
			'</thead>'+
			'</table>';
			$("#solutionTemplateBottomFrame").append(gridTable);
			var data = data["DATA"];
			$.each(data, function(k,v) {
				var gridRow = 	'<tr >'+
				'<td data-column-id="commands"></td>'+
				'<td data-column-id="accessSpeed">'+ data[k].accessSpeed +'</td>'+
				'<td data-column-id="ethernetHandoffInterface">'+ data[k].ethernetHandoffInterface +'</td>'+
				'<td data-column-id="designType" data-order="desc">'+ data[k].designType +'</td>'+
				'<td data-column-id="portSpeed">'+ data[k].portSpeed +'</td>'+
				'<td data-column-id="managedRouter">'+ data[k].managedRouter +'</td>'+
				'<td data-column-id="siteNameAlias">'+ data[k].siteNameAlias +'</td>'+
				'<td data-column-id="ratePlan">'+ data[k].ratePlan +'</td>'+
				/*'<td data-column-id="tailTechnologyId">'+ data[k].tailTechnologyId +'</td>'+*/
				'</tr>'
				$("#grid-data").append(gridRow);			
			});
			var grid = $("#grid-data");
			$("#grid-data").bootgrid({
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
			}).on("loaded.rs.jquery.bootgrid", function(){
				//used openLink class just to select the current button to call the click event
				$("#grid-data").find(".openLink").on("click", function(e) {
					var thisElementId = $(this);
					var rowId = thisElementId.closest('tr').attr('data-row-id');
					var url = data[rowId].links[0].href;
					var matchpercentage = data[rowId].matchPercentage;
					displaySelectedRowModal(url, matchpercentage);
				});

			});
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
		var stepWizardData = $("#stepwizard-step-template").tmpl(data);
		stepWizardData.appendTo("#stepWizardId");
		populateWizardElement(data[0].quesSeqId);
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
		highlightActiveStep(quesSeqId);
		showSalesHistoryGrid(userSolTmplSelectionObject);
	}).fail(function(jqXHR, textStatus, errorThrown) {
		console.log(textStatus);
	});
}

function generateUserSelectObject() {
	$("#formId input[type='radio']:checked").each(function() {
		var radioElName = $(this).attr('name');	
		//var radioElValue = $("input[name='"+ radioElName +"']").val();
		var radioElValue = $(this).val();
		userSolTmplSelectionObject[radioElName] = radioElValue;
	});

	$("#formId select").each(function() {
		var selectElName = $(this).attr('name');
		var selectElValue = $("select[name='"+ selectElName +"'] option:selected").val();
		userSolTmplSelectionObject[selectElName] = selectElValue;
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
			userSolTmplSelectionObject[elName] = checkedVal
		});
	});
}
function onStepContentFormClick(e) {
	var eventSourceId = e.target.id;
	if (eventSourceId.indexOf("btnNext-") >= 0) {
		var quesSeqId = eventSourceId.substring(eventSourceId.indexOf("-") + 1);
		quesSeqId++;
		populateWizardElement(quesSeqId);
	}

	if (eventSourceId.indexOf("btnPrev-") >= 0) {
		var quesSeqId = eventSourceId.substring(eventSourceId.indexOf("-") + 1);
		quesSeqId--;
		populateWizardElement(quesSeqId);
	}  
}

function onStepWizardClick(e) {
	var eventSourceName = e.target.name;

	if ("btnSolutionTemplateSteps" == eventSourceName) {
		e.preventDefault();	
		var stepNumber = e.target.text; //stepNumber is designed to be same ad question sequence number
		populateWizardElement(stepNumber);
	}
}

function displaySelectedRowModal(url, matchPercentage) {
	
	var data = httpGetWithJsonResponse(url, "");
	$("body").remove("#stepwizard-display-selected-row-modal");
	var DATA = {};
	var objectKeysArray = ["accessType", "accessSpeed", "portType", "portSpeed", "designName", "accessService", "ipVersionLabel", "protocol", "routingProtocol", "tailTechnology", "bundleCd","ratePlan"];

	$.each(objectKeysArray, function(k, value) {
		var key = value.replace(/([a-z])([A-Z])/g, '$1 $2').toUpperCase();
		DATA[key] = data[value];
	});
	DATA["SUCCESS RATIO"] = matchPercentage;
	var modalDataContent = $("#stepwizard-display-selected-row-modal").tmpl(DATA);
	modalDataContent.appendTo('body');
	$("#btnDisplayRowSelectModal").trigger('click');
}

