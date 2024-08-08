//--------------------------- Add User Controller ----------
myApp.controller('addUserCtrl', function($scope, $http, meassgeAlertService, $filter,tokenAuth) {
	$scope.onload = () => {
		$scope.genderList = genders;
	}

	$scope.saveUser = () => {
		//		Validation need to TODO
		let dataJson = {
			address: $scope.address,
			birthdate: $filter('date')($scope.birthdate, 'dd/MM/yyyy'),
			mobile: $scope.mobile,
			gender: $scope.gender,
			email: $scope.email,
			name: $scope.fullname,
			username: $scope.username,
			password: $scope.password
		}
		console.log("Update Json data ", dataJson);
		$http({
			method: "POST",
			url: 'adduserdetails',
			data: dataJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(response) {
			console.log("Update data is ", response);
			if (response.status === 201) {
				meassgeAlertService.showAlert('Success!', 'Data Updated successfully!', 'success');
			}
		}, function(error) {
			console.log(error);
			if (error.status === 409) {
				meassgeAlertService.showAlert('Error!', 'Username already exists!', 'error');
			} else {
				meassgeAlertService.showAlert('Error!', 'Failed to save user data!', 'error');
			}
			return false;
		});
	}
	$scope.clearFields = () => {
		window.location.reload();
	}
	$scope.goToLoginPage = () => {
		window.location.href = "login"
	}

	const genders = [
		'Male', 'Female'
	]
}
);