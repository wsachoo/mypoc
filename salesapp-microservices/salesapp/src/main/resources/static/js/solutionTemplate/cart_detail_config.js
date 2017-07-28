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
	})
});

function saveCartDetailConfigData(ipVersionValue, managedRouterValue, portSpeedValue, contractTermValue, siteId) {
	jsonObjectToShoppingCartTmpl[siteId]["IP VERSION"] = ipVersionValue;
	jsonObjectToShoppingCartTmpl[siteId]["MANAGED ROUTER"] = managedRouterValue;
	jsonObjectToShoppingCartTmpl[siteId]["PORT SPEED"] = portSpeedValue;
	jsonObjectToShoppingCartTmpl[siteId]["TERM"] = contractTermValue;
}

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
}

function displayFlewareDiscountDataOnCart() {
	var url = SALESEXPRESS_CONSTANTS.getUrlPath("postSpeedsBySelectedAccessSpeedUrl");
	var flexwareDiscountData = httpGetWithJsonResponse(url, "");
}

