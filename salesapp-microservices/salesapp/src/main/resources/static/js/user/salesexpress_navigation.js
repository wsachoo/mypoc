$(document).ready(function() {
	/*
	 * Event handler for side naviation
	 */
    $("#salesexpress-side-bar").on("click", ".menu-list #menu-content > div > li[data-toggle='collapse']", function(e) {
    		e.preventDefault();
    		var eul = $(this).closest("li");
    		var elem = eul.find("span:nth-child(2)");
    		elem.toggleClass("glyphicon-plus glyphicon-minus");

    		//var parentEls = $( this ).parents().map(function() {   return this.tagName; }).get() .join( ", " );
    });
    
    $("#salesexpress-side-bar").on("click", ".menu-list #menu-content > li > a[data-toggle='collapse']", function(e) {
    	e.preventDefault();
		var elem = $(this).find(".more-less");
		elem.toggleClass("glyphicon-plus glyphicon-minus");

		//var parentEls = $( this ).parents().map(function() {   return this.tagName; }).get() .join( ", " );
    });
    
    /*
     * Event handler for top navigation
     */
    $('.sachtopmenu').on('click', "a", function(event) {
        var name = $(event.target).data('name');
        
        if (("siteMap" === name)) {
        	//var url = $(event.target).data('url');
            //location.replace(url);
        	performTabChangeAction(name);
        	var topMenuDiv = $("div.sachtopmenu");
        	removeNextAllSiblingDivRows(topMenuDiv);
        	var initGmapTemplate = $.tmpl("init_gmap_template");
        	topMenuDiv.after(initGmapTemplate);
        	topMenuDiv.trigger('create');
        	displayUserSitesOnGoogleMap();
        }
        else if ("accessAndPort" === name) {
        	performTabChangeAction(name);
        	var topMenuDiv = $("div.sachtopmenu");
        	//var topMenuDiv = $("div.loadSolutionTemplate");
        	removeNextAllSiblingDivRows(topMenuDiv);
        	//$("#testLoadSolutionTemplate").load(contextPath + "/user/loadSolutionTemplate");
        	var initAccessConfigTemplate = $.tmpl("init_access_config_template");
        	topMenuDiv.after(initAccessConfigTemplate);
        	topMenuDiv.trigger('create');
        }
        else if (("serviceAndFeatures" === name)) {
        	if(Object.keys(gUserConfiguration.getUserConfigurationData().sites).length > 0){
        		performTabChangeAction(name);
        		handleProceedToFeatures(); // added to test being on the same page
        	}
        }
        else if(("results" === name)){
        	if(Object.keys(gUserConfiguration.getUserConfigurationData().sites).length > 0){
        		performTabChangeAction(name);
        		handleProceedToResults(); 
        	}
        }
        else if(("solutionTemplate" === name)) {
        	performTabChangeAction(name);
        	//var topMenuDiv = $("div.sachtopmenu");
        	var topMenuDiv = $("div.loadSolutionTemplate");
        	removeNextAllSiblingDivRows(topMenuDiv);
        	$("#testLoadSolutionTemplate").load(contextPath + "/user/stepWizard");
        }
    });
    
    
    $("#sales_side_bar :checkbox").click(function() {
    	if (this.checked) {
        	var activeTabName = getActiveTabName();
        	if ("accessAndPort" == activeTabName) {
            	var refFirstItemOnPage = $(".salesexpress-content-margin");
            	var refCutOffItem = refFirstItemOnPage.next("div");
            	removeNextAllSiblingDivRows(refCutOffItem);
            	$("input[name=accessConfig-accessRequired]").prop("checked", false); 
            	gUserConfiguration.clearSiteConfiguration();//to clear existing access & port config data
        	}
        	else if ("serviceAndFeatures" == activeTabName) {
            	var refFirstItemOnPage = $(".salesexpress-content-margin");
            	var refCutOffItem = refFirstItemOnPage.next("div");
            	removeNextAllSiblingDivRows(refCutOffItem);
            	$("input[name=serviceConfigServiceRequired]").prop("checked", false);
            	//guserServiceFeatures.clearServiceFeaturesConfiguration;
            	tempServiceAndFeatures={};//to clear existing siteFeatures Object Data
        	}        	
    	}
    });
    
});

function  getActiveTabName() {
	var activeTabClassName = "sachmenuitemactive";
	var activeTabDiv = $("." + activeTabClassName);
	var activeTabHref = activeTabDiv.children("a"); 
	var activeTabName = activeTabHref.data("name");
	return activeTabName;
}
