$(document).ready(function() {
	$("#accessSpeedConfigPlaceholder").on({
		"click" : function(e) {
			var eventSourceName = e.target.name;
			
			switch (eventSourceName) {
				case 'btnCustomize':
					handleBtnCustomizeClick($(this), e.target);
					break;
				case 'btnSetGreatestCompatibleSpeeds':
				case 'btnSetSiteMaxSpeeds':
					removeNextAllSiblingDivRows($(e.target));
					break;
				case 'accessConfig-radiofilteredAccessTypes':
					handleAccessTypeRadioSelectionChange($(this), e.target);
					break;
				case 'btnModifyConfigOptions':
					handleModifyConfigOptionsClick($(this), e.target);
					break;
				case 'btnCustomizePortSpeed':
					handleBtnCustomizePortSpeed($(this), e.target);
					break;
			}
		},
		
		"change" : function(e) {
			var eventSourceName = e.target.name;
			
			switch (eventSourceName) {
			case 'accessConfig-accessRequired':
				handleActionRequiredAction($(this), e.target);
				break;
			case 'accessConfig-selectAccessType':
				handleAccessTypeDropDownChange($(this), e.target);
				break;
			case 'resiliencyConfig-selectResiliencyOption':
				handleResiliencyOptionDropDownChange($(this), e.target);
				break;
			}
		}
	});
});

/*
 * This method is called from any action that requires removing the elements displayed after the clicked element
 */
function removeNextAllSiblingDivRows($triggerElement) {
	var $closestDiv = $triggerElement.closest("div.row");
	$closestDiv.nextAll('div').not('.sachbottommenu').remove();
}

function findLastDivRowOfElement($thisRef) {
	return $thisRef.find("div.row:not('.sachbottommenu'):last");
}

function configureDefaultAccessSpeedSlider() {
	var allAccessSpeeds = siteMetaData.accessSpeeds.all;
    $("#divSliderAccessSpeed").slider({
        min: 0,
        orientation: "horizontal",
        range: "min",
        max: allAccessSpeeds.range.length-1,   
        slide : function(e, ui) {
    		$(this).slider('value', 0);
    		$("#sliderSpeedValue").val(allAccessSpeeds.range[ui.value]);
        }
    });
    $("#divSliderAccessSpeed").slider("value", 0);
    $("#divSliderAccessSpeed").find(".ui-slider-range").css("background", "#337ab7");
    
    setAccessSpeedSliderLimit(allAccessSpeeds);
}

/*
 * This function is called when user changes the access type selected from the radio buttons.
 * It changes the calibration of the slider bar accordingly to the access type selected.
 */
function setAccessSpeedSliderLimit(objAccessSpeeds) {
	var $divSliderAccessSpeed = $("#divSliderAccessSpeed");
	
	var startIndex = Math.floor(objAccessSpeeds.range.length / 2);
	$divSliderAccessSpeed.slider('value', startIndex);
	$("#sliderSpeedValue").val(objAccessSpeeds.range[startIndex]);
	
	$("#divMRCNRC").text("MRC:" + objAccessSpeeds.MRC + "    NRC:" + objAccessSpeeds.NRC);
	$("#imgAccessType").attr("src", objAccessSpeeds.accessSpeedImagePath);
	
    $divSliderAccessSpeed.slider("option", "slide", function( event, ui ) {
		$(this).slider('value', 0);
		$("#sliderSpeedValue").val(objAccessSpeeds.range[ui.value]);
		$divSliderAccessSpeed.slider("option", "max", objAccessSpeeds.range.length-1);
    });	
}

function handleBtnCustomizeClick($thisRef, eventSource) {
	removeNextAllSiblingDivRows($(eventSource));
	var accessTypes = $.tmpl("filter_access_type_template", siteMetaData);
	var lastDiv = findLastDivRowOfElement($thisRef);
	lastDiv.after(accessTypes);
	$("#selectAccessType").multiselect({'includeSelectAllOption': true});
	
	var accessConfigOptions = $.tmpl("access_config_options_template", { 
		"accessConfiguration" : siteMetaData,
		"userSelectedAccessConfiguration" : gUserConfiguration
    });
	
	lastDiv = findLastDivRowOfElement($thisRef);
	lastDiv.after(accessConfigOptions);
	
	configureDefaultAccessSpeedSlider();
	$thisRef.trigger('create');	
}

function handleBtnCustomizePortSpeed($thisRef, eventSource) {
	removeNextAllSiblingDivRows($(eventSource));
	var portConfigOptions = $.tmpl("port_config_options_template", { 
		"portConfiguration" : siteMetaData.portSpeeds
    });
	
	lastDiv = findLastDivRowOfElement($thisRef);
	lastDiv.after(portConfigOptions);
	$thisRef.trigger('create');
}

function handleAccessTypeRadioSelectionChange($thisRef, eventSource) {
	var val = $(eventSource).val();
	
	removeNextAllSiblingDivRows($(eventSource));
	var accessConfigOptions = $.tmpl("access_config_options_template", { 
		"accessConfiguration" : siteMetaData.accessSpeeds[val],
		"userSelectedAccessConfiguration" : gUserConfiguration
	});

	lastDiv = findLastDivRowOfElement($thisRef);
	lastDiv.after(accessConfigOptions);
	
	if ("ethernet" == val || "private_line" == val) {
		setAccessSpeedSliderLimit(siteMetaData.accessSpeeds[val]);
		updateFooterMessage("Need to apply an access and port speed to all sites before moving on");
	}
	else if ("light_speed" == val) {
		setAccessSpeedSliderLimit(siteMetaData.accessSpeeds.light_speed);
		updateFooterMessage("Lighspeed bundles access and port speeds. 45M access speed comes with a 6M upload and a 45M port speeds which cannot be modified");
	}	
}

