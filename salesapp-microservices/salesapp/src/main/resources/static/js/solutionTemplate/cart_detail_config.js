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


function handleSsdfCallForContractDetail() {
	//START: Get SSDF Request JSON for each site
	var url = SALESEXPRESS_CONSTANTS.getUrlPath('getSsdfContractMicroserviceRequestInfoUrl');
	var ssdfContractApiResponse = {};
	
	$.each(jsonObjectToShoppingCartTmpl, function(k, v) {
		var reqData = {}; //JSON.stringify(productConfigObj);
		reqData["OPPORTUNITY_ID"] = v["OPPORTUNITY ID"];
		var promise = httpAsyncPostWithJsonRequestResponseSynchronous(url, JSON.stringify(reqData));
		
		promise.done(function(data, textStatus, jqXHR) {
			console.log(data);

			//START: Call SSDF Contract Microservice
			var ssdfUrl = data.REQUEST_URL;
			var ssdfReqObj = data.REQUEST_JSON;
			
			var promise = httpAsyncPostWithJsonRequestResponseSynchronous(ssdfUrl, JSON.stringify(ssdfReqObj));
			promise.done(function(data, textStatus, jqXHR) {
				ssdfContractApiResponse[k] = data;
			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log(textStatus);
			}); 
			
			//END: Call SSDF Contract Microservice
			//this is the call to web service to get response json from SSDF
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert("Error while getting SSDF Request Information");
		});		
	});
	
	return ssdfContractApiResponse;
	//END: Get SSDF Request JSON for each site
	
}
