myApp.controller('loginContrl', ($scope, $http, meassgeAlertService, $window, $timeout) => {

	$scope.init = () => {
		console.log(" this is the main controller login");
	};

	$scope.checkAuth = () => {
		if (!$scope.username || !$scope.password) {
			meassgeAlertService.showAlert('Error!', 'Username and password are mandatory. Please try again.', 'error');
			return;
		}

		let loginData = {
			username: $scope.username,
			password: $scope.password
		};

		$http({
			method: "POST",
			url: 'authenticate',
			data: loginData,
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(response) {
			console.log("Authentication success", response);
			if (response.status === 200) {
				meassgeAlertService.showAlert('Success!', response.data.message, 'success');
				$timeout(() => {
					$window.location.href = "dashboard"; // Redirect to dashboard upon successful authentication
				}, 1000);
			} else {
				console.log("Unexpected response status", response.status);
			}
		}, function(error) {
			console.error("Authentication failed", error);
			let errorMessage = error.data ? error.data.message : 'Authentication failed. Please check your credentials and try again.';
			meassgeAlertService.showAlert('Error!', errorMessage, 'error');
		});
	};

	$scope.goToSignUpForm = () => {
		window.location.href = "addUser"
	}

	$scope.forgotPass = () => {
		window.location.href = "forgotpass"
	}
	$scope.verifyOtp = () => {
		console.log(" receivedOpt ",$scope.receivedOpt);
//		window.location.href = "forgotpass"
	}

	$scope.sendOtp = () => {
		
		if(!$scope.regEmail) return ;

		$http({
			method: "POST",
			url: 'send_otp/' + $scope.regEmail,
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(response) {
			console.log("OTP success", response);
			sessionStorage.setItem("sentOtp",response.data);
			if (response.status === 202) {
				meassgeAlertService.showAlert('Success!',"OTP sent successfully!", 'success');
				$timeout(() => {
					$window.location.href = "verifyopt"; // Redirect to dashboard upon successful authentication
				}, 1000);
			} else {
				console.log("Unexpected response status", response.status);
			}
		}, function(error) {
			console.error("Authentication failed", error);
			meassgeAlertService.showAlert('Error!', error.data.message, 'error');
		});
	}
}
);