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
		changeTab("topCustomerSolutions");
    	var topMenuDiv = $("div.sachtopmenu");
    	removeNextAllSiblingDivRows(topMenuDiv);
    	$("#shoppingCartLink").find('.badge').remove();
    	
    	var frameFormat = "<div class='clearfix'></div>";
    	frameFormat = frameFormat  + "<div class='row salesexpress-content-margin'>";
    	frameFormat = frameFormat  + "  <div class='col-sm-12'  style='height:50%;'>"; 
        frameFormat = frameFormat  + 	"  <div id='solutionTemplateTopFrame'></div>";
    	frameFormat = frameFormat + "   </div>";
    	frameFormat = frameFormat  + "  <div class='col-sm-12'  style='height:50%;'>"; 
        frameFormat = frameFormat  + 	"   <div id='solutionTemplateBottomFrame'></div>";
    	frameFormat = frameFormat + "   </div>" +
    								" </div>";
        topMenuDiv.after(frameFormat);
        topMenuDiv.trigger('create');
        
        displayDataGridWithTop5Records("ETHERNET");
    }	
	else if (page.includes("stepWizard")) {
		changeTab(page);
    	var topMenuDiv = $("div.sachtopmenu");
    	removeNextAllSiblingDivRows(topMenuDiv);

    	var frameFormat = "<div class='clearfix'></div>";
    	frameFormat = frameFormat  + "<div class='row salesexpress-content-margin'>";
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
    	//changeTab(contextPath + "/user/solutionTemplate");
		changeTab("solutionTemplate");
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
	if(tabDataName == "stepWizard") {
		tabDataName = "topCustomerSolutions";
	}
	var div1 = $('a[data-name="siteMap"]').closest("div");
	var div2 = $('a[data-name="topCustomerSolutions"]').closest("div");
	var div3 = $('a[data-name="myCart"]').closest("div");
	div1.removeClass("sachmenuitemactive");
	div1.addClass("sachmenuitem");
	div2.removeClass("sachmenuitemactive");
	div2.addClass("sachmenuitem");
	div3.removeClass("sachmenuitemactive");
	div3.addClass("sachmenuitem");
	var searchPattern = 'a[data-url="' + tabDataName + '"]';
	var selectedTabElementDiv =  $(searchPattern).closest("div");
	var topMenuDivId = selectedTabElementDiv.attr('id');
/*	if(topMenuDivId != 'sachtopmenu_generateContract'){ // added to show contract generation tab
		$("#sachtopmenu_generateContract").css("display", "none");
		$("#sachtopmenu_gMap").removeClass('col-sm-4 col-xs-12').addClass('col-sm-6 col-xs-12');
		$("#sachtopmenu_solutionTemplate").removeClass('col-sm-4 col-xs-12').addClass('col-sm-6 col-xs-12');
	}*/
	selectedTabElementDiv.removeClass("sachmenuitem");
	selectedTabElementDiv.addClass("sachmenuitemactive");
}

function removeNextAllSiblingDivRows($triggerElement) {
	var $closestDiv = $triggerElement.closest("div.row");
	$closestDiv.nextAll('div').not('.sachbottommenu,.chat-box, #chat_window_1,.msg_container').remove();
}


