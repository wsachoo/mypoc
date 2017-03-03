<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="container">
    <div class="row chat-window col-sm-3" id="chat_window_1" style="margin-left:55%; z-index: 2000">
        <div class="col-xs-12 col-md-12">
        	<div class="panel panel-default">
        		<!-- top bar starts -->	
                <div class="panel-heading top-bar">
                    <div class="col-md-4 col-xs-4">
                        <h3 class="panel-title"><label id="show_user_id" ></label></h3>
                    </div>
                    <div class="col-md-4 col-xs-4">
                        <select id="chatWatsonLanguage" name="chatWatsonLanguage">
                        	<option value="ENGLISH">Select Language</option>
                        	<option>ARABIC</option>
                        	<option>ENGLISH</option>
                        	<option>FRENCH</option>
                        	<option>ITALIAN</option>
                        	<option>PORTUGUESE</option>
                        	<option>SPANISH</option>
                        </select>
                    </div>
                    <div class="col-md-4 col-xs-4" style="text-align: right;">
                        <a href="#"><span id="minim_chat_window" class="glyphicon glyphicon-minus icon_minim"></span></a>
                        <a href="#"><span class="glyphicon glyphicon-remove icon_close" data-id="chat_window_1"></span></a>
                    </div>
                </div>
                <!-- top  bar ends -->
                <!-- message container starts -->
                <div class="panel-body msg_container_base" id="message-container">
                    
                    <div class="row msg_container base_receive">
                        <div class="col-md-2 col-xs-2 avatar roger ${contextPath} federer">
                             <img src="${contextPath}/images/watson-analytics.jpg" class="img-responsive ">
                        </div>
                        <div class="col-md-10 col-xs-10">
                            <div class="messages msg_receive">
                            	<strong>IBM Watson</strong>
                            </div>
                        </div>
                    </div>
                     
                </div>
                <!-- message container ends -->
                <!-- footer starts -->
                <div class="panel-footer" id="div-chatbox-footer">
                    <div class="input-group">
                        <input id="btn-input-message" type="text" class="form-control input-sm chat_input" value="" />
                        <span class="input-group-btn">
                        <button class="btn btn-primary btn-sm" id="btn-chat">Send</button>
                        </span>
                    </div>
                </div>
                <!-- footer ends -->
    		</div>
        </div>
    </div>
 
</div>

