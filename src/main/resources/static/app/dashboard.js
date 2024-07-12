//--------------------------- Dashboard Controller ----------
myApp.controller('dashboardCtrl', ($scope, $http) => {

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
		}, function(response) {
			console.log("Response  Error ", response);
		});
	}

	$scope.goToAddPage = () => {
		window.location.href = "add";
	}

	$scope.editDetails = (id) => {
		sessionStorage.setItem('bikeId', id);
		window.location.href = "editBikeDetails";
	}
}
);