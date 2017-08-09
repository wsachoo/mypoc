$(document).ready(function() {
	$(document).on('click', '.panel-heading span.clickable', function(e){
		var $this = $(this);
		if(!$this.hasClass('panel-collapsed')) {
			$this.parents('.panel').find('.panel-body').slideUp();
			$this.addClass('panel-collapsed');
			$this.find('i').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
		} else {
			$this.parents('.panel').find('.panel-body').slideDown();
			$this.removeClass('panel-collapsed');
			$this.find('i').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
		}
	});

});

function saveCartDetailConfigData(ipVersionValue, managedRouterValue, portSpeedValue, contractTermValue, siteId) {
	jsonObjectToShoppingCartTmpl[siteId]["IP VERSION"] = ipVersionValue;
	jsonObjectToShoppingCartTmpl[siteId]["MANAGED ROUTER"] = managedRouterValue;
	jsonObjectToShoppingCartTmpl[siteId]["PORT SPEED"] = portSpeedValue;
	jsonObjectToShoppingCartTmpl[siteId]["TERM"] = contractTermValue;
}

var flexwareDiscountData = {};

function displayCartDetails(jsonObjectToShoppingCartTmpl) {

	$('body').find("#displayShoppingCart").remove();
	$('<div class="row" id="displayShoppingCart"></div>').insertAfter("div.sachtopmenu");
	var templatePath = contextPath + "/templates/shopping_cart.html";
	var modalTemplate = getTemplateDefinition(templatePath);
	$.template("shopping_cart", modalTemplate);
	var modalTemplateToDisplay = $.tmpl("shopping_cart", {"jsonObjectToShoppingCartTmpl" : jsonObjectToShoppingCartTmpl});

	$("#displayShoppingCart").append(modalTemplateToDisplay);
	var topMenuDiv = $("#displayShoppingCart");
	removeNextAllSiblingDivRows(topMenuDiv);
	displayFlewareDiscountDataOnCart();
	setStylesForDiscountData();
	setDiscountPercToChild();
}

function displayFlewareDiscountDataOnCart() {
	var url = SALESEXPRESS_CONSTANTS.getUrlPath("getAllSalesFlexwareDiscountDataUrl");
	var data = httpGetWithJsonResponse(url, "");
	flexwareDiscountData = data;

	var templatePath = contextPath + "/templates/flexware_discounts_data.html";
	var modalTemplate = getTemplateDefinition(templatePath);
	$.template("flexware_discounts_data", modalTemplate);
	var modalTemplateToDisplay = $.tmpl("flexware_discounts_data", {"flexwareDiscountData" : flexwareDiscountData});

	$("#divShoppingCartTemplate").append(modalTemplateToDisplay);
}

function setStylesForDiscountData() {
	$('[id^=childDiscountDiv-]').hide();
	$('[id^=parentDiscountDiv-]').click(function() {
		var parentId = $(this).attr('id');
		var parentIdNum = parentId.split("-")[1];
		var childDivId = $("#childDiscountDiv-"+parentIdNum);
		if( $("#"+parentId).find('i').hasClass("glyphicon-chevron-down") ) {
			childDivId.show();
			$("#"+parentId).find('i').removeClass("glyphicon-chevron-down");
			$("#"+parentId).find('i').addClass("glyphicon-chevron-up");
		}else if( $("#"+parentId).find('i').hasClass("glyphicon-chevron-up") ) {
			childDivId.hide();
			$("#"+parentId).find('i').removeClass("glyphicon-chevron-up");
			$("#"+parentId).find('i').addClass("glyphicon-chevron-down");
		}
	});
}

function setDiscountPercToChild() {
	$('[id^=parent-vnf-discount-]').on('change', function () {
		if($(this).val() <= 36) {
			var num = $(this).attr('id').split("-")[3];
			var parentDiscountValue = $(this).val();
			var childTextBoxname = 'child-vnf-discount-'+num;
			$('input[name='+childTextBoxname+']').val(parentDiscountValue+'%');
		}
		else
		{
			alert("Please enter a value which is less than or equal to MAX MRC Discount");
		}
	});

}

