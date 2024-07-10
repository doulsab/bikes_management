
const myApp = angular.module('myApp', []);

myApp.controller('loginContrl', [
	'$scope',
	($scope) => {

		$scope.init = () => {
			console.log(" this is the main controller login");
		};
	}
]);