function getProductSitesDisplayTextOnResultsPage(str) {
	var siteIdArray = str.split(",");
	var numberOfsitesForProduct = siteIdArray.length;
	var numberOfUserSites = Object.keys(gSiteIdNameMapping).length;
	
	if (numberOfUserSites == numberOfsitesForProduct) {
		return "All Sites";
	}

	var productSiteNames = $.map(gSiteIdNameMapping, function(k, v) {
        if (($.inArray(k, siteIdArray) > -1)) {
            return v;
        }
	});
	
	return productSiteNames.toString();
}