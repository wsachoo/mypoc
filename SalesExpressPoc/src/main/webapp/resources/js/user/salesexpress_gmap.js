$(document).ready(function () {
    displaySitesOnGoogleMap();
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
    var infowindow = new google.maps.InfoWindow();

    for (i = 0; i < defaultAddresses.length; i++) {
    	var p = getLatitudeLongitudeByLocation(defaultAddresses[i]);
        marker = new google.maps.Marker({
        	position: new google.maps.LatLng(p.lat, p.lng),
            map: map
        });

        google.maps.event.addListener(marker, 'click', (function (marker, i) {
            return function () {
                infowindow.setContent(defaultAddresses[i]);
                infowindow.open(map, marker);
            }
        }(marker, i)))
    }	
}
  
function getLatitudeLongitudeByLocation(address) {
	var jqxhr = httpGetWithJsonResponse('http://maps.googleapis.com/maps/api/geocode/json?address=' + address + '&sensor=false');
	var p = jqxhr.results[0].geometry.location;
	return p;
}