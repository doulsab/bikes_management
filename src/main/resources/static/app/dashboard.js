//--------------------------- Dashboard Controller ----------
myApp.controller('dashboardCtrl', ($scope, $http, meassgeAlertService, tokenAuth,msgOkayService) => {

	$scope.onload = () => {
		getAllBikesStored();
	}

	const getAllBikesStored = () => {
		$http({
			method: "GET",
			url: 'getBikesList',
			headers: {
				'Content-Type': 'application/json',
				...tokenAuth.getToken()
			}
		}).then(function(response) {
			console.log("Response Data ", response.data);
			$scope.bikes = response.data;
			sessionStorage.removeItem("bikeId");
		}, function(response) {
			console.log("Response  Error ", response);
		});
	}

	$scope.goToAddPage = () => {
		window.location.href = "add";
	}
	$scope.logOut = () => {
		sessionStorage.removeItem("authToken");
		window.location.href = "login";
	}

	$scope.editDetails = (id) => {
		sessionStorage.setItem('bikeId', id);
		window.location.href = "editBikeDetails";
	}

	$scope.openDeleteModel = (idToDelete) => {
		$scope.idToDelete = idToDelete;
		let openId = document.getElementById('deleteModal');
		openId.style.display = 'block';
	}
	$scope.closeModel = () => {
		let closed = document.getElementById('deleteModal');
		$scope.idToDelete = null;
		closed.style.display = 'none';
	}

	$scope.deleteBike = () => {
		$http({
			method: "DELETE",
			url: `deleteBike/${$scope.idToDelete}`,
			headers: {
				'Content-Type': 'application/json',
				...tokenAuth.getToken()
			}
		}).then(function(response) {
			if (response.status === 204) {
				meassgeAlertService.showAlert('Success!', 'Bike deleted successfully!', 'success');
				$scope.bikes = $scope.bikes.filter(item => item.id !== $scope.idToDelete);
				$scope.closeModel();
			}
		}, function(response) {
			let errorMsg = "";
			if (response.status === 403) {
				errorMsg = "Access Denied: You do not have the required permissions."
			} else {
				errorMsg = "Failed to delete the bike. Please try again"
			}
			msgOkayServicemsgOkayService.showAlert('Error!', errorMsg, 'error')
				.then(function(result) {
					window.location.href = "dashboard";
				})
				.catch(function(error) {
					window.location.href = "login";
				});
		});
	};




}
);