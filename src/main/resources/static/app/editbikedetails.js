//--------------------------- Add Controller ----------
myApp.controller('editBikeCtrl', function($scope, $http, BikeTypesFactory, meassgeAlertService) {
	$scope.bikeTypes = BikeTypesFactory.getBikeTypes();

	$scope.onload = () => {
		getBideDetailsById();
	}


	const getBideDetailsById = () => {
		let bikeId = sessionStorage.getItem("bikeId");

		$http({
			method: "GET",
			url: 'getDetailsById/' + bikeId,
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(response) {
			console.log("Response Edit", response.data);
			sessionStorage.removeItem("bikeId");
		}, function(response) {
			console.log("Response", response);
		});

	}

	$scope.updateDetails = () => {
		let dataJson = {
			model: $scope.model,
			manufacturer: $scope.manufacturer,
			year: $scope.myear,
			price: $scope.price,
			bikeType: $scope.selectedBikeType,
		}
		console.log("Data Json data ", dataJson);
		$http({
			method: "POST",
			url: 'updateDetails',
			data: dataJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(response) {

			if (response.status === 201) {
				meassgeAlertService.showAlert('Success!', 'Data Update successfully!', 'success');
			}
		}, function(response) {
			meassgeAlertService.showAlert('Error!', 'Failed to update data!', 'error');
			console.log(response);
		});
	}
	$scope.clearFields = () => {
		$scope.model = null;
		$scope.manufacturer = null;
		$scope.myear = null;
		$scope.price = null;
		$scope.selectedBikeType = null;
	}
	$scope.backToDashboard = () => {
		window.location.href = "dashboard"
	}
}
);