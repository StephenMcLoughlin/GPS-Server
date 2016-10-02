var myApp = angular.module('myApp', []);
	myApp.controller('myCtrl', listController);

	function listController($scope, $http, $interval) {
		$scope.locations = [];
		$scope.search="";
		$interval(updateTable, 5000)
		
		
		
	function updateTable() {
		$http.get('rest/userlocation/getdata').then(function(value) {
	        
			$scope.locations = value.data.location_data;
		});
	}
		
}

