$(document).ready(function() {
    $("#salesexpress-side-bar").on("click", ".menu-list #menu-content > li[data-toggle='collapse']", function(e){   
    		var eul = $(this).closest("li");
    		var elem = eul.find("span:nth-child(2)");
    		elem.toggleClass("glyphicon glyphicon-minus");

    		//var parentEls = $( this ).parents().map(function() {   return this.tagName; }).get() .join( ", " );
    } );
});
