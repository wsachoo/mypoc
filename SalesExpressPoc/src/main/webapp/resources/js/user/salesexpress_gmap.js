  $(document).ready(function () {
	  displaySitesOnGoogleMap();
 /*	    var map;

 	    var myOptions = {
	        zoom: 5,
	        center: new google.maps.LatLng(41.2417, -99.3829),
	        mapTypeId: 'terrain'
	    }
 	    ;
	    map = new google.maps.Map($('#sachgooglemapdiv')[0], myOptions);

		//Resize Function
		google.maps.event.addDomListener(window, "resize", function() {
			var center = map.getCenter();
			google.maps.event.trigger(map, "resize");
			map.setCenter(center);
		});

 				var addresses = ['200 S Laurel Ave, Middletown, NJ 07748', 'Waring Plaza, 72-286 CA-111, Palm Desert, CA 92260', 'adarsh nagar, kalewadi, pune, india 411017'];

	                var  i;

                for (i = 0; i < addresses.length; i++) {  
                	
               	var jqxhr = httpGetWithJsonResponse('http://maps.googleapis.com/maps/api/geocode/json?address='+addresses[i]+'&sensor=false');
               	var p = jqxhr.results[0].geometry.location;
                	
                  var marker = new google.maps.Marker({
                    position: new google.maps.LatLng(p.lat, p.lng),
                    map: map
                  });
                  
                  marker.addListener("click", function() {
              		alert('sachin' + this.getPosition());
              	});
  
                }*/ 
            
 	});
  
function displaySitesOnGoogleMap() {
	var defaultCenterLatitude = 41.2417;
	var defaultCenterLongitude = -99.3829;
	var defaultAddresses = ['200 S Laurel Ave, Middletown, NJ 07748', 'Waring Plaza, 72-286 CA-111, Palm Desert, CA 92260'];
	
	if (!(typeof gUserDetails === 'undefined')) {
		defaultAddresses = gUserDetails.siteAddresses;
	}
	
	var gmapConfigOptions = {
		zoom: 5,
		center: new google.maps.LatLng(defaultCenterLatitude, defaultCenterLongitude),
		mapTypeId: 'terrain'
	}
	
	var map = new google.maps.Map($('#sachgooglemapdiv')[0], gmapConfigOptions);
	//Resize Function
	google.maps.event.addDomListener(window, "resize", function() {
		var center = map.getCenter();
		google.maps.event.trigger(map, "resize");
		map.setCenter(center);
	});
	
    var  i;
    for (i = 0; i < defaultAddresses.length; i++) {  
    	var p = getLatitudeLongitudeByLocation(defaultAddresses[i]);
    	var marker = new google.maps.Marker({
    		position: new google.maps.LatLng(p.lat, p.lng),
    		map: map
    	});
      
    	marker.addListener("click", function() {
  		alert('sachin' + this.getPosition());
    	});
    } 	
}
  
function getLatitudeLongitudeByLocation(address) {
	var jqxhr = httpGetWithJsonResponse('http://maps.googleapis.com/maps/api/geocode/json?address=' + address + '&sensor=false');
	var p = jqxhr.results[0].geometry.location;
	return p;
}