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
        
        if (("accessAndPort" === name) || ("siteMap" === name)) {
        	var url = $(event.target).data('url');
            location.replace(url);
        }
    });    
    
});
