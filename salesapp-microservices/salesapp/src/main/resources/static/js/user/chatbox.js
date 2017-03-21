$(document).ready(function(){
	
	var isServiceUp = isLanguageTranslationServiceUp();
	if (! isServiceUp) {
		$("#chatWatsonLanguage").hide();
	}
	
	//$("#show_user_id").html(gUserDetails.user_id);
	$("#open-Chat").addClass('glyphicon glyphicon-comment');
	var chatWindow = $("#chat_window_1");
	
	var chatMessagesTemplate = {
		formatWatsonResponse  : function(receivedMessage) {
			return "<div class='row msg_container base_receive'>"+
					"<div class='col-md-2 col-xs-2 avatar'>"+
					"<img src='" + contextPath + "/images/watson-analytics.jpg' class=' img-responsive '>"+
					"</div>"+
					"<div class='col-md-10 col-xs-10'>"+
					"<div class='messages msg_receive'>"+
					"<strong>Watson:</strong>"+
					"<p>" + receivedMessage + "</p>"+
					"<time datetime='2009-11-13T20:00'>" + new Date().getHours()+ ":" + new Date().getMinutes() + "</time>" +
					"</div>"+
					"</div>"+
					"</div>";
		},		
		getRequestMessageTemplate : function (userText, vDatetime) {
			return "<div class='row msg_container my_base_sent' id='base-sent-receive'>"+
					"<div class='col-md-10 col-xs-10'>"+
					"<div class='messages msg_sent'>"+
					"<strong>Me:</strong>"+
					"<p>" + userText + "</p>"+
					"<time datetime='2009-11-13T20:00'>" + vDatetime + "</time>"+
					"</div>"+
					"</div>"+
					"<div class='col-md-2 col-xs-2 avatar1'>"+
					"<img src='" + contextPath + "/images/userImg.jpg' class=' img-responsive'>"+
					"</div>"+
					"</div>";
		} 
	};

	chatWindow.on('click', '.panel-heading span.icon_minim', function (e) {
	    var $this = $(this);
	    if (!$this.hasClass('panel-collapsed')) {
	        $this.parents('.panel').find('.panel-body').slideUp();
	        $this.addClass('panel-collapsed');
	        $this.removeClass('glyphicon-minus').addClass('glyphicon-plus');
	        $("#chat_window_1").css("margin-bottom","-50px");
	       /* $("#open-Chat").html('Open Chat');*/
	    } else {
	        $this.parents('.panel').find('.panel-body').slideDown();
	        $this.removeClass('panel-collapsed');
	        $this.removeClass('glyphicon-plus').addClass('glyphicon-minus');
	        $("#chat_window_1").css("margin-bottom", "50px");
	        $("#open-Chat").removeClass('glyphicon glyphicon-comment');
	        //$("#div-chatbox-footer").css("margin-bottom","50px");
	        //$("#chat_window_1").css("margin-bottom", "-42px");
	    }
	});

	chatWindow.on('focus', '.panel-footer input.chat_input', function (e) {
	    var $this = $(this);
	    if ($('#minim_chat_window').hasClass('panel-collapsed')) {
	        $this.parents('.panel').find('.panel-body').slideDown();
	        $('#minim_chat_window').removeClass('panel-collapsed');
	        $('#minim_chat_window').removeClass('glyphicon-plus').addClass('glyphicon-minus');
	    }
	});
	
	chatWindow.on('click', '#new_chat', function (e) {
	    var size = $( ".chat-window:last-child" ).css("margin-left");
	     size_total = parseInt(size) + 400;
	    alert(size_total);
	    var clone = $( "#chat_window_1" ).clone().appendTo( ".container" );
	    clone.css("margin-left", size_total);
	});
	
	chatWindow.on('click', '.icon_close', function (e) {
	    $( "#chat_window_1" ).hide();
	    $("#open-Chat").addClass('glyphicon glyphicon-comment');
	});
	
	$("#open-Chat").on('click', function(e){ 
		$("#chat_window_1").show();
		$("#chat_div").css("display","inline");
		$(this).removeClass('glyphicon glyphicon-comment');
		
	});
	
	chatWindow.on('click', '#btn-chat', function(e){
		var inputMessageByUser = $("#btn-input-message").val();
		
		if (inputMessageByUser != null && inputMessageByUser != ""){
		var sentMessageDiv =  chatMessagesTemplate.getRequestMessageTemplate(inputMessageByUser, new Date().getHours()+ ":" + new Date().getMinutes());
		$(".msg_container_base").append(sentMessageDiv);
		//$("#message-container p:last").html(inputMessageByUser);
		var setEmpty = "";
		$("#btn-input-message").val(setEmpty);
		
		var chatLanguage = $("#chatWatsonLanguage").val();
		
		inputMessageByUser = translateTextLanguage(inputMessageByUser, chatLanguage, "ENGLISH");
		
		var requestObj = {};
		requestObj.user = gUserDetails.user_id;
		requestObj.input = {};
		requestObj.input.text = inputMessageByUser;
		
		var promise = httpAsyncPostWithJsonRequestResponseToBluemix(
				SALESEXPRESS_CONSTANTS.getUrlPath("IBM_WATSON_CHAT_URL"), 
				requestObj)
			promise.done(function(data, textStatus, jqXHR) {
				var receivedResponseFromWatson = data.output.text;
				
				receivedResponseFromWatson = translateTextLanguage(receivedResponseFromWatson[0], "ENGLISH", chatLanguage);
				
				var receiveMessageDiv = chatMessagesTemplate.formatWatsonResponse(receivedResponseFromWatson);
				$(".msg_container_base").append(receiveMessageDiv);
				//$("#message-container p:last").html(receivedResponseFromWatson);
				$('.msg_container_base').scrollTop($('.msg_container_base')[0].scrollHeight);
			})
			.fail(function(jqXHR, textStatus, errorThrown) {
			var errorObject = $.parseJSON(jqXHR.responseText);
			 	console.log("error : " + errorObject);
			});
		}
		else{
			//do nothing
		}
		return false;
	});
});


