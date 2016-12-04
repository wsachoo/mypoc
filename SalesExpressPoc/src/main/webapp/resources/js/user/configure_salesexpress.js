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
				case 'radiofilteredAccessTypes':
					handleAccessTypeRadioSelectionChange($(this), e.target);
					break;
			}
		},
		
		"change" : function(e) {
			var eventSourceName = e.target.name;
			
			switch (eventSourceName) {
			case 'accessRequired':
				handleActionRequiredAction($(this), e.target);
				break;
			case 'selectAccessType':
				handleAccessTypeDropDownChange($(this), e.target);
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
        max: allAccessSpeeds.range.length-1,
        value: 0,   
        slide : function(e, ui) {
    		$(this).slider('value', 0);
    		$("#sliderSpeedValue").val(allAccessSpeeds.range[ui.value]);
        }
    });
    
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
	
	var accessConfigOptions = $.tmpl("access_config_options_template", siteMetaData);
	lastDiv = findLastDivRowOfElement($thisRef);
	lastDiv.after(accessConfigOptions);
	
	configureDefaultAccessSpeedSlider();
	$thisRef.trigger('create');	
}

function handleAccessTypeRadioSelectionChange($thisRef, eventSource) {
	var val = $(eventSource).val();
	
	removeNextAllSiblingDivRows($(eventSource));
	var accessConfigOptions = $.tmpl("access_config_options_template", siteMetaData.accessSpeeds[val]);
	lastDiv = findLastDivRowOfElement($thisRef);
	lastDiv.after(accessConfigOptions);
	
	if ("ethernet" == val || "private_line" == val) {
		setAccessSpeedSliderLimit(siteMetaData.accessSpeeds[val]);
		$("#divFooterMessage").text("Need to apply an access and port speed to all sites before moving on");
	}
	else if ("light_speed" == val) {
		setAccessSpeedSliderLimit(siteMetaData.accessSpeeds.light_speed);
		$("#divFooterMessage").text("Lighspeed bundles access and port speeds. 45M access speed comes with a 6M upload and a 45M port speeds which cannot be modified");
	}	
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
			var accessConfigOptions = $.tmpl("access_config_options_template", siteMetaData.accessSpeeds[accessType]);
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
				      type: 'radio', name: 'radiofilteredAccessTypes', value: key, id: 'filteredAccessTypes_' + key
				}));
				$divSelectedAccessTypes.append(value);
				$divSelectedAccessTypes.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			});
		}
		$divSelectedAccessTypes.trigger('create');
	}	
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
