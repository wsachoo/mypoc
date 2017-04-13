var userSolTmplSelectionObject = {}; //global object for user selection on step wizard

function showSalesHistoryGrid(userSolTmplSelectionObject) {
	
	//console.log("userSolTmplSelectionObject : from showSalesHistoryGrid" + JSON.stringify(userSolTmplSelectionObject));
	var jsonData = JSON.stringify(userSolTmplSelectionObject);
	
 	var promise = httpAsyncPostWithJsonRequestResponse("http://localhost:8080/user/salesHistory/getRecommendationBasedOnSalesHistory", jsonData);
	promise.done(function(data, textStatus, jqXHR){
			//console.log("Data :" + JSON.stringify(data));
			$("#solutionTemplateBottomFrame").empty();
			if(data["STATUS"] != null && data["STATUS"] != "" && data["STATUS"] == "SUCCESS" && data["DATA"].length>0){
				var gridTable = '<table id="grid-data" class="table table-condensed table-hover">'+
					    			'<thead>'+
							    		 '<tr>'+
							    		 '<th data-column-id="LEAD_DESIGN_ID" data-identifier="true" data-converter="numeric">LEAD DESIGN ID</th>'+
							    		 '<th data-column-id="ACCESS_SPEED">ACCESS SPEED</th>'+
							    		 '<th data-column-id="DESIGN_NAME" data-order="desc">DESIGN NAME</th>'+
							    		 '<th data-column-id="ACCESS_INTERCONNECT_TECH_S">ACCESS INTERCONNECT</th>'+
							    		 '</tr>'+
					    			'</thead>'+
					    		'</table>';
			$("#solutionTemplateBottomFrame").append(gridTable);
			var data = data["DATA"];
			$.each(data, function(k,v){ 
				var gridRow = 	'<tr>'+
    								'<td data-column-id="LEAD_DESIGN_ID" data-identifier="true" data-converter="numeric">'+ data[k].LEAD_DESIGN_ID +'</td>'+
    								'<td data-column-id="ACCESS_SPEED">'+ data[k].ACCESS_SPEED +'</td>'+
    								'<td data-column-id="DESIGN_NAME" data-order="desc">'+ data[k].DESIGN_NAME +'</td>'+
    								'<td data-column-id="ACCESS_INTERCONNECT_TECH_S">'+ data[k].ACCESS_INTERCONNECT_TECH_S +'</td>'+
    							'</tr>'
    			$("#grid-data").append(gridRow);			
			});
			$("#grid-data").bootgrid({
			    selection: true,
			    multiSelect: true,
			    rowSelect: true,
			    keepSelection: true,
			    
			    formatters: {
			        "link": function(column, row)
			        {
			            return "<a href=\"#\">" + column.id + ": " + row.id + "</a>";
			        }
			    }
			}).on("selected.rs.jquery.bootgrid", function(e, rows){
			    var rowIds = [];
			    for (var i = 0; i < rows.length; i++)
			    {
			        rowIds.push(rows[i].LEAD_DESIGN_ID);
			    }
			    alert("Select: " + rowIds.join(","));
			}).on("deselected.rs.jquery.bootgrid", function(e, rows){
			    var rowIds = [];
			    for (var i = 0; i < rows.length; i++)
			    {
			        rowIds.push(rows[i].id);
			    }
			    alert("Deselect: " + rowIds.join(","));
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
		$.each(checkElNames, function(k,elName){
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