function translateTextLanguage(inputText, fromLang, toLang) {
	
	if (("ENGLISH" == fromLang) && ("ENGLISH" == toLang)) {
		return inputText;
	}
	var promise = httpAsyncPostWithJsonRequestResponseToBluemixSynchronous(
			SALESEXPRESS_CONSTANTS.getUrlPath("IBM_WATSON_LANGUAGE_TRANSLATOR_URL"), {
	//var promise = httpAsyncPostWithJsonRequestResponseToBluemix("http://localhost:9901/salesLanguageTranslator", {
		"fromLanguage" : fromLang,
		"toLanguage" : toLang,
		"text" : inputText		
	});

	var result = "";
	promise.done(function(data, textStatus, jqXHR) {
		result = data.outputText; 
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		var errorObject = $.parseJSON(jqXHR.responseText);
	 	console.log("error : " + errorObject);
	 	result = inputText;
	});
	
	return result;
}


function isLanguageTranslationServiceUp() {
	var returnStatus = true;
	try {
	$.ajax({
		beforeSend: function(xhrObj){
			xhrObj.setRequestHeader("Content-Type","application/json");
			xhrObj.setRequestHeader("Accept","application/json");
			},
			
        type : "POST",
        url : SALESEXPRESS_CONSTANTS.getUrlPath("IBM_WATSON_LANGUAGE_TRANSLATOR_URL"),      
        jsonp : "jsonp",
        async: false,
        data: JSON.stringify({
        	"fromLanguage" : "ENGLISH",
    		"toLanguage" : "SPANISH",
    		"text" : "hello"
    	}),
        success : function (response, textS, xhr) {
        	returnStatus = true;
        },
        error : function (xmlHttpRequest, textStatus, errorThrown) {           
        	returnStatus = false;
        }
    });
	}
	catch (ex) {
		return false;
	}
	
	return returnStatus;
}