function handleModifyConfigOptionsClick($thisRef, eventSource) {
	$("#divAccessTypeclickApplyMessage").empty();
	$('#divAccessConfigOptions').remove();
	$('#divAccessConfigOptionsData').remove();
	var accessType = gUserConfiguration.getConfigurationData().accessConfig.radiofilteredAccessTypes || gUserConfiguration.getConfigurationData().accessConfig.selectAccessType;

	var accessConfigOptions = $.tmpl("access_config_options_template", {
		"accessConfiguration" : siteMetaData.accessSpeeds[accessType],
		"userSelectedAccessConfiguration" : gUserConfiguration
	});
	
	$("#divStartPortSpeedConfiguration").before(accessConfigOptions);			
}

function handleAccessTypeDropDownChange($thisRef, eventSource) {
	var $divSelectedAccessTypes = $("#divSelectedAccessTypes");
	var $selectAccessType = $(eventSource);
	
	if (!($selectAccessType == undefined)) {
		var selectAccessTypeLength = $selectAccessType.val().length;

		$divSelectedAccessTypes.empty();
		
		if (selectAccessTypeLength == 1) { //If a single value is selected display selection as normal label
			$divSelectedAccessTypes.html($selectAccessType.find(":selected").text());
			var accessType = $selectAccessType.find(":selected").val();
			removeNextAllSiblingDivRows($("#divAccessConfigOptions").prev());
			var accessConfigOptions = $.tmpl("access_config_options_template", { 
				"accessConfiguration" : siteMetaData.accessSpeeds[accessType],
				"userSelectedAccessConfiguration" : gUserConfiguration
			});
			
			lastDiv = findLastDivRowOfElement($thisRef);
			lastDiv.after(accessConfigOptions);			
			setAccessSpeedSliderLimit(siteMetaData.accessSpeeds[accessType]);
		}
		else {
			var selectedValues= {};

			$("#selectAccessType :selected").each(function(i,sel){
				selectedValues[$(sel).val()] = $(sel).text();
			});				

			$.each( selectedValues, function( key, value ) {
				$divSelectedAccessTypes.append($('<input>').attr({
				      type: 'radio', name: 'accessConfig-radiofilteredAccessTypes', value: key, id: 'filteredAccessTypes_' + key
				}));
				$divSelectedAccessTypes.append(value);
				$divSelectedAccessTypes.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			});
		}
		$divSelectedAccessTypes.trigger('create');
	}	
}

function handleResiliencyOptionDropDownChange($thisRef, eventSource) {
	var divSelectedResiliencyOption = $("#divSelectedResiliencyOption");
	var $selectResiliencyOption = $(eventSource);
	
	if (!($selectResiliencyOption.val() == "-1")) {
		divSelectedResiliencyOption.empty();

		divSelectedResiliencyOption.html($selectResiliencyOption.find(":selected").text());
		var resOpt = $selectResiliencyOption.find(":selected").val();
		setResiliencyOptionSpeedSliderLimit(siteMetaData.resiliencyOptions.resiliencyOptionsDetail[resOpt]);
	}	
}

/*
 * This function is called when user changes the resiliency option selected from the drop down.
 * It changes the calibration of the slider bar accordingly to the resiliency option selected.
 */
function setResiliencyOptionSpeedSliderLimit(objResiliencyOption) {
	var $divSliderResiliencySpeed = $("#divSliderResiliencySpeed");
	
	var startIndex = Math.floor(objResiliencyOption.range.length / 2);
	$divSliderResiliencySpeed.slider('value', startIndex);
	$("#sliderResiliencySpeedValue").val(objResiliencyOption.range[startIndex]);
	
	$("#resiliencyConfig-divMRCNRC").text("MRC:" + objResiliencyOption.MRC + "    NRC:" + objResiliencyOption.NRC);
	$("#imgResiliencyOption").attr("src", objResiliencyOption.resiliencyOptionImagePath);
	
	$divSliderResiliencySpeed.slider("option", "slide", function( event, ui ) {
		$(this).slider('value', 0);
		$("#sliderResiliencySpeedValue").val(objResiliencyOption.range[ui.value]);
		$divSliderResiliencySpeed.slider("option", "max", objResiliencyOption.range.length-1);
    });	
}

function handleActionRequiredAction($thisRef, eventSource) {
	var isAccessRequiredValue = $(eventSource).val();
	if ('true' === isAccessRequiredValue) {
		var optionButtons = $.tmpl("select_speed_configure_options", siteMetaData);
		var lastDiv = findLastDivRowOfElement($thisRef);
		lastDiv.after(optionButtons);
	} else {
		removeNextAllSiblingDivRows($(eventSource));
	}	
}

function updateFooterMessage(msg) {
	$("#divFooterMessage").text(msg);
}

/*function setAccessSpeedSliderLimit(allAccessSpeeds, minVal, maxVal) {
$("#divSliderAccessSpeed").slider("option", "slide", function( event, ui ) {
	if((typeof minVal != 'undefined') && (typeof maxVal != 'undefined')) {
		if (ui.value < minVal) {
			$(this).slider('value', minVal);
			$("#sliderSpeedValue").val(allAccessSpeeds[minVal]);
			return false;
		}
		else if (ui.value > maxVal) {
			$(this).slider('value', maxVal);
			$("#sliderSpeedValue").val(allAccessSpeeds[maxVal]);
			return false;
		}
		else {
			$(this).slider('value', ui.value);
			$("#sliderSpeedValue").val(allAccessSpeeds[ui.value]);
		}
	} 
	else {
		$("#sliderSpeedValue").val(allAccessSpeeds[ui.value]);
	}
});	
}
*/
