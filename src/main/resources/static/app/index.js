
myApp.controller('indexContrl', ($scope, $http) => {

	$scope.successMassage = false;

	$scope.sendRequestMail = function() {

		if (!$scope.custName && !$scope.custEmail && !$scope.custSubject && !$scope.custMessage) {
			console.log("hsaos");
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
				$scope.successMassage = true;
			}
			console.log("Massage Send success", response);
		}, function(error) {
			console.error("Massage Send failed", error);
		});

	};
});

