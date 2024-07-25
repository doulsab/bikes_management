//--------------------------- Dashboard Controller ----------
myApp.controller('dashboardCtrl', ($scope, $http, meassgeAlertService) => {

	$scope.onload = () => {
		getAllBikesStored();
	}

	const getAllBikesStored = () => {
		$http({
			method: "GET",
			url: 'getBikesList',
			headers: {
				'Content-Type': 'application/json'
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
				'Content-Type': 'application/json'
			}
		}).then(function(response) {
			if (response.status === 204) {
				meassgeAlertService.showAlert('Success!', 'Bike deleted successfully!', 'success');
				$scope.bikes = $scope.bikes.filter(item => item.id !== $scope.idToDelete);
				$scope.closeModel();
			}
		}, function(response) {
			console.log("Delete Response Error ", response);
			meassgeAlertService.showAlert('Error!', 'Failed to delete the bike. Please try again', 'error');
		});
	};
	
	


}
);