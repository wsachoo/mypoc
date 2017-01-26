$(document).ready(function() {
	/*
	 * Event handler for side naviation
	 */
    $("#salesexpress-side-bar").on("click", ".menu-list #menu-content > li[data-toggle='collapse']", function(e){   
    		var eul = $(this).closest("li");
    		var elem = eul.find("span:nth-child(2)");
    		elem.toggleClass("glyphicon glyphicon-minus");

    		//var parentEls = $( this ).parents().map(function() {   return this.tagName; }).get() .join( ", " );
    } );
    
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
        	removeNextAllSiblingDivRows(topMenuDiv);
        	var initAccessConfigTemplate = $.tmpl("init_access_config_template");
        	topMenuDiv.after(initAccessConfigTemplate);
        	topMenuDiv.trigger('create');
        }
        else if (("serviceAndFeatures" === name) ||  ("results" === name)) {
        	//performTabChangeAction(name);
        }
    });
    
    
    $("#sales_side_bar :checkbox").click(function() {
    	if (this.checked) {
        	var activeTabName = getActiveTabName();
        	if ("accessAndPort" == activeTabName) {
            	var refFirstItemOnPage = $(".salesexpress-content-margin");
            	var refCutOffItem = refFirstItemOnPage.next("div");
            	removeNextAllSiblingDivRows(refCutOffItem);
            	$("input[name=accessConfig-accessRequired]").prop("checked", false)        		
        	}
        	else if ("serviceAndFeatures" == activeTabName) {
            	var refFirstItemOnPage = $(".salesexpress-content-margin");
            	var refCutOffItem = refFirstItemOnPage.next("div");
            	removeNextAllSiblingDivRows(refCutOffItem);
            	$("input[name=serviceConfig-serviceRequired]").prop("checked", false)        		
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
