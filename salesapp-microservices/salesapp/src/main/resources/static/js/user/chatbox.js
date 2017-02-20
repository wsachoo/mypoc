$(document).ready(function(){
	
	$("#show_user_id").html(gUserDetails.user_id);
	$("#open-Chat").addClass('glyphicon glyphicon-comment');
	var chatWindow = $("#chat_window_1");
	
	var chatMessagesTemplate = {
		formatWatsonResponse  : function(receivedMessage) {
			return "<div class='row msg_container base_receive'>"+
					"<div class='col-md-2 col-xs-2 avatar'>"+
					"<img src='/images/watson-analytics.jpg' class=' img-responsive '>"+
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
					"<img src='/images/userImg.jpg' class=' img-responsive'>"+
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
		if(inputMessageByUser != null && inputMessageByUser != ""){
		var sentMessageDiv =  chatMessagesTemplate.getRequestMessageTemplate(inputMessageByUser, new Date().getHours()+ ":" + new Date().getMinutes());
		$(".msg_container_base").append(sentMessageDiv);
		//$("#message-container p:last").html(inputMessageByUser);
		var setEmpty = "";
		$("#btn-input-message").val(setEmpty);
		var requestObj = {};
		requestObj.user = gUserDetails.user_id;
		requestObj.input = {};
		requestObj.input.text = inputMessageByUser;
		var promise = httpAsyncPostWithJsonRequestResponseToBluemix("https://watson-sales.mybluemix.net/api/smart",requestObj)
			promise.done(function(data, textStatus, jqXHR) {
				var receivedResponseFromWatson = data.output.text;
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