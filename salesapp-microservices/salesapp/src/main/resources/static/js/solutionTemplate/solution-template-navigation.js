$(document).ready(function() {
	
	if ("onhashchange" in window) { //cool browser
		$(window).on('hashchange', hashChange).trigger('hashchange');
	}
	else { //lame browser
		var lastHash = ''
		setInterval(function() {
			if (lastHash != location.hash) {
				hashChange();
			}
			lastHash = location.hash;
		}, 100);
	}
	
	$('.sachtopmenu').on('click', "a", function(event) {
		event.preventDefault();
		var url = $(event.target).data('url');
		location.hash = url;

	});
	
});

function hashChange(){
	var page = location.hash.slice(1);
	
	if (page.includes("topCustomerSolutions")) {
		changeTab("stepWizard");
    	var topMenuDiv = $("div.sachtopmenu");
    	removeNextAllSiblingDivRows(topMenuDiv);

    	var frameFormat = "<div class='clearfix'></div>";
    	frameFormat = frameFormat  + "<div class='row salesexpress-content-margin solutiontemplateMiddleSpace'>";
    	frameFormat = frameFormat  + "<div class='col-sm-12'  style='height:50%;'>"; 
        frameFormat = frameFormat  + 	"<div id='solutionTemplateTopFrame' style='text-align:center;'></div>";
    	frameFormat = frameFormat + "</div>";
    	frameFormat = frameFormat  + "<div class='col-sm-12'  style='height:50%;'>"; 
    	frameFormat = frameFormat  + "<div class='row'>" +
    									"<div class='col-sm-12'><b>Based on the historical information, following are the top 5 solution that customers ordered.</b></div>" +
    								 "</div><div class='row' style='margin-top:20px'></div>";
        frameFormat = frameFormat  + 	"<div id='solutionTemplateBottomFrame' style='text-align:center;'></div>";
    	frameFormat = frameFormat + "</div>";
    	frameFormat = frameFormat + "</div>";
        topMenuDiv.after(frameFormat);
        topMenuDiv.trigger('create');
        
        var templatePath = contextPath + "/templates/solution_template_top_solutions.html";
        var toplSolutionTemplate = getTemplateDefinition(templatePath);
        $.template("solution_template_top_solutions", toplSolutionTemplate);
        
    	var promise = httpAsyncPostWithJsonRequestResponse(SALESEXPRESS_CONSTANTS.getUrlPath("ZUUL_GATEWAY_FIND_SALES_PERCENTAGE_URL"), "{}");
    	promise.done(function(data, textStatus, jqXHR){
    		var tmpData = data["DATA"];
    		
    		var percentObject = {};
    		$.each(tmpData, function(i, value) {
    			var accessType = value.ACCESS_TYPE_ID;
    			accessType = accessType.replace(/ /g, '_');
    			percentObject[accessType + "_PERCENTAGE"] = value.PERCENTAGE;
    		});
    		
        	var toplSolutionTemplate = $.tmpl("solution_template_top_solutions", percentObject);
        	$("#solutionTemplateTopFrame").after(toplSolutionTemplate);
        	$("#solutionTemplateTopFrame").trigger('create');
        	displayDataGridWithTop5Records("ETHERNET");
        	
    	}).fail(function(jqXHR, textStatus, errorThrown) {
    		console.log(textStatus);
    	}); 
    }	
	else if (page.includes("stepWizard")) {
		changeTab(page);
    	var topMenuDiv = $("div.sachtopmenu");
    	removeNextAllSiblingDivRows(topMenuDiv);

    	var frameFormat = "<div class='clearfix'></div>";
    	frameFormat = frameFormat  + "<div class='row salesexpress-content-margin '>";
    	frameFormat = frameFormat  + "<div class='col-sm-12'  style='height:50%;'>"; 
        frameFormat = frameFormat  + 	"<div id='solutionTemplateTopFrame' style='text-align:center;'></div>";
    	frameFormat = frameFormat + "</div>";
    	frameFormat = frameFormat  + "<div class='col-sm-12'  id='solutionTemplateBottomFrame' style='height:50%; text-align:center;'>";
    	frameFormat = frameFormat + "</div>";
    	frameFormat = frameFormat + "</div>";
        topMenuDiv.after(frameFormat);
        topMenuDiv.trigger('create');
        
    	var templatePath = contextPath + "/templates/StepWizard.html";
    	var initStepWizardTemplate = getTemplateDefinition(templatePath);
    	$("#solutionTemplateTopFrame").after(initStepWizardTemplate);
    	$("#solutionTemplateTopFrame").trigger('create');
    	//$("#testLoadSolutionTemplate").load(page);
	}
	else if (page.includes("solutionTemplate")) {
    	changeTab(page);
    	var topMenuDiv = $("div.sachtopmenu");
    	removeNextAllSiblingDivRows(topMenuDiv);
    	
    	var templatePath = contextPath + "/templates/init_gmap_template.html";
    	var initGmapTemplate = getTemplateDefinition(templatePath);
    	$.template("initGmapTemplate", initGmapTemplate);
    	initGmapTemplate = $.tmpl("initGmapTemplate");

    	topMenuDiv.after(initGmapTemplate);
    	topMenuDiv.trigger('create');
    	displayUserSitesOnGoogleMap();		
	}
	else if (page == "") {
    	changeTab(contextPath + "/user/solutionTemplate");
    	var topMenuDiv = $("div.sachtopmenu");
    	removeNextAllSiblingDivRows(topMenuDiv);
    	
    	var templatePath = contextPath + "/templates/init_gmap_template.html";
    	var initGmapTemplate = getTemplateDefinition(templatePath);
    	$.template("initGmapTemplate", initGmapTemplate);
    	initGmapTemplate = $.tmpl("initGmapTemplate");

    	topMenuDiv.after(initGmapTemplate);
    	topMenuDiv.trigger('create');
    	displayUserSitesOnGoogleMap();		
	}
}

function changeTab(tabDataName) {
	var div1 = $('a[data-name="siteMap"]').closest("div");
	var div2 = $('a[data-name="solutionTemplate"]').closest("div");
	div1.removeClass("sachmenuitemactive");
	div1.addClass("sachmenuitem");
	div2.removeClass("sachmenuitemactive");
	div2.addClass("sachmenuitem");
	var searchPattern = 'a[data-url="' + tabDataName + '"]';
	var selectedTabElementDiv =  $(searchPattern).closest("div");
	var topMenuDivId = selectedTabElementDiv.attr('id');
	selectedTabElementDiv.removeClass("sachmenuitem");
	selectedTabElementDiv.addClass("sachmenuitemactive");
}

function removeNextAllSiblingDivRows($triggerElement) {
	var $closestDiv = $triggerElement.closest("div.row");
	$closestDiv.nextAll('div').not('.sachbottommenu,.chat-box, #chat_window_1,.msg_container').remove();
}


