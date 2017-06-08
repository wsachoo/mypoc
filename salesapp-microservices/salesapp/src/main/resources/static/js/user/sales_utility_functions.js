function getProductSitesDisplayTextOnResultsPage(str) {
	var siteIdArray = str.split(",");
	var numberOfsitesForProduct = siteIdArray.length;
	var numberOfUserSites = Object.keys(gUserDetails.siteAddresses).length;
	
	if (numberOfUserSites == numberOfsitesForProduct) {
		return "All Sites";
	}

	var productSiteNames = $.map(gUserDetails.siteAddresses, function(value, i) {
        if (($.inArray("" + value.SITE_ID, siteIdArray) > -1)) {
            return value.SITE_NAME;
        }
	});
	
	return productSiteNames.toString();
}



function capitalize_jsobject_keys(obj) {
	return obj.map( function( item ){
	    for(var key in item){
	        var upper = key.toUpperCase();
	        // check if it already wasn't uppercase
	        if( upper !== key ){ 
	            item[ upper ] = item[key];
	            delete item[key];
	        }
	    }
	    return item;
	});	
}
