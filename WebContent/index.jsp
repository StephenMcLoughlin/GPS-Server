<%@ page import="java.io.*,java.util.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Device tracker</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" 
		integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" 
		crossorigin="anonymous">
		
<style type="text/css">
	div#map_container {
		width:100%;
		height:500px;
	}
</style>
		
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular.js"></script>
<script>

</script>

<!-- Display coords on Google Maps -->
<script type="text/javascript"
			src="http://maps.googleapis.com/maps/api/js?key=AIzaSyA8OiHP0gFenBjBT-Cm7QA-e5DCL2FENlQ"></script>

<script type="text/javascript">
	$(function() {
		
		
		var latlng = new google.maps.LatLng({lat: 53, lng: -9});
		var myOptions = {
		zoom: 15,
		center: latlng,
		mapTypeId: google.maps.MapTypeId.ROADMAP
		};
	
		
		var map = new google.maps.Map(document.getElementById("map_container"),myOptions);
		
		var marker = new google.maps.Marker({
			position: latlng,
			map: map,
			title: "User is here"
		});
		
		window.updateMapMarker = function(lat, lon) {
			var pos = new google.maps.LatLng(lat, lon)
			marker.setPosition(pos);
			map.setCenter(pos);
			
		}	
		
		setInterval(function() {
			$.ajax({
				url: 'rest/userlocation/get',
				dataType: 'json',
				type: 'get',
				cache: false,
				success: function(data) {
					updateMapMarker(data.latitude, data.longitude);
					$('#location').text("Location: " + data.address);
				}
			});
		},10000);
		
	})
	
</script>


</head>
<body ng-app="myApp">
<div id="header">
	<h1>GPS Device tracker</h1>
</div>
<div id="map_container"></div>
<div id="location"></div>

	
</div>
  
<div id="table" ng-controller="myCtrl">
	<form class="form-inline well well-sm clearfix">
		<span class="glyphicon glyphicon-search"></span>
		<input 
			type="text" 
			placeholder="Search..."
			class="form-control"
			ng-model="search">
	</form>
	<table class="table">
		<thead>
			<tr>
				<th>Time</th>
				<th>Address</th>
				<th>Latitude</th>
				<th>Longitude</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="location in locations | filter:search">
				<td>{{location.time}}</td>
				<td>{{location.address}}</td>
				<td>{{location.latitude}}</td>
				<td>{{location.longitude}}</td>
			</tr>
		</tbody>
	</table>

</div>

<script src="myCtrl.js"></script>

</body>
</html>