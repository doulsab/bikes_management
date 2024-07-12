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
			url: `getDetailsById/${bikeId}`,
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(response) {
			const bikeDetails = response.data;
			$scope.model = bikeDetails.model;
			$scope.manufacturer = bikeDetails.manufacturer;
			$scope.myear = bikeDetails.year;
			$scope.price = bikeDetails.price;
			$scope.selectedBikeType = bikeDetails.bikeType;
			$scope.bikeId = bikeDetails.id;
		}, function(response) {
			console.log("Error response", response);
			meassgeAlertService.showAlert('Error!', 'Failed to get data!', 'error');
		});

	}

	$scope.updateDetails = () => {
		let dataJson = {
			id:$scope.bikeId,
			model: $scope.model,
			manufacturer: $scope.manufacturer,
			year: $scope.myear,
			price: $scope.price,
			bikeType: $scope.selectedBikeType,
		}
		console.log("Update Json data ", dataJson);
		$http({
			method: "PUT",
			url: 'updateDetails',
			data: dataJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(response) {
			console.log("Update data is ", response);
			if (response.status === 200) {
				meassgeAlertService.showAlert('Success!', 'Data Updated successfully!', 'success');
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