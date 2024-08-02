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
		$window.location.href = "addUser";
	}

	$scope.forgotPass = () => {
		$window.location.href = "forgotpass";
	}
	$scope.homePage = () => {
		$window.location.href = "index";
	}
	$scope.verifyOtp = () => {
		$scope.invalidOtp = false;

		if (!$scope.enteredOTP) {
			$scope.invalidOtp = true;
			meassgeAlertService.showAlert('Warning!', "Please enter OTP.", 'warning');
			return;
		};
		const sessionOTP = sessionStorage.getItem("sentOtp");

		if (sessionOTP === $scope.enteredOTP) {
			meassgeAlertService.showAlert('Success!', "OTP verified success!", 'success');
			$timeout(() => {
				$window.location.href = "changePassword";
			}, 1000);
		} else {
			meassgeAlertService.showAlert('Error!', "OTP does not match! please try again...", 'error');
		}


	}

	$scope.sendOtp = (resend = false) => {
		let passingEmail = resend !== true ? $scope.regEmail :
			sessionStorage.getItem("regEmail");
		console.log("Reg -email ", passingEmail);

		if (!passingEmail) {
			return false;
		}

		$scope.loaderDiv = true;

		$http({
			method: "POST",
			url: 'send_otp/' + passingEmail,
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(response) {
			console.log("OTP success", response);
			if (response.status === 202) {
				sessionStorage.setItem("sentOtp", response.data);
				sessionStorage.setItem("regEmail", $scope.regEmail);
				meassgeAlertService.showAlert('Success!', "OTP sent successfully!", 'success');
				$scope.loaderDiv = false;
				$timeout(() => {
					$window.location.href = "verifyopt"; // Redirect to dashboard upon successful authentication
				}, 2000);
			} else {
				console.log("Unexpected response status", response.status);
			}
		}, function(error) {
			console.error("Authentication failed", error);
			meassgeAlertService.showAlert('Error!', error.data.message, 'error');
			$scope.loaderDiv = false;
		});
	}
}
);


// Password reset controller 

myApp.controller('passwordResetCtrl', function($scope, $http, $window,$timeout) {
	// Initialize scope variables
	$scope.invalidPassword = false;
	$scope.passwordsMismatch = false;
	$scope.successMessage = '';
	$scope.errorMessage = '';
	$scope.showNewPassword = false;
	$scope.showConfirmPassword = false;

	// Function to toggle password visibility
	$scope.toggleNewPassword = function() {
		$scope.showNewPassword = !$scope.showNewPassword;
	};

	$scope.toggleConfirmPassword = function() {
		$scope.showConfirmPassword = !$scope.showConfirmPassword;
	};

	// Function to update password
	$scope.updatePassword = function() {
		$scope.invalidPassword = false;
		$scope.passwordsMismatch = false;
		$scope.successMessage = null;
		$scope.errorMessage = '';

		// Validate password and confirm password match
		if ($scope.newPassword !== $scope.confirmPassword) {
			$scope.passwordsMismatch = true;
			$scope.errorMessage = "Passwords do not match.";
			return;
		}
		let emailToUpdate = sessionStorage.getItem("regEmail");

		$http({
			method: "PATCH",
			url: 'update_password',
			params: {
				email: emailToUpdate,
				password: $scope.newPassword
			},
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(response) {
			console.log(response);
			$scope.successMessage = response.data.message;
			// Optionally, redirect or clear session
			removeSessions();
			$timeout(() => {
				$window.location.href = "login";
			}, 2000);
		}, function(error) {
			$scope.errorMessage = error.data.message;
		});
	};
	const removeSessions = () => {
		sessionStorage.removeItem("regEmail");
		sessionStorage.removeItem("sentOtp");
	}
});

