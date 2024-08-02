
myApp.controller('indexContrl', ($scope, $http, meassgeAlertService,$window) => {

	$scope.successMessage = "Your message has been sent. Thank you!";
	$scope.failedMasg = "Please fill in all the details before sending the mail.";

	$scope.sendRequestMail = function() {
		$scope.successMassage = false;
		$scope.isInValidFrom = false;
		$scope.isMessageSent = false;

		if (!$scope.custName || !$scope.custEmail || !$scope.custSubject || !$scope.custMessage) {
			console.log("hsaos");
			meassgeAlertService.showAlert('Warning!', $scope.failedMasg, 'warning');
			$scope.isInValidFrom = true;
			return;
		}

		let formData = {
			name: $scope.custName,
			email: $scope.custEmail,
			subject: $scope.custSubject,
			message: $scope.custMessage
		};
		console.log("Form adata ", formData);
		$http({
			method: "POST",
			url: 'submitForm',
			data: formData,
			headers: {
				'Content-Type': 'application/json'
			}
		}).then(function(response) {
			if (response.status === 202) {
				$scope.custName = null;
				$scope.custEmail = null;
				$scope.custSubject = null;
				$scope.custMessage = null;
				meassgeAlertService.showAlert('Success!', $scope.successMessage, 'success');
			}
		}, function(error) {
			meassgeAlertService.showAlert('Error!', "Massage Send failed", 'error');
		});

	};
	$scope.goToLogin = () =>{
		$window.location.href ="login";
	}
	
});

