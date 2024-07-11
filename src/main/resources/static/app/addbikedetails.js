//--------------------------- Add Controller ----------
myApp.controller('addBikeCtrl', function($scope, $http, BikeTypesFactory, meassgeAlertService) {
	$scope.bikeTypes = BikeTypesFactory.getBikeTypes();

	$scope.saveDetails = () => {
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
			url: 'addDetails',
			data: dataJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(response) {
			//			console.log(response);
			if (response.status === 201) {
				meassgeAlertService.showAlert('Success!', 'Data saved successfully!', 'success')
					.then(() => {
						setTimeout(() => {
							window.location.href = "dashboard";
						}, 2500);
					})
					.catch(() => {
						console.log('Alert closed without action');
					});
			}
		}, function(response) {
			meassgeAlertService.showAlert('Error!', 'Failed to save data!', 'error');
			console.log(response);
		});
	}
	$scope.clearFields = () => {
		$scope.model = null;
		$scope.manufacturer = null;
		$scope.myear = null;
		$scope.price = null;
	}
	$scope.backToDashboard = () => {
		window.location.href = "dashboard"
	}
}
);