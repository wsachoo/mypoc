$(document).ready(function() {
	displayUserSitesOnGoogleMap();
});

function displayUserSitesOnGoogleMap() {
	if (!(typeof gUserDetails === 'undefined')) {
		displaySitesOnGoogleMap(gUserDetails.siteAddresses);
	}
	else {
		displaySitesOnGoogleMap();
	}	
}
/*
 * addressList: List of physical addresses
 * centerLatitude: Latitude of the location to be displayed at the center of the map.
 * centerLongitude: Longitude of the location to be displayed at the center of the map.
 */
function displaySitesOnGoogleMap(addressList, centerLatitude, centerLongitude) {
	var defaultCenterLatitude = centerLatitude || 41.2417;
	var defaultCenterLongitude = centerLongitude || -99.3829;
	var defaultAddresses = addressList || [{"site_id" : 1, "site_addr" : "200 S Laurel Ave, Middletown, NJ 07748"}, {"site_id" : 2, "site_addr" : "Waring Plaza, 72-286 CA-111, Palm Desert, CA 92260"}];

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
    	var p = getLatitudeLongitudeByLocation(defaultAddresses[i].site_addr);
        marker = new google.maps.Marker({
        	position: new google.maps.LatLng(p.lat, p.lng),
            map: map
        });

        google.maps.event.addListener(marker, 'click', (function (marker, i) {
            return function () {
                infowindow.setContent(defaultAddresses[i].site_addr);
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