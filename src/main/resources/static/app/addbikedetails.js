//--------------------------- Add Controller ----------
myApp.controller('addBikeCtrl', function($scope, $http, BikeTypesFactory, meassgeAlertService, tokenAuth, msgOkayService) {
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
				'Content-Type': 'application/json',
				...tokenAuth.getToken()
			}
		}).then(function(response) {
			console.log("Add Bike ss ", response);
			if (response.status === 201) {
				meassgeAlertService.showAlert('Success!', 'Data saved successfully!', 'success');
				setTimeout(() => {
					window.location.href = "dashboard";
				}, 2500);
			}
		}, function(response) {
			let errorMsg = "";
			if (response.status === 403) {
				errorMsg = "Access Denied: You do not have the required permissions."
			} else {
				errorMsg = "Failed to save data"
			}
			msgOkayService.showAlert('Error!', errorMsg, 'error')
				.then(function(result) {
					window.location.href = "dashboard";
				})
				.catch(function(error) {
					window.location.href = "login";
				});
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