var ssdfContractApiResponse = {};//Clear earlier SSDF response
function handleSsdfCallForContractDetail() {
	ssdfContractApiResponse = {};//Clear earlier SSDF response
	//START: Get SSDF Request JSON for each site
    var dfd = new $.Deferred();    
    var url = SALESEXPRESS_CONSTANTS.getUrlPath('getSsdfContractMicroserviceRequestInfoUrl');
	
	var i = Object.keys(jsonObjectToShoppingCartTmpl).length;
	var reqData = {}; //JSON.stringify(productConfigObj);
	
	var trigerList = $("input[id^='discountPercentage_']");
	$.each(trigerList, function(a, b) {
	    reqData[b.id] = b.value;
	});
	
	$.each(jsonObjectToShoppingCartTmpl, function(k, v) {
		reqData["OPPORTUNITY_ID"] = v["OPPORTUNITY ID"];
		
		var promise = httpAsyncPostWithJsonRequestResponse(url, JSON.stringify(reqData));
		
		promise.done(function(data, textStatus, jqXHR) {
			//START: Call SSDF Contract Microservice
			var ssdfUrl = data.REQUEST_URL;
			var ssdfReqObj = data.REQUEST_JSON;
			
			var promise = httpAsyncPostWithJsonRequestResponse(ssdfUrl, JSON.stringify(ssdfReqObj));

			promise.done(function(data2, textStatus, jqXHR) {
				
				ssdfContractApiResponse[k] = data2;
				i--;
				
				if (i == 0) {
					dfd.resolve(ssdfContractApiResponse, textStatus, jqXHR);
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				dfd.reject(jqXHR, textStatus, errorThrown);
				console.log("Error is: " + errorThrown);
				alert("Error while getting SSDF Response");
			}); 
			
			//END: Call SSDF Contract Microservice
			//this is the call to web service to get response json from SSDF
		}).fail(function(jqXHR, textStatus, errorThrown) {
			dfd.reject(jqXHR, textStatus, errorThrown);
			console.log("Error is: " + errorThrown);
			alert("Error while getting SSDF Request Information");
		});		
	});
	
	return dfd.promise();	
	//END: Get SSDF Request JSON for each site	
}


function setDataToCheckoutGenContractPopup(ssdfResp) {
	var ssdfKeys = Object.keys(ssdfResp);
	var firstSiteDataForContactInfo = ssdfResp[ssdfKeys[0]];
	setContactInfoToContractGenPopup(firstSiteDataForContactInfo);
	setTermInfoToContractGenPopup(firstSiteDataForContactInfo);
	setPriceAndDiscDetailsToContractGenPopup(ssdfResp);
	formTermsAndConditionsDisplayTable();
}

function setContactInfoToContractGenPopup(firstSiteDataForContactInfo) {
	
	var legalInfo = firstSiteDataForContactInfo[0]["customerProfile"]["customerLegalInfo"];
	$("#legalInfoLegalName").val(legalInfo["customerLegalName"]);
	$("#legalInfoCity").val(legalInfo["city"]);
	$("#legalInfoCountry").val(legalInfo["country"]);
	$("#legalInfoSAddress").val(legalInfo["streetAddress1"]);
	$("#legalInfoState").val(legalInfo["stateOrProvince"]);
	$("#legalInfoZipcode").val(legalInfo["zipOrPostalCode"]);
	
	var salesContactInfo = firstSiteDataForContactInfo[0]["attProfile"]["attSalesContact"];
	$("#salesInfoName").val(salesContactInfo["firstName"]);
	$("#salesInfoCountry").val(salesContactInfo["country"]);
	$("#salesInfoManager").val(salesContactInfo["branchManager"]);
	$("#salesInfoSAddress").val(salesContactInfo["streetAddress"]);
	$("#salesInfoPhone").val(salesContactInfo["telephone"]);
	$("#salesinfoState").val(salesContactInfo["state"]);
	$("#salesInfoCity").val(salesContactInfo["city"]);
	$("#salesInfoFax").val("123-456-789");
	$("#salesInfoZipcode").val(salesContactInfo["zip"]);
	
	
    var customerContactInfo = firstSiteDataForContactInfo[0]["customerProfile"]["customerContact"];
	$("#customerInfoName").val(customerContactInfo["firstName"]);
	$("#customerInfoCity").val(customerContactInfo["city"]);
	$("#customerInfoCountry").val(customerContactInfo["country"]);
	$("#customerInfoSAddress").val(customerContactInfo["streetAddress"]);
	$("#customerInfoState").val(customerContactInfo["state"]);
	$("#customerInfoZipcode").val(customerContactInfo["zip"]);
	$("#customerInfoPhone").val(customerContactInfo["telephone"]);
	$("#customerInfoEmail").val(customerContactInfo["email"]);

}

function setTermInfoToContractGenPopup(firstSiteDataForContactInfo) {
	var contractTerm = firstSiteDataForContactInfo[0]["intialTerm"];
	$("#contactTermTxt").val(contractTerm);
	var marc = firstSiteDataForContactInfo[0]["commitmentInstance"]["minAnnualRevCommitment"];
	$("#contractTermMarc").val(marc);
}

function formTermsAndConditionsDisplayTable() {
	var flexWareKey = undefined;
	var avpnKey = undefined;
	
	$.each(ssdfContractApiResponse, function(k, v) {
	    if (v[0].offerName == "FlexWare") {
	    	flexWareKey = k ;
	    	return false; 
	    }
	});

	$.each(ssdfContractApiResponse, function(k, v) {
	    if (v[0].offerName == "OFFER_AVPN") {
	    	avpnKey = k ;
	    	return false; 
	    }
	});

	if (flexWareKey || avpnKey) {
		$('#divTermsAndConditions').empty();
		$('#divTermsAndConditions').append('<table class="table table-striped" border="1"></table>');
		var table = $('#divTermsAndConditions').children();
		table.append("<thead><tr><th>Outline Number</th><th>Clause Level</th><th>Clause Code</th><th>Clause Name</th><th>Verbiage</th></tr></thead>");

		var tbody = "<tbody>";		
	}
	
	if (flexWareKey) {
		var flexWareObject = ssdfContractApiResponse[flexWareKey][0];
		
		$.each(flexWareObject.sections[0].subsections, function(i, objSubsection) {
			tbody = tbody + "<tr>";
			tbody = tbody + "<td>" + objSubsection.outlineNumber + "</td>";
			tbody = tbody + "<td>" + objSubsection.clauseLevel + "</td>";
			tbody = tbody + "<td>" + objSubsection.clauseCode + "</td>";
			tbody = tbody + "<td>" + objSubsection.name + "</td>";
			tbody = tbody + "<td>" + objSubsection.verbiage + "</td>";
			tbody = tbody + "</tr>";
		});
	}
	
	if (avpnKey) {
		var avpnObject = ssdfContractApiResponse[avpnKey][0];
		
		$.each(avpnObject.sections[0].subsections, function(i, objSubsection) {
			tbody = tbody + "<tr>";
			tbody = tbody + "<td>" + objSubsection.outlineNumber + "</td>";
			tbody = tbody + "<td>" + objSubsection.clauseLevel + "</td>";
			tbody = tbody + "<td>" + objSubsection.clauseCode + "</td>";
			tbody = tbody + "<td>" + objSubsection.name + "</td>";
			tbody = tbody + "<td>" + objSubsection.verbiage + "</td>";
			tbody = tbody + "</tr>";
		});
	}

	if (flexWareKey || avpnKey) {
		tbody = tbody + "</tbody>";
		table.append(tbody);				
	}
}

function setPriceAndDiscDetailsToContractGenPopup(ssdfResp) {
	var ssdfRespkeys = Object.keys(ssdfResp);
	for(var i = 0; i < ssdfRespkeys.length; i++) {
		if(ssdfResp[ssdfRespkeys[i]][0]["offerName"] == 'OFFER_AVPN') {
			$("#panelAvpnDiscounts").css('display','inline');
			$.each(ssdfResp[ssdfRespkeys[i]][0]["avpnDiscounts"], function(k,v) {
				var tr = "<tr>" +
							"<td>"+ ssdfResp[ssdfRespkeys[i]][0]["avpnDiscounts"][k]["description"] +"</td>"+
							"<td>"+ ssdfResp[ssdfRespkeys[i]][0]["avpnDiscounts"][k]["discountType"] +"</td>"+
							"<td>"+ ssdfResp[ssdfRespkeys[i]][0]["avpnDiscounts"][k]["discountPercentage"] +"</td>"+
						 "</tr>";
				$("#displayRatesInfoTableAvpn").append(tr);
			});
		}else if(ssdfResp[ssdfRespkeys[i]][0]["offerName"] == 'FlexWare') {
			$("#panelFlexwareDiscounts").css('display', 'inline');
			
			$.each(ssdfResp[ssdfRespkeys[i]][0]["netRateTables"], function(k,v) {
				var divDisplayFlexwarePricingElemsDivId = "divDisplayFlexwarePricingElements_"+k ;
				var flexwarePricingElemsDiv = "<div id='"+divDisplayFlexwarePricingElemsDivId+"'>" +
													"<strong>"+ ssdfResp[ssdfRespkeys[i]][0]["netRateTables"][k]["title"]  +"</strong>"+
						      					"</div>";
				$("#displayFlexwareDiscountsPanelbody").append(flexwarePricingElemsDiv);
				var tableId = "displayRatesInfoTableFlexware_"+k ;
				var vTable = "<table class='table' class='table table-striped' id='"+tableId+"'>" +
						      "</table>";
				$("#displayFlexwareDiscountsPanelbody").append(vTable);
				var tr = "";
				var newTableId = "";
				$.each( ssdfResp[ssdfRespkeys[i]][0]["netRateTables"][k]["rateRows"], function(rateRowkey, rateRowValue) {
					tr = "<tr>"+
								"<td>" +ssdfResp[ssdfRespkeys[i]][0]["netRateTables"][k]["rateRows"][rateRowkey]["description"] + "</td>" ;
					
					$.each(ssdfResp[ssdfRespkeys[i]][0]["netRateTables"][k]["rateRows"][rateRowkey]["cells"], function(cellKey, cellValue) {
							
							 var td = "<td>" +ssdfResp[ssdfRespkeys[i]][0]["netRateTables"][k]["rateRows"][rateRowkey]["cells"][cellKey]["value"] + "</td>" + "</tr>";
							 tr = tr + td;	
							$("#displayRatesInfoTableFlexware_"+k).append(tr);
							newTableId = $("#displayRatesInfoTableFlexware_"+k);
					});
					$("#divDisplayFlexwarePricingElements_"+k).append(newTableId);
				});
				
			});
		}
	}
}




