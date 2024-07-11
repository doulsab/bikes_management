
const myApp = angular.module('myApp', []);

myApp.controller('addBikeCtrl', ['$scope', '$http', ($scope, $http) => {

	$scope.saveDetails = () => {
		//		console.log("User clicked on save")
		let dataJson = {
			model: $scope.model,
			manufacturer: $scope.manufacturer,
			year: $scope.myear,
			price: $scope.price
		}
		console.log("Data Json data ", dataJson);
		$http({
			method: "POST",
			url: 'addDetails',
			data: dataJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(response) {
			console.log(response.data);
		}, function(response) {
			console.log(response);
		});
	}
	$scope.clearFields = () => {
		$scope.model = null;
		$scope.manufacturer = null;
		$scope.myear = null;
		$scope.price = null;
	}
}
]);