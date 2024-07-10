
const myApp = angular.module('myApp', []);

myApp.controller('loginContrl', [
	'$scope',
	($scope) => {

		$scope.init = () => {
			console.log(" this is the main controller login");
		};

		$scope.checkAuth = () => {
			console.log("User ", $scope.username);
			console.log("Password ", $scope.password);

		}
	}
]);