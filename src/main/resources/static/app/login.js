myApp.controller('loginContrl', ($scope, meassgeAlertService) => {

	$scope.init = () => {
		console.log(" this is the main controller login");
	};

	$scope.checkAuth = () => {

		if (!$scope.username || !$scope.password) {
			meassgeAlertService.showAlert('Error!', 'User name and password mandatory. Please try again', 'error');
			return false;
		}
		console.log("User ", $scope.username);
		console.log("Password ", $scope.password);

	}
	
	$scope.goToSignUpForm = () => {
		window.location.href = "addUser"
	}
}
);