//--------------------------- Add Controller ----------
myApp.controller('editBikeCtrl', function($scope, $http, BikeTypesFactory, meassgeAlertService, tokenAuth, msgOkayService) {
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
				'Content-Type': 'application/json',
				...tokenAuth.getToken()
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

	$scope.updateDetails = () => {
		let dataJson = {
			id: $scope.bikeId,
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
				'Content-Type': 'application/json',
				...tokenAuth.getToken()
			}
		}).then(function(response) {
			console.log("Update data is ", response);
			if (response.status === 200) {
				meassgeAlertService.showAlert('Success!', 'Data Updated successfully!', 'success');
			}
		}, function(response) {
			let errorMsg = "";
			if (response.status === 403) {
				errorMsg = "Access Denied: You do not have the required permissions."
			} else {
				errorMsg = "Failed to update data!";
			}
			msgOkayService.showAlert('Error!', errorMsg, 'error')
				.then(function(result) {
					//console.log("OK clicked. Performing post-action...");
				})
				.catch(function(error) {
					//console.log("Alert dismissed. No action performed.");
				});;
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